package pipe.gui.imperial.pipe.models.petrinet;

import pipe.constants.GUIConstants;
import pipe.gui.imperial.pipe.parsers.FunctionalResults;
import pipe.gui.imperial.pipe.parsers.PetriNetWeightParser;
import pipe.gui.imperial.pipe.parsers.StateEvalVisitor;
import pipe.gui.imperial.pipe.visitor.component.PetriNetComponentVisitor;
import pipe.gui.imperial.state.State;
import pipe.ucl.contract.models.ContractElement;
import pipe.ucl.contract.models.GateElement;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class DiscreteTransition extends AbstractConnectable implements Transition {
    public static final int DEGREES_135 = 135;
    public static final int DEGREES_45 = 45;
    public static final int DEGREES_225 = 225;
    public static final int DEGREES_315 = 315;
    private int priority = 1;
    private pipe.gui.imperial.pipe.models.petrinet.Rate rate = new NormalRate ("1");
    private boolean timed = false;
    private boolean infiniteServer = false;
    private int angle = 0;
    private boolean enabled = false;
    private GateElement gateElement;

    public DiscreteTransition(String id, String name) {
        super (id, name);
    }

    public DiscreteTransition(String id) {
        super (id, id);
    }

    public DiscreteTransition(String id, String name, GateElement gateElement) {
        super (id, name);
        this.gateElement = gateElement;
    }

    public DiscreteTransition(String id, String name, pipe.gui.imperial.pipe.models.petrinet.Rate rate, int priority) {
        super (id, name);
        this.rate = rate;
        this.priority = priority;
    }

    public DiscreteTransition(String id, String name, pipe.gui.imperial.pipe.models.petrinet.Rate rate, int priority, GateElement gateElement) {
        super (id, name);
        this.rate = rate;
        this.priority = priority;
        this.gateElement = gateElement;
    }

    public DiscreteTransition(DiscreteTransition transition) {
        super (transition);
        this.infiniteServer = transition.infiniteServer;
        this.angle = transition.angle;
        this.timed = transition.timed;
        this.rate = transition.rate;
        this.priority = transition.priority;
        this.gateElement = transition.gateElement;
    }

    @Override
    public ContractElement getAttachedContractElement() {
        return gateElement;
    }

    public GateElement getGateElement() {
        return gateElement;
    }

    @Override
    public String toString() {

        if(gateElement != null) {
            return gateElement.toString();
        }

        String string = this.id;
        if (this.name != null && this.name.length () > 0)
            string += ":" + this.name;
        return string;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof DiscreteTransition)) {
            return false;
        } else if (!super.equals (o)) {
            return false;
        } else {
            DiscreteTransition that = (DiscreteTransition) o;
            if (this.infiniteServer != that.infiniteServer) {
                return false;
            } else if (this.priority != that.priority) {
                return false;
            } else if (this.timed != that.timed) {
                return false;
            } else {
                return this.rate.equals (that.rate);
            }
        }
    }

    public int hashCode() {
        int result = super.hashCode ();
        result = 31 * result + this.priority;
        result = 31 * result + this.rate.hashCode ();
        result = 31 * result + (this.timed ? 1 : 0);
        result = 31 * result + (this.infiniteServer ? 1 : 0);
        return result;
    }

    public Double getCentre() {
        return new Double ((double) (this.getX () + this.getWidth () / 2), (double) (this.getY () + this.getHeight () / 2));
    }

    public Double getArcEdgePoint(double angle) {
        int halfHeight = this.getHeight () / 2;
        int halfWidth = this.getWidth () / 2;
        double centreX = this.x + (double) halfWidth;
        double centreY = this.y + (double) halfHeight;
        Double connectionPoint = new Double (centreX, centreY);
        double rotatedAngle = angle - Math.toRadians ((double) this.angle);
        if (rotatedAngle < 0.0D) {
            rotatedAngle += 6.283185307179586D;
        }

        if (this.connectToTop (rotatedAngle)) {
            connectionPoint.y -= (double) halfHeight;
        } else if (this.connectToBottom (rotatedAngle)) {
            connectionPoint.y += (double) halfHeight;
        } else if (this.connectToRight (rotatedAngle)) {
            connectionPoint.x += (double) halfWidth;
        } else {
            connectionPoint.x -= (double) halfWidth;
        }

        return this.rotateAroundCenter (Math.toRadians ((double) this.angle), connectionPoint);
    }

    public int getHeight() {
        return GUIConstants.TRANSITION_HEIGHT;
    }

    public int getWidth() {
        return GUIConstants.TRANSITION_WIDTH;
    }

    private boolean connectToTop(double angle) {
        return angle >= Math.toRadians (45.0D) && angle < Math.toRadians (135.0D);
    }

    private boolean connectToBottom(double angle) {
        return angle < Math.toRadians (315.0D) && angle >= Math.toRadians (225.0D);
    }

    private boolean connectToRight(double angle) {
        return angle < Math.toRadians (225.0D) && angle >= Math.toRadians (135.0D);
    }

    private Double rotateAroundCenter(double angle, Double point) {
        AffineTransform tx = AffineTransform.getRotateInstance (angle, this.getCentre ().getX (), this.getCentre ().getY ());
        Point2D center = this.getCentre ();
        Double rotatedPoint = new Double ();
        tx.transform (point, rotatedPoint);
        return rotatedPoint;
    }

    public boolean isEndPoint() {
        return true;
    }

    @Override
    public String toLongString() {

        if(gateElement != null)
            return gateElement.toLongString();
        else return "";

    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        int old = this.priority;
        this.priority = priority;
        this.changeSupport.firePropertyChange ("priority", old, priority);
    }

    public pipe.gui.imperial.pipe.models.petrinet.Rate getRate() {
        return this.rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public java.lang.Double getActualRate(PetriNet petriNet, State state) {
        StateEvalVisitor stateEvalVisitor = new StateEvalVisitor (petriNet, state);
        PetriNetWeightParser parser = new PetriNetWeightParser (stateEvalVisitor, petriNet);
        FunctionalResults results = parser.evaluateExpression (this.getRateExpr ());
        if (results.hasErrors ()) {
            return -1.0D;
        } else {
            java.lang.Double rate = (java.lang.Double) results.getResult ();
            if (!this.isInfiniteServer ()) {
                return rate;
            } else {
                Map arcWeights = this.evaluateInboundArcWeights (parser, petriNet.inboundArcs (this));
                int enablingDegree = this.getEnablingDegree (state, arcWeights);
                return rate.doubleValue () * (double) enablingDegree;
            }
        }
    }

    public String getRateExpr() {
        return this.rate.getExpression ();
    }

    public boolean isInfiniteServer() {
        return this.infiniteServer;
    }

    private Map evaluateInboundArcWeights(PetriNetWeightParser parser, Collection arcs) {
        Map result = new HashMap ();
        Iterator i$ = arcs.iterator ();

        while (i$.hasNext ()) {
            pipe.gui.imperial.pipe.models.petrinet.InboundArc arc = (InboundArc) i$.next ();
            String placeId = ((Place) arc.getSource ()).getId ();
            Map arcWeights = arc.getTokenWeights ();
            Map weights = this.evaluateArcWeight (parser, arcWeights);
            result.put (placeId, weights);
        }

        return result;
    }

    private int getEnablingDegree(State state, Map arcWeights) {
        int enablingDegree = Integer.MAX_VALUE;
        Iterator i$ = arcWeights.entrySet ().iterator ();

        while (i$.hasNext ()) {
            Entry entry = (Entry) i$.next ();
            String placeId = (String) entry.getKey ();
            Map weights = (Map) entry.getValue ();
            Iterator i$1 = weights.entrySet ().iterator ();

            while (i$1.hasNext ()) {
                Entry weightEntry = (Entry) i$1.next ();
                String tokenId = (String) weightEntry.getKey ();
                java.lang.Double weight = (java.lang.Double) weightEntry.getValue ();
                int requiredTokenCount = (int) Math.floor (weight.doubleValue ());
                if (requiredTokenCount == 0) {
                    enablingDegree = 0;
                } else {
                    Map tokenCount = state.getTokens (placeId);
                    int placeTokenCount = ((Integer) tokenCount.get (tokenId)).intValue ();
                    int currentDegree = placeTokenCount / requiredTokenCount;
                    if (currentDegree < enablingDegree) {
                        enablingDegree = currentDegree;
                    }
                }
            }
        }

        return enablingDegree;
    }

    private Map evaluateArcWeight(PetriNetWeightParser parser, Map arcWeights) {
        Map result = new HashMap ();
        Iterator i$ = arcWeights.entrySet ().iterator ();

        while (i$.hasNext ()) {
            Entry entry = (Entry) i$.next ();
            String tokenId = (String) entry.getKey ();
            double arcWeight = this.getArcWeight (parser, (String) arcWeights.get (tokenId));
            result.put (tokenId, arcWeight);
        }

        return result;
    }

    private double getArcWeight(PetriNetWeightParser parser, String weight) {
        FunctionalResults result = parser.evaluateExpression (weight);
        if (result.hasErrors ()) {
            throw new RuntimeException ("Could not parse arc weight");
        } else {
            return ((java.lang.Double) result.getResult ()).doubleValue ();
        }
    }

    public void setInfiniteServer(boolean infiniteServer) {
        boolean old = this.infiniteServer;
        this.infiniteServer = infiniteServer;
        this.changeSupport.firePropertyChange ("infiniteServer", old, infiniteServer);
    }

    public int getAngle() {
        return this.angle;
    }

    public void setAngle(int angle) {
        int old = this.angle;
        this.angle = angle;
        this.changeSupport.firePropertyChange ("angle", old, angle);
    }

    public boolean isTimed() {
        return this.timed;
    }

    public void setTimed(boolean timed) {
        boolean old = this.timed;
        this.timed = timed;
        this.changeSupport.firePropertyChange ("timed", old, timed);
    }

    public boolean isSelectable() {
        return true;
    }

    public boolean isDraggable() {
        return true;
    }

    public void accept(PetriNetComponentVisitor visitor) {
        if (visitor instanceof pipe.gui.imperial.pipe.models.petrinet.TransitionVisitor) {
            ((TransitionVisitor) visitor).visit (this);
        }

        if (visitor instanceof DiscreteTransitionVisitor) {
            ((DiscreteTransitionVisitor) visitor).visit (this);
        }

    }

    public void enable() {
        this.enabled = true;
        this.changeSupport.firePropertyChange ("enabled", false, true);
    }

    public void disable() {
        this.enabled = false;
        this.changeSupport.firePropertyChange ("disabled", true, false);
    }

    public boolean isEnabled() {
        return this.enabled;
    }

}
