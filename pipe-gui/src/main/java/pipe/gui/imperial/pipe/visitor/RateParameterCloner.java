package pipe.gui.imperial.pipe.visitor;

import pipe.gui.imperial.pipe.models.petrinet.FunctionalRateParameter;
import pipe.gui.imperial.pipe.models.petrinet.FunctionalRateParameterVisitor;
import pipe.gui.imperial.pipe.models.petrinet.RateParameter;

public final class RateParameterCloner implements FunctionalRateParameterVisitor {
   public RateParameter cloned;

   public void visit(FunctionalRateParameter rateParameter) {
      this.cloned = new FunctionalRateParameter(rateParameter);
   }
}
