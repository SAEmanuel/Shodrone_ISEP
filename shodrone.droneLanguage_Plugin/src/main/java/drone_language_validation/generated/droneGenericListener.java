package drone_language_validation.generated;// Generated from droneGeneric.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link droneGenericParser}.
 */
public interface droneGenericListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link droneGenericParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(droneGenericParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link droneGenericParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(droneGenericParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link droneGenericParser#header}.
	 * @param ctx the parse tree
	 */
	void enterHeader(droneGenericParser.HeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link droneGenericParser#header}.
	 * @param ctx the parse tree
	 */
	void exitHeader(droneGenericParser.HeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link droneGenericParser#section_types}.
	 * @param ctx the parse tree
	 */
	void enterSection_types(droneGenericParser.Section_typesContext ctx);
	/**
	 * Exit a parse tree produced by {@link droneGenericParser#section_types}.
	 * @param ctx the parse tree
	 */
	void exitSection_types(droneGenericParser.Section_typesContext ctx);
	/**
	 * Enter a parse tree produced by {@link droneGenericParser#section_variables}.
	 * @param ctx the parse tree
	 */
	void enterSection_variables(droneGenericParser.Section_variablesContext ctx);
	/**
	 * Exit a parse tree produced by {@link droneGenericParser#section_variables}.
	 * @param ctx the parse tree
	 */
	void exitSection_variables(droneGenericParser.Section_variablesContext ctx);
	/**
	 * Enter a parse tree produced by {@link droneGenericParser#variable_declaration}.
	 * @param ctx the parse tree
	 */
	void enterVariable_declaration(droneGenericParser.Variable_declarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link droneGenericParser#variable_declaration}.
	 * @param ctx the parse tree
	 */
	void exitVariable_declaration(droneGenericParser.Variable_declarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link droneGenericParser#section_instructions}.
	 * @param ctx the parse tree
	 */
	void enterSection_instructions(droneGenericParser.Section_instructionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link droneGenericParser#section_instructions}.
	 * @param ctx the parse tree
	 */
	void exitSection_instructions(droneGenericParser.Section_instructionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link droneGenericParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstruction(droneGenericParser.InstructionContext ctx);
	/**
	 * Exit a parse tree produced by {@link droneGenericParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstruction(droneGenericParser.InstructionContext ctx);
	/**
	 * Enter a parse tree produced by {@link droneGenericParser#param_list}.
	 * @param ctx the parse tree
	 */
	void enterParam_list(droneGenericParser.Param_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link droneGenericParser#param_list}.
	 * @param ctx the parse tree
	 */
	void exitParam_list(droneGenericParser.Param_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(droneGenericParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(droneGenericParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link droneGenericParser#vector}.
	 * @param ctx the parse tree
	 */
	void enterVector(droneGenericParser.VectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link droneGenericParser#vector}.
	 * @param ctx the parse tree
	 */
	void exitVector(droneGenericParser.VectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link droneGenericParser#array_literal}.
	 * @param ctx the parse tree
	 */
	void enterArray_literal(droneGenericParser.Array_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link droneGenericParser#array_literal}.
	 * @param ctx the parse tree
	 */
	void exitArray_literal(droneGenericParser.Array_literalContext ctx);
}