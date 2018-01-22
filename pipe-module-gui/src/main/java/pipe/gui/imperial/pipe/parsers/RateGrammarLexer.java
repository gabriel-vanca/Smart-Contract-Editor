package pipe.gui.imperial.pipe.parsers;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

public class RateGrammarLexer extends Lexer {
   protected static final DFA[] _decisionToDFA;
   protected static final PredictionContextCache _sharedContextCache;
   public static final int T__0 = 1;
   public static final int T__1 = 2;
   public static final int T__2 = 3;
   public static final int T__3 = 4;
   public static final int T__4 = 5;
   public static final int T__5 = 6;
   public static final int T__6 = 7;
   public static final int ID = 8;
   public static final int INT = 9;
   public static final int DOUBLE = 10;
   public static final int WS = 11;
   public static final int MUL = 12;
   public static final int DIV = 13;
   public static final int ADD = 14;
   public static final int SUB = 15;
   public static String[] modeNames;
   public static final String[] ruleNames;
   private static final String[] _LITERAL_NAMES;
   private static final String[] _SYMBOLIC_NAMES;
   public static final Vocabulary VOCABULARY;
   /** @deprecated */
   @Deprecated
   public static final String[] tokenNames;
   public static final String _serializedATN = "\u0003а훑舆괭䐗껱趀ꫝ\u0002\u0011b\b\u0001\u0004\u0002\t\u0002\u0004\u0003\t\u0003\u0004\u0004\t\u0004\u0004\u0005\t\u0005\u0004\u0006\t\u0006\u0004\u0007\t\u0007\u0004\b\t\b\u0004\t\t\t\u0004\n\t\n\u0004\u000b\t\u000b\u0004\f\t\f\u0004\r\t\r\u0004\u000e\t\u000e\u0004\u000f\t\u000f\u0004\u0010\t\u0010\u0003\u0002\u0003\u0002\u0003\u0003\u0003\u0003\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0007\u0003\u0007\u0003\u0007\u0003\b\u0003\b\u0003\t\u0003\t\u0007\t?\n\t\f\t\u000e\tB\u000b\t\u0003\n\u0006\nE\n\n\r\n\u000e\nF\u0003\u000b\u0006\u000bJ\n\u000b\r\u000b\u000e\u000bK\u0003\u000b\u0003\u000b\u0006\u000bP\n\u000b\r\u000b\u000e\u000bQ\u0003\f\u0006\fU\n\f\r\f\u000e\fV\u0003\f\u0003\f\u0003\r\u0003\r\u0003\u000e\u0003\u000e\u0003\u000f\u0003\u000f\u0003\u0010\u0003\u0010\u0002\u0002\u0011\u0003\u0003\u0005\u0004\u0007\u0005\t\u0006\u000b\u0007\r\b\u000f\t\u0011\n\u0013\u000b\u0015\f\u0017\r\u0019\u000e\u001b\u000f\u001d\u0010\u001f\u0011\u0003\u0002\u0005\u0004\u0002C\\c|\u0005\u00022;C\\c|\u0005\u0002\u000b\f\u000f\u000f\"\"f\u0002\u0003\u0003\u0002\u0002\u0002\u0002\u0005\u0003\u0002\u0002\u0002\u0002\u0007\u0003\u0002\u0002\u0002\u0002\t\u0003\u0002\u0002\u0002\u0002\u000b\u0003\u0002\u0002\u0002\u0002\r\u0003\u0002\u0002\u0002\u0002\u000f\u0003\u0002\u0002\u0002\u0002\u0011\u0003\u0002\u0002\u0002\u0002\u0013\u0003\u0002\u0002\u0002\u0002\u0015\u0003\u0002\u0002\u0002\u0002\u0017\u0003\u0002\u0002\u0002\u0002\u0019\u0003\u0002\u0002\u0002\u0002\u001b\u0003\u0002\u0002\u0002\u0002\u001d\u0003\u0002\u0002\u0002\u0002\u001f\u0003\u0002\u0002\u0002\u0003!\u0003\u0002\u0002\u0002\u0005#\u0003\u0002\u0002\u0002\u0007%\u0003\u0002\u0002\u0002\t+\u0003\u0002\u0002\u0002\u000b2\u0003\u0002\u0002\u0002\r7\u0003\u0002\u0002\u0002\u000f:\u0003\u0002\u0002\u0002\u0011<\u0003\u0002\u0002\u0002\u0013D\u0003\u0002\u0002\u0002\u0015I\u0003\u0002\u0002\u0002\u0017T\u0003\u0002\u0002\u0002\u0019Z\u0003\u0002\u0002\u0002\u001b\\\u0003\u0002\u0002\u0002\u001d^\u0003\u0002\u0002\u0002\u001f`\u0003\u0002\u0002\u0002!\"\u0007*\u0002\u0002\"\u0004\u0003\u0002\u0002\u0002#$\u0007+\u0002\u0002$\u0006\u0003\u0002\u0002\u0002%&\u0007e\u0002\u0002&'\u0007g\u0002\u0002'(\u0007k\u0002\u0002()\u0007n\u0002\u0002)*\u0007*\u0002\u0002*\b\u0003\u0002\u0002\u0002+,\u0007h\u0002\u0002,-\u0007n\u0002\u0002-.\u0007q\u0002\u0002./\u0007q\u0002\u0002/0\u0007t\u0002\u000201\u0007*\u0002\u00021\n\u0003\u0002\u0002\u000223\u0007e\u0002\u000234\u0007c\u0002\u000245\u0007r\u0002\u000256\u0007*\u0002\u00026\f\u0003\u0002\u0002\u000278\u0007%\u0002\u000289\u0007*\u0002\u00029\u000e\u0003\u0002\u0002\u0002:;\u0007.\u0002\u0002;\u0010\u0003\u0002\u0002\u0002<@\t\u0002\u0002\u0002=?\t\u0003\u0002\u0002>=\u0003\u0002\u0002\u0002?B\u0003\u0002\u0002\u0002@>\u0003\u0002\u0002\u0002@A\u0003\u0002\u0002\u0002A\u0012\u0003\u0002\u0002\u0002B@\u0003\u0002\u0002\u0002CE\u00042;\u0002DC\u0003\u0002\u0002\u0002EF\u0003\u0002\u0002\u0002FD\u0003\u0002\u0002\u0002FG\u0003\u0002\u0002\u0002G\u0014\u0003\u0002\u0002\u0002HJ\u00042;\u0002IH\u0003\u0002\u0002\u0002JK\u0003\u0002\u0002\u0002KI\u0003\u0002\u0002\u0002KL\u0003\u0002\u0002\u0002LM\u0003\u0002\u0002\u0002MO\u00070\u0002\u0002NP\u00042;\u0002ON\u0003\u0002\u0002\u0002PQ\u0003\u0002\u0002\u0002QO\u0003\u0002\u0002\u0002QR\u0003\u0002\u0002\u0002R\u0016\u0003\u0002\u0002\u0002SU\t\u0004\u0002\u0002TS\u0003\u0002\u0002\u0002UV\u0003\u0002\u0002\u0002VT\u0003\u0002\u0002\u0002VW\u0003\u0002\u0002\u0002WX\u0003\u0002\u0002\u0002XY\b\f\u0002\u0002Y\u0018\u0003\u0002\u0002\u0002Z[\u0007,\u0002\u0002[\u001a\u0003\u0002\u0002\u0002\\]\u00071\u0002\u0002]\u001c\u0003\u0002\u0002\u0002^_\u0007-\u0002\u0002_\u001e\u0003\u0002\u0002\u0002`a\u0007/\u0002\u0002a \u0003\u0002\u0002\u0002\b\u0002@FKQV\u0003\b\u0002\u0002";
   public static final ATN _ATN;

   /** @deprecated */
   @Deprecated
   public String[] getTokenNames() {
      return tokenNames;
   }

   public Vocabulary getVocabulary() {
      return VOCABULARY;
   }

   public RateGrammarLexer(CharStream input) {
      super(input);
      this._interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
   }

   public String getGrammarFileName() {
      return "RateGrammar.g4";
   }

   public String[] getRuleNames() {
      return ruleNames;
   }

   public String getSerializedATN() {
      return "\u0003а훑舆괭䐗껱趀ꫝ\u0002\u0011b\b\u0001\u0004\u0002\t\u0002\u0004\u0003\t\u0003\u0004\u0004\t\u0004\u0004\u0005\t\u0005\u0004\u0006\t\u0006\u0004\u0007\t\u0007\u0004\b\t\b\u0004\t\t\t\u0004\n\t\n\u0004\u000b\t\u000b\u0004\f\t\f\u0004\r\t\r\u0004\u000e\t\u000e\u0004\u000f\t\u000f\u0004\u0010\t\u0010\u0003\u0002\u0003\u0002\u0003\u0003\u0003\u0003\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0007\u0003\u0007\u0003\u0007\u0003\b\u0003\b\u0003\t\u0003\t\u0007\t?\n\t\f\t\u000e\tB\u000b\t\u0003\n\u0006\nE\n\n\r\n\u000e\nF\u0003\u000b\u0006\u000bJ\n\u000b\r\u000b\u000e\u000bK\u0003\u000b\u0003\u000b\u0006\u000bP\n\u000b\r\u000b\u000e\u000bQ\u0003\f\u0006\fU\n\f\r\f\u000e\fV\u0003\f\u0003\f\u0003\r\u0003\r\u0003\u000e\u0003\u000e\u0003\u000f\u0003\u000f\u0003\u0010\u0003\u0010\u0002\u0002\u0011\u0003\u0003\u0005\u0004\u0007\u0005\t\u0006\u000b\u0007\r\b\u000f\t\u0011\n\u0013\u000b\u0015\f\u0017\r\u0019\u000e\u001b\u000f\u001d\u0010\u001f\u0011\u0003\u0002\u0005\u0004\u0002C\\c|\u0005\u00022;C\\c|\u0005\u0002\u000b\f\u000f\u000f\"\"f\u0002\u0003\u0003\u0002\u0002\u0002\u0002\u0005\u0003\u0002\u0002\u0002\u0002\u0007\u0003\u0002\u0002\u0002\u0002\t\u0003\u0002\u0002\u0002\u0002\u000b\u0003\u0002\u0002\u0002\u0002\r\u0003\u0002\u0002\u0002\u0002\u000f\u0003\u0002\u0002\u0002\u0002\u0011\u0003\u0002\u0002\u0002\u0002\u0013\u0003\u0002\u0002\u0002\u0002\u0015\u0003\u0002\u0002\u0002\u0002\u0017\u0003\u0002\u0002\u0002\u0002\u0019\u0003\u0002\u0002\u0002\u0002\u001b\u0003\u0002\u0002\u0002\u0002\u001d\u0003\u0002\u0002\u0002\u0002\u001f\u0003\u0002\u0002\u0002\u0003!\u0003\u0002\u0002\u0002\u0005#\u0003\u0002\u0002\u0002\u0007%\u0003\u0002\u0002\u0002\t+\u0003\u0002\u0002\u0002\u000b2\u0003\u0002\u0002\u0002\r7\u0003\u0002\u0002\u0002\u000f:\u0003\u0002\u0002\u0002\u0011<\u0003\u0002\u0002\u0002\u0013D\u0003\u0002\u0002\u0002\u0015I\u0003\u0002\u0002\u0002\u0017T\u0003\u0002\u0002\u0002\u0019Z\u0003\u0002\u0002\u0002\u001b\\\u0003\u0002\u0002\u0002\u001d^\u0003\u0002\u0002\u0002\u001f`\u0003\u0002\u0002\u0002!\"\u0007*\u0002\u0002\"\u0004\u0003\u0002\u0002\u0002#$\u0007+\u0002\u0002$\u0006\u0003\u0002\u0002\u0002%&\u0007e\u0002\u0002&'\u0007g\u0002\u0002'(\u0007k\u0002\u0002()\u0007n\u0002\u0002)*\u0007*\u0002\u0002*\b\u0003\u0002\u0002\u0002+,\u0007h\u0002\u0002,-\u0007n\u0002\u0002-.\u0007q\u0002\u0002./\u0007q\u0002\u0002/0\u0007t\u0002\u000201\u0007*\u0002\u00021\n\u0003\u0002\u0002\u000223\u0007e\u0002\u000234\u0007c\u0002\u000245\u0007r\u0002\u000256\u0007*\u0002\u00026\f\u0003\u0002\u0002\u000278\u0007%\u0002\u000289\u0007*\u0002\u00029\u000e\u0003\u0002\u0002\u0002:;\u0007.\u0002\u0002;\u0010\u0003\u0002\u0002\u0002<@\t\u0002\u0002\u0002=?\t\u0003\u0002\u0002>=\u0003\u0002\u0002\u0002?B\u0003\u0002\u0002\u0002@>\u0003\u0002\u0002\u0002@A\u0003\u0002\u0002\u0002A\u0012\u0003\u0002\u0002\u0002B@\u0003\u0002\u0002\u0002CE\u00042;\u0002DC\u0003\u0002\u0002\u0002EF\u0003\u0002\u0002\u0002FD\u0003\u0002\u0002\u0002FG\u0003\u0002\u0002\u0002G\u0014\u0003\u0002\u0002\u0002HJ\u00042;\u0002IH\u0003\u0002\u0002\u0002JK\u0003\u0002\u0002\u0002KI\u0003\u0002\u0002\u0002KL\u0003\u0002\u0002\u0002LM\u0003\u0002\u0002\u0002MO\u00070\u0002\u0002NP\u00042;\u0002ON\u0003\u0002\u0002\u0002PQ\u0003\u0002\u0002\u0002QO\u0003\u0002\u0002\u0002QR\u0003\u0002\u0002\u0002R\u0016\u0003\u0002\u0002\u0002SU\t\u0004\u0002\u0002TS\u0003\u0002\u0002\u0002UV\u0003\u0002\u0002\u0002VT\u0003\u0002\u0002\u0002VW\u0003\u0002\u0002\u0002WX\u0003\u0002\u0002\u0002XY\b\f\u0002\u0002Y\u0018\u0003\u0002\u0002\u0002Z[\u0007,\u0002\u0002[\u001a\u0003\u0002\u0002\u0002\\]\u00071\u0002\u0002]\u001c\u0003\u0002\u0002\u0002^_\u0007-\u0002\u0002_\u001e\u0003\u0002\u0002\u0002`a\u0007/\u0002\u0002a \u0003\u0002\u0002\u0002\b\u0002@FKQV\u0003\b\u0002\u0002";
   }

   public String[] getModeNames() {
      return modeNames;
   }

   public ATN getATN() {
      return _ATN;
   }

   static {
      RuntimeMetaData.checkVersion("4.5.3", "4.5.3");
      _sharedContextCache = new PredictionContextCache();
      modeNames = new String[]{"DEFAULT_MODE"};
      ruleNames = new String[]{"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "ID", "INT", "DOUBLE", "WS", "MUL", "DIV", "ADD", "SUB"};
      _LITERAL_NAMES = new String[]{null, "'('", "')'", "'ceil('", "'floor('", "'cap('", "'#('", "','", null, null, null, null, "'*'", "'/'", "'+'", "'-'"};
      _SYMBOLIC_NAMES = new String[]{null, null, null, null, null, null, null, null, "ID", "INT", "DOUBLE", "WS", "MUL", "DIV", "ADD", "SUB"};
      VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);
      tokenNames = new String[_SYMBOLIC_NAMES.length];

      int i;
      for(i = 0; i < tokenNames.length; ++i) {
         tokenNames[i] = VOCABULARY.getLiteralName(i);
         if (tokenNames[i] == null) {
            tokenNames[i] = VOCABULARY.getSymbolicName(i);
         }

         if (tokenNames[i] == null) {
            tokenNames[i] = "<INVALID>";
         }
      }

      _ATN = (new ATNDeserializer()).deserialize("\u0003а훑舆괭䐗껱趀ꫝ\u0002\u0011b\b\u0001\u0004\u0002\t\u0002\u0004\u0003\t\u0003\u0004\u0004\t\u0004\u0004\u0005\t\u0005\u0004\u0006\t\u0006\u0004\u0007\t\u0007\u0004\b\t\b\u0004\t\t\t\u0004\n\t\n\u0004\u000b\t\u000b\u0004\f\t\f\u0004\r\t\r\u0004\u000e\t\u000e\u0004\u000f\t\u000f\u0004\u0010\t\u0010\u0003\u0002\u0003\u0002\u0003\u0003\u0003\u0003\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0007\u0003\u0007\u0003\u0007\u0003\b\u0003\b\u0003\t\u0003\t\u0007\t?\n\t\f\t\u000e\tB\u000b\t\u0003\n\u0006\nE\n\n\r\n\u000e\nF\u0003\u000b\u0006\u000bJ\n\u000b\r\u000b\u000e\u000bK\u0003\u000b\u0003\u000b\u0006\u000bP\n\u000b\r\u000b\u000e\u000bQ\u0003\f\u0006\fU\n\f\r\f\u000e\fV\u0003\f\u0003\f\u0003\r\u0003\r\u0003\u000e\u0003\u000e\u0003\u000f\u0003\u000f\u0003\u0010\u0003\u0010\u0002\u0002\u0011\u0003\u0003\u0005\u0004\u0007\u0005\t\u0006\u000b\u0007\r\b\u000f\t\u0011\n\u0013\u000b\u0015\f\u0017\r\u0019\u000e\u001b\u000f\u001d\u0010\u001f\u0011\u0003\u0002\u0005\u0004\u0002C\\c|\u0005\u00022;C\\c|\u0005\u0002\u000b\f\u000f\u000f\"\"f\u0002\u0003\u0003\u0002\u0002\u0002\u0002\u0005\u0003\u0002\u0002\u0002\u0002\u0007\u0003\u0002\u0002\u0002\u0002\t\u0003\u0002\u0002\u0002\u0002\u000b\u0003\u0002\u0002\u0002\u0002\r\u0003\u0002\u0002\u0002\u0002\u000f\u0003\u0002\u0002\u0002\u0002\u0011\u0003\u0002\u0002\u0002\u0002\u0013\u0003\u0002\u0002\u0002\u0002\u0015\u0003\u0002\u0002\u0002\u0002\u0017\u0003\u0002\u0002\u0002\u0002\u0019\u0003\u0002\u0002\u0002\u0002\u001b\u0003\u0002\u0002\u0002\u0002\u001d\u0003\u0002\u0002\u0002\u0002\u001f\u0003\u0002\u0002\u0002\u0003!\u0003\u0002\u0002\u0002\u0005#\u0003\u0002\u0002\u0002\u0007%\u0003\u0002\u0002\u0002\t+\u0003\u0002\u0002\u0002\u000b2\u0003\u0002\u0002\u0002\r7\u0003\u0002\u0002\u0002\u000f:\u0003\u0002\u0002\u0002\u0011<\u0003\u0002\u0002\u0002\u0013D\u0003\u0002\u0002\u0002\u0015I\u0003\u0002\u0002\u0002\u0017T\u0003\u0002\u0002\u0002\u0019Z\u0003\u0002\u0002\u0002\u001b\\\u0003\u0002\u0002\u0002\u001d^\u0003\u0002\u0002\u0002\u001f`\u0003\u0002\u0002\u0002!\"\u0007*\u0002\u0002\"\u0004\u0003\u0002\u0002\u0002#$\u0007+\u0002\u0002$\u0006\u0003\u0002\u0002\u0002%&\u0007e\u0002\u0002&'\u0007g\u0002\u0002'(\u0007k\u0002\u0002()\u0007n\u0002\u0002)*\u0007*\u0002\u0002*\b\u0003\u0002\u0002\u0002+,\u0007h\u0002\u0002,-\u0007n\u0002\u0002-.\u0007q\u0002\u0002./\u0007q\u0002\u0002/0\u0007t\u0002\u000201\u0007*\u0002\u00021\n\u0003\u0002\u0002\u000223\u0007e\u0002\u000234\u0007c\u0002\u000245\u0007r\u0002\u000256\u0007*\u0002\u00026\f\u0003\u0002\u0002\u000278\u0007%\u0002\u000289\u0007*\u0002\u00029\u000e\u0003\u0002\u0002\u0002:;\u0007.\u0002\u0002;\u0010\u0003\u0002\u0002\u0002<@\t\u0002\u0002\u0002=?\t\u0003\u0002\u0002>=\u0003\u0002\u0002\u0002?B\u0003\u0002\u0002\u0002@>\u0003\u0002\u0002\u0002@A\u0003\u0002\u0002\u0002A\u0012\u0003\u0002\u0002\u0002B@\u0003\u0002\u0002\u0002CE\u00042;\u0002DC\u0003\u0002\u0002\u0002EF\u0003\u0002\u0002\u0002FD\u0003\u0002\u0002\u0002FG\u0003\u0002\u0002\u0002G\u0014\u0003\u0002\u0002\u0002HJ\u00042;\u0002IH\u0003\u0002\u0002\u0002JK\u0003\u0002\u0002\u0002KI\u0003\u0002\u0002\u0002KL\u0003\u0002\u0002\u0002LM\u0003\u0002\u0002\u0002MO\u00070\u0002\u0002NP\u00042;\u0002ON\u0003\u0002\u0002\u0002PQ\u0003\u0002\u0002\u0002QO\u0003\u0002\u0002\u0002QR\u0003\u0002\u0002\u0002R\u0016\u0003\u0002\u0002\u0002SU\t\u0004\u0002\u0002TS\u0003\u0002\u0002\u0002UV\u0003\u0002\u0002\u0002VT\u0003\u0002\u0002\u0002VW\u0003\u0002\u0002\u0002WX\u0003\u0002\u0002\u0002XY\b\f\u0002\u0002Y\u0018\u0003\u0002\u0002\u0002Z[\u0007,\u0002\u0002[\u001a\u0003\u0002\u0002\u0002\\]\u00071\u0002\u0002]\u001c\u0003\u0002\u0002\u0002^_\u0007-\u0002\u0002_\u001e\u0003\u0002\u0002\u0002`a\u0007/\u0002\u0002a \u0003\u0002\u0002\u0002\b\u0002@FKQV\u0003\b\u0002\u0002".toCharArray());
      _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];

      for(i = 0; i < _ATN.getNumberOfDecisions(); ++i) {
         _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
      }

   }
}
