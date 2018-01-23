package pipe.gui.imperial.reachability.io;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collection;

public interface StateSpaceExplorationReader {
   Collection getRecords(ObjectInputStream var1) throws IOException;
}
