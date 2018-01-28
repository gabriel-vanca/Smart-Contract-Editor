package pipe.ucl.contract.models;

import pipe.ucl.contract.interfaces.GetCalendar;

import java.util.GregorianCalendar;

public class EventElement extends ContractElement implements GetCalendar {

    private static long NextId = 1;
    private static String MainLabel = "E";
    private static String[] Labels = {"E", "EVENT"};

    protected PartyElement actor;
    protected ActionElement action;

    protected GregorianCalendar discreteTime;

    public EventElement(String name, PartyElement actor, ActionElement action, GregorianCalendar discreteTime) {
        super(name);
        this.actor = actor;
        this.action = action;
        this.discreteTime = discreteTime;
    }

    public EventElement(String id, String name, PartyElement actor, ActionElement action, GregorianCalendar discreteTime) {
        super(id, name);
        this.actor = actor;
        this.action = action;
        this.discreteTime = discreteTime;
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

    public void setDiscreteTime(GregorianCalendar discreteTime) {
        this.discreteTime = discreteTime;
    }

    @Override
    public GregorianCalendar GetDiscreteDate() {
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

    @Override
    public String getMainLabel() {
        return MainLabel;
    }

    @Override
    public String[] getLabels() {
        return Labels;
    }
}
