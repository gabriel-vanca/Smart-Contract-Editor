package pipe.gui.imperial.pipe.models.petrinet;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import pipe.gui.imperial.pipe.models.petrinet.Annotation;
import pipe.gui.imperial.pipe.visitor.component.PetriNetComponentVisitor;

public class AnnotationImpl extends AbstractPetriNetPubSub implements Annotation {
   public static final String TEXT_CHANGE_MESSAGE = "text";
   public static final String TOGGLE_BORDER_CHANGE_MESSAGE = "toggleBorder";
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

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof AnnotationImpl)) {
         return false;
      } else {
         AnnotationImpl that = (AnnotationImpl)o;
         if (this.border != that.border) {
            return false;
         } else if (this.height != that.height) {
            return false;
         } else if (this.width != that.width) {
            return false;
         } else if (this.x != that.x) {
            return false;
         } else if (this.y != that.y) {
            return false;
         } else {
            return this.text.equals(that.text);
         }
      }
   }

   public int hashCode() {
      int result = this.border ? 1 : 0;
      result = 31 * result + this.x;
      result = 31 * result + this.y;
      result = 31 * result + this.text.hashCode();
      result = 31 * result + this.width;
      result = 31 * result + this.height;
      return result;
   }

   public AnnotationImpl(AnnotationImpl annotation) {
      this(annotation.x, annotation.y, annotation.text, annotation.width, annotation.height, annotation.border);
   }

   public AnnotationImpl(int x, int y, String text, int width, int height, boolean border) {
      this.border = border;
      this.x = x;
      this.y = y;
      this.text = text;
      this.width = width;
      this.height = height;
   }

   public final void setBorder(boolean border) {
      this.border = border;
   }

   public final boolean hasBorder() {
      return this.border;
   }

   public int getX() {
      return this.x;
   }

   public void setX(int x) {
      int old = this.x;
      this.x = x;
      this.changeSupport.firePropertyChange("x", old, x);
   }

   public int getY() {
      return this.y;
   }

   public final void setY(int y) {
      int old = this.y;
      this.y = y;
      this.changeSupport.firePropertyChange("y", old, y);
   }

   public int getHeight() {
      return this.height;
   }

   public int getWidth() {
      return this.width;
   }

   public void setWidth(int width) {
      this.width = width;
   }

   public void setHeight(int height) {
      this.height = height;
   }

   public String getText() {
      return this.text;
   }

   public void setText(String text) {
      String old = this.text;
      this.text = text;
      this.changeSupport.firePropertyChange("text", old, text);
      this.changeSupport.firePropertyChange("id", old, text);
   }

   public boolean isSelectable() {
      return true;
   }

   public boolean isDraggable() {
      return true;
   }

   public void accept(PetriNetComponentVisitor visitor) {
      if (visitor instanceof AnnotationVisitor) {
         ((AnnotationVisitor)visitor).visit(this);
      }

      if (visitor instanceof AnnotationImplVisitor) {
         ((AnnotationImplVisitor)visitor).visit(this);
      }

   }

   public String getId() {
      return this.getText();
   }

   public void setId(String id) {
   }

   public final void toggleBorder() {
      this.border = !this.border;
      this.changeSupport.firePropertyChange("toggleBorder", !this.border, this.border);
   }
}
