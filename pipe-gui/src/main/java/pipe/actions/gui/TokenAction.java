package pipe.actions.gui;

import pipe.controllers.PetriNetController;
import pipe.controllers.PlaceController;
import pipe.gui.imperial.pipe.models.petrinet.Connectable;
import pipe.gui.imperial.pipe.models.petrinet.DiscretePlace;
import pipe.gui.imperial.pipe.models.petrinet.Place;

import java.awt.event.MouseEvent;
import java.util.Map;

/**
 * Abstract action responsible for adding and deleting tokens to places
 */
@SuppressWarnings("serial")
public abstract class TokenAction extends CreateAction {
	

	/**
     *
     * @param name image name
     * @param tooltip tooltip message
     * @param key keyboard shortcut
     * @param modifiers keyboard accelerator
     * @param applicationModel current PIPE application model
     */
    public TokenAction(String name, String tooltip, int key, int modifiers, PipeApplicationModel applicationModel) {
        super(name, tooltip, key, modifiers, applicationModel);
    }

    /**
     * Noop action when a component has not been clicked on
     * @param event              mouse event
     * @param petriNetController controller for the petri net
     */
    @Override
    public void doAction(MouseEvent event, PetriNetController petriNetController) {
        // Do nothing unless clicked a connectable
    }

    /**
     * Performs the subclass token action on the place
     * @param connectable        item clicked
     * @param petriNetController controller for the petri net
     */
    @Override
    public void doConnectableAction(Connectable connectable, PetriNetController petriNetController) {
        //TODO: Maybe a method, connectable.containsTokens()  or visitor pattern
        if (connectable instanceof Place) {
            DiscretePlace place = (DiscretePlace) connectable;
            String token = petriNetController.getSelectedToken();
            performTokenAction(petriNetController.getPlaceController(place), token);
        }
    }

    /**
     * Subclasses should perform their relevant action on the token e.g. add/delete
     *
     * @param placeController controller for places
     * @param token token
     */
    protected abstract void performTokenAction(PlaceController placeController, String token);

    /**
     * Sets the place to contain counts.
     * <p>
     * Creates a new history edit
     * </p>
     * @param placeController controller for places
     * @param counts for the place 
     */
    protected void setTokenCounts(PlaceController placeController, Map<String, Integer> counts) {
        placeController.setTokenCounts(counts);
    }
}
