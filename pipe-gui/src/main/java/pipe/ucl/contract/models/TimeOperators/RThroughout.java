package pipe.ucl.contract.models.TimeOperators;

import pipe.ucl.contract.interfaces.GetDiscreteTime;

public class RThroughout  extends TimeOperator {

    public final static String MainLabel = "RT";
    public final static String[] Labels = {"RT", "THROUGHOUT"};

    public RThroughout(GetDiscreteTime initialDate, GetDiscreteTime finalDate) {
        super(initialDate, finalDate);
    }

    public RThroughout(String[] parameters) {
        super(parameters);
    }

    @Override
    public String toString() {
        return MainLabel + "(" + initialDate.GetDiscreteTimeString() + ", " + finalDate.GetDiscreteTimeString() + ")";
    }
}
