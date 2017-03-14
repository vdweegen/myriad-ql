package QL.semantic;

import QL.ast.*;
import QL.ast.atom.BoolAtom;
import QL.ast.atom.IntegerAtom;
import QL.ast.atom.StringAtom;
import QL.ast.expression.*;
import QL.ast.type.*;
import QL.errorhandling.Error;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** CheckCyclicDependencies checks for
 * <li> cyclic dependencies between the questions
 */
public class CheckCyclicDependencies implements FormVisitor, QL.ast.ExpressionVisitor<Type> {
    private final Environment environment;
    private Question current;

    private final Map<String, List<String>> dependencies ;

    public CheckCyclicDependencies(Environment environment) {
        this.environment = environment;
        dependencies = new HashMap<>();
    }

    @Override
        public void visit(Form form) {
        form.getBlock().accept(this);
    }

    @Override
    public void visit(Block block) {
        for (BlockItem blockItem : block.getBlockItems()) {
            blockItem.accept(this);
        }
    }

    @Override
    public void visit(BlockItem blockItem) {
        blockItem.accept(this);
    }

    @Override
    public void visit(Question question) {
    }

    @Override
    public void visit(ComputedQuestion question) {
        current = question;
        question.getComputedQuestion().accept(this);
    }

    @Override
    public void visit(Statement statement) {

    }

    @Override
    public void visit(IfElseStatement statement) {

    }

    @Override
    public Type visit(AddExpr expr) {
        expr.getLhs().accept(this);
        expr.getRhs().accept(this);

        return null;
    }

    @Override
    public Type visit(AndExpr expr) {
        expr.getLhs().accept(this);
        expr.getRhs().accept(this);

        return null;
    }

    @Override
    public Type visit(DivExpr expr) {
        expr.getLhs().accept(this);
        expr.getRhs().accept(this);

        return null;
    }

    @Override
    public Type visit(EqExpr expr) {
        expr.getLhs().accept(this);
        expr.getRhs().accept(this);

        return null;
    }

    @Override
    public Type visit(GEqExpr expr) {
        expr.getLhs().accept(this);
        expr.getRhs().accept(this);

        return null;
    }

    @Override
    public Type visit(GExpr expr) {
        expr.getLhs().accept(this);
        expr.getRhs().accept(this);

        return null;
    }

    @Override
    public Type visit(IdExpr id) {

        addReference(current.getVariable(), id.getName());
        check();

        return null;
    }

    @Override
    public Type visit(LEqExpr expr) {
        expr.getLhs().accept(this);
        expr.getRhs().accept(this);

        return null;
    }

    @Override
    public Type visit(LExpr expr) {
        expr.getLhs().accept(this);
        expr.getRhs().accept(this);

        return null;
    }

    @Override
    public Type visit(MinusExpr expr) {
        expr.getLhs().accept(this);

        return null;
    }


    @Override
    public Type visit(MulExpr expr) {
        expr.getLhs().accept(this);
        expr.getRhs().accept(this);

        return null;
    }

    @Override
    public Type visit(NEqExpr expr) {
        expr.getLhs().accept(this);
        expr.getRhs().accept(this);

        return null;
    }

    @Override
    public Type visit(NotExpr expr) {
        expr.getLhs().accept(this);

        return null;
    }

    @Override
    public Type visit(OrExpr expr) {
        expr.getLhs().accept(this);
        expr.getRhs().accept(this);

        return null;
    }

    @Override
    public Type visit(PlusExpr expr) {
        expr.getLhs().accept(this);

        return null;
    }

    @Override
    public Type visit(SubExpr expr) {
        expr.getLhs().accept(this);
        expr.getRhs().accept(this);

        return null;
    }

    @Override
    public Type visit(BoolAtom expr) {
        return null;
    }

    @Override
    public Type visit(IntegerAtom expr) {
        return null;
    }

    @Override
    public Type visit(StringAtom expr) {
        return null;
    }

    private void check() {
        List<String> references = getReferences(current.getVariable());

        for (String reference: references) {
            List<String> cycleReferences = getReferences(reference);
            if (cycleReferences != null && cycleReferences.contains(current.getVariable())) {
            	environment.getFaults().add(new Error("There is a cyclic dependency in "
            			+ "the computed questions " + current.getVariable() + " and " + reference, 
            			current.getLine()));
            }
        }
    }

    private void addReference(String name, String reference){
        List<String> references;

        if (dependencies.containsKey(name)) {
            references = dependencies.get(name);
        } else {
            references = new ArrayList<>();
        }

        references.add(reference);
        dependencies.put(name, references);
    }
    private List<String> getReferences(String name){
        if (dependencies.containsKey(name)) {
            return dependencies.get(name);
        } else {
            return null;
        }
    }

}
