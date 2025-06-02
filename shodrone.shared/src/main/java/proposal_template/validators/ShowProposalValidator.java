package proposal_template.validators;

import domain.entity.DroneModel;
import domain.entity.Figure;
import persistence.DroneModelRepository;
import persistence.FigureRepository;
import proposal_template.generated.showProposal_ptBaseListener;
import proposal_template.generated.showProposal_ptParser;
import domain.valueObjects.NIF;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ShowProposalValidator extends showProposal_ptBaseListener {
    private final Map<String, Integer> droneInfoMap = new HashMap<>();
    private DroneModelRepository modelRepo;
    private FigureRepository figureRepo;
    private String lastFigureName = null;

    @Override
    public void enterHeader(showProposal_ptParser.HeaderContext ctx) {
        String vat = ctx.VAT().getText().replaceAll("[\\[\\]]", "");
        String address = ctx.MORADA().getText();
        String ref = ctx.REF().getText();

        checkVat(vat);
        checkAddress(address);
        checkRef(ref);

    }

    @Override
    public void enterAnexo(showProposal_ptParser.AnexoContext ctx) {
        String date = ctx.DATA_FIELD().getText();
        String hour = ctx.HORA_FIELD().getText();
        String duration = ctx.DURACAO_FIELD().getText();

        checkDate(date);
        checkHour(hour);
        checkDuration(duration);

    }

    @Override
    public void enterDrone_info(showProposal_ptParser.Drone_infoContext ctx) {

        String droneLine = ctx.getText();
        Pattern pattern = Pattern.compile("\\[(.+?)\\]\\s*–\\s*\\[#(\\d+)] unidades\\.");
        Matcher matcher = pattern.matcher(droneLine);

        if (matcher.matches()) {
            String model = matcher.group(1).trim();
            int quantity = Integer.parseInt(matcher.group(2).trim());

            if (droneInfoMap.containsKey(model)) {
                System.err.println("Drone model repeated in list: " + model);
            } else {
                droneInfoMap.put(model, quantity);
            }

            if (quantity <= 0) {
                System.err.println("Invalid drone quantity for model " + model + ": " + quantity);
            }

            List<DroneModel> models = modelRepo.findAll();
            List<String> validModels = new ArrayList<>();
            for (DroneModel m : models) {
                validModels.add(m.droneName().name());
            }

            if (!validModels.contains(model)) {
                System.err.println("Unknown drone model: " + model);
            }

        } else {
            System.err.println("Invalid drone info format: " + droneLine);
        }
    }

    @Override
    public void enterFiguras_info(showProposal_ptParser.Figuras_infoContext ctx) {
        List<Figure> figures = figureRepo.findAll();
        String line = ctx.getText();
        Pattern pattern = Pattern.compile("\\[(.+?)\\]\\s*–\\s*\\[(.+?)\\]");
        Matcher matcher = pattern.matcher(line);

        if (matcher.matches()) {
            String position = matcher.group(1).trim();
            String figureName = matcher.group(2).trim();

            if (position.isEmpty()) {
                System.err.println("Figure position is empty: " + line);
            }

            if (figureName.equalsIgnoreCase(lastFigureName)) {
                System.err.println("Figure repeated in consecutive positions: " + figureName);
            }
            lastFigureName = figureName;

            List<String> validFigureNames = new ArrayList<>();
            for (Figure figure : figures) {
                validFigureNames.add(figure.name.name());
            }

            if (!validFigureNames.contains(figureName)) {
                System.err.println("Unknown figure: " + figureName);
            }

            List<Figure> actives = figureRepo.findAllActive();
            List<String> activeFigures = new ArrayList<>();
            for (Figure a : actives) {
                activeFigures.add(a.name.name());
            }
            if (actives.isEmpty() || !activeFigures.contains(figureName)) {
                System.err.println("Figure is not active: " + figureName);
            }

        } else {
            System.err.println("Invalid figure info format: " + line);
        }
    }

    private void checkVat(String vat) {
        try {
            new NIF(vat);
        } catch (IllegalArgumentException ex) {
            System.err.println("Invalid VAT: " + vat + " (" + ex.getMessage() + ")");
        }
    }

    private void checkAddress(String address) {
        address = address.substring(1, address.length() - 1);
        String[] p = address.split(",");
        for (int i = 0; i < p.length; i++) {
            p[i] = p[i].trim();
        }

        if (p.length != 4) {
            System.err.println("Invalid address: expected format is 'street, postal code, distrito, country (Portugal)': " + address);
            return;
        }

        boolean hasPostalCode = p[1].matches(".*\\d{4}-\\d{3}.*");
        boolean hasDistrito = containsDistrito(p[2]);
        boolean hasPortugal = p[3].toLowerCase().contains("portugal");

        if (!hasPostalCode) {
            System.err.println("Invalid address: postal code missing (NNNN-NNN): " + address);
            return;
        }
        if (!hasDistrito) {
            System.err.println("Invalid address: invalid \"distrito\" " + p[2]);
            return;
        }
        if (!hasPortugal) {
            System.err.println("Invalid address: country must be \"Portugal\": " + address);
        }
    }

    private boolean containsDistrito(String distrito) {
        for (DistritoPortugal d : DistritoPortugal.values()) {
            if (distrito.equalsIgnoreCase(d.name().replace("_", " "))) {
                return true;
            }
        }
        return false;
    }

    private void checkRef(String ref) {
        String pattern = "Referência \\[#(\\d+)] / \\[(\\d{2}/\\d{2}/\\d{4})]";
        Pattern r = java.util.regex.Pattern.compile(pattern);
        Matcher m = r.matcher(ref.replaceAll("\\s+", ""));

        if (!m.matches()) {
            System.err.println("Invalid reference format: " + ref);
            return;
        }

        String number = m.group(1);
        String dataStr = m.group(2);

        try {
            int n = Integer.parseInt(number);
            if (n <= 0) {
                System.err.println("Reference number must be positive: " + number);
            }
        } catch (NumberFormatException e) {
            System.err.println("Reference number is not a valid integer: " + number);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate data = LocalDate.parse(dataStr, formatter);
            if (data.isBefore(LocalDate.now())) {
                System.err.println("Reference date is in the past: " + dataStr);
            }
        } catch (DateTimeParseException e) {
            System.err.println("Invalid reference date: " + dataStr);
        }
    }

    private void checkDate(String dateField) {
        String pattern = ".*\\[(\\d{2}/\\d{2}/\\d{4})]";
        Matcher m = Pattern.compile(pattern).matcher(dateField);
        if (!m.find()) {
            System.err.println("Invalid date field format: " + dateField);
            return;
        }
        String dateStr = m.group(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate date = LocalDate.parse(dateStr, formatter);
            if (date.isBefore(LocalDate.now())) {
                System.err.println("Date is in the past: " + dateStr);
            }
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date: " + dateStr);
        }
    }

    private void checkHour(String hourField) {
        String pattern = ".*\\[(\\d{2}:\\d{2})]";
        Matcher m = Pattern.compile(pattern).matcher(hourField);
        if (!m.find()) {
            System.err.println("Invalid hour field format: " + hourField);
            return;
        }
        String hourStr = m.group(1);

        if (!hourStr.matches("([01]\\d|2[0-3]):[0-5]\\d")) {
            System.err.println("Invalid hour: " + hourStr + " (expected HH:MM, 00:00 to 23:59)");
        }
    }

    private void checkDuration(String durationField) {
        String pattern = ".*\\[(\\d+)] minutos";
        Matcher m = Pattern.compile(pattern).matcher(durationField);
        if (!m.find()) {
            System.err.println("Invalid duration field format: " + durationField);
            return;
        }
        String durationStr = m.group(1);

        try {
            int minutes = Integer.parseInt(durationStr);
            if (minutes <= 0) {
                System.err.println("Duration must be a positive number: " + durationStr);
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid duration number: " + durationStr);
        }
    }


}
