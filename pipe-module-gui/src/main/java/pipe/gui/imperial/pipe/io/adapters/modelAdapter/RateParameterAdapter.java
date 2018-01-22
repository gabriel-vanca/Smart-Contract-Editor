package pipe.gui.imperial.pipe.io.adapters.modelAdapter;

import pipe.gui.imperial.pipe.io.adapters.model.AdaptedRateParameter;
import pipe.gui.imperial.pipe.models.petrinet.FunctionalRateParameter;
import pipe.gui.imperial.pipe.models.petrinet.RateParameter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;

public final class RateParameterAdapter extends XmlAdapter {
   private final Map rateParameters;

   public RateParameterAdapter() {
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

   public RateParameterAdapter(Map rateParameters) {
      this.rateParameters = rateParameters;
   }

   public FunctionalRateParameter unmarshal(AdaptedRateParameter adaptedRateParameter) {
      FunctionalRateParameter rateParameter = new FunctionalRateParameter(adaptedRateParameter.getExpression(), adaptedRateParameter.getId(), adaptedRateParameter.getName());
      this.rateParameters.put(rateParameter.getId(), rateParameter);
      return rateParameter;
   }

   public AdaptedRateParameter marshal(RateParameter rateParameter) {
      AdaptedRateParameter adaptedRateParameter = new AdaptedRateParameter();
      adaptedRateParameter.setExpression(rateParameter.getExpression());
      adaptedRateParameter.setId(rateParameter.getId());
      adaptedRateParameter.setName(rateParameter.getId());
      return adaptedRateParameter;
   }
}
