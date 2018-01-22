package pipe.gui.imperial.pipe.models.petrinet;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class AbstractPetriNetPubSub {
   protected PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

   public void addPropertyChangeListener(PropertyChangeListener listener) {
      this.changeSupport.addPropertyChangeListener(listener);
   }

   public void removePropertyChangeListener(PropertyChangeListener listener) {
      this.changeSupport.removePropertyChangeListener(listener);
   }
}
