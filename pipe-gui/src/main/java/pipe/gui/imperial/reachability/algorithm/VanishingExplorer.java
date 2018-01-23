package pipe.gui.imperial.reachability.algorithm;

import java.util.Collection;
import pipe.gui.imperial.pipe.exceptions.InvalidRateException;
import pipe.gui.imperial.state.ClassifiedState;

public interface VanishingExplorer {
   Collection explore(ClassifiedState var1, double var2) throws TimelessTrapException, InvalidRateException;
}
