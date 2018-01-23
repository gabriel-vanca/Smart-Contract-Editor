package pipe.gui.imperial.pipe.io.adapters.modelAdapter;

import com.google.common.base.Joiner;
import pipe.gui.imperial.pipe.io.adapters.model.AdaptedArc;
import pipe.gui.imperial.pipe.models.petrinet.*;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArcAdapter extends XmlAdapter {
   private final Map places;
   private final Map transitions;

   public ArcAdapter() {
      this.places = new HashMap();
      this.transitions = new HashMap();
   }

   @Override
   public Object unmarshal(Object v) throws Exception {
      return null;
   }

   @Override
   public Object marshal(Object v) throws Exception {
      return null;
   }

   public ArcAdapter(Map places, Map transitions) {
      this.places = places;
      this.transitions = transitions;
   }

   public Arc unmarshal(AdaptedArc adaptedArc) {
      String source = adaptedArc.getSource();
      String target = adaptedArc.getTarget();
      Map weights = this.stringToWeights(adaptedArc.getInscription().getTokenCounts());
      Object arc;
      DiscretePlace place;
      DiscreteTransition transition;
      if (adaptedArc.getType().equals("inhibitor")) {
         place = (DiscretePlace)this.places.get(source);
         transition = (DiscreteTransition)this.transitions.get(target);
         arc = new InboundInhibitorArc(place, transition);
      } else if (this.places.containsKey(source)) {
         place = (DiscretePlace)this.places.get(source);
         transition = (DiscreteTransition)this.transitions.get(target);
         arc = new InboundNormalArc(place, transition, weights);
      } else {
         place = (DiscretePlace)this.places.get(target);
         transition = (DiscreteTransition)this.transitions.get(source);
         arc = new OutboundNormalArc(transition, place, weights);
      }

      ((Arc)arc).setId(adaptedArc.getId());
      ((Arc)arc).setTagged(false);
      this.setRealArcPoints((Arc)arc, adaptedArc);
      return (Arc)arc;
   }

   private Map stringToWeights(String weights) {
      Map tokenWeights = new HashMap();
      if (weights.isEmpty()) {
         return tokenWeights;
      } else {
         String[] commaSeparatedMarkings = weights.split(",");
         if (commaSeparatedMarkings.length == 1) {
            String weight = commaSeparatedMarkings[0];
            tokenWeights.put("Default", weight);
         } else {
            for(int i = 0; i < commaSeparatedMarkings.length; i += 2) {
               String weight = commaSeparatedMarkings[i + 1].replace("@", ",");
               String tokenName = commaSeparatedMarkings[i];
               tokenWeights.put(tokenName, weight);
            }
         }

         return tokenWeights;
      }
   }

   private void setRealArcPoints(Arc arc, AdaptedArc adapted) {
      List arcPoints = adapted.getArcPoints();
      if (!arcPoints.isEmpty()) {
         for(int i = 1; i < arcPoints.size() - 1; ++i) {
            arc.addIntermediatePoint((ArcPoint)arcPoints.get(i));
         }

      }
   }

   public AdaptedArc marshal(Arc arc) {
      AdaptedArc adapted = new AdaptedArc();
      this.setArcPoints(arc, adapted);
      adapted.setId(arc.getId());
      adapted.setSource(arc.getSource().getId());
      adapted.setTarget(arc.getTarget().getId());
      adapted.getInscription().setTokenCounts(this.weightToString(arc.getTokenWeights()));
      adapted.setType(arc.getType().name().toLowerCase());
      return adapted;
   }

   private String weightToString(Map weights) {
      return Joiner.on(",").withKeyValueSeparator(",").join(weights);
   }

   private void setArcPoints(Arc arc, AdaptedArc adapted) {
      List arcPoints = adapted.getArcPoints();
      arcPoints.addAll(arc.getArcPoints());
   }
}
