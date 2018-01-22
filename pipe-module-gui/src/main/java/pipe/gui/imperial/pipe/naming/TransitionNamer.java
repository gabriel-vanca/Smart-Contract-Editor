package pipe.gui.imperial.pipe.naming;

import java.util.Iterator;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;
import pipe.gui.imperial.pipe.models.petrinet.Transition;

public class TransitionNamer extends ComponentNamer {
   public TransitionNamer(PetriNet petriNet) {
      super(petriNet, "T", "newTransition", "deleteTransition");
      this.initialiseTransitionNames();
   }

   private void initialiseTransitionNames() {
      Iterator i$ = this.petriNet.getTransitionsMap ().iterator();

      while(i$.hasNext()) {
         Transition transition = (Transition)i$.next();
         transition.addPropertyChangeListener(this.nameListener);
         this.names.add(transition.getId());
      }

   }
}
