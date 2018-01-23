package pipe.gui.imperial.pipe.dsl;

import java.util.Map;

import pipe.gui.imperial.pipe.dsl.DSLCreator;
import pipe.gui.imperial.pipe.models.petrinet.DiscreteTransition;
import pipe.gui.imperial.pipe.models.petrinet.NormalRate;
import pipe.gui.imperial.pipe.models.petrinet.Rate;
import pipe.gui.imperial.pipe.models.petrinet.Transition;

public abstract class AnAbstractTransition implements DSLCreator {
   protected String id;
   private int priority = 1;
   private final boolean timed;
   private boolean infinite = false;
   protected String rate = "";
   protected String rateParameter = "";
   private int x = 0;
   private int y = 0;

   public AnAbstractTransition(String id, boolean timed) {
      this.id = id;
      this.timed = timed;
   }

   public Transition create(Map tokens, Map places, Map transitions, Map rateParameters) {
      Transition transition = new DiscreteTransition(this.id, this.id);
      transition.setPriority(this.priority);
      transition.setTimed(this.timed);
      transition.setInfiniteServer(this.infinite);
      transition.setX(this.x);
      transition.setY(this.y);
      if (!this.rate.isEmpty()) {
         transition.setRate(new NormalRate(this.rate));
      } else if (!this.rateParameter.isEmpty()) {
         transition.setRate((Rate)rateParameters.get(this.rateParameter));
      }

      transitions.put(this.id, transition);
      return transition;
   }

   public AnAbstractTransition andPriority(int priority) {
      this.priority = priority;
      return this.getInstance();
   }

   public AnAbstractTransition andIsAnInfinite() {
      this.infinite = true;
      return this.getInstance();
   }

   public AnAbstractTransition andIsASingle() {
      this.infinite = false;
      return this.getInstance();
   }

   public AnAbstractTransition server() {
      return this.getInstance();
   }

   public AnAbstractTransition locatedAt(int x, int y) {
      this.x = x;
      this.y = y;
      return this.getInstance();
   }

   protected abstract AnAbstractTransition getInstance();
}
