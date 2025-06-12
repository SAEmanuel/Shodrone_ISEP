package figure_dsl.validator;

import figure_dsl.generated.dslBaseVisitor;
import figure_dsl.generated.dslParser.*;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;
import java.util.Stack;

public class StatementBlockValidator extends dslBaseVisitor<Void> {
    private final FigureValidationPlugin plugin;
    private final Stack<String> blockStack = new Stack<>();
    private boolean afterBlockSeen = false;

    public StatementBlockValidator(FigureValidationPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Void visitBlock_type(Block_typeContext ctx) {
        String blockType = ctx.getText();
        if (afterBlockSeen && blockType.equals("before")) {
            plugin.addError("Error at line " + ctx.getStart().getLine() + ": 'before' block cannot appear after 'after' block");
        }
        blockStack.push(blockType);
        if (blockType.equals("after")) afterBlockSeen = true;
        return super.visitBlock_type(ctx);
    }

    @Override
    public Void visitEnd_block_type(End_block_typeContext ctx) {
        if (blockStack.isEmpty()) {
            plugin.addError("Error at line " + ctx.getStart().getLine() + ": Block closure found without an opening block");
            return null;
        }
        String expectedEnd = "end" + blockStack.pop().toLowerCase();
        String actualEnd = ctx.getText().toLowerCase();

        if (!actualEnd.equals(expectedEnd)) {
            plugin.addError("Error at line " + ctx.getStart().getLine() + ": Mismatched block closure. Expected '" + expectedEnd + "' but found '" + actualEnd + "'");
        }
        return null;
    }

    @Override
    public Void visitGroup_statement_block(Group_statement_blockContext ctx) {
        if (!blockStack.isEmpty() &&
                !(blockStack.peek().equals("before") || blockStack.peek().equals("after"))) {
            plugin.addError("Error at line " + ctx.getStart().getLine() + ": 'group' blocks must appear inside 'before' or 'after' blocks or be top-level");
        }
        return super.visitGroup_statement_block(ctx);
    }

    @Override
    public Void visitStatement(StatementContext ctx) {
        if (ctx.method() != null) {
            int line = ctx.getStart().getLine();
            String methodName = ctx.method().getChild(0).getText();
            String elementName = ctx.ID().getText();

            if (!plugin.getDeclaredElements().contains(elementName)) {
                plugin.addError(String.format("Error at line %d: Method '%s' called on undeclared element '%s'", line, methodName, elementName));
            }

            switch (methodName) {
                case "move":
                    validateMove(ctx.method(), line);
                    break;
                case "rotate":
                    validateRotate(ctx.method(), line);
                    break;
                case "lightsOn":
                    validateLightsOn(ctx.method(), line);
                    break;
                case "lightsOff":
                    break;
                default:
                    plugin.addError(String.format("Error at line %d: Unknown method '%s'", line, methodName));
            }
        }
        return super.visitStatement(ctx);
    }

    private void validateMove(MethodContext ctx, int line) {
        VectorContext vector = ctx.vector();
        if (vector == null) {
            plugin.addError(String.format("Error at line %d: move() first parameter must be a vector literal", line));
        }

        ExpressionContext expr = ctx.expression();
        if (expr == null || !isNumericOrVariable(expr)) {
            plugin.addError(String.format("Error at line %d: move() second parameter must be numeric or numeric variable", line));
        }

        TerminalNode id = ctx.ID();
        if (id == null || !isNumericVariable(id.getText())) {
            plugin.addError(String.format("Error at line %d: move() third parameter must be a declared numeric variable (Velocity or Distance)", line));
        }
    }

    private void validateRotate(MethodContext ctx, int line) {
        List<Rotate_paramContext> params = ctx.rotate_param();
        if (params.size() != 4) {
            plugin.addError(String.format("Error at line %d: rotate() requires exactly 4 parameters", line));
            return;
        }

        for (int i = 0; i < 2; i++) {
            Rotate_paramContext param = params.get(i);
            if (param.vector() != null) continue;
            ExpressionContext expr = param.expression();
            if (expr == null || !isPositionVariable(expr)) {
                plugin.addError(String.format("Error at line %d: rotate() parameter %d must be a vector or declared Position", line, i + 1));
            }
        }

        ExpressionContext expr3 = params.get(2).expression();
        if (expr3 == null || !isNumericOrVariable(expr3)) {
            plugin.addError(String.format("Error at line %d: rotate() parameter 3 must be numeric or declared Velocity/Distance", line));
        }

        ExpressionContext expr4 = params.get(3).expression();
        if (expr4 == null || !isVelocityLiteralOrVariable(expr4)) {
            plugin.addError(String.format("Error at line %d: rotate() parameter 4 must be a number or declared Velocity", line));
        }
    }

    private void validateLightsOn(MethodContext ctx, int line) {
        Parameter_listContext paramList = ctx.parameter_list();
        if (paramList == null || paramList.parameter().size() != 3) {
            plugin.addError(String.format("Error at line %d: lightsOn() requires exactly 3 numeric literal RGB values", line));
            return;
        }

        List<ParameterContext> params = paramList.parameter();
        for (int i = 0; i < 3; i++) {
            ParameterContext param = params.get(i);
            String text = param.getText();
            try {
                int value = Integer.parseInt(text);
                if (value < 0 || value > 255) {
                    plugin.addError(String.format("Error at line %d: lightsOn() RGB value %d must be in range 0–255", line, i + 1));
                }
            } catch (NumberFormatException e) {
                plugin.addError(String.format("Error at line %d: lightsOn() parameter %d must be a numeric literal, not '%s'", line, i + 1, text));
            }
        }
    }

    private boolean isNumericOrVariable(ExpressionContext expr) {
        if (expr.NUMBER() != null) return true;
        if (expr.ID() != null) return isNumericVariable(expr.ID().getText());
        if (expr.expression().size() == 1) {
            return isNumericOrVariable(expr.expression(0));
        }
        if (expr.expression().size() == 2) {
            return isNumericOrVariable(expr.expression(0)) && isNumericOrVariable(expr.expression(1));
        }
        return false;
    }

    private boolean isVelocityLiteralOrVariable(ExpressionContext expr) {
        if (expr.NUMBER() != null) return true;
        if (expr.ID() != null) {
            return plugin.getDeclaredVariables().get("Velocity").containsKey(expr.ID().getText());
        }
        if (expr.expression().size() == 1) {
            return isVelocityLiteralOrVariable(expr.expression(0));
        }
        if (expr.expression().size() == 2) {
            return isVelocityLiteralOrVariable(expr.expression(0)) &&
                    isVelocityLiteralOrVariable(expr.expression(1));
        }
        return false;
    }

    private boolean isNumericVariable(String varName) {
        return plugin.getDeclaredVariables().get("Velocity").containsKey(varName) ||
                plugin.getDeclaredVariables().get("Distance").containsKey(varName);
    }

    private boolean isPositionVariable(ExpressionContext expr) {
        return expr.ID() != null &&
                plugin.getDeclaredVariables().get("Position").containsKey(expr.ID().getText());
    }
}