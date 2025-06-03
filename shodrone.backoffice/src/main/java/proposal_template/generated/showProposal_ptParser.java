package proposal_template.generated;// Generated from showProposal_pt.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class showProposal_ptParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		EXMOS_SENHORES=1, EMPRESA=2, VAT=3, MORADA=4, REF=5, PROPOSTA_SHOW=6, 
		INICIO=7, FIM1=8, FIM2=9, FIM3=10, CRM_MANAGER=11, TITULO_ANEXO=12, LOCAL=13, 
		DATA_FIELD=14, HORA_FIELD=15, DURACAO_FIELD=16, TITULO_DRONES_L=17, DRONE_INFO=18, 
		TITULO_FIGURAS_L=19, FIGURAS_INFO=20, DESCRICAO=21, WS=22;
	public static final int
		RULE_proposal = 0, RULE_header = 1, RULE_corpo = 2, RULE_descricao = 3, 
		RULE_fim = 4, RULE_assinatura = 5, RULE_anexo = 6, RULE_lista_drones = 7, 
		RULE_drone_info = 8, RULE_lista_figuras = 9, RULE_figuras_info = 10;
	private static String[] makeRuleNames() {
		return new String[] {
			"proposal", "header", "corpo", "descricao", "fim", "assinatura", "anexo", 
			"lista_drones", "drone_info", "lista_figuras", "figuras_info"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'Exmos. Senhores'", null, null, null, null, "'Proposta de Show'", 
			null, "'Estando certos que seremos alvo da V/ prefer\\u00EAncia.'", "'Subscrevemo-nos ao dispor.'", 
			"'Melhores cumprimentos,'", "'CRM Manager'", null, null, null, null, 
			null, "'#Lista de drones utilizados'", null, "'#Lista de figuras'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "EXMOS_SENHORES", "EMPRESA", "VAT", "MORADA", "REF", "PROPOSTA_SHOW", 
			"INICIO", "FIM1", "FIM2", "FIM3", "CRM_MANAGER", "TITULO_ANEXO", "LOCAL", 
			"DATA_FIELD", "HORA_FIELD", "DURACAO_FIELD", "TITULO_DRONES_L", "DRONE_INFO", 
			"TITULO_FIGURAS_L", "FIGURAS_INFO", "DESCRICAO", "WS"
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
	public String getGrammarFileName() { return "showProposal_pt.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public showProposal_ptParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProposalContext extends ParserRuleContext {
		public HeaderContext header() {
			return getRuleContext(HeaderContext.class,0);
		}
		public CorpoContext corpo() {
			return getRuleContext(CorpoContext.class,0);
		}
		public AssinaturaContext assinatura() {
			return getRuleContext(AssinaturaContext.class,0);
		}
		public AnexoContext anexo() {
			return getRuleContext(AnexoContext.class,0);
		}
		public Lista_dronesContext lista_drones() {
			return getRuleContext(Lista_dronesContext.class,0);
		}
		public Lista_figurasContext lista_figuras() {
			return getRuleContext(Lista_figurasContext.class,0);
		}
		public TerminalNode EOF() { return getToken(showProposal_ptParser.EOF, 0); }
		public ProposalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_proposal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof showProposal_ptListener ) ((showProposal_ptListener)listener).enterProposal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof showProposal_ptListener ) ((showProposal_ptListener)listener).exitProposal(this);
		}
	}

	public final ProposalContext proposal() throws RecognitionException {
		ProposalContext _localctx = new ProposalContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_proposal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(22);
			header();
			setState(23);
			corpo();
			setState(24);
			assinatura();
			setState(25);
			anexo();
			setState(26);
			lista_drones();
			setState(27);
			lista_figuras();
			setState(28);
			match(Recognizer.EOF);
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
	public static class HeaderContext extends ParserRuleContext {
		public TerminalNode EXMOS_SENHORES() { return getToken(showProposal_ptParser.EXMOS_SENHORES, 0); }
		public TerminalNode EMPRESA() { return getToken(showProposal_ptParser.EMPRESA, 0); }
		public TerminalNode MORADA() { return getToken(showProposal_ptParser.MORADA, 0); }
		public TerminalNode VAT() { return getToken(showProposal_ptParser.VAT, 0); }
		public TerminalNode REF() { return getToken(showProposal_ptParser.REF, 0); }
		public TerminalNode PROPOSTA_SHOW() { return getToken(showProposal_ptParser.PROPOSTA_SHOW, 0); }
		public HeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_header; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof showProposal_ptListener ) ((showProposal_ptListener)listener).enterHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof showProposal_ptListener ) ((showProposal_ptListener)listener).exitHeader(this);
		}
	}

	public final HeaderContext header() throws RecognitionException {
		HeaderContext _localctx = new HeaderContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_header);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			match(EXMOS_SENHORES);
			setState(31);
			match(EMPRESA);
			setState(32);
			match(MORADA);
			setState(33);
			match(VAT);
			setState(34);
			match(REF);
			setState(35);
			match(PROPOSTA_SHOW);
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
	public static class CorpoContext extends ParserRuleContext {
		public TerminalNode INICIO() { return getToken(showProposal_ptParser.INICIO, 0); }
		public DescricaoContext descricao() {
			return getRuleContext(DescricaoContext.class,0);
		}
		public FimContext fim() {
			return getRuleContext(FimContext.class,0);
		}
		public CorpoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_corpo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof showProposal_ptListener ) ((showProposal_ptListener)listener).enterCorpo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof showProposal_ptListener ) ((showProposal_ptListener)listener).exitCorpo(this);
		}
	}

	public final CorpoContext corpo() throws RecognitionException {
		CorpoContext _localctx = new CorpoContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_corpo);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			match(INICIO);
			setState(38);
			descricao();
			setState(39);
			fim();
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
	public static class DescricaoContext extends ParserRuleContext {
		public List<TerminalNode> DESCRICAO() { return getTokens(showProposal_ptParser.DESCRICAO); }
		public TerminalNode DESCRICAO(int i) {
			return getToken(showProposal_ptParser.DESCRICAO, i);
		}
		public DescricaoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_descricao; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof showProposal_ptListener ) ((showProposal_ptListener)listener).enterDescricao(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof showProposal_ptListener ) ((showProposal_ptListener)listener).exitDescricao(this);
		}
	}

	public final DescricaoContext descricao() throws RecognitionException {
		DescricaoContext _localctx = new DescricaoContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_descricao);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(41);
				match(DESCRICAO);
				}
				}
				setState(44); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==DESCRICAO );
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
	public static class FimContext extends ParserRuleContext {
		public TerminalNode FIM1() { return getToken(showProposal_ptParser.FIM1, 0); }
		public TerminalNode FIM2() { return getToken(showProposal_ptParser.FIM2, 0); }
		public TerminalNode FIM3() { return getToken(showProposal_ptParser.FIM3, 0); }
		public FimContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fim; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof showProposal_ptListener ) ((showProposal_ptListener)listener).enterFim(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof showProposal_ptListener ) ((showProposal_ptListener)listener).exitFim(this);
		}
	}

	public final FimContext fim() throws RecognitionException {
		FimContext _localctx = new FimContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_fim);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			match(FIM1);
			setState(47);
			match(FIM2);
			setState(48);
			match(FIM3);
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
	public static class AssinaturaContext extends ParserRuleContext {
		public TerminalNode CRM_MANAGER() { return getToken(showProposal_ptParser.CRM_MANAGER, 0); }
		public List<TerminalNode> DESCRICAO() { return getTokens(showProposal_ptParser.DESCRICAO); }
		public TerminalNode DESCRICAO(int i) {
			return getToken(showProposal_ptParser.DESCRICAO, i);
		}
		public List<TerminalNode> WS() { return getTokens(showProposal_ptParser.WS); }
		public TerminalNode WS(int i) {
			return getToken(showProposal_ptParser.WS, i);
		}
		public AssinaturaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assinatura; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof showProposal_ptListener ) ((showProposal_ptListener)listener).enterAssinatura(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof showProposal_ptListener ) ((showProposal_ptListener)listener).exitAssinatura(this);
		}
	}

	public final AssinaturaContext assinatura() throws RecognitionException {
		AssinaturaContext _localctx = new AssinaturaContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_assinatura);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DESCRICAO || _la==WS) {
				{
				{
				setState(50);
				_la = _input.LA(1);
				if ( !(_la==DESCRICAO || _la==WS) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				}
				setState(55);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(56);
			match(CRM_MANAGER);
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
	public static class AnexoContext extends ParserRuleContext {
		public TerminalNode TITULO_ANEXO() { return getToken(showProposal_ptParser.TITULO_ANEXO, 0); }
		public TerminalNode LOCAL() { return getToken(showProposal_ptParser.LOCAL, 0); }
		public TerminalNode DATA_FIELD() { return getToken(showProposal_ptParser.DATA_FIELD, 0); }
		public TerminalNode HORA_FIELD() { return getToken(showProposal_ptParser.HORA_FIELD, 0); }
		public TerminalNode DURACAO_FIELD() { return getToken(showProposal_ptParser.DURACAO_FIELD, 0); }
		public AnexoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_anexo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof showProposal_ptListener ) ((showProposal_ptListener)listener).enterAnexo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof showProposal_ptListener ) ((showProposal_ptListener)listener).exitAnexo(this);
		}
	}

	public final AnexoContext anexo() throws RecognitionException {
		AnexoContext _localctx = new AnexoContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_anexo);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			match(TITULO_ANEXO);
			setState(59);
			match(LOCAL);
			setState(60);
			match(DATA_FIELD);
			setState(61);
			match(HORA_FIELD);
			setState(62);
			match(DURACAO_FIELD);
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
	public static class Lista_dronesContext extends ParserRuleContext {
		public TerminalNode TITULO_DRONES_L() { return getToken(showProposal_ptParser.TITULO_DRONES_L, 0); }
		public Drone_infoContext drone_info() {
			return getRuleContext(Drone_infoContext.class,0);
		}
		public Lista_dronesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lista_drones; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof showProposal_ptListener ) ((showProposal_ptListener)listener).enterLista_drones(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof showProposal_ptListener ) ((showProposal_ptListener)listener).exitLista_drones(this);
		}
	}

	public final Lista_dronesContext lista_drones() throws RecognitionException {
		Lista_dronesContext _localctx = new Lista_dronesContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_lista_drones);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			match(TITULO_DRONES_L);
			setState(65);
			drone_info();
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
	public static class Drone_infoContext extends ParserRuleContext {
		public List<TerminalNode> DRONE_INFO() { return getTokens(showProposal_ptParser.DRONE_INFO); }
		public TerminalNode DRONE_INFO(int i) {
			return getToken(showProposal_ptParser.DRONE_INFO, i);
		}
		public Drone_infoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_drone_info; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof showProposal_ptListener ) ((showProposal_ptListener)listener).enterDrone_info(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof showProposal_ptListener ) ((showProposal_ptListener)listener).exitDrone_info(this);
		}
	}

	public final Drone_infoContext drone_info() throws RecognitionException {
		Drone_infoContext _localctx = new Drone_infoContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_drone_info);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(67);
				match(DRONE_INFO);
				}
				}
				setState(70); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==DRONE_INFO );
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
	public static class Lista_figurasContext extends ParserRuleContext {
		public TerminalNode TITULO_FIGURAS_L() { return getToken(showProposal_ptParser.TITULO_FIGURAS_L, 0); }
		public Figuras_infoContext figuras_info() {
			return getRuleContext(Figuras_infoContext.class,0);
		}
		public Lista_figurasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lista_figuras; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof showProposal_ptListener ) ((showProposal_ptListener)listener).enterLista_figuras(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof showProposal_ptListener ) ((showProposal_ptListener)listener).exitLista_figuras(this);
		}
	}

	public final Lista_figurasContext lista_figuras() throws RecognitionException {
		Lista_figurasContext _localctx = new Lista_figurasContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_lista_figuras);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			match(TITULO_FIGURAS_L);
			setState(73);
			figuras_info();
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
	public static class Figuras_infoContext extends ParserRuleContext {
		public List<TerminalNode> FIGURAS_INFO() { return getTokens(showProposal_ptParser.FIGURAS_INFO); }
		public TerminalNode FIGURAS_INFO(int i) {
			return getToken(showProposal_ptParser.FIGURAS_INFO, i);
		}
		public Figuras_infoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_figuras_info; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof showProposal_ptListener ) ((showProposal_ptListener)listener).enterFiguras_info(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof showProposal_ptListener ) ((showProposal_ptListener)listener).exitFiguras_info(this);
		}
	}

	public final Figuras_infoContext figuras_info() throws RecognitionException {
		Figuras_infoContext _localctx = new Figuras_infoContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_figuras_info);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(75);
				match(FIGURAS_INFO);
				}
				}
				setState(78); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==FIGURAS_INFO );
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

	public static final String _serializedATN =
		"\u0004\u0001\u0016Q\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0004"+
		"\u0003+\b\u0003\u000b\u0003\f\u0003,\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0005\u0005\u00054\b\u0005\n\u0005\f\u00057\t\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0004"+
		"\bE\b\b\u000b\b\f\bF\u0001\t\u0001\t\u0001\t\u0001\n\u0004\nM\b\n\u000b"+
		"\n\f\nN\u0001\n\u0000\u0000\u000b\u0000\u0002\u0004\u0006\b\n\f\u000e"+
		"\u0010\u0012\u0014\u0000\u0001\u0001\u0000\u0015\u0016I\u0000\u0016\u0001"+
		"\u0000\u0000\u0000\u0002\u001e\u0001\u0000\u0000\u0000\u0004%\u0001\u0000"+
		"\u0000\u0000\u0006*\u0001\u0000\u0000\u0000\b.\u0001\u0000\u0000\u0000"+
		"\n5\u0001\u0000\u0000\u0000\f:\u0001\u0000\u0000\u0000\u000e@\u0001\u0000"+
		"\u0000\u0000\u0010D\u0001\u0000\u0000\u0000\u0012H\u0001\u0000\u0000\u0000"+
		"\u0014L\u0001\u0000\u0000\u0000\u0016\u0017\u0003\u0002\u0001\u0000\u0017"+
		"\u0018\u0003\u0004\u0002\u0000\u0018\u0019\u0003\n\u0005\u0000\u0019\u001a"+
		"\u0003\f\u0006\u0000\u001a\u001b\u0003\u000e\u0007\u0000\u001b\u001c\u0003"+
		"\u0012\t\u0000\u001c\u001d\u0005\u0000\u0000\u0001\u001d\u0001\u0001\u0000"+
		"\u0000\u0000\u001e\u001f\u0005\u0001\u0000\u0000\u001f \u0005\u0002\u0000"+
		"\u0000 !\u0005\u0004\u0000\u0000!\"\u0005\u0003\u0000\u0000\"#\u0005\u0005"+
		"\u0000\u0000#$\u0005\u0006\u0000\u0000$\u0003\u0001\u0000\u0000\u0000"+
		"%&\u0005\u0007\u0000\u0000&\'\u0003\u0006\u0003\u0000\'(\u0003\b\u0004"+
		"\u0000(\u0005\u0001\u0000\u0000\u0000)+\u0005\u0015\u0000\u0000*)\u0001"+
		"\u0000\u0000\u0000+,\u0001\u0000\u0000\u0000,*\u0001\u0000\u0000\u0000"+
		",-\u0001\u0000\u0000\u0000-\u0007\u0001\u0000\u0000\u0000./\u0005\b\u0000"+
		"\u0000/0\u0005\t\u0000\u000001\u0005\n\u0000\u00001\t\u0001\u0000\u0000"+
		"\u000024\u0007\u0000\u0000\u000032\u0001\u0000\u0000\u000047\u0001\u0000"+
		"\u0000\u000053\u0001\u0000\u0000\u000056\u0001\u0000\u0000\u000068\u0001"+
		"\u0000\u0000\u000075\u0001\u0000\u0000\u000089\u0005\u000b\u0000\u0000"+
		"9\u000b\u0001\u0000\u0000\u0000:;\u0005\f\u0000\u0000;<\u0005\r\u0000"+
		"\u0000<=\u0005\u000e\u0000\u0000=>\u0005\u000f\u0000\u0000>?\u0005\u0010"+
		"\u0000\u0000?\r\u0001\u0000\u0000\u0000@A\u0005\u0011\u0000\u0000AB\u0003"+
		"\u0010\b\u0000B\u000f\u0001\u0000\u0000\u0000CE\u0005\u0012\u0000\u0000"+
		"DC\u0001\u0000\u0000\u0000EF\u0001\u0000\u0000\u0000FD\u0001\u0000\u0000"+
		"\u0000FG\u0001\u0000\u0000\u0000G\u0011\u0001\u0000\u0000\u0000HI\u0005"+
		"\u0013\u0000\u0000IJ\u0003\u0014\n\u0000J\u0013\u0001\u0000\u0000\u0000"+
		"KM\u0005\u0014\u0000\u0000LK\u0001\u0000\u0000\u0000MN\u0001\u0000\u0000"+
		"\u0000NL\u0001\u0000\u0000\u0000NO\u0001\u0000\u0000\u0000O\u0015\u0001"+
		"\u0000\u0000\u0000\u0004,5FN";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}