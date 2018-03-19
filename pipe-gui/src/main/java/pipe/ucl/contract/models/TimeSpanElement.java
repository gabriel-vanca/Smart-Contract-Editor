package pipe.ucl.contract.models;

import pipe.ucl.constructor.controllers.LineParser;
import pipe.ucl.contract.models.TimeOperators.TimeOperator;

public class TimeSpanElement extends ContractElement {

    private static long NextId = 1;
    public final static String MainLabel = "TS";
    public final static String[] Labels = {"TS", "TIME-SPAN"};
    public final static String MainFullLabel = "TIME-SPAN";

    protected TimeOperator timeReference;

    public TimeSpanElement(String name, TimeOperator timeReference) {
        super(name);
        this.timeReference = timeReference;
    }

    public TimeSpanElement(String id, String name, TimeOperator timeReference) {
        super(id, name);
        this.timeReference = timeReference;
    }

    public TimeSpanElement(String[] parameters, Contract parentContract) {
        super(parameters, parentContract);
        if(parameters.length < 3) return;
        this.timeReference = (TimeOperator) LineParser.GetToken(LineParser.ParseLine(parameters[2]), parentContract);
        elementCorrectness = Boolean.TRUE;
    }

    @Override
    public String toString() {
        String string = id + " : " + name + " : " + timeReference;
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

    public TimeOperator getTimeReference() {
        return timeReference;
    }
}
