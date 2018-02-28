package pipe.ucl.contract.models;

import java.util.ArrayList;

public class ActionElement extends ContractElement {

    private static long NextId = 1;
    public final static String MainLabel = "A";
    public final static String[] Labels = {"A", "ACTION"};
    public final static String MainFullLabel = "ACTION";

    protected ArrayList<Object> parameterList;

    public ActionElement(String name) {
        super(name);
        this.parameterList = new ArrayList<>();
    }

    public ActionElement(String id, String name) {
        super(id, name);
        this.parameterList = new ArrayList<>();
    }

    public ActionElement(String[] parameters) {
        super(parameters);
        this.parameterList = new ArrayList<>();
        for(int index=2; index<parameters.length;index++) {
            parameterList.add(parameters[index]);
        }
        elementCorrectness = Boolean.TRUE;
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
        String id = MainLabel + NextId;
        NextId++;
        return id;
    }

    @Override
    public String getMainFullLabel() {
            return MainFullLabel;
    }


}
