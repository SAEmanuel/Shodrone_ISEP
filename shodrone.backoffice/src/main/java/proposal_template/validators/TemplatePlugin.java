package proposal_template.validators;

import domain.entity.DroneModel;
import domain.entity.Figure;
import domain.valueObjects.Content;
import domain.valueObjects.Location;
import org.antlr.v4.runtime.*;
import proposal_template.generated.templateLexer;
import proposal_template.generated.templateParser;
import utils.AuthUtils;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class TemplatePlugin {
    private static final String DEFAULT_VIDEO_PLACEHOLDER = "Pending video preview";

    private final List<String> templateContent;
    private final List<String> validationErrors = new ArrayList<>();

    public TemplatePlugin(List<String> templateContent) {
        this.templateContent = templateContent;
    }

    public List<String> validate() {
        String templateText = String.join("\n", templateContent);

        try {
            CharStream charStream = CharStreams.fromString(templateText);
            templateLexer lexer = new templateLexer(charStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            templateParser parser = new templateParser(tokens);

            parser.removeErrorListeners();
            parser.addErrorListener(new BaseErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                        int line, int charPositionInLine,
                                        String msg, RecognitionException e) {
                    validationErrors.add(String.format("Line %d:%d - %s", line, charPositionInLine, msg));
                }
            });

            TemplateFieldVisitor visitor = new TemplateFieldVisitor();
            visitor.visit(parser.template());

            checkRequiredFields(visitor.getFields());

        } catch (Exception e) {
            validationErrors.add("Template parsing error: " + e.getMessage());
        }

        return validationErrors;
    }

    private void checkRequiredFields(Set<String> presentFields) {
        for (RequiredFields required : RequiredFields.values()) {
            boolean found = required.getFieldNames().stream().anyMatch(presentFields::contains);
            if (!found) {
                validationErrors.add(String.format("Missing required field group: %s", required.name()));
            }
        }
    }

    public static Map<String, String> buildPlaceholderMap(Content context) {
        Map<String, String> placeholders = new LinkedHashMap<>();

        placeholders.putAll(mapFieldGroup(RequiredFields.CUSTOMER,
                context.customer().name().name()));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        placeholders.putAll(mapFieldGroup(RequiredFields.SHOW_DATE,
                context.showDate().format(dateFormatter)));

        placeholders.putAll(mapFieldGroup(RequiredFields.SHOW_LOCATION,
                formatLocation(context.showLocation())));

        placeholders.putAll(mapFieldGroup(RequiredFields.DURATION,
                formatDuration(context.showDuration())));

        placeholders.putAll(mapFieldGroup(RequiredFields.FIGURES,
                formatFigures(context.figures())));

        placeholders.putAll(mapFieldGroup(RequiredFields.DRONES,
                formatDrones(context.droneModels())));


        placeholders.putAll(mapFieldGroup(RequiredFields.VIDEO,
                context.video() != null ? context.video().toString() : DEFAULT_VIDEO_PLACEHOLDER));

        placeholders.putAll(mapFieldGroup(RequiredFields.MANAGER,
                AuthUtils.getCurrentUserName()));

        return placeholders;
    }

    private static Map<String, String> mapFieldGroup(RequiredFields fieldGroup, String value) {
        return fieldGroup.getFieldNames().stream()
                .collect(Collectors.toMap(
                        name -> name,
                        name -> value != null ? value : ""
                ));
    }

    private static String formatLocation(Location location) {
        return String.format("%s | Coordinates: %.6f, %.6f",
                location.address(),
                location.latitude(),
                location.longitude());
    }

    private static String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        return (hours > 0 ? hours + "h " : "") + minutes + "min";
    }

    private static String formatFigures(Map<Integer, Figure> figures) {
        return figures.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> String.format("  %d. %s", entry.getKey(), entry.getValue().name()))
                .collect(Collectors.joining("\n"));
    }

    private static String formatDrones(Map<DroneModel, Integer> droneModels) {
        return droneModels.entrySet().stream()
                .map(entry -> String.format("  %dx %s", entry.getValue(), entry.getKey().droneName()))
                .collect(Collectors.joining("\n"));
    }

    public static List<String> replacePlaceholders(List<String> template, Map<String, String> values) {
        return template.stream()
                .map(line -> replaceLinePlaceholders(line, values))
                .collect(Collectors.toList());
    }

    private static String replaceLinePlaceholders(String line, Map<String, String> values) {
        for (Map.Entry<String, String> entry : values.entrySet()) {
            line = line.replace("${" + entry.getKey() + "}", entry.getValue());
        }
        return line;
    }
}
