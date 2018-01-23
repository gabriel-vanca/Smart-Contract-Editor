package pipe.gui.imperial.pipe.io.adapters.modelAdapter;

import com.google.common.base.Joiner;
import pipe.gui.imperial.pipe.io.adapters.model.AdaptedPlace;
import pipe.gui.imperial.pipe.io.adapters.model.NameDetails;
import pipe.gui.imperial.pipe.io.adapters.model.OffsetGraphics;
import pipe.gui.imperial.pipe.io.adapters.model.Point;
import pipe.gui.imperial.pipe.io.adapters.utils.ConnectableUtils;
import pipe.gui.imperial.pipe.models.petrinet.DiscretePlace;
import pipe.gui.imperial.pipe.models.petrinet.Place;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;

public final class PlaceAdapter extends XmlAdapter {
   private final Map places;

   public PlaceAdapter() {
      this.places = new HashMap();
   }

   @Override
   public Object unmarshal(Object v) throws Exception {
      return unmarshal((AdaptedPlace) v);
   }

   @Override
   public Object marshal(Object v) throws Exception {
      return marshal((Place) v);
   }

   public PlaceAdapter(Map places) {
      this.places = places;
   }

   public Place unmarshal(AdaptedPlace adaptedPlace) throws Exception {
      NameDetails nameDetails = adaptedPlace.getName();
      DiscretePlace place = new DiscretePlace(adaptedPlace.getId(), nameDetails.getName());
      place.setCapacity(adaptedPlace.getCapacity().intValue());
      ConnectableUtils.setConnectablePosition(place, adaptedPlace);
      ConnectableUtils.setConntactableNameOffset(place, adaptedPlace);
      place.setTokenCounts(this.stringToWeights(adaptedPlace.getInitialMarking().getTokenCounts()));
      this.places.put(place.getId(), place);
      return place;
   }

   public AdaptedPlace marshal(Place place) {
      AdaptedPlace adapted = new AdaptedPlace();
      adapted.setId(place.getId());
      ConnectableUtils.setAdaptedName(place, adapted);
      ConnectableUtils.setPosition(place, adapted);
      adapted.setCapacity(place.getCapacity());
      adapted.getInitialMarking().setTokenCounts(this.weightToString(place.getTokenCounts()));
      OffsetGraphics offsetGraphics = new OffsetGraphics();
      offsetGraphics.point = new Point();
      offsetGraphics.point.setX(place.getMarkingXOffset());
      offsetGraphics.point.setY(place.getMarkingYOffset());
      adapted.getInitialMarking().setGraphics(offsetGraphics);
      return adapted;
   }

   private String weightToString(Map weights) {
      return Joiner.on(",").withKeyValueSeparator(",").join(weights);
   }

   public Map stringToWeights(String value) {
      Map tokenWeights = new HashMap();
      if (value.isEmpty()) {
         return tokenWeights;
      } else {
         String[] commaSeparatedMarkings = value.split(",");
         if (commaSeparatedMarkings.length == 1) {
            Integer weight = Integer.valueOf(commaSeparatedMarkings[0]);
            tokenWeights.put("Default", weight);
         } else {
            for(int i = 0; i < commaSeparatedMarkings.length; i += 2) {
               Integer weight = Integer.valueOf(commaSeparatedMarkings[i + 1].replace("@", ","));
               String tokenName = commaSeparatedMarkings[i];
               tokenWeights.put(tokenName, weight);
            }
         }

         return tokenWeights;
      }
   }
}
