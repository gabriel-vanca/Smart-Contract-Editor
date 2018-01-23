package pipe.gui.imperial.pipe.dsl;

import pipe.gui.imperial.pipe.dsl.AnAbstractTransition;

public final class AnImmediateTransition extends AnAbstractTransition {
   private AnImmediateTransition(String id) {
      super(id, false);
   }

   public static AnImmediateTransition withId(String id) {
      return new AnImmediateTransition(id);
   }

   public AnImmediateTransition andProbability(String probability) {
      this.rate = probability;
      return this;
   }

   protected AnImmediateTransition getInstance() {
      return this;
   }
}
