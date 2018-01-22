package matchers.component;


import pipe.gui.imperial.pipe.models.petrinet.DiscreteTransition;

public class HasTimed implements Has<DiscreteTransition> {
    boolean timed;
    public HasTimed(boolean timed) {
        this.timed = timed;

    }

    @Override
    public boolean matches(DiscreteTransition component) {
        return component.isTimed() == timed;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
