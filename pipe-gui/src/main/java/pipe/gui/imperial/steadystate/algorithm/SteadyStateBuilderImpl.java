package pipe.gui.imperial.steadystate.algorithm;

import java.util.concurrent.ExecutorService;

public final class SteadyStateBuilderImpl implements SteadyStateBuilder {
   public SteadyStateSolver buildGaussSeidel() {
      return new GaussSeidelSolver();
   }

   public SteadyStateSolver buildSequentialJacobi() {
      return new SequentialJacobiSolver();
   }

   public SteadyStateSolver buildBoundedSequentialJacobi(int maxIterations) {
      return new SequentialJacobiSolver(maxIterations);
   }

   public SteadyStateSolver buildJacobi(ExecutorService executorService, int threads) {
      return new ParallelJacobiSolver(threads, executorService);
   }

   public SteadyStateSolver buildBoundedParallelJacobiSolver(ExecutorService executorService, int threads, int maxIterations) {
      return new ParallelJacobiSolver(threads, executorService, maxIterations);
   }

   public SteadyStateSolver buildAsynchronousGaussSeidel(ExecutorService executorService, int threads, int subIterations) {
      return new ParallelGaussSeidel(threads, executorService, subIterations);
   }
}
