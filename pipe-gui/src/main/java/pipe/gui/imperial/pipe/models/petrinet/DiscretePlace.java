package pipe.gui.imperial.pipe.models.petrinet;

import pipe.constants.GUIConstants;
import pipe.gui.imperial.pipe.exceptions.PetriNetComponentException;
import pipe.gui.imperial.pipe.visitor.component.PetriNetComponentVisitor;
import pipe.ucl.contract.models.ContractElement;
import pipe.ucl.contract.models.StateElement;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class DiscretePlace extends AbstractConnectable implements Place {
   private double markingXOffset = 0.0D;
   private double markingYOffset = 0.0D;
   private int capacity = 0;
   private Map tokenCounts = new HashMap();
   private String time;
   private StateElement stateElement;
   

   public DiscretePlace(String id, String name, StateElement stateElement) {
      super(id, name);
      this.stateElement = stateElement;
   }

   public DiscretePlace(String id) {
      super(id, id);
   }

   @Override
   public ContractElement getAttachedContractElement() {
      return stateElement;
   }

   public DiscretePlace(String id, String name) {
      super(id, name);
   }

   public DiscretePlace(DiscretePlace place) {
      super(place);
      this.capacity = place.capacity;
      this.markingXOffset = place.markingXOffset;
      this.markingYOffset = place.markingYOffset;
      this.stateElement = place.stateElement;
   }

   public StateElement getStateElement() {
      return stateElement;
   }

   @Override
   public String toString() {

      if (stateElement != null) {
         return stateElement.toString();
      }

      String string = this.id;
      if (this.name != null && this.name.length() > 0)
         string += ":" + this.name;

      return string;

   }

   public String getTime(){
       return this.time;
   }

   public void SetTime(String time) {
       this.time = time;
   }

   public boolean isSelectable() {
      return true;
   }

   public boolean isDraggable() {
      return true;
   }

   public void accept(PetriNetComponentVisitor visitor) throws PetriNetComponentException {
      if (visitor instanceof pipe.gui.imperial.pipe.models.petrinet.PlaceVisitor) {
         ((PlaceVisitor)visitor).visit(this);
      }

      if (visitor instanceof DiscretePlaceVisitor) {
         ((DiscretePlaceVisitor)visitor).visit(this);
      }

   }

   public double getMarkingXOffset() {
      return this.markingXOffset;
   }

   public void setMarkingXOffset(double markingXOffset) {
      this.markingXOffset = markingXOffset;
   }

   public double getMarkingYOffset() {
      return this.markingYOffset;
   }

   public void setMarkingYOffset(double markingYOffset) {
      this.markingYOffset = markingYOffset;
   }

   public int getCapacity() {
      return this.capacity;
   }

   public void setCapacity(int capacity) {
      this.capacity = capacity;
   }

   public Map getTokenCounts() {
      return this.tokenCounts;
   }

   public void setTokenCounts(Map tokenCounts) {
      if (this.hasCapacityRestriction()) {
         int count = this.getNumberOfTokensStored(tokenCounts);
         if (count > this.capacity) {
            throw new RuntimeException("Count of tokens exceeds capacity!");
         }
      }

      Map old = new HashMap(this.tokenCounts);
      this.tokenCounts = new HashMap(tokenCounts);
      this.changeSupport.firePropertyChange("tokens", old, tokenCounts);
   }

   public boolean hasCapacityRestriction() {
      return this.capacity > 0;
   }

   private int getNumberOfTokensStored(Map tokens) {
      int sum = 0;

      Integer value;
      for(Iterator i$ = tokens.values().iterator(); i$.hasNext(); sum += value.intValue()) {
         value = (Integer)i$.next();
      }

      return sum;
   }

   public void incrementTokenCount(String token) {
      Integer count;
      if (this.tokenCounts.containsKey(token)) {
         count = (Integer)this.tokenCounts.get(token);
         count = count.intValue() + 1;
      } else {
         count = Integer.valueOf(1);
      }

      Map old = new HashMap(this.tokenCounts);
      this.setTokenCount(token, count.intValue());
      this.changeSupport.firePropertyChange("tokens", old, this.tokenCounts);
   }

   public void setTokenCount(String token, int count) {
      if (this.hasCapacityRestriction()) {
         int currentTokenCount = this.getNumberOfTokensStored();
         int countMinusToken = currentTokenCount - this.getTokenCount(token);
         if (countMinusToken + count > this.capacity) {
            throw new RuntimeException("Cannot set token count that exceeds the capacity of " + count);
         }
      }

      Map old = new HashMap(this.tokenCounts);
      this.tokenCounts.put(token, count);
      this.changeSupport.firePropertyChange("tokens", old, this.tokenCounts);
   }

   public int getNumberOfTokensStored() {
      return this.getNumberOfTokensStored(this.tokenCounts);
   }

   public int getTokenCount(String token) {
      return this.tokenCounts.containsKey(token) ? ((Integer)this.tokenCounts.get(token)).intValue() : 0;
   }

   public void decrementTokenCount(String token) {
      Map old = new HashMap(this.tokenCounts);
      if (this.tokenCounts.containsKey(token)) {
         Integer count = (Integer)this.tokenCounts.get(token);
         count = count.intValue() - 1;
         this.tokenCounts.put(token, count);
      }

      this.changeSupport.firePropertyChange("tokens", old, this.tokenCounts);
   }

   public int hashCode() {
      long temp = Double.doubleToLongBits(this.markingXOffset);
      int result = (int)(temp ^ temp >>> 32);
      temp = Double.doubleToLongBits(this.markingYOffset);
      result = 31 * result + (int)(temp ^ temp >>> 32);
      temp = Double.doubleToLongBits((double)this.capacity);
      result = 31 * result + (int)(temp ^ temp >>> 32);
      result = 31 * result + this.tokenCounts.hashCode();
      return result;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         DiscretePlace place = (DiscretePlace)o;
         if (!super.equals(place)) {
            return false;
         } else if (Double.compare((double)place.capacity, (double)this.capacity) != 0) {
            return false;
         } else if (Double.compare(place.markingXOffset, this.markingXOffset) != 0) {
            return false;
         } else if (Double.compare(place.markingYOffset, this.markingYOffset) != 0) {
            return false;
         } else {
            return this.tokenCounts.equals(place.tokenCounts);
         }
      } else {
         return false;
      }
   }

   public java.awt.geom.Point2D.Double getCentre() {
      return new java.awt.geom.Point2D.Double((double)(this.getX() + this.getWidth() / 2), (double)(this.getY() + this.getHeight() / 2));
   }

   public int getHeight() {
      return GUIConstants.PLACE_WIDTH_HEIGHT;
   }

   public int getWidth() {
      return GUIConstants.PLACE_WIDTH_HEIGHT;
   }

   public java.awt.geom.Point2D.Double getArcEdgePoint(double angle) {
      double radius = 15.0D;
      double centreX = this.x + radius;
      double opposite = Math.cos(angle);
      double attachX = centreX - radius * opposite;
      double centreY = this.y + radius;
      double adjacent = Math.sin(angle);
      double attachY = centreY - radius * adjacent;
      return new java.awt.geom.Point2D.Double(attachX, attachY);
   }

   public boolean isEndPoint() {
      return true;
   }

   @Override
   public String toLongString() {
      if(stateElement != null)
         return stateElement.toLongString();
      else return "";
   }

   public void removeAllTokens(String token) {
      this.tokenCounts.remove(token);
   }
}
