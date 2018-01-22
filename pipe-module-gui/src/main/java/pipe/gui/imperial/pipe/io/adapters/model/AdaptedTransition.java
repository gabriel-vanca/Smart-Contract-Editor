package pipe.gui.imperial.pipe.io.adapters.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import pipe.gui.imperial.pipe.io.adapters.valueAdapter.BooleanValueAdapter;
import pipe.gui.imperial.pipe.io.adapters.valueAdapter.IntValueAdapter;
import pipe.gui.imperial.pipe.io.adapters.valueAdapter.StringValueAdapter;

public class AdaptedTransition extends AdaptedConnectable {
   @XmlJavaTypeAdapter(BooleanValueAdapter.class)
   private Boolean infiniteServer = false;
   @XmlJavaTypeAdapter(BooleanValueAdapter.class)
   private Boolean timed = false;
   @XmlElement(
      name = "priority"
   )
   @XmlJavaTypeAdapter(IntValueAdapter.class)
   private Integer priority = Integer.valueOf(0);
   @XmlElement(
      name = "orientation"
   )
   @XmlJavaTypeAdapter(IntValueAdapter.class)
   private Integer angle = Integer.valueOf(0);
   @XmlElement(
      name = "rate"
   )
   @XmlJavaTypeAdapter(StringValueAdapter.class)
   private String rate = "";
   @XmlElement(
      name = "toolspecific"
   )
   private AdaptedTransition.ToolSpecific toolSpecific;

   public final Boolean getTimed() {
      return this.timed;
   }

   public final void setTimed(Boolean timed) {
      this.timed = timed;
   }

   public final Boolean getInfiniteServer() {
      return this.infiniteServer;
   }

   public final void setInfiniteServer(Boolean infiniteServer) {
      this.infiniteServer = infiniteServer;
   }

   public final int getPriority() {
      return this.priority.intValue();
   }

   public final void setPriority(int priority) {
      this.priority = priority;
   }

   public final int getAngle() {
      return this.angle.intValue();
   }

   public final void setAngle(int angle) {
      this.angle = angle;
   }

   public final String getRate() {
      return this.rate;
   }

   public final void setRate(String rate) {
      this.rate = rate;
   }

   public final AdaptedTransition.ToolSpecific getToolSpecific() {
      return this.toolSpecific;
   }

   public final void setToolSpecific(AdaptedTransition.ToolSpecific toolSpecific) {
      this.toolSpecific = toolSpecific;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   public static class ToolSpecific {
      @XmlAttribute
      private String tool = "PIPE";
      @XmlAttribute
      private String version = "5";
      @XmlAttribute
      private String rateDefinition;

      public final String getTool() {
         return this.tool;
      }

      public final void setTool(String tool) {
         this.tool = tool;
      }

      public final String getVersion() {
         return this.version;
      }

      public final void setVersion(String version) {
         this.version = version;
      }

      public final String getRateDefinition() {
         return this.rateDefinition;
      }

      public final void setRateDefinition(String rateDefinition) {
         this.rateDefinition = rateDefinition;
      }
   }
}
