package pipe.gui.imperial.io;

import com.esotericsoftware.kryo.io.Output;
import java.util.Map;
import pipe.gui.imperial.state.ClassifiedState;
import pipe.gui.imperial.io.StateProcessor;
import pipe.gui.imperial.io.StateWriter;

public final class StateIOProcessor implements StateProcessor {
   private final pipe.gui.imperial.io.StateWriter writer;
   private final Output transitionOutput;
   private Output stateOutput;

   public StateIOProcessor(StateWriter writer, Output transitionOutput, Output stateOutput) {
      this.writer = writer;
      this.transitionOutput = transitionOutput;
      this.stateOutput = stateOutput;
   }

   public void processTransitions(int stateId, Map successorRates) {
      this.writer.writeTransitions(stateId, successorRates, this.transitionOutput);
   }

   public void processState(ClassifiedState state, int stateId) {
      this.writer.writeState(state, stateId, this.stateOutput);
   }
}
