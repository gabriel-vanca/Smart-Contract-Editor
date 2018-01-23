package pipe.gui.imperial.reachability.algorithm.sequential;

import pipe.gui.imperial.io.StateProcessor;
import pipe.gui.imperial.pipe.exceptions.InvalidRateException;
import pipe.gui.imperial.reachability.algorithm.*;
import pipe.gui.imperial.state.ClassifiedState;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class SequentialStateSpaceExplorer extends AbstractStateSpaceExplorer {
   private static final Logger LOGGER = Logger.getLogger(SequentialStateSpaceExplorer.class.getName());

   public SequentialStateSpaceExplorer(ExplorerUtilities explorerUtilities, VanishingExplorer vanishingExplorer, StateProcessor stateProcessor) {
      super(explorerUtilities, vanishingExplorer, stateProcessor);
   }

   protected void stateSpaceExploration() throws TimelessTrapException, IOException, InvalidRateException {
      int iterations = 0;

      while(!this.explorationQueue.isEmpty() && this.explorerUtilities.canExploreMore(this.stateCount)) {
         ClassifiedState state = (ClassifiedState)this.explorationQueue.poll();
         this.successorRates.clear();
         Iterator i$ = this.explorerUtilities.getSuccessors(state).iterator();

         while(true) {
            while(i$.hasNext()) {
               ClassifiedState successor = (ClassifiedState)i$.next();
               double rate = this.explorerUtilities.rate(state, successor);
               if (successor.isTangible()) {
                  this.registerStateTransition(successor, rate);
               } else {
                  Collection explorableStates = this.vanishingExplorer.explore(successor, rate);
                  Iterator i$1 = explorableStates.iterator();

                  while(i$1.hasNext()) {
                     StateRateRecord record = (StateRateRecord)i$1.next();
                     this.registerStateTransition(record.getState(), record.getRate());
                  }
               }
            }

            this.writeStateTransitions(state, this.successorRates);
            this.explorerUtilities.clear();
            ++iterations;
            break;
         }
      }

      LOGGER.log(Level.INFO, String.format("Took %d iterations to explore state space", iterations));
   }
}
