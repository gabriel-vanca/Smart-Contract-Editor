package pipe.ucl.contract.models;

import pipe.controllers.PetriNetController;
import pipe.gui.imperial.pipe.models.petrinet.AbstractConnectable;
import pipe.gui.imperial.pipe.models.petrinet.DiscretePlace;
import pipe.gui.imperial.pipe.models.petrinet.DiscreteTransition;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;
import pipe.ucl.contract.interfaces.GraphicalRepresentation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static pipe.ucl.constructor.controllers.Constructor.AddArc;

public class GateElement extends ContractElement implements GraphicalRepresentation {

    private static long NextId = 1;
    public final static String MainLabel = "G";
    public final static String[] Labels = {"G", "GATE"};
    public final static String MainFullLabel = "GATE";

    DiscreteTransition discreteGate;

    protected Boolean sign;
    protected EventElement eventElement;
    protected TimeSpanElement timeSpanElement;

    protected DiscreteTransition graphicObject;

    protected ArrayList<StateElement> InitialStates = new ArrayList<>();
    protected ArrayList<StateElement> FinalStates = new ArrayList<>();

    public GateElement(String name, Boolean sign, EventElement eventElement, TimeSpanElement timeSpanElement) {
        super(name);
        this.sign = sign;
        this.eventElement = eventElement;
        this.timeSpanElement = timeSpanElement;
    }

    public GateElement(String id, String name, Boolean sign, EventElement eventElement, TimeSpanElement timeSpanElement) {
        super(id, name);
        this.sign = sign;
        this.eventElement = eventElement;
        this.timeSpanElement = timeSpanElement;
    }

    public GateElement(String[] parameters, Contract parentContract) {
        super(parameters, parentContract);

        if (parameters.length > 2) {

            this.sign = Boolean.valueOf(parameters[2]);
            elementCorrectness = Boolean.TRUE;

            if (parameters.length > 3) {
                elementCorrectness = Boolean.FALSE;
                List<ContractElement> contractElements = parentContract.getContractElementsList();
                for (ContractElement currentContractElement : contractElements) {
                    if (currentContractElement.id.equals(parameters[3])) {
                        this.eventElement = (EventElement) currentContractElement;
                        elementCorrectness = Boolean.TRUE;
                        break;
                    }
                }
                if (parameters.length > 4) {
                    elementCorrectness = Boolean.FALSE;
                    for (ContractElement currentContractElement : contractElements) {
                        if (currentContractElement.id.equals(parameters[4])) {
                            this.timeSpanElement = (TimeSpanElement) currentContractElement;
                            elementCorrectness = Boolean.TRUE;
                            break;
                        }
                    }
                }
            }
        }

        graphicObject = instantiateGraphicObject();
    }

    protected DiscreteTransition instantiateGraphicObject() {
        try{

            PetriNetController petriNetController = this.getParentContract().getPetriNet().getPetriNetController();

            String graphicalRepresentationId = petriNetController.getUniqueTransitionName ();

//            if(id == null || id == "") {
////                id = petriNetController.getUniqueTransitionName ();
////            }
            DiscreteTransition transition = new DiscreteTransition (graphicalRepresentationId, name, this);

            int randomX = ThreadLocalRandom.current ().nextInt (10, 1270);
            int randomY = ThreadLocalRandom.current ().nextInt (10, 675);
            transition.setX(randomX);
            transition.setY(randomY);
            transition.setTimed(Boolean.TRUE);

            PetriNet petriNet = parentContract.getPetriNet();
            petriNet.addTransition(transition);
            return transition;
        } catch(Exception e) {
            System.out.println ("ERROR: Could not add new gate due to following error: " + e.toString ());
        }
        return null;
    }

    public void addBeginState(StateElement stateElement) {
        InitialStates.add(stateElement);
        stateElement.addDestinationGate(this);
        DiscretePlace beginStateObject = stateElement.getGraphicObject();
        PetriNet petriNet = this.getParentContract().getPetriNet();
        AddArc(beginStateObject, this.graphicObject, petriNet);
    }

    public void addEndState(StateElement stateElement) {
        FinalStates.add(stateElement);
        stateElement.addSourceGate(this);
        DiscretePlace endStateObject = stateElement.getGraphicObject();
        PetriNet petriNet = this.getParentContract().getPetriNet();
        AddArc(this.graphicObject, endStateObject, petriNet);
    }

    public Boolean getSign() {
        return sign;
    }

    public void setSign(Boolean sign) {
        this.sign = sign;
    }

    public EventElement getEventElement() {
        return eventElement;
    }

    public void setEventElement(EventElement eventElement) {
        this.eventElement = eventElement;
    }

    public TimeSpanElement getTimeSpanElement() {
        return timeSpanElement;
    }

    public void setTimeSpanElement(TimeSpanElement timeSpanElement) {
        this.timeSpanElement = timeSpanElement;
    }

    public ArrayList<StateElement> getInitialStates() {
        return InitialStates;
    }

    public ArrayList<StateElement> getFinalStates() {
        return FinalStates;
    }

    @Override
    public String toString() {
        String string = id + " : " + name + " : " + sign;
        if(eventElement != null) {
            string += " : " + eventElement.getId();
        }
        if(timeSpanElement != null) {
            string += " : " + timeSpanElement.getId();
        }
        return string;
    }

    public String toLongString() {
        String string = id + " : " + name + " : " + sign;
        if(eventElement != null) {
            string += " : " + eventElement.getActor().getName() + " : " + eventElement.getAction().getName();
            ArrayList<Object> parametersList = eventElement.getAction().getParameterList();
            if(parametersList.size() != 0) {

                string += "(";

                for (int index = 0; index < parametersList.size(); index++) {
                    string += parametersList.get(index).toString() +", ";
                }

                string = string.substring(0, string.length() - 2);
                string += ")";

            }
        }
        if(timeSpanElement != null) {
            string += " : " + timeSpanElement.timeReference.toString();
        }
        return string;
    }

    @Override
    protected String getUniqueId() {
        String id = MainLabel + NextId;
        NextId++;
        return id;
    }

    @Override
    public DiscreteTransition getGraphicObject() {
        return graphicObject;
    }

    @Override
    public void setGraphicObject(AbstractConnectable graphicObject) {
        this.graphicObject = (DiscreteTransition) graphicObject;
    }

    @Override
    public String getMainFullLabel() {
        return MainFullLabel;
    }

}
