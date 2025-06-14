// Generated from droneGeneric.g4 by ANTLR 4.13.2
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
	 * Enter a parse tree produced by {@link droneGenericParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(droneGenericParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link droneGenericParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(droneGenericParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link droneGenericParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(droneGenericParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link droneGenericParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(droneGenericParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link droneGenericParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(droneGenericParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link droneGenericParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(droneGenericParser.TypeContext ctx);
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
	 * Enter a parse tree produced by the {@code ExpressionMulDiv}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionMulDiv(droneGenericParser.ExpressionMulDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExpressionMulDiv}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionMulDiv(droneGenericParser.ExpressionMulDivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExpressionFloat}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionFloat(droneGenericParser.ExpressionFloatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExpressionFloat}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionFloat(droneGenericParser.ExpressionFloatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExpressionVector}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionVector(droneGenericParser.ExpressionVectorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExpressionVector}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionVector(droneGenericParser.ExpressionVectorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExpressionTuple}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionTuple(droneGenericParser.ExpressionTupleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExpressionTuple}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionTuple(droneGenericParser.ExpressionTupleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExpressionPiDiv}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionPiDiv(droneGenericParser.ExpressionPiDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExpressionPiDiv}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionPiDiv(droneGenericParser.ExpressionPiDivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExpressionArray}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionArray(droneGenericParser.ExpressionArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExpressionArray}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionArray(droneGenericParser.ExpressionArrayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExpressionVarRef}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionVarRef(droneGenericParser.ExpressionVarRefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExpressionVarRef}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionVarRef(droneGenericParser.ExpressionVarRefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExpressionInt}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionInt(droneGenericParser.ExpressionIntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExpressionInt}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionInt(droneGenericParser.ExpressionIntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExpressionAddSub}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionAddSub(droneGenericParser.ExpressionAddSubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExpressionAddSub}
	 * labeled alternative in {@link droneGenericParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionAddSub(droneGenericParser.ExpressionAddSubContext ctx);
	/**
	 * Enter a parse tree produced by {@link droneGenericParser#tupleExpr}.
	 * @param ctx the parse tree
	 */
	void enterTupleExpr(droneGenericParser.TupleExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link droneGenericParser#tupleExpr}.
	 * @param ctx the parse tree
	 */
	void exitTupleExpr(droneGenericParser.TupleExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link droneGenericParser#arrayOfTuples}.
	 * @param ctx the parse tree
	 */
	void enterArrayOfTuples(droneGenericParser.ArrayOfTuplesContext ctx);
	/**
	 * Exit a parse tree produced by {@link droneGenericParser#arrayOfTuples}.
	 * @param ctx the parse tree
	 */
	void exitArrayOfTuples(droneGenericParser.ArrayOfTuplesContext ctx);
	/**
	 * Enter a parse tree produced by {@link droneGenericParser#vectorExpr}.
	 * @param ctx the parse tree
	 */
	void enterVectorExpr(droneGenericParser.VectorExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link droneGenericParser#vectorExpr}.
	 * @param ctx the parse tree
	 */
	void exitVectorExpr(droneGenericParser.VectorExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link droneGenericParser#floatLiteral}.
	 * @param ctx the parse tree
	 */
	void enterFloatLiteral(droneGenericParser.FloatLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link droneGenericParser#floatLiteral}.
	 * @param ctx the parse tree
	 */
	void exitFloatLiteral(droneGenericParser.FloatLiteralContext ctx);
}