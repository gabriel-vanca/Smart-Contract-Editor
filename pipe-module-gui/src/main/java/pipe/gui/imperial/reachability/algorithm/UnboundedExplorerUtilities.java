package pipe.gui.imperial.reachability.algorithm;

import pipe.gui.imperial.pipe.models.petrinet.PetriNet;

public final class UnboundedExplorerUtilities extends CachingExplorerUtilities {
   public UnboundedExplorerUtilities(PetriNet petriNet) {
      super(petriNet);
   }

   public boolean canExploreMore(int stateCount) {
      return true;
   }
}
