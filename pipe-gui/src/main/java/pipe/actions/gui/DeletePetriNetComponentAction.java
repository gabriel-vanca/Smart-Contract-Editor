/*
 * Created on 04-Mar-2004
 * Author is Michael Camacho
 *
 */
package pipe.actions.gui;

import pipe.controllers.PetriNetController;
import pipe.utilities.gui.GuiUtils;
import pipe.gui.imperial.pipe.exceptions.PetriNetComponentException;
import pipe.gui.imperial.pipe.models.petrinet.PetriNetComponent;

import java.awt.event.ActionEvent;


public class DeletePetriNetComponentAction extends GuiAction {

    private final PetriNetComponent component;

    private final PetriNetController petriNetController;


    public DeletePetriNetComponentAction(PetriNetComponent component, PetriNetController petriNetController) {
        super("Delete Petri net component", "Delete this component");
        this.component = component;
        this.petriNetController = petriNetController;
    }

    /**
     * Deletes component from the petri net
     *
     * @param event action event
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            registerUndoEvent(petriNetController.delete(component));
        } catch (PetriNetComponentException e) {
            GuiUtils.displayErrorMessage(null, e.getMessage());
        }
    }

}
