package pipe.gui.imperial.pipe.parsers;

import com.google.common.primitives.Doubles;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;

import java.util.*;

public class PetriNetWeightParser implements pipe.gui.imperial.pipe.parsers.FunctionalWeightParser {
   private final PetriNet petriNet;
   private final pipe.gui.imperial.pipe.parsers.RateGrammarBaseVisitor evalVisitor;

   public PetriNetWeightParser(pipe.gui.imperial.pipe.parsers.RateGrammarBaseVisitor evalVisitor, PetriNet petriNet) {
      this.evalVisitor = evalVisitor;
      this.petriNet = petriNet;
   }

   private Set getReferencedComponents(ParseTree parseTree) {
      ParseTreeWalker walker = new ParseTreeWalker();
      PetriNetWeightParser.ComponentListener listener = new PetriNetWeightParser.ComponentListener();
      walker.walk(listener, parseTree);
      return listener.getComponentIds();
   }

   private boolean allComponentsInPetriNet(Set components) {
      Iterator i$ = components.iterator();

      String id;
      do {
         if (!i$.hasNext()) {
            return true;
         }

         id = (String)i$.next();
      } while(this.petriNet.containsComponent(id));

      return false;
   }

   public FunctionalResults evaluateExpression(String expression) {
      Double maybeDouble = Doubles.tryParse(expression);
      if (maybeDouble != null) {
         return new pipe.gui.imperial.pipe.parsers.FunctionalResults(maybeDouble, new HashSet());
      } else {
         RateGrammarErrorListener errorListener = new RateGrammarErrorListener();
         ParseTree parseTree = GrammarUtils.parse(expression, errorListener);
         List errors = new LinkedList();
         if (errorListener.hasErrors()) {
            errors.addAll(errorListener.getErrors());
         }

         Set components = this.getReferencedComponents(parseTree);
         if (!this.allComponentsInPetriNet(components)) {
            errors.add("Not all referenced components exist in the Petri net!");
         }

         if (!errors.isEmpty()) {
            return new pipe.gui.imperial.pipe.parsers.FunctionalResults(-1.0D, errors, components);
         } else {
            Double result = (Double)this.evalVisitor.visit(parseTree);
            if (result.doubleValue() < 0.0D) {
               errors.add("Expression result cannot be less than zero!");
               return new pipe.gui.imperial.pipe.parsers.FunctionalResults(-1.0D, errors, components);
            } else {
               return new FunctionalResults((Number)this.evalVisitor.visit(parseTree), components);
            }
         }
      }
   }

   static class ComponentListener extends RateGrammarBaseListener {
      private Set componentIds = new HashSet();

      public void exitToken_number(@NotNull pipe.gui.imperial.pipe.parsers.RateGrammarParser.Token_numberContext ctx) {
         this.componentIds.add(ctx.ID().getText());
      }

      public void exitToken_color_number(RateGrammarParser.Token_color_numberContext ctx) {
         this.componentIds.add(((TerminalNode)ctx.ID().get(0)).getText());
         this.componentIds.add(((TerminalNode)ctx.ID().get(1)).getText());
      }

      public Set getComponentIds() {
         return this.componentIds;
      }
   }
}
