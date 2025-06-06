package proposal_template.validators;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;

public enum RequiredFields {

    CUSTOMER(Arrays.asList("customer", "customerName", "client", "clientName", "cliente", "nomeCliente")),
    SHOW_DATE(Arrays.asList("showDate", "dataShow", "data", "date")),
    SHOW_LOCATION(Arrays.asList("showLocation", "local", "location")),
    DURATION(Arrays.asList("duration", "showDuration", "duracao", "duracaoShow")),
    FIGURES(List.of("figures", "figuras")),
    DRONES(List.of("drones")),
    VIDEO(List.of("video", "linkVideo", "videoLink")),
    MANAGER(List.of("manager", "crmManager", "crm_manager", "gestor_responsavel", "gestorResponsavel"));


    private final List<String> fieldNames;

    RequiredFields(List<String> fieldNames) {
        this.fieldNames = fieldNames;
    }

    public List<String> getFieldNames() {
        return fieldNames;
    }

    public boolean contains(String fieldName) {
        return fieldNames.contains(fieldName);
    }


    public static Optional<RequiredFields> getByFieldName(String fieldName) {
        return Arrays.stream(values()).filter(rf -> rf.contains(fieldName)).findFirst();
    }
}
