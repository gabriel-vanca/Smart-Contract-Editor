package pipe.ucl.contract.models;

public class PartyElement extends ContractElement {

    private static long NextId = 1;
    public final static String MainLabel = "P";
    public final static String[] Labels = {"P", "PARTY"};
    public final static String MainFullLabel = "PARTY";

    PartyElement(String name) {
        super(name);
    }

    PartyElement(String id, String name) {
        super(id, name);
    }

    public PartyElement(String[] parameters) {
        super(parameters);
        elementCorrectness = Boolean.TRUE;
    }

    @Override
    protected String getUniqueId() {
        String id = MainLabel + NextId;
        NextId++;
        return id;
    }

    @Override
    public String toString() {
        return id + " : " + name;
    }

    @Override
    public String getMainFullLabel() {
        return MainFullLabel;
    }
}
