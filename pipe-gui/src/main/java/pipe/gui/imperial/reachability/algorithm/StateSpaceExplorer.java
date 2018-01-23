package pipe.gui.imperial.reachability.algorithm;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import pipe.gui.imperial.pipe.exceptions.InvalidRateException;
import pipe.gui.imperial.state.ClassifiedState;

public interface StateSpaceExplorer {
   StateSpaceExplorer.StateSpaceExplorerResults generate(ClassifiedState var1) throws TimelessTrapException, InterruptedException, ExecutionException, IOException, InvalidRateException;

   public static class StateSpaceExplorerResults {
      public final int processedTransitions;
      public final int numberOfStates;

      public StateSpaceExplorerResults(int processedTransitions, int numberOfStates) {
         this.processedTransitions = processedTransitions;
         this.numberOfStates = numberOfStates;
      }
   }
}
