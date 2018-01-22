package pipe.gui.imperial.pipe.naming;

import pipe.gui.imperial.pipe.naming.NameChangeListener;
import pipe.gui.imperial.pipe.naming.UniqueNamer;

import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.HashSet;

public abstract class AbstractUniqueNamer implements UniqueNamer {
   private final String namePrefix;
   protected final Collection names = new HashSet();
   protected final PropertyChangeListener nameListener;

   protected AbstractUniqueNamer(String namePrefix) {
      this.nameListener = new NameChangeListener(this.names);
      this.namePrefix = namePrefix;
   }

   public final String getName() {
      int nameNumber = 0;

      String name;
      for(name = this.namePrefix + nameNumber; this.names.contains(name); name = this.namePrefix + nameNumber) {
         ++nameNumber;
      }

      return name;
   }

   public final boolean isUniqueName(String name) {
      return !this.names.contains(name);
   }
}
