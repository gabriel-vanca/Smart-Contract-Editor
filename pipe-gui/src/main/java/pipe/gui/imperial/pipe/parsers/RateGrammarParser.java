package pipe.gui.imperial.pipe.parsers;

import java.util.List;
import org.antlr.v4.runtime.FailedPredicateException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

public class RateGrammarParser extends Parser {
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
   public static final int RULE_program = 0;
   public static final int RULE_expression = 1;
   public static final int RULE_capacity = 2;
   public static final int RULE_token_number = 3;
   public static final int RULE_token_color_number = 4;
   public static final String[] ruleNames;
   private static final String[] _LITERAL_NAMES;
   private static final String[] _SYMBOLIC_NAMES;
   public static final Vocabulary VOCABULARY;
   /** @deprecated */
   @Deprecated
   public static final String[] tokenNames;
   public static final String _serializedATN = "\u0003а훑舆괭䐗껱趀ꫝ\u0003\u0011<\u0004\u0002\t\u0002\u0004\u0003\t\u0003\u0004\u0004\t\u0004\u0004\u0005\t\u0005\u0004\u0006\t\u0006\u0003\u0002\u0003\u0002\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0005\u0003!\n\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0007\u0003)\n\u0003\f\u0003\u000e\u0003,\u000b\u0003\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0002\u0003\u0004\u0007\u0002\u0004\u0006\b\n\u0002\u0004\u0003\u0002\u000e\u000f\u0003\u0002\u0010\u0011?\u0002\f\u0003\u0002\u0002\u0002\u0004 \u0003\u0002\u0002\u0002\u0006-\u0003\u0002\u0002\u0002\b1\u0003\u0002\u0002\u0002\n5\u0003\u0002\u0002\u0002\f\r\u0005\u0004\u0003\u0002\r\u0003\u0003\u0002\u0002\u0002\u000e\u000f\b\u0003\u0001\u0002\u000f\u0010\u0007\u0003\u0002\u0002\u0010\u0011\u0005\u0004\u0003\u0002\u0011\u0012\u0007\u0004\u0002\u0002\u0012!\u0003\u0002\u0002\u0002\u0013\u0014\u0007\u0005\u0002\u0002\u0014\u0015\u0005\u0004\u0003\u0002\u0015\u0016\u0007\u0004\u0002\u0002\u0016!\u0003\u0002\u0002\u0002\u0017\u0018\u0007\u0006\u0002\u0002\u0018\u0019\u0005\u0004\u0003\u0002\u0019\u001a\u0007\u0004\u0002\u0002\u001a!\u0003\u0002\u0002\u0002\u001b!\u0005\u0006\u0004\u0002\u001c!\u0005\b\u0005\u0002\u001d!\u0005\n\u0006\u0002\u001e!\u0007\u000b\u0002\u0002\u001f!\u0007\f\u0002\u0002 \u000e\u0003\u0002\u0002\u0002 \u0013\u0003\u0002\u0002\u0002 \u0017\u0003\u0002\u0002\u0002 \u001b\u0003\u0002\u0002\u0002 \u001c\u0003\u0002\u0002\u0002 \u001d\u0003\u0002\u0002\u0002 \u001e\u0003\u0002\u0002\u0002 \u001f\u0003\u0002\u0002\u0002!*\u0003\u0002\u0002\u0002\"#\f\u000b\u0002\u0002#$\t\u0002\u0002\u0002$)\u0005\u0004\u0003\f%&\f\n\u0002\u0002&'\t\u0003\u0002\u0002')\u0005\u0004\u0003\u000b(\"\u0003\u0002\u0002\u0002(%\u0003\u0002\u0002\u0002),\u0003\u0002\u0002\u0002*(\u0003\u0002\u0002\u0002*+\u0003\u0002\u0002\u0002+\u0005\u0003\u0002\u0002\u0002,*\u0003\u0002\u0002\u0002-.\u0007\u0007\u0002\u0002./\u0007\n\u0002\u0002/0\u0007\u0004\u0002\u00020\u0007\u0003\u0002\u0002\u000212\u0007\b\u0002\u000223\u0007\n\u0002\u000234\u0007\u0004\u0002\u00024\t\u0003\u0002\u0002\u000256\u0007\b\u0002\u000267\u0007\n\u0002\u000278\u0007\t\u0002\u000289\u0007\n\u0002\u00029:\u0007\u0004\u0002\u0002:\u000b\u0003\u0002\u0002\u0002\u0005 (*";
   public static final ATN _ATN;

   /** @deprecated */
   @Deprecated
   public String[] getTokenNames() {
      return tokenNames;
   }

   public Vocabulary getVocabulary() {
      return VOCABULARY;
   }

   public String getGrammarFileName() {
      return "RateGrammar.g4";
   }

   public String[] getRuleNames() {
      return ruleNames;
   }

   public String getSerializedATN() {
      return "\u0003а훑舆괭䐗껱趀ꫝ\u0003\u0011<\u0004\u0002\t\u0002\u0004\u0003\t\u0003\u0004\u0004\t\u0004\u0004\u0005\t\u0005\u0004\u0006\t\u0006\u0003\u0002\u0003\u0002\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0005\u0003!\n\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0007\u0003)\n\u0003\f\u0003\u000e\u0003,\u000b\u0003\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0002\u0003\u0004\u0007\u0002\u0004\u0006\b\n\u0002\u0004\u0003\u0002\u000e\u000f\u0003\u0002\u0010\u0011?\u0002\f\u0003\u0002\u0002\u0002\u0004 \u0003\u0002\u0002\u0002\u0006-\u0003\u0002\u0002\u0002\b1\u0003\u0002\u0002\u0002\n5\u0003\u0002\u0002\u0002\f\r\u0005\u0004\u0003\u0002\r\u0003\u0003\u0002\u0002\u0002\u000e\u000f\b\u0003\u0001\u0002\u000f\u0010\u0007\u0003\u0002\u0002\u0010\u0011\u0005\u0004\u0003\u0002\u0011\u0012\u0007\u0004\u0002\u0002\u0012!\u0003\u0002\u0002\u0002\u0013\u0014\u0007\u0005\u0002\u0002\u0014\u0015\u0005\u0004\u0003\u0002\u0015\u0016\u0007\u0004\u0002\u0002\u0016!\u0003\u0002\u0002\u0002\u0017\u0018\u0007\u0006\u0002\u0002\u0018\u0019\u0005\u0004\u0003\u0002\u0019\u001a\u0007\u0004\u0002\u0002\u001a!\u0003\u0002\u0002\u0002\u001b!\u0005\u0006\u0004\u0002\u001c!\u0005\b\u0005\u0002\u001d!\u0005\n\u0006\u0002\u001e!\u0007\u000b\u0002\u0002\u001f!\u0007\f\u0002\u0002 \u000e\u0003\u0002\u0002\u0002 \u0013\u0003\u0002\u0002\u0002 \u0017\u0003\u0002\u0002\u0002 \u001b\u0003\u0002\u0002\u0002 \u001c\u0003\u0002\u0002\u0002 \u001d\u0003\u0002\u0002\u0002 \u001e\u0003\u0002\u0002\u0002 \u001f\u0003\u0002\u0002\u0002!*\u0003\u0002\u0002\u0002\"#\f\u000b\u0002\u0002#$\t\u0002\u0002\u0002$)\u0005\u0004\u0003\f%&\f\n\u0002\u0002&'\t\u0003\u0002\u0002')\u0005\u0004\u0003\u000b(\"\u0003\u0002\u0002\u0002(%\u0003\u0002\u0002\u0002),\u0003\u0002\u0002\u0002*(\u0003\u0002\u0002\u0002*+\u0003\u0002\u0002\u0002+\u0005\u0003\u0002\u0002\u0002,*\u0003\u0002\u0002\u0002-.\u0007\u0007\u0002\u0002./\u0007\n\u0002\u0002/0\u0007\u0004\u0002\u00020\u0007\u0003\u0002\u0002\u000212\u0007\b\u0002\u000223\u0007\n\u0002\u000234\u0007\u0004\u0002\u00024\t\u0003\u0002\u0002\u000256\u0007\b\u0002\u000267\u0007\n\u0002\u000278\u0007\t\u0002\u000289\u0007\n\u0002\u00029:\u0007\u0004\u0002\u0002:\u000b\u0003\u0002\u0002\u0002\u0005 (*";
   }

   public ATN getATN() {
      return _ATN;
   }

   public RateGrammarParser(TokenStream input) {
      super(input);
      this._interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
   }

   public final RateGrammarParser.ProgramContext program() throws RecognitionException {
      RateGrammarParser.ProgramContext _localctx = new RateGrammarParser.ProgramContext(this._ctx, this.getState());
      this.enterRule(_localctx, 0, 0);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(10);
         this.expression(0);
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final RateGrammarParser.ExpressionContext expression() throws RecognitionException {
      return this.expression(0);
   }

   private RateGrammarParser.ExpressionContext expression(int _p) throws RecognitionException {
      ParserRuleContext _parentctx = this._ctx;
      int _parentState = this.getState();
      RateGrammarParser.ExpressionContext _localctx = new RateGrammarParser.ExpressionContext(this._ctx, _parentState);
      int _startState = 2;
      this.enterRecursionRule((ParserRuleContext)_localctx, 2, 1, _p);

      try {
         this.enterOuterAlt((ParserRuleContext)_localctx, 1);
         this.setState(30);
         this._errHandler.sync(this);
         switch(((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 0, this._ctx)) {
         case 1:
            _localctx = new RateGrammarParser.ParenExpressionContext((RateGrammarParser.ExpressionContext)_localctx);
            this._ctx = (ParserRuleContext)_localctx;
            this.setState(13);
            this.match(1);
            this.setState(14);
            this.expression(0);
            this.setState(15);
            this.match(2);
            break;
         case 2:
            _localctx = new RateGrammarParser.CeilContext((RateGrammarParser.ExpressionContext)_localctx);
            this._ctx = (ParserRuleContext)_localctx;
            this.setState(17);
            this.match(3);
            this.setState(18);
            this.expression(0);
            this.setState(19);
            this.match(2);
            break;
         case 3:
            _localctx = new RateGrammarParser.FloorContext((RateGrammarParser.ExpressionContext)_localctx);
            this._ctx = (ParserRuleContext)_localctx;
            this.setState(21);
            this.match(4);
            this.setState(22);
            this.expression(0);
            this.setState(23);
            this.match(2);
            break;
         case 4:
            _localctx = new RateGrammarParser.PlaceCapacityContext((RateGrammarParser.ExpressionContext)_localctx);
            this._ctx = (ParserRuleContext)_localctx;
            this.setState(25);
            this.capacity();
            break;
         case 5:
            _localctx = new RateGrammarParser.PlaceTokensContext((RateGrammarParser.ExpressionContext)_localctx);
            this._ctx = (ParserRuleContext)_localctx;
            this.setState(26);
            this.token_number();
            break;
         case 6:
            _localctx = new RateGrammarParser.PlaceColorTokensContext((RateGrammarParser.ExpressionContext)_localctx);
            this._ctx = (ParserRuleContext)_localctx;
            this.setState(27);
            this.token_color_number();
            break;
         case 7:
            _localctx = new RateGrammarParser.IntegerContext((RateGrammarParser.ExpressionContext)_localctx);
            this._ctx = (ParserRuleContext)_localctx;
            this.setState(28);
            this.match(9);
            break;
         case 8:
            _localctx = new RateGrammarParser.DoubleContext((RateGrammarParser.ExpressionContext)_localctx);
            this._ctx = (ParserRuleContext)_localctx;
            this.setState(29);
            this.match(10);
         }

         this._ctx.stop = this._input.LT(-1);
         this.setState(40);
         this._errHandler.sync(this);

         for(int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 2, this._ctx); _alt != 2 && _alt != 0; _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 2, this._ctx)) {
            if (_alt == 1) {
               if (this._parseListeners != null) {
                  this.triggerExitRuleEvent();
               }

               this.setState(38);
               this._errHandler.sync(this);
               int _la;
               switch(((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 1, this._ctx)) {
               case 1:
                  _localctx = new RateGrammarParser.MultOrDivContext(new RateGrammarParser.ExpressionContext(_parentctx, _parentState));
                  this.pushNewRecursionContext((ParserRuleContext)_localctx, _startState, 1);
                  this.setState(32);
                  if (!this.precpred(this._ctx, 9)) {
                     throw new FailedPredicateException(this, "precpred(_ctx, 9)");
                  }

                  this.setState(33);
                  ((RateGrammarParser.MultOrDivContext)_localctx).op = this._input.LT(1);
                  _la = this._input.LA(1);
                  if (_la != 12 && _la != 13) {
                     ((RateGrammarParser.MultOrDivContext)_localctx).op = this._errHandler.recoverInline(this);
                  } else {
                     this.consume();
                  }

                  this.setState(34);
                  this.expression(10);
                  break;
               case 2:
                  _localctx = new RateGrammarParser.AddOrSubtractContext(new RateGrammarParser.ExpressionContext(_parentctx, _parentState));
                  this.pushNewRecursionContext((ParserRuleContext)_localctx, _startState, 1);
                  this.setState(35);
                  if (!this.precpred(this._ctx, 8)) {
                     throw new FailedPredicateException(this, "precpred(_ctx, 8)");
                  }

                  this.setState(36);
                  ((RateGrammarParser.AddOrSubtractContext)_localctx).op = this._input.LT(1);
                  _la = this._input.LA(1);
                  if (_la != 14 && _la != 15) {
                     ((RateGrammarParser.AddOrSubtractContext)_localctx).op = this._errHandler.recoverInline(this);
                  } else {
                     this.consume();
                  }

                  this.setState(37);
                  this.expression(9);
               }
            }

            this.setState(42);
            this._errHandler.sync(this);
         }
      } catch (RecognitionException var12) {
         ((RateGrammarParser.ExpressionContext)_localctx).exception = var12;
         this._errHandler.reportError(this, var12);
         this._errHandler.recover(this, var12);
      } finally {
         this.unrollRecursionContexts(_parentctx);
      }

      return (RateGrammarParser.ExpressionContext)_localctx;
   }

   public final RateGrammarParser.CapacityContext capacity() throws RecognitionException {
      RateGrammarParser.CapacityContext _localctx = new RateGrammarParser.CapacityContext(this._ctx, this.getState());
      this.enterRule(_localctx, 4, 2);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(43);
         this.match(5);
         this.setState(44);
         this.match(8);
         this.setState(45);
         this.match(2);
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final RateGrammarParser.Token_numberContext token_number() throws RecognitionException {
      RateGrammarParser.Token_numberContext _localctx = new RateGrammarParser.Token_numberContext(this._ctx, this.getState());
      this.enterRule(_localctx, 6, 3);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(47);
         this.match(6);
         this.setState(48);
         this.match(8);
         this.setState(49);
         this.match(2);
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final RateGrammarParser.Token_color_numberContext token_color_number() throws RecognitionException {
      RateGrammarParser.Token_color_numberContext _localctx = new RateGrammarParser.Token_color_numberContext(this._ctx, this.getState());
      this.enterRule(_localctx, 8, 4);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(51);
         this.match(6);
         this.setState(52);
         this.match(8);
         this.setState(53);
         this.match(7);
         this.setState(54);
         this.match(8);
         this.setState(55);
         this.match(2);
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
      switch(ruleIndex) {
      case 1:
         return this.expression_sempred((RateGrammarParser.ExpressionContext)_localctx, predIndex);
      default:
         return true;
      }
   }

   private boolean expression_sempred(RateGrammarParser.ExpressionContext _localctx, int predIndex) {
      switch(predIndex) {
      case 0:
         return this.precpred(this._ctx, 9);
      case 1:
         return this.precpred(this._ctx, 8);
      default:
         return true;
      }
   }

   static {
      RuntimeMetaData.checkVersion("4.5.3", "4.5.3");
      _sharedContextCache = new PredictionContextCache();
      ruleNames = new String[]{"program", "expression", "capacity", "token_number", "token_color_number"};
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

      _ATN = (new ATNDeserializer()).deserialize("\u0003а훑舆괭䐗껱趀ꫝ\u0003\u0011<\u0004\u0002\t\u0002\u0004\u0003\t\u0003\u0004\u0004\t\u0004\u0004\u0005\t\u0005\u0004\u0006\t\u0006\u0003\u0002\u0003\u0002\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0005\u0003!\n\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0007\u0003)\n\u0003\f\u0003\u000e\u0003,\u000b\u0003\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0002\u0003\u0004\u0007\u0002\u0004\u0006\b\n\u0002\u0004\u0003\u0002\u000e\u000f\u0003\u0002\u0010\u0011?\u0002\f\u0003\u0002\u0002\u0002\u0004 \u0003\u0002\u0002\u0002\u0006-\u0003\u0002\u0002\u0002\b1\u0003\u0002\u0002\u0002\n5\u0003\u0002\u0002\u0002\f\r\u0005\u0004\u0003\u0002\r\u0003\u0003\u0002\u0002\u0002\u000e\u000f\b\u0003\u0001\u0002\u000f\u0010\u0007\u0003\u0002\u0002\u0010\u0011\u0005\u0004\u0003\u0002\u0011\u0012\u0007\u0004\u0002\u0002\u0012!\u0003\u0002\u0002\u0002\u0013\u0014\u0007\u0005\u0002\u0002\u0014\u0015\u0005\u0004\u0003\u0002\u0015\u0016\u0007\u0004\u0002\u0002\u0016!\u0003\u0002\u0002\u0002\u0017\u0018\u0007\u0006\u0002\u0002\u0018\u0019\u0005\u0004\u0003\u0002\u0019\u001a\u0007\u0004\u0002\u0002\u001a!\u0003\u0002\u0002\u0002\u001b!\u0005\u0006\u0004\u0002\u001c!\u0005\b\u0005\u0002\u001d!\u0005\n\u0006\u0002\u001e!\u0007\u000b\u0002\u0002\u001f!\u0007\f\u0002\u0002 \u000e\u0003\u0002\u0002\u0002 \u0013\u0003\u0002\u0002\u0002 \u0017\u0003\u0002\u0002\u0002 \u001b\u0003\u0002\u0002\u0002 \u001c\u0003\u0002\u0002\u0002 \u001d\u0003\u0002\u0002\u0002 \u001e\u0003\u0002\u0002\u0002 \u001f\u0003\u0002\u0002\u0002!*\u0003\u0002\u0002\u0002\"#\f\u000b\u0002\u0002#$\t\u0002\u0002\u0002$)\u0005\u0004\u0003\f%&\f\n\u0002\u0002&'\t\u0003\u0002\u0002')\u0005\u0004\u0003\u000b(\"\u0003\u0002\u0002\u0002(%\u0003\u0002\u0002\u0002),\u0003\u0002\u0002\u0002*(\u0003\u0002\u0002\u0002*+\u0003\u0002\u0002\u0002+\u0005\u0003\u0002\u0002\u0002,*\u0003\u0002\u0002\u0002-.\u0007\u0007\u0002\u0002./\u0007\n\u0002\u0002/0\u0007\u0004\u0002\u00020\u0007\u0003\u0002\u0002\u000212\u0007\b\u0002\u000223\u0007\n\u0002\u000234\u0007\u0004\u0002\u00024\t\u0003\u0002\u0002\u000256\u0007\b\u0002\u000267\u0007\n\u0002\u000278\u0007\t\u0002\u000289\u0007\n\u0002\u00029:\u0007\u0004\u0002\u0002:\u000b\u0003\u0002\u0002\u0002\u0005 (*".toCharArray());
      _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];

      for(i = 0; i < _ATN.getNumberOfDecisions(); ++i) {
         _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
      }

   }

   public static class Token_color_numberContext extends ParserRuleContext {
      public List ID() {
         return this.getTokens(8);
      }

      public TerminalNode ID(int i) {
         return this.getToken(8, i);
      }

      public Token_color_numberContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 4;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).enterToken_color_number(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).exitToken_color_number(this);
         }

      }

      public Object accept(ParseTreeVisitor visitor) {
         return visitor instanceof RateGrammarVisitor ? ((RateGrammarVisitor)visitor).visitToken_color_number(this) : visitor.visitChildren(this);
      }
   }

   public static class Token_numberContext extends ParserRuleContext {
      public TerminalNode ID() {
         return this.getToken(8, 0);
      }

      public Token_numberContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 3;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).enterToken_number(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).exitToken_number(this);
         }

      }

      public Object accept(ParseTreeVisitor visitor) {
         return visitor instanceof RateGrammarVisitor ? ((RateGrammarVisitor)visitor).visitToken_number(this) : visitor.visitChildren(this);
      }
   }

   public static class CapacityContext extends ParserRuleContext {
      public TerminalNode ID() {
         return this.getToken(8, 0);
      }

      public CapacityContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 2;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).enterCapacity(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).exitCapacity(this);
         }

      }

      public Object accept(ParseTreeVisitor visitor) {
         return visitor instanceof RateGrammarVisitor ? ((RateGrammarVisitor)visitor).visitCapacity(this) : visitor.visitChildren(this);
      }
   }

   public static class PlaceCapacityContext extends RateGrammarParser.ExpressionContext {
      public RateGrammarParser.CapacityContext capacity() {
         return (RateGrammarParser.CapacityContext)this.getRuleContext(RateGrammarParser.CapacityContext.class, 0);
      }

      public PlaceCapacityContext(RateGrammarParser.ExpressionContext ctx) {
         this.copyFrom(ctx);
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).enterPlaceCapacity(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).exitPlaceCapacity(this);
         }

      }

      public Object accept(ParseTreeVisitor visitor) {
         return visitor instanceof RateGrammarVisitor ? ((RateGrammarVisitor)visitor).visitPlaceCapacity(this) : visitor.visitChildren(this);
      }
   }

   public static class DoubleContext extends RateGrammarParser.ExpressionContext {
      public TerminalNode DOUBLE() {
         return this.getToken(10, 0);
      }

      public DoubleContext(RateGrammarParser.ExpressionContext ctx) {
         this.copyFrom(ctx);
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).enterDouble(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).exitDouble(this);
         }

      }

      public Object accept(ParseTreeVisitor visitor) {
         return visitor instanceof RateGrammarVisitor ? ((RateGrammarVisitor)visitor).visitDouble(this) : visitor.visitChildren(this);
      }
   }

   public static class ParenExpressionContext extends RateGrammarParser.ExpressionContext {
      public RateGrammarParser.ExpressionContext expression() {
         return (RateGrammarParser.ExpressionContext)this.getRuleContext(RateGrammarParser.ExpressionContext.class, 0);
      }

      public ParenExpressionContext(RateGrammarParser.ExpressionContext ctx) {
         this.copyFrom(ctx);
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).enterParenExpression(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).exitParenExpression(this);
         }

      }

      public Object accept(ParseTreeVisitor visitor) {
         return visitor instanceof RateGrammarVisitor ? ((RateGrammarVisitor)visitor).visitParenExpression(this) : visitor.visitChildren(this);
      }
   }

   public static class PlaceColorTokensContext extends RateGrammarParser.ExpressionContext {
      public RateGrammarParser.Token_color_numberContext token_color_number() {
         return (RateGrammarParser.Token_color_numberContext)this.getRuleContext(RateGrammarParser.Token_color_numberContext.class, 0);
      }

      public PlaceColorTokensContext(RateGrammarParser.ExpressionContext ctx) {
         this.copyFrom(ctx);
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).enterPlaceColorTokens(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).exitPlaceColorTokens(this);
         }

      }

      public Object accept(ParseTreeVisitor visitor) {
         return visitor instanceof RateGrammarVisitor ? ((RateGrammarVisitor)visitor).visitPlaceColorTokens(this) : visitor.visitChildren(this);
      }
   }

   public static class PlaceTokensContext extends RateGrammarParser.ExpressionContext {
      public RateGrammarParser.Token_numberContext token_number() {
         return (RateGrammarParser.Token_numberContext)this.getRuleContext(RateGrammarParser.Token_numberContext.class, 0);
      }

      public PlaceTokensContext(RateGrammarParser.ExpressionContext ctx) {
         this.copyFrom(ctx);
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).enterPlaceTokens(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).exitPlaceTokens(this);
         }

      }

      public Object accept(ParseTreeVisitor visitor) {
         return visitor instanceof RateGrammarVisitor ? ((RateGrammarVisitor)visitor).visitPlaceTokens(this) : visitor.visitChildren(this);
      }
   }

   public static class CeilContext extends RateGrammarParser.ExpressionContext {
      public RateGrammarParser.ExpressionContext expression() {
         return (RateGrammarParser.ExpressionContext)this.getRuleContext(RateGrammarParser.ExpressionContext.class, 0);
      }

      public CeilContext(RateGrammarParser.ExpressionContext ctx) {
         this.copyFrom(ctx);
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).enterCeil(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).exitCeil(this);
         }

      }

      public Object accept(ParseTreeVisitor visitor) {
         return visitor instanceof RateGrammarVisitor ? ((RateGrammarVisitor)visitor).visitCeil(this) : visitor.visitChildren(this);
      }
   }

   public static class IntegerContext extends RateGrammarParser.ExpressionContext {
      public TerminalNode INT() {
         return this.getToken(9, 0);
      }

      public IntegerContext(RateGrammarParser.ExpressionContext ctx) {
         this.copyFrom(ctx);
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).enterInteger(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).exitInteger(this);
         }

      }

      public Object accept(ParseTreeVisitor visitor) {
         return visitor instanceof RateGrammarVisitor ? ((RateGrammarVisitor)visitor).visitInteger(this) : visitor.visitChildren(this);
      }
   }

   public static class FloorContext extends RateGrammarParser.ExpressionContext {
      public RateGrammarParser.ExpressionContext expression() {
         return (RateGrammarParser.ExpressionContext)this.getRuleContext(RateGrammarParser.ExpressionContext.class, 0);
      }

      public FloorContext(RateGrammarParser.ExpressionContext ctx) {
         this.copyFrom(ctx);
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).enterFloor(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).exitFloor(this);
         }

      }

      public Object accept(ParseTreeVisitor visitor) {
         return visitor instanceof RateGrammarVisitor ? ((RateGrammarVisitor)visitor).visitFloor(this) : visitor.visitChildren(this);
      }
   }

   public static class MultOrDivContext extends RateGrammarParser.ExpressionContext {
      public Token op;

      public List expression() {
         return this.getRuleContexts(RateGrammarParser.ExpressionContext.class);
      }

      public RateGrammarParser.ExpressionContext expression(int i) {
         return (RateGrammarParser.ExpressionContext)this.getRuleContext(RateGrammarParser.ExpressionContext.class, i);
      }

      public MultOrDivContext(RateGrammarParser.ExpressionContext ctx) {
         this.copyFrom(ctx);
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).enterMultOrDiv(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).exitMultOrDiv(this);
         }

      }

      public Object accept(ParseTreeVisitor visitor) {
         return visitor instanceof RateGrammarVisitor ? ((RateGrammarVisitor)visitor).visitMultOrDiv(this) : visitor.visitChildren(this);
      }
   }

   public static class AddOrSubtractContext extends RateGrammarParser.ExpressionContext {
      public Token op;

      public List expression() {
         return this.getRuleContexts(RateGrammarParser.ExpressionContext.class);
      }

      public RateGrammarParser.ExpressionContext expression(int i) {
         return (RateGrammarParser.ExpressionContext)this.getRuleContext(RateGrammarParser.ExpressionContext.class, i);
      }

      public AddOrSubtractContext(RateGrammarParser.ExpressionContext ctx) {
         this.copyFrom(ctx);
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).enterAddOrSubtract(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).exitAddOrSubtract(this);
         }

      }

      public Object accept(ParseTreeVisitor visitor) {
         return visitor instanceof RateGrammarVisitor ? ((RateGrammarVisitor)visitor).visitAddOrSubtract(this) : visitor.visitChildren(this);
      }
   }

   public static class ExpressionContext extends ParserRuleContext {
      public ExpressionContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 1;
      }

      public ExpressionContext() {
      }

      public void copyFrom(RateGrammarParser.ExpressionContext ctx) {
         super.copyFrom(ctx);
      }
   }

   public static class ProgramContext extends ParserRuleContext {
      public RateGrammarParser.ExpressionContext expression() {
         return (RateGrammarParser.ExpressionContext)this.getRuleContext(RateGrammarParser.ExpressionContext.class, 0);
      }

      public ProgramContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 0;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).enterProgram(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof RateGrammarListener) {
            ((RateGrammarListener)listener).exitProgram(this);
         }

      }

      public Object accept(ParseTreeVisitor visitor) {
         return visitor instanceof RateGrammarVisitor ? ((RateGrammarVisitor)visitor).visitProgram(this) : visitor.visitChildren(this);
      }
   }
}
