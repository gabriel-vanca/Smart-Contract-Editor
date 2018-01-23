package pipe.gui.imperial.state;

import java.util.Map;

public class Record {
   public final int state;
   public final Map successors;

   public Record(int state, Map successors) {
      this.state = state;
      this.successors = successors;
   }

   public int hashCode() {
      int result = this.state;
      result = 31 * result + this.successors.hashCode();
      return result;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof Record)) {
         return false;
      } else {
         Record record = (Record)o;
         if (this.state != record.state) {
            return false;
         } else {
            return this.successors.equals(record.successors);
         }
      }
   }
}
