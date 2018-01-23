package pipe.gui.imperial.io;

import com.esotericsoftware.kryo.io.Input;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public interface MultiStateReader {
   Collection readRecords(Input var1) throws IOException;

   Map readStates(Input var1);
}
