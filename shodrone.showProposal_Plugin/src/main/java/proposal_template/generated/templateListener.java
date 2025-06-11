package proposal_template.generated;// Generated from template.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link templateParser}.
 */
public interface templateListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link templateParser#template}.
	 * @param ctx the parse tree
	 */
	void enterTemplate(templateParser.TemplateContext ctx);
	/**
	 * Exit a parse tree produced by {@link templateParser#template}.
	 * @param ctx the parse tree
	 */
	void exitTemplate(templateParser.TemplateContext ctx);
	/**
	 * Enter a parse tree produced by {@link templateParser#field}.
	 * @param ctx the parse tree
	 */
	void enterField(templateParser.FieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link templateParser#field}.
	 * @param ctx the parse tree
	 */
	void exitField(templateParser.FieldContext ctx);
}