package pipe.gui.imperial.steadystate.algorithm;

import com.esotericsoftware.kryo.io.Input;
import pipe.gui.imperial.io.EntireStateReader;
import pipe.gui.imperial.io.KryoStateIO;
import pipe.gui.imperial.io.MultiStateReader;
import pipe.gui.imperial.state.Record;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;

public abstract class AbstractSteadyStateSolver implements SteadyStateSolver {
   protected static final double EPSILON = 1.0E-6D;

   protected final Map transpose(List records) {
      Map transpose = new HashMap();
      Iterator i$ = records.iterator();

      while(i$.hasNext()) {
         Record record = (Record)i$.next();
         Integer state = record.state;
         this.createAndGet(transpose, state);
         Iterator i$1 = record.successors.entrySet().iterator();

         while(i$1.hasNext()) {
            Entry entry = (Entry)i$1.next();
            Integer successor = (Integer)entry.getKey();
            Double rate = (Double)entry.getValue();
            Map successors = this.createAndGet(transpose, successor);
            successors.put(state, rate);
         }
      }

      return transpose;
   }

   protected final double geta(Map diagonals) {
      List values = new ArrayList(diagonals.values());
      double largest = Math.abs(((Double)values.get(0)).doubleValue());
      Iterator i$ = values.iterator();

      while(i$.hasNext()) {
         double value = ((Double)i$.next()).doubleValue();
         double abs = Math.abs(value);
         if (abs > largest) {
            largest = abs;
         }
      }

      return largest + 2.0D;
   }

   private Map createAndGet(Map transpose, Integer state) {
      if (!transpose.containsKey(state)) {
         transpose.put(state, new HashMap());
      }

      return (Map)transpose.get(state);
   }

   public final Map solve(String path) throws IOException {
      try {
         List records = this.loadRecords(path);
         return this.solve(records);
      } catch (URISyntaxException var3) {
         throw new IOException(var3);
      }
   }

   private List loadRecords(String path) throws URISyntaxException, IOException {
      KryoStateIO io = new KryoStateIO();
      Path p = Paths.get(this.getClass().getResource(path).toURI());
      InputStream fileStream = Files.newInputStream(p);
      Throwable var5 = null;

      ArrayList var9;
      try {
         Input inputStream = new Input(fileStream);
         Throwable var7 = null;

         try {
            MultiStateReader reader = new EntireStateReader(io);
            var9 = new ArrayList(reader.readRecords(inputStream));
         } catch (Throwable var32) {
            var7 = var32;
            throw var32;
         } finally {
            if (inputStream != null) {
               if (var7 != null) {
                  try {
                     inputStream.close();
                  } catch (Throwable var31) {
                     var7.addSuppressed(var31);
                  }
               } else {
                  inputStream.close();
               }
            }

         }
      } catch (Throwable var34) {
         var5 = var34;
         throw var34;
      } finally {
         if (fileStream != null) {
            if (var5 != null) {
               try {
                  fileStream.close();
               } catch (Throwable var30) {
                  var5.addSuppressed(var30);
               }
            } else {
               fileStream.close();
            }
         }

      }

      return var9;
   }

   protected final Map normalize(Map x) {
      double sum = 0.0D;

      double value;
      for(Iterator i$ = x.values().iterator(); i$.hasNext(); sum += value) {
         value = ((Double)i$.next()).doubleValue();
      }

      Map normalized = new HashMap();
      if (sum == 0.0D) {
         normalized.putAll(x);
      } else {
         Iterator i$ = x.entrySet().iterator();

         while(i$.hasNext()) {
            Entry entry = (Entry)i$.next();
            normalized.put(entry.getKey(), ((Double)entry.getValue()).doubleValue() / sum);
         }
      }

      return normalized;
   }

   protected final Map initialiseXWithGuess(Map records) {
      Map x = new HashMap();
      Iterator i$ = records.keySet().iterator();

      while(i$.hasNext()) {
         Integer state = (Integer)i$.next();
         x.put(state, 1.0D);
      }

      return x;
   }

   protected final double multiplyAndSum(Map row, Map x) {
      double sum = 0.0D;

      Integer state;
      Double rate;
      for(Iterator i$ = row.entrySet().iterator(); i$.hasNext(); sum += rate.doubleValue() * ((Double)x.get(state)).doubleValue()) {
         Entry entry = (Entry)i$.next();
         state = (Integer)entry.getKey();
         rate = (Double)entry.getValue();
      }

      return sum;
   }

   protected final Map calculateDiagonals(List records) {
      Map diagonals = new HashMap();
      Iterator i$ = records.iterator();

      while(i$.hasNext()) {
         Record record = (Record)i$.next();
         double rowSum = 0.0D;

         Double rate;
         for(Iterator i$1 = record.successors.values().iterator(); i$.hasNext(); rowSum += rate.doubleValue()) {
            rate = (Double)i$1.next();
         }

         diagonals.put(record.state, -rowSum);
      }

      return diagonals;
   }

   protected final List divide(List A, double a) {
      List results = new ArrayList();
      Iterator i$ = A.iterator();

      while(i$.hasNext()) {
         Record record = (Record)i$.next();
         results.add(new Record(record.state, this.divide(a, record.successors)));
      }

      return results;
   }

   protected final Map divide(double a, Map row) {
      Map divided = new HashMap();
      Iterator i$ = row.entrySet().iterator();

      while(i$.hasNext()) {
         Entry entry1 = (Entry)i$.next();
         divided.put(entry1.getKey(), ((Double)entry1.getValue()).doubleValue() / a);
      }

      return divided;
   }
}
