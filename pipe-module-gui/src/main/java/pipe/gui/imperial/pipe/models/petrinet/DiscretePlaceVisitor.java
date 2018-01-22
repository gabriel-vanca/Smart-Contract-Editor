package pipe.gui.imperial.pipe.models.petrinet;

import pipe.gui.imperial.pipe.models.petrinet.DiscretePlace;
import pipe.gui.imperial.pipe.visitor.component.PetriNetComponentVisitor;

public interface DiscretePlaceVisitor extends PetriNetComponentVisitor {
   void visit(DiscretePlace var1);
}
