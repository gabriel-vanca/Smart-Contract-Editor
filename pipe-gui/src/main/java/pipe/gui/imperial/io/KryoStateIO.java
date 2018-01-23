package pipe.gui.imperial.io;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.MapSerializer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import pipe.gui.imperial.state.ClassifiedState;
import pipe.gui.imperial.state.HashedClassifiedState;
import pipe.gui.imperial.state.HashedState;
import pipe.gui.imperial.state.Record;
import pipe.gui.imperial.state.State;

public final class KryoStateIO implements StateWriter, StateReader {
   private Kryo kryo = new Kryo();

   public KryoStateIO() {
      this.kryo.register(HashedState.class);
      this.kryo.register(HashedClassifiedState.class);
      this.kryo.register(Double.class);
      this.kryo.register(Integer.class);
      this.kryo.register(Boolean.class);
      this.kryo.register(Map.class, new MapSerializer());
   }

   public void writeTransitions(int state, Map successors, Output output) {
      this.kryo.writeObject(output, state);
      this.kryo.writeObject(output, successors.size());
      Iterator i$ = successors.entrySet().iterator();

      while(i$.hasNext()) {
         Entry entry = (Entry)i$.next();
         this.kryo.writeObject(output, entry.getKey());
         this.kryo.writeObject(output, entry.getValue());
      }

   }

   public void writeState(ClassifiedState state, int stateId, Output output) {
      this.kryo.writeObject(output, stateId);
      this.kryo.writeObject(output, state.isTangible());
      this.kryo.writeObject(output, state.asMap());
   }

   public Record readRecord(Input input) throws IOException {
      try {
         Integer state = (Integer)this.kryo.readObject(input, Integer.class);
         int successors = ((Integer)this.kryo.readObject(input, Integer.class)).intValue();
         Map successorRates = new HashMap();

         for(int i = 0; i < successors; ++i) {
            Integer successor = (Integer)this.kryo.readObject(input, Integer.class);
            Double rate = (Double)this.kryo.readObject(input, Double.class);
            successorRates.put(successor, rate);
         }

         return new Record(state.intValue(), successorRates);
      } catch (KryoException var8) {
         throw new IOException("Cannot read record", var8);
      }
   }

   public StateMapping readState(Input inputStream) {
      Integer id = (Integer)this.kryo.readObject(inputStream, Integer.class);
      Boolean tangible = (Boolean)this.kryo.readObject(inputStream, Boolean.class);
      Map map = (Map)this.kryo.readObject(inputStream, HashMap.class);
      State state = new HashedState(map);
      HashedClassifiedState classifiedState;
      if (tangible.booleanValue()) {
         classifiedState = HashedClassifiedState.tangibleState(state);
      } else {
         classifiedState = HashedClassifiedState.vanishingState(state);
      }

      return new StateMapping(classifiedState, id.intValue());
   }
}
