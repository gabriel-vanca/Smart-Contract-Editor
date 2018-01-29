package pipe.ucl.constructor.controllers;

import pipe.actions.gui.CreateAction;
import pipe.actions.gui.PipeApplicationModel;
import pipe.actions.manager.ComponentCreatorManager;
import pipe.controllers.PetriNetController;
import pipe.controllers.application.PipeApplicationController;
import pipe.gui.imperial.pipe.layout.Layout;
import pipe.gui.imperial.pipe.models.petrinet.*;
import pipe.ucl.constructor.models.InputLine;
import pipe.ucl.contract.enums.StateType;
import pipe.ucl.contract.models.Contract;
import pipe.ucl.contract.models.ContractElement;
import pipe.views.PipeApplicationBuilder;
import pipe.views.PipeApplicationView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Constructor {

    PipeApplicationController applicationController;
    static PetriNetController petriNetController;
    PipeApplicationModel applicationModel;
    PipeApplicationView applicationView;
    PipeApplicationBuilder pipeApplicationBuilder;
    static ComponentCreatorManager componentCreatorManager;

    public static Contract MainContract;

    public Constructor(PipeApplicationController applicationController, PipeApplicationModel applicationModel, PipeApplicationBuilder pipeApplicationBuilder, PipeApplicationView applicationView) {
        this.applicationController = applicationController;
        this.applicationModel = applicationModel;
        this.petriNetController = applicationController.getActivePetriNetController ();
        this.pipeApplicationBuilder = pipeApplicationBuilder;
        this.applicationView = applicationView;
        this.componentCreatorManager = applicationView.getComponentCreatorManager ();

        InputFileParser inputFileParser = new InputFileParser();
        inputFileParser.ParseInputFile();
        ArrayList<InputLine>  ParsedReadDataLinesList = inputFileParser.getParsedReadDataLinesList();

        MainContract = new Contract("Test Contract");

        for(InputLine parsedReadDataLine : ParsedReadDataLinesList) {
            Object token =  LineParser.GetToken(parsedReadDataLine);
            if(token == null || token.equals(""))
                continue;
            if(ContractElement.class.isInstance(token))
                MainContract.getContractElementsList().add((ContractElement)token);
        }

        Layout ();

        inputFileParser.EmptyParsedReadDataLinesList ();


//        for(InputLine parsedReadDataLine : ParsedReadDataLinesList) {
//            String type = parsedReadDataLine.getType ();
//            String[] parameterList = parsedReadDataLine.getParameterList ();
//
//            switch (type) {
//                case "STATE": case "S": {
//                    if(parameterList.length != 3) {
//                        System.out.println ("ERROR: Token < " + type + " > has an incompatible number of parameters. Line was ignored.");
//                        continue;
//                    }
//                    String id = parameterList[0];
//                    String name = parameterList[1];
//                    StateType typeOfState = StateType.valueOf(parameterList[2].toUpperCase ());
//                    DiscretePlace state = AddState (id, name,  typeOfState);
//                    break;
//                }
//
//                case "GATE": case "G": {
//                    if(parameterList.length != 6) {
//                        System.out.println ("ERROR: Token < " + type + " > has an incompatible number of parameters. Line was ignored.");
//                        continue;
//                    }
//                    String id = parameterList[0];
//                    String name = parameterList[1];
//                    Boolean sign = Boolean.valueOf (parameterList[2]);
//                    String actor = parameterList[3];
//
//                    String timeString = parameterList[4];
//                    String time = "";
//                    if(timeString != null && timeString.length () > 0) {
//                        int timeStringIndexBegin = timeString.indexOf ("(");
//                        int timeStringIndexEnd = timeString.lastIndexOf (")");
//
//                        if(timeStringIndexBegin == -1 || timeStringIndexEnd == -1) {
//                            System.out.println ("ERROR: Time in incorrect format inside gate. Line was ignored.");
//                            continue;
//                        }
//
//                        String[] timeStringArray = timeString.substring (timeStringIndexBegin + 1, timeStringIndexEnd).split (Pattern.quote (","));
//                        time = timeString.substring (0, timeString.indexOf ("(")) + "(";
//                        Boolean error = Boolean.FALSE;
//                        for (int index = 0; index < timeStringArray.length; index++) {
//                            DiscreteDate discreteDate = GetDate (timeStringArray[index]);
//                            if (discreteDate == null) {
//                                System.out.println ("ERROR: Date < " + timeStringArray[index] + " > used in a gate was not found. Line was ignored.");
//                                error = Boolean.TRUE;
//                                break;
//                            }
//                            time += discreteDate.toString ();
//                            if (index + 1 < timeStringArray.length)
//                                time += ",";
//                        }
//                        time += ")";
//
//                        if(error == Boolean.TRUE)
//                            continue;
//
//                    }
//
//                    DiscreteFunction action = GetFunction (parameterList[5]);
//                    DiscreteTransition gate = AddGate (id, name, sign, actor, time, action);
//                    break;
//                }
//
//                case "DATE": case "D": {
//                    if(parameterList.length != 3) {
//                        System.out.println ("ERROR: Token < " + type + " > has an incompatible number of parameters. Line was ignored.");
//                        continue;
//                    }
//                    String id = parameterList[0];
//                    String name = parameterList[1];
//                    String dateValue = parameterList[2];
//                    DiscreteDate date = AddDate (id, name, dateValue);
//                    break;
//                }
//
//                case "FUNCTION": case "F": {
//                    if(parameterList.length < 2) {
//                        System.out.println ("ERROR: Token < " + type + " > has an incompatible number of parameters. Line was ignored.");
//                        continue;
//                    }
//                    String id = parameterList[0];
//                    String name = parameterList[1];
//                    ArrayList<String> functionParameters = new ArrayList<> (Arrays.asList (parameterList));
//                    functionParameters.remove (0);
//                    functionParameters.remove (0);
//                    DiscreteFunction function = AddFunction (id, name, functionParameters);
//                    break;
//                }
//
//                case "TRANS-ASSERTION": case "TA": case "T-A": {
//                    if(parameterList.length != 3) {
//                        System.out.println ("ERROR: Token < " + type + " > has an incompatible number of parameters. Line was ignored.");
//                        continue;
//                    }
//                    String[] beginStates = parameterList[0].split (Pattern.quote ("AND"));
//                    String[] endStates = parameterList[1].split (Pattern.quote ("CONCURRENT"));
//                    DiscreteTransition gate = GetGate(parameterList[2]);
//
//                    for (String beginState: beginStates) {
//                        DiscretePlace beginStateObject = GetState (beginState);
//                        AddArc(beginStateObject, gate);
//                    }
//
//                    for (String endState: endStates) {
//                        DiscretePlace endStateObject = GetState (endState);
//                        AddArc(gate, endStateObject);
//                    }
//
//                    break;
//                }
//
//                default: {
//                    System.out.println ("ERROR: Token < " + type + " > is not recognised. Line was ignored.");
//                    break;
//                }
//
//            }
//        }
//
//        Layout ();
//
//        inputFileParser.EmptyParsedReadDataLinesList ();
    }

    private void Layout() {
        Layout.layoutHierarchical (petriNetController.getPetriNet (), 40,
                50,50, 350, SwingConstants.NORTH);
//        componentCreatorManager..changed(petriNet);
    }

    private DiscretePlace GetState(String id) {
        DiscretePlace place = null;

        try {
            place = petriNetController.getPetriNet ().getPlace (id);
        } catch (Exception e) {
            System.out.println ("ERROR: Could not get existing state due to following error: " + e.toString ());
        }

        return place;
    }

    public static DiscretePlace AddState(String id, String name, StateType stateType) {
        DiscretePlace place = null;

        try {
            int randomX, randomY;
            randomX = ThreadLocalRandom.current ().nextInt (10, 1270);
            randomY = ThreadLocalRandom.current ().nextInt (10, 675);

            if(id == null || id == "")
            {
                id = petriNetController.getUniquePlaceName ();
            }
            place = new DiscretePlace (id, name, stateType);
            place.setX (randomX);
            place.setY (randomY);

            PetriNet petriNet = petriNetController.getPetriNet ();
            petriNet.addPlace (place);
        } catch (Exception e) {
            System.out.println ("ERROR: Could not add new state due to following error: " + e.toString ());
        }

        return place;
    }

    private DiscreteTransition GetGate(String id) {
        DiscreteTransition gate = null;

        try {
             gate = petriNetController.getPetriNet ().getTransition(id);
        } catch (Exception e) {
            System.out.println ("ERROR: Could not get existing state due to following error: " + e.toString ());
        }

        return gate;
    }

    public static DiscreteTransition AddGate(String id, String name, Boolean sign, String actor, String time, String action) {
        try{
            int randomX, randomY;
            randomX = ThreadLocalRandom.current ().nextInt (10, 1270);
            randomY = ThreadLocalRandom.current ().nextInt (10, 675);

            if(id == null || id == "") {
                id = petriNetController.getUniqueTransitionName ();
            }
            DiscreteTransition transition = new DiscreteTransition (id, name, actor, action, time, sign);
            transition.setX(randomX);
            transition.setY(randomY);
            transition.setTimed(Boolean.TRUE);

            PetriNet petriNet = petriNetController.getPetriNet();
            petriNet.addTransition(transition);
            return transition;
        } catch(Exception e) {
            System.out.println ("ERROR: Could not add new gate due to following error: " + e.toString ());
        }
        return null;
    }

    public static void AddArc(AbstractConnectable connectableSource, AbstractConnectable connectableDestination) {

        CreateAction selectedAction = componentCreatorManager.getArcAction();
        selectedAction.doConnectableAction(connectableSource, petriNetController);
        selectedAction.doConnectableAction(connectableDestination, petriNetController);

    }

    private DiscreteDate AddDate(String id, String name, String dateValue) {
        DiscreteDate date = null;

        try {
            date = new DiscreteDate (id, name, dateValue);
            PetriNet petriNet = petriNetController.getPetriNet ();
            petriNet.addDate (date);
        } catch (Exception e) {
            System.out.println ("ERROR: Could not add new date due to following error: " + e.toString ());
        }

        return date;
    }

    private DiscreteDate GetDate(String id) {
        DiscreteDate date = null;

        try {
            date = petriNetController.getPetriNet ().getDate (id);
        } catch (Exception e) {
            System.out.println ("ERROR: Could not get existing date due to following error: " + e.toString ());
        }

        return date;
    }

    private DiscreteFunction AddFunction(String id, String name, ArrayList<String> parameters) {
        DiscreteFunction function = null;

        try {
            function = new DiscreteFunction (id, name, parameters);
            PetriNet petriNet = petriNetController.getPetriNet ();
            petriNet.addFunction (function);
        } catch (Exception e) {
            System.out.println ("ERROR: Could not add new function due to following error: " + e.toString ());
        }

        return function;
    }

    private DiscreteFunction GetFunction(String id) {
        DiscreteFunction function = null;

        try {
            function = petriNetController.getPetriNet ().getFunction (id);
        } catch (Exception e) {
            System.out.println ("ERROR: Could not get existing function due to following error: " + e.toString ());
        }

        return function;
    }

}
