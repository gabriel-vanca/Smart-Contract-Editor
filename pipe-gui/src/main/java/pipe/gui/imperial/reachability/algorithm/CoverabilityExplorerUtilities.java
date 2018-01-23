package pipe.gui.imperial.reachability.algorithm;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import pipe.gui.imperial.pipe.exceptions.InvalidRateException;
import pipe.gui.imperial.state.ClassifiedState;
import pipe.gui.imperial.state.HashedClassifiedState;
import pipe.gui.imperial.state.HashedStateBuilder;

import java.util.*;
import java.util.Map.Entry;

public final class CoverabilityExplorerUtilities implements ExplorerUtilities {
   private final ExplorerUtilities explorerUtilities;
   private final Multimap parents = HashMultimap.create();

   public CoverabilityExplorerUtilities(ExplorerUtilities utilities) {
      this.explorerUtilities = utilities;
   }

   public Map getSuccessorsWithTransitions(ClassifiedState state) {
      Map successors = this.explorerUtilities.getSuccessorsWithTransitions(state);
      Map boundedSuccessors = this.boundSuccessors(state, successors);
      this.registerParent(state, boundedSuccessors.keySet());
      return boundedSuccessors;
   }

   private Map boundSuccessors(ClassifiedState state, Map successors) {
      Map boundedSuccessors = new HashMap();
      Iterator i$ = successors.entrySet().iterator();

      while(i$.hasNext()) {
         Entry entry = (Entry)i$.next();
         ClassifiedState successor = (ClassifiedState)entry.getKey();
         ClassifiedState bounded = this.getBoundedState(state, successor);
         boundedSuccessors.put(bounded, entry.getValue());
      }

      return boundedSuccessors;
   }

   private ClassifiedState getBoundedState(ClassifiedState parent, ClassifiedState state) {
      Queue ancestors = new ArrayDeque();
      Set exploredAncestors = new HashSet();
      ancestors.add(parent);

      while(!ancestors.isEmpty()) {
         ClassifiedState ancestor = (ClassifiedState)ancestors.poll();
         if (this.isUnbounded(state, ancestor)) {
            return this.boundState(state, ancestor);
         }

         exploredAncestors.add(ancestor);
         Iterator i$ = this.parents.get(ancestor).iterator();

         while(i$.hasNext()) {
            ClassifiedState p = (ClassifiedState)i$.next();
            if (!exploredAncestors.contains(p)) {
               ancestors.add(p);
            }
         }
      }

      return state;
   }

   private boolean isUnbounded(ClassifiedState state, ClassifiedState ancestor) {
      Iterator i$ = state.getPlaces().iterator();

      while(i$.hasNext()) {
         String place = (String)i$.next();
         Iterator i$1 = state.getTokens(place).entrySet().iterator();

         while(i$1.hasNext()) {
            Entry entry = (Entry)i$1.next();
            String token = (String)entry.getKey();
            int stateCount = ((Integer)entry.getValue()).intValue();
            int ancestorCount = ((Integer)ancestor.getTokens(place).get(token)).intValue();
            if (ancestorCount > stateCount) {
               return false;
            }
         }
      }

      return true;
   }

   private ClassifiedState boundState(ClassifiedState state, ClassifiedState ancestor) {
      HashedStateBuilder builder = new HashedStateBuilder();
      Iterator i$ = state.getPlaces().iterator();

      while(i$.hasNext()) {
         String place = (String)i$.next();
         Iterator i$1 = state.getTokens(place).entrySet().iterator();

         while(i$1.hasNext()) {
            Entry entry = (Entry)i$1.next();
            String token = (String)entry.getKey();
            int stateCount = ((Integer)entry.getValue()).intValue();
            int ancestorCount = ((Integer)ancestor.getTokens(place).get(token)).intValue();
            if (ancestorCount >= stateCount) {
               builder.placeWithToken(place, token, stateCount);
            } else {
               builder.placeWithToken(place, token, Integer.MAX_VALUE);
            }
         }
      }

      if (state.isTangible()) {
         return HashedClassifiedState.tangibleState(builder.build());
      } else {
         return HashedClassifiedState.vanishingState(builder.build());
      }
   }

   private void registerParent(ClassifiedState state, Collection successors) {
      Iterator i$ = successors.iterator();

      while(i$.hasNext()) {
         ClassifiedState successor = (ClassifiedState)i$.next();
         if (!this.isBackArc(successor, state)) {
            this.parents.put(successor, state);
         }
      }

   }

   private boolean isBackArc(ClassifiedState successor, ClassifiedState state) {
      Queue ancestors = new ArrayDeque();
      Set exploredAncestors = new HashSet();
      ancestors.add(state);

      while(!ancestors.isEmpty()) {
         ClassifiedState ancestor = (ClassifiedState)ancestors.poll();
         if (ancestor.equals(successor)) {
            return true;
         }

         exploredAncestors.add(ancestor);
         Iterator i$ = this.parents.get(ancestor).iterator();

         while(i$.hasNext()) {
            ClassifiedState p = (ClassifiedState)i$.next();
            if (!exploredAncestors.contains(p)) {
               ancestors.add(p);
            }
         }
      }

      return false;
   }

   public Collection getSuccessors(ClassifiedState state) {
      return this.getSuccessorsWithTransitions(state).keySet();
   }

   public double rate(ClassifiedState state, ClassifiedState successor) throws InvalidRateException {
      return this.explorerUtilities.rate(state, successor);
   }

   public ClassifiedState getCurrentState() {
      return this.explorerUtilities.getCurrentState();
   }

   public Collection getTransitions(ClassifiedState state, ClassifiedState successor) {
      return this.explorerUtilities.getTransitions(state, successor);
   }

   public double getWeightOfTransitions(ClassifiedState state, Iterable transitions) throws InvalidRateException {
      return this.explorerUtilities.getWeightOfTransitions(state, transitions);
   }

   public Collection getAllEnabledTransitions(ClassifiedState state) {
      return this.explorerUtilities.getAllEnabledTransitions(state);
   }

   public void clear() {
      this.explorerUtilities.clear();
   }

   public boolean canExploreMore(int stateCount) {
      return true;
   }
}
