package drone_language_validation.generated;// Generated from droneGeneric.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class droneGenericLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, ID=16, NUMBER=17, 
		WS=18;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "ID", "NUMBER", 
			"WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'programming'", "'language'", "'version'", "'Types'", "'Variables'", 
			"'='", "';'", "'Instructions'", "'('", "')'", "','", "'*'", "'/'", "'+'", 
			"'-'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, "ID", "NUMBER", "WS"
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


	public droneGenericLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "droneGeneric.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0012\u008f\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006"+
		"\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001"+
		"\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001\u000e"+
		"\u0001\u000e\u0001\u000f\u0001\u000f\u0005\u000ft\b\u000f\n\u000f\f\u000f"+
		"w\t\u000f\u0001\u0010\u0003\u0010z\b\u0010\u0001\u0010\u0004\u0010}\b"+
		"\u0010\u000b\u0010\f\u0010~\u0001\u0010\u0001\u0010\u0004\u0010\u0083"+
		"\b\u0010\u000b\u0010\f\u0010\u0084\u0003\u0010\u0087\b\u0010\u0001\u0011"+
		"\u0004\u0011\u008a\b\u0011\u000b\u0011\f\u0011\u008b\u0001\u0011\u0001"+
		"\u0011\u0000\u0000\u0012\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004"+
		"\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017"+
		"\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012\u0001\u0000"+
		"\u0004\u0003\u0000AZ__az\u0004\u000009AZ__az\u0001\u000009\u0003\u0000"+
		"\t\n\r\r  \u0094\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001"+
		"\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001"+
		"\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000"+
		"\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000"+
		"\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000"+
		"\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000"+
		"\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000"+
		"\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000"+
		"\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0001"+
		"%\u0001\u0000\u0000\u0000\u00031\u0001\u0000\u0000\u0000\u0005:\u0001"+
		"\u0000\u0000\u0000\u0007B\u0001\u0000\u0000\u0000\tH\u0001\u0000\u0000"+
		"\u0000\u000bR\u0001\u0000\u0000\u0000\rT\u0001\u0000\u0000\u0000\u000f"+
		"V\u0001\u0000\u0000\u0000\u0011c\u0001\u0000\u0000\u0000\u0013e\u0001"+
		"\u0000\u0000\u0000\u0015g\u0001\u0000\u0000\u0000\u0017i\u0001\u0000\u0000"+
		"\u0000\u0019k\u0001\u0000\u0000\u0000\u001bm\u0001\u0000\u0000\u0000\u001d"+
		"o\u0001\u0000\u0000\u0000\u001fq\u0001\u0000\u0000\u0000!y\u0001\u0000"+
		"\u0000\u0000#\u0089\u0001\u0000\u0000\u0000%&\u0005p\u0000\u0000&\'\u0005"+
		"r\u0000\u0000\'(\u0005o\u0000\u0000()\u0005g\u0000\u0000)*\u0005r\u0000"+
		"\u0000*+\u0005a\u0000\u0000+,\u0005m\u0000\u0000,-\u0005m\u0000\u0000"+
		"-.\u0005i\u0000\u0000./\u0005n\u0000\u0000/0\u0005g\u0000\u00000\u0002"+
		"\u0001\u0000\u0000\u000012\u0005l\u0000\u000023\u0005a\u0000\u000034\u0005"+
		"n\u0000\u000045\u0005g\u0000\u000056\u0005u\u0000\u000067\u0005a\u0000"+
		"\u000078\u0005g\u0000\u000089\u0005e\u0000\u00009\u0004\u0001\u0000\u0000"+
		"\u0000:;\u0005v\u0000\u0000;<\u0005e\u0000\u0000<=\u0005r\u0000\u0000"+
		"=>\u0005s\u0000\u0000>?\u0005i\u0000\u0000?@\u0005o\u0000\u0000@A\u0005"+
		"n\u0000\u0000A\u0006\u0001\u0000\u0000\u0000BC\u0005T\u0000\u0000CD\u0005"+
		"y\u0000\u0000DE\u0005p\u0000\u0000EF\u0005e\u0000\u0000FG\u0005s\u0000"+
		"\u0000G\b\u0001\u0000\u0000\u0000HI\u0005V\u0000\u0000IJ\u0005a\u0000"+
		"\u0000JK\u0005r\u0000\u0000KL\u0005i\u0000\u0000LM\u0005a\u0000\u0000"+
		"MN\u0005b\u0000\u0000NO\u0005l\u0000\u0000OP\u0005e\u0000\u0000PQ\u0005"+
		"s\u0000\u0000Q\n\u0001\u0000\u0000\u0000RS\u0005=\u0000\u0000S\f\u0001"+
		"\u0000\u0000\u0000TU\u0005;\u0000\u0000U\u000e\u0001\u0000\u0000\u0000"+
		"VW\u0005I\u0000\u0000WX\u0005n\u0000\u0000XY\u0005s\u0000\u0000YZ\u0005"+
		"t\u0000\u0000Z[\u0005r\u0000\u0000[\\\u0005u\u0000\u0000\\]\u0005c\u0000"+
		"\u0000]^\u0005t\u0000\u0000^_\u0005i\u0000\u0000_`\u0005o\u0000\u0000"+
		"`a\u0005n\u0000\u0000ab\u0005s\u0000\u0000b\u0010\u0001\u0000\u0000\u0000"+
		"cd\u0005(\u0000\u0000d\u0012\u0001\u0000\u0000\u0000ef\u0005)\u0000\u0000"+
		"f\u0014\u0001\u0000\u0000\u0000gh\u0005,\u0000\u0000h\u0016\u0001\u0000"+
		"\u0000\u0000ij\u0005*\u0000\u0000j\u0018\u0001\u0000\u0000\u0000kl\u0005"+
		"/\u0000\u0000l\u001a\u0001\u0000\u0000\u0000mn\u0005+\u0000\u0000n\u001c"+
		"\u0001\u0000\u0000\u0000op\u0005-\u0000\u0000p\u001e\u0001\u0000\u0000"+
		"\u0000qu\u0007\u0000\u0000\u0000rt\u0007\u0001\u0000\u0000sr\u0001\u0000"+
		"\u0000\u0000tw\u0001\u0000\u0000\u0000us\u0001\u0000\u0000\u0000uv\u0001"+
		"\u0000\u0000\u0000v \u0001\u0000\u0000\u0000wu\u0001\u0000\u0000\u0000"+
		"xz\u0005-\u0000\u0000yx\u0001\u0000\u0000\u0000yz\u0001\u0000\u0000\u0000"+
		"z|\u0001\u0000\u0000\u0000{}\u0007\u0002\u0000\u0000|{\u0001\u0000\u0000"+
		"\u0000}~\u0001\u0000\u0000\u0000~|\u0001\u0000\u0000\u0000~\u007f\u0001"+
		"\u0000\u0000\u0000\u007f\u0086\u0001\u0000\u0000\u0000\u0080\u0082\u0005"+
		".\u0000\u0000\u0081\u0083\u0007\u0002\u0000\u0000\u0082\u0081\u0001\u0000"+
		"\u0000\u0000\u0083\u0084\u0001\u0000\u0000\u0000\u0084\u0082\u0001\u0000"+
		"\u0000\u0000\u0084\u0085\u0001\u0000\u0000\u0000\u0085\u0087\u0001\u0000"+
		"\u0000\u0000\u0086\u0080\u0001\u0000\u0000\u0000\u0086\u0087\u0001\u0000"+
		"\u0000\u0000\u0087\"\u0001\u0000\u0000\u0000\u0088\u008a\u0007\u0003\u0000"+
		"\u0000\u0089\u0088\u0001\u0000\u0000\u0000\u008a\u008b\u0001\u0000\u0000"+
		"\u0000\u008b\u0089\u0001\u0000\u0000\u0000\u008b\u008c\u0001\u0000\u0000"+
		"\u0000\u008c\u008d\u0001\u0000\u0000\u0000\u008d\u008e\u0006\u0011\u0000"+
		"\u0000\u008e$\u0001\u0000\u0000\u0000\u0007\u0000uy~\u0084\u0086\u008b"+
		"\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}