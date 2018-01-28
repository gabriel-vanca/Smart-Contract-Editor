package pipe.ucl.contract.models;

public class PartyElement extends ContractElement {

    private static long NextId = 1;
    private static String MainLabel = "P";
    private static String[] Labels = {"P", "PARTY"};

    PartyElement(String name) {
        super(name);
    }

    PartyElement(String id, String name) {
        super(id, name);
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
    public String getMainLabel() {
        return MainLabel;
    }

    @Override
    public String[] getLabels() {
        return Labels;
    }
    
}
