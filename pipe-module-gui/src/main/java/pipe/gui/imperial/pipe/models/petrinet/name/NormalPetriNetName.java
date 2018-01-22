package pipe.gui.imperial.pipe.models.petrinet.name;

import pipe.gui.imperial.pipe.models.petrinet.name.FileNameVisitor;
import pipe.gui.imperial.pipe.models.petrinet.name.NameVisitor;
import pipe.gui.imperial.pipe.models.petrinet.name.NormalNameVisitor;
import pipe.gui.imperial.pipe.models.petrinet.name.PetriNetName;

public final class NormalPetriNetName implements PetriNetName {
   private String name;

   public NormalPetriNetName(String name) {
      this.name = name;
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof NormalPetriNetName)) {
         return false;
      } else {
         NormalPetriNetName that = (NormalPetriNetName)o;
         return this.name.equals(that.name);
      }
   }

   public String getName() {
      return this.name;
   }

   public void visit(NameVisitor visitor) {
      if (visitor instanceof FileNameVisitor) {
         ((NormalNameVisitor)visitor).visit(this);
      }

   }
}
