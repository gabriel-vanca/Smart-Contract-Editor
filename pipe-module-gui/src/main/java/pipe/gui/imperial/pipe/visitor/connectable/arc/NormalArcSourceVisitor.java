package pipe.gui.imperial.pipe.visitor.connectable.arc;

import pipe.gui.imperial.pipe.exceptions.PetriNetComponentException;
import pipe.gui.imperial.pipe.models.petrinet.Connectable;
import pipe.gui.imperial.pipe.models.petrinet.DiscretePlace;
import pipe.gui.imperial.pipe.models.petrinet.DiscreteTransition;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class NormalArcSourceVisitor implements ArcSourceVisitor {
   private static final Logger LOGGER = Logger.getLogger(NormalArcSourceVisitor.class.getName());
   private boolean canCreate = false;

   public void visit(DiscretePlace place) {
      this.canCreate = true;
   }

   public void visit(DiscreteTransition transition) {
      this.canCreate = true;
   }

   public boolean canStart(Connectable connectable) {
      try {
         connectable.accept(this);
      } catch (PetriNetComponentException var3) {
         LOGGER.log(Level.SEVERE, var3.getMessage());
         return false;
      }

      return this.canCreate;
   }
}
