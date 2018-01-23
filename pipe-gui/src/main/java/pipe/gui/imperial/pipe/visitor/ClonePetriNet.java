package pipe.gui.imperial.pipe.visitor;

import pipe.gui.imperial.pipe.exceptions.InvalidRateException;
import pipe.gui.imperial.pipe.exceptions.PetriNetComponentException;
import pipe.gui.imperial.pipe.models.petrinet.*;
import pipe.gui.imperial.pipe.models.petrinet.name.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ClonePetriNet {
   private static final Logger LOGGER = Logger.getLogger(ClonePetriNet.class.getName());
   private final PetriNet petriNet;
   private final PetriNet newPetriNet;
   private final Map rateParameters = new HashMap();
   private final Map places = new HashMap();
   private final Map transitions = new HashMap();

   private ClonePetriNet(PetriNet petriNet) {
      this.petriNet = petriNet;
      this.newPetriNet = new PetriNet();
   }

   public static PetriNet clone(PetriNet petriNet) {
      ClonePetriNet clone = new ClonePetriNet(petriNet);
      return clone.clonePetriNet();
   }

   private PetriNet clonePetriNet() {
      this.visit(this.petriNet.getName());
      Iterator i$ = this.petriNet.getTokens().iterator();

      while(i$.hasNext()) {
         Token token = (Token)i$.next();
         this.visit(token);
      }

      i$ = this.petriNet.getAnnotations().iterator();

      while(i$.hasNext()) {
         Annotation annotation = (Annotation)i$.next();
         this.visit(annotation);
      }

      i$ = this.petriNet.getPlacesMap ().iterator();

      while(i$.hasNext()) {
         DiscretePlace place = (DiscretePlace)i$.next();
         this.visit(place);
      }

      i$ = this.petriNet.getRateParameters().iterator();

      while(i$.hasNext()) {
         RateParameter rateParameter = (RateParameter)i$.next();
         this.visit(rateParameter);
      }

      i$ = this.petriNet.getTransitionsMap ().iterator();

      while(i$.hasNext()) {
         Transition transition = (Transition)i$.next();
         this.visit(transition);
      }

      i$ = this.petriNet.getInboundArcs().iterator();

      while(i$.hasNext()) {
         InboundArc arc = (InboundArc)i$.next();
         this.visit(arc);
      }

      i$ = this.petriNet.getOutboundArcs().iterator();

      while(i$.hasNext()) {
         OutboundArc arc = (OutboundArc)i$.next();
         this.visit(arc);
      }

      return this.newPetriNet;
   }

   private void visit(PetriNetName name) {
      if (name != null) {
         name.visit(new ClonePetriNet.NameCloner((ClonePetriNet.SyntheticClass_1)null));
      }

   }

   public void visit(Token token) {
      Token newToken = new ColoredToken(token);
      this.newPetriNet.addToken(newToken);
   }

   public void visit(RateParameter rate) {
      RateParameterCloner cloner = new RateParameterCloner();

      try {
         rate.accept(cloner);
      } catch (PetriNetComponentException var6) {
         LOGGER.log(Level.SEVERE, var6.getMessage());
      }

      RateParameter rateParameter = cloner.cloned;

      try {
         this.newPetriNet.addRateParameter(rateParameter);
         this.rateParameters.put(rateParameter.getId(), rateParameter);
      } catch (InvalidRateException var5) {
         LOGGER.log(Level.SEVERE, var5.getMessage());
      }

   }

   public void visit(Annotation annotation) {
      AnnotationCloner cloner = new AnnotationCloner();

      try {
         annotation.accept(cloner);
      } catch (PetriNetComponentException var4) {
         LOGGER.log(Level.SEVERE, var4.getMessage());
      }

      Annotation newAnnotation = cloner.cloned;
      this.newPetriNet.addAnnotation(newAnnotation);
   }

   public void visit(Place place) {
      pipe.gui.imperial.pipe.visitor.PlaceCloner cloner = new PlaceCloner();

      try {
         place.accept(cloner);
      } catch (PetriNetComponentException var6) {
         LOGGER.log(Level.SEVERE, var6.getMessage());
      }

      DiscretePlace newPlace = cloner.cloned;
      Iterator i$ = place.getTokenCounts().entrySet().iterator();

      while(i$.hasNext()) {
         Entry entry = (Entry)i$.next();
         newPlace.setTokenCount((String)entry.getKey(), ((Integer)entry.getValue()).intValue());
      }

      this.newPetriNet.addPlace(newPlace);
      this.places.put(place.getId(), newPlace);
   }

   public void visit(Transition transition) {
      TransitionCloner cloner = new TransitionCloner();

      try {
         transition.accept(cloner);
      } catch (PetriNetComponentException var5) {
         LOGGER.log(Level.SEVERE, var5.getMessage());
      }

      DiscreteTransition newTransition = cloner.cloned;
      if (transition.getRate().getRateType().equals(RateType.RATE_PARAMETER)) {
         FunctionalRateParameter rateParameter = (FunctionalRateParameter)transition.getRate();
         newTransition.setRate((Rate)this.rateParameters.get(rateParameter.getId()));
      }

      this.transitions.put(transition.getId(), newTransition);
      this.newPetriNet.addTransition(newTransition);
   }

   public void visit(InboundArc arc) {
      DiscretePlace source = (DiscretePlace)this.places.get(((DiscretePlace)arc.getSource()).getId());
      DiscreteTransition target = (DiscreteTransition)this.transitions.get(((DiscreteTransition)arc.getTarget()).getId());
      Object newArc;
      switch(arc.getType()) {
      case INHIBITOR:
         newArc = new InboundInhibitorArc(source, target);
         break;
      default:
         newArc = new InboundNormalArc(source, target, arc.getTokenWeights());
      }

      List arcPoints = arc.getArcPoints();

      for(int i = 1; i < arcPoints.size() - 1; ++i) {
         ((InboundArc)newArc).addIntermediatePoint((ArcPoint)arcPoints.get(i));
      }

      ((InboundArc)newArc).setId(arc.getId());
      this.newPetriNet.addArc((InboundArc)newArc);
   }

   public void visit(OutboundArc arc) {
      DiscretePlace target = (DiscretePlace)this.places.get(((DiscretePlace)arc.getTarget()).getId());
      DiscreteTransition source = (DiscreteTransition)this.transitions.get(((DiscreteTransition)arc.getSource()).getId());
      OutboundArc newArc = new OutboundNormalArc(source, target, arc.getTokenWeights());
      List arcPoints = arc.getArcPoints();

      for(int i = 1; i < arcPoints.size() - 1; ++i) {
         newArc.addIntermediatePoint((ArcPoint)arcPoints.get(i));
      }

      newArc.setId(arc.getId());
      this.newPetriNet.addArc((OutboundArc)newArc);
   }

   // $FF: synthetic class
   static class SyntheticClass_1 {
      // $FF: synthetic field
      static final int[] $SwitchMap$uk$ac$imperial$pipe$models$petrinet$ArcType = new int[ArcType.values().length];

      static {
         try {
            $SwitchMap$uk$ac$imperial$pipe$models$petrinet$ArcType[ArcType.INHIBITOR.ordinal()] = 1;
         } catch (NoSuchFieldError var1) {
            ;
         }

      }
   }

   private class NameCloner implements NormalNameVisitor, FileNameVisitor {
      private NameCloner() {
      }

      public void visit(PetriNetFileName name) {
         ClonePetriNet.this.newPetriNet.setName(new PetriNetFileName(name.getFile()));
      }

      public void visit(NormalPetriNetName name) {
         ClonePetriNet.this.newPetriNet.setName(new NormalPetriNetName(name.getName()));
      }

      // $FF: synthetic method
      NameCloner(ClonePetriNet.SyntheticClass_1 x1) {
         this();
      }
   }
}
