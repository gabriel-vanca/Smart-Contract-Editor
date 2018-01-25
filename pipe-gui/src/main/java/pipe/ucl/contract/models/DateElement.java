package pipe.ucl.contract.models;

import pipe.ucl.contract.interfaces.GetCalendar;

public class DateElement extends ContractElement{

    protected GetCalendar discreteDate;

    protected static long NextId = 1;

    public DateElement(String name, GetCalendar discreteDate) {
        super(name);
        this.discreteDate = discreteDate;
    }

    public DateElement(String id, String name, GetCalendar discreteDate) {
        super(id, name);
        this.discreteDate = discreteDate;
    }

    public GetCalendar getDiscreteDate() {
        return discreteDate;
    }

    public void setDiscreteDate(GetCalendar discreteDate) {
        this.discreteDate = discreteDate;
    }

    @Override
    public String toString() {
        String string = id + " : " + name + " : " + discreteDate;
        return string;
    }

    @Override
    protected String getUniqueId() {
        String id = "E" + NextId;
        NextId++;
        return id;
    }
}
