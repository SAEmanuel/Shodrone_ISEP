//package ui.showproposal;
//
//
//import domain.entity.ProposalTemplate;
//import domain.valueObjects.Description;
//import domain.valueObjects.Name;
//import utils.Utils;
//
//import java.util.Optional;
//
//public class RegisterProposalTemplate implements Runnable {
//
//    private final RegisterProposalTemplateController controller = new RegisterProposalTemplateController();
//
//
//    @Override
//    public void run() {
//        Utils.printCenteredTitle("Register Proposal Template");
//
//        Utils.printCenteredSubtitleV2("Name");
//        Utils.showNameRules();
//        Name name = Utils.rePromptWhileInvalid("Enter the Proposal Template name", Name::new);
//        Description description;
//
//        Utils.printCenteredSubtitleV2("Description");
//        boolean option = Utils.confirm("Do you want to add a description? (y/n)");
//
//        Optional<ProposalTemplate> result;
//
//        if (!option) {
//            Utils.printAlterMessage("Description skipped...");
//        } else {
//            Utils.showDescriptionRules();
//            description = Utils.rePromptWhileInvalid("Enter the Proposal Template description", Description::new);
//        }
//    }
//}
