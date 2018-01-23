package pipe.gui.imperial.pipe.parsers;

import org.antlr.v4.runtime.tree.ParseTreeListener;
import pipe.gui.imperial.pipe.parsers.RateGrammarParser;

public interface RateGrammarListener extends ParseTreeListener {
   void enterProgram(pipe.gui.imperial.pipe.parsers.RateGrammarParser.ProgramContext var1);

   void exitProgram(pipe.gui.imperial.pipe.parsers.RateGrammarParser.ProgramContext var1);

   void enterAddOrSubtract(pipe.gui.imperial.pipe.parsers.RateGrammarParser.AddOrSubtractContext var1);

   void exitAddOrSubtract(pipe.gui.imperial.pipe.parsers.RateGrammarParser.AddOrSubtractContext var1);

   void enterMultOrDiv(pipe.gui.imperial.pipe.parsers.RateGrammarParser.MultOrDivContext var1);

   void exitMultOrDiv(pipe.gui.imperial.pipe.parsers.RateGrammarParser.MultOrDivContext var1);

   void enterFloor(pipe.gui.imperial.pipe.parsers.RateGrammarParser.FloorContext var1);

   void exitFloor(pipe.gui.imperial.pipe.parsers.RateGrammarParser.FloorContext var1);

   void enterInteger(pipe.gui.imperial.pipe.parsers.RateGrammarParser.IntegerContext var1);

   void exitInteger(pipe.gui.imperial.pipe.parsers.RateGrammarParser.IntegerContext var1);

   void enterPlaceTokens(pipe.gui.imperial.pipe.parsers.RateGrammarParser.PlaceTokensContext var1);

   void exitPlaceTokens(pipe.gui.imperial.pipe.parsers.RateGrammarParser.PlaceTokensContext var1);

   void enterCeil(pipe.gui.imperial.pipe.parsers.RateGrammarParser.CeilContext var1);

   void exitCeil(pipe.gui.imperial.pipe.parsers.RateGrammarParser.CeilContext var1);

   void enterPlaceColorTokens(pipe.gui.imperial.pipe.parsers.RateGrammarParser.PlaceColorTokensContext var1);

   void exitPlaceColorTokens(pipe.gui.imperial.pipe.parsers.RateGrammarParser.PlaceColorTokensContext var1);

   void enterParenExpression(pipe.gui.imperial.pipe.parsers.RateGrammarParser.ParenExpressionContext var1);

   void exitParenExpression(pipe.gui.imperial.pipe.parsers.RateGrammarParser.ParenExpressionContext var1);

   void enterDouble(pipe.gui.imperial.pipe.parsers.RateGrammarParser.DoubleContext var1);

   void exitDouble(pipe.gui.imperial.pipe.parsers.RateGrammarParser.DoubleContext var1);

   void enterPlaceCapacity(pipe.gui.imperial.pipe.parsers.RateGrammarParser.PlaceCapacityContext var1);

   void exitPlaceCapacity(pipe.gui.imperial.pipe.parsers.RateGrammarParser.PlaceCapacityContext var1);

   void enterCapacity(pipe.gui.imperial.pipe.parsers.RateGrammarParser.CapacityContext var1);

   void exitCapacity(pipe.gui.imperial.pipe.parsers.RateGrammarParser.CapacityContext var1);

   void enterToken_number(pipe.gui.imperial.pipe.parsers.RateGrammarParser.Token_numberContext var1);

   void exitToken_number(pipe.gui.imperial.pipe.parsers.RateGrammarParser.Token_numberContext var1);

   void enterToken_color_number(pipe.gui.imperial.pipe.parsers.RateGrammarParser.Token_color_numberContext var1);

   void exitToken_color_number(RateGrammarParser.Token_color_numberContext var1);
}
