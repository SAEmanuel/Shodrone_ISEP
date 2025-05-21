// Generated from DroneOne.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DroneOneParser}.
 */
public interface DroneOneListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link DroneOneParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(DroneOneParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneOneParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(DroneOneParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneOneParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(DroneOneParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneOneParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(DroneOneParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneOneParser#type_Var}.
	 * @param ctx the parse tree
	 */
	void enterType_Var(DroneOneParser.Type_VarContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneOneParser#type_Var}.
	 * @param ctx the parse tree
	 */
	void exitType_Var(DroneOneParser.Type_VarContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneOneParser#position}.
	 * @param ctx the parse tree
	 */
	void enterPosition(DroneOneParser.PositionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneOneParser#position}.
	 * @param ctx the parse tree
	 */
	void exitPosition(DroneOneParser.PositionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneOneParser#vector}.
	 * @param ctx the parse tree
	 */
	void enterVector(DroneOneParser.VectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneOneParser#vector}.
	 * @param ctx the parse tree
	 */
	void exitVector(DroneOneParser.VectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneOneParser#linearVelocity}.
	 * @param ctx the parse tree
	 */
	void enterLinearVelocity(DroneOneParser.LinearVelocityContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneOneParser#linearVelocity}.
	 * @param ctx the parse tree
	 */
	void exitLinearVelocity(DroneOneParser.LinearVelocityContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneOneParser#angularVelocity}.
	 * @param ctx the parse tree
	 */
	void enterAngularVelocity(DroneOneParser.AngularVelocityContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneOneParser#angularVelocity}.
	 * @param ctx the parse tree
	 */
	void exitAngularVelocity(DroneOneParser.AngularVelocityContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneOneParser#distance}.
	 * @param ctx the parse tree
	 */
	void enterDistance(DroneOneParser.DistanceContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneOneParser#distance}.
	 * @param ctx the parse tree
	 */
	void exitDistance(DroneOneParser.DistanceContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneOneParser#time}.
	 * @param ctx the parse tree
	 */
	void enterTime(DroneOneParser.TimeContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneOneParser#time}.
	 * @param ctx the parse tree
	 */
	void exitTime(DroneOneParser.TimeContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneOneParser#tupleEpression}.
	 * @param ctx the parse tree
	 */
	void enterTupleEpression(DroneOneParser.TupleEpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneOneParser#tupleEpression}.
	 * @param ctx the parse tree
	 */
	void exitTupleEpression(DroneOneParser.TupleEpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneOneParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(DroneOneParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneOneParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(DroneOneParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneOneParser#timeExpression}.
	 * @param ctx the parse tree
	 */
	void enterTimeExpression(DroneOneParser.TimeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneOneParser#timeExpression}.
	 * @param ctx the parse tree
	 */
	void exitTimeExpression(DroneOneParser.TimeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneOneParser#tuple}.
	 * @param ctx the parse tree
	 */
	void enterTuple(DroneOneParser.TupleContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneOneParser#tuple}.
	 * @param ctx the parse tree
	 */
	void exitTuple(DroneOneParser.TupleContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneOneParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(DroneOneParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneOneParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(DroneOneParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneOneParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstruction(DroneOneParser.InstructionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneOneParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstruction(DroneOneParser.InstructionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneOneParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(DroneOneParser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneOneParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(DroneOneParser.ArgumentListContext ctx);
}