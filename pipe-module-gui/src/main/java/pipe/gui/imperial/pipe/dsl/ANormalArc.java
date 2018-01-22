package pipe.gui.imperial.pipe.dsl;

import pipe.gui.imperial.pipe.models.petrinet.*;

import java.awt.geom.Point2D.Double;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class ANormalArc implements DSLCreator {
   private String source;
   private String target;
   private Map weights = new HashMap();
   private List intermediatePoints = new LinkedList();

   public static ANormalArc withSource(String source) {
      ANormalArc aNormalArc = new ANormalArc();
      aNormalArc.source = source;
      return aNormalArc;
   }

   public ANormalArc andTarget(String target) {
      this.target = target;
      return this;
   }

   public ANormalArc with(String tokenWeight, String tokenName) {
      this.weights.put(tokenName, tokenWeight);
      return this;
   }

   public ANormalArc and(String tokenWeight, String tokenName) {
      return this.with(tokenWeight, tokenName);
   }

   public Arc create(Map tokens, Map places, Map transitions, Map rateParameters) {
      Object arc;
      if (places.containsKey(this.source)) {
         arc = new InboundNormalArc((DiscretePlace)places.get(this.source), (DiscreteTransition)transitions.get(this.target), this.weights);
      } else {
         arc = new OutboundNormalArc((DiscreteTransition)transitions.get(this.source), (DiscretePlace)places.get(this.target), this.weights);
      }

      ((Arc)arc).addIntermediatePoints(this.intermediatePoints);
      return (Arc)arc;
   }

   public ANormalArc tokens() {
      return this;
   }

   public ANormalArc token() {
      return this;
   }

   public ANormalArc andIntermediatePoint(int x, int y) {
      this.intermediatePoints.add(new ArcPoint(new Double((double)x, (double)y), false));
      return this;
   }

   public ANormalArc andACurvedIntermediatePoint(int x, int y) {
      this.intermediatePoints.add(new ArcPoint(new Double((double)x, (double)y), true));
      return this;
   }
}
