package figure_dsl.validator;

import figure_dsl.generated.dslBaseVisitor;
import figure_dsl.generated.dslParser.*;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;
import java.util.Stack;

public class StatementBlockValidator extends dslBaseVisitor<Void> {
    private final FigureValidationPlugin plugin;
    private final Stack<String> blockStack = new Stack<>();

    public StatementBlockValidator(FigureValidationPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Void visitBlock_type(Block_typeContext ctx) {
        String blockType = ctx.getText();
        blockStack.push(blockType);
        return super.visitBlock_type(ctx);
    }

    @Override
    public Void visitEnd_block_type(End_block_typeContext ctx) {
        if (blockStack.isEmpty()) {
            plugin.addError("Error at line " + ctx.getStart().getLine() +
                    ": Block closure found without an opening block");
            return null;
        }
        String expectedEnd = "end" + blockStack.pop().toLowerCase();
        String actualEnd = ctx.getText().toLowerCase();

        if (!actualEnd.equals(expectedEnd)) {
            plugin.addError("Error at line " + ctx.getStart().getLine() +
                    ": Mismatched block closure. Expected '" + expectedEnd +
                    "' but found '" + ctx.getText() + "'");
        }
        return null;
    }

    @Override
    public Void visitGroup_statement_block(Group_statement_blockContext ctx) {
        if (blockStack.isEmpty() ||
                (!blockStack.peek().equals("before") && !blockStack.peek().equals("after"))) {
            plugin.addError("Error at line " + ctx.getStart().getLine() +
                    ": Group blocks must be nested inside before/after blocks");
        }
        return super.visitGroup_statement_block(ctx);
    }

    @Override
    public Void visitMethod(MethodContext ctx) {
        int line = ctx.getStart().getLine();
        String methodName = ctx.getChild(0).getText();

        switch (methodName) {
            case "move":
                validateMove(ctx, line);
                break;
            case "rotate":
                validateRotate(ctx, line);
                break;
            case "lightsOn":
                validateLightsOn(ctx, line);
                break;
        }
        return super.visitMethod(ctx);
    }

    private void validateMove(MethodContext ctx, int line) {
        VectorContext vectorParam = ctx.vector();
        ExpressionContext exprParam = ctx.expression();
        TerminalNode idParam = ctx.ID();

        if (vectorParam == null) {
            plugin.addError(String.format("Error at line %d: move() first parameter must be a vector", line));
        }

        if (exprParam == null || !isNumericOrVariable(exprParam)) {
            plugin.addError(String.format("Error at line %d: move() second parameter must be numeric", line));
        }

        if (idParam != null) {
            String varName = idParam.getText();
            if (!isNumericVariable(varName)) {
                plugin.addError(String.format("Error at line %d: move() third parameter '%s' is not a declared numeric variable", line, varName));
            }
        } else {
            plugin.addError(String.format("Error at line %d: move() third parameter must be a numeric variable", line));
        }
    }

    private void validateRotate(MethodContext ctx, int line) {
        List<Rotate_paramContext> params = ctx.rotate_param();
        if (params.size() != 4) {
            plugin.addError(String.format("Error at line %d: rotate() requires exactly 4 parameters", line));
            return;
        }

        for (int i = 0; i < 2; i++) {
            if (!isVectorRotateParam(params.get(i))) {
                plugin.addError(String.format("Error at line %d: rotate() parameter %d must be a vector or position variable", line, i + 1));
            }
        }

        for (int i = 2; i < 4; i++) {
            ExpressionContext expr = getExpressionFromRotateParam(params.get(i));
            if (expr == null || !isNumericOrVariable(expr)) {
                plugin.addError(String.format("Error at line %d: rotate() parameter %d must be numeric or a numeric variable", line, i + 1));
            }
        }
    }

    private void validateLightsOn(MethodContext ctx, int line) {
        VectorContext vectorParam = ctx.vector();
        TerminalNode idParam = ctx.ID();

        if (vectorParam != null) {
            validateRGBVector(vectorParam, line);
        } else if (idParam != null) {
            validateRGBVariable(idParam.getText(), line);
        } else {
            plugin.addError(String.format("Error at line %d: lightsOn() requires a vector or variable", line));
        }
    }

    private void validateRGBVector(VectorContext vector, int line) {
        List<ExpressionContext> components = vector.expression();
        if (components.size() != 3) {
            plugin.addError(String.format("Error at line %d: RGB vector must have exactly 3 components", line));
            return;
        }

        for (int i = 0; i < 3; i++) {
            ExpressionContext expr = components.get(i);
            if (expr.NUMBER() == null || !isValidRGBValue(expr.NUMBER().getText())) {
                plugin.addError(String.format("Error at line %d: RGB value %d must be between 0-255", line, i + 1));
            }
        }
    }

    private void validateRGBVariable(String varName, int line) {
        if (!plugin.getDeclaredVariables().get("Position").containsKey(varName)) {
            plugin.addError(String.format("Error at line %d: Undefined RGB variable '%s'", line, varName));
        }
    }

    private boolean isVectorRotateParam(Rotate_paramContext param) {
        if (param.vector() != null) return true;
        if (param.ID() != null) {
            String varName = param.ID().getText();
            return plugin.getDeclaredVariables().get("Position").containsKey(varName);
        }
        return false;
    }

    private ExpressionContext getExpressionFromRotateParam(Rotate_paramContext param) {
        if (param.expression() != null) return param.expression();
        if (param.ID() != null) {
            String varName = param.ID().getText();
            if (isNumericVariable(varName)) {
                return param.expression();
            }
        }
        return null;
    }

    private boolean isNumericOrVariable(ExpressionContext expr) {
        if (expr == null) return false;
        if (expr.NUMBER() != null) return true;
        if (expr.ID() != null) return isNumericVariable(expr.ID().getText());
        if (expr.expression().size() == 2) {
            return isNumericOrVariable(expr.expression(0)) && isNumericOrVariable(expr.expression(1));
        }
        if (expr.expression().size() == 1) {
            return isNumericOrVariable(expr.expression(0));
        }
        return false;
    }

    private boolean isNumericVariable(String varName) {
        return plugin.getDeclaredVariables().get("Velocity").containsKey(varName) ||
                plugin.getDeclaredVariables().get("Distance").containsKey(varName);
    }

    private boolean isValidRGBValue(String value) {
        try {
            int num = Integer.parseInt(value);
            return num >= 0 && num <= 255;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public Void visitPause_statement(Pause_statementContext ctx) {
        int line = ctx.getStart().getLine();
        ExpressionContext expr = ctx.expression();

        if (expr == null) {
            plugin.addError(String.format("Error at line %d: pause() requires a numeric parameter", line));
            return null;
        }

        if (expr.NUMBER() != null) {
            try {
                double value = Double.parseDouble(expr.NUMBER().getText());
                if (value <= 0) {
                    plugin.addError(String.format("Error at line %d: pause() value must be positive", line));
                }
            } catch (NumberFormatException e) {
                plugin.addError(String.format("Error at line %d: Invalid numeric value in pause()", line));
            }
        } else {
            validateExpression(expr, line);
        }

        return super.visitPause_statement(ctx);
    }

    private void validateExpression(ExpressionContext expr, int line) {
        if (expr == null) return;

        if (expr.NUMBER() != null) {
            // validated above
        } else if (expr.ID() != null) {
            String varName = expr.ID().getText();
            if (!isNumericVariable(varName)) {
                plugin.addError(String.format("Error at line %d: Undefined numeric variable '%s'", line, varName));
            }
        } else if (expr.expression().size() == 2) {
            validateExpression(expr.expression(0), line);
            validateExpression(expr.expression(1), line);
        } else if (expr.expression().size() == 1) {
            validateExpression(expr.expression(0), line);
        }
    }
}
