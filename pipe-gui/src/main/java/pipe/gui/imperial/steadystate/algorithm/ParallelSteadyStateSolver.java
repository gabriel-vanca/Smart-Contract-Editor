package pipe.gui.imperial.steadystate.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ParallelSteadyStateSolver extends AbstractSteadyStateSolver {
   private final int threads;
   private final SteadyStateBuilder builder;
   private boolean waitForAllSolversForTesting;
   private static final Logger LOGGER = Logger.getLogger(ParallelSteadyStateSolver.class.getName());

   public ParallelSteadyStateSolver(int threads, SteadyStateBuilder builder) {
      this.threads = threads;
      this.builder = builder;
   }

   public Map solve(List records) {
      Map diagonals = this.calculateDiagonals(records);
      double a = this.geta(diagonals);
      List Q = this.divide(records, -a);
      Map newDiagonals = this.divide(-a, diagonals);
      Map QTranspose = this.transpose(Q);
      ExecutorService executorService = Executors.newFixedThreadPool(this.threads);

      Map var9;
      try {
         if (this.isDiagonallyDominant(QTranspose, newDiagonals)) {
            var9 = this.solveWithJacobi(records, executorService);
            return var9;
         }

         var9 = this.solveWithJacobiAndGS(records, executorService);
      } finally {
         executorService.shutdownNow();
      }

      return var9;
   }

   private Map solveWithJacobiAndGS(List records, ExecutorService executorService) {
      SteadyStateSolver gsSolver = this.builder.buildGaussSeidel();
      if (this.threads == 1) {
         return gsSolver.solve(records);
      } else {
         SteadyStateSolver jacobiSolver = this.builder.buildJacobi(executorService, this.threads - 1);
         CompletionService completionService = new ExecutorCompletionService(executorService);
         List futures = this.submit(completionService, records, gsSolver, jacobiSolver);
         boolean var16 = false;

         Map var7;
         label104: {
            HashMap var8;
            try {
               var16 = true;
               var7 = this.getFirstCompletedFuture(completionService, futures);
               var16 = false;
               break label104;
            } catch (ExecutionException | InterruptedException var17) {
               LOGGER.log(Level.SEVERE, var17.getMessage());
               var8 = new HashMap();
               var16 = false;
            } finally {
               if (var16) {
                  Iterator i$ = futures.iterator();

                  while(i$.hasNext()) {
                     Future future = (Future)i$.next();
                     future.cancel(true);
                  }

               }
            }

            Iterator i$ = futures.iterator();

            while(i$.hasNext()) {
               Future future = (Future)i$.next();
               future.cancel(true);
            }

            return var8;
         }

         Iterator i$ = futures.iterator();

         while(i$.hasNext()) {
            Future future = (Future)i$.next();
            future.cancel(true);
         }

         return var7;
      }
   }

   protected Map getFirstCompletedFuture(CompletionService completionService, List futures) throws InterruptedException, ExecutionException {
      if (!this.waitForAllSolversForTesting) {
         return (Map)completionService.take().get();
      } else {
         List futureResults = new ArrayList();

         for(int i = 0; i < futures.size(); ++i) {
            futureResults.add(completionService.poll(1000L, TimeUnit.MILLISECONDS));
         }

         return (Map)((Future)futureResults.get(0)).get();
      }
   }

   private Map solveWithJacobi(List records, ExecutorService executorService) {
      SteadyStateSolver solver = this.builder.buildJacobi(executorService, this.threads);
      return solver.solve(records);
   }

   private boolean isDiagonallyDominant(Map records, Map diagonalElements) {
      Iterator i$ = records.entrySet().iterator();

      double rowSum;
      double a_ii;
      do {
         if (!i$.hasNext()) {
            return true;
         }

         Entry entry = (Entry)i$.next();
         rowSum = this.getAbsRowSum(entry);
         a_ii = ((Double)diagonalElements.get(entry.getKey())).doubleValue();
      } while(Math.abs(a_ii) >= rowSum);

      return false;
   }

   private List submit(CompletionService completionService, List records, SteadyStateSolver... solvers) {
      List futures = new ArrayList();
      SteadyStateSolver[] arr$ = solvers;
      int len$ = solvers.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         SteadyStateSolver solver = arr$[i$];
         futures.add(completionService.submit(new ParallelSteadyStateSolver.CallableSteadyStateSolver(solver, records)));
      }

      return futures;
   }

   private double getAbsRowSum(Entry entry) {
      double rowSum = 0.0D;

      double value;
      for(Iterator i$ = ((Map)entry.getValue()).values().iterator(); i$.hasNext(); rowSum += Math.abs(value)) {
         value = ((Double)i$.next()).doubleValue();
      }

      return rowSum;
   }

   protected void waitForAllSolversForTesting(boolean waitForAllSolversForTesting) {
      this.waitForAllSolversForTesting = waitForAllSolversForTesting;
   }

   // $FF: synthetic class
   static class SyntheticClass_1 {
   }

   private static final class CallableSteadyStateSolver implements Callable {
      private final SteadyStateSolver solver;
      private final List records;

      private CallableSteadyStateSolver(SteadyStateSolver solver, List records) {
         this.solver = solver;
         this.records = records;
      }

      public Map call() {
         return this.solver.solve(this.records);
      }

      // $FF: synthetic method
      CallableSteadyStateSolver(SteadyStateSolver x0, List x1, ParallelSteadyStateSolver.SyntheticClass_1 x2) {
         this(x0, x1);
      }
   }
}
