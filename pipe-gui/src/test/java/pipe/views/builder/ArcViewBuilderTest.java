package pipe.views.builder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pipe.actions.gui.PipeApplicationModel;
import pipe.controllers.NormalArcViewBuilder;
import pipe.controllers.PetriNetController;
import pipe.gui.PetriNetTab;
import pipe.gui.imperial.pipe.models.petrinet.*;
import pipe.views.ArcView;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ArcViewBuilderTest {
    InboundArc arc;

    NormalArcViewBuilder builder;
    @Mock
    PetriNetTab parent;

    @Mock
    private PipeApplicationModel model;

    @Mock
    private PetriNetController mockController;

    @Before
    public void setUp() {
        DiscretePlace source = new DiscretePlace("source", "source");
        DiscreteTransition transition = new DiscreteTransition("id", "name");

        arc = new InboundNormalArc(source, transition, new HashMap<String, String>());
        arc.setId("id");
        builder = new NormalArcViewBuilder(arc, mockController);
    }

    @Test
    public void setsCorrectModel() {
        ArcView<Connectable, Connectable> view = builder.build(parent, model);
        assertEquals(arc, view.getModel());
    }

    @Test
    public void setsCorrectAttributes() {
        ArcView<Connectable, Connectable> view = builder.build(parent, model);
        assertEquals(arc.getId(), view.getId());
        assertEquals(arc.isTagged(), view.isTagged());
    }
}
