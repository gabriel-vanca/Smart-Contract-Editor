package pipe.ucl.contract.models;

import pipe.ucl.contract.interfaces.GetDiscreteTime;

import java.util.List;

public class EventElement extends ContractElement implements GetDiscreteTime {

    private static long NextId = 1;
    public final static String MainLabel = "E";
    public final static String[] Labels = {"E", "EVENT"};
    public final static String MainFullLabel = "EVENT";

    protected PartyElement actor;
    protected ActionElement action;
    protected DiscreteTimeElement discreteTime;

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

    public EventElement(String[] parameters, Contract parentContract) {
        super(parameters, parentContract);
        if(parameters.length < 4) return;

        List<ContractElement> contractElements = parentContract.getContractElementsList();

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

//        String discreteTimeId = this.id + "_" + DiscreteTimeElement.MainLabel;
//        this.discreteTime = new DiscreteTimeElement(discreteTimeId, discreteTimeId);
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

    public void SetDiscreteTime(DiscreteTimeElement _discreteTime) {
        discreteTime = _discreteTime;
    }

    @Override
    public DiscreteTimeElement GetDiscreteTime() {
        return discreteTime;
    }

    @Override
    public String GetDiscreteTimeString() {
        if(discreteTime != null)
            return discreteTime.toString();
        else
            return id;
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
    public String getMainFullLabel() {
        return MainFullLabel;
    }

}
