package pipe.gui.imperial.pipe.models.petrinet;

import pipe.gui.imperial.pipe.exceptions.PetriNetComponentException;
import pipe.gui.imperial.pipe.visitor.component.PetriNetComponentVisitor;

public class DiscreteDate extends AbstractPetriNetPubSub implements PetriNetComponent {

    private String id;
    private String name;
    private String dateValue;

    public DiscreteDate(String id) {
        this.id = id;
    }

    public DiscreteDate(String id, String name, String dateValue) {
        this.id = id;
        this.name = name;
        this.dateValue = dateValue;
    }

    public String toString() {
        return dateValue;
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

    public String getDateValue() {
        return dateValue;
    }

    public void setDateValue(String dateValue) {
        this.dateValue = dateValue;
    }
}
