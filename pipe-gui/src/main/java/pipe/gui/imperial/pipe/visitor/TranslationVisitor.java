package pipe.gui.imperial.pipe.visitor;

import pipe.gui.imperial.pipe.models.petrinet.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class TranslationVisitor implements ArcVisitor, ArcPointVisitor, PlaceVisitor, TransitionVisitor, AnnotationVisitor {
   private final Point translation;
   private final Collection selected;

   public TranslationVisitor(Point translation, Collection selected) {
      this.translation = translation;
      this.selected = selected;
   }

   public void visit(DiscretePlace place) {
      place.setX(place.getX() + this.translation.x);
      place.setY(place.getY() + this.translation.y);
   }

   public void visit(DiscreteTransition transition) {
      transition.setX(transition.getX() + this.translation.x);
      transition.setY(transition.getY() + this.translation.y);
   }

   public void visit(ArcPoint arcPoint) {
      double x = (double)arcPoint.getX() + this.translation.getX();
      double y = (double)arcPoint.getY() + this.translation.getY();
      arcPoint.setPoint(new Double(x, y));
   }

   public void visit(Annotation annotation) {
      annotation.setX(annotation.getX() + (int)this.translation.getX());
      annotation.setY(annotation.getY() + (int)this.translation.getY());
   }

   public void visit(InboundArc inboundArc) {
      this.visitArc(inboundArc);
   }

   public void visit(OutboundArc outboundArc) {
      this.visitArc(outboundArc);
   }

   private void visitArc(Arc arc) {
      if (this.selected.contains(arc.getSource()) && this.selected.contains(arc.getTarget())) {
         List points = arc.getArcPoints();
         Iterator i$ = points.iterator();

         while(i$.hasNext()) {
            ArcPoint arcPoint = (ArcPoint)i$.next();
            Point2D point = arcPoint.getPoint();
            Point2D newPoint = new Double(point.getX() + this.translation.getX(), point.getY() + this.translation.getY());
            arcPoint.setPoint(newPoint);
         }
      }

   }
}
