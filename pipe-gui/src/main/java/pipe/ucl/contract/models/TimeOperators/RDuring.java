package pipe.ucl.contract.models.TimeOperators;

import pipe.ucl.constructor.controllers.Constructor;
import pipe.ucl.contract.interfaces.GetDiscreteTime;
import pipe.ucl.contract.models.ContractElement;
import pipe.ucl.contract.models.EventElement;

import java.util.ArrayList;

public class RDuring extends TimeOperator {

    public final static String MainLabel = "RD";
    public final static String[] Labels = {"RD", "DURING"};

    protected EventElement conditionalEvent;

    public RDuring(GetDiscreteTime initialDate, GetDiscreteTime finalDate, EventElement conditionalEvent) {
        super(initialDate, finalDate);
        this.conditionalEvent = conditionalEvent;
    }

    public RDuring(String[] parameters) {
        super(parameters);
        if(parameters.length < 3) return;
        ArrayList<ContractElement> contractElements = Constructor.MainContract.getContractElementsList();
        for (ContractElement currentContractElement : contractElements) {
            if (currentContractElement.getId().equals(parameters[2])) {
                this.conditionalEvent = (EventElement) currentContractElement;
                break;
            }
        }
    }

    public EventElement getConditionalEvent() {
        return conditionalEvent;
    }
}
