import org.antlr.v4.runtime.tree.TerminalNode;

public class DroneOneValidator extends DroneOneBaseVisitor<Void> {

    // Validate variables (declarations)
    @Override
    public Void visitVariable(DroneOneParser.VariableContext ctx) {
        String varName = null;

        if (ctx.type_Var().position() != null) {
            varName = ctx.type_Var().position().IDENTIFIER().getText();
            validateTupleOrArray(ctx.type_Var().position());
        } else if (ctx.type_Var().vector() != null) {
            varName = ctx.type_Var().vector().IDENTIFIER().getText();
            validateTupleExpression(ctx.type_Var().vector().tupleEpression());
        } else if (ctx.type_Var().linearVelocity() != null) {
            varName = ctx.type_Var().linearVelocity().IDENTIFIER().getText();
            validateExpression(ctx.type_Var().linearVelocity().expression());
        } else if (ctx.type_Var().angularVelocity() != null) {
            varName = ctx.type_Var().angularVelocity().IDENTIFIER().getText();
            validateExpression(ctx.type_Var().angularVelocity().expression());
        } else if (ctx.type_Var().distance() != null) {
            varName = ctx.type_Var().distance().IDENTIFIER().getText();
            validateExpression(ctx.type_Var().distance().expression());
        } else if (ctx.type_Var().time() != null) {
            varName = ctx.type_Var().time().IDENTIFIER().getText();
            validateTimeExpression(ctx.type_Var().time().timeExpression());
        }

        if (varName == null || varName.isEmpty()) {
            System.err.println("Erro: Variável com nome inválido");
        } else {
            System.out.println("Variável válida: " + varName);
        }

        return visitChildren(ctx);
    }

    // Helper to validate tuple or array for Position
    private void validateTupleOrArray(DroneOneParser.PositionContext ctx) {
        if (ctx.tupleEpression() != null) {
            validateTupleExpression(ctx.tupleEpression());
        } else if (ctx.array() != null) {
            validateArray(ctx.array());
        } else {
            System.err.println("Erro: Position deve ter tupleEpression ou array");
        }
    }

    // Validate tuple expressions recursively
    private void validateTupleExpression(DroneOneParser.TupleEpressionContext ctx) {
        if (ctx == null) return;

        if (ctx.tuple() != null) {
            validateTuple(ctx.tuple());
        }
        if (ctx.tupleEpression() != null) {
            validateTupleExpression(ctx.tupleEpression());
        }
    }

    // Validate a tuple (LPAREN VALUE COMMA VALUE COMMA VALUE RPAREN)
    private void validateTuple(DroneOneParser.TupleContext ctx) {
        if (ctx == null) return;
        for (TerminalNode val : new TerminalNode[]{ctx.VALUE(0), ctx.VALUE(1), ctx.VALUE(2)}) {
            if (val == null || val.getText().isEmpty()) {
                System.err.println("Erro: Tuple com valor inválido");
            }
        }
    }

    // Validate array of tuples
    private void validateArray(DroneOneParser.ArrayContext ctx) {
        if (ctx == null) return;
        for (DroneOneParser.TupleContext tuple : ctx.tuple()) {
            validateTuple(tuple);
        }
    }

    // Validate arithmetic expression (simplified)
    private void validateExpression(DroneOneParser.ExpressionContext ctx) {
        if (ctx == null) return;
        // Here you could add checks on expression validity (types, operators, etc.)
        // For now, just print the expression text
        System.out.println("Validando expressão: " + ctx.getText());
    }

    // Validate time expressions recursively
    private void validateTimeExpression(DroneOneParser.TimeExpressionContext ctx) {
        if (ctx == null) return;
        System.out.println("Validando time expression: " + ctx.getText());
        // You could add more semantic checks here
    }

    // Validate instructions (function calls)
    @Override
    public Void visitInstruction(DroneOneParser.InstructionContext ctx) {
        String funcName = ctx.IDENTIFIER().getText();
        System.out.println("A validar instrução: " + funcName);

        if (ctx.argumentList() != null) {
            visit(ctx.argumentList());
        }
        return null;
    }

    // Validate arguments list
    @Override
    public Void visitArgumentList(DroneOneParser.ArgumentListContext ctx) {
        TerminalNode varNode = ctx.VARIABLE();
        if (varNode != null) {
            String argName = varNode.getText();
            // VARIABLE is like <variableName> so check length > 2 minimally
            if (argName.length() < 3) {
                System.err.println("Erro: Argumento inválido " + argName);
            } else {
                System.out.println("Argumento válido: " + argName);
            }
        }
        if (ctx.COMMA() != null && ctx.argumentList() != null) {
            visit(ctx.argumentList());
        }
        return null;
    }
}
