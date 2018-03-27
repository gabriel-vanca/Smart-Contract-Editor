package pipe.ucl.constructor.controllers;

import pipe.actions.gui.CreateAction;
import pipe.actions.gui.PipeApplicationModel;
import pipe.actions.manager.ComponentCreatorManager;
import pipe.constants.GUIConstants;
import pipe.controllers.PetriNetController;
import pipe.controllers.application.PipeApplicationController;
import pipe.gui.imperial.pipe.layout.Layout;
import pipe.gui.imperial.pipe.models.petrinet.*;
import pipe.gui.imperial.pipe.parsers.UnparsableException;
import pipe.ucl.constructor.models.InputLine;
import pipe.ucl.contract.models.Contract;
import pipe.ucl.contract.models.ContractElement;
import pipe.views.PipeApplicationBuilder;
import pipe.views.PipeApplicationView;
import pipe.views.TextLabel;

import javax.swing.*;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Constructor {


    static PipeApplicationController applicationController;
//    static PetriNetController petriNetController;

    static PipeApplicationModel applicationModel;

    static PipeApplicationView applicationView;

    static PipeApplicationBuilder pipeApplicationBuilder;
    static ComponentCreatorManager componentCreatorManager;

    public static ArrayList<TextLabel> TextLabels = new ArrayList<>();

    public Constructor(PipeApplicationController applicationController, PipeApplicationModel applicationModel, PipeApplicationBuilder pipeApplicationBuilder, PipeApplicationView applicationView) {
        this.applicationController = applicationController;
        this.applicationModel = applicationModel;
//        this.petriNetController = applicationController.getActivePetriNetController();
        this.pipeApplicationBuilder = pipeApplicationBuilder;
        this.applicationView = applicationView;
        this.componentCreatorManager = applicationView.getComponentCreatorManager();

//        LoadDefaultContractExample();
    }

    public static PipeApplicationView getApplicationView() {
        return applicationView;
    }

    public static PipeApplicationBuilder getPipeApplicationBuilder() {
        return pipeApplicationBuilder;
    }

    public static void LoadDefaultContractExample() {
        String inputFileLocation = "Contracts/input_washer_January18.txt";

        try {
            URI inputFileURI = ClassLoader.getSystemResource(inputFileLocation).toURI();
            File file = new File(inputFileURI);
            if (file.exists() &&
                    file.isFile() &&
                    file.canRead()) {

                PetriNetController currentPetriNetController = applicationController.getActivePetriNetController();
                PetriNet petriNet = currentPetriNetController.getPetriNet();
                if (!petriNet.isEmpty()) {
                    petriNet = applicationController.createNewTabFromFile(file);
                }

                LoadContractFile(inputFileURI, petriNet);
            }
        } catch (URISyntaxException e) {
            System.out.println("ERROR while reading input file: " + e);
        } catch (UnparsableException e) {
            System.out.println("ERROR while reading input file: " + e);
        }
    }

    public static void UpdateApplicationViewPanes(PetriNetController petriNetController) {
        if(petriNetController == null)
            return;
        if(petriNetController.getPetriNet() == null)
            return;
        try{
            Contract currentContract = petriNetController.getPetriNet().getContract();
            UpdateApplicationViewPanes(currentContract);
        }
        catch (Exception err) {
            System.out.print(err);
        }
    }

    private static void UpdateApplicationViewPanes(Contract currentContract) {

        if(applicationView == null)
            return;

        try {
            if(currentContract == null) {

                applicationView.getMainPaneLeft().setLeftComponent(null);
                applicationView.setMainPaneRight(null);

            } else {

                applicationView.getMainPaneLeft().setLeftComponent(currentContract.getContractTreeManager().getModuleTree());
                applicationView.setMainPaneRight(currentContract.getConsoleFrameManager().getQuerryPane());

                UpdateApplicationViewPaneSizes();

            }
        } catch (Exception err) {
            System.out.print(err);
        }
    }

    private static void UpdateApplicationViewPaneSizes() {
        applicationView.getMainPane().setDividerLocation(0.8);
        applicationView.getMainPaneLeft().setDividerLocation(0.14);
        applicationView.getMainPaneRight().setDividerLocation(0.70);
        ((JSplitPane) applicationView.getMainPaneRight().getBottomComponent()).setDividerLocation(applicationView.getMainPaneRight().getBottomComponent().getSize().height - 40);

    }

    public static void LoadContractFile(URI inputFileURI, PetriNet petriNet) {

//        petriNetController.getPetriNet().ReInitialisePetriNet();

//        PetriNetController petriNetController = applicationController.netControllers.get(petriNet.);

        InputFileParser inputFileParser = new InputFileParser(inputFileURI);
        inputFileParser.ParseInputFile();
        ArrayList<InputLine> ParsedReadDataLinesList = inputFileParser.getParsedReadDataLinesList();

        Contract currentContract = new Contract(inputFileParser.getFileNameWithoutExtension(), petriNet);

        UpdateApplicationViewPanes(currentContract);

        currentContract.getConsoleFrameManager().addLineToLabel("");
        currentContract.getConsoleFrameManager().addLineToLabel("Contract parsing has started");
        currentContract.getConsoleFrameManager().addLineToLabel("");

        for (String readLine :inputFileParser.getReadDataLinesList()) {
            currentContract.getConsoleFrameManager().addLineToLabel(readLine);
        }

        currentContract.getConsoleFrameManager().addLineToLabel("");
        currentContract.getConsoleFrameManager().addLineToLabel("Contract parsing is complete");
        currentContract.getConsoleFrameManager().addLineToLabel("");

        for (InputLine parsedReadDataLine : ParsedReadDataLinesList) {
            Object token = LineParser.GetToken(parsedReadDataLine, currentContract);
            if (token == null || token.equals(""))
                continue;
            if (Contract.class.isInstance(token)) {
                currentContract.setContractProperties((Contract) token);
            }
            if (ContractElement.class.isInstance(token)) {
                currentContract.addContractElement((ContractElement) token);
            }
        }

        Layout(petriNet);

        inputFileParser.EmptyParsedReadDataLinesList();
    }

    public static void Layout(PetriNet petriNet) {
        Layout.layoutHierarchical (petriNet, 40,
                50,50, 100 + GUIConstants.LABEL_FONT_SIZE.getValue()*25, SwingConstants.NORTH);

//        Layout.layoutOrganic(petriNet, 100, 80);

    }


    private DiscretePlace GetState(String id, PetriNet petriNet) {
        DiscretePlace place = null;

        try {
            place = petriNet.getPlace (id);
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

    private DiscreteTransition GetGate(String id, PetriNet petriNet) {
        DiscreteTransition gate = null;

        try {
             gate = petriNet.getTransition(id);
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

    public static void AddArc(AbstractConnectable connectableSource, AbstractConnectable connectableDestination, PetriNet petriNet) {

        PetriNetController petriNetController = petriNet.getPetriNetController();

        CreateAction selectedAction = componentCreatorManager.getArcAction();
        selectedAction.doConnectableAction(connectableSource, petriNetController);
        selectedAction.doConnectableAction(connectableDestination, petriNetController);

    }

}
