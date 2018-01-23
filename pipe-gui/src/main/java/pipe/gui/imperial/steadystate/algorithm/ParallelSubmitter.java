package pipe.gui.imperial.steadystate.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.logging.Level;
import java.util.logging.Logger;

class ParallelSubmitter {
   private static final Logger LOGGER = Logger.getLogger(ParallelSubmitter.class.getName());
   private final int maxIterations;
   private boolean bounded = false;

   public ParallelSubmitter() {
      this.maxIterations = 0;
   }

   public ParallelSubmitter(int maxIterations) {
      this.maxIterations = maxIterations;
      this.bounded = true;
   }

   public List solve(int threads, ExecutorService executorService, List firstGuess, Map records, Map diagonalElements, ParallelSubmitter.ParallelUtils utils) {
      AtomicReferenceArray x = new AtomicReferenceArray(firstGuess.toArray(new Double[firstGuess.size()]));
      List previous = new ArrayList(firstGuess);
      boolean converged = false;

      int iterations;
      for(iterations = 0; !converged && this.canContinue(iterations); ++iterations) {
         CountDownLatch latch = this.submitTasks(utils, threads, records, diagonalElements, executorService, x, (List)previous);

         try {
            latch.await();
         } catch (InterruptedException var13) {
            LOGGER.log(Level.ALL, var13.getMessage());
         }

         converged = utils.isConverged((List)previous, x, records, diagonalElements);
         previous = this.atomicToList(x);
      }

      LOGGER.log(Level.INFO, String.format("Took %d iterations to converge", iterations));
      List values = new ArrayList();

      for(int i = 0; i < x.length(); ++i) {
         values.add(x.get(i));
      }

      return this.atomicToList(x);
   }

   private List atomicToList(AtomicReferenceArray x) {
      List result = new ArrayList();

      for(int i = 0; i < x.length(); ++i) {
         result.add(x.get(i));
      }

      return result;
   }

   private boolean canContinue(int iterations) {
      if (this.bounded && iterations >= this.maxIterations) {
         LOGGER.log(Level.INFO, "Reached maximum number of iterations. Cutting short!");
      }

      return !this.bounded || iterations < this.maxIterations;
   }

   private CountDownLatch submitTasks(ParallelSubmitter.ParallelUtils utils, int threads, Map records, Map diagonalElements, ExecutorService executorService, AtomicReferenceArray x, List previous) {
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

         executorService.submit(utils.createRunnable(from, to, latch, previous, x, records, diagonalElements));
         from = to + 1;
      }

      return latch;
   }

   public interface ParallelUtils {
      boolean isConverged(List var1, AtomicReferenceArray var2, Map var3, Map var4);

      Runnable createRunnable(int var1, int var2, CountDownLatch var3, List var4, AtomicReferenceArray var5, Map var6, Map var7);
   }
}
