package pipe.gui.imperial.pipe.models.petrinet;

import pipe.gui.imperial.state.State;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class InboundInhibitorArc extends InboundArc {
   public InboundInhibitorArc(DiscretePlace source, DiscreteTransition target) {
      super(source, target, new HashMap(), ArcType.INHIBITOR);
   }

   public final boolean canFire(PetriNet petriNet, State state) {
      Map tokens = state.getTokens(((DiscretePlace)this.getSource()).getId());
      Iterator i$ = tokens.values().iterator();

      Integer tokenCount;
      do {
         if (!i$.hasNext()) {
            return true;
         }

         tokenCount = (Integer)i$.next();
      } while(tokenCount.intValue() == 0);

      return false;
   }
}
