package pipe.gui.imperial.pipe.models.petrinet;

import pipe.gui.imperial.pipe.models.petrinet.Rate;
import pipe.gui.imperial.pipe.visitor.component.PetriNetComponentVisitor;

public interface RateVisitor extends PetriNetComponentVisitor {
   void visit(Rate var1);
}
