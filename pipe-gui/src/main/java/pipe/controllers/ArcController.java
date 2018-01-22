package pipe.controllers;

import pipe.historyActions.MultipleEdit;
import pipe.historyActions.arc.AddArcPathPoint;
import pipe.historyActions.arc.ArcPathPointType;
import pipe.historyActions.arc.DeleteArcPathPoint;
import pipe.historyActions.arc.SetArcWeightAction;
import pipe.gui.imperial.pipe.models.petrinet.Connectable;
import pipe.gui.imperial.pipe.models.petrinet.Arc;
import pipe.gui.imperial.pipe.models.petrinet.ArcPoint;
import pipe.gui.imperial.pipe.parsers.FunctionalResults;
import pipe.gui.imperial.pipe.parsers.UnparsableException;

import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoableEdit;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Controller for the underlying arc model
 * @param <S> source model
 * @param <T> target model
 */
public class ArcController<S extends Connectable, T extends Connectable> extends AbstractPetriNetComponentController<Arc>
{
    /**
     * Underlying model
     */
    private final Arc arc;

    /**
     * PetriNetController in order to determine if arc expressions are valid
     */
    //TODO: I cant at the moment think of a better way to do this since the arc model
    //      does not know anything about the petri net in which it resides
    private final PetriNetController petriNetController;

    /**
     * Constructor
     * @param arc underlying model
     * @param petriNetController Petri net controller for the Petri net the arc is housed in
     * @param listener undo event listener
     */
    ArcController(Arc arc, PetriNetController petriNetController, UndoableEditListener listener) {
        super(arc, listener);
        this.arc = arc;
        this.petriNetController = petriNetController;
    }

    /**
     * Sets the weight for the current arc
     * @param token token id
     * @param expr weight for the associated token on the underlying arc
     * @throws UnparsableException if the weight could not be parsed or is not an integer
     */
    public void setWeight(String token, String expr) throws UnparsableException {
        throwExceptionIfWeightNotValid(expr);
        registerUndoableEdit(updateWeightForArc(token, expr));
    }

    /**
     *
     * @param expr weight expression
     * @throws UnparsableException if the weight could not be parsed or is not an integer
     */
    private void throwExceptionIfWeightNotValid(String expr) throws UnparsableException {
        FunctionalResults result = petriNetController.parseFunctionalExpression(expr);
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (String error : result.getErrors()) {
                errorMessage.append(error).append("\n");
            }
            throw new UnparsableException(errorMessage.toString());
        } else if (valueIsNotInteger((Double) result.getResult())) {
            throw new UnparsableException("Value is not an integer, please surround expression with floor or ceil");
        }
    }

    /**
     *
     * @param value to evaluate 
     * @return true if the value was an integer
     */
    private boolean valueIsNotInteger(double value) {
        return value % 1 != 0;
    }

    /**
     * Loop through the weights and throw an exception if its not valid
     * @param weights
     * @throws UnparsableException if the weight could not be parsed or is not an integer
     */
    private void throwExceptionIfWeightsNotValid(Map<String, String> weights) throws UnparsableException {
        for (Map.Entry<String, String> entry : weights.entrySet()) {
            throwExceptionIfWeightNotValid(entry.getValue());
        }
    }

    /**
     * Creates a historyItem for updating weight and applies it
     * @param token token id to associate the expression with
     * @param expr new weight expression for the arc
     * @return the UndoableEdit associated with this action
     */
    private UndoableEdit updateWeightForArc(String token,
                                    String expr) {
        String oldWeight = arc.getWeightForToken(token);
        arc.setWeight(token, expr);

        return new SetArcWeightAction<>(arc, token, oldWeight, expr);
    }

    /**
     * Set the new token weights
     * @param newWeights map token id -&gt;  functional expression
     * @throws UnparsableException if the weight could not be parsed or is not an integer
     */
    public void setWeights(Map<String, String> newWeights) throws UnparsableException {
        throwExceptionIfWeightsNotValid(newWeights);
        List<UndoableEdit> edits = new LinkedList<>();
        for (Map.Entry<String, String> entry : newWeights.entrySet()) {
            edits.add(updateWeightForArc(entry.getKey(), entry.getValue()));
        }
        registerUndoableEdit(new MultipleEdit(edits));
    }

    /**
     *
     * @param token to evaluate 
     * @return functional expression for the token
     */
    public String getWeightForToken(String token) {
        return arc.getWeightForToken(token);
    }

    /**
     *
     * @return target model
     */
    public Connectable getTarget() {
        return arc.getTarget();
    }

    /**
     * Toggle arc point between straight and curved
     * @param arcPoint to be toggled 
     */
    public void toggleArcPointType(ArcPoint arcPoint) {
        arcPoint.setCurved(!arcPoint.isCurved());
        registerUndoableEdit(new ArcPathPointType(arcPoint));
    }

    /**
     * Add another arc point between this point and the next
     * @param arcPoint at which to split 
     */
    public void splitArcPoint(ArcPoint arcPoint) {
        ArcPoint nextPoint = arc.getNextPoint(arcPoint);

        double x = (arcPoint.getPoint().getX() + nextPoint.getPoint().getX())/2;
        double y = (arcPoint.getPoint().getY() + nextPoint.getPoint().getY())/2;

        Point2D point = new Point2D.Double(x,y);
        ArcPoint newPoint = new ArcPoint(point, arcPoint.isCurved());
        arc.addIntermediatePoint(newPoint);
        UndoableEdit splitEdit = new AddArcPathPoint<>(arc, newPoint);
        registerUndoableEdit(splitEdit);
    }

    /**
     * Add an arc point at this location
     * @param point to add
     */
    public void addPoint(Point2D point) {
        ArcPoint newPoint = new ArcPoint(point, false);
        arc.addIntermediatePoint(newPoint);
        registerUndoableEdit(new AddArcPathPoint<>(arc, newPoint));
    }

    /**
     * Delete this arc point
     * @param point to delete
     */
    public void deletePoint(ArcPoint point) {
        arc.removeIntermediatePoint(point);
        registerUndoableEdit(new DeleteArcPathPoint<>(arc, point));
    }
}
