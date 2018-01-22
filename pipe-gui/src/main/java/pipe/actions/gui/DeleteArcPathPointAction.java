package pipe.actions.gui;

import pipe.controllers.ArcController;
import pipe.gui.imperial.pipe.models.petrinet.ArcPoint;

import javax.swing.*;

import java.awt.event.ActionEvent;

/**
 * Action used to delete points on an arc path
 */
@SuppressWarnings("serial")
public class DeleteArcPathPointAction extends AbstractAction {
    /**
     * Point to delete
     */
    private final ArcPoint component;

    /**
     * Controller of the arc the point belongs to
     */
    private final ArcController<?, ?> arcController;

    /**
     *
     * @param component Point to delete
     * @param arcController controller of the arc the point belongs to
     */
    public DeleteArcPathPointAction(ArcPoint component, ArcController<?, ?> arcController) {
        this.component = component;
        this.arcController = arcController;
    }

    /**
     * Deletes the point from the arc
     * @param actionEvent event 
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        arcController.deletePoint(component);
    }
}
