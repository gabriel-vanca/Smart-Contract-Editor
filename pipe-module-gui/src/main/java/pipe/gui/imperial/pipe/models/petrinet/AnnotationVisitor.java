package pipe.gui.imperial.pipe.models.petrinet;

import pipe.gui.imperial.pipe.models.petrinet.Annotation;
import pipe.gui.imperial.pipe.visitor.component.PetriNetComponentVisitor;

public interface AnnotationVisitor extends PetriNetComponentVisitor {
   void visit(Annotation var1);
}
