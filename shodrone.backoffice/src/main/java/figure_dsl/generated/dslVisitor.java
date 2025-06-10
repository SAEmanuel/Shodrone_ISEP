package figure_dsl.generated;// Generated from dsl.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link dslParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface dslVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link dslParser#dsl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDsl(dslParser.DslContext ctx);
	/**
	 * Visit a parse tree produced by {@link dslParser#version}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVersion(dslParser.VersionContext ctx);
	/**
	 * Visit a parse tree produced by {@link dslParser#drone_model}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDrone_model(dslParser.Drone_modelContext ctx);
	/**
	 * Visit a parse tree produced by {@link dslParser#variable_declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable_declaration(dslParser.Variable_declarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link dslParser#position_declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPosition_declaration(dslParser.Position_declarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link dslParser#velocity_declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVelocity_declaration(dslParser.Velocity_declarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link dslParser#distance_declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDistance_declaration(dslParser.Distance_declarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link dslParser#vector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVector(dslParser.VectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link dslParser#element_definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElement_definition(dslParser.Element_definitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link dslParser#parameter_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter_list(dslParser.Parameter_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link dslParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(dslParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link dslParser#statement_block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement_block(dslParser.Statement_blockContext ctx);
	/**
	 * Visit a parse tree produced by {@link dslParser#block_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock_type(dslParser.Block_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link dslParser#end_block_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnd_block_type(dslParser.End_block_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link dslParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(dslParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link dslParser#group_statement_block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroup_statement_block(dslParser.Group_statement_blockContext ctx);
	/**
	 * Visit a parse tree produced by {@link dslParser#method}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethod(dslParser.MethodContext ctx);
	/**
	 * Visit a parse tree produced by {@link dslParser#rotate_param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRotate_param(dslParser.Rotate_paramContext ctx);
	/**
	 * Visit a parse tree produced by {@link dslParser#pause_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPause_statement(dslParser.Pause_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link dslParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(dslParser.ExpressionContext ctx);
}