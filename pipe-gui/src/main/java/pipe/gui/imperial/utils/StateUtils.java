package pipe.gui.imperial.utils;

import com.google.common.hash.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import pipe.gui.imperial.state.ClassifiedState;
import pipe.gui.imperial.state.HashedClassifiedState;
import pipe.gui.imperial.state.HashedState;
import pipe.gui.imperial.state.State;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public final class StateUtils {
   public static State stateFromJson(String jsonState) throws IOException {
      ObjectMapper mapper = new ObjectMapper();
      Map map = (Map)mapper.readValue(jsonState, new TypeReference() {
      });
      return new HashedState(map);
   }

   public static ClassifiedState vanishingStateFromJson(String jsonState) throws IOException {
      State state = stateFromJson(jsonState);
      return HashedClassifiedState.vanishingState(state);
   }

   public static ClassifiedState tangibleStateFromJson(String jsonState) throws IOException {
      State state = stateFromJson(jsonState);
      return HashedClassifiedState.tangibleState(state);
   }

   public static Funnel getFunnel(final Collection placeOrdering) {
      return new Funnel() {
         @Override
         public void funnel(Object from, PrimitiveSink into) {

         }

         public void funnel(State from, PrimitiveSink into) {
            Iterator i$ = placeOrdering.iterator();

            while(i$.hasNext()) {
               String place = (String)i$.next();
               into.putBytes(place.getBytes());
               Iterator i$x = from.getTokens(place).entrySet().iterator();

               while(i$x.hasNext()) {
                  Entry entry = (Entry)i$x.next();
                  into.putBytes(((String)entry.getKey()).getBytes());
                  into.putInt(((Integer)entry.getValue()).intValue());
               }
            }

         }
      };
   }

   public static HashFunction getPrimaryHash() {
      return Hashing.adler32();
   }

   public static HashFunction getSecondaryHash() {
      return Hashing.murmur3_128();
   }

   public static HashCode hashCodeForState(State state, HashFunction hf) {
      List placeOrdering = getOrdering(state);
      Funnel funnel = getFunnel(placeOrdering);
      return hf.newHasher().putObject(state, funnel).hash();
   }

   private static List getOrdering(State state) {
      List placeOrdering = new ArrayList(state.getPlaces());
      Collections.sort(placeOrdering);
      return placeOrdering;
   }
}
