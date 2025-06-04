package proposal_template.validators;

import proposal_template.generated.templateBaseVisitor;
import proposal_template.generated.templateParser;

import java.util.*;

public class TemplateFieldVisitor extends templateBaseVisitor<Void> {
    private final Set<String> allFields = new HashSet<>();

    @Override
    public Void visitField(templateParser.FieldContext ctx) {
        String field = ctx.FIELD().getText();
        String fieldName = field.substring(2, field.length() - 1);
        allFields.add(fieldName);

        return super.visitField(ctx);
    }


    public Set<String> getFields() {
        return allFields;
    }
}
