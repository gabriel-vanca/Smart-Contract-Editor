package pipe.gui.imperial.io;

import com.esotericsoftware.kryo.io.Input;
import java.io.IOException;
import pipe.gui.imperial.state.Record;

public interface StateReader {
   Record readRecord(Input var1) throws IOException;

   StateMapping readState(Input var1);
}
