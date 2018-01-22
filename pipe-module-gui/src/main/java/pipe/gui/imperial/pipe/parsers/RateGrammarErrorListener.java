package pipe.gui.imperial.pipe.parsers;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public final class RateGrammarErrorListener extends BaseErrorListener {
   private List errors = new LinkedList();

   public void syntaxError(Recognizer recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
      List stack = ((Parser)recognizer).getRuleInvocationStack();
      Collections.reverse(stack);
      this.errors.add(String.format("line %d:%d %s", line, charPositionInLine, msg));
   }

   public boolean hasErrors() {
      return !this.errors.isEmpty();
   }

   public List getErrors() {
      return this.errors;
   }
}
