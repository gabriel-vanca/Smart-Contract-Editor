package pipe.ucl.contract.models.TimeOperators;

import pipe.ucl.contract.interfaces.GetCalendar;

public class RThroughout  extends TimeOperator {

    public RThroughout(GetCalendar initialDate, GetCalendar finalDate) {
        super(initialDate, finalDate);
    }
}
