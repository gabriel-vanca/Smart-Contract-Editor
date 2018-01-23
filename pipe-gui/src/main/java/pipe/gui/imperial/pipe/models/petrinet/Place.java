package pipe.gui.imperial.pipe.models.petrinet;

import java.util.Map;

public interface Place extends Connectable {
   int DIAMETER = 30;
   String TOKEN_CHANGE_MESSAGE = "tokens";

   double getMarkingXOffset();

   void setMarkingXOffset(double var1);

   double getMarkingYOffset();

   void setMarkingYOffset(double var1);

   int getCapacity();

   void setCapacity(int var1);

   Map getTokenCounts();

   void setTokenCounts(Map var1);

   boolean hasCapacityRestriction();

   void incrementTokenCount(String var1);

   void setTokenCount(String var1, int var2);

   int getNumberOfTokensStored();

   int getTokenCount(String var1);

   void decrementTokenCount(String var1);

   void removeAllTokens(String var1);
}
