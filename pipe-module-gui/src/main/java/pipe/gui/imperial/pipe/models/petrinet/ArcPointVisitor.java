package pipe.gui.imperial.pipe.models.petrinet;

import pipe.gui.imperial.pipe.models.petrinet.ArcPoint;
import pipe.gui.imperial.pipe.visitor.component.PetriNetComponentVisitor;

public interface ArcPointVisitor extends PetriNetComponentVisitor {
   void visit(ArcPoint var1);
}
