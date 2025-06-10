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
        validateVectorDeclaration(ctx.ID().getText(), "Position", ctx.vector(), ctx.getStart().getLine());
        return null;
    }

    @Override
    public Void visitVelocity_declaration(Velocity_declarationContext ctx) {
        validateScalarDeclaration(ctx.ID().getText(), "Velocity", ctx.expression(), ctx.getStart().getLine());
        return null;
    }

    @Override
    public Void visitDistance_declaration(Distance_declarationContext ctx) {
        validateScalarDeclaration(ctx.ID().getText(), "Distance", ctx.expression(), ctx.getStart().getLine());
        return null;
    }

    private void validateVectorDeclaration(String varName, String varType, VectorContext vector, int lineNumber) {
        if (vector.expression().size() != 3) {
            validator.addError(String.format("Error at line %d: Invalid vector for '%s'. Expected (x,y,z) with numeric components.",
                    lineNumber, varName));
            return;
        }

        for (int i = 0; i < 3; i++) {
            if (!isNumericExpression(vector.expression(i))) {
                validator.addError(String.format("Error at line %d: Component %d of vector for '%s' must be a numeric value.",
                        lineNumber, i + 1, varName));
                return;
            }
        }

        addVariable(varType, varName, buildVectorValue(vector), lineNumber);
    }

    private void validateScalarDeclaration(String varName, String varType, ExpressionContext expr, int lineNumber) {
        if (!isNumericExpression(expr)) {
            validator.addError(String.format("Error at line %d: %s '%s' must have a numeric value (float or integer).",
                    lineNumber, varType, varName));
            return;
        }

        addVariable(varType, varName, getExpressionText(expr), lineNumber);
    }

    private boolean isNumericExpression(ExpressionContext expr) {
        return expr != null && expr.NUMBER() != null;
    }

    private void addVariable(String varType, String varName, String value, int lineNumber) {
        Map<String, String> variables = validator.getDeclaredVariables().get(varType);
        if (variables.containsKey(varName)) {
            validator.addError(String.format("Error at line %d: Duplicate variable '%s' of type '%s' declared.",
                    lineNumber, varName, varType));
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
