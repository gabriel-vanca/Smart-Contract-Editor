package pipe.gui.imperial.steadystate.algorithm;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class GaussSeidelSolver extends AXEqualsBSolver {
   private static final Logger LOGGER = Logger.getLogger(GaussSeidelSolver.class.getName());

   protected List solve(Map records, Map diagonalElements) {
      List x = this.initialiseXWithGuessList(records);
      boolean converged = false;

      int iterations;
      for(iterations = 0; !converged; ++iterations) {
         Iterator i$ = records.entrySet().iterator();

         while(i$.hasNext()) {
            Entry entry = (Entry)i$.next();
            Integer state = (Integer)entry.getKey();
            double aii = ((Double)diagonalElements.get(state)).doubleValue();
            double rowValue = this.getRowValue(state, (Map)entry.getValue(), aii, x);
            x.set(((Integer)this.stateToIndex.get(state)).intValue(), rowValue);
         }

         converged = this.hasConverged(records, diagonalElements, x);
      }

      LOGGER.log(Level.INFO, String.format("Took %d iterations to converge", iterations));
      return x;
   }
}
