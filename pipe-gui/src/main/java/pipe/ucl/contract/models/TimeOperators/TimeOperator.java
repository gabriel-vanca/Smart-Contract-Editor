package pipe.ucl.contract.models.TimeOperators;

import pipe.ucl.constructor.controllers.Constructor;
import pipe.ucl.contract.interfaces.GetDiscreteTime;
import pipe.ucl.contract.models.ContractElement;

import java.util.ArrayList;

public abstract class TimeOperator {

    protected GetDiscreteTime initialDate;
    protected GetDiscreteTime finalDate;

    public TimeOperator(GetDiscreteTime initialDate, GetDiscreteTime finalDate) {
        this.initialDate = initialDate;
        this.finalDate = finalDate;
    }

    public TimeOperator(String[] parameters) {
        if(parameters.length < 2)
            return;

        ArrayList<ContractElement> contractElements = Constructor.MainContract.getContractElementsList();
        for (ContractElement currentContractElement : contractElements) {
            if (currentContractElement.getId().equals(parameters[0])) {
                this.initialDate = (GetDiscreteTime) currentContractElement;
                break;
            }
        }
        for (ContractElement currentContractElement : contractElements) {
            if (currentContractElement.getId().equals(parameters[1])) {
                this.finalDate = (GetDiscreteTime) currentContractElement;
                break;
            }
        }
    }


    public GetDiscreteTime getInitialDate() {
        return initialDate;
    }

    public GetDiscreteTime getFinalDate() {
        return finalDate;
    }

}
