package pipe.ucl.contract.models;

public abstract class ContractElement {

    protected String id;
    protected String name;

    public ContractElement(String name) {
        this.name = name;
        id = getUniqueId();
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
}
