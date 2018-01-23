package pipe.gui.imperial.pipe.models.petrinet;

import pipe.gui.imperial.pipe.parsers.FunctionalResults;
import pipe.gui.imperial.pipe.parsers.FunctionalWeightParser;
import pipe.gui.imperial.pipe.parsers.PetriNetWeightParser;
import pipe.gui.imperial.pipe.parsers.StateEvalVisitor;
import pipe.gui.imperial.state.State;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class OutboundNormalArc extends OutboundArc {
   public OutboundNormalArc(DiscreteTransition source, DiscretePlace target, Map tokenWeights) {
      super(source, target, tokenWeights, ArcType.NORMAL);
   }

   public final boolean canFire(PetriNet petriNet, State state) {
      DiscretePlace place = (DiscretePlace)this.getTarget();
      if (!place.hasCapacityRestriction()) {
         return true;
      } else {
         int totalTokensIn = this.getTokenCounts(petriNet, state, this);
         int totalTokensOut = this.getNumberOfTokensLeavingPlace(state, petriNet);
         int tokensInPlace = this.getTokensInPlace(state);
         return tokensInPlace + totalTokensIn - totalTokensOut <= place.getCapacity();
      }
   }

   private int getNumberOfTokensLeavingPlace(State state, pipe.gui.imperial.pipe.models.petrinet.PetriNet petriNet) {
      DiscretePlace place = (DiscretePlace)this.getTarget();
      int count = 0;
      Iterator i$ = petriNet.outboundArcs(place).iterator();

      while(i$.hasNext()) {
         pipe.gui.imperial.pipe.models.petrinet.InboundArc arc = (InboundArc)i$.next();
         if (((pipe.gui.imperial.pipe.models.petrinet.Place)arc.getSource()).equals(this.getTarget()) && ((Transition)arc.getTarget()).equals(this.getSource())) {
            count += this.getTokenCounts(petriNet, state, arc);
         }
      }

      return count;
   }

   private int getTokenCounts(PetriNet petriNet, State state, AbstractArc arc) {
      StateEvalVisitor stateEvalVisitor = new StateEvalVisitor(petriNet, state);
      FunctionalWeightParser functionalWeightParser = new PetriNetWeightParser(stateEvalVisitor, petriNet);
      int count = 0;

      double weight;
      for(Iterator i$ = arc.tokenWeights.entrySet().iterator(); i$.hasNext(); count = (int)((double)count + weight)) {
         Entry entry = (Entry)i$.next();
         FunctionalResults result = functionalWeightParser.evaluateExpression((String)entry.getValue());
         if (result.hasErrors()) {
            throw new RuntimeException("Cannot parse outbound arc weight");
         }

         weight = ((Double)result.getResult()).doubleValue();
      }

      return count;
   }

   private int getTokensInPlace(State state) {
      DiscretePlace place = (DiscretePlace)this.getTarget();
      int count = 0;

      Integer value;
      for(Iterator i$ = state.getTokens(place.getId()).values().iterator(); i$.hasNext(); count += value.intValue()) {
         value = (Integer)i$.next();
      }

      return count;
   }
}
