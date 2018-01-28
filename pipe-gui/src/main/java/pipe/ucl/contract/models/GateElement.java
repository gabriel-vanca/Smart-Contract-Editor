package pipe.ucl.contract.models;

import pipe.gui.imperial.pipe.models.petrinet.AbstractConnectable;
import pipe.gui.imperial.pipe.models.petrinet.DiscreteTransition;
import pipe.ucl.contract.interfaces.GraphicalRepresentation;

import java.util.ArrayList;

public class GateElement extends ContractElement implements GraphicalRepresentation {

    private static long NextId = 1;
    private static String MainLabel = "G";
    private static String[] Labels = {"G", "GATE"};

    protected Boolean sign;
    protected EventElement eventElement;
    protected TimeSpanElement timeSpanElement;

    protected DiscreteTransition graphicObject;

    protected ArrayList<StateElement> InitialStates = new ArrayList<>();
    protected ArrayList<StateElement> FinalStates = new ArrayList<>();

    public GateElement(String name, Boolean sign, EventElement eventElement, TimeSpanElement timeSpanElement) {
        super(name);
        this.sign = sign;
        this.eventElement = eventElement;
        this.timeSpanElement = timeSpanElement;
    }

    public GateElement(String id, String name, Boolean sign, EventElement eventElement, TimeSpanElement timeSpanElement) {
        super(id, name);
        this.sign = sign;
        this.eventElement = eventElement;
        this.timeSpanElement = timeSpanElement;
    }

    public Boolean getSign() {
        return sign;
    }

    public void setSign(Boolean sign) {
        this.sign = sign;
    }

    public EventElement getEventElement() {
        return eventElement;
    }

    public void setEventElement(EventElement eventElement) {
        this.eventElement = eventElement;
    }

    public TimeSpanElement getTimeSpanElement() {
        return timeSpanElement;
    }

    public void setTimeSpanElement(TimeSpanElement timeSpanElement) {
        this.timeSpanElement = timeSpanElement;
    }

    public ArrayList<StateElement> getInitialStates() {
        return InitialStates;
    }

    public ArrayList<StateElement> getFinalStates() {
        return FinalStates;
    }

    @Override
    public String toString() {
        String string = id + " : " + name + " : " + sign;
        if(eventElement != null) {
            string += " : " + eventElement.getActor().getName() + " : " + eventElement.getAction().getName();
        }
        if(timeSpanElement != null) {
            string += " : " + timeSpanElement.toString();
        }
        return string;
    }

    @Override
    protected String getUniqueId() {
        String id = MainLabel + NextId;
        NextId++;
        return id;
    }

    @Override
    public DiscreteTransition getGraphicObject() {
        return graphicObject;
    }

    @Override
    public void setGraphicObject(AbstractConnectable graphicObject) {
        this.graphicObject = (DiscreteTransition) graphicObject;
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
