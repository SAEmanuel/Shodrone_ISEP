package drone_language_validation.validator;

import drone_language_validation.DroneGenericPlugin;
import drone_language_validation.RequiredFields;
import drone_language_validation.generated.droneGenericBaseVisitor;
import drone_language_validation.generated.droneGenericParser;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Visitor responsible for validating the declared types section in a DroneGeneric DSL program.
 * Ensures that all declared types are part of the required built-in types specified in {@link RequiredFields}.
 */
public class TypesValidationVisitor extends droneGenericBaseVisitor<Void> {

    /** Reference to the plugin instance for reporting validation errors. */
    private final DroneGenericPlugin plugin;

    /**
     * Constructs the visitor with a reference to the central plugin instance.
     *
     * @param plugin The validation plugin that stores shared state and error list
     */
    public TypesValidationVisitor(DroneGenericPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Visits the `types` section of the DSL and validates that each declared type
     * is among the supported/required types defined in {@link RequiredFields#TYPES}.
     *
     * @param ctx The parse context for the types section
     * @return null (no value is returned from this visitor)
     */
    @Override
    public Void visitSection_types(droneGenericParser.Section_typesContext ctx) {
        for (TerminalNode idNode : ctx.ID()) {
            String typeName = idNode.getText();

            if (!RequiredFields.TYPES.contains(typeName)) {
                plugin.addError(
                        String.format(
                                "Invalid type '%s' in Types section. Valid types are: %s",
                                typeName,
                                RequiredFields.TYPES.getFieldNames()
                        )
                );
            }
        }
        return super.visitSection_types(ctx);
    }
}
