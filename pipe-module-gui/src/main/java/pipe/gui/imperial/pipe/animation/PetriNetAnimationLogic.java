package pipe.gui.imperial.pipe.animation;

import pipe.gui.imperial.pipe.models.petrinet.Arc;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;
import pipe.gui.imperial.pipe.models.petrinet.Place;
import pipe.gui.imperial.pipe.models.petrinet.Transition;
import pipe.gui.imperial.pipe.parsers.FunctionalResults;
import pipe.gui.imperial.pipe.parsers.PetriNetWeightParser;
import pipe.gui.imperial.pipe.parsers.StateEvalVisitor;
import pipe.gui.imperial.state.HashedStateBuilder;
import pipe.gui.imperial.state.State;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public final class PetriNetAnimationLogic implements AnimationLogic {
   private final PetriNet petriNet;
   public Map cachedEnabledTransitions = new ConcurrentHashMap();

   public PetriNetAnimationLogic(PetriNet petriNet) {
      this.petriNet = petriNet;
   }

   public Set getEnabledTransitions(State state) {
      if (this.cachedEnabledTransitions.containsKey(state)) {
         return (Set)this.cachedEnabledTransitions.get(state);
      } else {
         Set enabledTransitions = this.findEnabledTransitions(state);
         boolean hasImmediate = this.areAnyTransitionsImmediate(enabledTransitions);
         int maxPriority = hasImmediate ? this.getMaxPriority(enabledTransitions) : 0;
         if (hasImmediate) {
            this.removeTimedTransitions(enabledTransitions);
         }

         this.removePrioritiesLessThan(maxPriority, enabledTransitions);
         this.cachedEnabledTransitions.put(state, enabledTransitions);
         return enabledTransitions;
      }
   }

   public Map getSuccessors(State state) {
      Collection enabled = this.getEnabledTransitions(state);
      Map successors = new HashMap();

      Transition transition;
      State successor;
      for(Iterator i$ = enabled.iterator(); i$.hasNext(); ((Collection)successors.get(successor)).add(transition)) {
         transition = (Transition)i$.next();
         successor = this.getFiredState(state, transition);
         if (!successors.containsKey(successor)) {
            successors.put(successor, new LinkedList());
         }
      }

      return successors;
   }

   public State getFiredState(State state, Transition transition) {
      HashedStateBuilder builder = new HashedStateBuilder();
      Iterator i$ = state.getPlaces().iterator();

      while(i$.hasNext()) {
         String placeId = (String)i$.next();
         builder.placeWithTokens(placeId, state.getTokens(placeId));
      }

      Set enabled = this.getEnabledTransitions(state);
      if (enabled.contains(transition)) {
         Iterator i$1 = this.petriNet.inboundArcs(transition).iterator();

         int currentCount;
         while(i$1.hasNext()) {
            Arc arc = (Arc)i$1.next();
            String placeId = arc.getSource().getId();
            Map arcWeights = arc.getTokenWeights();
            Iterator i$2 = state.getTokens(placeId).entrySet().iterator();

            while(i$2.hasNext()) {
               Entry entry = (Entry)i$2.next();
               String tokenId = (String)entry.getKey();
               if (arcWeights.containsKey(tokenId)) {
                  int currentCount1 = ((Integer)entry.getValue()).intValue();
                  currentCount = (int)this.getArcWeight(state, (String)arcWeights.get(tokenId));
                  builder.placeWithToken(placeId, tokenId, this.subtractWeight(currentCount, currentCount1));
               }
            }
         }

         State temporaryState = builder.build();
         Iterator i$3 = this.petriNet.outboundArcs(transition).iterator();

         while(i$3.hasNext()) {
            Arc arc = (Arc)i$3.next();
            String placeId = ((Place)arc.getTarget()).getId();
            Map arcWeights = arc.getTokenWeights();
            Iterator i$4 = arcWeights.entrySet().iterator();

            while(i$4.hasNext()) {
               Entry entry = (Entry)i$4.next();
               String tokenId = (String)entry.getKey();
               currentCount = ((Integer)temporaryState.getTokens(placeId).get(tokenId)).intValue();
               int arcWeight = (int)this.getArcWeight(state, (String)entry.getValue());
               builder.placeWithToken(placeId, tokenId, this.addWeight(currentCount, arcWeight));
            }
         }
      }

      return builder.build();
   }

   public double getArcWeight(State state, String weight) {
      StateEvalVisitor evalVisitor = new StateEvalVisitor(this.petriNet, state);
      PetriNetWeightParser parser = new PetriNetWeightParser(evalVisitor, this.petriNet);
      FunctionalResults result = parser.evaluateExpression(weight);
      if (result.hasErrors()) {
         throw new RuntimeException("Could not parse arc weight");
      } else {
         return ((Double)result.getResult()).doubleValue();
      }
   }

   public void clear() {
      this.cachedEnabledTransitions.clear();
   }

   private int subtractWeight(int currentWeight, int arcWeight) {
      return currentWeight == Integer.MAX_VALUE ? currentWeight : currentWeight - arcWeight;
   }

   private int addWeight(int currentWeight, int arcWeight) {
      return currentWeight == Integer.MAX_VALUE ? currentWeight : currentWeight + arcWeight;
   }

   private Set findEnabledTransitions(State state) {
      Set enabledTransitions = new HashSet();
      Iterator i$ = this.petriNet.getTransitionsMap ().iterator();

      while(i$.hasNext()) {
         Transition transition = (Transition)i$.next();
         if (this.isEnabled(transition, state)) {
            enabledTransitions.add(transition);
         }
      }

      return enabledTransitions;
   }

   private boolean isEnabled(Transition transition, State state) {
      Iterator i$ = this.petriNet.inboundArcs(transition).iterator();

      Arc arc;
      do {
         if (!i$.hasNext()) {
            i$ = this.petriNet.outboundArcs(transition).iterator();

            do {
               if (!i$.hasNext()) {
                  return true;
               }

               arc = (Arc)i$.next();
            } while(arc.canFire(this.petriNet, state));

            return false;
         }

         arc = (Arc)i$.next();
      } while(arc.canFire(this.petriNet, state));

      return false;
   }

   private boolean areAnyTransitionsImmediate(Iterable transitions) {
      Iterator i$ = transitions.iterator();

      Transition transition;
      do {
         if (!i$.hasNext()) {
            return false;
         }

         transition = (Transition)i$.next();
      } while(transition.isTimed());

      return true;
   }

   private int getMaxPriority(Iterable transitions) {
      int maxPriority = 0;
      Iterator i$ = transitions.iterator();

      while(i$.hasNext()) {
         Transition transition = (Transition)i$.next();
         if (!transition.isTimed()) {
            maxPriority = Math.max(maxPriority, transition.getPriority());
         }
      }

      return maxPriority;
   }

   private void removePrioritiesLessThan(int priority, Iterable transitions) {
      Iterator transitionIterator = transitions.iterator();

      while(transitionIterator.hasNext()) {
         Transition transition = (Transition)transitionIterator.next();
         if (!transition.isTimed() && transition.getPriority() < priority) {
            transitionIterator.remove();
         }
      }

   }

   private void removeTimedTransitions(Iterable transitions) {
      Iterator transitionIterator = transitions.iterator();

      while(transitionIterator.hasNext()) {
         Transition transition = (Transition)transitionIterator.next();
         if (transition.isTimed()) {
            transitionIterator.remove();
         }
      }

   }
}
