package figure_dsl.validator;

import figure_dsl.generated.dslBaseVisitor;
import figure_dsl.generated.dslParser.ExpressionContext;
import figure_dsl.generated.dslParser.MethodContext;
import figure_dsl.generated.dslParser.ParameterContext;
import figure_dsl.generated.dslParser.Parameter_listContext;
import figure_dsl.generated.dslParser.Pause_statementContext;
import figure_dsl.generated.dslParser.Unscoped_statementContext;

import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

public class UnscopedStatementValidator extends dslBaseVisitor<Void> {
    private final FigureValidationPlugin plugin;

    public UnscopedStatementValidator(FigureValidationPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Void visitUnscoped_statement(Unscoped_statementContext ctx) {
        plugin.getUnscopedStatements().add(ctx.getText());

        if (ctx.pause_statement() != null) {
            validatePause(ctx.pause_statement());
            return null;
        }

        if (ctx.getChildCount() >= 4 && ctx.getChild(0) instanceof TerminalNode &&
                ctx.getChild(2) instanceof MethodContext methodCtx) {

            String elementName = ctx.getChild(0).getText();
            int line = ctx.getStart().getLine();

            if (!plugin.getDeclaredElements().contains(elementName)) {
                plugin.addError(String.format("Error at line %d: Method called on undeclared element '%s'", line, elementName));
                return null;
            }


            String methodName = methodCtx.getChild(0).getText();

            switch (methodName) {
                case "lightsOn":
                    validateLightsOn(methodCtx, line);
                    break;
                case "lightsOff":
                    break;
                default:
                    plugin.addError(String.format("Error at line %d: Unsupported method '%s' in unscoped statement", line, methodName));
            }
        }

        return null;
    }

    private void validateLightsOn(MethodContext ctx, int line) {
        Parameter_listContext paramList = ctx.parameter_list();
        if (paramList == null || paramList.parameter().size() != 3) {
            plugin.addError(String.format("Error at line %d: lightsOn() requires exactly 3 RGB numeric literals", line));
            return;
        }

        List<ParameterContext> params = paramList.parameter();
        for (int i = 0; i < 3; i++) {
            ParameterContext param = params.get(i);
            String text = param.getText();

            try {
                double value = Double.parseDouble(text);
                if (value < 0 || value > 255) {
                    plugin.addError(String.format("Error at line %d: lightsOn() RGB value %d out of range (0-255)", line, i + 1));
                }
            } catch (NumberFormatException e) {
                plugin.addError(String.format("Error at line %d: lightsOn() RGB value %d must be a numeric literal, not '%s'", line, i + 1, text));
            }
        }
    }

    private void validatePause(Pause_statementContext ctx) {
        int line = ctx.getStart().getLine();
        ExpressionContext expr = ctx.expression();

        if (expr == null || !isValidNumericExpression(expr)) {
            plugin.addError(String.format("Error at line %d: pause() requires a numeric value or valid expression", line));
        }
    }

    private boolean isValidNumericExpression(ExpressionContext expr) {
        if (expr.NUMBER() != null) return true;
        if (expr.ID() != null) {
            String var = expr.ID().getText();
            return plugin.getDeclaredVariables().get("Velocity").containsKey(var)
                    || plugin.getDeclaredVariables().get("Distance").containsKey(var);
        }
        if (expr.expression().size() == 2) {
            return isValidNumericExpression(expr.expression(0))
                    && isValidNumericExpression(expr.expression(1));
        }
        if (expr.expression().size() == 1) {
            return isValidNumericExpression(expr.expression(0));
        }
        return false;
    }
}