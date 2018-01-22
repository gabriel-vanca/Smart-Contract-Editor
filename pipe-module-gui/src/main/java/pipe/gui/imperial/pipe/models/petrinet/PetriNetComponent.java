package pipe.gui.imperial.pipe.models.petrinet;

import java.beans.PropertyChangeListener;
import pipe.gui.imperial.pipe.exceptions.PetriNetComponentException;
import pipe.gui.imperial.pipe.visitor.component.PetriNetComponentVisitor;

public interface PetriNetComponent {
   String ID_CHANGE_MESSAGE = "id";
   String NAME_CHANGE_MESSAGE = "name";

   boolean isSelectable();

   boolean isDraggable();

   void accept(PetriNetComponentVisitor var1) throws PetriNetComponentException;

   String getId();

   void setId(String var1);

   void addPropertyChangeListener(PropertyChangeListener var1);

   void removePropertyChangeListener(PropertyChangeListener var1);
}
