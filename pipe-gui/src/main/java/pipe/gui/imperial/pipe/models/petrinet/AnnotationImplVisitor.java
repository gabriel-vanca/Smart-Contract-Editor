package pipe.gui.imperial.pipe.models.petrinet;

import pipe.gui.imperial.pipe.models.petrinet.AnnotationImpl;
import pipe.gui.imperial.pipe.visitor.component.PetriNetComponentVisitor;

public interface AnnotationImplVisitor extends PetriNetComponentVisitor {
   void visit(AnnotationImpl var1);
}
