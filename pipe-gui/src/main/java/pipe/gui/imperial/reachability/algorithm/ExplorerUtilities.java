package pipe.gui.imperial.reachability.algorithm;

import java.util.Collection;
import java.util.Map;
import pipe.gui.imperial.pipe.exceptions.InvalidRateException;
import pipe.gui.imperial.state.ClassifiedState;

public interface ExplorerUtilities {
   Map getSuccessorsWithTransitions(ClassifiedState var1);

   Collection getSuccessors(ClassifiedState var1);

   double rate(ClassifiedState var1, ClassifiedState var2) throws InvalidRateException;

   ClassifiedState getCurrentState();

   Collection getTransitions(ClassifiedState var1, ClassifiedState var2);

   double getWeightOfTransitions(ClassifiedState var1, Iterable var2) throws InvalidRateException;

   Collection getAllEnabledTransitions(ClassifiedState var1);

   void clear();

   boolean canExploreMore(int var1);
}
