package pipe.ucl.contract.models;

import pipe.gui.imperial.pipe.models.petrinet.AbstractConnectable;
import pipe.gui.imperial.pipe.models.petrinet.DiscretePlace;
import pipe.ucl.contract.enums.StateType;
import pipe.ucl.contract.interfaces.GetCalendar;
import pipe.ucl.contract.interfaces.GraphicalRepresentation;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class StateElement extends ContractElement implements GraphicalRepresentation, GetCalendar {

    protected StateType type;
    private static long NextId = 1;
    private static String MainLabel = "S";
    private static String[] Labels = {"S", "STATE"};
    protected DiscretePlace graphicObject;

    protected GregorianCalendar discreteDate;

    protected ArrayList<GateElement> InitialGate = new ArrayList<>();

    protected ArrayList<GateElement> FinalGate = new ArrayList<>();

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

    public void setDiscreteDate(GregorianCalendar discreteDate) {
        this.discreteDate = discreteDate;
    }

    @Override
    public GregorianCalendar GetDiscreteDate() {
        return null;
    }

    @Override
    public String getMainLabel() {
        return MainLabel;
    }

    @Override
    public String[] getLabels() {
        return Labels;
    }
}
