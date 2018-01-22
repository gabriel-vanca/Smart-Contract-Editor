package pipe.gui.imperial.pipe.io.adapters.valueAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public final class StringAttributeValueAdaptor extends XmlAdapter {
   public String unmarshal(StringAttributeValueAdaptor.AdaptedAttributeString adaptedString) {
      return adaptedString.value;
   }

   public StringAttributeValueAdaptor.AdaptedAttributeString marshal(String s) {
      StringAttributeValueAdaptor.AdaptedAttributeString adaptedString = new StringAttributeValueAdaptor.AdaptedAttributeString();
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

   public static class AdaptedAttributeString {
      @XmlAttribute
      public String value;
   }
}
