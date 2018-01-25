package pipe.ucl.contract.models.TimeOperators;

import pipe.ucl.contract.interfaces.GetDateInterface;

public abstract class TimeOperator {

    protected GetDateInterface initialDate;
    protected GetDateInterface finalDate;

    public TimeOperator(GetDateInterface initialDate, GetDateInterface finalDate) {
        this.initialDate = initialDate;
        this.finalDate = finalDate;
    }

    public GetDateInterface getInitialDate() {
        return initialDate;
    }

    public GetDateInterface getFinalDate() {
        return finalDate;
    }

}
