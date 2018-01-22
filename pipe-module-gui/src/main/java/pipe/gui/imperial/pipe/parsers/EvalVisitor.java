package pipe.gui.imperial.pipe.parsers;

import org.antlr.v4.runtime.tree.TerminalNode;
import pipe.gui.imperial.pipe.exceptions.PetriNetComponentNotFoundException;
import pipe.gui.imperial.pipe.models.petrinet.DiscretePlace;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;

public final class EvalVisitor extends RateGrammarBaseVisitor {
   private PetriNet petriNet;

   public EvalVisitor(PetriNet petriNet) {
      this.petriNet = petriNet;
   }

   public Double visitMultOrDiv(RateGrammarParser.MultOrDivContext ctx) {
      Double left = (Double)this.visit(ctx.expression(0));
      Double right = (Double)this.visit(ctx.expression(1));
      return ctx.op.getType() == 12 ? left.doubleValue() * right.doubleValue() : left.doubleValue() / right.doubleValue();
   }

   public Double visitAddOrSubtract(RateGrammarParser.AddOrSubtractContext ctx) {
      Double left = (Double)this.visit(ctx.expression(0));
      Double right = (Double)this.visit(ctx.expression(1));
      return ctx.op.getType() == 14 ? left.doubleValue() + right.doubleValue() : left.doubleValue() - right.doubleValue();
   }

   public Double visitParenExpression(RateGrammarParser.ParenExpressionContext ctx) {
      return (Double)this.visit(ctx.expression());
   }

   public Double visitToken_number(RateGrammarParser.Token_numberContext ctx) {
      try {
         DiscretePlace place = this.getPlace(ctx.ID().getText());
         return (double)place.getNumberOfTokensStored();
      } catch (PetriNetComponentNotFoundException var3) {
         return 0.0D;
      }
   }

   public Double visitToken_color_number(RateGrammarParser.Token_color_numberContext ctx) {
      String name = ((TerminalNode)ctx.ID().get(0)).getText();
      String color = ((TerminalNode)ctx.ID().get(1)).getText();

      try {
         DiscretePlace place = this.getPlace(name);
         return (double)place.getTokenCount(color);
      } catch (PetriNetComponentNotFoundException var5) {
         return 0.0D;
      }
   }

   public Double visitCapacity(RateGrammarParser.CapacityContext ctx) {
      try {
         DiscretePlace place = this.getPlace(ctx.ID().getText());
         return (double)place.getCapacity();
      } catch (PetriNetComponentNotFoundException var3) {
         return 0.0D;
      }
   }

   public Double visitInteger(RateGrammarParser.IntegerContext ctx) {
      return Double.valueOf(ctx.INT().getText());
   }

   public Double visitDouble(RateGrammarParser.DoubleContext ctx) {
      return Double.valueOf(ctx.DOUBLE().getText());
   }

   public Double visitFloor(RateGrammarParser.FloorContext ctx) {
      Double value = (Double)this.visit(ctx.expression());
      return Math.floor(value.doubleValue());
   }

   public Double visitCeil(RateGrammarParser.CeilContext ctx) {
      Double value = (Double)this.visit(ctx.expression());
      return Math.ceil(value.doubleValue());
   }

   public DiscretePlace getPlace(String id) throws PetriNetComponentNotFoundException {
      return (DiscretePlace)this.petriNet.getComponent(id, DiscretePlace.class);
   }
}
