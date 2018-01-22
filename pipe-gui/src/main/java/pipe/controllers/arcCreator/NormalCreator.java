package pipe.controllers.arcCreator;

import pipe.controllers.PetriNetController;
import pipe.controllers.application.PipeApplicationController;
import pipe.gui.imperial.pipe.models.petrinet.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Used to create a normal arc. That is one which takes the specified number of tokens
 * from a place/produces the specified number of tokens in a place
 */
public class NormalCreator implements ArcActionCreator {

    private final PipeApplicationController controller;

    public NormalCreator(PipeApplicationController controller) {

        this.controller = controller;
    }

    private Map<String, String> getInitialTokenWeights() {

        PetriNetController netController = controller.getActivePetriNetController();
        String token = netController.getSelectedToken();

        Map<String, String> tokens = new HashMap<>();
        tokens.put(token, "1");

        return tokens;
    }

    @Override
    public InboundArc createInboundArc(DiscretePlace source, DiscreteTransition target, List<ArcPoint> arcPoints) {
        Map<String, String> weights = getInitialTokenWeights();
        InboundArc inboundArc = new InboundNormalArc(source, target, weights);
        inboundArc.addIntermediatePoints(arcPoints);
        return inboundArc;
    }

    @Override
    public OutboundArc createOutboundArc(DiscretePlace target, DiscreteTransition source, List<ArcPoint> arcPoints) {
        Map<String, String> weights = getInitialTokenWeights();
        OutboundArc outboundArc = new OutboundNormalArc(source, target, weights);
        outboundArc.addIntermediatePoints(arcPoints);
        return outboundArc;
    }

//    @Override
//    public InboundArc createInboundArc(Place source, Transition target, List<ArcPoint> arcPoints) {
//        return null;
//    }

    @Override
    public <S extends Connectable, T extends Connectable> boolean canCreate(S source, T target) {

        return !source.getClass().equals(target.getClass());
    }

}
