package pipe.gui.imperial.pipe.models.petrinet;

import pipe.gui.imperial.pipe.visitor.component.PetriNetComponentVisitor;

public interface ArcVisitor extends PetriNetComponentVisitor {
   void visit(InboundArc var1);

   void visit(OutboundArc var1);
}
