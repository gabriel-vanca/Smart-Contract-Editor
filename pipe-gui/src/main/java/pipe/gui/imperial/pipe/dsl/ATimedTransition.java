package pipe.gui.imperial.pipe.dsl;

public final class ATimedTransition extends AnAbstractTransition {
   private ATimedTransition(String id) {
      super(id, true);
   }

   public static ATimedTransition withId(String id) {
      return new ATimedTransition(id);
   }

   protected ATimedTransition getInstance() {
      return this;
   }

   public ATimedTransition withRateParameter(String rateParameterName) {
      this.rateParameter = rateParameterName;
      return this.getInstance();
   }

   public ATimedTransition andRate(String rate) {
      this.rate = rate;
      return this.getInstance();
   }
}
