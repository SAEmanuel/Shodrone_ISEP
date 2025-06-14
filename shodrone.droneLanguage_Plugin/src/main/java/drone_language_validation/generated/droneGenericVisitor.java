package drone_language_validation.generated;// Generated from droneGeneric.g4 by ANTLR 4.13.2
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
	 * Visit a parse tree produced by {@link droneGenericParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(droneGenericParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link droneGenericParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(droneGenericParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link droneGenericParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(droneGenericParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link droneGenericParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstruction(droneGenericParser.InstructionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpressionMulDiv}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionMulDiv(droneGenericParser.ExpressionMulDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpressionFloat}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionFloat(droneGenericParser.ExpressionFloatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpressionVector}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionVector(droneGenericParser.ExpressionVectorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpressionTuple}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionTuple(droneGenericParser.ExpressionTupleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpressionPiDiv}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionPiDiv(droneGenericParser.ExpressionPiDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpressionArray}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionArray(droneGenericParser.ExpressionArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpressionVarRef}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionVarRef(droneGenericParser.ExpressionVarRefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpressionInt}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionInt(droneGenericParser.ExpressionIntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpressionAddSub}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionAddSub(droneGenericParser.ExpressionAddSubContext ctx);
	/**
	 * Visit a parse tree produced by {@link droneGenericParser#tupleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTupleExpr(droneGenericParser.TupleExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link droneGenericParser#arrayOfTuples}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayOfTuples(droneGenericParser.ArrayOfTuplesContext ctx);
	/**
	 * Visit a parse tree produced by {@link droneGenericParser#vectorExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVectorExpr(droneGenericParser.VectorExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link droneGenericParser#floatLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatLiteral(droneGenericParser.FloatLiteralContext ctx);
}