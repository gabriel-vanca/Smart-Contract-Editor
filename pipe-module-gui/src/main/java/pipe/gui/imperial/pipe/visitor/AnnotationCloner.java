package pipe.gui.imperial.pipe.visitor;

import pipe.gui.imperial.pipe.models.petrinet.Annotation;
import pipe.gui.imperial.pipe.models.petrinet.AnnotationImpl;
import pipe.gui.imperial.pipe.models.petrinet.AnnotationImplVisitor;

public class AnnotationCloner implements AnnotationImplVisitor {
   public Annotation cloned;

   public void visit(AnnotationImpl annotation) {
      this.cloned = new AnnotationImpl(annotation);
   }
}
