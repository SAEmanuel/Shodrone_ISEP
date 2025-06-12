package figure_dsl.validator;

import figure_dsl.generated.dslBaseVisitor;
import figure_dsl.generated.dslParser;

public class DslVersionValidator extends dslBaseVisitor<Void> {
    private final FigureValidationPlugin plugin;

    public DslVersionValidator(FigureValidationPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Void visitDsl(dslParser.DslContext ctx) {

        if (ctx.version() == null) {
            plugin.addError("Missing DSL version declaration");
        } else {
            String version = ctx.version().VERSION_NUMBER().getText();
            if (!version.matches("\\d+(\\.\\d+)+")) {
                plugin.addError("Invalid version format. Use X.Y.Z");
            }
            plugin.setDslVersion(version);
        }
        return super.visitDsl(ctx);
    }
}
