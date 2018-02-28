package pipe.ucl.contract.models;

public abstract class ContractElement {

    protected String id;
    protected String name;

    protected Boolean elementCorrectness;

    public ContractElement() {
        String unique = getUniqueId();
        id = name = unique;
        elementCorrectness = Boolean.TRUE;
    }

    public ContractElement(String name) {
        this.name = name;
        id = getUniqueId();
        elementCorrectness = Boolean.TRUE;
    }

    public ContractElement(String id, String name) {
        this.name = name;
        this.id = id;
        elementCorrectness = Boolean.TRUE;
    }

    public ContractElement(String[] parameters) {
        if(parameters.length < 2)
            return;
        this.id = parameters[0];
        this.name = parameters[1];
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract String toString();

    protected abstract String getUniqueId();

    public Boolean getElementCorrectness() {
        return elementCorrectness;
    }

    public abstract String getMainFullLabel();


}
