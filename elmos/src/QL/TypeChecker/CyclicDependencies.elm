module QL.TypeChecker.CyclicDependencies exposing (cyclicDependencies)

import DictList exposing (DictList)
import List.Extra as List
import QL.AST exposing (Expression, Form, Id)
import QL.FormVisitor exposing (defaultConfig, inspect)
import QL.TypeChecker.CheckerUtil as CheckerUtil
import QL.TypeChecker.Messages as Messages exposing (Message)
import Set exposing (Set)
import VisitorUtil exposing (Order(Post))


type alias DependencyTable =
    DictList String (Set String)


type alias DependencyEntry =
    ( String, Set String )


type alias DependencyCycle =
    List String


cyclicDependencies : Form -> List Message
cyclicDependencies form =
    let
        dependencyTable =
            collectComputedFields form
                |> List.map extractDependencies
                |> toDependencyTable
    in
        List.concatMap (asCyclicDependencies [] dependencyTable) (DictList.keys dependencyTable)
            |> List.uniqueBy (Set.fromList >> toString)
            |> List.map Messages.dependencyCycle


asCyclicDependencies : List String -> DependencyTable -> String -> List DependencyCycle
asCyclicDependencies visited dependencyTable currentVar =
    if List.member currentVar visited then
        [ visited ++ [ currentVar ] ]
    else
        Set.map
            (asCyclicDependencies (visited ++ [ currentVar ]) dependencyTable)
            (dependenciesOf currentVar dependencyTable)
            |> Set.toList
            |> List.concat


dependenciesOf : String -> DependencyTable -> Set String
dependenciesOf name table =
    DictList.get name table |> Maybe.withDefault Set.empty


collectComputedFields : Form -> List ( String, Expression )
collectComputedFields form =
    inspect
        { defaultConfig
            | onComputedField = Post (\( _, ( name, _ ), _, computation ) result -> ( name, computation ) :: result)
        }
        form
        []


extractDependencies : ( String, Expression ) -> DependencyEntry
extractDependencies ( name, computation ) =
    ( name, CheckerUtil.collectQuestionReferences computation |> uniqueVarNames )


toDependencyTable : List DependencyEntry -> DependencyTable
toDependencyTable entries =
    List.foldr (\entry result -> updateDependencyTable entry result) DictList.empty entries


updateDependencyTable : DependencyEntry -> DependencyTable -> DependencyTable
updateDependencyTable ( name, dependencies ) result =
    DictList.update
        name
        (Maybe.withDefault Set.empty >> Set.union dependencies >> Just)
        result


uniqueVarNames : List Id -> Set String
uniqueVarNames =
    List.map Tuple.first >> Set.fromList
