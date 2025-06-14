package drone_language_validation.validator;

import drone_language_validation.DroneGenericPlugin;
import drone_language_validation.RequiredFields;
import drone_language_validation.generated.droneGenericBaseVisitor;
import drone_language_validation.generated.droneGenericParser;

import java.util.List;

public class TypeAndInstructionVisitor extends droneGenericBaseVisitor<Void> {
    private final DroneGenericPlugin plugin;

    public TypeAndInstructionVisitor(DroneGenericPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Void visitSection_types(droneGenericParser.Section_typesContext ctx) {
        List<RequiredFields> expectedTypes = List.of(RequiredFields.TYPES);
        for (int i = 1; i < ctx.ID().size(); i++) { // first ID is 'Types'
            String type = ctx.ID(i).getText();
            plugin.getDeclaredTypes().add(type);
        }

        // Check if all required types are declared
        for (String requiredType : RequiredFields.TYPES.getFieldNames()) {
            if (!plugin.getDeclaredTypes().contains(requiredType)) {
                plugin.addError("Missing required type: " + requiredType);
            }
        }

        return visitChildren(ctx);
    }

    @Override
    public Void visitSection_instructions(droneGenericParser.Section_instructionsContext ctx) {
        ctx.instruction().forEach(instr -> {
            String instrName = instr.ID().getText();
            plugin.getDeclaredInstructions().add(instrName);
        });

        // Check for required instructions
        for (String requiredInstr : RequiredFields.INSTRUCTIONS.getFieldNames()) {
            if (!plugin.getDeclaredInstructions().contains(requiredInstr)) {
                plugin.addError("Missing required instruction: " + requiredInstr);
            }
        }

        return visitChildren(ctx);
    }
}
