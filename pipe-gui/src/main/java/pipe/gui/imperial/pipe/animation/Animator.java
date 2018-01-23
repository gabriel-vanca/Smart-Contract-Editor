package pipe.gui.imperial.pipe.animation;

import pipe.gui.imperial.pipe.models.petrinet.Transition;

import java.util.Set;

public interface Animator {
   void saveState();

   void reset();

   Transition getRandomEnabledTransition();

   Set<Transition> getEnabledTransitions();

   void fireTransition(Transition var1);

   void fireTransitionBackwards(Transition var1);
}
