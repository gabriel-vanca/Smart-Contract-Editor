package pipe.gui.imperial.pipe.models.petrinet;

import pipe.gui.imperial.pipe.models.petrinet.Rate;
import pipe.gui.imperial.pipe.models.petrinet.RateType;

public final class NormalRate implements Rate {
   public final String rate;

   public NormalRate(String rate) {
      this.rate = rate;
   }

   public String getExpression() {
      return this.rate;
   }

   public pipe.gui.imperial.pipe.models.petrinet.RateType getRateType() {
      return RateType.NORMAL_RATE;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         NormalRate that = (NormalRate)o;
         return this.rate.equals(that.rate);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.rate.hashCode();
   }
}
