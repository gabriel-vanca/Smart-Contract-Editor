package pipe.ucl.contract.models.TimeOperators;

import pipe.ucl.contract.interfaces.GetDiscreteTime;
import pipe.ucl.contract.models.Contract;

public class RThroughout  extends TimeOperator {

    public final static String MainLabel = "RT";
    public final static String[] Labels = {"RT", "THROUGHOUT"};

    public RThroughout(GetDiscreteTime initialDate, GetDiscreteTime finalDate) {
        super(initialDate, finalDate);
    }

    public RThroughout(String[] parameters, Contract currentContract) {
        super(parameters, currentContract);
    }

    @Override
    public String toString() {
        return MainLabel + "(" + initialDate.GetDiscreteTimeString() + ", " + finalDate.GetDiscreteTimeString() + ")";
    }
}
