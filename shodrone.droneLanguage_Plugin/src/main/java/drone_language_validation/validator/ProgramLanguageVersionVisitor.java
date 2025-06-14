package drone_language_validation.validator;


import drone_language_validation.DroneGenericPlugin;
import drone_language_validation.generated.droneGenericBaseVisitor;
import drone_language_validation.generated.droneGenericParser;

public class ProgramLanguageVersionVisitor extends droneGenericBaseVisitor<Void> {
    private final DroneGenericPlugin plugin;

    public ProgramLanguageVersionVisitor(DroneGenericPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Void visitProgram(droneGenericParser.ProgramContext ctx) {

        if (ctx.header() == null) {
            plugin.addError("Missing Header Program Language Version declaration");
        } else {
            String version = ctx.header().NUMBER().getText();
            if (!version.matches("\\d+(\\.\\d+)+")) {
                plugin.addError("Invalid version format. Use X.Y.Z");
            }
            plugin.setDroneLanguageVersion(version);
        }
        return super.visitProgram(ctx);
    }
}