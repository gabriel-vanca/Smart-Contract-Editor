package pipe.gui.imperial.pipe.io.adapters.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import pipe.gui.imperial.pipe.io.adapters.modelAdapter.ArcPointAdapter;
import pipe.gui.imperial.pipe.io.adapters.valueAdapter.StringAttributeValueAdaptor;

@XmlAccessorType(XmlAccessType.FIELD)
public class AdaptedArc {
   @XmlAttribute
   private String id;
   @XmlAttribute
   private String source;
   @XmlAttribute
   private String target;
   @XmlElement(
      name = "arcpath"
   )
   @XmlJavaTypeAdapter(ArcPointAdapter.class)
   private List arcPoints = new ArrayList();
   @XmlElement
   @XmlJavaTypeAdapter(StringAttributeValueAdaptor.class)
   private String type = "normal";
   private AdaptedArc.Inscription inscription = new AdaptedArc.Inscription();

   public final String getId() {
      return this.id;
   }

   public final void setId(String id) {
      this.id = id;
   }

   public final String getSource() {
      return this.source;
   }

   public final void setSource(String source) {
      this.source = source;
   }

   public final String getTarget() {
      return this.target;
   }

   public final void setTarget(String target) {
      this.target = target;
   }

   public final String getType() {
      return this.type;
   }

   public final void setType(String type) {
      this.type = type;
   }

   public final AdaptedArc.Inscription getInscription() {
      return this.inscription;
   }

   public final void setInscription(AdaptedArc.Inscription inscription) {
      this.inscription = inscription;
   }

   public final List getArcPoints() {
      return this.arcPoints;
   }

   public final void setArcPoints(List arcPoints) {
      this.arcPoints = arcPoints;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   public static class Inscription {
      @XmlElement(
         name = "value"
      )
      private String tokenCounts = "";
      private OffsetGraphics graphics;

      public String getTokenCounts() {
         return this.tokenCounts;
      }

      public void setTokenCounts(String tokenCounts) {
         this.tokenCounts = tokenCounts;
      }

      public OffsetGraphics getGraphics() {
         return this.graphics;
      }

      public void setGraphics(OffsetGraphics graphics) {
         this.graphics = graphics;
      }
   }
}
