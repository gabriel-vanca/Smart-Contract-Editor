package pipe.gui.imperial.pipe.visitor;

import pipe.gui.imperial.pipe.models.petrinet.AnnotationVisitor;
import pipe.gui.imperial.pipe.models.petrinet.ArcVisitor;
import pipe.gui.imperial.pipe.models.petrinet.PlaceVisitor;
import pipe.gui.imperial.pipe.models.petrinet.RateVisitor;
import pipe.gui.imperial.pipe.models.petrinet.TokenVisitor;
import pipe.gui.imperial.pipe.models.petrinet.TransitionVisitor;

public interface AllComponentVisitor extends PlaceVisitor, TransitionVisitor, ArcVisitor, AnnotationVisitor, TokenVisitor, RateVisitor {
}
