package pipe.gui.imperial.reachability.algorithm;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import pipe.gui.imperial.pipe.exceptions.InvalidRateException;
import pipe.gui.imperial.state.ClassifiedState;

public final class OnTheFlyVanishingExplorer implements VanishingExplorer {
   private static final int ALLOWED_ITERATIONS = 1000;
   private static final double EPSILON = 1.0E-7D;
   private final ExplorerUtilities explorerUtilities;

   public OnTheFlyVanishingExplorer(ExplorerUtilities explorerUtilities) {
      this.explorerUtilities = explorerUtilities;
   }

   public Collection explore(ClassifiedState vanishingState, double rate) throws TimelessTrapException, InvalidRateException {
      Deque vanishingStack = new ArrayDeque();
      vanishingStack.push(new StateRateRecord(vanishingState, rate));
      int iterations = 0;

      LinkedList tangibleStatesFound;
      for(tangibleStatesFound = new LinkedList(); !vanishingStack.isEmpty() && iterations < 1000; ++iterations) {
         StateRateRecord record = (StateRateRecord)vanishingStack.pop();
         ClassifiedState previous = record.getState();
         Iterator i$ = this.explorerUtilities.getSuccessors(previous).iterator();

         while(i$.hasNext()) {
            ClassifiedState successor = (ClassifiedState)i$.next();
            double successorRate = record.getRate() * this.probability(previous, successor);
            if (successor.isTangible()) {
               tangibleStatesFound.add(new StateRateRecord(successor, successorRate));
            } else if (successorRate > 1.0E-7D) {
               vanishingStack.push(new StateRateRecord(successor, successorRate));
            }
         }
      }

      if (iterations == 1000) {
         throw new TimelessTrapException();
      } else {
         return tangibleStatesFound;
      }
   }

   private double probability(ClassifiedState state, ClassifiedState successor) throws InvalidRateException {
      Collection marked = this.explorerUtilities.getTransitions(state, successor);
      if (marked.isEmpty()) {
         return 0.0D;
      } else {
         double toSuccessorWeight = this.explorerUtilities.getWeightOfTransitions(state, marked);
         double totalWeight = this.explorerUtilities.getWeightOfTransitions(state, this.explorerUtilities.getAllEnabledTransitions(state));
         return toSuccessorWeight / totalWeight;
      }
   }
}
