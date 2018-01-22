package pipe.gui.imperial.pipe.dsl;

import java.util.Map;

import pipe.gui.imperial.pipe.dsl.DSLCreator;
import pipe.gui.imperial.pipe.models.petrinet.FunctionalRateParameter;

public final class ARateParameter implements DSLCreator {
   private final String id;
   private String expression;

   private ARateParameter(String id) {
      this.id = id;
   }

   public static ARateParameter withId(String id) {
      return new ARateParameter(id);
   }

   public ARateParameter andExpression(String expression) {
      this.expression = expression;
      return this;
   }

   public FunctionalRateParameter create(Map tokens, Map places, Map transitions, Map rateParameters) {
      FunctionalRateParameter rateParameter = new FunctionalRateParameter(this.expression, this.id, this.id);
      rateParameters.put(this.id, rateParameter);
      return rateParameter;
   }
}
