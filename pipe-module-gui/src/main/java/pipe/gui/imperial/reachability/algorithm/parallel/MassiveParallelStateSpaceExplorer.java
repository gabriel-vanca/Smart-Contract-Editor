package pipe.gui.imperial.reachability.algorithm.parallel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import pipe.gui.imperial.reachability.algorithm.AbstractStateSpaceExplorer;
import pipe.gui.imperial.reachability.algorithm.ExplorerUtilities;
import pipe.gui.imperial.reachability.algorithm.StateRateRecord;
import pipe.gui.imperial.reachability.algorithm.TimelessTrapException;
import pipe.gui.imperial.reachability.algorithm.VanishingExplorer;
import pipe.gui.imperial.io.StateProcessor;
import pipe.gui.imperial.pipe.exceptions.InvalidRateException;
import pipe.gui.imperial.state.ClassifiedState;

public final class MassiveParallelStateSpaceExplorer extends AbstractStateSpaceExplorer {
   private final int threads;
   private static final Logger LOGGER = Logger.getLogger(MassiveParallelStateSpaceExplorer.class.getName());
   private final int statesPerThread;
   protected ExecutorService executorService;
   private Queue sharedIterationQueue = new ConcurrentLinkedQueue();
   private Map iterationTransitions = new ConcurrentHashMap();
   private Map sharedHashSeen = new ConcurrentHashMap();

   public MassiveParallelStateSpaceExplorer(ExplorerUtilities explorerUtilities, VanishingExplorer vanishingExplorer, StateProcessor stateProcessor, int threads, int statesPerThread) {
      super(explorerUtilities, vanishingExplorer, stateProcessor);
      this.statesPerThread = statesPerThread;
      this.threads = threads;
   }

   protected void stateSpaceExploration() throws InterruptedException, ExecutionException, TimelessTrapException, IOException {
      this.executorService = Executors.newFixedThreadPool(this.threads);
      CompletionService completionService = new ExecutorCompletionService(this.executorService);
      int iterations = 0;
      long duration = 0L;
      List explorers = this.initialiseExplorers();
      this.sharedIterationQueue.addAll(this.explorationQueue);

      while(!this.sharedIterationQueue.isEmpty() && this.explorerUtilities.canExploreMore(this.stateCount)) {
         int submitted;
         for(submitted = 0; submitted < this.threads && !this.sharedIterationQueue.isEmpty(); ++submitted) {
            MassiveParallelStateSpaceExplorer.MultiStateExplorer explorer = (MassiveParallelStateSpaceExplorer.MultiStateExplorer)explorers.get(submitted);
            completionService.submit(explorer);
         }

         long start = System.nanoTime();

         for(int i = 0; i < submitted; ++i) {
            completionService.take().get();
         }

         long end = System.nanoTime();
         duration += end - start;
         this.markAsExplored(this.sharedHashSeen.keySet());
         Iterator i$ = this.iterationTransitions.entrySet().iterator();

         while(i$.hasNext()) {
            Entry entry = (Entry)i$.next();
            this.writeStateTransitions((ClassifiedState)entry.getKey(), (Map)entry.getValue());
         }

         this.sharedHashSeen.clear();
         this.iterationTransitions.clear();
         this.explorerUtilities.clear();
         ++iterations;
      }

      this.executorService.shutdownNow();
      LOGGER.log(Level.INFO, "Took " + iterations + " iterations to explore state space with " + (double)duration / (double)iterations + " time for each iteration");
   }

   private List initialiseExplorers() {
      List explorers = new ArrayList();

      for(int i = 0; i < this.threads; ++i) {
         explorers.add(new MassiveParallelStateSpaceExplorer.MultiStateExplorer());
      }

      return explorers;
   }

   // $FF: synthetic class
   static class SyntheticClass_1 {
   }

   private final class MultiStateExplorer implements Callable {
      private MultiStateExplorer() {
      }

      public Collection call() throws TimelessTrapException, InvalidRateException {
         for(int explored = 0; explored < MassiveParallelStateSpaceExplorer.this.statesPerThread; ++explored) {
            ClassifiedState state = (ClassifiedState)MassiveParallelStateSpaceExplorer.this.sharedIterationQueue.poll();
            if (state == null) {
               return null;
            }

            Map successorRates = new HashMap();
            Iterator i$x = MassiveParallelStateSpaceExplorer.this.explorerUtilities.getSuccessors(state).iterator();

            while(true) {
               while(i$x.hasNext()) {
                  ClassifiedState successor = (ClassifiedState)i$x.next();
                  double rate = MassiveParallelStateSpaceExplorer.this.explorerUtilities.rate(state, successor);
                  if (successor.isTangible()) {
                     this.registerStateRate(successor, rate, successorRates);
                     if (!this.seen(successor)) {
                        MassiveParallelStateSpaceExplorer.this.sharedIterationQueue.add(successor);
                        this.addToSharedSeen(successor);
                     }
                  } else {
                     Collection explorableStates = MassiveParallelStateSpaceExplorer.this.vanishingExplorer.explore(successor, rate);
                     Iterator i$ = explorableStates.iterator();

                     while(i$.hasNext()) {
                        StateRateRecord record = (StateRateRecord)i$.next();
                        this.registerStateRate(record.getState(), record.getRate(), successorRates);
                        if (!this.seen(record.getState())) {
                           MassiveParallelStateSpaceExplorer.this.sharedIterationQueue.add(record.getState());
                           this.addToSharedSeen(record.getState());
                        }
                     }
                  }
               }

               this.writeStateTransitions(state, successorRates);
               break;
            }
         }

         return null;
      }

      private void addToSharedSeen(ClassifiedState state) {
         MassiveParallelStateSpaceExplorer.this.sharedHashSeen.put(state, true);
      }

      private void registerStateRate(ClassifiedState successor, double rate, Map successorRates) {
         if (successorRates.containsKey(successor)) {
            double previousRate = ((Double)successorRates.get(successor)).doubleValue();
            successorRates.put(successor, previousRate + rate);
         } else {
            successorRates.put(successor, rate);
         }

      }

      private boolean seen(ClassifiedState state) {
         return MassiveParallelStateSpaceExplorer.this.sharedHashSeen.containsKey(state) || MassiveParallelStateSpaceExplorer.this.explored.contains(state);
      }

      private void writeStateTransitions(ClassifiedState state, Map successorRates) {
         if (!MassiveParallelStateSpaceExplorer.this.iterationTransitions.containsKey(state)) {
            MassiveParallelStateSpaceExplorer.this.iterationTransitions.put(state, successorRates);
         }

      }

      // $FF: synthetic method
      MultiStateExplorer(MassiveParallelStateSpaceExplorer.SyntheticClass_1 x1) {
         this();
      }
   }
}
