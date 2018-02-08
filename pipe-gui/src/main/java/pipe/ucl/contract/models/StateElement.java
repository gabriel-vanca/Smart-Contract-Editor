package pipe.ucl.contract.models;

import pipe.controllers.PetriNetController;
import pipe.gui.imperial.pipe.models.petrinet.AbstractConnectable;
import pipe.gui.imperial.pipe.models.petrinet.DiscretePlace;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;
import pipe.ucl.constructor.controllers.Constructor;
import pipe.ucl.contract.enums.StateType;
import pipe.ucl.contract.interfaces.GetDiscreteTime;
import pipe.ucl.contract.interfaces.GraphicalRepresentation;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class StateElement extends ContractElement implements GraphicalRepresentation, GetDiscreteTime {

    protected StateType type;
    private static long NextId = 1;
    public final static String MainLabel = "S";
    public final static String[] Labels = {"S", "STATE"};

    protected DiscretePlace graphicObject;

    protected DiscreteTimeElement discreteTime;

    protected ArrayList<GateElement> SourceGates = new ArrayList<>();
    protected ArrayList<GateElement> DestinationGates = new ArrayList<>();

    StateElement(String name, StateType type) {
        super(name);
        this.type = type;
        // todo it will need to generate a discrete date
    }

    public StateElement(String[] parameters) {
        super(parameters);
        if(parameters.length < 3) return;
        this.type = StateType.valueOf(parameters[2]);
        
        String discreteTimeId = this.id + "_" + DiscreteTimeElement.MainLabel;
        this.discreteTime = new DiscreteTimeElement(discreteTimeId, discreteTimeId);
        elementCorrectness = Boolean.TRUE;

        graphicObject = instantiateGraphicObject();
    }

    protected DiscretePlace instantiateGraphicObject() {
        DiscretePlace place = null;

        try {

            PetriNetController petriNetController = Constructor.getPetriNetController();

            if(id == null || id == "")
            {
                id = petriNetController.getUniquePlaceName ();
            }

            place = new DiscretePlace (id, name, this);

            int randomX = ThreadLocalRandom.current ().nextInt (10, 1270);
            int randomY = ThreadLocalRandom.current ().nextInt (10, 675);
            place.setX (randomX);
            place.setY (randomY);

            PetriNet petriNet = petriNetController.getPetriNet ();
            petriNet.addPlace (place);
        } catch (Exception e) {
            System.out.println ("ERROR: Could not add new state due to following error: " + e.toString ());
        }

        return place;
    }

    @Override
    public String toString() {
        String string = id + " : " + name + " : " + type;
        return string;
    }

    @Override
    protected String getUniqueId() {
        String id = MainLabel + NextId;
        NextId++;
        return id;
    }

    public StateType getType() {
        return type;
    }

    public void setType(StateType type) {
        this.type = type;
    }

    public ArrayList<GateElement> getSourceGates() {
        return SourceGates;
    }

    public ArrayList<GateElement> getDestinationGates() {
        return DestinationGates;
    }

    public void addSourceGate(GateElement gateElement) {
        SourceGates.add(gateElement);
    }

    public void addDestinationGate(GateElement gateElement) {
        DestinationGates.add(gateElement);
    }

    @Override
    public DiscretePlace getGraphicObject() {
        return graphicObject;
    }

    @Override
    public void setGraphicObject(AbstractConnectable graphicObject) {
        this.graphicObject = (DiscretePlace) graphicObject;
    }

    public void setdiscreteTime(DiscreteTimeElement discreteTime) {
        this.discreteTime = discreteTime;
    }

    @Override
    public DiscreteTimeElement GetDiscreteTime() {
        return discreteTime;
    }

}
