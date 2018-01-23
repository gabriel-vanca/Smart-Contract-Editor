package pipe.gui.imperial.steadystate.algorithm;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.logging.Level;
import java.util.logging.Logger;

class ParallelJacobiSolver extends AXEqualsBSolver {
   private static final Logger LOGGER = Logger.getLogger(ParallelJacobiSolver.class.getName());
   private final int threads;
   private final ExecutorService executorService;
   private final int maxIterations;
   private boolean bounded = false;

   public ParallelJacobiSolver(int threads, ExecutorService executorService) {
      this.threads = threads;
      this.executorService = executorService;
      this.maxIterations = 0;
   }

   public ParallelJacobiSolver(int threads, ExecutorService executorService, int maxIterations) {
      this.threads = threads;
      this.executorService = executorService;
      this.maxIterations = maxIterations;
      this.bounded = true;
   }

   protected List solve(Map records, Map diagonalElements) {
      ParallelSubmitter submitter = this.getSubmitter();
      List x = submitter.solve(this.threads, this.executorService, this.initialiseXWithGuessList(records), records, diagonalElements, new ParallelSubmitter.ParallelUtils() {
         public boolean isConverged(List previousX, AtomicReferenceArray x, Map records, Map diagonalElements) {
            return ParallelJacobiSolver.this.hasConverged(records, diagonalElements, x);
         }

         public Runnable createRunnable(int from, int to, CountDownLatch latch, List previousX, AtomicReferenceArray x, Map records, Map diagonalElements) {
            return ParallelJacobiSolver.this.new ParallelSolver(from, to, latch, previousX, records, x, diagonalElements, null);
         }
      });
      return x;
   }

   private ParallelSubmitter getSubmitter() {
      return !this.bounded ? new ParallelSubmitter() : new ParallelSubmitter(this.maxIterations);
   }

   private final class ParallelSolver implements Runnable {
      private final List previous;
      private final Map records;
      private final AtomicReferenceArray x;
      private final Map diagonalElements;
      private final int from;
      private final int to;
      private final CountDownLatch latch;

      private ParallelSolver(int from, int to, CountDownLatch latch, List previous, Map records, AtomicReferenceArray x, Map diagonalElements) {
         this.previous = previous;
         this.records = records;
         this.latch = latch;
         this.x = x;
         this.diagonalElements = diagonalElements;
         this.from = from;
         this.to = to;
      }

      public void run() {
         try {
            for(int state = this.from; state <= this.to; ++state) {
               Map row = (Map)this.records.get(state);
               double aii = ((Double)this.diagonalElements.get(state)).doubleValue();
               double rowValue = ParallelJacobiSolver.this.getRowValue(state, row, aii, this.previous);
               this.x.set(state, rowValue);
            }
         } catch (Exception var10) {
            ParallelJacobiSolver.LOGGER.log(Level.SEVERE, var10.getMessage());
         } finally {
            this.latch.countDown();
         }

      }

      // $FF: synthetic method
      ParallelSolver(int x1, int x2, CountDownLatch x3, List x4, Map x5, AtomicReferenceArray x6, Map x7, Object x8) {
         this(x1, x2, x3, x4, x5, x6, x7);
      }
   }
}
