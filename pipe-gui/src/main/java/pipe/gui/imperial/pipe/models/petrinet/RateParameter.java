package pipe.gui.imperial.pipe.models.petrinet;

import pipe.gui.imperial.pipe.models.petrinet.Rate;

public interface RateParameter extends PetriNetComponent, Rate {
   String EXPRESSION_CHANGE_MESSAGE = "expression";

   void setExpression(String var1);
}
