package pipe.historyActions;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pipe.gui.imperial.pipe.models.petrinet.DiscreteTransition;

import java.util.Observable;
import java.util.Observer;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AnimationHistoryTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private AnimationHistoryImpl history;
    private Observer observer;

    @Before
    public void setUp() {
        history = new AnimationHistoryImpl();
        observer = mock(Observer.class);
    }

    @Test
    public void addingTransitionNotifiesObserver() {
        DiscreteTransition transition = mock(DiscreteTransition.class);
        history.addObserver(observer);
        history.addHistoryItem(transition);
        verify(observer).update(any(Observable.class), any(Object.class));
    }

    @Test
    public void clearNotifiesObserver() {
        history.addObserver(observer);
        history.clear();
        verify(observer).update(any(Observable.class), any(Object.class));
    }

    @Test
    public void steppingForwardNotifiesObserver() {
        DiscreteTransition transition = mock(DiscreteTransition.class);
        history.addHistoryItem(transition);
        history.stepBackwards();
        history.addObserver(observer);
        history.stepForward();
        verify(observer).update(any(Observable.class), any(Object.class));
    }

    @Test
    public void steppingBackwardNotifiesObserver() {
        DiscreteTransition transition = mock(DiscreteTransition.class);
        history.addHistoryItem(transition);
        history.addObserver(observer);
        history.stepBackwards();
        verify(observer).update(any(Observable.class), any(Object.class));
    }

    @Test
    public void returnsLatestTransitionAdded() {
        DiscreteTransition transition = mock(DiscreteTransition.class);
        history.addHistoryItem(transition);
        assertEquals(transition, history.getCurrentTransition());
    }

    @Test
    public void incrementsCurrentPositionOnAdd() {
        DiscreteTransition transition = mock(DiscreteTransition.class);
        history.addHistoryItem(transition);
        assertEquals(0, history.getCurrentPosition());

        history.addHistoryItem(transition);
        assertEquals(1, history.getCurrentPosition());
    }

    @Test
    public void whenEmptyNoStepBackAllowed() {
        assertFalse(history.isStepBackAllowed());
    }

    @Test
    public void whenEmptyNoStepForwardAllowed() {
        assertFalse(history.isStepForwardAllowed());
    }

    @Test
    public void whenContainsOneItemCanStepBack() {
        DiscreteTransition transition = mock(DiscreteTransition.class);
        history.addHistoryItem(transition);
        assertTrue(history.isStepBackAllowed());
    }

    @Test
    public void whenAtTailCannotStepBackward() {
        DiscreteTransition transition = mock(DiscreteTransition.class);
        history.addHistoryItem(transition);
        history.stepBackwards();
        assertFalse(history.isStepBackAllowed());
    }

    @Test
    public void whenAtHeadCannotStepForward() {
        DiscreteTransition transition = mock(DiscreteTransition.class);
        history.addHistoryItem(transition);
        assertFalse(history.isStepForwardAllowed());
    }

    @Test
    public void whenNotAtHeadCanStepForward() {
        DiscreteTransition transition = mock(DiscreteTransition.class);
        history.addHistoryItem(transition);
        history.stepBackwards();
        assertTrue(history.isStepForwardAllowed());
    }

    /**
     * Adds three transitions
     * Takes a step back
     * Checks that the last transition has been cleared
     */
    @Test
    public void clearStepsForwardRemovesFutureSteps() {
        DiscreteTransition transition1 = mock(DiscreteTransition.class);
        DiscreteTransition transition2 = mock(DiscreteTransition.class);
        DiscreteTransition transition3 = mock(DiscreteTransition.class);

        history.addHistoryItem(transition1);
        history.addHistoryItem(transition2);
        history.addHistoryItem(transition3);

        history.stepBackwards();
        history.clearStepsForward();

        assertEquals(1, history.getCurrentPosition());
        assertEquals(2, history.getFiringSequence().size());
    }

    @Test
    public void getTransitionReturnsCorrectTransition() {
        DiscreteTransition transition1 = mock(DiscreteTransition.class);
        DiscreteTransition transition2 = mock(DiscreteTransition.class);

        history.addHistoryItem(transition1);
        history.addHistoryItem(transition2);

        assertEquals(transition1, history.getTransition(0));
        assertEquals(transition2, history.getTransition(1));
    }

    @Test
    public void throwsErrorIfNoTransitionsToGet() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("No transitions in history");
        history.getCurrentTransition();
    }

}
