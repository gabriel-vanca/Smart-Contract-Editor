package pipe.gui.imperial.pipe.models.petrinet;

import pipe.gui.imperial.pipe.exceptions.InvalidRateException;

public final class PetriNetComponentAddVisitor implements PlaceVisitor, ArcVisitor, TransitionVisitor, TokenVisitor, AnnotationVisitor, RateParameterVisitor {
   private final pipe.gui.imperial.pipe.models.petrinet.PetriNet petriNet;

   public PetriNetComponentAddVisitor(PetriNet petriNet) {
      this.petriNet = petriNet;
   }

   public void visit(DiscretePlace place) {
      this.petriNet.addPlace(place);
   }

   public void visit(DiscreteTransition transition) {
      this.petriNet.addTransition(transition);
   }

   public void visit(Token token) {
      this.petriNet.addToken(token);
   }

   public void visit(Annotation annotation) {
      this.petriNet.addAnnotation(annotation);
   }

   public void visit(FunctionalRateParameter rate) throws InvalidRateException {
      this.petriNet.addRateParameter(rate);
   }

   public void visit(InboundArc inboundArc) {
      this.petriNet.addArc(inboundArc);
   }

   public void visit(OutboundArc outboundArc) {
      this.petriNet.addArc(outboundArc);
   }
}
