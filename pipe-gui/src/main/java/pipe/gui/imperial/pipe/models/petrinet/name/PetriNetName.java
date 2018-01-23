package pipe.gui.imperial.pipe.models.petrinet.name;

import pipe.gui.imperial.pipe.models.petrinet.name.NameVisitor;

public interface PetriNetName {
   String getName();

   void visit(NameVisitor var1);
}
