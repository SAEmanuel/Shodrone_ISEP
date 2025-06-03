package proposal_template;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import persistence.RepositoryProvider;
import proposal_template.generated.showProposal_ptLexer;
import proposal_template.generated.showProposal_ptParser;
import proposal_template.validators.ShowProposalValidator;

import java.io.InputStream;

public class ProposalValidatorMain {
    public static void main(String[] args) throws Exception {
        RepositoryProvider.setUseInMemory(false);
        InputStream inputStream = ProposalValidatorMain.class.getResourceAsStream("/proposal_template/proposta_pt.txt");
        CharStream input = CharStreams.fromStream(inputStream);

        showProposal_ptLexer lexer = new showProposal_ptLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        showProposal_ptParser parser = new showProposal_ptParser(tokens);

        ParseTree tree = parser.proposal();

        ShowProposalValidator listener = new ShowProposalValidator();
        ParseTreeWalker.DEFAULT.walk(listener, tree);
    }
}
