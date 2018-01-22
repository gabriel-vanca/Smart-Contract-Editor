package pipe.gui.imperial.pipe.visitor.connectable.arc;

import pipe.gui.imperial.pipe.models.petrinet.Connectable;
import pipe.gui.imperial.pipe.models.petrinet.PlaceVisitor;
import pipe.gui.imperial.pipe.models.petrinet.TransitionVisitor;

public interface ArcSourceVisitor extends PlaceVisitor, TransitionVisitor {
   boolean canStart(Connectable var1);
}
