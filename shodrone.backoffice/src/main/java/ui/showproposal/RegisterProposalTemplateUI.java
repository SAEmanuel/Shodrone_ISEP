package ui.showproposal;


import controller.showproposal.RegisterProposalTemplateController;
import domain.entity.ProposalTemplate;
import domain.valueObjects.Description;
import domain.valueObjects.Name;
import utils.TextBoxUtils;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class RegisterProposalTemplateUI implements Runnable {

    private final RegisterProposalTemplateController controller = new RegisterProposalTemplateController();


    @Override
    public void run() {
        Utils.printCenteredTitle("Register Proposal Template");

        Utils.printCenteredSubtitleV2("Name");
        Utils.showNameRules();
        Name name = Utils.rePromptWhileInvalid("Enter the Proposal Template name", Name::new);
        Description description;

        Utils.printCenteredSubtitleV2("Description");
        boolean option = Utils.confirm("Do you want to add a description? (y/n)");

        Optional<ProposalTemplate> result;

        if (!option) {
            Utils.printAlterMessage("Description skipped...");
            description = new Description("Not provided!");
        } else {
            Utils.showDescriptionRules();
            description = Utils.rePromptWhileInvalid("Enter the Proposal Template description", Description::new);
        }

        Utils.printCenteredSubtitleV2("Text insertion");
        List<String> text = TextBoxUtils.getLinesFromTextBox("Insert the template");
        List<String> missingFields = new ArrayList<>();

        result = controller.registerProposalTemplate(name, description, text, missingFields);

        if (result.isEmpty()) {
            Utils.printFailMessage("Failed to register Proposal Template! mandatory fields missing: ");
            Utils.printFailMessage("Mandatory field(s) missing: ");
            for (String s : missingFields)
                Utils.printFailMessage(s);

        } else {
            Utils.printSuccessMessage("Proposal Template registered successfully!");
        }
    }
}
