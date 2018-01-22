package pipe.gui.imperial.pipe.models.petrinet;

import java.awt.geom.Point2D.Double;

public interface Connectable extends PlaceablePetriNetComponent {
    String NAME_X_OFFSET_CHANGE_MESSAGE = "nameXOffset";
    String NAME_Y_OFFSET_CHANGE_MESSAGE = "nameYOffset";

    double getNameXOffset();

    void setNameXOffset(double var1);

    void setName(String var1);

    String getName();

    double getNameYOffset();

    void setNameYOffset(double var1);

    Double getCentre();

    Double getArcEdgePoint(double var1);

    boolean isEndPoint();

}
