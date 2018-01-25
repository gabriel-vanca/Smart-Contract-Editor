package pipe.ucl.contract.models.TimeOperators;

import pipe.ucl.contract.interfaces.GetDateInterface;
import pipe.ucl.contract.models.EventElement;

public class RDuring extends TimeOperator {

    protected EventElement conditionalEvent;

    public RDuring(GetDateInterface initialDate, GetDateInterface finalDate, EventElement conditionalEvent) {
        super(initialDate, finalDate);
        this.conditionalEvent = conditionalEvent;
    }

    public EventElement getConditionalEvent() {
        return conditionalEvent;
    }
}
