package proposal_template.validators;

import domain.entity.DroneModel;
import domain.entity.Figure;
import domain.valueObjects.Content;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import proposal_template.generated.templateLexer;
import proposal_template.generated.templateParser;
import utils.AuthUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static Map<String, String> buildPlaceholderMap(Content context) {
        Map<String, String> map = new HashMap<>();

        String customerName = context.customer().name().name();
        String showDate = context.showDate().toString();
        String location = context.showLocation().toString();
        String duration = String.valueOf(context.showDuration());
        String figures = formatFigures(context.figures());
        String drones = formatDrones(context.droneModels());
        String video = "video"; // TODO: substituir quando estiver pronto
        String manager = AuthUtils.getCurrentUserName();

        for (RequiredFields required : RequiredFields.values()) {
            String value = switch (required) {
                case CUSTOMER -> customerName;
                case SHOW_DATE -> showDate;
                case SHOW_LOCATION -> location;
                case DURATION -> duration;
                case FIGURES -> figures;
                case DRONES -> drones;
                case VIDEO -> video;
                case MANAGER -> manager;
            };

            for (String name : required.getFieldNames()) {
                map.put(name, value);
            }
        }

        return map;
    }


    private static String formatFigures(Map<Integer, Figure> figures) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Figure> entry : figures.entrySet()) {
            sb.append(entry.getKey()).append(". ").append(entry.getValue().name()).append("\n");
        }
        return sb.toString();
    }

    private static String formatDrones(Map<DroneModel, Integer> droneModels) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<DroneModel, Integer> entry : droneModels.entrySet()) {
            sb.append(entry.getValue()).append("x ").append(entry.getKey().droneName().name()).append("\n");
        }
        return sb.toString();
    }

    public static List<String> replacePlaceholders(List<String> template, Map<String, String> values) {
        List<String> result = new ArrayList<>();
        for (String line : template) {
            for (Map.Entry<String, String> entry : values.entrySet()) {
                line = line.replace("${" + entry.getKey() + "}", entry.getValue());
            }
            result.add(line);
        }
        return result;
    }



    public List<String> getTemplateContent() {
        return templateContent;
    }
}
