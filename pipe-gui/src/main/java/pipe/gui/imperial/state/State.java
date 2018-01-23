package pipe.gui.imperial.state;

import com.google.common.hash.HashCode;
import java.util.Collection;
import java.util.Map;

public interface State {
   Map getTokens(String var1);

   boolean containsTokens(String var1);

   Collection getPlaces();

   int primaryHash();

   HashCode secondaryHash();

   Map asMap();
}
