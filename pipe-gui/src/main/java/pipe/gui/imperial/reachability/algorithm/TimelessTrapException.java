package pipe.gui.imperial.reachability.algorithm;

public class TimelessTrapException extends Exception {
   private static final long serialVersionUID = 1L;

   public TimelessTrapException(Throwable cause) {
      super(cause);
   }

   public TimelessTrapException() {
   }

   public TimelessTrapException(String message) {
      super(message);
   }

   public TimelessTrapException(String message, Throwable cause) {
      super(message, cause);
   }
}
