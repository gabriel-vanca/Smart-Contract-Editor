package pipe.gui.imperial.io;

import com.esotericsoftware.kryo.io.Output;
import java.util.Map;
import pipe.gui.imperial.state.ClassifiedState;

public interface StateWriter {
   void writeTransitions(int var1, Map var2, Output var3);

   void writeState(ClassifiedState var1, int var2, Output var3);
}
