package pipe.ucl.contract.models.TimeOperators;

import pipe.ucl.contract.interfaces.GetDiscreteTime;
import pipe.ucl.contract.models.Contract;
import pipe.ucl.contract.models.ContractElement;

import java.util.List;

public abstract class TimeOperator {

    protected GetDiscreteTime initialDate;
    protected GetDiscreteTime finalDate;

    private Contract parentCOntract = null;

    public TimeOperator(GetDiscreteTime initialDate, GetDiscreteTime finalDate) {
        this.initialDate = initialDate;
        this.finalDate = finalDate;
    }

    public TimeOperator(String[] parameters, Contract currentContract) {
        if(parameters.length < 2)
            return;

        List<ContractElement> contractElements = currentContract.getContractElementsList();
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

    public abstract String toString();

}
