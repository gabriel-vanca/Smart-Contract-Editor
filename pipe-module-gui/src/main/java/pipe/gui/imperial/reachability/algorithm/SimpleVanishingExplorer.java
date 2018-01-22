package pipe.gui.imperial.reachability.algorithm;

import java.util.Arrays;
import java.util.Collection;
import pipe.gui.imperial.state.ClassifiedState;

public final class SimpleVanishingExplorer implements VanishingExplorer {
   public Collection explore(ClassifiedState vanishingState, double rate) throws TimelessTrapException {
      return Arrays.asList(new StateRateRecord(vanishingState, rate));
   }
}
