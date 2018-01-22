package pipe.gui.imperial.pipe.io.adapters.utils;

import pipe.gui.imperial.pipe.io.adapters.model.AdaptedConnectable;
import pipe.gui.imperial.pipe.io.adapters.model.NameDetails;
import pipe.gui.imperial.pipe.io.adapters.model.OffsetGraphics;
import pipe.gui.imperial.pipe.io.adapters.model.Point;
import pipe.gui.imperial.pipe.io.adapters.model.PositionGraphics;
import pipe.gui.imperial.pipe.models.petrinet.Connectable;

public final class ConnectableUtils {
   public static void setAdaptedName(Connectable connectable, AdaptedConnectable adaptedConnectable) {
      NameDetails details = new NameDetails();
      details.setName(connectable.getName());
      OffsetGraphics graphics = new OffsetGraphics();
      graphics.point = new Point();
      graphics.point.setX(connectable.getNameXOffset());
      graphics.point.setY(connectable.getNameYOffset());
      details.setGraphics(graphics);
      adaptedConnectable.setNameDetails(details);
   }

   public static void setPosition(Connectable connectable, AdaptedConnectable adaptedConnectable) {
      PositionGraphics positionGraphics = new PositionGraphics();
      positionGraphics.point = new Point();
      positionGraphics.point.setX((double)connectable.getX());
      positionGraphics.point.setY((double)connectable.getY());
      adaptedConnectable.setGraphics(positionGraphics);
   }

   public static void setConntactableNameOffset(Connectable connectable, AdaptedConnectable adaptedConnectable) {
      NameDetails nameDetails = adaptedConnectable.getName();
      OffsetGraphics offsetGraphics = nameDetails.getGraphics();
      if (offsetGraphics.point != null) {
         connectable.setNameXOffset(offsetGraphics.point.getX());
         connectable.setNameYOffset(offsetGraphics.point.getY());
      }

   }

   public static void setConnectablePosition(Connectable connectable, AdaptedConnectable adaptedConnectable) {
      connectable.setX((int)adaptedConnectable.getGraphics().point.getX());
      connectable.setY((int)adaptedConnectable.getGraphics().point.getY());
   }
}
