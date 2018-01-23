package pipe.gui.imperial.pipe.io.adapters.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AdaptedAnnotation {
   @XmlAttribute
   private boolean border;
   @XmlAttribute
   private int x;
   @XmlAttribute
   private int y;
   @XmlElement
   private String text;
   @XmlAttribute
   private int width;
   @XmlAttribute
   private int height;

   public final void setBorder(boolean border) {
      this.border = border;
   }

   public final boolean hasBoarder() {
      return this.border;
   }

   public final int getX() {
      return this.x;
   }

   public final void setX(int x) {
      this.x = x;
   }

   public final int getY() {
      return this.y;
   }

   public final void setY(int y) {
      this.y = y;
   }

   public final int getHeight() {
      return this.height;
   }

   public final void setHeight(int height) {
      this.height = height;
   }

   public final int getWidth() {
      return this.width;
   }

   public final void setWidth(int width) {
      this.width = width;
   }

   public final String getText() {
      return this.text;
   }

   public final void setText(String text) {
      this.text = text;
   }
}
