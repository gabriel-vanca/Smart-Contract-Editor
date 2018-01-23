package pipe.gui.imperial.steadystate.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AXEqualsBSolver extends AbstractSteadyStateSolver {
   private static final Logger LOGGER = Logger.getLogger(AXEqualsBSolver.class.getName());
   protected Map stateToIndex = new HashMap();

   public final Map solve(List A) {
      Map diagonals = this.calculateDiagonals(A);
      double a = this.geta(diagonals);
      List Q = this.divide(A, -a);
      Map newDiagonals = this.divide(-a, diagonals);
      Map QTranspose = this.transpose(Q);
      return this.timeAndSolve(QTranspose, newDiagonals);
   }

   private Map timeAndSolve(Map QTranspose, Map diagonals) {
      long startTime = System.nanoTime();
      List results = this.solve(QTranspose, diagonals);
      long finishTime = System.nanoTime();
      long duration = finishTime - startTime;
      Map x = this.normalize(results);
      LOGGER.log(Level.INFO, "Steady state solved in " + duration);
      return x;
   }

   protected final Map normalize(List x) {
      double sum = 0.0D;

      double value;
      for(Iterator i$ = x.iterator(); i$.hasNext(); sum += value) {
         value = ((Double)i$.next()).doubleValue();
      }

      Map normalized = new HashMap();
      Iterator i$ = this.stateToIndex.entrySet().iterator();

      while(i$.hasNext()) {
         Entry entry = (Entry)i$.next();
         Integer state = (Integer)entry.getKey();
         Integer index = (Integer)entry.getValue();
         if (sum == 0.0D) {
            normalized.put(state, x.get(index.intValue()));
         } else {
            normalized.put(state, ((Double)x.get(index.intValue())).doubleValue() / sum);
         }
      }

      return normalized;
   }

   protected abstract List solve(Map var1, Map var2);

   protected final double multiplyAndSum(Map row, List x) {
      double sum = 0.0D;

      Integer state;
      Double rate;
      for(Iterator i$ = row.entrySet().iterator(); i$.hasNext(); sum += rate.doubleValue() * ((Double)x.get(state.intValue())).doubleValue()) {
         Entry entry = (Entry)i$.next();
         state = (Integer)entry.getKey();
         rate = (Double)entry.getValue();
      }

      return sum;
   }

   protected final double multiplyAndSum(Map row, AtomicReferenceArray x) {
      double sum = 0.0D;

      Integer state;
      Double rate;
      for(Iterator i$ = row.entrySet().iterator(); i$.hasNext(); sum += rate.doubleValue() * ((Double)x.get(state.intValue())).doubleValue()) {
         Entry entry = (Entry)i$.next();
         state = (Integer)entry.getKey();
         rate = (Double)entry.getValue();
      }

      return sum;
   }

   protected final boolean hasConverged(Map records, Map diagonals, List x) {
      Iterator i$ = records.entrySet().iterator();

      double rowValue;
      do {
         if (!i$.hasNext()) {
            return this.isPlausible(x);
         }

         Entry entry = (Entry)i$.next();
         int state = ((Integer)entry.getKey()).intValue();
         rowValue = this.multiplyAndSum((Map)entry.getValue(), x);
         rowValue += ((Double)diagonals.get(state)).doubleValue() * ((Double)x.get(state)).doubleValue();
      } while(rowValue < 1.0E-6D);

      return false;
   }

   protected final boolean hasConverged(Map records, Map diagonals, AtomicReferenceArray x) {
      Iterator i$ = records.entrySet().iterator();

      double rowValue;
      do {
         if (!i$.hasNext()) {
            return this.isPlausible(x);
         }

         Entry entry = (Entry)i$.next();
         int state = ((Integer)entry.getKey()).intValue();
         rowValue = this.multiplyAndSum((Map)entry.getValue(), x);
         rowValue += ((Double)diagonals.get(state)).doubleValue() * ((Double)x.get(state)).doubleValue();
      } while(rowValue < 1.0E-6D);

      return false;
   }

   private boolean isPlausible(List x) {
      Iterator i$ = x.iterator();

      double value;
      do {
         if (!i$.hasNext()) {
            return true;
         }

         value = ((Double)i$.next()).doubleValue();
      } while(value >= 0.0D);

      return false;
   }

   private boolean isPlausible(AtomicReferenceArray x) {
      for(int i = 0; i < x.length(); ++i) {
         double value = ((Double)x.get(i)).doubleValue();
         if (value < 0.0D) {
            return false;
         }
      }

      return true;
   }

   protected final double getRowValue(Integer state, Map row, double aii, List x) {
      if (aii == 0.0D) {
         return ((Double)x.get(state.intValue())).doubleValue();
      } else {
         double rowSum = this.multiplyAndSum(row, x);
         return -rowSum / aii;
      }
   }

   protected final double getRowValue(Integer state, Map row, double aii, AtomicReferenceArray x) {
      if (aii == 0.0D) {
         return ((Double)x.get(state.intValue())).doubleValue();
      } else {
         double rowSum = this.multiplyAndSum(row, x);
         return -rowSum / aii;
      }
   }

   protected final List initialiseXWithGuessList(Map records) {
      List x = new ArrayList();
      int index = 0;

      for(Iterator i$ = records.keySet().iterator(); i$.hasNext(); ++index) {
         Integer state = (Integer)i$.next();
         this.stateToIndex.put(index, state);
         x.add(1.0D);
      }

      return x;
   }
}
