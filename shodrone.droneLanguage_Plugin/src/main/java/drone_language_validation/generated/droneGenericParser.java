package drone_language_validation.generated;// Generated from droneGeneric.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class droneGenericParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, ID=27, INT=28, FLOAT=29, WS=30, COMMENT=31, EXPONENT=32;
	public static final int
		RULE_program = 0, RULE_statement = 1, RULE_variableDeclaration = 2, RULE_type = 3, 
		RULE_instruction = 4, RULE_expression = 5, RULE_tupleExpr = 6, RULE_arrayOfTuples = 7, 
		RULE_vectorExpr = 8, RULE_floatLiteral = 9;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "statement", "variableDeclaration", "type", "instruction", 
			"expression", "tupleExpr", "arrayOfTuples", "vectorExpr", "floatLiteral"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'='", "';'", "'Position'", "'Point'", "'Vector'", "'LinearVelocity'", 
			"'AngularVelocity'", "'Distance'", "'Time'", "'takeOff'", "'('", "','", 
			"')'", "'land'", "'move'", "'movePath'", "'moveCircle'", "'hoover'", 
			"'lightsOn'", "'lightsOff'", "'blink'", "'*'", "'/'", "'+'", "'-'", "'PI'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, "ID", "INT", "FLOAT", "WS", "COMMENT", "EXPONENT"
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
	public String getGrammarFileName() { return "droneGeneric.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public droneGenericParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(droneGenericParser.EOF, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof droneGenericVisitor ) return ((droneGenericVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(23);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4179960L) != 0)) {
				{
				{
				setState(20);
				statement();
				}
				}
				setState(25);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(26);
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
	public static class StatementContext extends ParserRuleContext {
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public InstructionContext instruction() {
			return getRuleContext(InstructionContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof droneGenericVisitor ) return ((droneGenericVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(30);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__2:
			case T__3:
			case T__4:
			case T__5:
			case T__6:
			case T__7:
			case T__8:
				enterOuterAlt(_localctx, 1);
				{
				setState(28);
				variableDeclaration();
				}
				break;
			case T__9:
			case T__13:
			case T__14:
			case T__15:
			case T__16:
			case T__17:
			case T__18:
			case T__19:
			case T__20:
				enterOuterAlt(_localctx, 2);
				{
				setState(29);
				instruction();
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
	public static class VariableDeclarationContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(droneGenericParser.ID, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).enterVariableDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).exitVariableDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof droneGenericVisitor ) return ((droneGenericVisitor<? extends T>)visitor).visitVariableDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclarationContext variableDeclaration() throws RecognitionException {
		VariableDeclarationContext _localctx = new VariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_variableDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			type();
			setState(33);
			match(ID);
			setState(34);
			match(T__0);
			setState(35);
			expression(0);
			setState(36);
			match(T__1);
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
	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof droneGenericVisitor ) return ((droneGenericVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1016L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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
	public static class InstructionContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public InstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instruction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).enterInstruction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).exitInstruction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof droneGenericVisitor ) return ((droneGenericVisitor<? extends T>)visitor).visitInstruction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstructionContext instruction() throws RecognitionException {
		InstructionContext _localctx = new InstructionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_instruction);
		int _la;
		try {
			setState(115);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(40);
				match(T__9);
				setState(41);
				match(T__10);
				setState(42);
				expression(0);
				setState(43);
				match(T__11);
				setState(44);
				expression(0);
				setState(45);
				match(T__12);
				setState(46);
				match(T__1);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(48);
				match(T__13);
				setState(49);
				match(T__10);
				setState(50);
				expression(0);
				setState(51);
				match(T__12);
				setState(52);
				match(T__1);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(54);
				match(T__14);
				setState(55);
				match(T__10);
				setState(56);
				expression(0);
				setState(57);
				match(T__11);
				setState(58);
				expression(0);
				setState(59);
				match(T__12);
				setState(60);
				match(T__1);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(62);
				match(T__14);
				setState(63);
				match(T__10);
				setState(64);
				expression(0);
				setState(65);
				match(T__11);
				setState(66);
				expression(0);
				setState(67);
				match(T__11);
				setState(68);
				expression(0);
				setState(69);
				match(T__12);
				setState(70);
				match(T__1);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(72);
				match(T__15);
				setState(73);
				match(T__10);
				setState(74);
				expression(0);
				setState(75);
				match(T__11);
				setState(76);
				expression(0);
				setState(77);
				match(T__12);
				setState(78);
				match(T__1);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(80);
				match(T__16);
				setState(81);
				match(T__10);
				setState(82);
				expression(0);
				setState(83);
				match(T__11);
				setState(84);
				expression(0);
				setState(85);
				match(T__11);
				setState(86);
				expression(0);
				setState(87);
				match(T__12);
				setState(88);
				match(T__1);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(90);
				match(T__17);
				setState(91);
				match(T__10);
				setState(92);
				expression(0);
				setState(93);
				match(T__12);
				setState(94);
				match(T__1);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(96);
				match(T__18);
				setState(97);
				match(T__10);
				setState(99);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1006635008L) != 0)) {
					{
					setState(98);
					expression(0);
					}
				}

				setState(101);
				match(T__12);
				setState(102);
				match(T__1);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(103);
				match(T__19);
				setState(105);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__10) {
					{
					setState(104);
					match(T__10);
					}
				}

				setState(107);
				match(T__12);
				setState(108);
				match(T__1);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(109);
				match(T__20);
				setState(110);
				match(T__10);
				setState(111);
				expression(0);
				setState(112);
				match(T__12);
				setState(113);
				match(T__1);
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
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionMulDivContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExpressionMulDivContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).enterExpressionMulDiv(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).exitExpressionMulDiv(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof droneGenericVisitor ) return ((droneGenericVisitor<? extends T>)visitor).visitExpressionMulDiv(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionFloatContext extends ExpressionContext {
		public FloatLiteralContext floatLiteral() {
			return getRuleContext(FloatLiteralContext.class,0);
		}
		public ExpressionFloatContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).enterExpressionFloat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).exitExpressionFloat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof droneGenericVisitor ) return ((droneGenericVisitor<? extends T>)visitor).visitExpressionFloat(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionVectorContext extends ExpressionContext {
		public VectorExprContext vectorExpr() {
			return getRuleContext(VectorExprContext.class,0);
		}
		public ExpressionVectorContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).enterExpressionVector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).exitExpressionVector(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof droneGenericVisitor ) return ((droneGenericVisitor<? extends T>)visitor).visitExpressionVector(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionTupleContext extends ExpressionContext {
		public TupleExprContext tupleExpr() {
			return getRuleContext(TupleExprContext.class,0);
		}
		public ExpressionTupleContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).enterExpressionTuple(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).exitExpressionTuple(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof droneGenericVisitor ) return ((droneGenericVisitor<? extends T>)visitor).visitExpressionTuple(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionPiDivContext extends ExpressionContext {
		public TerminalNode INT() { return getToken(droneGenericParser.INT, 0); }
		public ExpressionPiDivContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).enterExpressionPiDiv(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).exitExpressionPiDiv(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof droneGenericVisitor ) return ((droneGenericVisitor<? extends T>)visitor).visitExpressionPiDiv(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionArrayContext extends ExpressionContext {
		public ArrayOfTuplesContext arrayOfTuples() {
			return getRuleContext(ArrayOfTuplesContext.class,0);
		}
		public ExpressionArrayContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).enterExpressionArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).exitExpressionArray(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof droneGenericVisitor ) return ((droneGenericVisitor<? extends T>)visitor).visitExpressionArray(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionVarRefContext extends ExpressionContext {
		public TerminalNode ID() { return getToken(droneGenericParser.ID, 0); }
		public ExpressionVarRefContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).enterExpressionVarRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).exitExpressionVarRef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof droneGenericVisitor ) return ((droneGenericVisitor<? extends T>)visitor).visitExpressionVarRef(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionIntContext extends ExpressionContext {
		public TerminalNode INT() { return getToken(droneGenericParser.INT, 0); }
		public ExpressionIntContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).enterExpressionInt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).exitExpressionInt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof droneGenericVisitor ) return ((droneGenericVisitor<? extends T>)visitor).visitExpressionInt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionAddSubContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExpressionAddSubContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).enterExpressionAddSub(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).exitExpressionAddSub(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof droneGenericVisitor ) return ((droneGenericVisitor<? extends T>)visitor).visitExpressionAddSub(this);
			else return visitor.visitChildren(this);
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
		int _startState = 10;
		enterRecursionRule(_localctx, 10, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(127);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				_localctx = new ExpressionPiDivContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(118);
				match(T__25);
				setState(119);
				match(T__22);
				setState(120);
				match(INT);
				}
				break;
			case 2:
				{
				_localctx = new ExpressionVectorContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(121);
				vectorExpr();
				}
				break;
			case 3:
				{
				_localctx = new ExpressionTupleContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(122);
				tupleExpr();
				}
				break;
			case 4:
				{
				_localctx = new ExpressionArrayContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(123);
				arrayOfTuples();
				}
				break;
			case 5:
				{
				_localctx = new ExpressionFloatContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(124);
				floatLiteral();
				}
				break;
			case 6:
				{
				_localctx = new ExpressionIntContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(125);
				match(INT);
				}
				break;
			case 7:
				{
				_localctx = new ExpressionVarRefContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(126);
				match(ID);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(137);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(135);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionMulDivContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(129);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(130);
						((ExpressionMulDivContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__21 || _la==T__22) ) {
							((ExpressionMulDivContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(131);
						expression(9);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionAddSubContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(132);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(133);
						((ExpressionAddSubContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__23 || _la==T__24) ) {
							((ExpressionAddSubContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(134);
						expression(8);
						}
						break;
					}
					} 
				}
				setState(139);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
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
	public static class TupleExprContext extends ParserRuleContext {
		public List<FloatLiteralContext> floatLiteral() {
			return getRuleContexts(FloatLiteralContext.class);
		}
		public FloatLiteralContext floatLiteral(int i) {
			return getRuleContext(FloatLiteralContext.class,i);
		}
		public TupleExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tupleExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).enterTupleExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).exitTupleExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof droneGenericVisitor ) return ((droneGenericVisitor<? extends T>)visitor).visitTupleExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TupleExprContext tupleExpr() throws RecognitionException {
		TupleExprContext _localctx = new TupleExprContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_tupleExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			match(T__10);
			setState(141);
			floatLiteral();
			setState(142);
			match(T__11);
			setState(143);
			floatLiteral();
			setState(144);
			match(T__11);
			setState(145);
			floatLiteral();
			setState(146);
			match(T__12);
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
	public static class ArrayOfTuplesContext extends ParserRuleContext {
		public List<TupleExprContext> tupleExpr() {
			return getRuleContexts(TupleExprContext.class);
		}
		public TupleExprContext tupleExpr(int i) {
			return getRuleContext(TupleExprContext.class,i);
		}
		public ArrayOfTuplesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayOfTuples; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).enterArrayOfTuples(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).exitArrayOfTuples(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof droneGenericVisitor ) return ((droneGenericVisitor<? extends T>)visitor).visitArrayOfTuples(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayOfTuplesContext arrayOfTuples() throws RecognitionException {
		ArrayOfTuplesContext _localctx = new ArrayOfTuplesContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_arrayOfTuples);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(148);
			match(T__10);
			setState(149);
			tupleExpr();
			setState(154);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__11) {
				{
				{
				setState(150);
				match(T__11);
				setState(151);
				tupleExpr();
				}
				}
				setState(156);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(157);
			match(T__12);
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
	public static class VectorExprContext extends ParserRuleContext {
		public List<TupleExprContext> tupleExpr() {
			return getRuleContexts(TupleExprContext.class);
		}
		public TupleExprContext tupleExpr(int i) {
			return getRuleContext(TupleExprContext.class,i);
		}
		public VectorExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vectorExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).enterVectorExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).exitVectorExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof droneGenericVisitor ) return ((droneGenericVisitor<? extends T>)visitor).visitVectorExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VectorExprContext vectorExpr() throws RecognitionException {
		VectorExprContext _localctx = new VectorExprContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_vectorExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(159);
			tupleExpr();
			setState(160);
			match(T__24);
			setState(161);
			tupleExpr();
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
	public static class FloatLiteralContext extends ParserRuleContext {
		public TerminalNode FLOAT() { return getToken(droneGenericParser.FLOAT, 0); }
		public TerminalNode EXPONENT() { return getToken(droneGenericParser.EXPONENT, 0); }
		public FloatLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_floatLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).enterFloatLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof droneGenericListener ) ((droneGenericListener)listener).exitFloatLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof droneGenericVisitor ) return ((droneGenericVisitor<? extends T>)visitor).visitFloatLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FloatLiteralContext floatLiteral() throws RecognitionException {
		FloatLiteralContext _localctx = new FloatLiteralContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_floatLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
			match(FLOAT);
			setState(165);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				setState(164);
				match(EXPONENT);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 5:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 9);
		case 1:
			return precpred(_ctx, 8);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001 \u00a8\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0001\u0000\u0005\u0000\u0016\b\u0000\n\u0000"+
		"\f\u0000\u0019\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"+
		"\u0003\u0001\u001f\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004d\b\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004j\b\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0003\u0004t\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0003\u0005\u0080\b\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005\u0088\b\u0005\n\u0005"+
		"\f\u0005\u008b\t\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0005\u0007\u0099\b\u0007\n\u0007\f\u0007\u009c"+
		"\t\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\t\u0001\t\u0003\t\u00a6\b\t\u0001\t\u0000\u0001\n\n\u0000\u0002\u0004"+
		"\u0006\b\n\f\u000e\u0010\u0012\u0000\u0003\u0001\u0000\u0003\t\u0001\u0000"+
		"\u0016\u0017\u0001\u0000\u0018\u0019\u00b4\u0000\u0017\u0001\u0000\u0000"+
		"\u0000\u0002\u001e\u0001\u0000\u0000\u0000\u0004 \u0001\u0000\u0000\u0000"+
		"\u0006&\u0001\u0000\u0000\u0000\bs\u0001\u0000\u0000\u0000\n\u007f\u0001"+
		"\u0000\u0000\u0000\f\u008c\u0001\u0000\u0000\u0000\u000e\u0094\u0001\u0000"+
		"\u0000\u0000\u0010\u009f\u0001\u0000\u0000\u0000\u0012\u00a3\u0001\u0000"+
		"\u0000\u0000\u0014\u0016\u0003\u0002\u0001\u0000\u0015\u0014\u0001\u0000"+
		"\u0000\u0000\u0016\u0019\u0001\u0000\u0000\u0000\u0017\u0015\u0001\u0000"+
		"\u0000\u0000\u0017\u0018\u0001\u0000\u0000\u0000\u0018\u001a\u0001\u0000"+
		"\u0000\u0000\u0019\u0017\u0001\u0000\u0000\u0000\u001a\u001b\u0005\u0000"+
		"\u0000\u0001\u001b\u0001\u0001\u0000\u0000\u0000\u001c\u001f\u0003\u0004"+
		"\u0002\u0000\u001d\u001f\u0003\b\u0004\u0000\u001e\u001c\u0001\u0000\u0000"+
		"\u0000\u001e\u001d\u0001\u0000\u0000\u0000\u001f\u0003\u0001\u0000\u0000"+
		"\u0000 !\u0003\u0006\u0003\u0000!\"\u0005\u001b\u0000\u0000\"#\u0005\u0001"+
		"\u0000\u0000#$\u0003\n\u0005\u0000$%\u0005\u0002\u0000\u0000%\u0005\u0001"+
		"\u0000\u0000\u0000&\'\u0007\u0000\u0000\u0000\'\u0007\u0001\u0000\u0000"+
		"\u0000()\u0005\n\u0000\u0000)*\u0005\u000b\u0000\u0000*+\u0003\n\u0005"+
		"\u0000+,\u0005\f\u0000\u0000,-\u0003\n\u0005\u0000-.\u0005\r\u0000\u0000"+
		"./\u0005\u0002\u0000\u0000/t\u0001\u0000\u0000\u000001\u0005\u000e\u0000"+
		"\u000012\u0005\u000b\u0000\u000023\u0003\n\u0005\u000034\u0005\r\u0000"+
		"\u000045\u0005\u0002\u0000\u00005t\u0001\u0000\u0000\u000067\u0005\u000f"+
		"\u0000\u000078\u0005\u000b\u0000\u000089\u0003\n\u0005\u00009:\u0005\f"+
		"\u0000\u0000:;\u0003\n\u0005\u0000;<\u0005\r\u0000\u0000<=\u0005\u0002"+
		"\u0000\u0000=t\u0001\u0000\u0000\u0000>?\u0005\u000f\u0000\u0000?@\u0005"+
		"\u000b\u0000\u0000@A\u0003\n\u0005\u0000AB\u0005\f\u0000\u0000BC\u0003"+
		"\n\u0005\u0000CD\u0005\f\u0000\u0000DE\u0003\n\u0005\u0000EF\u0005\r\u0000"+
		"\u0000FG\u0005\u0002\u0000\u0000Gt\u0001\u0000\u0000\u0000HI\u0005\u0010"+
		"\u0000\u0000IJ\u0005\u000b\u0000\u0000JK\u0003\n\u0005\u0000KL\u0005\f"+
		"\u0000\u0000LM\u0003\n\u0005\u0000MN\u0005\r\u0000\u0000NO\u0005\u0002"+
		"\u0000\u0000Ot\u0001\u0000\u0000\u0000PQ\u0005\u0011\u0000\u0000QR\u0005"+
		"\u000b\u0000\u0000RS\u0003\n\u0005\u0000ST\u0005\f\u0000\u0000TU\u0003"+
		"\n\u0005\u0000UV\u0005\f\u0000\u0000VW\u0003\n\u0005\u0000WX\u0005\r\u0000"+
		"\u0000XY\u0005\u0002\u0000\u0000Yt\u0001\u0000\u0000\u0000Z[\u0005\u0012"+
		"\u0000\u0000[\\\u0005\u000b\u0000\u0000\\]\u0003\n\u0005\u0000]^\u0005"+
		"\r\u0000\u0000^_\u0005\u0002\u0000\u0000_t\u0001\u0000\u0000\u0000`a\u0005"+
		"\u0013\u0000\u0000ac\u0005\u000b\u0000\u0000bd\u0003\n\u0005\u0000cb\u0001"+
		"\u0000\u0000\u0000cd\u0001\u0000\u0000\u0000de\u0001\u0000\u0000\u0000"+
		"ef\u0005\r\u0000\u0000ft\u0005\u0002\u0000\u0000gi\u0005\u0014\u0000\u0000"+
		"hj\u0005\u000b\u0000\u0000ih\u0001\u0000\u0000\u0000ij\u0001\u0000\u0000"+
		"\u0000jk\u0001\u0000\u0000\u0000kl\u0005\r\u0000\u0000lt\u0005\u0002\u0000"+
		"\u0000mn\u0005\u0015\u0000\u0000no\u0005\u000b\u0000\u0000op\u0003\n\u0005"+
		"\u0000pq\u0005\r\u0000\u0000qr\u0005\u0002\u0000\u0000rt\u0001\u0000\u0000"+
		"\u0000s(\u0001\u0000\u0000\u0000s0\u0001\u0000\u0000\u0000s6\u0001\u0000"+
		"\u0000\u0000s>\u0001\u0000\u0000\u0000sH\u0001\u0000\u0000\u0000sP\u0001"+
		"\u0000\u0000\u0000sZ\u0001\u0000\u0000\u0000s`\u0001\u0000\u0000\u0000"+
		"sg\u0001\u0000\u0000\u0000sm\u0001\u0000\u0000\u0000t\t\u0001\u0000\u0000"+
		"\u0000uv\u0006\u0005\uffff\uffff\u0000vw\u0005\u001a\u0000\u0000wx\u0005"+
		"\u0017\u0000\u0000x\u0080\u0005\u001c\u0000\u0000y\u0080\u0003\u0010\b"+
		"\u0000z\u0080\u0003\f\u0006\u0000{\u0080\u0003\u000e\u0007\u0000|\u0080"+
		"\u0003\u0012\t\u0000}\u0080\u0005\u001c\u0000\u0000~\u0080\u0005\u001b"+
		"\u0000\u0000\u007fu\u0001\u0000\u0000\u0000\u007fy\u0001\u0000\u0000\u0000"+
		"\u007fz\u0001\u0000\u0000\u0000\u007f{\u0001\u0000\u0000\u0000\u007f|"+
		"\u0001\u0000\u0000\u0000\u007f}\u0001\u0000\u0000\u0000\u007f~\u0001\u0000"+
		"\u0000\u0000\u0080\u0089\u0001\u0000\u0000\u0000\u0081\u0082\n\t\u0000"+
		"\u0000\u0082\u0083\u0007\u0001\u0000\u0000\u0083\u0088\u0003\n\u0005\t"+
		"\u0084\u0085\n\b\u0000\u0000\u0085\u0086\u0007\u0002\u0000\u0000\u0086"+
		"\u0088\u0003\n\u0005\b\u0087\u0081\u0001\u0000\u0000\u0000\u0087\u0084"+
		"\u0001\u0000\u0000\u0000\u0088\u008b\u0001\u0000\u0000\u0000\u0089\u0087"+
		"\u0001\u0000\u0000\u0000\u0089\u008a\u0001\u0000\u0000\u0000\u008a\u000b"+
		"\u0001\u0000\u0000\u0000\u008b\u0089\u0001\u0000\u0000\u0000\u008c\u008d"+
		"\u0005\u000b\u0000\u0000\u008d\u008e\u0003\u0012\t\u0000\u008e\u008f\u0005"+
		"\f\u0000\u0000\u008f\u0090\u0003\u0012\t\u0000\u0090\u0091\u0005\f\u0000"+
		"\u0000\u0091\u0092\u0003\u0012\t\u0000\u0092\u0093\u0005\r\u0000\u0000"+
		"\u0093\r\u0001\u0000\u0000\u0000\u0094\u0095\u0005\u000b\u0000\u0000\u0095"+
		"\u009a\u0003\f\u0006\u0000\u0096\u0097\u0005\f\u0000\u0000\u0097\u0099"+
		"\u0003\f\u0006\u0000\u0098\u0096\u0001\u0000\u0000\u0000\u0099\u009c\u0001"+
		"\u0000\u0000\u0000\u009a\u0098\u0001\u0000\u0000\u0000\u009a\u009b\u0001"+
		"\u0000\u0000\u0000\u009b\u009d\u0001\u0000\u0000\u0000\u009c\u009a\u0001"+
		"\u0000\u0000\u0000\u009d\u009e\u0005\r\u0000\u0000\u009e\u000f\u0001\u0000"+
		"\u0000\u0000\u009f\u00a0\u0003\f\u0006\u0000\u00a0\u00a1\u0005\u0019\u0000"+
		"\u0000\u00a1\u00a2\u0003\f\u0006\u0000\u00a2\u0011\u0001\u0000\u0000\u0000"+
		"\u00a3\u00a5\u0005\u001d\u0000\u0000\u00a4\u00a6\u0005 \u0000\u0000\u00a5"+
		"\u00a4\u0001\u0000\u0000\u0000\u00a5\u00a6\u0001\u0000\u0000\u0000\u00a6"+
		"\u0013\u0001\u0000\u0000\u0000\n\u0017\u001ecis\u007f\u0087\u0089\u009a"+
		"\u00a5";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}