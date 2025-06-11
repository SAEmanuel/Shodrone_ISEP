package figure_dsl.validator;

import figure_dsl.generated.dslBaseVisitor;
import figure_dsl.generated.dslParser.*;
import java.util.Map;

public class DeclaredVariablesValidator extends dslBaseVisitor<Void> {
    private final FigureValidationPlugin validator;

    public DeclaredVariablesValidator(FigureValidationPlugin validator) {
        this.validator = validator;
    }

    @Override
    public Void visitPosition_declaration(Position_declarationContext ctx) {
        String varName = ctx.ID().getText();
        String varType = "Position";
        VectorContext vector = ctx.vector();
        int lineNumber = ctx.getStart().getLine();

        if (vector.expression().size() != 3) {
            validator.addError(String.format("[Semantic Error] Line %d: Position '%s' must be a vector with exactly 3 components.", lineNumber, varName));
            return null;
        }

        for (int i = 0; i < 3; i++) {
            ExpressionContext component = vector.expression(i);
            if (!isValidNumericExpression(component)) {
                validator.addError(String.format("[Semantic Error] Line %d: Component %d of Position '%s' is not a valid numeric expression.", lineNumber, i + 1, varName));
                return null;
            }
        }

        String value = buildVectorValue(vector);
        addVariable(varType, varName, value, lineNumber);
        return null;
    }

    @Override
    public Void visitVelocity_declaration(Velocity_declarationContext ctx) {
        return validateScalarDeclaration(ctx.ID().getText(), "Velocity", ctx.expression(), ctx.getStart().getLine());
    }

    @Override
    public Void visitDistance_declaration(Distance_declarationContext ctx) {
        return validateScalarDeclaration(ctx.ID().getText(), "Distance", ctx.expression(), ctx.getStart().getLine());
    }

    private Void validateScalarDeclaration(String varName, String varType, ExpressionContext expr, int lineNumber) {
        if (!isValidNumericExpression(expr)) {
            validator.addError(String.format("[Semantic Error] Line %d: %s '%s' must be a valid numeric expression (number, variable or arithmetic expression).", lineNumber, varType, varName));
            return null;
        }

        addVariable(varType, varName, getExpressionText(expr), lineNumber);
        return null;
    }

    /**
     * Valida se a expressão é:
     * - um número
     * - uma variável declarada do tipo Velocity ou Distance
     * - uma operação aritmética entre expressões válidas
     */
    private boolean isValidNumericExpression(ExpressionContext expr) {
        if (expr == null) return false;

        if (expr.NUMBER() != null) {
            return true; // É um número
        } else if (expr.ID() != null) {
            String varName = expr.ID().getText();
            // Tem que ser uma variável de Velocity ou Distance
            return validator.getDeclaredVariables().get("Velocity").containsKey(varName) ||
                    validator.getDeclaredVariables().get("Distance").containsKey(varName);
        } else if (expr.expression().size() == 2) {
            // Operação binária
            return isValidNumericExpression(expr.expression(0)) &&
                    isValidNumericExpression(expr.expression(1));
        } else if (expr.expression().size() == 1) {
            // Expressão entre parêntesis
            return isValidNumericExpression(expr.expression(0));
        }

        return false;
    }

    private void addVariable(String varType, String varName, String value, int lineNumber) {
        Map<String, String> variables = validator.getDeclaredVariables().get(varType);
        if (variables.containsKey(varName)) {
            validator.addError(String.format("[Semantic Error] Line %d: Duplicate variable '%s' of type '%s' declared.", lineNumber, varName, varType));
        } else {
            variables.put(varName, value);
        }
    }

    private String buildVectorValue(VectorContext vector) {
        return String.format("(%s,%s,%s)",
                getExpressionText(vector.expression(0)),
                getExpressionText(vector.expression(1)),
                getExpressionText(vector.expression(2)));
    }

    private String getExpressionText(ExpressionContext expr) {
        if (expr == null) return "";
        if (expr.NUMBER() != null) {
            return expr.NUMBER().getText();
        } else if (expr.ID() != null) {
            return expr.ID().getText();
        } else if (expr.expression().size() == 2) {
            return String.format("%s %s %s",
                    getExpressionText(expr.expression(0)),
                    expr.getChild(1).getText(),
                    getExpressionText(expr.expression(1)));
        } else if (expr.expression().size() == 1) {
            return String.format("(%s)", getExpressionText(expr.expression(0)));
        }
        return expr.getText();
    }
}