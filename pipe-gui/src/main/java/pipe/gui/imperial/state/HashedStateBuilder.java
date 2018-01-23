package pipe.gui.imperial.state;

import pipe.gui.imperial.state.HashedState;

import java.util.HashMap;
import java.util.Map;

public final class HashedStateBuilder {
   private Map tokenCounts = new HashMap();

   public HashedStateBuilder placeWithToken(String place, String token, int count) {
      Map tokens = this.getTokens(place);
      tokens.put(token, count);
      return this;
   }

   public HashedStateBuilder placeWithTokens(String place, Map tokenValues) {
      Map tokens = this.getTokens(place);
      tokens.putAll(tokenValues);
      return this;
   }

   private Map getTokens(String place) {
      if (!this.tokenCounts.containsKey(place)) {
         this.tokenCounts.put(place, new HashMap());
      }

      return (Map)this.tokenCounts.get(place);
   }

   public pipe.gui.imperial.state.HashedState build() {
      return new HashedState(this.tokenCounts);
   }
}
