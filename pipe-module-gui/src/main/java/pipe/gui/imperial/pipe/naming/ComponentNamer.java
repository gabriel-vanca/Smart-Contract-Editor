package pipe.gui.imperial.pipe.naming;

import pipe.gui.imperial.pipe.models.petrinet.PetriNet;
import pipe.gui.imperial.pipe.models.petrinet.PetriNetComponent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ComponentNamer extends AbstractUniqueNamer {
   protected final PetriNet petriNet;

   protected ComponentNamer(PetriNet petriNet, String namePrefix, String newChangeMessage, String deleteChangeMessage) {
      super(namePrefix);
      this.petriNet = petriNet;
      this.observeChanges(petriNet, newChangeMessage, deleteChangeMessage);
   }

   private void observeChanges(PetriNet petriNet, final String newChangeMessage, final String deleteChangeMessage) {
      PropertyChangeListener listener = new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            String name = propertyChangeEvent.getPropertyName();
            PetriNetComponent component;
            if (name.equals(newChangeMessage)) {
               component = (PetriNetComponent)propertyChangeEvent.getNewValue();
               component.addPropertyChangeListener(ComponentNamer.this.nameListener);
               ComponentNamer.this.names.add(component.getId());
            } else if (name.equals(deleteChangeMessage)) {
               component = (PetriNetComponent)propertyChangeEvent.getOldValue();
               component.removePropertyChangeListener(ComponentNamer.this.nameListener);
               ComponentNamer.this.names.remove(component.getId());
            }

         }
      };
      petriNet.addPropertyChangeListener(listener);
   }
}
