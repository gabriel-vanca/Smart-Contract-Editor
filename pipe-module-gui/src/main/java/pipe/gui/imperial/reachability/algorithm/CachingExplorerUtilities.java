package pipe.gui.imperial.reachability.algorithm;

import pipe.gui.imperial.pipe.animation.AnimationLogic;
import pipe.gui.imperial.pipe.animation.PetriNetAnimationLogic;
import pipe.gui.imperial.pipe.exceptions.InvalidRateException;
import pipe.gui.imperial.pipe.models.petrinet.DiscretePlace;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;
import pipe.gui.imperial.pipe.models.petrinet.Token;
import pipe.gui.imperial.pipe.models.petrinet.Transition;
import pipe.gui.imperial.pipe.parsers.FunctionalResults;
import pipe.gui.imperial.pipe.parsers.PetriNetWeightParser;
import pipe.gui.imperial.pipe.parsers.StateEvalVisitor;
import pipe.gui.imperial.pipe.visitor.ClonePetriNet;
import pipe.gui.imperial.state.ClassifiedState;
import pipe.gui.imperial.state.HashedClassifiedState;
import pipe.gui.imperial.state.HashedStateBuilder;
import pipe.gui.imperial.state.State;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public abstract class CachingExplorerUtilities implements ExplorerUtilities {
   private final PetriNet petriNet;
   private final AnimationLogic animationLogic;
   private Map cachedSuccessors = new ConcurrentHashMap();

   public CachingExplorerUtilities(PetriNet petriNet) {
      this.petriNet = ClonePetriNet.clone(petriNet);
      this.animationLogic = new PetriNetAnimationLogic(this.petriNet);
   }

   public final Map getSuccessorsWithTransitions(ClassifiedState state) {
      if (this.cachedSuccessors.containsKey(state)) {
         return (Map)this.cachedSuccessors.get(state);
      } else {
         Map successors = this.animationLogic.getSuccessors(state);
         Map classifiedSuccessors = new HashMap();
         Iterator i$ = successors.entrySet().iterator();

         while(i$.hasNext()) {
            Entry entry = (Entry)i$.next();
            ClassifiedState succ = this.classify((State)entry.getKey());
            if (!state.equals(succ)) {
               classifiedSuccessors.put(succ, entry.getValue());
            }
         }

         this.cachedSuccessors.put(state, classifiedSuccessors);
         return classifiedSuccessors;
      }
   }

   public final Collection getSuccessors(ClassifiedState state) {
      return this.getSuccessorsWithTransitions(state).keySet();
   }

   public final double rate(ClassifiedState state, ClassifiedState successor) throws InvalidRateException {
      Collection transitionsToSuccessor = this.getTransitions(state, successor);
      return this.getWeightOfTransitions(state, transitionsToSuccessor);
   }

   public final ClassifiedState getCurrentState() {
      HashedStateBuilder builder = new HashedStateBuilder();
      Iterator i$ = this.petriNet.getPlacesMap ().iterator();

      while(i$.hasNext()) {
         DiscretePlace place = (DiscretePlace)i$.next();
         Iterator i$1 = this.petriNet.getTokens().iterator();

         while(i$1.hasNext()) {
            Token token = (Token)i$1.next();
            builder.placeWithToken(place.getId(), token.getId(), place.getTokenCount(token.getId()));
         }
      }

      State state = builder.build();
      boolean tanigble = this.isTangible(state);
      return tanigble ? HashedClassifiedState.tangibleState(state) : HashedClassifiedState.vanishingState(state);
   }

   public final ClassifiedState classify(State state) {
      boolean tanigble = this.isTangible(state);
      return tanigble ? HashedClassifiedState.tangibleState(state) : HashedClassifiedState.vanishingState(state);
   }

   private boolean isTangible(State state) {
      Set enabledTransitions = this.animationLogic.getEnabledTransitions(state);
      boolean anyTimed = false;
      boolean anyImmediate = false;
      Iterator i$ = enabledTransitions.iterator();

      while(i$.hasNext()) {
         Transition transition = (Transition)i$.next();
         if (transition.isTimed()) {
            anyTimed = true;
         } else {
            anyImmediate = true;
         }
      }

      return enabledTransitions.isEmpty() || anyTimed && !anyImmediate;
   }

   public final Collection getTransitions(ClassifiedState state, ClassifiedState successor) {
      Map stateTransitions = this.getSuccessorsWithTransitions(state);
      return (Collection)(stateTransitions.containsKey(successor) ? (Collection)stateTransitions.get(successor) : new LinkedList());
   }

   public final double getWeightOfTransitions(ClassifiedState state, Iterable transitions) throws InvalidRateException {
      double weight = 0.0D;
      StateEvalVisitor evalVisitor = new StateEvalVisitor(this.petriNet, state);
      PetriNetWeightParser parser = new PetriNetWeightParser(evalVisitor, this.petriNet);

      FunctionalResults results;
      for(Iterator i$ = transitions.iterator(); i$.hasNext(); weight += ((Double)results.getResult()).doubleValue()) {
         Transition transition = (Transition)i$.next();
         results = parser.evaluateExpression(transition.getRateExpr());
         if (results.hasErrors()) {
            throw new InvalidRateException("Invalid functional expression observed for transition : " + transition.getId() + " " + transition.getRateExpr());
         }
      }

      return weight;
   }

   public final Collection getAllEnabledTransitions(ClassifiedState state) {
      Collection results = new LinkedList();
      Iterator i$ = this.getSuccessorsWithTransitions(state).values().iterator();

      while(i$.hasNext()) {
         Collection transitions = (Collection)i$.next();
         results.addAll(transitions);
      }

      return results;
   }

   public final void clear() {
      this.cachedSuccessors.clear();
      this.animationLogic.clear();
   }
}
