package pipe.gui.imperial.steadystate.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParallelGaussSeidel extends AXEqualsBSolver {
   private static final Logger LOGGER = Logger.getLogger(ParallelJacobiSolver.class.getName());
   private final int threads;
   private final ExecutorService executorService;
   private final int subIterations;

   public ParallelGaussSeidel(int threads, ExecutorService executorService, int subIterations) {
      this.threads = threads;
      this.executorService = executorService;
      this.subIterations = subIterations;
   }

   protected List solve(Map records, Map diagonalElements) {
      List firstGuess = this.initialiseXWithGuessList(records);
      AtomicReferenceArray x = new AtomicReferenceArray(firstGuess.toArray(new Double[firstGuess.size()]));
      boolean converged = false;

      int iterations;
      for(iterations = 0; !converged; ++iterations) {
         CountDownLatch latch = this.submitTasks(this.threads, records, diagonalElements, this.executorService, x);

         try {
            latch.await();
         } catch (InterruptedException var9) {
            LOGGER.log(Level.ALL, var9.getMessage());
         }

         converged = this.hasConverged(records, diagonalElements, x);
      }

      LOGGER.log(Level.INFO, String.format("Took %d iterations to converge", iterations));
      return this.toArray(x);
   }

   private List toArray(AtomicReferenceArray x) {
      List result = new ArrayList();

      for(int i = 0; i < x.length(); ++i) {
         result.add(x.get(i));
      }

      return result;
   }

   private CountDownLatch submitTasks(int threads, Map records, Map diagonalElements, ExecutorService executorService, AtomicReferenceArray x) {
      int scheduledThreads = x.length() < threads ? x.length() : threads;
      int split = x.length() / scheduledThreads;
      int remaining = x.length() % scheduledThreads;
      CountDownLatch latch = new CountDownLatch(scheduledThreads);
      int from = 0;

      for(int thread = 0; thread < scheduledThreads; ++thread) {
         int to = from + split - 1 + (remaining > 0 ? 1 : 0);
         if (remaining > 0) {
            --remaining;
         }

         executorService.submit(this.createRunnable(from, to, latch, x, records, diagonalElements));
         from = to + 1;
      }

      return latch;
   }

   public Runnable createRunnable(int from, int to, CountDownLatch latch, AtomicReferenceArray x, Map records, Map diagonalElements) {
      return new ParallelGaussSeidel.ParallelSolver(from, to, latch, records, x, diagonalElements);
   }

   // $FF: synthetic class
   static class SyntheticClass_1 {
   }

   private final class ParallelSolver implements Runnable {
      private final Map records;
      private final AtomicReferenceArray x;
      private final Map diagonalElements;
      private final int from;
      private final int to;
      private final CountDownLatch latch;

      private ParallelSolver(int from, int to, CountDownLatch latch, Map records, AtomicReferenceArray x, Map diagonalElements) {
         this.records = records;
         this.latch = latch;
         this.x = x;
         this.diagonalElements = diagonalElements;
         this.from = from;
         this.to = to;
      }

      public void run() {
         for(int i = 0; i < ParallelGaussSeidel.this.subIterations; ++i) {
            try {
               for(int index = this.from; index <= this.to; ++index) {
                  Map row = (Map)this.records.get(index);
                  double aii = ((Double)this.diagonalElements.get(index)).doubleValue();
                  double rowValue = ParallelGaussSeidel.this.getRowValue(index, row, aii, this.x);
                  this.x.set(index, rowValue);
               }
            } catch (Exception var11) {
               ParallelGaussSeidel.LOGGER.log(Level.SEVERE, var11.getMessage());
            } finally {
               this.latch.countDown();
            }
         }

      }

      // $FF: synthetic method
      ParallelSolver(int x1, int x2, CountDownLatch x3, Map x4, AtomicReferenceArray x5, Map x6, ParallelGaussSeidel.SyntheticClass_1 x7) {
         this(x1, x2, x3, x4, x5, x6);
      }
   }
}
