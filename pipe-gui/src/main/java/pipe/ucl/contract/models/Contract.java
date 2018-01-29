package pipe.ucl.contract.models;

import java.util.ArrayList;

public class Contract {

    private String name;

    private ArrayList<ContractElement> ContractElementsList;

    Contract(String name) {
        this.name = name;
        ContractElementsList = new ArrayList<ContractElement>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ContractElement> getContractElementsList() {
        return ContractElementsList;
    }

}
