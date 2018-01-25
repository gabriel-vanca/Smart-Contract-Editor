package pipe.ucl.contract.models.TimeOperators;

import pipe.ucl.contract.interfaces.GetDateInterface;

public class RThroughout  extends TimeOperator {

    public RThroughout(GetDateInterface initialDate, GetDateInterface finalDate) {
        super(initialDate, finalDate);
    }
}
