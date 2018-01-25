package pipe.ucl.contract.models;

import pipe.ucl.contract.models.TimeOperators.TimeOperator;

public class TimeElement extends ContractElement {

    protected static long NextId = 1;

    protected TimeOperator timeReference;

    public TimeElement(String name, TimeOperator timeReference) {
        super(name);
        this.timeReference = timeReference;
    }

    public TimeElement(String id, String name, TimeOperator timeReference) {
        super(id, name);
        this.timeReference = timeReference;
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
