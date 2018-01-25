package pipe.ucl.contract.interfaces;

import pipe.gui.imperial.pipe.models.petrinet.AbstractConnectable;

public interface GraphicalRepresentation {

    public AbstractConnectable getGraphicObject();

    public void setGraphicObject(AbstractConnectable graphicObject);
}
