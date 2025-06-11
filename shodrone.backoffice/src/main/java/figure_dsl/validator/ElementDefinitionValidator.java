package figure_dsl.validator;

import figure_dsl.generated.dslBaseVisitor;
import figure_dsl.generated.dslParser.Element_definitionContext;
import figure_dsl.generated.dslParser.ParameterContext;

import java.util.Collections;
import java.util.List;

public class ElementDefinitionValidator extends dslBaseVisitor<Void> {
    private final FigureValidationPlugin plugin;

    public ElementDefinitionValidator(FigureValidationPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Void visitElement_definition(Element_definitionContext ctx) {
        String elementType = ctx.ID(0).getText();
        String elementName = ctx.ID(1).getText();
        List<ParameterContext> params = ctx.parameter_list() != null ? ctx.parameter_list().parameter() : Collections.emptyList();
        int lineNumber = ctx.getStart().getLine();

        if (params.size() < 3 || params.size() > 4) {
            plugin.addError(String.format(
                    "Error at line %d: Element '%s' must have 3 or 4 parameters (position, numeric values, droneType). Found: %d",
                    lineNumber, elementName, params.size()));
            return null;
        }

        ParameterContext firstParam = params.get(0);
        if (!isVectorOrDeclaredPosition(firstParam)) {
            plugin.addError(String.format(
                    "Error at line %d: First parameter of element '%s' must be a Position (vector literal or declared Position). Found: '%s'",
                    lineNumber, elementName, firstParam.getText()));
        }

        ParameterContext lastParam = params.get(params.size() - 1);
        String droneTypeName = lastParam.getText();
        if (!plugin.getDeclaredVariables().get("DroneType").containsKey(droneTypeName)) {
            plugin.addError(String.format(
                    "Error at line %d: Last parameter of element '%s' must be the declared DroneType. Found: '%s'",
                    lineNumber, elementName, droneTypeName));
        } else if (plugin.getDroneModelName() != null && !droneTypeName.equals(plugin.getDroneModelName())) {
            plugin.addError(String.format(
                    "Error at line %d: DroneType '%s' does not match the declared DroneType '%s'.",
                    lineNumber, droneTypeName, plugin.getDroneModelName()));
        }

        for (int i = 1; i < params.size() - 1; i++) {
            ParameterContext param = params.get(i);
            if (!isNumericOrDeclaredNumeric(param)) {
                plugin.addError(String.format(
                        "Error at line %d: Parameter %d ('%s') of element '%s' must be a numeric value, expression or declared numeric variable (Velocity/Distance).",
                        lineNumber, i + 1, param.getText(), elementName));
            }
        }

        plugin.addDeclaredElement(elementName);

        return null;
    }

    private boolean isVectorOrDeclaredPosition(ParameterContext param) {
        if (param.vector() != null) {
            return true;
        }

        String paramName = param.getText();
        return plugin.getDeclaredVariables().get("Position").containsKey(paramName);
    }

    private boolean isNumericOrDeclaredNumeric(ParameterContext param) {
        if (param.expression() != null) {
            return isNumericExpression(param.expression());
        } else if (param.ID() != null) {
            String varName = param.ID().getText();
            return plugin.getDeclaredVariables().get("Velocity").containsKey(varName) ||
                    plugin.getDeclaredVariables().get("Distance").containsKey(varName);
        }
        return false;
    }

    private boolean isNumericExpression(figure_dsl.generated.dslParser.ExpressionContext expr) {
        if (expr.NUMBER() != null) return true;
        if (expr.ID() != null) {
            String varName = expr.ID().getText();
            return plugin.getDeclaredVariables().get("Velocity").containsKey(varName) ||
                    plugin.getDeclaredVariables().get("Distance").containsKey(varName);
        }
        if (expr.expression().size() == 2) {
            return isNumericExpression(expr.expression(0)) && isNumericExpression(expr.expression(1));
        }
        if (expr.expression().size() == 1) {
            return isNumericExpression(expr.expression(0));
        }
        return false;
    }
}