package pipe.ucl.contract.models.TimeOperators;

import pipe.ucl.contract.interfaces.GetCalendar;

public abstract class TimeOperator {

    protected GetCalendar initialDate;
    protected GetCalendar finalDate;

    public TimeOperator(GetCalendar initialDate, GetCalendar finalDate) {
        this.initialDate = initialDate;
        this.finalDate = finalDate;
    }

    public GetCalendar getInitialDate() {
        return initialDate;
    }

    public GetCalendar getFinalDate() {
        return finalDate;
    }

}
