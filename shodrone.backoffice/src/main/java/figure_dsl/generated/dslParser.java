package figure_dsl.generated;// Generated from dsl.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class dslParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, VERSION_NUMBER=28, ID=29, NUMBER=30, WS=31;
	public static final int
		RULE_dsl = 0, RULE_version = 1, RULE_drone_model = 2, RULE_variable_declaration = 3, 
		RULE_position_declaration = 4, RULE_velocity_declaration = 5, RULE_distance_declaration = 6, 
		RULE_vector = 7, RULE_element_definition = 8, RULE_parameter_list = 9, 
		RULE_parameter = 10, RULE_statement_block = 11, RULE_block_type = 12, 
		RULE_end_block_type = 13, RULE_statement = 14, RULE_group_statement_block = 15, 
		RULE_method = 16, RULE_rotate_param = 17, RULE_pause_statement = 18, RULE_expression = 19;
	private static String[] makeRuleNames() {
		return new String[] {
			"dsl", "version", "drone_model", "variable_declaration", "position_declaration", 
			"velocity_declaration", "distance_declaration", "vector", "element_definition", 
			"parameter_list", "parameter", "statement_block", "block_type", "end_block_type", 
			"statement", "group_statement_block", "method", "rotate_param", "pause_statement", 
			"expression"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'DSL'", "'version'", "';'", "'DroneType'", "'Position'", "'='", 
			"'Velocity'", "'Distance'", "'('", "','", "')'", "'before'", "'after'", 
			"'group'", "'endbefore'", "'endafter'", "'endgroup'", "'.'", "'move'", 
			"'rotate'", "'lightsOn'", "'lightsOff()'", "'pause'", "'*'", "'/'", "'+'", 
			"'-'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, "VERSION_NUMBER", "ID", "NUMBER", "WS"
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
	public String getGrammarFileName() { return "dsl.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public dslParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DslContext extends ParserRuleContext {
		public VersionContext version() {
			return getRuleContext(VersionContext.class,0);
		}
		public List<Drone_modelContext> drone_model() {
			return getRuleContexts(Drone_modelContext.class);
		}
		public Drone_modelContext drone_model(int i) {
			return getRuleContext(Drone_modelContext.class,i);
		}
		public List<Variable_declarationContext> variable_declaration() {
			return getRuleContexts(Variable_declarationContext.class);
		}
		public Variable_declarationContext variable_declaration(int i) {
			return getRuleContext(Variable_declarationContext.class,i);
		}
		public List<Element_definitionContext> element_definition() {
			return getRuleContexts(Element_definitionContext.class);
		}
		public Element_definitionContext element_definition(int i) {
			return getRuleContext(Element_definitionContext.class,i);
		}
		public List<Statement_blockContext> statement_block() {
			return getRuleContexts(Statement_blockContext.class);
		}
		public Statement_blockContext statement_block(int i) {
			return getRuleContext(Statement_blockContext.class,i);
		}
		public List<Pause_statementContext> pause_statement() {
			return getRuleContexts(Pause_statementContext.class);
		}
		public Pause_statementContext pause_statement(int i) {
			return getRuleContext(Pause_statementContext.class,i);
		}
		public DslContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dsl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterDsl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitDsl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof dslVisitor ) return ((dslVisitor<? extends T>)visitor).visitDsl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DslContext dsl() throws RecognitionException {
		DslContext _localctx = new DslContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_dsl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			version();
			setState(42); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(41);
				drone_model();
				}
				}
				setState(44); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__3 );
			setState(49);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 416L) != 0)) {
				{
				{
				setState(46);
				variable_declaration();
				}
				}
				setState(51);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(55);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ID) {
				{
				{
				setState(52);
				element_definition();
				}
				}
				setState(57);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(61);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 28672L) != 0)) {
				{
				{
				setState(58);
				statement_block();
				}
				}
				setState(63);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(67);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__22) {
				{
				{
				setState(64);
				pause_statement();
				}
				}
				setState(69);
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
	public static class VersionContext extends ParserRuleContext {
		public TerminalNode VERSION_NUMBER() { return getToken(dslParser.VERSION_NUMBER, 0); }
		public VersionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_version; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterVersion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitVersion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof dslVisitor ) return ((dslVisitor<? extends T>)visitor).visitVersion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VersionContext version() throws RecognitionException {
		VersionContext _localctx = new VersionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_version);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			match(T__0);
			setState(71);
			match(T__1);
			setState(72);
			match(VERSION_NUMBER);
			setState(73);
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
	public static class Drone_modelContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(dslParser.ID, 0); }
		public Drone_modelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_drone_model; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterDrone_model(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitDrone_model(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof dslVisitor ) return ((dslVisitor<? extends T>)visitor).visitDrone_model(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Drone_modelContext drone_model() throws RecognitionException {
		Drone_modelContext _localctx = new Drone_modelContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_drone_model);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			match(T__3);
			setState(76);
			match(ID);
			setState(77);
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
	public static class Variable_declarationContext extends ParserRuleContext {
		public Position_declarationContext position_declaration() {
			return getRuleContext(Position_declarationContext.class,0);
		}
		public Velocity_declarationContext velocity_declaration() {
			return getRuleContext(Velocity_declarationContext.class,0);
		}
		public Distance_declarationContext distance_declaration() {
			return getRuleContext(Distance_declarationContext.class,0);
		}
		public Variable_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterVariable_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitVariable_declaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof dslVisitor ) return ((dslVisitor<? extends T>)visitor).visitVariable_declaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Variable_declarationContext variable_declaration() throws RecognitionException {
		Variable_declarationContext _localctx = new Variable_declarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_variable_declaration);
		try {
			setState(82);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
				enterOuterAlt(_localctx, 1);
				{
				setState(79);
				position_declaration();
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 2);
				{
				setState(80);
				velocity_declaration();
				}
				break;
			case T__7:
				enterOuterAlt(_localctx, 3);
				{
				setState(81);
				distance_declaration();
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
	public static class Position_declarationContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(dslParser.ID, 0); }
		public VectorContext vector() {
			return getRuleContext(VectorContext.class,0);
		}
		public Position_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_position_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterPosition_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitPosition_declaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof dslVisitor ) return ((dslVisitor<? extends T>)visitor).visitPosition_declaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Position_declarationContext position_declaration() throws RecognitionException {
		Position_declarationContext _localctx = new Position_declarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_position_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84);
			match(T__4);
			setState(85);
			match(ID);
			setState(86);
			match(T__5);
			setState(87);
			vector();
			setState(88);
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
	public static class Velocity_declarationContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(dslParser.ID, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Velocity_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_velocity_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterVelocity_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitVelocity_declaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof dslVisitor ) return ((dslVisitor<? extends T>)visitor).visitVelocity_declaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Velocity_declarationContext velocity_declaration() throws RecognitionException {
		Velocity_declarationContext _localctx = new Velocity_declarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_velocity_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			match(T__6);
			setState(91);
			match(ID);
			setState(92);
			match(T__5);
			setState(93);
			expression(0);
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
	public static class Distance_declarationContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(dslParser.ID, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Distance_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_distance_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterDistance_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitDistance_declaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof dslVisitor ) return ((dslVisitor<? extends T>)visitor).visitDistance_declaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Distance_declarationContext distance_declaration() throws RecognitionException {
		Distance_declarationContext _localctx = new Distance_declarationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_distance_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			match(T__7);
			setState(97);
			match(ID);
			setState(98);
			match(T__5);
			setState(99);
			expression(0);
			setState(100);
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
	public static class VectorContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public VectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vector; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterVector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitVector(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof dslVisitor ) return ((dslVisitor<? extends T>)visitor).visitVector(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VectorContext vector() throws RecognitionException {
		VectorContext _localctx = new VectorContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_vector);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			match(T__8);
			setState(103);
			expression(0);
			setState(104);
			match(T__9);
			setState(105);
			expression(0);
			setState(106);
			match(T__9);
			setState(107);
			expression(0);
			setState(108);
			match(T__10);
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
	public static class Element_definitionContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(dslParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(dslParser.ID, i);
		}
		public Parameter_listContext parameter_list() {
			return getRuleContext(Parameter_listContext.class,0);
		}
		public Element_definitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element_definition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterElement_definition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitElement_definition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof dslVisitor ) return ((dslVisitor<? extends T>)visitor).visitElement_definition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Element_definitionContext element_definition() throws RecognitionException {
		Element_definitionContext _localctx = new Element_definitionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_element_definition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110);
			match(ID);
			setState(111);
			match(ID);
			setState(112);
			match(T__8);
			setState(114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1610613248L) != 0)) {
				{
				setState(113);
				parameter_list();
				}
			}

			setState(116);
			match(T__10);
			setState(117);
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
	public static class Parameter_listContext extends ParserRuleContext {
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public Parameter_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterParameter_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitParameter_list(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof dslVisitor ) return ((dslVisitor<? extends T>)visitor).visitParameter_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Parameter_listContext parameter_list() throws RecognitionException {
		Parameter_listContext _localctx = new Parameter_listContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_parameter_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			parameter();
			setState(124);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(120);
				match(T__9);
				setState(121);
				parameter();
				}
				}
				setState(126);
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
	public static class ParameterContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(dslParser.ID, 0); }
		public VectorContext vector() {
			return getRuleContext(VectorContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof dslVisitor ) return ((dslVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_parameter);
		try {
			setState(130);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(127);
				match(ID);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(128);
				vector();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(129);
				expression(0);
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
	public static class Statement_blockContext extends ParserRuleContext {
		public Block_typeContext block_type() {
			return getRuleContext(Block_typeContext.class,0);
		}
		public End_block_typeContext end_block_type() {
			return getRuleContext(End_block_typeContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public Statement_blockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterStatement_block(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitStatement_block(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof dslVisitor ) return ((dslVisitor<? extends T>)visitor).visitStatement_block(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Statement_blockContext statement_block() throws RecognitionException {
		Statement_blockContext _localctx = new Statement_blockContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_statement_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			block_type();
			setState(134); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(133);
				statement();
				}
				}
				setState(136); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 545275904L) != 0) );
			setState(138);
			end_block_type();
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
	public static class Block_typeContext extends ParserRuleContext {
		public Block_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterBlock_type(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitBlock_type(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof dslVisitor ) return ((dslVisitor<? extends T>)visitor).visitBlock_type(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Block_typeContext block_type() throws RecognitionException {
		Block_typeContext _localctx = new Block_typeContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_block_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 28672L) != 0)) ) {
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
	public static class End_block_typeContext extends ParserRuleContext {
		public End_block_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_end_block_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterEnd_block_type(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitEnd_block_type(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof dslVisitor ) return ((dslVisitor<? extends T>)visitor).visitEnd_block_type(this);
			else return visitor.visitChildren(this);
		}
	}

	public final End_block_typeContext end_block_type() throws RecognitionException {
		End_block_typeContext _localctx = new End_block_typeContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_end_block_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(142);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 229376L) != 0)) ) {
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
	public static class StatementContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(dslParser.ID, 0); }
		public MethodContext method() {
			return getRuleContext(MethodContext.class,0);
		}
		public Group_statement_blockContext group_statement_block() {
			return getRuleContext(Group_statement_blockContext.class,0);
		}
		public Pause_statementContext pause_statement() {
			return getRuleContext(Pause_statementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof dslVisitor ) return ((dslVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_statement);
		try {
			setState(151);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(144);
				match(ID);
				setState(145);
				match(T__17);
				setState(146);
				method();
				setState(147);
				match(T__2);
				}
				break;
			case T__13:
				enterOuterAlt(_localctx, 2);
				{
				setState(149);
				group_statement_block();
				}
				break;
			case T__22:
				enterOuterAlt(_localctx, 3);
				{
				setState(150);
				pause_statement();
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
	public static class Group_statement_blockContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public Group_statement_blockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_group_statement_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterGroup_statement_block(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitGroup_statement_block(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof dslVisitor ) return ((dslVisitor<? extends T>)visitor).visitGroup_statement_block(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Group_statement_blockContext group_statement_block() throws RecognitionException {
		Group_statement_blockContext _localctx = new Group_statement_blockContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_group_statement_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			match(T__13);
			setState(155); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(154);
				statement();
				}
				}
				setState(157); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 545275904L) != 0) );
			setState(159);
			match(T__16);
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
	public static class MethodContext extends ParserRuleContext {
		public VectorContext vector() {
			return getRuleContext(VectorContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ID() { return getToken(dslParser.ID, 0); }
		public List<Rotate_paramContext> rotate_param() {
			return getRuleContexts(Rotate_paramContext.class);
		}
		public Rotate_paramContext rotate_param(int i) {
			return getRuleContext(Rotate_paramContext.class,i);
		}
		public List<TerminalNode> NUMBER() { return getTokens(dslParser.NUMBER); }
		public TerminalNode NUMBER(int i) {
			return getToken(dslParser.NUMBER, i);
		}
		public MethodContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterMethod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitMethod(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof dslVisitor ) return ((dslVisitor<? extends T>)visitor).visitMethod(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodContext method() throws RecognitionException {
		MethodContext _localctx = new MethodContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_method);
		try {
			setState(190);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__18:
				enterOuterAlt(_localctx, 1);
				{
				setState(161);
				match(T__18);
				setState(162);
				match(T__8);
				setState(163);
				vector();
				setState(164);
				match(T__9);
				setState(165);
				expression(0);
				setState(166);
				match(T__9);
				setState(167);
				match(ID);
				setState(168);
				match(T__10);
				}
				break;
			case T__19:
				enterOuterAlt(_localctx, 2);
				{
				setState(170);
				match(T__19);
				setState(171);
				match(T__8);
				setState(172);
				rotate_param();
				setState(173);
				match(T__9);
				setState(174);
				rotate_param();
				setState(175);
				match(T__9);
				setState(176);
				rotate_param();
				setState(177);
				match(T__9);
				setState(178);
				rotate_param();
				setState(179);
				match(T__10);
				}
				break;
			case T__20:
				enterOuterAlt(_localctx, 3);
				{
				setState(181);
				match(T__20);
				setState(182);
				match(T__8);
				setState(183);
				match(NUMBER);
				setState(184);
				match(T__9);
				setState(185);
				match(NUMBER);
				setState(186);
				match(T__9);
				setState(187);
				match(NUMBER);
				setState(188);
				match(T__10);
				}
				break;
			case T__21:
				enterOuterAlt(_localctx, 4);
				{
				setState(189);
				match(T__21);
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
	public static class Rotate_paramContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(dslParser.ID, 0); }
		public VectorContext vector() {
			return getRuleContext(VectorContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Rotate_paramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rotate_param; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterRotate_param(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitRotate_param(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof dslVisitor ) return ((dslVisitor<? extends T>)visitor).visitRotate_param(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Rotate_paramContext rotate_param() throws RecognitionException {
		Rotate_paramContext _localctx = new Rotate_paramContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_rotate_param);
		try {
			setState(195);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(192);
				match(ID);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(193);
				vector();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(194);
				expression(0);
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
	public static class Pause_statementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Pause_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pause_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterPause_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitPause_statement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof dslVisitor ) return ((dslVisitor<? extends T>)visitor).visitPause_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Pause_statementContext pause_statement() throws RecognitionException {
		Pause_statementContext _localctx = new Pause_statementContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_pause_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197);
			match(T__22);
			setState(198);
			match(T__8);
			setState(199);
			expression(0);
			setState(200);
			match(T__10);
			setState(201);
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
	public static class ExpressionContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(dslParser.NUMBER, 0); }
		public TerminalNode ID() { return getToken(dslParser.ID, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dslListener ) ((dslListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof dslVisitor ) return ((dslVisitor<? extends T>)visitor).visitExpression(this);
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
		int _startState = 38;
		enterRecursionRule(_localctx, 38, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NUMBER:
				{
				setState(204);
				match(NUMBER);
				}
				break;
			case ID:
				{
				setState(205);
				match(ID);
				}
				break;
			case T__8:
				{
				setState(206);
				match(T__8);
				setState(207);
				expression(0);
				setState(208);
				match(T__10);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(217);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ExpressionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_expression);
					setState(212);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(213);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 251658240L) != 0)) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(214);
					expression(3);
					}
					} 
				}
				setState(219);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 19:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u001f\u00dd\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007"+
		"\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007"+
		"\u0012\u0002\u0013\u0007\u0013\u0001\u0000\u0001\u0000\u0004\u0000+\b"+
		"\u0000\u000b\u0000\f\u0000,\u0001\u0000\u0005\u00000\b\u0000\n\u0000\f"+
		"\u00003\t\u0000\u0001\u0000\u0005\u00006\b\u0000\n\u0000\f\u00009\t\u0000"+
		"\u0001\u0000\u0005\u0000<\b\u0000\n\u0000\f\u0000?\t\u0000\u0001\u0000"+
		"\u0005\u0000B\b\u0000\n\u0000\f\u0000E\t\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003S\b\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0003\bs\b\b"+
		"\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0005\t{\b\t\n\t\f\t"+
		"~\t\t\u0001\n\u0001\n\u0001\n\u0003\n\u0083\b\n\u0001\u000b\u0001\u000b"+
		"\u0004\u000b\u0087\b\u000b\u000b\u000b\f\u000b\u0088\u0001\u000b\u0001"+
		"\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u0098\b\u000e"+
		"\u0001\u000f\u0001\u000f\u0004\u000f\u009c\b\u000f\u000b\u000f\f\u000f"+
		"\u009d\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0003\u0010\u00bf\b\u0010\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0003\u0011\u00c4\b\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0003\u0013\u00d3\b\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0005\u0013\u00d8\b\u0013\n\u0013\f\u0013"+
		"\u00db\t\u0013\u0001\u0013\u0000\u0001&\u0014\u0000\u0002\u0004\u0006"+
		"\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&\u0000"+
		"\u0003\u0001\u0000\f\u000e\u0001\u0000\u000f\u0011\u0001\u0000\u0018\u001b"+
		"\u00df\u0000(\u0001\u0000\u0000\u0000\u0002F\u0001\u0000\u0000\u0000\u0004"+
		"K\u0001\u0000\u0000\u0000\u0006R\u0001\u0000\u0000\u0000\bT\u0001\u0000"+
		"\u0000\u0000\nZ\u0001\u0000\u0000\u0000\f`\u0001\u0000\u0000\u0000\u000e"+
		"f\u0001\u0000\u0000\u0000\u0010n\u0001\u0000\u0000\u0000\u0012w\u0001"+
		"\u0000\u0000\u0000\u0014\u0082\u0001\u0000\u0000\u0000\u0016\u0084\u0001"+
		"\u0000\u0000\u0000\u0018\u008c\u0001\u0000\u0000\u0000\u001a\u008e\u0001"+
		"\u0000\u0000\u0000\u001c\u0097\u0001\u0000\u0000\u0000\u001e\u0099\u0001"+
		"\u0000\u0000\u0000 \u00be\u0001\u0000\u0000\u0000\"\u00c3\u0001\u0000"+
		"\u0000\u0000$\u00c5\u0001\u0000\u0000\u0000&\u00d2\u0001\u0000\u0000\u0000"+
		"(*\u0003\u0002\u0001\u0000)+\u0003\u0004\u0002\u0000*)\u0001\u0000\u0000"+
		"\u0000+,\u0001\u0000\u0000\u0000,*\u0001\u0000\u0000\u0000,-\u0001\u0000"+
		"\u0000\u0000-1\u0001\u0000\u0000\u0000.0\u0003\u0006\u0003\u0000/.\u0001"+
		"\u0000\u0000\u000003\u0001\u0000\u0000\u00001/\u0001\u0000\u0000\u0000"+
		"12\u0001\u0000\u0000\u000027\u0001\u0000\u0000\u000031\u0001\u0000\u0000"+
		"\u000046\u0003\u0010\b\u000054\u0001\u0000\u0000\u000069\u0001\u0000\u0000"+
		"\u000075\u0001\u0000\u0000\u000078\u0001\u0000\u0000\u00008=\u0001\u0000"+
		"\u0000\u000097\u0001\u0000\u0000\u0000:<\u0003\u0016\u000b\u0000;:\u0001"+
		"\u0000\u0000\u0000<?\u0001\u0000\u0000\u0000=;\u0001\u0000\u0000\u0000"+
		"=>\u0001\u0000\u0000\u0000>C\u0001\u0000\u0000\u0000?=\u0001\u0000\u0000"+
		"\u0000@B\u0003$\u0012\u0000A@\u0001\u0000\u0000\u0000BE\u0001\u0000\u0000"+
		"\u0000CA\u0001\u0000\u0000\u0000CD\u0001\u0000\u0000\u0000D\u0001\u0001"+
		"\u0000\u0000\u0000EC\u0001\u0000\u0000\u0000FG\u0005\u0001\u0000\u0000"+
		"GH\u0005\u0002\u0000\u0000HI\u0005\u001c\u0000\u0000IJ\u0005\u0003\u0000"+
		"\u0000J\u0003\u0001\u0000\u0000\u0000KL\u0005\u0004\u0000\u0000LM\u0005"+
		"\u001d\u0000\u0000MN\u0005\u0003\u0000\u0000N\u0005\u0001\u0000\u0000"+
		"\u0000OS\u0003\b\u0004\u0000PS\u0003\n\u0005\u0000QS\u0003\f\u0006\u0000"+
		"RO\u0001\u0000\u0000\u0000RP\u0001\u0000\u0000\u0000RQ\u0001\u0000\u0000"+
		"\u0000S\u0007\u0001\u0000\u0000\u0000TU\u0005\u0005\u0000\u0000UV\u0005"+
		"\u001d\u0000\u0000VW\u0005\u0006\u0000\u0000WX\u0003\u000e\u0007\u0000"+
		"XY\u0005\u0003\u0000\u0000Y\t\u0001\u0000\u0000\u0000Z[\u0005\u0007\u0000"+
		"\u0000[\\\u0005\u001d\u0000\u0000\\]\u0005\u0006\u0000\u0000]^\u0003&"+
		"\u0013\u0000^_\u0005\u0003\u0000\u0000_\u000b\u0001\u0000\u0000\u0000"+
		"`a\u0005\b\u0000\u0000ab\u0005\u001d\u0000\u0000bc\u0005\u0006\u0000\u0000"+
		"cd\u0003&\u0013\u0000de\u0005\u0003\u0000\u0000e\r\u0001\u0000\u0000\u0000"+
		"fg\u0005\t\u0000\u0000gh\u0003&\u0013\u0000hi\u0005\n\u0000\u0000ij\u0003"+
		"&\u0013\u0000jk\u0005\n\u0000\u0000kl\u0003&\u0013\u0000lm\u0005\u000b"+
		"\u0000\u0000m\u000f\u0001\u0000\u0000\u0000no\u0005\u001d\u0000\u0000"+
		"op\u0005\u001d\u0000\u0000pr\u0005\t\u0000\u0000qs\u0003\u0012\t\u0000"+
		"rq\u0001\u0000\u0000\u0000rs\u0001\u0000\u0000\u0000st\u0001\u0000\u0000"+
		"\u0000tu\u0005\u000b\u0000\u0000uv\u0005\u0003\u0000\u0000v\u0011\u0001"+
		"\u0000\u0000\u0000w|\u0003\u0014\n\u0000xy\u0005\n\u0000\u0000y{\u0003"+
		"\u0014\n\u0000zx\u0001\u0000\u0000\u0000{~\u0001\u0000\u0000\u0000|z\u0001"+
		"\u0000\u0000\u0000|}\u0001\u0000\u0000\u0000}\u0013\u0001\u0000\u0000"+
		"\u0000~|\u0001\u0000\u0000\u0000\u007f\u0083\u0005\u001d\u0000\u0000\u0080"+
		"\u0083\u0003\u000e\u0007\u0000\u0081\u0083\u0003&\u0013\u0000\u0082\u007f"+
		"\u0001\u0000\u0000\u0000\u0082\u0080\u0001\u0000\u0000\u0000\u0082\u0081"+
		"\u0001\u0000\u0000\u0000\u0083\u0015\u0001\u0000\u0000\u0000\u0084\u0086"+
		"\u0003\u0018\f\u0000\u0085\u0087\u0003\u001c\u000e\u0000\u0086\u0085\u0001"+
		"\u0000\u0000\u0000\u0087\u0088\u0001\u0000\u0000\u0000\u0088\u0086\u0001"+
		"\u0000\u0000\u0000\u0088\u0089\u0001\u0000\u0000\u0000\u0089\u008a\u0001"+
		"\u0000\u0000\u0000\u008a\u008b\u0003\u001a\r\u0000\u008b\u0017\u0001\u0000"+
		"\u0000\u0000\u008c\u008d\u0007\u0000\u0000\u0000\u008d\u0019\u0001\u0000"+
		"\u0000\u0000\u008e\u008f\u0007\u0001\u0000\u0000\u008f\u001b\u0001\u0000"+
		"\u0000\u0000\u0090\u0091\u0005\u001d\u0000\u0000\u0091\u0092\u0005\u0012"+
		"\u0000\u0000\u0092\u0093\u0003 \u0010\u0000\u0093\u0094\u0005\u0003\u0000"+
		"\u0000\u0094\u0098\u0001\u0000\u0000\u0000\u0095\u0098\u0003\u001e\u000f"+
		"\u0000\u0096\u0098\u0003$\u0012\u0000\u0097\u0090\u0001\u0000\u0000\u0000"+
		"\u0097\u0095\u0001\u0000\u0000\u0000\u0097\u0096\u0001\u0000\u0000\u0000"+
		"\u0098\u001d\u0001\u0000\u0000\u0000\u0099\u009b\u0005\u000e\u0000\u0000"+
		"\u009a\u009c\u0003\u001c\u000e\u0000\u009b\u009a\u0001\u0000\u0000\u0000"+
		"\u009c\u009d\u0001\u0000\u0000\u0000\u009d\u009b\u0001\u0000\u0000\u0000"+
		"\u009d\u009e\u0001\u0000\u0000\u0000\u009e\u009f\u0001\u0000\u0000\u0000"+
		"\u009f\u00a0\u0005\u0011\u0000\u0000\u00a0\u001f\u0001\u0000\u0000\u0000"+
		"\u00a1\u00a2\u0005\u0013\u0000\u0000\u00a2\u00a3\u0005\t\u0000\u0000\u00a3"+
		"\u00a4\u0003\u000e\u0007\u0000\u00a4\u00a5\u0005\n\u0000\u0000\u00a5\u00a6"+
		"\u0003&\u0013\u0000\u00a6\u00a7\u0005\n\u0000\u0000\u00a7\u00a8\u0005"+
		"\u001d\u0000\u0000\u00a8\u00a9\u0005\u000b\u0000\u0000\u00a9\u00bf\u0001"+
		"\u0000\u0000\u0000\u00aa\u00ab\u0005\u0014\u0000\u0000\u00ab\u00ac\u0005"+
		"\t\u0000\u0000\u00ac\u00ad\u0003\"\u0011\u0000\u00ad\u00ae\u0005\n\u0000"+
		"\u0000\u00ae\u00af\u0003\"\u0011\u0000\u00af\u00b0\u0005\n\u0000\u0000"+
		"\u00b0\u00b1\u0003\"\u0011\u0000\u00b1\u00b2\u0005\n\u0000\u0000\u00b2"+
		"\u00b3\u0003\"\u0011\u0000\u00b3\u00b4\u0005\u000b\u0000\u0000\u00b4\u00bf"+
		"\u0001\u0000\u0000\u0000\u00b5\u00b6\u0005\u0015\u0000\u0000\u00b6\u00b7"+
		"\u0005\t\u0000\u0000\u00b7\u00b8\u0005\u001e\u0000\u0000\u00b8\u00b9\u0005"+
		"\n\u0000\u0000\u00b9\u00ba\u0005\u001e\u0000\u0000\u00ba\u00bb\u0005\n"+
		"\u0000\u0000\u00bb\u00bc\u0005\u001e\u0000\u0000\u00bc\u00bf\u0005\u000b"+
		"\u0000\u0000\u00bd\u00bf\u0005\u0016\u0000\u0000\u00be\u00a1\u0001\u0000"+
		"\u0000\u0000\u00be\u00aa\u0001\u0000\u0000\u0000\u00be\u00b5\u0001\u0000"+
		"\u0000\u0000\u00be\u00bd\u0001\u0000\u0000\u0000\u00bf!\u0001\u0000\u0000"+
		"\u0000\u00c0\u00c4\u0005\u001d\u0000\u0000\u00c1\u00c4\u0003\u000e\u0007"+
		"\u0000\u00c2\u00c4\u0003&\u0013\u0000\u00c3\u00c0\u0001\u0000\u0000\u0000"+
		"\u00c3\u00c1\u0001\u0000\u0000\u0000\u00c3\u00c2\u0001\u0000\u0000\u0000"+
		"\u00c4#\u0001\u0000\u0000\u0000\u00c5\u00c6\u0005\u0017\u0000\u0000\u00c6"+
		"\u00c7\u0005\t\u0000\u0000\u00c7\u00c8\u0003&\u0013\u0000\u00c8\u00c9"+
		"\u0005\u000b\u0000\u0000\u00c9\u00ca\u0005\u0003\u0000\u0000\u00ca%\u0001"+
		"\u0000\u0000\u0000\u00cb\u00cc\u0006\u0013\uffff\uffff\u0000\u00cc\u00d3"+
		"\u0005\u001e\u0000\u0000\u00cd\u00d3\u0005\u001d\u0000\u0000\u00ce\u00cf"+
		"\u0005\t\u0000\u0000\u00cf\u00d0\u0003&\u0013\u0000\u00d0\u00d1\u0005"+
		"\u000b\u0000\u0000\u00d1\u00d3\u0001\u0000\u0000\u0000\u00d2\u00cb\u0001"+
		"\u0000\u0000\u0000\u00d2\u00cd\u0001\u0000\u0000\u0000\u00d2\u00ce\u0001"+
		"\u0000\u0000\u0000\u00d3\u00d9\u0001\u0000\u0000\u0000\u00d4\u00d5\n\u0002"+
		"\u0000\u0000\u00d5\u00d6\u0007\u0002\u0000\u0000\u00d6\u00d8\u0003&\u0013"+
		"\u0003\u00d7\u00d4\u0001\u0000\u0000\u0000\u00d8\u00db\u0001\u0000\u0000"+
		"\u0000\u00d9\u00d7\u0001\u0000\u0000\u0000\u00d9\u00da\u0001\u0000\u0000"+
		"\u0000\u00da\'\u0001\u0000\u0000\u0000\u00db\u00d9\u0001\u0000\u0000\u0000"+
		"\u0010,17=CRr|\u0082\u0088\u0097\u009d\u00be\u00c3\u00d2\u00d9";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}