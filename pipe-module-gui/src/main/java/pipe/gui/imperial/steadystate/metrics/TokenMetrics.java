package pipe.gui.imperial.steadystate.metrics;

import pipe.gui.imperial.state.ClassifiedState;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class TokenMetrics {
   public static Map averageTokensOnPlace(Map stateSpace, Map steadyState) {
      Map averages = new HashMap();
      Iterator i$ = stateSpace.entrySet().iterator();

      while(i$.hasNext()) {
         Entry entry = (Entry)i$.next();
         int id = ((Integer)entry.getKey()).intValue();
         ClassifiedState state = (ClassifiedState)entry.getValue();
         Iterator i$1 = state.getPlaces().iterator();

         while(i$1.hasNext()) {
            String place = (String)i$1.next();
            Map tokenAverages = getTokenAverages(averages, place);
            Iterator i$2 = state.getTokens(place).entrySet().iterator();

            while(i$2.hasNext()) {
               Entry tokenEntry = (Entry)i$2.next();
               String token = (String)tokenEntry.getKey();
               Integer tokenCount = (Integer)tokenEntry.getValue();
               Double previous = (Double)tokenAverages.get(token);
               double probability = (double)tokenCount.intValue() * ((Double)steadyState.get(id)).doubleValue();
               double newAverage = probability + (previous == null ? 0.0D : previous.doubleValue());
               tokenAverages.put(token, newAverage);
            }
         }
      }

      return averages;
   }

   private static Map getTokenAverages(Map existingAverages, String place) {
      if (!existingAverages.containsKey(place)) {
         existingAverages.put(place, new HashMap());
      }

      return (Map)existingAverages.get(place);
   }
}
