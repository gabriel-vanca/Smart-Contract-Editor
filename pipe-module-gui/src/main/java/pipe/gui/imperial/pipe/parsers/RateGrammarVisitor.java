package pipe.gui.imperial.pipe.parsers;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import pipe.gui.imperial.pipe.parsers.RateGrammarParser;

public interface RateGrammarVisitor extends ParseTreeVisitor {
   Object visitProgram(pipe.gui.imperial.pipe.parsers.RateGrammarParser.ProgramContext var1);

   Object visitAddOrSubtract(pipe.gui.imperial.pipe.parsers.RateGrammarParser.AddOrSubtractContext var1);

   Object visitMultOrDiv(pipe.gui.imperial.pipe.parsers.RateGrammarParser.MultOrDivContext var1);

   Object visitFloor(pipe.gui.imperial.pipe.parsers.RateGrammarParser.FloorContext var1);

   Object visitInteger(pipe.gui.imperial.pipe.parsers.RateGrammarParser.IntegerContext var1);

   Object visitPlaceTokens(pipe.gui.imperial.pipe.parsers.RateGrammarParser.PlaceTokensContext var1);

   Object visitCeil(pipe.gui.imperial.pipe.parsers.RateGrammarParser.CeilContext var1);

   Object visitPlaceColorTokens(pipe.gui.imperial.pipe.parsers.RateGrammarParser.PlaceColorTokensContext var1);

   Object visitParenExpression(pipe.gui.imperial.pipe.parsers.RateGrammarParser.ParenExpressionContext var1);

   Object visitDouble(pipe.gui.imperial.pipe.parsers.RateGrammarParser.DoubleContext var1);

   Object visitPlaceCapacity(pipe.gui.imperial.pipe.parsers.RateGrammarParser.PlaceCapacityContext var1);

   Object visitCapacity(pipe.gui.imperial.pipe.parsers.RateGrammarParser.CapacityContext var1);

   Object visitToken_number(pipe.gui.imperial.pipe.parsers.RateGrammarParser.Token_numberContext var1);

   Object visitToken_color_number(RateGrammarParser.Token_color_numberContext var1);
}
