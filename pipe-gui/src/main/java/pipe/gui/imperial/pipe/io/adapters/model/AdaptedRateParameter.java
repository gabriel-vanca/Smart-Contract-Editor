package pipe.gui.imperial.pipe.io.adapters.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public final class AdaptedRateParameter {
   @XmlAttribute
   private String name;
   @XmlAttribute
   private String expression;
   @XmlAttribute
   private String id;
   @XmlAttribute
   private String defType = "real";
   @XmlAttribute
   private String type = "text";

   public String getType() {
      return this.type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getExpression() {
      return this.expression;
   }

   public void setExpression(String expression) {
      this.expression = expression;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getDefType() {
      return this.defType;
   }

   public void setDefType(String defType) {
      this.defType = defType;
   }
}
