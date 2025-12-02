package drone_code.generated;// Generated from DroneShowDSL.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link DroneShowDSLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface DroneShowDSLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link DroneShowDSLParser#dslFile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDslFile(DroneShowDSLParser.DslFileContext ctx);
	/**
	 * Visit a parse tree produced by {@link DroneShowDSLParser#droneTypeDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDroneTypeDecl(DroneShowDSLParser.DroneTypeDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link DroneShowDSLParser#variableDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDecl(DroneShowDSLParser.VariableDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link DroneShowDSLParser#positionDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPositionDecl(DroneShowDSLParser.PositionDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link DroneShowDSLParser#velocityDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVelocityDecl(DroneShowDSLParser.VelocityDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link DroneShowDSLParser#distanceDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDistanceDecl(DroneShowDSLParser.DistanceDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link DroneShowDSLParser#shapeDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShapeDecl(DroneShowDSLParser.ShapeDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link DroneShowDSLParser#commandBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandBlock(DroneShowDSLParser.CommandBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link DroneShowDSLParser#groupBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupBlock(DroneShowDSLParser.GroupBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link DroneShowDSLParser#beforeBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBeforeBlock(DroneShowDSLParser.BeforeBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link DroneShowDSLParser#afterBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAfterBlock(DroneShowDSLParser.AfterBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link DroneShowDSLParser#pauseCommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPauseCommand(DroneShowDSLParser.PauseCommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link DroneShowDSLParser#simpleCommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleCommand(DroneShowDSLParser.SimpleCommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link DroneShowDSLParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand(DroneShowDSLParser.CommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link DroneShowDSLParser#exprArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprArguments(DroneShowDSLParser.ExprArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link DroneShowDSLParser#exprList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprList(DroneShowDSLParser.ExprListContext ctx);
	/**
	 * Visit a parse tree produced by {@link DroneShowDSLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(DroneShowDSLParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link DroneShowDSLParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(DroneShowDSLParser.AtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link DroneShowDSLParser#vector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVector(DroneShowDSLParser.VectorContext ctx);
}