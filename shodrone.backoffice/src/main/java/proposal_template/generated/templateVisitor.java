package proposal_template.generated;// Generated from template.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link templateParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface templateVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link templateParser#template}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplate(templateParser.TemplateContext ctx);
	/**
	 * Visit a parse tree produced by {@link templateParser#field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitField(templateParser.FieldContext ctx);
}