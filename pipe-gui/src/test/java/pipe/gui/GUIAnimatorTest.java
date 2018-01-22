package pipe.gui;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pipe.controllers.GUIAnimator;
import pipe.controllers.application.PipeApplicationController;
import pipe.gui.imperial.pipe.animation.Animator;
import pipe.gui.imperial.pipe.models.petrinet.DiscreteTransition;
import pipe.historyActions.AnimationHistory;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GUIAnimatorTest {

    private GUIAnimator animator;

    @Mock
    private AnimationHistory mockHistory;

    @Mock
    private Animator mockAnimator;

    @Mock
    private PipeApplicationController applicationController;

    @Before
    public void setUp() {
        animator = new GUIAnimator(mockAnimator, mockHistory, applicationController);
    }

    @Test
    public void firingAddsToHistoryAndFires() {
        DiscreteTransition transition = mock(DiscreteTransition.class);
        animator.fireTransition(transition);

        InOrder inOrder = inOrder(mockHistory);
        inOrder.verify(mockHistory, times(1)).clearStepsForward();
        inOrder.verify(mockHistory, times(1)).addHistoryItem(transition);
        verify(mockAnimator).fireTransition(transition);
    }

    @Test
    public void ifStepForwardAnimatesTransition() {
        when(mockHistory.isStepForwardAllowed()).thenReturn(true);
        when(mockHistory.getCurrentPosition()).thenReturn(1);
        DiscreteTransition transition = mock(DiscreteTransition.class);
        when(mockHistory.getTransition(2)).thenReturn(transition);

        animator.stepForward();
        verify(mockAnimator).fireTransition(transition);
        verify(mockHistory).stepForward();
    }

    @Test
    public void ifCannotStepForwardDoesNotAnimateTransition() {
        when(mockHistory.isStepForwardAllowed()).thenReturn(false);
        when(mockHistory.getCurrentPosition()).thenReturn(1);
        DiscreteTransition transition = mock(DiscreteTransition.class);
        when(mockHistory.getTransition(2)).thenReturn(transition);

        animator.stepForward();
        verify(mockAnimator, never()).fireTransition(transition);
        verify(mockHistory, never()).stepForward();
    }

    @Test
    public void ifStepBackwardAnimatesTransition() {
        when(mockHistory.isStepBackAllowed()).thenReturn(true);
        DiscreteTransition transition = mock(DiscreteTransition.class);
        when(mockHistory.getCurrentTransition()).thenReturn(transition);

        animator.stepBack();
        verify(mockAnimator).fireTransitionBackwards(transition);
        verify(mockHistory).stepBackwards();
    }

    @Test
    public void ifCannotStepBackwardDoesNotAnimateTransition() {
        when(mockHistory.isStepBackAllowed()).thenReturn(true);
        DiscreteTransition transition = mock(DiscreteTransition.class);
        when(mockHistory.getCurrentTransition()).thenReturn(transition);

        animator.stepForward();
        verify(mockAnimator, never()).fireTransitionBackwards(transition);
        verify(mockHistory, never()).stepBackwards();
    }

    @Test
    public void doRandomFiringClearsForwardsThenAddsToHistory() {
        DiscreteTransition transition = mock(DiscreteTransition.class);
        when(mockAnimator.getRandomEnabledTransition()).thenReturn(transition);
        animator.doRandomFiring();
        InOrder inOrder = inOrder(mockHistory);
        inOrder.verify(mockHistory, times(1)).clearStepsForward();
        inOrder.verify(mockHistory, times(1)).addHistoryItem(transition);
    }

    @Test
    public void doRandomFiringFiresPetriNet() {
        DiscreteTransition transition = mock(DiscreteTransition.class);
        when(mockAnimator.getRandomEnabledTransition()).thenReturn(transition);
        animator.doRandomFiring();
        verify(mockAnimator).fireTransition(transition);
    }

    @Test
    public void restoresOriginalTokensWhenFinished() {
        animator.startAnimation();
        animator.finish();

        verify(mockAnimator).reset();
    }
}
