package pipe.ucl.contract.models;

import pipe.ucl.contract.interfaces.GetCalendar;

public class DateElement extends ContractElement{

    protected GetCalendar discreteDate;

    public DateElement(String name, GetCalendar discreteDate) {
        super(name);
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
        return null;
    }

    @Override
    protected String getUniqueId() {
        return null;
    }
}
