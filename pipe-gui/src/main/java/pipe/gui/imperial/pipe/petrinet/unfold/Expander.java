package pipe.gui.imperial.pipe.petrinet.unfold;

import pipe.gui.imperial.pipe.exceptions.PetriNetComponentException;
import pipe.gui.imperial.pipe.exceptions.PetriNetComponentNotFoundException;
import pipe.gui.imperial.pipe.models.petrinet.*;
import pipe.gui.imperial.pipe.visitor.PlaceCloner;
import pipe.gui.imperial.pipe.visitor.TransitionCloner;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Expander {
   private static final Logger LOGGER = Logger.getLogger(Expander.class.getName());
   private final PetriNet petriNet;
   private final Token unfoldToken;
   private final Map newPlaces = new HashMap();
   private final Map newTransitions = new HashMap();
   private final Map newArcs = new HashMap();

   public Expander(PetriNet petriNet) {
      this.petriNet = petriNet;
      this.unfoldToken = this.getCopiedToken();
   }

   private Token getCopiedToken() {
      return new ColoredToken(this.getToken());
   }

   private Token getToken() {
      if (this.petriNet.containsDefaultToken()) {
         return this.getDefaultToken();
      } else {
         Token blackToken = this.getBlackToken();
         return blackToken != null ? blackToken : this.getFirstToken();
      }
   }

   private Token getDefaultToken() {
      try {
         return (Token)this.petriNet.getComponent("Default", Token.class);
      } catch (PetriNetComponentNotFoundException var2) {
         return null;
      }
   }

   private Token getBlackToken() {
      Iterator i$ = this.petriNet.getTokens().iterator();

      Token token;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         token = (Token)i$.next();
      } while(!token.getColor().equals(Color.BLACK));

      return token;
   }

   private Token getFirstToken() {
      return (Token)this.petriNet.getTokens().iterator().next();
   }

   public PetriNet unfold() {
      this.unfoldTransitions();
      return this.createPetriNet();
   }

   private void unfoldTransitions() {
      Iterator i$ = this.petriNet.getTransitionsMap ().iterator();

      while(i$.hasNext()) {
         DiscreteTransition transition = (DiscreteTransition)i$.next();
         TransitionCloner cloner = new TransitionCloner();

         try {
            transition.accept(cloner);
         } catch (Exception var5) {
            LOGGER.log(Level.SEVERE, var5.getMessage());
         }

         DiscreteTransition newTransition = cloner.cloned;
         this.newTransitions.put(newTransition.getId(), newTransition);
         this.analyseOutboundArcs(newTransition, this.petriNet.outboundArcs(transition));
         this.analyseInboundArcs(newTransition, this.petriNet.inboundArcs(transition));
      }

   }

   private PetriNet createPetriNet() {
      PetriNet petriNet = new PetriNet();
      petriNet.addToken(this.unfoldToken);
      Iterator i$ = this.newPlaces.values().iterator();

      while(i$.hasNext()) {
         DiscretePlace place = (DiscretePlace)i$.next();
         petriNet.addPlace(place);
      }

      i$ = this.newTransitions.values().iterator();

      while(i$.hasNext()) {
         DiscreteTransition transition = (DiscreteTransition)i$.next();
         petriNet.addTransition(transition);
      }

      i$ = this.newArcs.values().iterator();

      while(i$.hasNext()) {
         Arc arc = (Arc)i$.next();

         try {
            petriNet.add(arc);
         } catch (PetriNetComponentException var5) {
            LOGGER.log(Level.SEVERE, var5.getMessage());
         }
      }

      return petriNet;
   }

   public void analyseOutboundArcs(DiscreteTransition newTransition, Iterable arcs) {
      Iterator i$ = arcs.iterator();

      while(i$.hasNext()) {
         Arc arc = (Arc)i$.next();
         DiscretePlace place = (DiscretePlace)arc.getTarget();
         Expander.Data data = this.getPlaceData(arc, place);
         DiscretePlace newPlace = this.getNewPlace(place, newTransition.getX(), newTransition.getY(), data.placeTokenCount, data.name);
         this.createArc(newTransition, newPlace, data.arcWeight, arc.getType());
      }

   }

   public void analyseInboundArcs(DiscreteTransition newTransition, Iterable arcs) {
      Iterator i$ = arcs.iterator();

      while(i$.hasNext()) {
         Arc arc = (Arc)i$.next();
         DiscretePlace place = (DiscretePlace)arc.getSource();
         Expander.Data data = this.getPlaceData(arc, place);
         DiscretePlace newPlace = this.getNewPlace(place, newTransition.getX(), newTransition.getY(), data.placeTokenCount, data.name);
         this.createArc(newPlace, newTransition, data.arcWeight, arc.getType());
      }

   }

   private Expander.Data getPlaceData(Arc arc, Place place) {
      StringBuilder newNameBuilder = new StringBuilder(place.getName());
      int placeTokenCount = 0;
      int arcWeight = 0;
      Iterator i$ = (new TreeMap(arc.getTokenWeights())).entrySet().iterator();

      while(i$.hasNext()) {
         Entry entry = (Entry)i$.next();
         String token = (String)entry.getKey();
         String weight = (String)entry.getValue();
         arcWeight = Integer.valueOf(weight).intValue();
         if (arcWeight > 0) {
            newNameBuilder.append("_").append(token);
            placeTokenCount = place.getTokenCount(token);
         }
      }

      return new Expander.Data(placeTokenCount, arcWeight, newNameBuilder.toString());
   }

   private DiscretePlace getNewPlace(DiscretePlace original, int newX, int newY, int tokenCount, String id) {
      if (this.newPlaces.containsKey(id)) {
         return (DiscretePlace)this.newPlaces.get(id);
      } else {
         PlaceCloner cloner = new PlaceCloner();

         try {
            original.accept(cloner);
         } catch (PetriNetComponentException var9) {
            LOGGER.log(Level.SEVERE, var9.getMessage());
         }

         DiscretePlace place = cloner.cloned;
         Map newTokenCounts = new HashMap();
         if (tokenCount > 0) {
            newTokenCounts.put(this.unfoldToken.getId(), tokenCount);
         }

         place.setTokenCounts(newTokenCounts);
         place.setName(id);
         place.setId(id);
         place.setX(newX);
         place.setY(newY);
         this.newPlaces.put(place.getId(), place);
         return place;
      }
   }

   private void createArc(DiscreteTransition source, DiscretePlace target, int arcWeight, ArcType type) {
      Arc newArc = new OutboundNormalArc(source, target, this.getNewArcWeight(arcWeight));
      this.newArcs.put(newArc.getId(), newArc);
   }

   private void createArc(DiscretePlace source, DiscreteTransition target, int arcWeight, ArcType type) {
      Object newArc;
      switch(type) {
      case INHIBITOR:
         newArc = new InboundInhibitorArc(source, target);
         break;
      default:
         newArc = new InboundNormalArc(source, target, this.getNewArcWeight(arcWeight));
      }

      this.newArcs.put(((Arc)newArc).getId(), newArc);
   }

   private Map getNewArcWeight(int arcWeight) {
      Map arcWeights = new HashMap();
      arcWeights.put(this.unfoldToken.getId(), Integer.toString(arcWeight));
      return arcWeights;
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

   private static class Data {
      public final int placeTokenCount;
      public final int arcWeight;
      public final String name;

      public Data(int placeTokenCount, int arcWeight, String name) {
         this.placeTokenCount = placeTokenCount;
         this.arcWeight = arcWeight;
         this.name = name;
      }
   }
}
