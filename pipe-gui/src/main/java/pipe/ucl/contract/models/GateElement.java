package pipe.ucl.contract.models;

import pipe.gui.imperial.pipe.models.petrinet.AbstractConnectable;
import pipe.gui.imperial.pipe.models.petrinet.DiscreteTransition;
import pipe.ucl.contract.interfaces.GraphicalRepresentation;

public class GateElement extends ContractElement implements GraphicalRepresentation {

    protected Boolean sign;
    protected EventElement eventElement;
    protected TimeElement timeElement;

    protected DiscreteTransition graphicObject;

    public GateElement(String name, Boolean sign, EventElement eventElement, TimeElement timeElement) {
        super(name);
        this.sign = sign;
        this.eventElement = eventElement;
        this.timeElement = timeElement;
    }

    public GateElement(String id, String name, Boolean sign, EventElement eventElement, TimeElement timeElement) {
        super(id, name);
        this.sign = sign;
        this.eventElement = eventElement;
        this.timeElement = timeElement;
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

    public TimeElement getTimeElement() {
        return timeElement;
    }

    public void setTimeElement(TimeElement timeElement) {
        this.timeElement = timeElement;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    protected String getUniqueId() {
        return null;
    }

    @Override
    public DiscreteTransition getGraphicObject() {
        return graphicObject;
    }

    @Override
    public void setGraphicObject(AbstractConnectable graphicObject) {
        this.graphicObject = (DiscreteTransition) graphicObject;
    }
}
