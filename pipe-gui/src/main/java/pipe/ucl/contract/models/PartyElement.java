package pipe.ucl.contract.models;

public class PartyElement extends ContractElement {

    protected static long NextId = 1;

    PartyElement(String name) {
        super(name);
    }

    PartyElement(String id, String name) {
        super(id, name);
    }

    @Override
    protected String getUniqueId() {
        String id = "P" + NextId;
        NextId++;
        return id;
    }

    @Override
    public String toString() {
        return id + " : " + name;
    }

}
