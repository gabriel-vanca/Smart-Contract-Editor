package pipe.gui.imperial.pipe.models.petrinet;

import pipe.gui.imperial.pipe.parsers.FunctionalResults;
import pipe.gui.imperial.pipe.parsers.FunctionalWeightParser;
import pipe.gui.imperial.pipe.parsers.PetriNetWeightParser;
import pipe.gui.imperial.pipe.parsers.StateEvalVisitor;
import pipe.gui.imperial.state.State;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class InboundNormalArc extends InboundArc {
   public InboundNormalArc(DiscretePlace source, DiscreteTransition target, Map tokenWeights) {
      super(source, target, tokenWeights, ArcType.NORMAL);
   }

   public final boolean canFire(PetriNet petriNet, State state) {
      pipe.gui.imperial.pipe.models.petrinet.Place place = (Place)this.getSource();
      Map tokenCounts = state.getTokens(place.getId());
      if (this.allTokenCountsAreZero(tokenCounts)) {
         return false;
      } else {
         Map tokenWeights = this.getTokenWeights();
         StateEvalVisitor stateEvalVisitor = new StateEvalVisitor(petriNet, state);
         FunctionalWeightParser functionalWeightParser = new PetriNetWeightParser(stateEvalVisitor, petriNet);
         Iterator i$ = tokenWeights.entrySet().iterator();

         double tokenWeight;
         int currentCount;
         do {
            if (!i$.hasNext()) {
               return true;
            }

            Entry entry = (Entry)i$.next();
            FunctionalResults results = functionalWeightParser.evaluateExpression((String)entry.getValue());
            if (results.hasErrors()) {
               throw new RuntimeException("Errors evaluating arc weight against Petri net. Needs handling in code");
            }

            tokenWeight = ((Double)results.getResult()).doubleValue();
            String tokenId = (String)entry.getKey();
            currentCount = ((Integer)tokenCounts.get(tokenId)).intValue();
         } while((double)currentCount >= tokenWeight);

         return false;
      }
   }

   private boolean allTokenCountsAreZero(Map tokenCounts) {
      boolean allCountsAreZero = true;
      Iterator i$ = tokenCounts.values().iterator();

      while(i$.hasNext()) {
         Integer count = (Integer)i$.next();
         if (count.intValue() > 0) {
            allCountsAreZero = false;
         }
      }

      return allCountsAreZero;
   }
}
