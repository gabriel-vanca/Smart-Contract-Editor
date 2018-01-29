package pipe.ucl.contract.models;

import pipe.gui.imperial.pipe.models.petrinet.AbstractConnectable;
import pipe.gui.imperial.pipe.models.petrinet.DiscretePlace;
import pipe.ucl.constructor.controllers.Constructor;
import pipe.ucl.contract.enums.StateType;
import pipe.ucl.contract.interfaces.GetDiscreteTime;
import pipe.ucl.contract.interfaces.GraphicalRepresentation;

import java.util.ArrayList;

public class StateElement extends ContractElement implements GraphicalRepresentation, GetDiscreteTime {

    protected StateType type;
    private static long NextId = 1;
    public final static String MainLabel = "S";
    public final static String[] Labels = {"S", "STATE"};

    protected DiscretePlace graphicObject;

    protected DiscreteTimeElement discreteTime;

    protected ArrayList<GateElement> InitialGate = new ArrayList<>();

    protected ArrayList<GateElement> FinalGate = new ArrayList<>();


    StateElement(String name, StateType type) {
        super(name);
        this.type = type;
        // todo it will need to generate a discrete date
    }

    public StateElement(String[] parameters) {
        super(parameters);
        if(parameters.length < 3) return;
        this.type = StateType.valueOf(parameters[2]);
        
        String discreteTimeId = this.id + "_" + DiscreteTimeElement.MainLabel;
        this.discreteTime = new DiscreteTimeElement(discreteTimeId, discreteTimeId);
        elementCorrectness = Boolean.TRUE;

        graphicObject = Constructor.AddState (id, name,  type);

    }

    @Override
    public String toString() {
        String string = id + " : " + name + " : " + type;
        return string;
    }

    @Override
    protected String getUniqueId() {
        String id = MainLabel + NextId;
        NextId++;
        return id;
    }

    public StateType getType() {
        return type;
    }

    public void setType(StateType type) {
        this.type = type;
    }

    public ArrayList<GateElement> getInitialGate() {
        return InitialGate;
    }

    public ArrayList<GateElement> getFinalGate() {
        return FinalGate;
    }

    @Override
    public DiscretePlace getGraphicObject() {
        return graphicObject;
    }

    @Override
    public void setGraphicObject(AbstractConnectable graphicObject) {
        this.graphicObject = (DiscretePlace) graphicObject;
    }

    public void setdiscreteTime(DiscreteTimeElement discreteTime) {
        this.discreteTime = discreteTime;
    }

    @Override
    public DiscreteTimeElement GetDiscreteTime() {
        return discreteTime;
    }

}
