package pipe.gui.imperial.pipe.dsl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import pipe.gui.imperial.pipe.exceptions.PetriNetComponentException;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;

public final class APetriNet {
   private static final Logger LOGGER = Logger.getLogger(APetriNet.class.getName());
   private Collection creators = new ArrayList();

   public static APetriNet with(DSLCreator creator) {
      APetriNet aPetriNet = new APetriNet();
      aPetriNet.and(creator);
      return aPetriNet;
   }

   public APetriNet and(DSLCreator creator) {
      this.creators.add(creator);
      return this;
   }

   public PetriNet andFinally(DSLCreator finalCreator) throws PetriNetComponentException {
      return this.and(finalCreator).makePetriNet();
   }

   public static PetriNet withOnly(DSLCreator creator) throws PetriNetComponentException {
      APetriNet aPetriNet = new APetriNet();
      return aPetriNet.andFinally(creator);
   }

   private PetriNet makePetriNet() throws PetriNetComponentException {
      Map tokens = new HashMap();
      Map places = new HashMap();
      Map transitions = new HashMap();
      Map rateParameters = new HashMap();
      PetriNet petriNet = new PetriNet();
      Iterator i$ = this.creators.iterator();

      while(i$.hasNext()) {
         DSLCreator creator = (DSLCreator)i$.next();

         try {
            petriNet.add(creator.create(tokens, places, transitions, rateParameters));
         } catch (PetriNetComponentException var9) {
            throw var9;
         }
      }

      return petriNet;
   }
}
