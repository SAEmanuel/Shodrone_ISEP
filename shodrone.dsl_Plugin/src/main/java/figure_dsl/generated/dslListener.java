package figure_dsl.generated;// Generated from dsl.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link dslParser}.
 */
public interface dslListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link dslParser#dsl}.
	 * @param ctx the parse tree
	 */
	void enterDsl(dslParser.DslContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#dsl}.
	 * @param ctx the parse tree
	 */
	void exitDsl(dslParser.DslContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#version}.
	 * @param ctx the parse tree
	 */
	void enterVersion(dslParser.VersionContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#version}.
	 * @param ctx the parse tree
	 */
	void exitVersion(dslParser.VersionContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#drone_model}.
	 * @param ctx the parse tree
	 */
	void enterDrone_model(dslParser.Drone_modelContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#drone_model}.
	 * @param ctx the parse tree
	 */
	void exitDrone_model(dslParser.Drone_modelContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#variable_declaration}.
	 * @param ctx the parse tree
	 */
	void enterVariable_declaration(dslParser.Variable_declarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#variable_declaration}.
	 * @param ctx the parse tree
	 */
	void exitVariable_declaration(dslParser.Variable_declarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#position_declaration}.
	 * @param ctx the parse tree
	 */
	void enterPosition_declaration(dslParser.Position_declarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#position_declaration}.
	 * @param ctx the parse tree
	 */
	void exitPosition_declaration(dslParser.Position_declarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#velocity_declaration}.
	 * @param ctx the parse tree
	 */
	void enterVelocity_declaration(dslParser.Velocity_declarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#velocity_declaration}.
	 * @param ctx the parse tree
	 */
	void exitVelocity_declaration(dslParser.Velocity_declarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#distance_declaration}.
	 * @param ctx the parse tree
	 */
	void enterDistance_declaration(dslParser.Distance_declarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#distance_declaration}.
	 * @param ctx the parse tree
	 */
	void exitDistance_declaration(dslParser.Distance_declarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#vector}.
	 * @param ctx the parse tree
	 */
	void enterVector(dslParser.VectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#vector}.
	 * @param ctx the parse tree
	 */
	void exitVector(dslParser.VectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#element_definition}.
	 * @param ctx the parse tree
	 */
	void enterElement_definition(dslParser.Element_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#element_definition}.
	 * @param ctx the parse tree
	 */
	void exitElement_definition(dslParser.Element_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#parameter_list}.
	 * @param ctx the parse tree
	 */
	void enterParameter_list(dslParser.Parameter_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#parameter_list}.
	 * @param ctx the parse tree
	 */
	void exitParameter_list(dslParser.Parameter_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(dslParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(dslParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#statement_block}.
	 * @param ctx the parse tree
	 */
	void enterStatement_block(dslParser.Statement_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#statement_block}.
	 * @param ctx the parse tree
	 */
	void exitStatement_block(dslParser.Statement_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#block_type}.
	 * @param ctx the parse tree
	 */
	void enterBlock_type(dslParser.Block_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#block_type}.
	 * @param ctx the parse tree
	 */
	void exitBlock_type(dslParser.Block_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#end_block_type}.
	 * @param ctx the parse tree
	 */
	void enterEnd_block_type(dslParser.End_block_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#end_block_type}.
	 * @param ctx the parse tree
	 */
	void exitEnd_block_type(dslParser.End_block_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(dslParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(dslParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#group_statement_block}.
	 * @param ctx the parse tree
	 */
	void enterGroup_statement_block(dslParser.Group_statement_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#group_statement_block}.
	 * @param ctx the parse tree
	 */
	void exitGroup_statement_block(dslParser.Group_statement_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#method}.
	 * @param ctx the parse tree
	 */
	void enterMethod(dslParser.MethodContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#method}.
	 * @param ctx the parse tree
	 */
	void exitMethod(dslParser.MethodContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#rotate_param}.
	 * @param ctx the parse tree
	 */
	void enterRotate_param(dslParser.Rotate_paramContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#rotate_param}.
	 * @param ctx the parse tree
	 */
	void exitRotate_param(dslParser.Rotate_paramContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#pause_statement}.
	 * @param ctx the parse tree
	 */
	void enterPause_statement(dslParser.Pause_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#pause_statement}.
	 * @param ctx the parse tree
	 */
	void exitPause_statement(dslParser.Pause_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link dslParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(dslParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link dslParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(dslParser.ExpressionContext ctx);
}