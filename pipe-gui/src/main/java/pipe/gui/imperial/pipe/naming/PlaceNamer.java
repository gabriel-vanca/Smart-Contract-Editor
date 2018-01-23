package pipe.gui.imperial.pipe.naming;

import pipe.gui.imperial.pipe.models.petrinet.DiscretePlace;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;

import java.util.Iterator;

public class PlaceNamer extends ComponentNamer {
   public PlaceNamer(PetriNet petriNet) {
      super(petriNet, "P", "newPlace", "deletePlace");
      this.initialisePlaceNames();
   }

   private void initialisePlaceNames() {
      Iterator i$ = this.petriNet.getPlacesMap ().iterator();

      while(i$.hasNext()) {
         DiscretePlace place = (DiscretePlace)i$.next();
         place.addPropertyChangeListener(this.nameListener);
         this.names.add(place.getId());
      }

   }
}
