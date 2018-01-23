package pipe.gui.imperial.pipe.io.adapters.modelAdapter;

import com.google.common.base.Joiner;
import pipe.gui.imperial.pipe.models.petrinet.Token;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;

public final class TokenSetIntegerAdapter extends XmlAdapter {
   private final Map tokens;

   public TokenSetIntegerAdapter() {
      this.tokens = new HashMap();
   }

   @Override
   public Object unmarshal(Object v) throws Exception {
      return null;
   }

   @Override
   public Object marshal(Object v) throws Exception {
      return null;
   }

   public TokenSetIntegerAdapter(Map tokens) {
      this.tokens = tokens;
   }

   public Map unmarshal(TokenSetIntegerAdapter.AdaptedIntegerTokenSet adaptedTokenSet) {
      Map tokenWeights = new HashMap();
      String weightInput = adaptedTokenSet.value;
      String[] commaSeparatedMarkings = weightInput.split(",");
      Integer weight;
      if (commaSeparatedMarkings.length == 1) {
         Token token = this.getDefaultToken();
         weight = Integer.valueOf(commaSeparatedMarkings[0]);
         tokenWeights.put(token, weight);
      } else {
         for(int i = 0; i < commaSeparatedMarkings.length; i += 2) {
            weight = Integer.valueOf(commaSeparatedMarkings[i + 1].replace("@", ","));
            String tokenName = commaSeparatedMarkings[i];
            Token token = this.getTokenIfExists(tokenName);
            tokenWeights.put(token, weight);
         }
      }

      return tokenWeights;
   }

   public TokenSetIntegerAdapter.AdaptedIntegerTokenSet marshal(Map tokenIntegerMap) {
      TokenSetIntegerAdapter.AdaptedIntegerTokenSet adapted = new TokenSetIntegerAdapter.AdaptedIntegerTokenSet();
      adapted.value = Joiner.on(",").withKeyValueSeparator(",").join(tokenIntegerMap);
      return adapted;
   }

   private Token getTokenIfExists(String tokenName) {
      if (!this.tokens.containsKey(tokenName)) {
         throw new RuntimeException("No " + tokenName + " token exists!");
      } else {
         return (Token)this.tokens.get(tokenName);
      }
   }

   private Token getDefaultToken() {
      return this.getTokenIfExists("Default");
   }

   public static class AdaptedIntegerTokenSet {
      public String value;
   }
}
