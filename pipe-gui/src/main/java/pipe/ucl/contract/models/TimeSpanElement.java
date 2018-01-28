package pipe.ucl.contract.models;

import pipe.ucl.contract.models.TimeOperators.TimeOperator;

public class TimeSpanElement extends ContractElement {

    private static long NextId = 1;
    private static String MainLabel = "TS";
    private static String[] Labels = {"TS", "TIME-SPAN"};

    protected TimeOperator timeReference;

    public TimeSpanElement(String name, TimeOperator timeReference) {
        super(name);
        this.timeReference = timeReference;
    }

    public TimeSpanElement(String id, String name, TimeOperator timeReference) {
        super(id, name);
        this.timeReference = timeReference;
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
    public String getMainLabel() {
        return MainLabel;
    }

    @Override
    public String[] getLabels() {
        return Labels;
    }
}
