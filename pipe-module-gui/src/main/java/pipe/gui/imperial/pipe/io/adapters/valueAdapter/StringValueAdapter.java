package pipe.gui.imperial.pipe.io.adapters.valueAdapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public final class StringValueAdapter extends XmlAdapter {
   public String unmarshal(StringValueAdapter.AdaptedString adaptedString) {
      return adaptedString.value;
   }

   public StringValueAdapter.AdaptedString marshal(String s) {
      StringValueAdapter.AdaptedString adaptedString = new StringValueAdapter.AdaptedString();
      adaptedString.value = s;
      return adaptedString;
   }

   @Override
   public Object unmarshal(Object v) throws Exception {
      return null;
   }

   @Override
   public Object marshal(Object v) throws Exception {
      return null;
   }

   public static class AdaptedString {
      public String value;
   }
}
