package pipe.gui.imperial.state;

import com.google.common.hash.HashCode;
import pipe.gui.imperial.utils.StateUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class HashedState implements State {
   private int hashOne;
   private HashCode hashTwo;
   private Map tokenCounts = new HashMap();

   public HashedState() {
   }

   public HashedState(Map tokenCounts) {
      this.tokenCounts.putAll(tokenCounts);
      this.hashOne = this.calculateHashOne().asInt();
      this.hashTwo = this.calculateHashTwo();
   }

   private HashCode calculateHashTwo() {
      return StateUtils.hashCodeForState(this, StateUtils.getSecondaryHash());
   }

   private HashCode calculateHashOne() {
      return StateUtils.hashCodeForState(this, StateUtils.getPrimaryHash());
   }

   public Map getTokenCounts() {
      return this.tokenCounts;
   }

   public void setTokenCounts(Map tokenCounts) {
      this.tokenCounts = tokenCounts;
   }

   public Map getTokens(String id) {
      return (Map)this.tokenCounts.get(id);
   }

   public boolean containsTokens(String id) {
      return this.tokenCounts.containsKey(id);
   }

   public Collection getPlaces() {
      return this.tokenCounts.keySet();
   }

   public int primaryHash() {
      return this.hashOne;
   }

   public HashCode secondaryHash() {
      return this.hashTwo;
   }

   public Map asMap() {
      return this.tokenCounts;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof HashedState)) {
         return false;
      } else {
         HashedState that = (HashedState)o;
         if (this.hashOne != that.hashOne) {
            return false;
         } else {
            return this.hashTwo.equals(that.hashTwo);
         }
      }
   }

   public int hashCode() {
      int result = this.hashOne;
      result = 31 * result + this.hashTwo.hashCode();
      return result;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("{");
      int count = 0;

      for(Iterator i$ = this.tokenCounts.entrySet().iterator(); i$.hasNext(); ++count) {
         Entry entry = (Entry)i$.next();
         builder.append("\"").append((String)entry.getKey()).append("\"").append(": {");
         int insideCount = 0;
         Iterator i$1 = ((Map)entry.getValue()).entrySet().iterator();

         while(i$1.hasNext()) {
            Entry entry1 = (Entry)i$1.next();
            builder.append("\"").append((String)entry1.getKey()).append("\"").append(": ").append(entry1.getValue());
            if (insideCount < ((Map)entry.getValue()).size() - 1) {
               builder.append(", ");
            }
         }

         builder.append("}");
         if (count < this.tokenCounts.size() - 1) {
            builder.append(", ");
         }
      }

      builder.append("}");
      return builder.toString();
   }
}
