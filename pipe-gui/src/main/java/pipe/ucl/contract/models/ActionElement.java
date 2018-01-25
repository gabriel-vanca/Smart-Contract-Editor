package pipe.ucl.contract.models;

import pipe.ucl.contract.interfaces.ActionParameter;

import java.util.ArrayList;

public class ActionElement extends ContractElement {

    protected ArrayList<ActionParameter> parameterList;

    public ActionElement(String name) {
        super(name);
        this.parameterList = new ArrayList<ActionParameter>();
    }

    public ArrayList<ActionParameter> getParameterList() {
        return parameterList;
    }

    public void addParameter(ActionParameter parameter) {
        parameterList.add(parameter);
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    protected String getUniqueId() {
        return null;
    }
}
