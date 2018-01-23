package pipe.gui.imperial.io;

import java.util.Map;
import pipe.gui.imperial.state.ClassifiedState;

public interface StateProcessor {
   void processTransitions(int var1, Map var2);

   void processState(ClassifiedState var1, int var2);
}
