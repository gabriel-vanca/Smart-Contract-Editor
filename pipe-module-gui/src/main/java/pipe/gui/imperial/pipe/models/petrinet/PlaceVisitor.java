package pipe.gui.imperial.pipe.models.petrinet;

import pipe.gui.imperial.pipe.exceptions.PetriNetComponentException;
import pipe.gui.imperial.pipe.visitor.component.PetriNetComponentVisitor;

public interface PlaceVisitor extends PetriNetComponentVisitor {
   void visit(DiscretePlace var1) throws PetriNetComponentException;
}
