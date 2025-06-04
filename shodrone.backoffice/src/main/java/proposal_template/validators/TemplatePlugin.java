package proposal_template.validators;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import proposal_template.generated.templateLexer;
import proposal_template.generated.templateParser;

import java.util.List;
import java.util.stream.Collectors;

public class TemplatePlugin {

    private final List<String> templateContent;

    public TemplatePlugin(List<String> templateContent) {
        this.templateContent = templateContent;
    }

    public List<String> validate() {
        String templateText = String.join("\n", templateContent);
        CharStream charStream = CharStreams.fromString(templateText);

        templateLexer lexer = new templateLexer(charStream);
        templateParser parser = new templateParser(new CommonTokenStream(lexer));
        TemplateFieldVisitor visitor = new TemplateFieldVisitor();
        visitor.visit(parser.template());

        List<String> missing = new java.util.ArrayList<>();
        for (RequiredFields required : RequiredFields.values()) {
            boolean found = visitor.getFields().stream().anyMatch(fieldName -> required.contains(fieldName));
            if (!found) {
                missing.add(required.name());
            }
        }
        return missing;
    }

    public boolean isValid() {
        return validate().isEmpty();
    }

    public List<String> getTemplateContent() {
        return templateContent;
    }
}
