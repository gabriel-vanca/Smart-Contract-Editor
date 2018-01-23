package pipe.gui.imperial.pipe.io.adapters.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import pipe.gui.imperial.pipe.io.adapters.valueAdapter.IntValueAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class AdaptedPlace extends AdaptedConnectable {
   @XmlJavaTypeAdapter(IntValueAdapter.class)
   private Integer capacity = Integer.valueOf(0);
   private AdaptedPlace.InitialMarking initialMarking = new AdaptedPlace.InitialMarking();

   public final AdaptedPlace.InitialMarking getInitialMarking() {
      return this.initialMarking;
   }

   public final void setInitialMarking(AdaptedPlace.InitialMarking initialMarking) {
      this.initialMarking = initialMarking;
   }

   public final Integer getCapacity() {
      return this.capacity;
   }

   public final void setCapacity(Integer capacity) {
      this.capacity = capacity;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   public static class InitialMarking {
      private OffsetGraphics graphics;
      @XmlElement(
         name = "value"
      )
      private String tokenCounts = "";

      public final String getTokenCounts() {
         return this.tokenCounts;
      }

      public final void setTokenCounts(String tokenCounts) {
         this.tokenCounts = tokenCounts;
      }

      public final OffsetGraphics getGraphics() {
         return this.graphics;
      }

      public final void setGraphics(OffsetGraphics graphics) {
         this.graphics = graphics;
      }
   }
}
