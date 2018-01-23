package pipe.gui.imperial.pipe.visitor;

import pipe.gui.imperial.pipe.models.petrinet.DiscretePlace;
import pipe.gui.imperial.pipe.models.petrinet.DiscretePlaceVisitor;

public final class PlaceCloner implements DiscretePlaceVisitor {
   public DiscretePlace cloned = null;

   public void visit(DiscretePlace discretePlace) {
      this.cloned = new DiscretePlace(discretePlace);
   }
}
