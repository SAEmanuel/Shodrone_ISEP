package drone_language_validation.validators;

import drone_language_validation.generated.droneGenericParser;
import  drone_language_validation.generated.droneGenericBaseVisitor;

public class DroneProgramVisitor extends droneGenericBaseVisitor<Void> {

    @Override
    public Void visitProgram(droneGenericParser.ProgramContext ctx) {
        System.out.println("Visiting program...");
        return super.visitProgram(ctx);
    }

    @Override
    public Void visitVarDeclaration(droneGenericParser.VariableDeclarationContext ctx) {
        String type = ctx.type().getText();
        String id = ctx.ID().getText();
        System.out.println("Declared variable: " + type + " " + id);
        return visit(ctx.expression());
    }

    @Override
    public Void visitInstruction(droneGenericParser.InstructionContext ctx) {
        System.out.println("Instruction: " + ctx.getText());
        return visitChildren(ctx);
    }

    @Override
    public Void visitExpressionVector(droneGenericParser.ExpressionVectorContext ctx) {
        System.out.println("Vector expression: " + ctx.getText());
        return visit(ctx.vectorExpr());
    }

    @Override
    public Void visitExpressionTuple(droneGenericParser.ExpressionTupleContext ctx) {
        System.out.println("Tuple expression: " + ctx.getText());
        return visit(ctx.tupleExpr());
    }

    @Override
    public Void visitExpressionArray(droneGenericParser.ExpressionArrayContext ctx) {
        System.out.println("Array of tuples: " + ctx.getText());
        return visit(ctx.arrayOfTuples());
    }

    @Override
    public Void visitExpressionMulDiv(droneGenericParser.ExpressionMulDivContext ctx) {
        System.out.println("Arithmetic multiplication/division: " + ctx.getText());
        return visitChildren(ctx);
    }

    @Override
    public Void visitExpressionAddSub(droneGenericParser.ExpressionAddSubContext ctx) {
        System.out.println("Arithmetic addition/subtraction: " + ctx.getText());
        return visitChildren(ctx);
    }

    @Override
    public Void visitExpressionPiDiv(droneGenericParser.ExpressionPiDivContext ctx) {
        System.out.println("Pi division expression: " + ctx.getText());
        return null;
    }

    @Override
    public Void visitExpressionFloat(droneGenericParser.ExpressionFloatContext ctx) {
        System.out.println("Float value: " + ctx.getText());
        return null;
    }

    @Override
    public Void visitExpressionInt(droneGenericParser.ExpressionIntContext ctx) {
        System.out.println("Int value: " + ctx.getText());
        return null;
    }

    @Override
    public Void visitExpressionVarRef(droneGenericParser.ExpressionVarRefContext ctx) {
        System.out.println("Variable reference: " + ctx.getText());
        return null;
    }
}
