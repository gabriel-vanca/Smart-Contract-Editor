package pipe.gui.imperial.pipe.io.adapters.model;

import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import pipe.gui.imperial.pipe.io.adapters.modelAdapter.AnnotationAdapter;
import pipe.gui.imperial.pipe.io.adapters.modelAdapter.ArcAdapter;
import pipe.gui.imperial.pipe.io.adapters.modelAdapter.PlaceAdapter;
import pipe.gui.imperial.pipe.io.adapters.modelAdapter.RateParameterAdapter;
import pipe.gui.imperial.pipe.io.adapters.modelAdapter.TokenAdapter;
import pipe.gui.imperial.pipe.io.adapters.modelAdapter.TransitionAdapter;

@XmlType(
   propOrder = {"tokens", "annotations", "rateParameters", "places", "transitions", "arcs"}
)
public class AdaptedPetriNet {
   @XmlElement(
      name = "token"
   )
   @XmlJavaTypeAdapter(TokenAdapter.class)
   public Collection tokens;
   @XmlElement(
      name = "labels"
   )
   @XmlJavaTypeAdapter(AnnotationAdapter.class)
   public Collection annotations;
   @XmlElement(
      name = "definition"
   )
   @XmlJavaTypeAdapter(RateParameterAdapter.class)
   public Collection rateParameters;
   @XmlElement(
      name = "place"
   )
   @XmlJavaTypeAdapter(PlaceAdapter.class)
   public Collection places;
   @XmlElement(
      name = "transition"
   )
   @XmlJavaTypeAdapter(TransitionAdapter.class)
   public Collection transitions;
   @XmlElement(
      name = "arc"
   )
   @XmlJavaTypeAdapter(ArcAdapter.class)
   public Collection arcs;
}
