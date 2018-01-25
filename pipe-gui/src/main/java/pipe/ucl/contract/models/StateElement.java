package pipe.ucl.contract.models;

import pipe.gui.imperial.pipe.models.petrinet.DiscretePlace;
import pipe.ucl.contract.enums.StateType;
import pipe.ucl.contract.interfaces.GraphicalRepresentation;

public class StateElement extends ContractElement implements GraphicalRepresentation {

    protected StateType type;
    protected static long NextId = 1;
    protected DiscretePlace graphicObject;

    StateElement(String name, StateType type) {
        super(name);
        this.type = type;
    }

    @Override
    public String toString() {
        String string = id + " : " + name + " : " + type;
        return string;
    }

    @Override
    protected String getUniqueId() {
        String id = "P" + NextId;
        NextId++;
        return id;
    }

    public StateType getType() {
        return type;
    }

    public void setType(StateType type) {
        this.type = type;
    }

    public DiscretePlace getGraphicObject() {
        return graphicObject;
    }

    public void setGraphicObject(DiscretePlace graphicObject) {
        this.graphicObject = graphicObject;
    }
}
