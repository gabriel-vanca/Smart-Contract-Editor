package pipe.ucl.contract.models;

import pipe.ucl.contract.interfaces.GetCalendar;

import java.util.GregorianCalendar;

public class EventElement extends ContractElement implements GetCalendar {

    protected static long NextId = 1;

    protected PartyElement actor;
    protected ActionElement action;

    protected GregorianCalendar discreteDate;

    public EventElement(String name, PartyElement actor, ActionElement action, GregorianCalendar discreteDate) {
        super(name);
        this.actor = actor;
        this.action = action;
        this.discreteDate = discreteDate;
    }

    public EventElement(String id, String name, PartyElement actor, ActionElement action, GregorianCalendar discreteDate) {
        super(id, name);
        this.actor = actor;
        this.action = action;
        this.discreteDate = discreteDate;
    }

    public EventElement(String name, PartyElement actor, ActionElement action) {
        super(name);
        this.actor = actor;
        this.action = action;
    }

    public EventElement(String id, String name, PartyElement actor, ActionElement action) {
        super(id, name);
        this.actor = actor;
        this.action = action;
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

    public void setDiscreteDate(GregorianCalendar discreteDate) {
        this.discreteDate = discreteDate;
    }

    @Override
    public GregorianCalendar GetDiscreteDate() {
        return null;
    }

    @Override
    public String toString() {
        String string = id + " : " + name + " : " + actor + " : " + action;
        if(discreteDate != null)
        {
            string += " : " + discreteDate;
        }
        return string;
    }

    @Override
    protected String getUniqueId() {
        String id = "E" + NextId;
        NextId++;
        return id;
    }
}
