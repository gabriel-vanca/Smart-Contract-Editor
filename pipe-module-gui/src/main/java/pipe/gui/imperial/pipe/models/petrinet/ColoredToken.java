package pipe.gui.imperial.pipe.models.petrinet;

import java.awt.Color;
import pipe.gui.imperial.pipe.exceptions.PetriNetComponentException;
import pipe.gui.imperial.pipe.models.petrinet.Token;
import pipe.gui.imperial.pipe.visitor.component.PetriNetComponentVisitor;

public class ColoredToken extends AbstractPetriNetPubSub implements pipe.gui.imperial.pipe.models.petrinet.Token {
   private String id;
   private Color color;

   public ColoredToken(String id, Color color) {
      this.id = id;
      this.color = color;
   }

   public ColoredToken(Token token) {
      this.id = token.getId();
      this.color = token.getColor();
   }

   public Color getColor() {
      return this.color;
   }

   public void setColor(Color color) {
      Color old = this.color;
      this.color = color;
      this.changeSupport.firePropertyChange("color", old, color);
   }

   public boolean isSelectable() {
      return false;
   }

   public boolean isDraggable() {
      return false;
   }

   public void accept(PetriNetComponentVisitor visitor) throws PetriNetComponentException {
      if (visitor instanceof TokenVisitor) {
         ((TokenVisitor)visitor).visit(this);
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
      int result = this.id.hashCode();
      result = 31 * result + this.color.hashCode();
      return result;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ColoredToken token = (ColoredToken)o;
         if (!this.color.equals(token.color)) {
            return false;
         } else {
            return this.id.equals(token.id);
         }
      } else {
         return false;
      }
   }

   public String toString() {
      return this.getId();
   }
}
