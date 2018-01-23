package pipe.gui.imperial.pipe.models.petrinet;

import pipe.gui.imperial.pipe.visitor.component.PetriNetComponentVisitor;

public interface TransitionVisitor extends PetriNetComponentVisitor {
   void visit(DiscreteTransition var1);
}
