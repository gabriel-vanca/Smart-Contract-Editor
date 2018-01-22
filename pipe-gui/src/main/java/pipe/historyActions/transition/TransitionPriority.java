package pipe.historyActions.transition;


import pipe.gui.imperial.pipe.models.petrinet.Transition;

import javax.swing.undo.AbstractUndoableEdit;

/**
 * Undo action for changing the transition priority
 */
public class TransitionPriority extends AbstractUndoableEdit {
    /**
     * Underlying transition model
     */
    private final Transition transition;

    /**
     * Old transition priority
     */
    private final int oldPriority;

    /**
     * New transition priority
     */
    private final int newPriority;

    /**
     * Constructor
     *
     * @param transition  underlying transition model
     * @param oldPriority old priority
     * @param newPriority new priority
     */
    public TransitionPriority(final Transition transition, final int oldPriority, final int newPriority) {

        this.transition = transition;
        this.oldPriority = oldPriority;
        this.newPriority = newPriority;
    }

    @Override
    public int hashCode() {
        int result = transition.hashCode();
        result = 31 * result + oldPriority;
        result = 31 * result + newPriority;
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final TransitionPriority priority = (TransitionPriority) o;

        if (newPriority != priority.newPriority) {
            return false;
        }
        if (oldPriority != priority.oldPriority) {
            return false;
        }
        if (!transition.equals(priority.transition)) {
            return false;
        }

        return true;
    }

    /**
     * Sets the transition to its old priority
     */
    @Override
    public void undo() {
        super.undo();
        transition.setPriority(oldPriority);
    }

    /**
     * Sets the transition to its new priority
     */
    @Override
    public void redo() {
        super.redo();
        transition.setPriority(newPriority);
    }

}
