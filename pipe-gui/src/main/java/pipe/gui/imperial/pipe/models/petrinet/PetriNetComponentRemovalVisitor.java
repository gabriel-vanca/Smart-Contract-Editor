package pipe.gui.imperial.pipe.models.petrinet;

import pipe.gui.imperial.pipe.exceptions.InvalidRateException;
import pipe.gui.imperial.pipe.exceptions.PetriNetComponentException;

public final class PetriNetComponentRemovalVisitor implements PlaceVisitor, TransitionVisitor, ArcVisitor, TokenVisitor, AnnotationVisitor, RateParameterVisitor {
   private final pipe.gui.imperial.pipe.models.petrinet.PetriNet net;

   public PetriNetComponentRemovalVisitor(PetriNet net) {
      this.net = net;
   }

   public void visit(DiscretePlace place) throws PetriNetComponentException {
      this.net.removePlace(place);
   }

   public void visit(DiscreteTransition transition) {
      this.net.removeTransition(transition);
   }

   public void visit(Token token) throws PetriNetComponentException {
      this.net.removeToken(token);
   }

   public void visit(Annotation annotation) {
      this.net.removeAnnotation(annotation);
   }

   public void visit(FunctionalRateParameter rate) throws InvalidRateException {
      this.net.removeRateParameter(rate);
   }

   public void visit(InboundArc inboundArc) {
      this.net.removeArc(inboundArc);
   }

   public void visit(OutboundArc outboundArc) {
      this.net.removeArc(outboundArc);
   }
}
