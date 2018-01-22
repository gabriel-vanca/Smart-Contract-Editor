package pipe.gui.imperial.reachability.algorithm;

import pipe.gui.imperial.pipe.models.petrinet.PetriNet;

public class BoundedExplorerUtilities extends CachingExplorerUtilities {
   private final int maxNumberOfStates;

   public BoundedExplorerUtilities(PetriNet petriNet, int maxNumberOfStates) {
      super(petriNet);
      this.maxNumberOfStates = maxNumberOfStates;
   }

   public final boolean canExploreMore(int stateCount) {
      return stateCount <= this.maxNumberOfStates;
   }
}
