package pipe.gui.imperial.state;

import com.google.common.hash.HashCode;
import pipe.gui.imperial.state.ClassifiedState;
import pipe.gui.imperial.state.State;

import java.util.Collection;
import java.util.Map;

public final class HashedClassifiedState implements ClassifiedState {
   private pipe.gui.imperial.state.State state;
   private boolean tangible;

   public HashedClassifiedState() {
   }

   public HashedClassifiedState(pipe.gui.imperial.state.State state, boolean tangible) {
      this.state = state;
      this.tangible = tangible;
   }

   public static HashedClassifiedState tangibleState(pipe.gui.imperial.state.State state) {
      return new HashedClassifiedState(state, true);
   }

   public static HashedClassifiedState vanishingState(pipe.gui.imperial.state.State state) {
      return new HashedClassifiedState(state, false);
   }

   public pipe.gui.imperial.state.State getState() {
      return this.state;
   }

   public void setState(State state) {
      this.state = state;
   }

   public int hashCode() {
      int result = this.state.hashCode();
      result = 31 * result + (this.tangible ? 1 : 0);
      return result;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof HashedClassifiedState)) {
         return false;
      } else {
         HashedClassifiedState that = (HashedClassifiedState)o;
         if (this.tangible != that.tangible) {
            return false;
         } else {
            return this.state.equals(that.state);
         }
      }
   }

   public boolean isTangible() {
      return this.tangible;
   }

   public void setTangible(boolean tangible) {
      this.tangible = tangible;
   }

   public Map getTokens(String id) {
      return this.state.getTokens(id);
   }

   public boolean containsTokens(String id) {
      return this.state.containsTokens(id);
   }

   public Collection getPlaces() {
      return this.state.getPlaces();
   }

   public int primaryHash() {
      return this.state.primaryHash();
   }

   public HashCode secondaryHash() {
      return this.state.secondaryHash();
   }

   public Map asMap() {
      return this.state.asMap();
   }

   public String toString() {
      String stateString = this.state.toString();
      return this.isTangible() ? "Tangible: " + stateString : "Vanishing: " + stateString;
   }
}
