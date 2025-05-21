// Generated from DroneOne.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class DroneOneParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, VARIABLE=7, VALUE=8, CONSTANT=9, 
		FLOAT=10, INT=11, DIGIT=12, EQUALS=13, PLUS=14, MINUS=15, STAR=16, DIVIDE=17, 
		LPAREN=18, RPAREN=19, COMMA=20, SEMICOLON=21, IDENTIFIER=22, WS=23, DESCRIPTION_LINE=24;
	public static final int
		RULE_program = 0, RULE_variable = 1, RULE_type_Var = 2, RULE_position = 3, 
		RULE_vector = 4, RULE_linearVelocity = 5, RULE_angularVelocity = 6, RULE_distance = 7, 
		RULE_time = 8, RULE_tupleEpression = 9, RULE_expression = 10, RULE_timeExpression = 11, 
		RULE_tuple = 12, RULE_array = 13, RULE_instruction = 14, RULE_argumentList = 15;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "variable", "type_Var", "position", "vector", "linearVelocity", 
			"angularVelocity", "distance", "time", "tupleEpression", "expression", 
			"timeExpression", "tuple", "array", "instruction", "argumentList"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'Position'", "'Vector'", "'LinearVelocity'", "'AngularVelocity'", 
			"'Distance'", "'Time'", null, null, null, null, null, null, "'='", "'+'", 
			"'-'", "'*'", "'/'", "'('", "')'", "','", "';'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, "VARIABLE", "VALUE", "CONSTANT", 
			"FLOAT", "INT", "DIGIT", "EQUALS", "PLUS", "MINUS", "STAR", "DIVIDE", 
			"LPAREN", "RPAREN", "COMMA", "SEMICOLON", "IDENTIFIER", "WS", "DESCRIPTION_LINE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "DroneOne.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public DroneOneParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(DroneOneParser.EOF, 0); }
		public List<VariableContext> variable() {
			return getRuleContexts(VariableContext.class);
		}
		public VariableContext variable(int i) {
			return getRuleContext(VariableContext.class,i);
		}
		public List<InstructionContext> instruction() {
			return getRuleContexts(InstructionContext.class);
		}
		public InstructionContext instruction(int i) {
			return getRuleContext(InstructionContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(36);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4194430L) != 0)) {
				{
				setState(34);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__0:
				case T__1:
				case T__2:
				case T__3:
				case T__4:
				case T__5:
					{
					setState(32);
					variable();
					}
					break;
				case IDENTIFIER:
					{
					setState(33);
					instruction();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(38);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(39);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VariableContext extends ParserRuleContext {
		public Type_VarContext type_Var() {
			return getRuleContext(Type_VarContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(DroneOneParser.SEMICOLON, 0); }
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).enterVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).exitVariable(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			type_Var();
			setState(42);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Type_VarContext extends ParserRuleContext {
		public PositionContext position() {
			return getRuleContext(PositionContext.class,0);
		}
		public VectorContext vector() {
			return getRuleContext(VectorContext.class,0);
		}
		public LinearVelocityContext linearVelocity() {
			return getRuleContext(LinearVelocityContext.class,0);
		}
		public AngularVelocityContext angularVelocity() {
			return getRuleContext(AngularVelocityContext.class,0);
		}
		public DistanceContext distance() {
			return getRuleContext(DistanceContext.class,0);
		}
		public TimeContext time() {
			return getRuleContext(TimeContext.class,0);
		}
		public Type_VarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_Var; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).enterType_Var(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).exitType_Var(this);
		}
	}

	public final Type_VarContext type_Var() throws RecognitionException {
		Type_VarContext _localctx = new Type_VarContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_type_Var);
		try {
			setState(50);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(44);
				position();
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 2);
				{
				setState(45);
				vector();
				}
				break;
			case T__2:
				enterOuterAlt(_localctx, 3);
				{
				setState(46);
				linearVelocity();
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 4);
				{
				setState(47);
				angularVelocity();
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 5);
				{
				setState(48);
				distance();
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 6);
				{
				setState(49);
				time();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PositionContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(DroneOneParser.IDENTIFIER, 0); }
		public TerminalNode EQUALS() { return getToken(DroneOneParser.EQUALS, 0); }
		public TupleEpressionContext tupleEpression() {
			return getRuleContext(TupleEpressionContext.class,0);
		}
		public ArrayContext array() {
			return getRuleContext(ArrayContext.class,0);
		}
		public PositionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_position; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).enterPosition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).exitPosition(this);
		}
	}

	public final PositionContext position() throws RecognitionException {
		PositionContext _localctx = new PositionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_position);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			match(T__0);
			setState(53);
			match(IDENTIFIER);
			setState(54);
			match(EQUALS);
			setState(57);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(55);
				tupleEpression();
				}
				break;
			case 2:
				{
				setState(56);
				array();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VectorContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(DroneOneParser.IDENTIFIER, 0); }
		public TerminalNode EQUALS() { return getToken(DroneOneParser.EQUALS, 0); }
		public TupleEpressionContext tupleEpression() {
			return getRuleContext(TupleEpressionContext.class,0);
		}
		public VectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vector; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).enterVector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).exitVector(this);
		}
	}

	public final VectorContext vector() throws RecognitionException {
		VectorContext _localctx = new VectorContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_vector);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59);
			match(T__1);
			setState(60);
			match(IDENTIFIER);
			setState(61);
			match(EQUALS);
			setState(62);
			tupleEpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LinearVelocityContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(DroneOneParser.IDENTIFIER, 0); }
		public TerminalNode EQUALS() { return getToken(DroneOneParser.EQUALS, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public LinearVelocityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_linearVelocity; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).enterLinearVelocity(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).exitLinearVelocity(this);
		}
	}

	public final LinearVelocityContext linearVelocity() throws RecognitionException {
		LinearVelocityContext _localctx = new LinearVelocityContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_linearVelocity);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			match(T__2);
			setState(65);
			match(IDENTIFIER);
			setState(66);
			match(EQUALS);
			setState(67);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AngularVelocityContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(DroneOneParser.IDENTIFIER, 0); }
		public TerminalNode EQUALS() { return getToken(DroneOneParser.EQUALS, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AngularVelocityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_angularVelocity; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).enterAngularVelocity(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).exitAngularVelocity(this);
		}
	}

	public final AngularVelocityContext angularVelocity() throws RecognitionException {
		AngularVelocityContext _localctx = new AngularVelocityContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_angularVelocity);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			match(T__3);
			setState(70);
			match(IDENTIFIER);
			setState(71);
			match(EQUALS);
			setState(72);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DistanceContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(DroneOneParser.IDENTIFIER, 0); }
		public TerminalNode EQUALS() { return getToken(DroneOneParser.EQUALS, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public DistanceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_distance; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).enterDistance(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).exitDistance(this);
		}
	}

	public final DistanceContext distance() throws RecognitionException {
		DistanceContext _localctx = new DistanceContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_distance);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			match(T__4);
			setState(75);
			match(IDENTIFIER);
			setState(76);
			match(EQUALS);
			setState(77);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TimeContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(DroneOneParser.IDENTIFIER, 0); }
		public TerminalNode EQUALS() { return getToken(DroneOneParser.EQUALS, 0); }
		public TimeExpressionContext timeExpression() {
			return getRuleContext(TimeExpressionContext.class,0);
		}
		public TimeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_time; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).enterTime(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).exitTime(this);
		}
	}

	public final TimeContext time() throws RecognitionException {
		TimeContext _localctx = new TimeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_time);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			match(T__5);
			setState(80);
			match(IDENTIFIER);
			setState(81);
			match(EQUALS);
			setState(82);
			timeExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TupleEpressionContext extends ParserRuleContext {
		public TupleContext tuple() {
			return getRuleContext(TupleContext.class,0);
		}
		public TupleEpressionContext tupleEpression() {
			return getRuleContext(TupleEpressionContext.class,0);
		}
		public TerminalNode MINUS() { return getToken(DroneOneParser.MINUS, 0); }
		public TerminalNode PLUS() { return getToken(DroneOneParser.PLUS, 0); }
		public TupleEpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tupleEpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).enterTupleEpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).exitTupleEpression(this);
		}
	}

	public final TupleEpressionContext tupleEpression() throws RecognitionException {
		TupleEpressionContext _localctx = new TupleEpressionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_tupleEpression);
		try {
			setState(96);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(84);
				tuple();
				setState(85);
				tupleEpression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(87);
				tuple();
				setState(88);
				match(MINUS);
				setState(89);
				tupleEpression();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(91);
				tuple();
				setState(92);
				match(PLUS);
				setState(93);
				tupleEpression();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(95);
				tuple();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public TerminalNode VALUE() { return getToken(DroneOneParser.VALUE, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(DroneOneParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(DroneOneParser.MINUS, 0); }
		public TerminalNode STAR() { return getToken(DroneOneParser.STAR, 0); }
		public TerminalNode DIVIDE() { return getToken(DroneOneParser.DIVIDE, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).exitExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 20;
		enterRecursionRule(_localctx, 20, RULE_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(99);
			match(VALUE);
			}
			_ctx.stop = _input.LT(-1);
			setState(115);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(113);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(101);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(102);
						match(PLUS);
						setState(103);
						expression(5);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(104);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(105);
						match(MINUS);
						setState(106);
						expression(4);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(107);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(108);
						match(STAR);
						setState(109);
						expression(3);
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(110);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(111);
						match(DIVIDE);
						setState(112);
						expression(2);
						}
						break;
					}
					} 
				}
				setState(117);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TimeExpressionContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(DroneOneParser.INT, 0); }
		public TerminalNode STAR() { return getToken(DroneOneParser.STAR, 0); }
		public TimeExpressionContext timeExpression() {
			return getRuleContext(TimeExpressionContext.class,0);
		}
		public TerminalNode PLUS() { return getToken(DroneOneParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(DroneOneParser.MINUS, 0); }
		public TerminalNode DIVIDE() { return getToken(DroneOneParser.DIVIDE, 0); }
		public TimeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_timeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).enterTimeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).exitTimeExpression(this);
		}
	}

	public final TimeExpressionContext timeExpression() throws RecognitionException {
		TimeExpressionContext _localctx = new TimeExpressionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_timeExpression);
		try {
			setState(131);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(118);
				match(INT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(119);
				match(INT);
				setState(120);
				match(STAR);
				setState(121);
				timeExpression();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(122);
				match(INT);
				setState(123);
				match(PLUS);
				setState(124);
				timeExpression();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(125);
				match(INT);
				setState(126);
				match(MINUS);
				setState(127);
				timeExpression();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(128);
				match(INT);
				setState(129);
				match(DIVIDE);
				setState(130);
				timeExpression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TupleContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(DroneOneParser.LPAREN, 0); }
		public List<TerminalNode> VALUE() { return getTokens(DroneOneParser.VALUE); }
		public TerminalNode VALUE(int i) {
			return getToken(DroneOneParser.VALUE, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DroneOneParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DroneOneParser.COMMA, i);
		}
		public TerminalNode RPAREN() { return getToken(DroneOneParser.RPAREN, 0); }
		public TupleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tuple; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).enterTuple(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).exitTuple(this);
		}
	}

	public final TupleContext tuple() throws RecognitionException {
		TupleContext _localctx = new TupleContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_tuple);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			match(LPAREN);
			setState(134);
			match(VALUE);
			setState(135);
			match(COMMA);
			setState(136);
			match(VALUE);
			setState(137);
			match(COMMA);
			setState(138);
			match(VALUE);
			setState(139);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArrayContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(DroneOneParser.LPAREN, 0); }
		public List<TupleContext> tuple() {
			return getRuleContexts(TupleContext.class);
		}
		public TupleContext tuple(int i) {
			return getRuleContext(TupleContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(DroneOneParser.RPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(DroneOneParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DroneOneParser.COMMA, i);
		}
		public ArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).enterArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).exitArray(this);
		}
	}

	public final ArrayContext array() throws RecognitionException {
		ArrayContext _localctx = new ArrayContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_array);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			match(LPAREN);
			setState(142);
			tuple();
			setState(147);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(143);
				match(COMMA);
				setState(144);
				tuple();
				}
				}
				setState(149);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(150);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InstructionContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(DroneOneParser.IDENTIFIER, 0); }
		public TerminalNode LPAREN() { return getToken(DroneOneParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(DroneOneParser.RPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(DroneOneParser.SEMICOLON, 0); }
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public InstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instruction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).enterInstruction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).exitInstruction(this);
		}
	}

	public final InstructionContext instruction() throws RecognitionException {
		InstructionContext _localctx = new InstructionContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_instruction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(152);
			match(IDENTIFIER);
			setState(153);
			match(LPAREN);
			setState(155);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==VARIABLE) {
				{
				setState(154);
				argumentList();
				}
			}

			setState(157);
			match(RPAREN);
			setState(158);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentListContext extends ParserRuleContext {
		public TerminalNode VARIABLE() { return getToken(DroneOneParser.VARIABLE, 0); }
		public TerminalNode COMMA() { return getToken(DroneOneParser.COMMA, 0); }
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public ArgumentListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).enterArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneOneListener ) ((DroneOneListener)listener).exitArgumentList(this);
		}
	}

	public final ArgumentListContext argumentList() throws RecognitionException {
		ArgumentListContext _localctx = new ArgumentListContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_argumentList);
		try {
			setState(164);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(160);
				match(VARIABLE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(161);
				match(VARIABLE);
				setState(162);
				match(COMMA);
				setState(163);
				argumentList();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 10:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 4);
		case 1:
			return precpred(_ctx, 3);
		case 2:
			return precpred(_ctx, 2);
		case 3:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0018\u00a7\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007"+
		"\u000f\u0001\u0000\u0001\u0000\u0005\u0000#\b\u0000\n\u0000\f\u0000&\t"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003"+
		"\u00023\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0003\u0003:\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b"+
		"\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003\ta\b\t\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0005\nr\b\n\n\n\f\nu\t\n\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0003\u000b\u0084\b\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0005\r\u0092"+
		"\b\r\n\r\f\r\u0095\t\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0003\u000e\u009c\b\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u00a5\b\u000f\u0001\u000f"+
		"\u0000\u0001\u0014\u0010\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012"+
		"\u0014\u0016\u0018\u001a\u001c\u001e\u0000\u0000\u00ac\u0000$\u0001\u0000"+
		"\u0000\u0000\u0002)\u0001\u0000\u0000\u0000\u00042\u0001\u0000\u0000\u0000"+
		"\u00064\u0001\u0000\u0000\u0000\b;\u0001\u0000\u0000\u0000\n@\u0001\u0000"+
		"\u0000\u0000\fE\u0001\u0000\u0000\u0000\u000eJ\u0001\u0000\u0000\u0000"+
		"\u0010O\u0001\u0000\u0000\u0000\u0012`\u0001\u0000\u0000\u0000\u0014b"+
		"\u0001\u0000\u0000\u0000\u0016\u0083\u0001\u0000\u0000\u0000\u0018\u0085"+
		"\u0001\u0000\u0000\u0000\u001a\u008d\u0001\u0000\u0000\u0000\u001c\u0098"+
		"\u0001\u0000\u0000\u0000\u001e\u00a4\u0001\u0000\u0000\u0000 #\u0003\u0002"+
		"\u0001\u0000!#\u0003\u001c\u000e\u0000\" \u0001\u0000\u0000\u0000\"!\u0001"+
		"\u0000\u0000\u0000#&\u0001\u0000\u0000\u0000$\"\u0001\u0000\u0000\u0000"+
		"$%\u0001\u0000\u0000\u0000%\'\u0001\u0000\u0000\u0000&$\u0001\u0000\u0000"+
		"\u0000\'(\u0005\u0000\u0000\u0001(\u0001\u0001\u0000\u0000\u0000)*\u0003"+
		"\u0004\u0002\u0000*+\u0005\u0015\u0000\u0000+\u0003\u0001\u0000\u0000"+
		"\u0000,3\u0003\u0006\u0003\u0000-3\u0003\b\u0004\u0000.3\u0003\n\u0005"+
		"\u0000/3\u0003\f\u0006\u000003\u0003\u000e\u0007\u000013\u0003\u0010\b"+
		"\u00002,\u0001\u0000\u0000\u00002-\u0001\u0000\u0000\u00002.\u0001\u0000"+
		"\u0000\u00002/\u0001\u0000\u0000\u000020\u0001\u0000\u0000\u000021\u0001"+
		"\u0000\u0000\u00003\u0005\u0001\u0000\u0000\u000045\u0005\u0001\u0000"+
		"\u000056\u0005\u0016\u0000\u000069\u0005\r\u0000\u00007:\u0003\u0012\t"+
		"\u00008:\u0003\u001a\r\u000097\u0001\u0000\u0000\u000098\u0001\u0000\u0000"+
		"\u0000:\u0007\u0001\u0000\u0000\u0000;<\u0005\u0002\u0000\u0000<=\u0005"+
		"\u0016\u0000\u0000=>\u0005\r\u0000\u0000>?\u0003\u0012\t\u0000?\t\u0001"+
		"\u0000\u0000\u0000@A\u0005\u0003\u0000\u0000AB\u0005\u0016\u0000\u0000"+
		"BC\u0005\r\u0000\u0000CD\u0003\u0014\n\u0000D\u000b\u0001\u0000\u0000"+
		"\u0000EF\u0005\u0004\u0000\u0000FG\u0005\u0016\u0000\u0000GH\u0005\r\u0000"+
		"\u0000HI\u0003\u0014\n\u0000I\r\u0001\u0000\u0000\u0000JK\u0005\u0005"+
		"\u0000\u0000KL\u0005\u0016\u0000\u0000LM\u0005\r\u0000\u0000MN\u0003\u0014"+
		"\n\u0000N\u000f\u0001\u0000\u0000\u0000OP\u0005\u0006\u0000\u0000PQ\u0005"+
		"\u0016\u0000\u0000QR\u0005\r\u0000\u0000RS\u0003\u0016\u000b\u0000S\u0011"+
		"\u0001\u0000\u0000\u0000TU\u0003\u0018\f\u0000UV\u0003\u0012\t\u0000V"+
		"a\u0001\u0000\u0000\u0000WX\u0003\u0018\f\u0000XY\u0005\u000f\u0000\u0000"+
		"YZ\u0003\u0012\t\u0000Za\u0001\u0000\u0000\u0000[\\\u0003\u0018\f\u0000"+
		"\\]\u0005\u000e\u0000\u0000]^\u0003\u0012\t\u0000^a\u0001\u0000\u0000"+
		"\u0000_a\u0003\u0018\f\u0000`T\u0001\u0000\u0000\u0000`W\u0001\u0000\u0000"+
		"\u0000`[\u0001\u0000\u0000\u0000`_\u0001\u0000\u0000\u0000a\u0013\u0001"+
		"\u0000\u0000\u0000bc\u0006\n\uffff\uffff\u0000cd\u0005\b\u0000\u0000d"+
		"s\u0001\u0000\u0000\u0000ef\n\u0004\u0000\u0000fg\u0005\u000e\u0000\u0000"+
		"gr\u0003\u0014\n\u0005hi\n\u0003\u0000\u0000ij\u0005\u000f\u0000\u0000"+
		"jr\u0003\u0014\n\u0004kl\n\u0002\u0000\u0000lm\u0005\u0010\u0000\u0000"+
		"mr\u0003\u0014\n\u0003no\n\u0001\u0000\u0000op\u0005\u0011\u0000\u0000"+
		"pr\u0003\u0014\n\u0002qe\u0001\u0000\u0000\u0000qh\u0001\u0000\u0000\u0000"+
		"qk\u0001\u0000\u0000\u0000qn\u0001\u0000\u0000\u0000ru\u0001\u0000\u0000"+
		"\u0000sq\u0001\u0000\u0000\u0000st\u0001\u0000\u0000\u0000t\u0015\u0001"+
		"\u0000\u0000\u0000us\u0001\u0000\u0000\u0000v\u0084\u0005\u000b\u0000"+
		"\u0000wx\u0005\u000b\u0000\u0000xy\u0005\u0010\u0000\u0000y\u0084\u0003"+
		"\u0016\u000b\u0000z{\u0005\u000b\u0000\u0000{|\u0005\u000e\u0000\u0000"+
		"|\u0084\u0003\u0016\u000b\u0000}~\u0005\u000b\u0000\u0000~\u007f\u0005"+
		"\u000f\u0000\u0000\u007f\u0084\u0003\u0016\u000b\u0000\u0080\u0081\u0005"+
		"\u000b\u0000\u0000\u0081\u0082\u0005\u0011\u0000\u0000\u0082\u0084\u0003"+
		"\u0016\u000b\u0000\u0083v\u0001\u0000\u0000\u0000\u0083w\u0001\u0000\u0000"+
		"\u0000\u0083z\u0001\u0000\u0000\u0000\u0083}\u0001\u0000\u0000\u0000\u0083"+
		"\u0080\u0001\u0000\u0000\u0000\u0084\u0017\u0001\u0000\u0000\u0000\u0085"+
		"\u0086\u0005\u0012\u0000\u0000\u0086\u0087\u0005\b\u0000\u0000\u0087\u0088"+
		"\u0005\u0014\u0000\u0000\u0088\u0089\u0005\b\u0000\u0000\u0089\u008a\u0005"+
		"\u0014\u0000\u0000\u008a\u008b\u0005\b\u0000\u0000\u008b\u008c\u0005\u0013"+
		"\u0000\u0000\u008c\u0019\u0001\u0000\u0000\u0000\u008d\u008e\u0005\u0012"+
		"\u0000\u0000\u008e\u0093\u0003\u0018\f\u0000\u008f\u0090\u0005\u0014\u0000"+
		"\u0000\u0090\u0092\u0003\u0018\f\u0000\u0091\u008f\u0001\u0000\u0000\u0000"+
		"\u0092\u0095\u0001\u0000\u0000\u0000\u0093\u0091\u0001\u0000\u0000\u0000"+
		"\u0093\u0094\u0001\u0000\u0000\u0000\u0094\u0096\u0001\u0000\u0000\u0000"+
		"\u0095\u0093\u0001\u0000\u0000\u0000\u0096\u0097\u0005\u0013\u0000\u0000"+
		"\u0097\u001b\u0001\u0000\u0000\u0000\u0098\u0099\u0005\u0016\u0000\u0000"+
		"\u0099\u009b\u0005\u0012\u0000\u0000\u009a\u009c\u0003\u001e\u000f\u0000"+
		"\u009b\u009a\u0001\u0000\u0000\u0000\u009b\u009c\u0001\u0000\u0000\u0000"+
		"\u009c\u009d\u0001\u0000\u0000\u0000\u009d\u009e\u0005\u0013\u0000\u0000"+
		"\u009e\u009f\u0005\u0015\u0000\u0000\u009f\u001d\u0001\u0000\u0000\u0000"+
		"\u00a0\u00a5\u0005\u0007\u0000\u0000\u00a1\u00a2\u0005\u0007\u0000\u0000"+
		"\u00a2\u00a3\u0005\u0014\u0000\u0000\u00a3\u00a5\u0003\u001e\u000f\u0000"+
		"\u00a4\u00a0\u0001\u0000\u0000\u0000\u00a4\u00a1\u0001\u0000\u0000\u0000"+
		"\u00a5\u001f\u0001\u0000\u0000\u0000\u000b\"$29`qs\u0083\u0093\u009b\u00a4";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}