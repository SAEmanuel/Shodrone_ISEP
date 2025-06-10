package drone_code.generated;// Generated from DroneShowDSL.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DroneShowDSLParser}.
 */
public interface DroneShowDSLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link DroneShowDSLParser#dslFile}.
	 * @param ctx the parse tree
	 */
	void enterDslFile(DroneShowDSLParser.DslFileContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneShowDSLParser#dslFile}.
	 * @param ctx the parse tree
	 */
	void exitDslFile(DroneShowDSLParser.DslFileContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneShowDSLParser#droneTypeDecl}.
	 * @param ctx the parse tree
	 */
	void enterDroneTypeDecl(DroneShowDSLParser.DroneTypeDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneShowDSLParser#droneTypeDecl}.
	 * @param ctx the parse tree
	 */
	void exitDroneTypeDecl(DroneShowDSLParser.DroneTypeDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneShowDSLParser#variableDecl}.
	 * @param ctx the parse tree
	 */
	void enterVariableDecl(DroneShowDSLParser.VariableDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneShowDSLParser#variableDecl}.
	 * @param ctx the parse tree
	 */
	void exitVariableDecl(DroneShowDSLParser.VariableDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneShowDSLParser#positionDecl}.
	 * @param ctx the parse tree
	 */
	void enterPositionDecl(DroneShowDSLParser.PositionDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneShowDSLParser#positionDecl}.
	 * @param ctx the parse tree
	 */
	void exitPositionDecl(DroneShowDSLParser.PositionDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneShowDSLParser#velocityDecl}.
	 * @param ctx the parse tree
	 */
	void enterVelocityDecl(DroneShowDSLParser.VelocityDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneShowDSLParser#velocityDecl}.
	 * @param ctx the parse tree
	 */
	void exitVelocityDecl(DroneShowDSLParser.VelocityDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneShowDSLParser#distanceDecl}.
	 * @param ctx the parse tree
	 */
	void enterDistanceDecl(DroneShowDSLParser.DistanceDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneShowDSLParser#distanceDecl}.
	 * @param ctx the parse tree
	 */
	void exitDistanceDecl(DroneShowDSLParser.DistanceDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneShowDSLParser#shapeDecl}.
	 * @param ctx the parse tree
	 */
	void enterShapeDecl(DroneShowDSLParser.ShapeDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneShowDSLParser#shapeDecl}.
	 * @param ctx the parse tree
	 */
	void exitShapeDecl(DroneShowDSLParser.ShapeDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneShowDSLParser#commandBlock}.
	 * @param ctx the parse tree
	 */
	void enterCommandBlock(DroneShowDSLParser.CommandBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneShowDSLParser#commandBlock}.
	 * @param ctx the parse tree
	 */
	void exitCommandBlock(DroneShowDSLParser.CommandBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneShowDSLParser#groupBlock}.
	 * @param ctx the parse tree
	 */
	void enterGroupBlock(DroneShowDSLParser.GroupBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneShowDSLParser#groupBlock}.
	 * @param ctx the parse tree
	 */
	void exitGroupBlock(DroneShowDSLParser.GroupBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneShowDSLParser#beforeBlock}.
	 * @param ctx the parse tree
	 */
	void enterBeforeBlock(DroneShowDSLParser.BeforeBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneShowDSLParser#beforeBlock}.
	 * @param ctx the parse tree
	 */
	void exitBeforeBlock(DroneShowDSLParser.BeforeBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneShowDSLParser#afterBlock}.
	 * @param ctx the parse tree
	 */
	void enterAfterBlock(DroneShowDSLParser.AfterBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneShowDSLParser#afterBlock}.
	 * @param ctx the parse tree
	 */
	void exitAfterBlock(DroneShowDSLParser.AfterBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneShowDSLParser#pauseCommand}.
	 * @param ctx the parse tree
	 */
	void enterPauseCommand(DroneShowDSLParser.PauseCommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneShowDSLParser#pauseCommand}.
	 * @param ctx the parse tree
	 */
	void exitPauseCommand(DroneShowDSLParser.PauseCommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneShowDSLParser#simpleCommand}.
	 * @param ctx the parse tree
	 */
	void enterSimpleCommand(DroneShowDSLParser.SimpleCommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneShowDSLParser#simpleCommand}.
	 * @param ctx the parse tree
	 */
	void exitSimpleCommand(DroneShowDSLParser.SimpleCommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneShowDSLParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommand(DroneShowDSLParser.CommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneShowDSLParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommand(DroneShowDSLParser.CommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneShowDSLParser#exprArguments}.
	 * @param ctx the parse tree
	 */
	void enterExprArguments(DroneShowDSLParser.ExprArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneShowDSLParser#exprArguments}.
	 * @param ctx the parse tree
	 */
	void exitExprArguments(DroneShowDSLParser.ExprArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneShowDSLParser#exprList}.
	 * @param ctx the parse tree
	 */
	void enterExprList(DroneShowDSLParser.ExprListContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneShowDSLParser#exprList}.
	 * @param ctx the parse tree
	 */
	void exitExprList(DroneShowDSLParser.ExprListContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneShowDSLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(DroneShowDSLParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneShowDSLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(DroneShowDSLParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneShowDSLParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(DroneShowDSLParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneShowDSLParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(DroneShowDSLParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link DroneShowDSLParser#vector}.
	 * @param ctx the parse tree
	 */
	void enterVector(DroneShowDSLParser.VectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link DroneShowDSLParser#vector}.
	 * @param ctx the parse tree
	 */
	void exitVector(DroneShowDSLParser.VectorContext ctx);
}