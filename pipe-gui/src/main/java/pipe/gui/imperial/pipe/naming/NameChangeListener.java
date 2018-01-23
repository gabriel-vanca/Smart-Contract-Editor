package pipe.gui.imperial.pipe.naming;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;

public final class NameChangeListener implements PropertyChangeListener {
   private final Collection names;

   NameChangeListener(Collection names) {
      this.names = names;
   }

   public void propertyChange(PropertyChangeEvent evt) {
      if (evt.getPropertyName().equals("id")) {
         String newName = (String)evt.getNewValue();
         String oldName = (String)evt.getOldValue();
         this.names.remove(oldName);
         this.names.add(newName);
      }

   }
}
