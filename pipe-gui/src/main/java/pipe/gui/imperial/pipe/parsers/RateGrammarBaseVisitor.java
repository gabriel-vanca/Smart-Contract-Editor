package pipe.gui.imperial.pipe.parsers;

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import pipe.gui.imperial.pipe.parsers.RateGrammarParser;
import pipe.gui.imperial.pipe.parsers.RateGrammarVisitor;

public class RateGrammarBaseVisitor extends AbstractParseTreeVisitor implements RateGrammarVisitor {
   public Object visitProgram(pipe.gui.imperial.pipe.parsers.RateGrammarParser.ProgramContext ctx) {
      return this.visitChildren(ctx);
   }

   public Object visitAddOrSubtract(pipe.gui.imperial.pipe.parsers.RateGrammarParser.AddOrSubtractContext ctx) {
      return this.visitChildren(ctx);
   }

   public Object visitMultOrDiv(pipe.gui.imperial.pipe.parsers.RateGrammarParser.MultOrDivContext ctx) {
      return this.visitChildren(ctx);
   }

   public Object visitFloor(pipe.gui.imperial.pipe.parsers.RateGrammarParser.FloorContext ctx) {
      return this.visitChildren(ctx);
   }

   public Object visitInteger(pipe.gui.imperial.pipe.parsers.RateGrammarParser.IntegerContext ctx) {
      return this.visitChildren(ctx);
   }

   public Object visitPlaceTokens(pipe.gui.imperial.pipe.parsers.RateGrammarParser.PlaceTokensContext ctx) {
      return this.visitChildren(ctx);
   }

   public Object visitCeil(pipe.gui.imperial.pipe.parsers.RateGrammarParser.CeilContext ctx) {
      return this.visitChildren(ctx);
   }

   public Object visitPlaceColorTokens(pipe.gui.imperial.pipe.parsers.RateGrammarParser.PlaceColorTokensContext ctx) {
      return this.visitChildren(ctx);
   }

   public Object visitParenExpression(pipe.gui.imperial.pipe.parsers.RateGrammarParser.ParenExpressionContext ctx) {
      return this.visitChildren(ctx);
   }

   public Object visitDouble(pipe.gui.imperial.pipe.parsers.RateGrammarParser.DoubleContext ctx) {
      return this.visitChildren(ctx);
   }

   public Object visitPlaceCapacity(pipe.gui.imperial.pipe.parsers.RateGrammarParser.PlaceCapacityContext ctx) {
      return this.visitChildren(ctx);
   }

   public Object visitCapacity(pipe.gui.imperial.pipe.parsers.RateGrammarParser.CapacityContext ctx) {
      return this.visitChildren(ctx);
   }

   public Object visitToken_number(pipe.gui.imperial.pipe.parsers.RateGrammarParser.Token_numberContext ctx) {
      return this.visitChildren(ctx);
   }

   public Object visitToken_color_number(RateGrammarParser.Token_color_numberContext ctx) {
      return this.visitChildren(ctx);
   }
}
