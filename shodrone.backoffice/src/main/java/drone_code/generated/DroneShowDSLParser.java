package drone_code.generated;// Generated from DroneShowDSL.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class DroneShowDSLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, VERSION=29, ID=30, NUMBER=31, 
		WS=32, COMMENT=33;
	public static final int
		RULE_dslFile = 0, RULE_droneTypeDecl = 1, RULE_variableDecl = 2, RULE_positionDecl = 3, 
		RULE_velocityDecl = 4, RULE_distanceDecl = 5, RULE_shapeDecl = 6, RULE_commandBlock = 7, 
		RULE_groupBlock = 8, RULE_beforeBlock = 9, RULE_afterBlock = 10, RULE_pauseCommand = 11, 
		RULE_simpleCommand = 12, RULE_command = 13, RULE_exprArguments = 14, RULE_exprList = 15, 
		RULE_expr = 16, RULE_atom = 17, RULE_vector = 18;
	private static String[] makeRuleNames() {
		return new String[] {
			"dslFile", "droneTypeDecl", "variableDecl", "positionDecl", "velocityDecl", 
			"distanceDecl", "shapeDecl", "commandBlock", "groupBlock", "beforeBlock", 
			"afterBlock", "pauseCommand", "simpleCommand", "command", "exprArguments", 
			"exprList", "expr", "atom", "vector"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'DSL'", "'version'", "';'", "'DroneType'", "'Position'", "'='", 
			"'Velocity'", "'Distance'", "'Line'", "'Rectangle'", "'Circle'", "'Circumference'", 
			"'('", "')'", "'group'", "'endgroup'", "'before'", "'endbefore'", "'after'", 
			"'endafter'", "'pause'", "'.'", "','", "'-'", "'*'", "'/'", "'+'", "'PI'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, "VERSION", "ID", "NUMBER", "WS", "COMMENT"
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
	public String getGrammarFileName() { return "DroneShowDSL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public DroneShowDSLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DslFileContext extends ParserRuleContext {
		public TerminalNode VERSION() { return getToken(DroneShowDSLParser.VERSION, 0); }
		public DroneTypeDeclContext droneTypeDecl() {
			return getRuleContext(DroneTypeDeclContext.class,0);
		}
		public TerminalNode EOF() { return getToken(DroneShowDSLParser.EOF, 0); }
		public List<VariableDeclContext> variableDecl() {
			return getRuleContexts(VariableDeclContext.class);
		}
		public VariableDeclContext variableDecl(int i) {
			return getRuleContext(VariableDeclContext.class,i);
		}
		public List<ShapeDeclContext> shapeDecl() {
			return getRuleContexts(ShapeDeclContext.class);
		}
		public ShapeDeclContext shapeDecl(int i) {
			return getRuleContext(ShapeDeclContext.class,i);
		}
		public List<CommandBlockContext> commandBlock() {
			return getRuleContexts(CommandBlockContext.class);
		}
		public CommandBlockContext commandBlock(int i) {
			return getRuleContext(CommandBlockContext.class,i);
		}
		public DslFileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dslFile; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).enterDslFile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).exitDslFile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DroneShowDSLVisitor ) return ((DroneShowDSLVisitor<? extends T>)visitor).visitDslFile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DslFileContext dslFile() throws RecognitionException {
		DslFileContext _localctx = new DslFileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_dslFile);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			match(T__0);
			setState(39);
			match(T__1);
			setState(40);
			match(VERSION);
			setState(41);
			match(T__2);
			setState(42);
			droneTypeDecl();
			setState(46);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 416L) != 0)) {
				{
				{
				setState(43);
				variableDecl();
				}
				}
				setState(48);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(50); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(49);
				shapeDecl();
				}
				}
				setState(52); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 7680L) != 0) );
			setState(57);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1076527104L) != 0)) {
				{
				{
				setState(54);
				commandBlock();
				}
				}
				setState(59);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(60);
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
	public static class DroneTypeDeclContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(DroneShowDSLParser.ID, 0); }
		public DroneTypeDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_droneTypeDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).enterDroneTypeDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).exitDroneTypeDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DroneShowDSLVisitor ) return ((DroneShowDSLVisitor<? extends T>)visitor).visitDroneTypeDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DroneTypeDeclContext droneTypeDecl() throws RecognitionException {
		DroneTypeDeclContext _localctx = new DroneTypeDeclContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_droneTypeDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			match(T__3);
			setState(63);
			match(ID);
			setState(64);
			match(T__2);
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
	public static class VariableDeclContext extends ParserRuleContext {
		public PositionDeclContext positionDecl() {
			return getRuleContext(PositionDeclContext.class,0);
		}
		public VelocityDeclContext velocityDecl() {
			return getRuleContext(VelocityDeclContext.class,0);
		}
		public DistanceDeclContext distanceDecl() {
			return getRuleContext(DistanceDeclContext.class,0);
		}
		public VariableDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).enterVariableDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).exitVariableDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DroneShowDSLVisitor ) return ((DroneShowDSLVisitor<? extends T>)visitor).visitVariableDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclContext variableDecl() throws RecognitionException {
		VariableDeclContext _localctx = new VariableDeclContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_variableDecl);
		try {
			setState(69);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
				enterOuterAlt(_localctx, 1);
				{
				setState(66);
				positionDecl();
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 2);
				{
				setState(67);
				velocityDecl();
				}
				break;
			case T__7:
				enterOuterAlt(_localctx, 3);
				{
				setState(68);
				distanceDecl();
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
	public static class PositionDeclContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(DroneShowDSLParser.ID, 0); }
		public VectorContext vector() {
			return getRuleContext(VectorContext.class,0);
		}
		public PositionDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_positionDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).enterPositionDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).exitPositionDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DroneShowDSLVisitor ) return ((DroneShowDSLVisitor<? extends T>)visitor).visitPositionDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PositionDeclContext positionDecl() throws RecognitionException {
		PositionDeclContext _localctx = new PositionDeclContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_positionDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			match(T__4);
			setState(72);
			match(ID);
			setState(73);
			match(T__5);
			setState(74);
			vector();
			setState(75);
			match(T__2);
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
	public static class VelocityDeclContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(DroneShowDSLParser.ID, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public VelocityDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_velocityDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).enterVelocityDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).exitVelocityDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DroneShowDSLVisitor ) return ((DroneShowDSLVisitor<? extends T>)visitor).visitVelocityDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VelocityDeclContext velocityDecl() throws RecognitionException {
		VelocityDeclContext _localctx = new VelocityDeclContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_velocityDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			match(T__6);
			setState(78);
			match(ID);
			setState(79);
			match(T__5);
			setState(80);
			expr(0);
			setState(81);
			match(T__2);
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
	public static class DistanceDeclContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(DroneShowDSLParser.ID, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public DistanceDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_distanceDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).enterDistanceDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).exitDistanceDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DroneShowDSLVisitor ) return ((DroneShowDSLVisitor<? extends T>)visitor).visitDistanceDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DistanceDeclContext distanceDecl() throws RecognitionException {
		DistanceDeclContext _localctx = new DistanceDeclContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_distanceDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			match(T__7);
			setState(84);
			match(ID);
			setState(85);
			match(T__5);
			setState(86);
			expr(0);
			setState(87);
			match(T__2);
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
	public static class ShapeDeclContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(DroneShowDSLParser.ID, 0); }
		public ExprListContext exprList() {
			return getRuleContext(ExprListContext.class,0);
		}
		public ShapeDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shapeDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).enterShapeDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).exitShapeDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DroneShowDSLVisitor ) return ((DroneShowDSLVisitor<? extends T>)visitor).visitShapeDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShapeDeclContext shapeDecl() throws RecognitionException {
		ShapeDeclContext _localctx = new ShapeDeclContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_shapeDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 7680L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(90);
			match(ID);
			setState(91);
			match(T__12);
			setState(92);
			exprList();
			setState(93);
			match(T__13);
			setState(94);
			match(T__2);
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
	public static class CommandBlockContext extends ParserRuleContext {
		public BeforeBlockContext beforeBlock() {
			return getRuleContext(BeforeBlockContext.class,0);
		}
		public GroupBlockContext groupBlock() {
			return getRuleContext(GroupBlockContext.class,0);
		}
		public AfterBlockContext afterBlock() {
			return getRuleContext(AfterBlockContext.class,0);
		}
		public PauseCommandContext pauseCommand() {
			return getRuleContext(PauseCommandContext.class,0);
		}
		public SimpleCommandContext simpleCommand() {
			return getRuleContext(SimpleCommandContext.class,0);
		}
		public CommandBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_commandBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).enterCommandBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).exitCommandBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DroneShowDSLVisitor ) return ((DroneShowDSLVisitor<? extends T>)visitor).visitCommandBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommandBlockContext commandBlock() throws RecognitionException {
		CommandBlockContext _localctx = new CommandBlockContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_commandBlock);
		try {
			setState(101);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__16:
				enterOuterAlt(_localctx, 1);
				{
				setState(96);
				beforeBlock();
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 2);
				{
				setState(97);
				groupBlock();
				}
				break;
			case T__18:
				enterOuterAlt(_localctx, 3);
				{
				setState(98);
				afterBlock();
				}
				break;
			case T__20:
				enterOuterAlt(_localctx, 4);
				{
				setState(99);
				pauseCommand();
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 5);
				{
				setState(100);
				simpleCommand();
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
	public static class GroupBlockContext extends ParserRuleContext {
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public GroupBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).enterGroupBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).exitGroupBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DroneShowDSLVisitor ) return ((DroneShowDSLVisitor<? extends T>)visitor).visitGroupBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupBlockContext groupBlock() throws RecognitionException {
		GroupBlockContext _localctx = new GroupBlockContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_groupBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			match(T__14);
			setState(107);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ID) {
				{
				{
				setState(104);
				command();
				}
				}
				setState(109);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(110);
			match(T__15);
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
	public static class BeforeBlockContext extends ParserRuleContext {
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public List<GroupBlockContext> groupBlock() {
			return getRuleContexts(GroupBlockContext.class);
		}
		public GroupBlockContext groupBlock(int i) {
			return getRuleContext(GroupBlockContext.class,i);
		}
		public BeforeBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_beforeBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).enterBeforeBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).exitBeforeBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DroneShowDSLVisitor ) return ((DroneShowDSLVisitor<? extends T>)visitor).visitBeforeBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BeforeBlockContext beforeBlock() throws RecognitionException {
		BeforeBlockContext _localctx = new BeforeBlockContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_beforeBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			match(T__16);
			setState(117);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__14 || _la==ID) {
				{
				setState(115);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case ID:
					{
					setState(113);
					command();
					}
					break;
				case T__14:
					{
					setState(114);
					groupBlock();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(119);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(120);
			match(T__17);
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
	public static class AfterBlockContext extends ParserRuleContext {
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public List<GroupBlockContext> groupBlock() {
			return getRuleContexts(GroupBlockContext.class);
		}
		public GroupBlockContext groupBlock(int i) {
			return getRuleContext(GroupBlockContext.class,i);
		}
		public AfterBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_afterBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).enterAfterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).exitAfterBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DroneShowDSLVisitor ) return ((DroneShowDSLVisitor<? extends T>)visitor).visitAfterBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AfterBlockContext afterBlock() throws RecognitionException {
		AfterBlockContext _localctx = new AfterBlockContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_afterBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			match(T__18);
			setState(127);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__14 || _la==ID) {
				{
				setState(125);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case ID:
					{
					setState(123);
					command();
					}
					break;
				case T__14:
					{
					setState(124);
					groupBlock();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(129);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(130);
			match(T__19);
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
	public static class PauseCommandContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public PauseCommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pauseCommand; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).enterPauseCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).exitPauseCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DroneShowDSLVisitor ) return ((DroneShowDSLVisitor<? extends T>)visitor).visitPauseCommand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PauseCommandContext pauseCommand() throws RecognitionException {
		PauseCommandContext _localctx = new PauseCommandContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_pauseCommand);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			match(T__20);
			setState(133);
			match(T__12);
			setState(134);
			expr(0);
			setState(135);
			match(T__13);
			setState(136);
			match(T__2);
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
	public static class SimpleCommandContext extends ParserRuleContext {
		public CommandContext command() {
			return getRuleContext(CommandContext.class,0);
		}
		public SimpleCommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleCommand; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).enterSimpleCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).exitSimpleCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DroneShowDSLVisitor ) return ((DroneShowDSLVisitor<? extends T>)visitor).visitSimpleCommand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleCommandContext simpleCommand() throws RecognitionException {
		SimpleCommandContext _localctx = new SimpleCommandContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_simpleCommand);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
			command();
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
	public static class CommandContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(DroneShowDSLParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(DroneShowDSLParser.ID, i);
		}
		public ExprArgumentsContext exprArguments() {
			return getRuleContext(ExprArgumentsContext.class,0);
		}
		public CommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_command; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).enterCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).exitCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DroneShowDSLVisitor ) return ((DroneShowDSLVisitor<? extends T>)visitor).visitCommand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommandContext command() throws RecognitionException {
		CommandContext _localctx = new CommandContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_command);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			match(ID);
			setState(141);
			match(T__21);
			setState(142);
			match(ID);
			setState(148);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__12) {
				{
				setState(143);
				match(T__12);
				setState(145);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 3506446336L) != 0)) {
					{
					setState(144);
					exprArguments();
					}
				}

				setState(147);
				match(T__13);
				}
			}

			setState(150);
			match(T__2);
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
	public static class ExprArgumentsContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ExprArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprArguments; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).enterExprArguments(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).exitExprArguments(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DroneShowDSLVisitor ) return ((DroneShowDSLVisitor<? extends T>)visitor).visitExprArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprArgumentsContext exprArguments() throws RecognitionException {
		ExprArgumentsContext _localctx = new ExprArgumentsContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_exprArguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(152);
			expr(0);
			setState(157);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__22) {
				{
				{
				setState(153);
				match(T__22);
				setState(154);
				expr(0);
				}
				}
				setState(159);
				_errHandler.sync(this);
				_la = _input.LA(1);
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
	public static class ExprListContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ExprListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).enterExprList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).exitExprList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DroneShowDSLVisitor ) return ((DroneShowDSLVisitor<? extends T>)visitor).visitExprList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprListContext exprList() throws RecognitionException {
		ExprListContext _localctx = new ExprListContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_exprList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			expr(0);
			setState(165);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__22) {
				{
				{
				setState(161);
				match(T__22);
				setState(162);
				expr(0);
				}
				}
				setState(167);
				_errHandler.sync(this);
				_la = _input.LA(1);
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
	public static class ExprContext extends ParserRuleContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DroneShowDSLVisitor ) return ((DroneShowDSLVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 32;
		enterRecursionRule(_localctx, 32, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__12:
			case T__27:
			case ID:
			case NUMBER:
				{
				setState(169);
				atom();
				}
				break;
			case T__23:
				{
				setState(170);
				match(T__23);
				setState(171);
				expr(3);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(182);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(180);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(174);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(175);
						_la = _input.LA(1);
						if ( !(_la==T__24 || _la==T__25) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(176);
						expr(3);
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(177);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(178);
						_la = _input.LA(1);
						if ( !(_la==T__23 || _la==T__26) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(179);
						expr(2);
						}
						break;
					}
					} 
				}
				setState(184);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
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
	public static class AtomContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(DroneShowDSLParser.NUMBER, 0); }
		public TerminalNode ID() { return getToken(DroneShowDSLParser.ID, 0); }
		public VectorContext vector() {
			return getRuleContext(VectorContext.class,0);
		}
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).enterAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).exitAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DroneShowDSLVisitor ) return ((DroneShowDSLVisitor<? extends T>)visitor).visitAtom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_atom);
		try {
			setState(192);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(185);
				match(NUMBER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(186);
				match(T__27);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(187);
				match(T__27);
				setState(188);
				match(T__25);
				setState(189);
				match(NUMBER);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(190);
				match(ID);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(191);
				vector();
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
	public static class VectorContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public VectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vector; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).enterVector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DroneShowDSLListener ) ((DroneShowDSLListener)listener).exitVector(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DroneShowDSLVisitor ) return ((DroneShowDSLVisitor<? extends T>)visitor).visitVector(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VectorContext vector() throws RecognitionException {
		VectorContext _localctx = new VectorContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_vector);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			match(T__12);
			setState(195);
			expr(0);
			setState(196);
			match(T__22);
			setState(197);
			expr(0);
			setState(198);
			match(T__22);
			setState(199);
			expr(0);
			setState(200);
			match(T__13);
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
		case 16:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		case 1:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001!\u00cb\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0005\u0000-\b\u0000\n\u0000\f\u00000\t\u0000\u0001\u0000\u0004\u0000"+
		"3\b\u0000\u000b\u0000\f\u00004\u0001\u0000\u0005\u00008\b\u0000\n\u0000"+
		"\f\u0000;\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002F\b"+
		"\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0003\u0007f\b\u0007\u0001\b\u0001\b\u0005\bj\b\b\n\b\f\bm\t\b"+
		"\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0005\tt\b\t\n\t\f\tw\t\t\u0001"+
		"\t\u0001\t\u0001\n\u0001\n\u0001\n\u0005\n~\b\n\n\n\f\n\u0081\t\n\u0001"+
		"\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0003"+
		"\r\u0092\b\r\u0001\r\u0003\r\u0095\b\r\u0001\r\u0001\r\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0005\u000e\u009c\b\u000e\n\u000e\f\u000e\u009f\t\u000e"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0005\u000f\u00a4\b\u000f\n\u000f"+
		"\f\u000f\u00a7\t\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0003\u0010\u00ad\b\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0005\u0010\u00b5\b\u0010\n\u0010\f\u0010\u00b8"+
		"\t\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0011\u0003\u0011\u00c1\b\u0011\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0000\u0001 \u0013\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012"+
		"\u0014\u0016\u0018\u001a\u001c\u001e \"$\u0000\u0003\u0001\u0000\t\f\u0001"+
		"\u0000\u0019\u001a\u0002\u0000\u0018\u0018\u001b\u001b\u00d0\u0000&\u0001"+
		"\u0000\u0000\u0000\u0002>\u0001\u0000\u0000\u0000\u0004E\u0001\u0000\u0000"+
		"\u0000\u0006G\u0001\u0000\u0000\u0000\bM\u0001\u0000\u0000\u0000\nS\u0001"+
		"\u0000\u0000\u0000\fY\u0001\u0000\u0000\u0000\u000ee\u0001\u0000\u0000"+
		"\u0000\u0010g\u0001\u0000\u0000\u0000\u0012p\u0001\u0000\u0000\u0000\u0014"+
		"z\u0001\u0000\u0000\u0000\u0016\u0084\u0001\u0000\u0000\u0000\u0018\u008a"+
		"\u0001\u0000\u0000\u0000\u001a\u008c\u0001\u0000\u0000\u0000\u001c\u0098"+
		"\u0001\u0000\u0000\u0000\u001e\u00a0\u0001\u0000\u0000\u0000 \u00ac\u0001"+
		"\u0000\u0000\u0000\"\u00c0\u0001\u0000\u0000\u0000$\u00c2\u0001\u0000"+
		"\u0000\u0000&\'\u0005\u0001\u0000\u0000\'(\u0005\u0002\u0000\u0000()\u0005"+
		"\u001d\u0000\u0000)*\u0005\u0003\u0000\u0000*.\u0003\u0002\u0001\u0000"+
		"+-\u0003\u0004\u0002\u0000,+\u0001\u0000\u0000\u0000-0\u0001\u0000\u0000"+
		"\u0000.,\u0001\u0000\u0000\u0000./\u0001\u0000\u0000\u0000/2\u0001\u0000"+
		"\u0000\u00000.\u0001\u0000\u0000\u000013\u0003\f\u0006\u000021\u0001\u0000"+
		"\u0000\u000034\u0001\u0000\u0000\u000042\u0001\u0000\u0000\u000045\u0001"+
		"\u0000\u0000\u000059\u0001\u0000\u0000\u000068\u0003\u000e\u0007\u0000"+
		"76\u0001\u0000\u0000\u00008;\u0001\u0000\u0000\u000097\u0001\u0000\u0000"+
		"\u00009:\u0001\u0000\u0000\u0000:<\u0001\u0000\u0000\u0000;9\u0001\u0000"+
		"\u0000\u0000<=\u0005\u0000\u0000\u0001=\u0001\u0001\u0000\u0000\u0000"+
		">?\u0005\u0004\u0000\u0000?@\u0005\u001e\u0000\u0000@A\u0005\u0003\u0000"+
		"\u0000A\u0003\u0001\u0000\u0000\u0000BF\u0003\u0006\u0003\u0000CF\u0003"+
		"\b\u0004\u0000DF\u0003\n\u0005\u0000EB\u0001\u0000\u0000\u0000EC\u0001"+
		"\u0000\u0000\u0000ED\u0001\u0000\u0000\u0000F\u0005\u0001\u0000\u0000"+
		"\u0000GH\u0005\u0005\u0000\u0000HI\u0005\u001e\u0000\u0000IJ\u0005\u0006"+
		"\u0000\u0000JK\u0003$\u0012\u0000KL\u0005\u0003\u0000\u0000L\u0007\u0001"+
		"\u0000\u0000\u0000MN\u0005\u0007\u0000\u0000NO\u0005\u001e\u0000\u0000"+
		"OP\u0005\u0006\u0000\u0000PQ\u0003 \u0010\u0000QR\u0005\u0003\u0000\u0000"+
		"R\t\u0001\u0000\u0000\u0000ST\u0005\b\u0000\u0000TU\u0005\u001e\u0000"+
		"\u0000UV\u0005\u0006\u0000\u0000VW\u0003 \u0010\u0000WX\u0005\u0003\u0000"+
		"\u0000X\u000b\u0001\u0000\u0000\u0000YZ\u0007\u0000\u0000\u0000Z[\u0005"+
		"\u001e\u0000\u0000[\\\u0005\r\u0000\u0000\\]\u0003\u001e\u000f\u0000]"+
		"^\u0005\u000e\u0000\u0000^_\u0005\u0003\u0000\u0000_\r\u0001\u0000\u0000"+
		"\u0000`f\u0003\u0012\t\u0000af\u0003\u0010\b\u0000bf\u0003\u0014\n\u0000"+
		"cf\u0003\u0016\u000b\u0000df\u0003\u0018\f\u0000e`\u0001\u0000\u0000\u0000"+
		"ea\u0001\u0000\u0000\u0000eb\u0001\u0000\u0000\u0000ec\u0001\u0000\u0000"+
		"\u0000ed\u0001\u0000\u0000\u0000f\u000f\u0001\u0000\u0000\u0000gk\u0005"+
		"\u000f\u0000\u0000hj\u0003\u001a\r\u0000ih\u0001\u0000\u0000\u0000jm\u0001"+
		"\u0000\u0000\u0000ki\u0001\u0000\u0000\u0000kl\u0001\u0000\u0000\u0000"+
		"ln\u0001\u0000\u0000\u0000mk\u0001\u0000\u0000\u0000no\u0005\u0010\u0000"+
		"\u0000o\u0011\u0001\u0000\u0000\u0000pu\u0005\u0011\u0000\u0000qt\u0003"+
		"\u001a\r\u0000rt\u0003\u0010\b\u0000sq\u0001\u0000\u0000\u0000sr\u0001"+
		"\u0000\u0000\u0000tw\u0001\u0000\u0000\u0000us\u0001\u0000\u0000\u0000"+
		"uv\u0001\u0000\u0000\u0000vx\u0001\u0000\u0000\u0000wu\u0001\u0000\u0000"+
		"\u0000xy\u0005\u0012\u0000\u0000y\u0013\u0001\u0000\u0000\u0000z\u007f"+
		"\u0005\u0013\u0000\u0000{~\u0003\u001a\r\u0000|~\u0003\u0010\b\u0000}"+
		"{\u0001\u0000\u0000\u0000}|\u0001\u0000\u0000\u0000~\u0081\u0001\u0000"+
		"\u0000\u0000\u007f}\u0001\u0000\u0000\u0000\u007f\u0080\u0001\u0000\u0000"+
		"\u0000\u0080\u0082\u0001\u0000\u0000\u0000\u0081\u007f\u0001\u0000\u0000"+
		"\u0000\u0082\u0083\u0005\u0014\u0000\u0000\u0083\u0015\u0001\u0000\u0000"+
		"\u0000\u0084\u0085\u0005\u0015\u0000\u0000\u0085\u0086\u0005\r\u0000\u0000"+
		"\u0086\u0087\u0003 \u0010\u0000\u0087\u0088\u0005\u000e\u0000\u0000\u0088"+
		"\u0089\u0005\u0003\u0000\u0000\u0089\u0017\u0001\u0000\u0000\u0000\u008a"+
		"\u008b\u0003\u001a\r\u0000\u008b\u0019\u0001\u0000\u0000\u0000\u008c\u008d"+
		"\u0005\u001e\u0000\u0000\u008d\u008e\u0005\u0016\u0000\u0000\u008e\u0094"+
		"\u0005\u001e\u0000\u0000\u008f\u0091\u0005\r\u0000\u0000\u0090\u0092\u0003"+
		"\u001c\u000e\u0000\u0091\u0090\u0001\u0000\u0000\u0000\u0091\u0092\u0001"+
		"\u0000\u0000\u0000\u0092\u0093\u0001\u0000\u0000\u0000\u0093\u0095\u0005"+
		"\u000e\u0000\u0000\u0094\u008f\u0001\u0000\u0000\u0000\u0094\u0095\u0001"+
		"\u0000\u0000\u0000\u0095\u0096\u0001\u0000\u0000\u0000\u0096\u0097\u0005"+
		"\u0003\u0000\u0000\u0097\u001b\u0001\u0000\u0000\u0000\u0098\u009d\u0003"+
		" \u0010\u0000\u0099\u009a\u0005\u0017\u0000\u0000\u009a\u009c\u0003 \u0010"+
		"\u0000\u009b\u0099\u0001\u0000\u0000\u0000\u009c\u009f\u0001\u0000\u0000"+
		"\u0000\u009d\u009b\u0001\u0000\u0000\u0000\u009d\u009e\u0001\u0000\u0000"+
		"\u0000\u009e\u001d\u0001\u0000\u0000\u0000\u009f\u009d\u0001\u0000\u0000"+
		"\u0000\u00a0\u00a5\u0003 \u0010\u0000\u00a1\u00a2\u0005\u0017\u0000\u0000"+
		"\u00a2\u00a4\u0003 \u0010\u0000\u00a3\u00a1\u0001\u0000\u0000\u0000\u00a4"+
		"\u00a7\u0001\u0000\u0000\u0000\u00a5\u00a3\u0001\u0000\u0000\u0000\u00a5"+
		"\u00a6\u0001\u0000\u0000\u0000\u00a6\u001f\u0001\u0000\u0000\u0000\u00a7"+
		"\u00a5\u0001\u0000\u0000\u0000\u00a8\u00a9\u0006\u0010\uffff\uffff\u0000"+
		"\u00a9\u00ad\u0003\"\u0011\u0000\u00aa\u00ab\u0005\u0018\u0000\u0000\u00ab"+
		"\u00ad\u0003 \u0010\u0003\u00ac\u00a8\u0001\u0000\u0000\u0000\u00ac\u00aa"+
		"\u0001\u0000\u0000\u0000\u00ad\u00b6\u0001\u0000\u0000\u0000\u00ae\u00af"+
		"\n\u0002\u0000\u0000\u00af\u00b0\u0007\u0001\u0000\u0000\u00b0\u00b5\u0003"+
		" \u0010\u0003\u00b1\u00b2\n\u0001\u0000\u0000\u00b2\u00b3\u0007\u0002"+
		"\u0000\u0000\u00b3\u00b5\u0003 \u0010\u0002\u00b4\u00ae\u0001\u0000\u0000"+
		"\u0000\u00b4\u00b1\u0001\u0000\u0000\u0000\u00b5\u00b8\u0001\u0000\u0000"+
		"\u0000\u00b6\u00b4\u0001\u0000\u0000\u0000\u00b6\u00b7\u0001\u0000\u0000"+
		"\u0000\u00b7!\u0001\u0000\u0000\u0000\u00b8\u00b6\u0001\u0000\u0000\u0000"+
		"\u00b9\u00c1\u0005\u001f\u0000\u0000\u00ba\u00c1\u0005\u001c\u0000\u0000"+
		"\u00bb\u00bc\u0005\u001c\u0000\u0000\u00bc\u00bd\u0005\u001a\u0000\u0000"+
		"\u00bd\u00c1\u0005\u001f\u0000\u0000\u00be\u00c1\u0005\u001e\u0000\u0000"+
		"\u00bf\u00c1\u0003$\u0012\u0000\u00c0\u00b9\u0001\u0000\u0000\u0000\u00c0"+
		"\u00ba\u0001\u0000\u0000\u0000\u00c0\u00bb\u0001\u0000\u0000\u0000\u00c0"+
		"\u00be\u0001\u0000\u0000\u0000\u00c0\u00bf\u0001\u0000\u0000\u0000\u00c1"+
		"#\u0001\u0000\u0000\u0000\u00c2\u00c3\u0005\r\u0000\u0000\u00c3\u00c4"+
		"\u0003 \u0010\u0000\u00c4\u00c5\u0005\u0017\u0000\u0000\u00c5\u00c6\u0003"+
		" \u0010\u0000\u00c6\u00c7\u0005\u0017\u0000\u0000\u00c7\u00c8\u0003 \u0010"+
		"\u0000\u00c8\u00c9\u0005\u000e\u0000\u0000\u00c9%\u0001\u0000\u0000\u0000"+
		"\u0012.49Eeksu}\u007f\u0091\u0094\u009d\u00a5\u00ac\u00b4\u00b6\u00c0";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}