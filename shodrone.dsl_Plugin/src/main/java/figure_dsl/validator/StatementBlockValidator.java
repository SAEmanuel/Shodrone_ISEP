package figure_dsl.validator;

import figure_dsl.generated.dslBaseVisitor;
import figure_dsl.generated.dslParser.*;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;
import java.util.Stack;

// Validator class that visits the parse tree to check the correctness of statement blocks in the DSL.
// Extends the ANTLR-generated base visitor class for the DSL.
public class StatementBlockValidator extends dslBaseVisitor<Void> {
    // Reference to the main validation plugin to report errors and access declared elements/variables
    private final FigureValidationPlugin plugin;

    // Stack to keep track of open blocks ('before', 'after', etc.)
    private final Stack<String> blockStack = new Stack<>();

    // Flag to indicate if an 'after' block has been seen, used to enforce block order rules
    private boolean afterBlockSeen = false;

    // Constructor stores the plugin instance
    public StatementBlockValidator(FigureValidationPlugin plugin) {
        this.plugin = plugin;
    }

    // Visit a block type declaration (e.g., "before", "after")
    @Override
    public Void visitBlock_type(Block_typeContext ctx) {
        String blockType = ctx.getText();

        // Enforce rule: 'before' block cannot appear after an 'after' block
        if (afterBlockSeen && blockType.equals("before")) {
            plugin.addError("Error at line " + ctx.getStart().getLine() + ": 'before' block cannot appear after 'after' block");
        }

        // Push the block type to the stack to track nesting
        blockStack.push(blockType);

        // Mark that we've seen an 'after' block (to restrict future 'before' blocks)
        if (blockType.equals("after")) afterBlockSeen = true;

        // Continue visiting children nodes
        return super.visitBlock_type(ctx);
    }

    // Visit the closing of a block (e.g., "endbefore", "endafter")
    @Override
    public Void visitEnd_block_type(End_block_typeContext ctx) {
        // If no open blocks, error on unmatched closure
        if (blockStack.isEmpty()) {
            plugin.addError("Error at line " + ctx.getStart().getLine() + ": Block closure found without an opening block");
            return null;
        }

        // Expected closing block keyword based on last opened block
        String expectedEnd = "end" + blockStack.pop().toLowerCase();

        // Actual closing block keyword found in code
        String actualEnd = ctx.getText().toLowerCase();

        // Report error if block closure does not match expected
        if (!actualEnd.equals(expectedEnd)) {
            plugin.addError("Error at line " + ctx.getStart().getLine() + ": Mismatched block closure. Expected '" + expectedEnd + "' but found '" + actualEnd + "'");
        }
        return null;
    }

    // Visit a 'group' statement block
    @Override
    public Void visitGroup_statement_block(Group_statement_blockContext ctx) {
        // Validate that 'group' blocks only appear inside 'before' or 'after' blocks or at top-level (not inside other blocks)
        if (!blockStack.isEmpty() &&
                !(blockStack.peek().equals("before") || blockStack.peek().equals("after"))) {
            plugin.addError("Error at line " + ctx.getStart().getLine() + ": 'group' blocks must appear inside 'before' or 'after' blocks or be top-level");
        }
        return super.visitGroup_statement_block(ctx);
    }

    // Visit a generic statement, which may call methods on elements
    @Override
    public Void visitStatement(StatementContext ctx) {
        // Only proceed if the statement contains a method call
        if (ctx.method() != null) {
            int line = ctx.getStart().getLine();

            // Extract method name and element name on which the method is called
            String methodName = ctx.method().getChild(0).getText();
            String elementName = ctx.ID().getText();

            // Check if the element has been declared; error if not
            if (!plugin.getDeclaredElements().contains(elementName)) {
                plugin.addError(String.format("Error at line %d: Method '%s' called on undeclared element '%s'", line, methodName, elementName));
            }

            // Dispatch to specific validation methods based on method name
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
                    // No parameters, no validation needed here
                    break;
                default:
                    // Unknown method called on element
                    plugin.addError(String.format("Error at line %d: Unknown method '%s'", line, methodName));
            }
        }
        return super.visitStatement(ctx);
    }

    // Validate parameters for move() method calls
    private void validateMove(MethodContext ctx, int line) {
        // First parameter must be a vector literal
        VectorContext vector = ctx.vector();
        if (vector == null) {
            plugin.addError(String.format("Error at line %d: move() first parameter must be a vector literal", line));
        }

        // Second parameter must be numeric or a numeric variable
        ExpressionContext expr = ctx.expression();
        if (expr == null || !isNumericOrVariable(expr)) {
            plugin.addError(String.format("Error at line %d: move() second parameter must be numeric or numeric variable", line));
        }

        // Third parameter must be a declared numeric variable (Velocity or Distance)
        TerminalNode id = ctx.ID();
        if (id == null || !isNumericVariable(id.getText())) {
            plugin.addError(String.format("Error at line %d: move() third parameter must be a declared numeric variable (Velocity or Distance)", line));
        }
    }

    // Validate parameters for rotate() method calls
    private void validateRotate(MethodContext ctx, int line) {
        List<Rotate_paramContext> params = ctx.rotate_param();

        // Must have exactly 4 parameters
        if (params.size() != 4) {
            plugin.addError(String.format("Error at line %d: rotate() requires exactly 4 parameters", line));
            return;
        }

        // First two parameters must be vector literals or declared Position variables
        for (int i = 0; i < 2; i++) {
            Rotate_paramContext param = params.get(i);
            if (param.vector() != null) continue;
            ExpressionContext expr = param.expression();
            if (expr == null || !isPositionVariable(expr)) {
                plugin.addError(String.format("Error at line %d: rotate() parameter %d must be a vector or declared Position", line, i + 1));
            }
        }

        // Third parameter must be numeric or declared Velocity/Distance variable
        ExpressionContext expr3 = params.get(2).expression();
        if (expr3 == null || !isNumericOrVariable(expr3)) {
            plugin.addError(String.format("Error at line %d: rotate() parameter 3 must be numeric or declared Velocity/Distance", line));
        }

        // Fourth parameter must be a number or declared Velocity variable
        ExpressionContext expr4 = params.get(3).expression();
        if (expr4 == null || !isVelocityLiteralOrVariable(expr4)) {
            plugin.addError(String.format("Error at line %d: rotate() parameter 4 must be a number or declared Velocity", line));
        }
    }

    // Validate parameters for lightsOn() method calls
    private void validateLightsOn(MethodContext ctx, int line) {
        Parameter_listContext paramList = ctx.parameter_list();

        // Must have exactly 3 parameters representing RGB values
        if (paramList == null || paramList.parameter().size() != 3) {
            plugin.addError(String.format("Error at line %d: lightsOn() requires exactly 3 numeric literal RGB values", line));
            return;
        }

        List<ParameterContext> params = paramList.parameter();

        // Check each parameter is a numeric literal between 0 and 255 (valid RGB)
        for (int i = 0; i < 3; i++) {
            ParameterContext param = params.get(i);
            String text = param.getText();
            try {
                int value = Integer.parseInt(text);
                if (value < 0 || value > 255) {
                    plugin.addError(String.format("Error at line %d: lightsOn() RGB value %d must be in range 0–255", line, i + 1));
                }
            } catch (NumberFormatException e) {
                // Parameter was not a valid integer literal
                plugin.addError(String.format("Error at line %d: lightsOn() parameter %d must be a numeric literal, not '%s'", line, i + 1, text));
            }
        }
    }

    // Helper method to check if an expression is a numeric literal or numeric variable (Velocity/Distance)
    private boolean isNumericOrVariable(ExpressionContext expr) {
        if (expr.NUMBER() != null) return true;
        if (expr.ID() != null) return isNumericVariable(expr.ID().getText());

        // Check recursively for nested expressions (unary or binary)
        if (expr.expression().size() == 1) {
            return isNumericOrVariable(expr.expression(0));
        }
        if (expr.expression().size() == 2) {
            return isNumericOrVariable(expr.expression(0)) && isNumericOrVariable(expr.expression(1));
        }
        return false;
    }

    // Helper method to check if an expression is a Velocity literal or variable
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

    // Helper method to check if a variable name is a declared numeric variable (Velocity or Distance)
    private boolean isNumericVariable(String varName) {
        return plugin.getDeclaredVariables().get("Velocity").containsKey(varName) ||
                plugin.getDeclaredVariables().get("Distance").containsKey(varName);
    }

    // Helper method to check if an expression is a declared Position variable
    private boolean isPositionVariable(ExpressionContext expr) {
        return expr.ID() != null &&
                plugin.getDeclaredVariables().get("Position").containsKey(expr.ID().getText());
    }
}
