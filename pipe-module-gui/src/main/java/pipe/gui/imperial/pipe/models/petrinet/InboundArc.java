package pipe.gui.imperial.pipe.models.petrinet;

import pipe.gui.imperial.pipe.visitor.component.PetriNetComponentVisitor;

import java.util.Map;

public abstract class InboundArc extends AbstractArc {
   public InboundArc(DiscretePlace source, DiscreteTransition target, Map tokenWeights, ArcType type) {
      super(source, target, tokenWeights, type);
   }

   public final void accept(PetriNetComponentVisitor visitor) {
      if (visitor instanceof pipe.gui.imperial.pipe.models.petrinet.ArcVisitor) {
         ((ArcVisitor)visitor).visit(this);
      }

   }
}
