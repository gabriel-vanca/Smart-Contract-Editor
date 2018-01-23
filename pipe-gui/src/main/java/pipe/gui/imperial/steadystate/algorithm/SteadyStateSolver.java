package pipe.gui.imperial.steadystate.algorithm;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SteadyStateSolver {
   Map solve(String var1) throws IOException;

   Map solve(List var1);
}
