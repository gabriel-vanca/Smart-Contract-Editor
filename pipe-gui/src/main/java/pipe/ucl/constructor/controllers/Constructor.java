package pipe.ucl.constructor.controllers;

import pipe.actions.gui.CreateAction;
import pipe.actions.gui.PipeApplicationModel;
import pipe.actions.manager.ComponentCreatorManager;
import pipe.controllers.PetriNetController;
import pipe.controllers.application.PipeApplicationController;
import pipe.gui.imperial.pipe.layout.Layout;
import pipe.gui.imperial.pipe.models.petrinet.AbstractConnectable;
import pipe.gui.imperial.pipe.models.petrinet.DiscretePlace;
import pipe.gui.imperial.pipe.models.petrinet.DiscreteTransition;
import pipe.ucl.constructor.models.InputLine;
import pipe.ucl.contract.models.Contract;
import pipe.ucl.contract.models.ContractElement;
import pipe.views.PipeApplicationBuilder;
import pipe.views.PipeApplicationView;

import javax.swing.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Constructor {


    static PipeApplicationController applicationController;
    static PetriNetController petriNetController;

    static PipeApplicationModel applicationModel;

    public static PipeApplicationView getApplicationView() {
        return applicationView;
    }

    static PipeApplicationView applicationView;

    public static PipeApplicationBuilder getPipeApplicationBuilder() {
        return pipeApplicationBuilder;
    }

    static PipeApplicationBuilder pipeApplicationBuilder;
    static ComponentCreatorManager componentCreatorManager;

    public static Contract MainContract;

    public Constructor(PipeApplicationController applicationController, PipeApplicationModel applicationModel, PipeApplicationBuilder pipeApplicationBuilder, PipeApplicationView applicationView) {
        this.applicationController = applicationController;
        this.applicationModel = applicationModel;
        this.petriNetController = applicationController.getActivePetriNetController();
        this.pipeApplicationBuilder = pipeApplicationBuilder;
        this.applicationView = applicationView;
        this.componentCreatorManager = applicationView.getComponentCreatorManager();

//        LoadDefaultContractExample();
    }

    public static void LoadDefaultContractExample() {
        String inputFileLocation = "Contracts/input_washer_January18.txt";
        try {
            URI inputFileURI = ClassLoader.getSystemResource(inputFileLocation).toURI();
            LoadContractFile(inputFileURI);
        } catch (URISyntaxException e) {
            System.out.println("ERROR while reading input file: " + e);
        }
    }

    public static void LoadContractFile(URI inputFileURI) {

//        petriNetController.getPetriNet().ReInitialisePetriNet();

        InputFileParser inputFileParser = new InputFileParser(inputFileURI);
        inputFileParser.ParseInputFile();
        ArrayList<InputLine> ParsedReadDataLinesList = inputFileParser.getParsedReadDataLinesList();

        MainContract = new Contract(inputFileParser.getFileNameWithoutExtension());

        applicationView.getMainPaneLeft().setLeftComponent(MainContract.getContractTreeManager().getModuleTree());
        applicationView.setMainPaneRight(MainContract.getConsoleFrameManager().getQuerryPane());

        MainContract.getConsoleFrameManager().addLineToLabel("");
        MainContract.getConsoleFrameManager().addLineToLabel("Contract parsing has started");
        MainContract.getConsoleFrameManager().addLineToLabel("");

        for (String readLine :inputFileParser.getReadDataLinesList()) {
            MainContract.getConsoleFrameManager().addLineToLabel(readLine);
        }

        MainContract.getConsoleFrameManager().addLineToLabel("");
        MainContract.getConsoleFrameManager().addLineToLabel("Contract parsing is complete");
        MainContract.getConsoleFrameManager().addLineToLabel("");

        for (InputLine parsedReadDataLine : ParsedReadDataLinesList) {
            Object token = LineParser.GetToken(parsedReadDataLine);
            if (token == null || token.equals(""))
                continue;
            if (Contract.class.isInstance(token)) {
                MainContract.setContractProperties((Contract) token);
            }
            if (ContractElement.class.isInstance(token)) {
                MainContract.addContractElement((ContractElement) token);
            }
        }

        Layout();

        inputFileParser.EmptyParsedReadDataLinesList();
    }

    public static PetriNetController getPetriNetController() {
        return petriNetController;
    }

    public static void Layout() {
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

//    public static DiscretePlace AddState(String id, String name, StateType stateType) {
//        DiscretePlace place = null;
//
//        try {
//            int randomX, randomY;
//            randomX = ThreadLocalRandom.current ().nextInt (10, 1270);
//            randomY = ThreadLocalRandom.current ().nextInt (10, 675);
//
//            if(id == null || id == "")
//            {
//                id = petriNetController.getUniquePlaceName ();
//            }
//            place = new DiscretePlace (id, name, stateType);
//            place.setX (randomX);
//            place.setY (randomY);
//
//            PetriNet petriNet = petriNetController.getPetriNet ();
//            petriNet.addPlace (place);
//        } catch (Exception e) {
//            System.out.println ("ERROR: Could not add new state due to following error: " + e.toString ());
//        }
//
//        return place;
//    }

    private DiscreteTransition GetGate(String id) {
        DiscreteTransition gate = null;

        try {
             gate = petriNetController.getPetriNet ().getTransition(id);
        } catch (Exception e) {
            System.out.println ("ERROR: Could not get existing state due to following error: " + e.toString ());
        }

        return gate;
    }

    public static PipeApplicationModel getApplicationModel() {
        return applicationModel;
    }

//    public static DiscreteTransition AddGate(String id, String name, Boolean sign, String actor, String time, String action) {
//        try{
//            int randomX, randomY;
//            randomX = ThreadLocalRandom.current ().nextInt (10, 1270);
//            randomY = ThreadLocalRandom.current ().nextInt (10, 675);
//
//            if(id == null || id == "") {
//                id = petriNetController.getUniqueTransitionName ();
//            }
//            DiscreteTransition transition = new DiscreteTransition (id, name, actor, action, time, sign);
//            transition.setX(randomX);
//            transition.setY(randomY);
//            transition.setTimed(Boolean.TRUE);
//
//            PetriNet petriNet = petriNetController.getPetriNet();
//            petriNet.addTransition(transition);
//            return transition;
//        } catch(Exception e) {
//            System.out.println ("ERROR: Could not add new gate due to following error: " + e.toString ());
//        }
//        return null;
//    }

    public static PipeApplicationController getApplicationController() {
        return applicationController;
    }

    public static void AddArc(AbstractConnectable connectableSource, AbstractConnectable connectableDestination) {

        CreateAction selectedAction = componentCreatorManager.getArcAction();
        selectedAction.doConnectableAction(connectableSource, petriNetController);
        selectedAction.doConnectableAction(connectableDestination, petriNetController);

    }

}
