package pipe.views;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pipe.actions.gui.PipeApplicationModel;
import pipe.controllers.PetriNetController;
import pipe.gui.PetriNetTab;
import pipe.gui.imperial.pipe.models.petrinet.*;
import pipe.handlers.ArcHandler;

import java.awt.geom.Point2D;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArcViewTest {

    private ArcView<Place, Transition> arcView;
    @Mock
    PetriNetTab parent;

    @Mock
    private PipeApplicationModel model;

    @Mock
    ArcHandler<? extends Connectable, ? extends Connectable> handler;

    @Mock
    private Arc mockArc;
    private Point2D.Double start = new Point2D.Double(50, 39);
    private Point2D.Double end = new Point2D.Double(100, 500);

    private DiscretePlace source;
    private DiscreteTransition target;

    @Before
    public void setUp() {
        PetriNetController mockController = mock(PetriNetController.class);
        source = new DiscretePlace("P0","P0");
        target =  new DiscreteTransition("T0", "T0");
        when(mockArc.getSource()).thenReturn(source);
        when(mockArc.getTarget()).thenReturn(target);
        when(mockArc.getStartPoint()).thenReturn(start);
        when(mockArc.getEndPoint()).thenReturn(end);
        when(mockArc.getArcPoints()).thenReturn(Arrays.asList(new ArcPoint(start, false), new ArcPoint(end, false)));
        arcView = new NormalArcView<>(mockArc, mockController, parent, handler, model);
    }

    @Test
    public void ArcViewSetsCorrectPathInConstructor() {
        ArcPath path = arcView.getArcPath();
        assertEquals(2, path.getNumPoints());
        assertEquals(start, path.getPoint(0));
        assertEquals(end, path.getPoint(1));
    }
}
