package pipe.gui.imperial.pipe.io.adapters.modelAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public final class PointAdapter extends XmlAdapter {
   public Point2D unmarshal(PointAdapter.AdaptedPoint adaptedPoint) {
      return new Double(adaptedPoint.x, adaptedPoint.y);
   }

   public PointAdapter.AdaptedPoint marshal(Point2D point2D) {
      PointAdapter.AdaptedPoint adaptedPoint = new PointAdapter.AdaptedPoint();
      adaptedPoint.x = point2D.getX();
      adaptedPoint.y = point2D.getY();
      return adaptedPoint;
   }

   @Override
   public Object unmarshal(Object v) throws Exception {
      return null;
   }

   @Override
   public Object marshal(Object v) throws Exception {
      return null;
   }

   public static class AdaptedPoint {
      @XmlAttribute
      public double x;
      @XmlAttribute
      public double y;
   }
}
