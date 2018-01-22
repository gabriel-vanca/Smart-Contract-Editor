package pipe.gui.imperial.pipe.parsers;

import java.util.HashSet;
import java.util.Set;
import org.antlr.v4.runtime.misc.NotNull;

public class ComponentListener extends RateGrammarBaseListener {
   private Set componentIds = new HashSet();

   public void exitToken_number(@NotNull RateGrammarParser.Token_numberContext ctx) {
      this.componentIds.add(ctx.ID().getText());
   }

   public Set getComponentIds() {
      return this.componentIds;
   }
}
