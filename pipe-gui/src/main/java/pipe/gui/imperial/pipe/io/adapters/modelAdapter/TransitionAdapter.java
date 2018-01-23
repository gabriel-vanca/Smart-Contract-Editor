package pipe.gui.imperial.pipe.io.adapters.modelAdapter;

import pipe.gui.imperial.pipe.io.adapters.model.AdaptedTransition;
import pipe.gui.imperial.pipe.io.adapters.model.NameDetails;
import pipe.gui.imperial.pipe.io.adapters.utils.ConnectableUtils;
import pipe.gui.imperial.pipe.models.petrinet.*;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;

public final class TransitionAdapter extends XmlAdapter {
   private final Map transitions;
   private final Map rateParameters;

   public TransitionAdapter() {
      this.transitions = new HashMap();
      this.rateParameters = new HashMap();
   }

   @Override
   public Object unmarshal(Object v) throws Exception {
      return null;
   }

   @Override
   public Object marshal(Object v) throws Exception {
      return null;
   }

   public TransitionAdapter(Map transitions, Map rateParameters) {
      this.transitions = transitions;
      this.rateParameters = rateParameters;
   }

   public Transition unmarshal(AdaptedTransition adaptedTransition) {
      NameDetails nameDetails = adaptedTransition.getName();
      Transition transition = new DiscreteTransition(adaptedTransition.getId(), nameDetails.getName());
      ConnectableUtils.setConntactableNameOffset(transition, adaptedTransition);
      ConnectableUtils.setConnectablePosition(transition, adaptedTransition);
      transition.setAngle(adaptedTransition.getAngle());
      transition.setPriority(adaptedTransition.getPriority());
      AdaptedTransition.ToolSpecific toolSpecific = adaptedTransition.getToolSpecific();
      Object rate;
      if (toolSpecific == null) {
         rate = new NormalRate(adaptedTransition.getRate());
      } else {
         rate = (Rate)this.rateParameters.get(toolSpecific.getRateDefinition());
      }

      transition.setRate((Rate)rate);
      transition.setTimed(adaptedTransition.getTimed().booleanValue());
      transition.setInfiniteServer(adaptedTransition.getInfiniteServer().booleanValue());
      this.transitions.put(transition.getId(), transition);
      return transition;
   }

   public AdaptedTransition marshal(Transition transition) {
      AdaptedTransition adaptedTransition = new AdaptedTransition();
      ConnectableUtils.setAdaptedName(transition, adaptedTransition);
      adaptedTransition.setId(transition.getId());
      ConnectableUtils.setPosition(transition, adaptedTransition);
      adaptedTransition.setPriority(transition.getPriority());
      adaptedTransition.setAngle(transition.getAngle());
      adaptedTransition.setRate(transition.getRateExpr());
      adaptedTransition.setInfiniteServer(transition.isInfiniteServer());
      adaptedTransition.setTimed(transition.isTimed());
      Rate rate = transition.getRate();
      if (rate instanceof FunctionalRateParameter) {
         FunctionalRateParameter rateParameter = (FunctionalRateParameter)rate;
         AdaptedTransition.ToolSpecific toolSpecific = new AdaptedTransition.ToolSpecific();
         toolSpecific.setRateDefinition(rateParameter.getId());
         adaptedTransition.setToolSpecific(toolSpecific);
      }

      return adaptedTransition;
   }
}
