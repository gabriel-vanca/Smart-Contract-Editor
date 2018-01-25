package pipe.ucl.contract.models;

import pipe.ucl.contract.interfaces.GetDateInterface;

public class DateElement extends ContractElement{

    protected GetDateInterface discreteDate;

    public DateElement(String name, GetDateInterface discreteDate) {
        super(name);
        this.discreteDate = discreteDate;
    }

    public GetDateInterface getDiscreteDate() {
        return discreteDate;
    }

    public void setDiscreteDate(GetDateInterface discreteDate) {
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
