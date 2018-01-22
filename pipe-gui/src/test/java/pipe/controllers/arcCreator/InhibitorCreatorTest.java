package pipe.controllers.arcCreator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pipe.controllers.PetriNetController;
import pipe.controllers.application.PipeApplicationController;
import pipe.gui.PetriNetTab;
import pipe.gui.imperial.pipe.models.petrinet.*;
import pipe.views.PipeApplicationView;

import java.util.LinkedList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InhibitorCreatorTest {

    @Mock
    PipeApplicationView mockView;

    @Mock
    PipeApplicationController mockController;

    @Mock
    PetriNetController mockPetriNetController;

    @Mock
    PetriNet mockNet;

    @Mock
    PetriNetTab mockTab;

    InhibitorCreator creator;

    @Before
    public void setUp() {
        when(mockController.getActivePetriNetController()).thenReturn(mockPetriNetController);
        when(mockPetriNetController.getPetriNet()).thenReturn(mockNet);
        when(mockView.getCurrentTab()).thenReturn(mockTab);
        creator = new InhibitorCreator();
    }

    @Test
    public void createsCorrectArc() {
        DiscretePlace source = new DiscretePlace("", "");
        DiscreteTransition transition = new DiscreteTransition("", "");
        InboundArc actual = creator.createInboundArc(source, transition,
                new LinkedList<ArcPoint>());

        InboundArc expected = new InboundInhibitorArc(source, transition);
        assertEquals(expected, actual);
    }

    @Test
    public void canCreatePlaceToTransition() {
        DiscreteTransition transition = new DiscreteTransition("T0");
        DiscretePlace place = new DiscretePlace("P0");
        assertTrue(creator.canCreate(place, transition));
    }

    @Test
    public void canCreateTransitionToPlace() {
        DiscreteTransition transition = new DiscreteTransition("T0");
        DiscretePlace place = new DiscretePlace("P0");
        assertFalse(creator.canCreate(transition, place));
    }

}
