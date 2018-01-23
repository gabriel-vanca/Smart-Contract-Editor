package pipe.gui.imperial.pipe.models.petrinet;

import pipe.gui.imperial.pipe.models.petrinet.FunctionalRateParameter;
import pipe.gui.imperial.pipe.visitor.component.PetriNetComponentVisitor;

public interface FunctionalRateParameterVisitor extends PetriNetComponentVisitor {
   void visit(FunctionalRateParameter var1);
}
