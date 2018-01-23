package pipe.gui.imperial.utils;

import com.google.common.hash.HashCode;
import pipe.gui.imperial.state.ClassifiedState;

import java.util.*;

public final class ExploredSet {
   private final int arraySize;
   private final List array;
   private int itemCount = 0;

   public ExploredSet(int arraySize) {
      this.arraySize = arraySize;
      this.array = new ArrayList(arraySize);

      for(int i = 0; i < arraySize; ++i) {
         this.array.add(new TreeMap());
      }

   }

   public void add(ClassifiedState state, int id) {
      int location = this.getLocation(state);
      TreeMap structure = (TreeMap)this.array.get(location);
      int previousSize = structure.size();
      structure.put(new ExploredSet.WrappedHash(state.secondaryHash()), id);
      if (structure.size() > previousSize) {
         ++this.itemCount;
      }

   }

   public void addAll(Collection states, List ids) {
      int i = 0;

      for(Iterator i$ = states.iterator(); i$.hasNext(); ++i) {
         ClassifiedState state = (ClassifiedState)i$.next();
         this.add(state, ((Integer)ids.get(i)).intValue());
      }

   }

   public boolean contains(ClassifiedState state) {
      int location = this.getLocation(state);
      TreeMap structure = (TreeMap)this.array.get(location);
      ExploredSet.WrappedHash wrappedHash = new ExploredSet.WrappedHash(state.secondaryHash());
      return structure.containsKey(wrappedHash);
   }

   public int getLocation(ClassifiedState state) {
      int location = this.hashOneInt(state) % this.arraySize;
      return Math.abs(location);
   }

   private int hashOneInt(ClassifiedState state) {
      return this.hashOne(state);
   }

   private int hashOne(ClassifiedState state) {
      return state.primaryHash();
   }

   public void clear() {
      Iterator i$ = this.array.iterator();

      while(i$.hasNext()) {
         TreeMap list = (TreeMap)i$.next();
         list.clear();
      }

   }

   public int size() {
      return this.itemCount;
   }

   public int getId(ClassifiedState state) {
      int location = this.getLocation(state);
      TreeMap structure = (TreeMap)this.array.get(location);
      ExploredSet.WrappedHash wrappedHash = new ExploredSet.WrappedHash(state.secondaryHash());
      return ((Integer)structure.get(wrappedHash)).intValue();
   }

   // $FF: synthetic class
   static class SyntheticClass_1 {
   }

   private static final class WrappedHash implements Comparable {
      private final HashCode hash;

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (!(o instanceof ExploredSet.WrappedHash)) {
            return false;
         } else {
            ExploredSet.WrappedHash that = (ExploredSet.WrappedHash)o;
            return this.hash.equals(that.hash);
         }
      }

      public int hashCode() {
         return this.hash.hashCode();
      }

      private WrappedHash(HashCode hash) {
         this.hash = hash;
      }

      public int compareTo(ExploredSet.WrappedHash o) {
         return Integer.compare(this.hash.asInt(), o.hash.asInt());
      }

      // $FF: synthetic method
      WrappedHash(HashCode x0, ExploredSet.SyntheticClass_1 x1) {
         this(x0);
      }

      @Override
      public int compareTo(Object o) {
         return 0;
      }
   }
}
