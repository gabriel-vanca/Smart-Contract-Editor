package pipe.gui.imperial.pipe.io.adapters.modelAdapter;

import pipe.gui.imperial.pipe.exceptions.PetriNetComponentException;
import pipe.gui.imperial.pipe.io.adapters.model.AdaptedPetriNet;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;
import pipe.gui.imperial.pipe.models.petrinet.PetriNetComponent;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Iterator;

public class PetriNetAdapter extends XmlAdapter {
   public PetriNet unmarshal(AdaptedPetriNet v) throws PetriNetComponentException {
      PetriNet petriNet = new PetriNet();
      this.addToPetriNet(v.tokens, petriNet);
      this.addToPetriNet(v.annotations, petriNet);
      this.addToPetriNet(v.places, petriNet);
      this.addToPetriNet(v.rateParameters, petriNet);
      this.addToPetriNet(v.transitions, petriNet);
      this.addToPetriNet(v.arcs, petriNet);
      return petriNet;
   }

   public AdaptedPetriNet marshal(PetriNet v) {
      AdaptedPetriNet petriNet = new AdaptedPetriNet();
      petriNet.tokens = v.getTokens();
      petriNet.annotations = v.getAnnotations();
      petriNet.rateParameters = v.getRateParameters();
      petriNet.places = v.getPlacesMap ();
      petriNet.transitions = v.getTransitionsMap ();
      petriNet.arcs = v.getArcs();
      return petriNet;
   }

   private void addToPetriNet(Iterable components, PetriNet petriNet) throws PetriNetComponentException {
      if (components != null) {
         Iterator i$ = components.iterator();

         while(i$.hasNext()) {
            PetriNetComponent component = (PetriNetComponent)i$.next();
            petriNet.add(component);
         }
      }

   }

   @Override
   public Object unmarshal(Object v) throws Exception {
      return null;
   }

   @Override
   public Object marshal(Object v) throws Exception {
      return null;
   }
}
