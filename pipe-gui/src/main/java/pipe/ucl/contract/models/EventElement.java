package pipe.ucl.contract.models;

import pipe.ucl.constructor.controllers.Constructor;
import pipe.ucl.contract.interfaces.GetDiscreteTime;

import java.util.ArrayList;

public class EventElement extends ContractElement implements GetDiscreteTime {

    private static long NextId = 1;
    public final static String MainLabel = "E";
    public final static String[] Labels = {"E", "EVENT"};

    protected PartyElement actor;
    protected ActionElement action;
    protected DiscreteTimeElement discreteTime;
//    protected GregorianCalendar discreteTime;

    public EventElement(String name, PartyElement actor, ActionElement action) {
        super(name);
        this.actor = actor;
        this.action = action;
    }

    public EventElement(String id, String name, PartyElement actor, ActionElement action, DiscreteTimeElement discreteTime) {
        super(id, name);
        this.actor = actor;
        this.action = action;
        this.discreteTime = discreteTime;
    }

    public EventElement(String name, PartyElement actor, ActionElement action, DiscreteTimeElement discreteTime) {
        super(name);
        this.actor = actor;
        this.action = action;
        this.discreteTime = discreteTime;
    }

    public EventElement(String id, String name, PartyElement actor, ActionElement action) {
        super(id, name);
        this.actor = actor;
        this.action = action;
    }

    public EventElement(String[] parameters) {
        super(parameters);
        if(parameters.length < 4) return;

        ArrayList<ContractElement> contractElements = Constructor.MainContract.getContractElementsList();

        for (ContractElement currentContractElement : contractElements) {
            if (currentContractElement.id.equals(parameters[2])) {
                this.actor = (PartyElement) currentContractElement;
                break;
            }
        }

        for (ContractElement currentContractElement : contractElements) {
            if (currentContractElement.id.equals(parameters[3])) {
                this.action = (ActionElement) currentContractElement;
                break;
            }
        }

        String discreteTimeId = this.id + "_" + DiscreteTimeElement.MainLabel;
        this.discreteTime = new DiscreteTimeElement(discreteTimeId, discreteTimeId);
        elementCorrectness = Boolean.TRUE;
    }

    public void setParty(PartyElement actor) {
        this.actor = actor;
    }

    public PartyElement getActor() {
        return actor;
    }

    public ActionElement getAction() {
        return action;
    }

    public void setAction(ActionElement action) {
        this.action = action;
    }

    public void setDiscreteTime(DiscreteTimeElement discreteTime) {
        this.discreteTime = discreteTime;
    }

    @Override
    public DiscreteTimeElement GetDiscreteTime() {
        return null;
    }

    @Override
    public String toString() {
        String string = id + " : " + name + " : " + actor + " : " + action;
        if(discreteTime != null)
        {
            string += " : " + discreteTime;
        }
        return string;
    }

    @Override
    protected String getUniqueId() {
        String id = MainLabel + NextId;
        NextId++;
        return id;
    }

}
