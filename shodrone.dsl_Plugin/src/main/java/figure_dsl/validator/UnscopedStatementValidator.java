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

// Validator class for unscoped statements in the DSL.
// Unscoped statements are statements not enclosed within a block (like 'before' or 'after').
// Extends the ANTLR-generated base visitor for traversing the parse tree.
public class UnscopedStatementValidator extends dslBaseVisitor<Void> {
    // Reference to the validation plugin for error reporting and accessing declared elements and variables
    private final FigureValidationPlugin plugin;

    // Constructor receives the validation plugin instance
    public UnscopedStatementValidator(FigureValidationPlugin plugin) {
        this.plugin = plugin;
    }

    // Visit an unscoped statement node in the parse tree
    @Override
    public Void visitUnscoped_statement(Unscoped_statementContext ctx) {
        // Add the raw text of the unscoped statement to the plugin's list (for tracking or later use)
        plugin.getUnscopedStatements().add(ctx.getText());

        // If this unscoped statement is a pause statement, validate it accordingly
        if (ctx.pause_statement() != null) {
            validatePause(ctx.pause_statement());
            return null;
        }

        // If the unscoped statement looks like a method call on an element
        // Check if it has at least 4 children (basic sanity for a method call like "element.method(...)")
        // Also verify first child is a TerminalNode (element name) and third child is a MethodContext (method called)
        if (ctx.getChildCount() >= 4 && ctx.getChild(0) instanceof TerminalNode &&
                ctx.getChild(2) instanceof MethodContext methodCtx) {

            // Extract the element name and the line number for error reporting
            String elementName = ctx.getChild(0).getText();
            int line = ctx.getStart().getLine();

            // Check if the element has been declared; report error if undeclared
            if (!plugin.getDeclaredElements().contains(elementName)) {
                plugin.addError(String.format("Error at line %d: Method called on undeclared element '%s'", line, elementName));
                return null;
            }


            String methodName = methodCtx.getChild(0).getText();

            // Validate specific allowed methods for unscoped statements
            switch (methodName) {
                case "lightsOn":
                    validateLightsOn(methodCtx, line);
                    break;
                case "lightsOff":
                    // No parameters to validate for lightsOff()
                    break;
                default:
                    // Error for unsupported methods in unscoped statements
                    plugin.addError(String.format("Error at line %d: Unsupported method '%s' in unscoped statement", line, methodName));
            }
        }

        return null;
    }

    // Validate parameters of the lightsOn() method in unscoped statements
    private void validateLightsOn(MethodContext ctx, int line) {
        // Extract the parameter list node from the method call
        Parameter_listContext paramList = ctx.parameter_list();

        // Validate that exactly 3 parameters (RGB values) are provided
        if (paramList == null || paramList.parameter().size() != 3) {
            plugin.addError(String.format("Error at line %d: lightsOn() requires exactly 3 RGB numeric literals", line));
            return;
        }

        // Iterate over the 3 parameters to validate each as numeric literals within 0-255
        List<ParameterContext> params = paramList.parameter();
        for (int i = 0; i < 3; i++) {
            ParameterContext param = params.get(i);
            String text = param.getText();

            try {
                // Parse the parameter as a double value
                double value = Double.parseDouble(text);

                // Check if value is within valid RGB range
                if (value < 0 || value > 255) {
                    plugin.addError(String.format("Error at line %d: lightsOn() RGB value %d out of range (0-255)", line, i + 1));
                }
            } catch (NumberFormatException e) {
                // Parameter is not a valid numeric literal
                plugin.addError(String.format("Error at line %d: lightsOn() RGB value %d must be a numeric literal, not '%s'", line, i + 1, text));
            }
        }
    }

    // Validate a pause statement
    private void validatePause(Pause_statementContext ctx) {
        // Get line number for error reporting
        int line = ctx.getStart().getLine();

        // Extract the expression provided as the pause duration
        ExpressionContext expr = ctx.expression();

        // Validate that the pause expression is a valid numeric value or variable
        if (expr == null || !isValidNumericExpression(expr)) {
            plugin.addError(String.format("Error at line %d: pause() requires a numeric value or valid expression", line));
        }
    }

    // Recursive helper method to check if an expression is valid numeric:
    // - a number literal, OR
    // - a declared numeric variable (Velocity or Distance), OR
    // - a valid numeric expression made from nested sub-expressions
    private boolean isValidNumericExpression(ExpressionContext expr) {
        // Direct number literal is valid
        if (expr.NUMBER() != null) return true;

        // Variable ID must be declared as numeric (Velocity or Distance)
        if (expr.ID() != null) {
            String var = expr.ID().getText();
            return plugin.getDeclaredVariables().get("Velocity").containsKey(var)
                    || plugin.getDeclaredVariables().get("Distance").containsKey(var);
        }

        // Recursively check binary expressions (two sub-expressions)
        if (expr.expression().size() == 2) {
            return isValidNumericExpression(expr.expression(0))
                    && isValidNumericExpression(expr.expression(1));
        }

        // Recursively check unary expressions (one sub-expression)
        if (expr.expression().size() == 1) {
            return isValidNumericExpression(expr.expression(0));
        }

        // Otherwise invalid
        return false;
    }
}
