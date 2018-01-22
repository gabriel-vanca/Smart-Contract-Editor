package pipe.gui.imperial.pipe.parsers;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public final class GrammarUtils {
   public static ParseTree parse(String expression, ANTLRErrorListener... errorListeners) {
      CharStream input = new ANTLRInputStream(expression);
      RateGrammarLexer lexer = new RateGrammarLexer(input);
      TokenStream tokens = new CommonTokenStream(lexer);
      RateGrammarParser parser = new RateGrammarParser(tokens);
      parser.removeErrorListeners();
      ANTLRErrorListener[] arr$ = errorListeners;
      int len$ = errorListeners.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         ANTLRErrorListener errorListener = arr$[i$];
         parser.addErrorListener(errorListener);
      }

      return parser.program();
   }
}
