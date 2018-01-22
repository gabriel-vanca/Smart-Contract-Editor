package pipe.gui.imperial.pipe.io.adapters.valueAdapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BooleanValueAdapter extends XmlAdapter {
   public Boolean unmarshal(BooleanValueAdapter.AdaptedBoolean adaptedBoolean) {
      return adaptedBoolean.value;
   }

   public BooleanValueAdapter.AdaptedBoolean marshal(Boolean aBoolean) {
      BooleanValueAdapter.AdaptedBoolean adapted = new BooleanValueAdapter.AdaptedBoolean();
      adapted.value = aBoolean.booleanValue();
      return adapted;
   }

   @Override
   public Object unmarshal(Object v) throws Exception {
      return null;
   }

   @Override
   public Object marshal(Object v) throws Exception {
      return null;
   }

   public static class AdaptedBoolean {
      public boolean value;
   }
}
