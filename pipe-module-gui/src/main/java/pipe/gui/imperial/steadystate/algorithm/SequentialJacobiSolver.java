package pipe.gui.imperial.steadystate.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

class SequentialJacobiSolver extends AXEqualsBSolver {
   private static final Logger LOGGER = Logger.getLogger(GaussSeidelSolver.class.getName());
   private final int maxIterations;
   private boolean bounded = false;

   public SequentialJacobiSolver() {
      this.maxIterations = 0;
   }

   public SequentialJacobiSolver(int maxIterations) {
      this.maxIterations = maxIterations;
      this.bounded = true;
   }

   protected List solve(Map records, Map diagonalElements) {
      List x = this.initialiseXWithGuessList(records);
      List previous = new ArrayList(x);
      boolean converged = false;
      int iterations = 0;

      while(!converged && this.canContinue(iterations)) {
         Iterator i$ = records.entrySet().iterator();

         while(i$.hasNext()) {
            Entry entry = (Entry)i$.next();
            Integer state = (Integer)entry.getKey();
            double aii = ((Double)diagonalElements.get(state)).doubleValue();
            double rowValue = this.getRowValue(state, (Map)entry.getValue(), aii, previous);
            x.set(state.intValue(), rowValue);
         }

         converged = this.hasConverged(records, diagonalElements, x);
         ++iterations;
         previous.clear();
         previous.addAll(x);
      }

      LOGGER.log(Level.INFO, String.format("Sequential Jacobi took %d iterations to converge", iterations));
      return x;
   }

   private boolean canContinue(int iterations) {
      if (this.bounded && iterations >= this.maxIterations) {
         LOGGER.log(Level.INFO, "Reached maximum number of iterations. Cutting short!");
      }

      return !this.bounded || iterations < this.maxIterations;
   }
}
