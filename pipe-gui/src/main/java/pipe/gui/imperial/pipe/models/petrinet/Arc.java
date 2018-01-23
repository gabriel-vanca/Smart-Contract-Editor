package pipe.gui.imperial.pipe.models.petrinet;

import pipe.gui.imperial.state.State;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.List;
import java.util.Map;

public interface Arc extends PetriNetComponent {
   String SOURCE_CHANGE_MESSAGE = "source";
   String TARGET_CHANGE_MESSAGE = "target";
   String WEIGHT_CHANGE_MESSAGE = "weight";
   String DELETE_INTERMEDIATE_POINT_CHANGE_MESSAGE = "deleteIntermediatePoint";
   String NEW_INTERMEDIATE_POINT_CHANGE_MESSAGE = "newIntermediatePoint";

   Map getTokenWeights();

   Connectable getSource();

   void setSource(Connectable var1);

   Connectable getTarget();

   void setTarget(Connectable var1);

   String getName();

   boolean isTagged();

   void setTagged(boolean var1);

   String getWeightForToken(String var1);

   void setWeight(String var1, String var2);

   boolean hasFunctionalWeight();

   ArcType getType();

   void addIntermediatePoints(Iterable var1);

   void addIntermediatePoint(ArcPoint var1);

   List<ArcPoint> getArcPoints();

   void removeIntermediatePoint(ArcPoint var1);

   ArcPoint getNextPoint(ArcPoint var1);

   Double getStartPoint();

   Point2D getEndPoint();

   double getEndAngle();

   boolean canFire(PetriNet var1, State var2);

   void removeAllTokenWeights(String var1);
}
