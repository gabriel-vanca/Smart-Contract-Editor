package pipe.gui.imperial.pipe.dsl;

import java.awt.Color;
import java.util.Map;
import pipe.gui.imperial.pipe.models.petrinet.ColoredToken;
import pipe.gui.imperial.pipe.models.petrinet.Token;

public final class AToken implements DSLCreator {
   private String name;
   private Color color;

   private AToken(String name) {
      this.color = Color.BLACK;
      this.name = name;
   }

   public static AToken called(String name) {
      return new AToken(name);
   }

   public AToken withColor(Color color) {
      this.color = color;
      return this;
   }

   public Token create(Map tokens, Map places, Map transitions, Map rateParameters) {
      Token token = new ColoredToken(this.name, this.color);
      tokens.put(this.name, token);
      return token;
   }
}
