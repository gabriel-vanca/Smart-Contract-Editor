package pipe.ucl.contract.models;

public class PartyElement extends ContractElement{

    protected String type;
    protected static long NextId = 1;

    PartyElement(String name) {
        super(name);
    }

    PartyElement(String name, String type) {
        super(name);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected String getUniqueId() {
        String id = "P" + NextId;
        NextId++;
        return id;
    }

    @Override
    public String toString() {
        String string = id + " : " + name;
        if(type != null && type.length() > 0){
            string+=" : " + type;
        }
        return string;
    }

}
