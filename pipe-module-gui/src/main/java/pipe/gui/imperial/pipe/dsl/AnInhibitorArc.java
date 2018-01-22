package pipe.gui.imperial.pipe.dsl;

import pipe.gui.imperial.pipe.models.petrinet.Arc;
import pipe.gui.imperial.pipe.models.petrinet.DiscretePlace;
import pipe.gui.imperial.pipe.models.petrinet.DiscreteTransition;
import pipe.gui.imperial.pipe.models.petrinet.InboundInhibitorArc;

import java.util.Map;

public final class AnInhibitorArc implements DSLCreator {
   private String source;
   private String target;

   public static AnInhibitorArc withSource(String source) {
      AnInhibitorArc anInhibitorArc = new AnInhibitorArc();
      anInhibitorArc.source = source;
      return anInhibitorArc;
   }

   public AnInhibitorArc andTarget(String target) {
      this.target = target;
      return this;
   }

   public Arc create(Map tokens, Map places, Map transitions, Map rateParameters) {
      return new InboundInhibitorArc((DiscretePlace)places.get(this.source), (DiscreteTransition)transitions.get(this.target));
   }
}
