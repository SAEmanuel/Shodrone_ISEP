package drone_language_validation.generated;// Generated from droneGeneric.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link droneGenericParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface droneGenericVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link droneGenericParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(droneGenericParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link droneGenericParser#header}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHeader(droneGenericParser.HeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link droneGenericParser#section_types}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSection_types(droneGenericParser.Section_typesContext ctx);
	/**
	 * Visit a parse tree produced by {@link droneGenericParser#section_variables}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSection_variables(droneGenericParser.Section_variablesContext ctx);
	/**
	 * Visit a parse tree produced by {@link droneGenericParser#variable_declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable_declaration(droneGenericParser.Variable_declarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link droneGenericParser#section_instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSection_instructions(droneGenericParser.Section_instructionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link droneGenericParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstruction(droneGenericParser.InstructionContext ctx);
	/**
	 * Visit a parse tree produced by {@link droneGenericParser#param_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam_list(droneGenericParser.Param_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(droneGenericParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link droneGenericParser#vector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVector(droneGenericParser.VectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link droneGenericParser#array_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_literal(droneGenericParser.Array_literalContext ctx);
}