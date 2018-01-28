package pipe.ucl.contract.models;

import java.util.ArrayList;

public class ActionElement extends ContractElement {

    private static long NextId = 1;
    private static String MainLabel = "A";
    private static String[] Labels = {"A", "ACTION"};

    protected ArrayList<Object> parameterList;

    public ActionElement(String name) {
        super(name);
        this.parameterList = new ArrayList<>();
    }

    public ActionElement(String id, String name) {
        super(id, name);
        this.parameterList = new ArrayList<>();
    }

    public ArrayList<Object> getParameterList() {
        return parameterList;
    }

    public void addParameter(Object parameter) {
        parameterList.add(parameter);
    }

    @Override
    public String toString() {
        String string = id + " : " + name;
        return string;
    }

    @Override
    protected String getUniqueId() {
        String id = "A" + NextId;
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
