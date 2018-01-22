package pipe.gui.imperial.pipe.dsl;

import java.util.Map;
import pipe.gui.imperial.pipe.models.petrinet.PetriNetComponent;

public interface DSLCreator {
   PetriNetComponent create(Map var1, Map var2, Map var3, Map var4);
}
