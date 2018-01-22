package pipe.gui.imperial.pipe.exceptions;

public class InvalidRateException extends Exception {
   public InvalidRateException(String invalidRate) {
      super("Rate of " + invalidRate + " is invalid");
   }
}
