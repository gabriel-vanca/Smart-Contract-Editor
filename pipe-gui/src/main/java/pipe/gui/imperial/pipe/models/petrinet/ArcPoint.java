package pipe.gui.imperial.pipe.models.petrinet;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import pipe.gui.imperial.pipe.visitor.component.PetriNetComponentVisitor;

public class ArcPoint extends AbstractPetriNetPubSub implements PlaceablePetriNetComponent {
   public static final String UPDATE_CURVED_CHANGE_MESSAGE = "updateCurved";
   public static final String UPDATE_LOCATION_CHANGE_MESSAGE = "updateLocation";
   private int x;
   private int y;
   private boolean curved;
   private final boolean draggable;

   public ArcPoint(Point2D point, boolean curved) {
      this(point, curved, true);
   }

   public ArcPoint(Point2D point, boolean curved, boolean draggable) {
      this.setPoint(point);
      this.curved = curved;
      this.draggable = draggable;
   }

   public ArcPoint(ArcPoint arcPoint) {
      this.x = arcPoint.x;
      this.y = arcPoint.y;
      this.curved = arcPoint.curved;
      this.draggable = arcPoint.draggable;
   }

   public Point2D getPoint() {
      return new Double((double)this.x, (double)this.y);
   }

   public void setPoint(Point2D point) {
      Point2D old = new Double((double)this.x, (double)this.y);
      this.x = (int)Math.round(point.getX());
      this.y = (int)Math.round(point.getY());
      this.changeSupport.firePropertyChange("updateLocation", old, point);
   }

   public int getX() {
      return this.x;
   }

   public void setX(int x) {
      this.setPoint(new Double((double)x, (double)this.y));
   }

   public int getY() {
      return this.y;
   }

   public void setY(int y) {
      this.setPoint(new Double((double)this.x, (double)y));
   }

   public int getHeight() {
      return 0;
   }

   public int getWidth() {
      return 0;
   }

   public boolean isSelectable() {
      return false;
   }

   public boolean isDraggable() {
      return this.draggable;
   }

   public void accept(PetriNetComponentVisitor visitor) {
      if (visitor instanceof ArcPointVisitor) {
         ((ArcPointVisitor)visitor).visit(this);
      }

   }

   public String getId() {
      return "";
   }

   public void setId(String id) {
   }

   public boolean isCurved() {
      return this.curved;
   }

   public void setCurved(boolean curved) {
      boolean old = this.curved;
      this.curved = curved;
      this.changeSupport.firePropertyChange("updateCurved", old, curved);
   }

   public int hashCode() {
      long temp = java.lang.Double.doubleToLongBits((double)this.x);
      int result = (int)(temp ^ temp >>> 32);
      temp = java.lang.Double.doubleToLongBits((double)this.y);
      result = 31 * result + (int)(temp ^ temp >>> 32);
      result = 31 * result + (this.curved ? 1 : 0);
      return result;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ArcPoint arcPoint = (ArcPoint)o;
         if (this.curved != arcPoint.curved) {
            return false;
         } else if (java.lang.Double.compare((double)arcPoint.x, (double)this.x) != 0) {
            return false;
         } else {
            return java.lang.Double.compare((double)arcPoint.y, (double)this.y) == 0;
         }
      } else {
         return false;
      }
   }
}
