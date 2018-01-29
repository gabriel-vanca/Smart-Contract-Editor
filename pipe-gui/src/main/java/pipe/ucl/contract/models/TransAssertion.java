package pipe.ucl.contract.models;

import pipe.gui.imperial.pipe.models.petrinet.DiscretePlace;
import pipe.gui.imperial.pipe.models.petrinet.DiscreteTransition;
import pipe.ucl.constructor.controllers.Constructor;
import pipe.ucl.constructor.controllers.LineParser;

import java.util.ArrayList;

import static pipe.ucl.constructor.controllers.Constructor.AddArc;

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

        if(gateElement == null)
            return;

        DiscreteTransition gate = gateElement.getGraphicObject();

        for (String beginState : beginStates) {
            for (ContractElement currentContractElement : contractElements) {
                if (currentContractElement.id.equals(beginState)) {
                    StateElement stateElement = (StateElement) currentContractElement;
                    DiscretePlace beginStateObject = stateElement.getGraphicObject();
                    AddArc(beginStateObject, gate);
                    break;
                }
            }
        }

        for (String endState : endStates) {
            for (ContractElement currentContractElement : contractElements) {
                if (currentContractElement.id.equals(endState)) {
                    StateElement stateElement = (StateElement) currentContractElement;
                    DiscretePlace endStateObject = stateElement.getGraphicObject();
                    AddArc(gate, endStateObject);
                    break;
                }
            }
        }
    }
}
