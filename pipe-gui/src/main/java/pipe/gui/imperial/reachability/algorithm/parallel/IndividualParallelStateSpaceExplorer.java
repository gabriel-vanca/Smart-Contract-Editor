package pipe.gui.imperial.reachability.algorithm.parallel;

import pipe.gui.imperial.io.StateProcessor;
import pipe.gui.imperial.reachability.algorithm.AbstractStateSpaceExplorer;
import pipe.gui.imperial.reachability.algorithm.ExplorerUtilities;
import pipe.gui.imperial.reachability.algorithm.TimelessTrapException;
import pipe.gui.imperial.reachability.algorithm.VanishingExplorer;
import pipe.gui.imperial.state.ClassifiedState;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.*;

public final class IndividualParallelStateSpaceExplorer extends AbstractStateSpaceExplorer {
   private static final int THREADS = 8;
   protected ExecutorService executorService = Executors.newFixedThreadPool(8);

   public IndividualParallelStateSpaceExplorer(StateProcessor stateProcessor, VanishingExplorer vanishingExplorer, ExplorerUtilities explorerUtilities) {
      super(explorerUtilities, vanishingExplorer, stateProcessor);
   }

   protected void stateSpaceExploration() throws InterruptedException, ExecutionException, TimelessTrapException {
      if (this.executorService.isTerminated()) {
         this.executorService = Executors.newFixedThreadPool(8);
      }

      int elemsAtCurrentLevel = this.explorationQueue.size();

      for(int elemsAtNextLevel = 0; !this.explorationQueue.isEmpty(); elemsAtNextLevel = 0) {
         Map successorFutures = new HashMap();
         CountDownLatch latch = new CountDownLatch(elemsAtCurrentLevel);

         for(int i = 0; i < elemsAtCurrentLevel; ++i) {
            ClassifiedState state = (ClassifiedState)this.explorationQueue.poll();
            successorFutures.put(state, this.executorService.submit(new ParallelStateExplorer(latch, state, this.explorerUtilities, this.vanishingExplorer)));
         }

         latch.await();
         Iterator i$ = successorFutures.entrySet().iterator();

         while(i$.hasNext()) {
            Entry entry = (Entry)i$.next();
            Future future = (Future)entry.getValue();
            this.successorRates.clear();

            try {
               Map successors = (Map)future.get();
               Iterator i$1 = successors.entrySet().iterator();

               while(i$1.hasNext()) {
                  Entry successorEntry = (Entry)i$1.next();
                  ClassifiedState successor = (ClassifiedState)successorEntry.getKey();
                  double rate = ((Double)successorEntry.getValue()).doubleValue();
                  this.registerStateRate(successor, rate);
                  if (!this.explored.contains(successor)) {
                     ++elemsAtNextLevel;
                     this.explorationQueue.add(successor);
                     this.markAsExplored(successor);
                  }
               }
            } catch (ExecutionException var14) {
               throw new TimelessTrapException(var14);
            }

            ClassifiedState state = (ClassifiedState)entry.getKey();
            this.writeStateTransitions(state, this.successorRates);
         }

         elemsAtCurrentLevel = elemsAtNextLevel;
      }

      this.executorService.shutdownNow();
   }
}
