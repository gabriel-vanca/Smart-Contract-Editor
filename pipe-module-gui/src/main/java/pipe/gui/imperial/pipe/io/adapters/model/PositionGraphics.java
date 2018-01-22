package pipe.gui.imperial.pipe.io.adapters.model;

import pipe.gui.imperial.pipe.io.adapters.model.Point;

import javax.xml.bind.annotation.XmlElement;

public class PositionGraphics {
   @XmlElement(
      name = "position"
   )
   public Point point;
}
