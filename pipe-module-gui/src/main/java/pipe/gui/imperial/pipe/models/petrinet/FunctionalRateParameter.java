package pipe.gui.imperial.pipe.models.petrinet;

import pipe.gui.imperial.pipe.exceptions.InvalidRateException;
import pipe.gui.imperial.pipe.exceptions.PetriNetComponentException;
import pipe.gui.imperial.pipe.models.petrinet.AbstractPetriNetPubSub;
import pipe.gui.imperial.pipe.models.petrinet.RateParameter;
import pipe.gui.imperial.pipe.models.petrinet.RateParameterVisitor;
import pipe.gui.imperial.pipe.models.petrinet.RateType;
import pipe.gui.imperial.pipe.visitor.component.PetriNetComponentVisitor;

public final class FunctionalRateParameter extends AbstractPetriNetPubSub implements RateParameter {
   private String expression;
   private String id;
   private String name;

   public FunctionalRateParameter(FunctionalRateParameter rateParameter) {
      this(rateParameter.expression, rateParameter.id, rateParameter.name);
   }

   public FunctionalRateParameter(String expression, String id, String name) {
      this.expression = expression;
      this.id = id;
      this.name = name;
   }

   public String getExpression() {
      return this.expression;
   }

   public pipe.gui.imperial.pipe.models.petrinet.RateType getRateType() {
      return RateType.RATE_PARAMETER;
   }

   public void setExpression(String expression) {
      String old = this.expression;
      this.expression = expression;
      this.changeSupport.firePropertyChange("expression", old, expression);
   }

   public boolean isSelectable() {
      return false;
   }

   public boolean isDraggable() {
      return false;
   }

   public void accept(PetriNetComponentVisitor visitor) throws PetriNetComponentException {
      if (visitor instanceof pipe.gui.imperial.pipe.models.petrinet.RateParameterVisitor) {
         try {
            ((RateParameterVisitor)visitor).visit(this);
         } catch (InvalidRateException var3) {
            throw new PetriNetComponentException(var3);
         }
      }

      if (visitor instanceof FunctionalRateParameterVisitor) {
         ((FunctionalRateParameterVisitor)visitor).visit(this);
      }

   }

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      String old = this.id;
      this.id = id;
      this.changeSupport.firePropertyChange("id", old, id);
   }

   public int hashCode() {
      int result = this.expression.hashCode();
      result = 31 * result + this.id.hashCode();
      result = 31 * result + this.name.hashCode();
      return result;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         FunctionalRateParameter that = (FunctionalRateParameter)o;
         if (!this.expression.equals(that.expression)) {
            return false;
         } else if (!this.id.equals(that.id)) {
            return false;
         } else {
            return this.name.equals(that.name);
         }
      } else {
         return false;
      }
   }

   public String toString() {
      return this.id + ": " + this.expression;
   }
}
