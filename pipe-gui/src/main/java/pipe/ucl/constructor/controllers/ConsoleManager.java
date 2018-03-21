package pipe.ucl.constructor.controllers;

import pipe.gui.imperial.pipe.models.petrinet.PetriNet;
import pipe.ucl.contract.enums.StateType;
import pipe.ucl.contract.models.*;
import pipe.ucl.gui.ConsoleFrameManager;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Queue;

import static pipe.ucl.constructor.controllers.LineParser.*;

public class ConsoleManager {
    public static String Compute(ConsoleFrameManager consoleFrameManager) {

        Map<Object, DiscreteTimeElement> eventTimeMap =
                parseConsoleInput(consoleFrameManager.getTextFromConsoleInput(),
                consoleFrameManager.getContract().getContractElementsList());

        PetriNet currentPetriNet  = consoleFrameManager.getContract().getPetriNet();
        currentPetriNet.getPetriNetController().clearMarks();

        Constructor.getPipeApplicationBuilder().selectActionButton.doClick();

        String results = "Parsing finalised. \n";

        DiscreteTimeElement currentTimeElement = eventTimeMap.get((String)"NOW");

        if(currentTimeElement == null) {
            results += "Error: Current time now found. Computation cancelled.\n";
            return results;
        }

        GregorianCalendar currentTime = currentTimeElement.GetCalendarTime();

        if(currentTime == null) {
            results += "Error: Current time empty. Computation cancelled.\n";
            return results;
        }

        results += "Computing finalised. \n\nResults:\n";

        Queue<StateElement> stateElementQueue = new LinkedList<StateElement>();
        stateElementQueue.add(consoleFrameManager.getContract().getStartState());

        while(!stateElementQueue.isEmpty()) {

            StateElement stateElement = stateElementQueue.poll();
            StateType stateType = stateElement.getType();

            switch (stateType) {
                case START: {
                    stateElement.Mark(Color.CYAN);
                    break;
                }
                case INTERMEDIARY: {
                    stateElement.Mark(Color.CYAN);
                    break;
                }
                case PAUSE: {
                    stateElement.Mark(Color.ORANGE);
                    results += "Contract branch paused for external input at the following state: "
                            + stateElement.getId()
                            + " : "
                            + stateElement.getName()
                            + "\n";
                    continue;
                }

                case DEFAULT: {
                    stateElement.Mark(Color.RED);
                    results += "Contract default at the following state: "
                            + stateElement.getId()
                            + " : "
                            + stateElement.getName()
                            + "\n";
                    break;
                }

                case END: {
                    stateElement.Mark(Color.CYAN);
                    results += "Contract ended due to reaching the following end state: "
                            + stateElement.getId()
                            + " : "
                            + stateElement.getName()
                            + "\n";

                    stateElementQueue.clear();
                    continue;
                }
            }


            ArrayList<GateElement> destinationGates = stateElement.getDestinationGates();

            for (GateElement gate : destinationGates) {
                try {

                    DiscreteTimeElement eventDiscreteTimeElement = null;

                    TimeSpanElement gateTimeSpanElement = gate.getTimeSpanElement();
                    EventElement gateEventElement = gate.getEventElement();

                    if(gate.getSign() == Boolean.FALSE && (gateTimeSpanElement == null || gateEventElement == null)) {
                        continue;
                    }

                    if(gateTimeSpanElement != null && gateEventElement != null) {
                        GregorianCalendar beginDate = gateTimeSpanElement
                                .getTimeReference()
                                .getInitialDate()
                                .GetDiscreteTime()
                                .GetCalendarTime();
                        GregorianCalendar endDate = gateTimeSpanElement
                                .getTimeReference()
                                .getFinalDate()
                                .GetDiscreteTime()
                                .GetCalendarTime();


                        if (beginDate == null || endDate == null || gateEventElement == null) {
//                        System.out.println("Unknown timespan limits or event not existent.");
                            continue;
                        }

                        if(currentTime.compareTo(beginDate) < 0) {
                            //Begin of timespan not reached
                            continue;
                        }

                        eventDiscreteTimeElement = eventTimeMap.get(gateEventElement);

                        if (eventDiscreteTimeElement == null) {
                            // Event not accomplished
                            if(gate.getSign() == Boolean.TRUE) {

                                if(currentTime.compareTo(endDate) < 0) {
                                    // Current time is in the timespan
                                    results += "Action: "
                                            + gateEventElement.getAction().getName()
                                            + " pending to be completed by "
                                            + gateEventElement.getActor().getName()
                                            + " by "
                                            + DiscreteTimeElement.ConvertGregorialCalendarToString(endDate)
                                            + "\n";
                                }

                                continue;
                            }

                            if(currentTime.compareTo(endDate) < 0) {
                                // End of timespan not reached
                                continue;
                            }

                            //Remaining case: FALSE gate, current time passed deadline and event is not done
                        } else {

                            //Event accomplished

                            GregorianCalendar eventTime = eventDiscreteTimeElement.GetCalendarTime();

                            if (eventTime.compareTo(beginDate) < 0 || eventTime.compareTo(endDate) > 0) {
                                // Event has NOT been accomplished within the timespan but either before or after it

                                if(gate.getSign() == Boolean.TRUE) {

                                    if(currentTime.compareTo(endDate) < 0) {
                                        // Current time is in the timespan
                                        results += "Action: "
                                                + gateEventElement.getAction().getName()
                                                + " pending to be completed by "
                                                + gateEventElement.getActor().getName()
                                                + " by "
                                                + DiscreteTimeElement.ConvertGregorialCalendarToString(endDate)
                                                + "\n";
                                    }

                                    continue;
                                }

                                if(currentTime.compareTo(endDate) < 0) {
                                    // End of timespan not reached
                                    continue;
                                }

                                //Remaining case: FALSE gate, current time passed deadline and
                                // event is not done within timespan

                            } else {
                                // Event accomplished within timespan

                                if(gate.getSign() == Boolean.FALSE) {
                                    continue;
                                }

                                // Remaining case: True gate, event accomplished within timespan
                            }
                        }

                    }
                    // Event has been accomplished within the timespan
                    ArrayList<StateElement> destinationStates = gate.getFinalStates();

                    for (StateElement destinationState : destinationStates) {
                        stateElementQueue.add(destinationState);
                        destinationState.setdiscreteTime(eventDiscreteTimeElement);
                    }

                } catch (Exception err) {
                    System.out.println(err);
                }
            }
        }

        currentPetriNet.getTab().repaint();

        return results;
    }

    private static Map<Object, DiscreteTimeElement> parseConsoleInput(String inputString, List<ContractElement> contractElements) {

        Map<Object, DiscreteTimeElement> eventTimeMap = new HashMap<Object, DiscreteTimeElement>();

        if(inputString.length()<2)
            return eventTimeMap;

        inputString = NormaliseQuotes(inputString);
        inputString = RemoveWhiteSpacesAndCommentExceptInsideQuotes(inputString);

        String[] inputEvents = SplitByCommasExceptQuotesAndParentheses(inputString);

        for (String inputEvent: inputEvents) {

            if(inputEvent.length()<2)
                continue;

            inputEvent = inputEvent.substring(1, inputEvent.length()-1);

            String[] eventInfo = SplitByCommasExceptQuotesAndParentheses(inputEvent);

            if(eventInfo.length < 2) {
                System.out.println("Error in console instruction: " + inputEvent);
                continue;
            }

            Object eventElement = null;
            DiscreteTimeElement discreteTimeElement;

            if(eventInfo[0].equals("NOW")) {

                eventElement = eventInfo[0];

            }
            else {
                for (ContractElement currentContractElement : contractElements) {
                    if (currentContractElement.getId().equals(eventInfo[0])) {
                        eventElement = (EventElement) currentContractElement;
                        break;
                    }
                }
                if(eventElement == null) {
                    continue;
                }
            }

            String[] discreteTimeElementWrapper = new String[3];
            discreteTimeElementWrapper[0] = discreteTimeElementWrapper[1] = "CONSOLE";
            discreteTimeElementWrapper[2] = eventInfo[1];

            discreteTimeElement = new DiscreteTimeElement(discreteTimeElementWrapper, Constructor.getSelectedContract());

            eventTimeMap.putIfAbsent(eventElement, discreteTimeElement);

            if(eventInfo[0].equals("NOW"))
                continue;

            ((EventElement)eventElement).SetDiscreteTime(discreteTimeElement);

        }

        return eventTimeMap;
    }
}
