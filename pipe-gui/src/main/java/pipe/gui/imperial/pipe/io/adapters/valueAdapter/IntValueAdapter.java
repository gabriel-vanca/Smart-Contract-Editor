package pipe.gui.imperial.pipe.io.adapters.valueAdapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public final class IntValueAdapter extends XmlAdapter {
   public Integer unmarshal(IntValueAdapter.IntAdapter intAdapter) {
      return intAdapter.value;
   }

   public IntValueAdapter.IntAdapter marshal(Integer integer) {
      IntValueAdapter.IntAdapter adapter = new IntValueAdapter.IntAdapter();
      adapter.value = integer.intValue();
      return adapter;
   }

   @Override
   public Object unmarshal(Object v) throws Exception {
      return null;
   }

   @Override
   public Object marshal(Object v) throws Exception {
      return null;
   }

   public static class IntAdapter {
      public int value;
   }
}
