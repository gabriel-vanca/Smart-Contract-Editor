package pipe.gui.imperial.reachability.algorithm;

import pipe.gui.imperial.state.ClassifiedState;

public final class StateRateRecord {
   private final ClassifiedState state;
   private double rate;

   public StateRateRecord(ClassifiedState state, double rate) {
      this.state = state;
      this.rate = rate;
   }

   public double getRate() {
      return this.rate;
   }

   public ClassifiedState getState() {
      return this.state;
   }

   public void setRate(double rate) {
      this.rate = rate;
   }
}
