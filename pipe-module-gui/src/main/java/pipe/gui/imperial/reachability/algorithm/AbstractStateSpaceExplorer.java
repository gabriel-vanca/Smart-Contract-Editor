package pipe.gui.imperial.reachability.algorithm;

import pipe.gui.imperial.io.StateProcessor;
import pipe.gui.imperial.pipe.exceptions.InvalidRateException;
import pipe.gui.imperial.state.ClassifiedState;
import pipe.gui.imperial.steadystate.algorithm.AbstractSteadyStateSolver;
import pipe.gui.imperial.utils.ExploredSet;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractStateSpaceExplorer implements StateSpaceExplorer {
   private static final int EXPLORED_SET_SIZE = 358591;
   protected final ExploredSet explored = new ExploredSet(358591);
   private static final Logger LOGGER = Logger.getLogger(AbstractSteadyStateSolver.class.getName());
   protected final StateProcessor stateProcessor;
   protected final VanishingExplorer vanishingExplorer;
   protected final Map successorRates = new HashMap();
   protected ExplorerUtilities explorerUtilities;
   protected Deque explorationQueue = new ArrayDeque();
   protected int stateCount = 0;
   private int processedCount = 0;

   public AbstractStateSpaceExplorer(ExplorerUtilities explorerUtilities, VanishingExplorer vanishingExplorer, StateProcessor stateProcessor) {
      this.explorerUtilities = explorerUtilities;
      this.vanishingExplorer = vanishingExplorer;
      this.stateProcessor = stateProcessor;
   }

   public final StateSpaceExplorer.StateSpaceExplorerResults generate(ClassifiedState initialState) throws TimelessTrapException, InterruptedException, ExecutionException, IOException, InvalidRateException {
      long start = System.nanoTime();
      this.exploreInitialState(initialState);
      this.stateSpaceExploration();
      long end = System.nanoTime();
      long duration = end - start;
      LOGGER.log(Level.INFO, "Took " + duration + " to solve state space");
      return new StateSpaceExplorer.StateSpaceExplorerResults(this.processedCount, this.stateCount);
   }

   protected final void exploreInitialState(ClassifiedState initialState) throws TimelessTrapException, InvalidRateException {
      if (initialState.isTangible()) {
         this.explorationQueue.add(initialState);
         this.markAsExplored(initialState);
      } else {
         Collection explorableStates = this.vanishingExplorer.explore(initialState, 1.0D);
         Iterator i$ = explorableStates.iterator();

         while(i$.hasNext()) {
            StateRateRecord record = (StateRateRecord)i$.next();
            this.registerStateTransition(record.getState(), record.getRate());
         }
      }

   }

   protected abstract void stateSpaceExploration() throws InterruptedException, ExecutionException, TimelessTrapException, IOException, InvalidRateException;

   protected final void markAsExplored(ClassifiedState state) {
      if (!this.explored.contains(state)) {
         int uniqueNumber = this.getUniqueStateNumber();
         this.stateProcessor.processState(state, uniqueNumber);
         this.explored.add(state, uniqueNumber);
      }

   }

   protected final void registerStateTransition(ClassifiedState successor, double rate) {
      this.registerStateRate(successor, rate);
      if (!this.explored.contains(successor)) {
         this.explorationQueue.add(successor);
         this.markAsExplored(successor);
      }

   }

   private int getUniqueStateNumber() {
      int number = this.stateCount++;
      return number;
   }

   protected final void registerStateRate(ClassifiedState successor, double rate) {
      if (this.successorRates.containsKey(successor)) {
         double previousRate = ((Double)this.successorRates.get(successor)).doubleValue();
         this.successorRates.put(successor, previousRate + rate);
      } else {
         this.successorRates.put(successor, rate);
      }

   }

   protected final void markAsExplored(Collection exploredStates) {
      Iterator i$ = exploredStates.iterator();

      while(i$.hasNext()) {
         ClassifiedState state = (ClassifiedState)i$.next();
         if (!this.explored.contains(state)) {
            this.markAsExplored(state);
         }
      }

   }

   protected final void writeStateTransitions(ClassifiedState state, Map successorRates) {
      Map transitions = this.getIntegerTransitions(successorRates);
      int stateId = this.explored.getId(state);
      this.stateProcessor.processTransitions(stateId, transitions);
      this.processedCount += successorRates.size();
   }

   private Map getIntegerTransitions(Map successorRates) {
      Map transitions = new HashMap();
      Iterator i$ = successorRates.entrySet().iterator();

      while(i$.hasNext()) {
         Entry entry = (Entry)i$.next();
         int id = this.explored.getId((ClassifiedState)entry.getKey());
         transitions.put(id, entry.getValue());
      }

      return transitions;
   }
}
