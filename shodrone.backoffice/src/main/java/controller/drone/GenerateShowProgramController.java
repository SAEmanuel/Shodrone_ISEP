package controller.drone;

import Interface.DroneProgramGenerator;
import domain.valueObjects.ShowProposalStatus;
import drone.DroneProgramsGenerator;
import domain.entity.*;
import drone_language_validation.DroneGenericPlugin;
import figure_dsl.validator.FigureValidationPlugin;
import persistence.RepositoryProvider;
import utils.DslMetadata;

import java.util.*;

public class GenerateShowProgramController {

    private final DroneProgramGenerator generator;

    public GenerateShowProgramController() {
        this.generator = new DroneProgramsGenerator();
    }

    public Optional<ShowProposal> generateProgramsForShow(ShowProposal showProposal) {

        if (showProposal.getSequenceFigues().isEmpty() || showProposal.getModelsUsed().isEmpty()) {
            System.out.println("❌ ShowProposal must contain at least one figure and one drone model.");
            return Optional.empty();
        }

        for (Figure fig : showProposal.getSequenceFigues()) {
            if (fig.dslVersions().isEmpty()) {
                System.out.println("❌ Figure '" + fig.name() + "' has no DSL description.");
                return Optional.empty();
            }
        }

        List<Figure> figures = showProposal.getSequenceFigues();
        Map<Integer, List<int[]>> dronePositions = new HashMap<>();
        int droneIdCounter = 0;

        for (Map.Entry<DroneModel, Integer> entry : showProposal.getModelsUsed().entrySet()) {
            DroneModel droneModel = entry.getKey();

            for (Figure figure : figures) {
                Map<String, DslMetadata> dslVersions = figure.dslVersions();

                for (Map.Entry<String, DslMetadata> dslEntry : dslVersions.entrySet()) {
                    String dslVersion = dslEntry.getKey();
                    DslMetadata dslMetadata = dslEntry.getValue();

                    FigureValidationPlugin validator = new FigureValidationPlugin();
                    validator.setDroneModelName(droneModel.identity());
                    List<String> validationErrors = validator.validate(dslMetadata.getDslLines());

                    if (!validationErrors.isEmpty()) {
                        //System.out.println("❌ Validation errors in figure '" + figure.name() + "' with drone '" + droneModel.identity() + "':");
                        validationErrors.forEach(System.out::println);
                        return Optional.empty();
                    }

                    String program = ((DroneProgramsGenerator) generator).generateProgram(
                            figure, droneModel, dslVersion, dslMetadata, validator, droneIdCounter, dronePositions);

                    DroneGenericPlugin languagePlugin = new DroneGenericPlugin();
                    List<String> droneProgramLines = Arrays.asList(program.split("\n"));
                    languagePlugin.validate(droneProgramLines);
                    List<String> validationErrorsDroneLang = languagePlugin.getErrors();

                    if (!validationErrorsDroneLang.isEmpty()) {
                        //System.out.println("❌ Drone program validation errors for figure '" + figure.name() + "' and drone '" + droneModel.identity() + "':");
                        validationErrorsDroneLang.forEach(System.out::println);
                        return Optional.empty();
                    }

                    String key = figure.name().toString() + "_" + droneModel.identity() + "_DSL_" + dslVersion;
                    showProposal.getDroneLanguageSpecifications().put(key, program);

                    droneIdCounter++;
                }
            }
        }

        List<String> script = generateScriptFromPositions(dronePositions);
        showProposal.setScript(script);
        showProposal.setStatus(ShowProposalStatus.STAND_BY);

        return RepositoryProvider.showProposalRepository().saveInStore(showProposal);
    }


    private List<String> generateScriptFromPositions(Map<Integer, List<int[]>> dronePositions) {
        List<String> script = new ArrayList<>();
        script.add("[" + dronePositions.size() + "]");

        for (Map.Entry<Integer, List<int[]>> entry : dronePositions.entrySet()) {
            int droneId = entry.getKey();
            List<int[]> positions = entry.getValue();
            script.add(droneId + " - 3x4x5");


            for (int[] pos : positions) {
                script.add(pos[0] + " " + pos[1] + " " + pos[2]);
            }
        }

        return script;
    }

}
