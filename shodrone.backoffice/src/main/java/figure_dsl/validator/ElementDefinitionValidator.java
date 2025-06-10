package figure_dsl.validator;

import figure_dsl.generated.dslBaseVisitor;
import figure_dsl.generated.dslParser.Element_definitionContext;
import figure_dsl.generated.dslParser.ParameterContext;
import lombok.Getter;

import java.util.*;

public class ElementDefinitionValidator extends dslBaseVisitor<Void> {
    private final FigureValidationPlugin plugin;
    @Getter
    private final List<String> elementNames = new ArrayList<>();

    public ElementDefinitionValidator(FigureValidationPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Void visitElement_definition(Element_definitionContext ctx) {
        String elementName = ctx.ID(0).getText();
        elementNames.add(elementName);

        List<ParameterContext> params = ctx.parameter_list() != null ? ctx.parameter_list().parameter() : Collections.emptyList();
        int lineNumber = ctx.getStart().getLine();

        if (params.size() != 3) {
            plugin.addError(String.format(
                    "Error at line %d: Element '%s' must have exactly 3 parameters (position, position, droneType).",
                    lineNumber, elementName));
            return null;
        }

        for (int i = 0; i < 2; i++) {
            String posName = params.get(i).getText();
            if (!plugin.getDeclaredVariables().get("Position").containsKey(posName)) {
                plugin.addError(String.format(
                        "Error at line %d: Parameter %d ('%s') of element '%s' is not a declared position.",
                        lineNumber, i + 1, posName, elementName));
            }
        }

        String droneTypeName = params.get(2).getText();

        if (!plugin.getDeclaredVariables().containsKey("DroneType") ||
                !plugin.getDeclaredVariables().get("DroneType").containsKey(droneTypeName)) {
            plugin.addError(String.format(
                    "Error at line %d: The third parameter ('%s') of element '%s' is not a declared droneType.",
                    lineNumber, droneTypeName, elementName));
        }

        return null;
    }
}
