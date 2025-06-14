package controller.drone;

import Interface.DroneProgramGenerator;
import drone.DroneProgramGeneratorRegistry;
import domain.entity.*;
import persistence.RepositoryProvider;
import utils.DslMetadata;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ShowProgramGeneratorController {

    private final DroneProgramGeneratorRegistry registry;

    public ShowProgramGeneratorController() {
        this.registry = new DroneProgramGeneratorRegistry();
    }

    /**
     * Generates the programs for all drones in a given ShowProposal.
     *
     * @param showProposal The ShowProposal to generate programs for.
     */
    public Optional<ShowProposal> generateProgramsForShow(ShowProposal showProposal) {

        DroneProgramGenerator generator = registry.getGenerator();

        List<Figure> figures = showProposal.getSequenceFigues();

        for (Map.Entry<DroneModel, Integer> entry : showProposal.getModelsUsed().entrySet()) {

            DroneModel droneModel = entry.getKey();

            for (Figure figure : figures) {

                Map<String, DslMetadata> dslVersions = figure.dslVersions();

                for (Map.Entry<String, DslMetadata> dslEntry : dslVersions.entrySet()) {

                    String dslVersion = dslEntry.getKey();
                    DslMetadata dslMetadata = dslEntry.getValue();

                    String program = generator.generateProgram(figure, droneModel, dslVersion, dslMetadata);

                    String key = figure.name().toString() + "_" + droneModel.identity() + "_DSL_" + dslVersion;
                    showProposal.getDroneLanguageSpecifications().put(key, program);

                }
            }
        }
        return RepositoryProvider.showProposalRepository().saveInStore(showProposal);
    }
}
