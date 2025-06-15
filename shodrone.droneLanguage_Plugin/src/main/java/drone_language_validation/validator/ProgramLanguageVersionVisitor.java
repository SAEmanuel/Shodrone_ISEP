package drone_language_validation.validator;

import drone_language_validation.DroneGenericPlugin;
import drone_language_validation.generated.droneGenericBaseVisitor;
import drone_language_validation.generated.droneGenericParser;

/**
 * Visitor responsible for validating the program's header section,
 * specifically the presence and format of the Drone Language version declaration.
 */
public class ProgramLanguageVersionVisitor extends droneGenericBaseVisitor<Void> {

    /** Reference to the plugin instance for storing the version and reporting errors. */
    private final DroneGenericPlugin plugin;

    /**
     * Constructs a new instance of the version validator.
     *
     * @param plugin The validation plugin that manages error reporting and state
     */
    public ProgramLanguageVersionVisitor(DroneGenericPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Visits the root `program` node of the DSL and validates the header section.
     * Ensures the header exists and the version follows a semantic format (e.g., "1.0.0").
     *
     * @param ctx The context of the program root node
     * @return null (no value is returned from this visitor)
     */
    @Override
    public Void visitProgram(droneGenericParser.ProgramContext ctx) {
        if (ctx.header() == null) {
            plugin.addError("Missing Header Program Language Version declaration");
        } else {
            String version = ctx.header().VERSION_NUMBER().getText();
            if (!version.matches("\\d+(\\.\\d+)+")) {
                plugin.addError("Invalid version format. Use X.Y.Z");
            }
            plugin.setDroneLanguageVersion(version);
        }
        return super.visitProgram(ctx);
    }
}
