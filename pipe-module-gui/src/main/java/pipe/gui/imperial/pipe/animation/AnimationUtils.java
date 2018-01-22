package pipe.gui.imperial.pipe.animation;

import pipe.gui.imperial.pipe.models.petrinet.DiscretePlace;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;
import pipe.gui.imperial.pipe.models.petrinet.Token;
import pipe.gui.imperial.state.HashedStateBuilder;
import pipe.gui.imperial.state.State;

import java.util.Iterator;

public final class AnimationUtils {
   public static State getState(PetriNet petriNet) {
      HashedStateBuilder builder = new HashedStateBuilder();
      Iterator i$ = petriNet.getPlacesMap ().iterator();

      while(i$.hasNext()) {
         DiscretePlace place = (DiscretePlace)i$.next();
         Iterator i$1 = petriNet.getTokens().iterator();

         while(i$1.hasNext()) {
            Token token = (Token)i$1.next();
            builder.placeWithToken(place.getId(), token.getId(), place.getTokenCount(token.getId()));
         }
      }

      return builder.build();
   }
}
