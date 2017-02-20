module TypeChecker.BadReferencesTests exposing (all)

import TypeChecker.BadReferences exposing (badReferences)
import Parser.Form exposing (form)
import Test exposing (..)
import ParserTestUtil exposing (parseToMaybe)
import Set
import Expect


badReferencesExample1 : String
badReferencesExample1 =
    """form taxOfficeExample {
        if (hasSoldHouse) {
            "What was the selling price?"
            sellingPrice: money
        }
      }"""


badReferencesExample2 : String
badReferencesExample2 =
    """form taxOfficeExample {
        "What was the selling price?"
        sellingPrice: money = price * 2
      }"""


goodExample1 : String
goodExample1 =
    """form taxOfficeExample {
        if (sellingPrice){
            "Question ?"
            y: integer
        }

        if (true) {
            "What was the selling price?"
            sellingPrice: integer = 3
        } else {
            "What was the selling price?"
            sellingPrice: integer = 4
        }
      }"""


goodExample2 : String
goodExample2 =
    """form taxOfficeExample {
      "Q?"
      x: money = sellingPrice

      if (true) {
          "What was the selling price?"
          sellingPrice: integer = 3
      } else {
          "What was the selling price?"
          sellingPrice: integer = 4
      }
    }"""


goodExample3 : String
goodExample3 =
    """form taxOfficeExample {
    if (true) {
        "Q?"
        variable: money = sellingPrice
    }


    if (true) {
        "What was the selling price?"
        sellingPrice: money = 3
    } else {
        "What was the selling price?"
        sellingPrice: money = 4
    }
  }"""


goodExample4 : String
goodExample4 =
    """form taxOfficeExample {
  "Did you sell a house in 2010?"
    hasSoldHouse: boolean
}
"""


all : Test
all =
    describe "BadReferences"
        [ testExamplesWithoutBadRefences ]


testFindBadReferences : Test
testFindBadReferences =
    describe "testFindBadReferences"
        [ parseAndFindExpectedBadReferences "Bad reference in If block" badReferencesExample1 (Set.fromList [ "hasSoldHouse" ])
        , parseAndFindExpectedBadReferences "Bad reference in If block" badReferencesExample1 (Set.fromList [ "price" ])
        ]


testExamplesWithoutBadRefences : Test
testExamplesWithoutBadRefences =
    describe "testExamplesWithoutBadRefences"
        [ parseAndFindExpectedBadReferences "Order of definition/usage should not matter 1" goodExample1 Set.empty
        , parseAndFindExpectedBadReferences "Order of definition/usage should not matter 2" goodExample2 Set.empty
        , parseAndFindExpectedBadReferences "Order of definition/usage should not matter 3" goodExample3 Set.empty
        , parseAndFindExpectedBadReferences "Should not find any undefined used vars" goodExample4 Set.empty
        ]


parseAndFindExpectedBadReferences : String -> String -> Set.Set String -> Test
parseAndFindExpectedBadReferences message input expectedBadReferences =
    test message <|
        \() ->
            parseAndGetBadReferences input
                |> Expect.equal (Just expectedBadReferences)


parseAndGetBadReferences : String -> Maybe (Set.Set String)
parseAndGetBadReferences rawForm =
    Maybe.map badReferences (parseToMaybe form rawForm)