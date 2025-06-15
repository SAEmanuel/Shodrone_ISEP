package drone_language_validation.validator;

import drone_language_validation.DroneGenericPlugin;
import drone_language_validation.RequiredFields;
import drone_language_validation.generated.droneGenericBaseVisitor;
import drone_language_validation.generated.droneGenericParser;
import org.antlr.v4.runtime.tree.TerminalNode;

public class TypesValidationVisitor extends droneGenericBaseVisitor<Void> {
    private final DroneGenericPlugin plugin;

    public TypesValidationVisitor(DroneGenericPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Void visitSection_types(droneGenericParser.Section_typesContext ctx) {
        // Itera sobre cada ID declarado após a keyword 'Types'
        for (TerminalNode idNode : ctx.ID()) {
            String typeName = idNode.getText();
            // Verifica se o tipo consta em RequiredFields.TYPES
            if (!RequiredFields.TYPES.contains(typeName)) {
                // Mensagem de erro em inglês
                plugin.addError(
                        String.format(
                                "Invalid type '%s' in Types section. Valid types are: %s",
                                typeName,
                                RequiredFields.TYPES.getFieldNames()
                        )
                );
            }
        }
        // Continua visitação para outras validações
        return super.visitSection_types(ctx);
    }
}
