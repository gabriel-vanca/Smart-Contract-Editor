package pipe.gui.imperial.pipe.models.petrinet;

import pipe.gui.imperial.pipe.exceptions.PetriNetComponentException;
import pipe.gui.imperial.pipe.visitor.component.PetriNetComponentVisitor;

import java.util.ArrayList;

public class DiscreteFunction extends AbstractPetriNetPubSub implements PetriNetComponent {

    private String id;
    private String name;
    private ArrayList<String> parameters;

    public DiscreteFunction(String id, String name, ArrayList<String> parameters) {
        this.id = id;
        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        String value = name.substring (1,name.length ()-1) +"(";
        for(int index =0; index<parameters.size (); index++) {
            value += parameters.get (index);
            if(index + 1 < parameters.size()) {
                value+= ",";
            }
        }
        value += ")";
        return value;
    }

    @Override
    public boolean isSelectable() {
        return false;
    }

    @Override
    public boolean isDraggable() {
        return false;
    }

    @Override
    public void accept(PetriNetComponentVisitor var1) throws PetriNetComponentException {

    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<String> parameters) {
        this.parameters = parameters;
    }
}
