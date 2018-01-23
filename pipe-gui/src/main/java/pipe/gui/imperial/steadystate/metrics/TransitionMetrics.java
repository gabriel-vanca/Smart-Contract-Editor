package pipe.gui.imperial.steadystate.metrics;

import pipe.gui.imperial.pipe.animation.AnimationLogic;
import pipe.gui.imperial.pipe.animation.PetriNetAnimationLogic;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;
import pipe.gui.imperial.pipe.models.petrinet.Transition;
import pipe.gui.imperial.state.ClassifiedState;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class TransitionMetrics {
   public static Map getTransitionThroughput(Map stateSpace, Map steadyState, PetriNet petriNet) {
      AnimationLogic animationLogic = new PetriNetAnimationLogic(petriNet);
      Map throughputs = new HashMap();
      Iterator i$ = stateSpace.entrySet().iterator();

      while(i$.hasNext()) {
         Entry entry = (Entry)i$.next();
         int id = ((Integer)entry.getKey()).intValue();
         ClassifiedState state = (ClassifiedState)entry.getValue();
         Iterator i$1 = animationLogic.getEnabledTransitions(state).iterator();

         while(i$1.hasNext()) {
            Transition transition = (Transition)i$1.next();
            String transitionId = transition.getId();
            double throughput = transition.getActualRate(petriNet, state).doubleValue() * ((Double)steadyState.get(id)).doubleValue();
            double previous = throughputs.containsKey(transitionId) ? ((Double)throughputs.get(transitionId)).doubleValue() : 0.0D;
            throughputs.put(transitionId, throughput + previous);
         }
      }

      return throughputs;
   }
}
