package pipe.gui.imperial.pipe.io.adapters.modelAdapter;

import pipe.gui.imperial.pipe.io.adapters.model.AdaptedArcPoint;
import pipe.gui.imperial.pipe.models.petrinet.ArcPoint;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class ArcPointAdapter extends XmlAdapter {
   public ArcPoint unmarshal(AdaptedArcPoint adaptedArcPoint) {
      Point2D point = new Double(adaptedArcPoint.getX(), adaptedArcPoint.getY());
      return new ArcPoint(point, adaptedArcPoint.isCurved());
   }

   public AdaptedArcPoint marshal(ArcPoint arcPoint) {
      AdaptedArcPoint adaptedArcPoint = new AdaptedArcPoint();
      adaptedArcPoint.setX((double)arcPoint.getX());
      adaptedArcPoint.setY((double)arcPoint.getY());
      adaptedArcPoint.setCurved(arcPoint.isCurved());
      return adaptedArcPoint;
   }

   @Override
   public Object unmarshal(Object v) throws Exception {
      return null;
   }

   @Override
   public Object marshal(Object v) throws Exception {
      return null;
   }
}
