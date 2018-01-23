package pipe.gui.imperial.steadystate.algorithm;

import java.util.concurrent.ExecutorService;

public interface SteadyStateBuilder {
   SteadyStateSolver buildGaussSeidel();

   SteadyStateSolver buildSequentialJacobi();

   SteadyStateSolver buildBoundedSequentialJacobi(int var1);

   SteadyStateSolver buildJacobi(ExecutorService var1, int var2);

   SteadyStateSolver buildBoundedParallelJacobiSolver(ExecutorService var1, int var2, int var3);

   SteadyStateSolver buildAsynchronousGaussSeidel(ExecutorService var1, int var2, int var3);
}
