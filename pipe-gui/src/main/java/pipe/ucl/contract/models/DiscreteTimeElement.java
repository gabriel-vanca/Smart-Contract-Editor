package pipe.ucl.contract.models;

import pipe.ucl.contract.interfaces.GetCalendar;

import java.util.GregorianCalendar;

public class DiscreteTimeElement extends ContractElement implements GetCalendar {

    private static long NextId = 1;
    private static String MainLabel = "DT";
    private static String[] Labels = {"DT", "DISCRETE-TIME"};

    protected GregorianCalendar discreteTime;

    public DiscreteTimeElement(String name) {
        super(name);
    }

    public DiscreteTimeElement(String id, String name) {
        super(id, name);
    }

    @Override
    public GregorianCalendar GetDiscreteDate() {
        return null;
    }

    public void setDiscreteDate(GregorianCalendar discreteTime) {
        this.discreteTime = discreteTime;
    }

    @Override
    public String toString() {
        String string = id + " : " + name + " : " + discreteTime;
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
