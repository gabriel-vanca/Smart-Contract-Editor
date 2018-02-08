package pipe.ucl.contract.models;

import pipe.ucl.constructor.controllers.Constructor;
import pipe.ucl.constructor.controllers.LineParser;

import java.util.ArrayList;

public class TransAssertion {

    public final static String MainLabel = "TA";
    public final static String[] Labels = {"TA", "TRANS-ASSERTION"};

    public TransAssertion (String[] parameters) {

        if (parameters.length != 3) {
            System.out.println("ERROR: Token < " + MainLabel + " > has an incompatible number of parameters. Line was ignored.");
            return;
        }

        String[] beginStates = LineParser.ParseLine(parameters[0]).getParameterList();
        String[] endStates = LineParser.ParseLine(parameters[1]).getParameterList();

        GateElement gateElement = null ;

        ArrayList<ContractElement> contractElements = Constructor.MainContract.getContractElementsList();
        for (ContractElement currentContractElement : contractElements) {
            if (currentContractElement.id.equals(parameters[2])) {
                gateElement = (GateElement) currentContractElement;
                break;
            }
        }

        if(gateElement == null){
            System.out.println("ERROR: Token < " + MainLabel + " > references an undefined gate. Line was ignored.");
            return;
        }

//        DiscreteTransition gate = gateElement.getGraphicObject();

        for (String beginState : beginStates) {
            for (ContractElement currentContractElement : contractElements) {
                if (currentContractElement.id.equals(beginState)) {
                    gateElement.addBeginState((StateElement) currentContractElement);
                    break;
                }
            }
        }

        for (String endState : endStates) {
            for (ContractElement currentContractElement : contractElements) {
                if (currentContractElement.id.equals(endState)) {
                    gateElement.addEndState((StateElement) currentContractElement);
                    break;
                }
            }
        }
    }
}
