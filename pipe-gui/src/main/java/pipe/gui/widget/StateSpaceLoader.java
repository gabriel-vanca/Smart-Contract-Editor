package pipe.gui.widget;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import pipe.gui.imperial.io.*;
import pipe.gui.imperial.reachability.algorithm.*;
import pipe.gui.imperial.reachability.algorithm.parallel.MassiveParallelStateSpaceExplorer;
import pipe.gui.imperial.reachability.algorithm.sequential.SequentialStateSpaceExplorer;
import pipe.gui.imperial.pipe.exceptions.InvalidRateException;
import pipe.gui.imperial.pipe.io.PetriNetIOImpl;
import pipe.gui.imperial.pipe.io.PetriNetReader;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;
import pipe.gui.imperial.state.ClassifiedState;
import pipe.gui.imperial.state.Record;

import javax.swing.*;
import javax.xml.bind.JAXBException;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JPanel used to load the state space exploration results from Petri nets and binary state space results.
 */
public class StateSpaceLoader {
    /**
     * Class logger
     */
    private static final Logger LOGGER = Logger.getLogger(StateSpaceLoader.class.getName());

    /**
     * Number of states to explore before reducing and writing out
     */
    private static final int STATES_PER_THREAD = 100;

    /**
     * For loading Petri nets to explore
     */
    private final FileDialog loadDialog;

    /**
     * Enabled if a Petri net is specified, disabled otherwise
     */
    private JRadioButton useExistingPetriNetRadioButton;

    /**
     * Name label for the loaded Petri net
     */
    private JTextField petriNetNameLabel;

    /**
     * Load from file radio, always enabled. Clicking on this
     * brings up the file loader
     */
    private JRadioButton loadPetriNetFromFileRadioButton;

    /**
     * Main display panel with all the loading options
     */
    private JPanel mainPanel;

    /**
     * Binary field label displaying the name of the binary state file loaded
     */
    private JTextField stateFieldLabel;

    /**
     * Binary field label displaying the name of the binary transitions file loaded
     */
    private JTextField transitionFieldLabel;

    /**
     * Load from binaries radio, always enabled. Clicking on this will
     * bring up the file loader twice, once for the states binary and
     * once for the transitions binary.
     */
    private JRadioButton loadFromBinariesRadio;

    /**
     * Default petri net
     */
    private PetriNet defaultPetriNet;

    /**
     * Temporary transitions file for generating results into
     */
    private Path temporaryTransitions;

    /**
     * Temporary states file for generating results into
     */
    private Path temporaryStates;

    /**
     * Last loaded Petri net via the load dialog
     */
    private PetriNet lastLoadedPetriNet;

    /**
     * Binary transitions loaded when binary transitions radio check box is selected
     */
    private Path binaryTransitions;

    /**
     * Binary states loaded when binary transitions radio check box is selected
     */
    private Path binaryStates;

    /**
     * Sets up the load Petri net options with the "use current Petri net" disabled
     *
     * @param loadDialog dialog
     */
    public StateSpaceLoader(FileDialog loadDialog) {
        this.loadDialog = loadDialog;
        useExistingPetriNetRadioButton.setEnabled(false);
        setUp();
    }

    /**
     * Set up the binary radio button action listeners.
     * Clicking on the load Petri net brings up a since xml file loader
     * Clicking on the load from binaries brings up two loaders, once for
     * the states binaries and one for the transition binaries.
     */
    private void setUp() {
        loadPetriNetFromFileRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData();
            }
        });
        loadFromBinariesRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadBinaryFiles();
            }
        });
    }

    /**
     * Opens the file dialog and saves the selected Petri net into lastLoadedPetriNet
     * for use when calculating the state space exploration
     */
    private void loadData() {
        loadDialog.setMode(FileDialog.LOAD);
        loadDialog.setTitle("Select petri net");
        loadDialog.setVisible(true);

        File[] files = loadDialog.getFiles();
        if (files.length > 0) {
            File path = files[0];
            try {
                petriNetNameLabel.setText(path.getName());
                PetriNetReader petriNetIO = new PetriNetIOImpl();
                lastLoadedPetriNet = petriNetIO.read(path.getAbsolutePath());
            } catch (JAXBException | FileNotFoundException e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        }
    }

    /**
     * Loads the transition and state binary files into the member variables
     */
    private void loadBinaryFiles() {
        loadDialog.setMode(FileDialog.LOAD);
        loadDialog.setTitle("Load transitions file");
        loadDialog.setVisible(true);
        File[] files = loadDialog.getFiles();
        if (files.length > 0) {
            File file = files[0];
            binaryTransitions = Paths.get(file.toURI());
            transitionFieldLabel.setText(file.getName());
        } else {
            return;
        }

        loadDialog.setTitle("Load states file");
        loadDialog.setVisible(true);
        File[] statesFiles = loadDialog.getFiles();
        if (statesFiles.length > 0) {
            File file = statesFiles[0];
            binaryStates = Paths.get(file.toURI());
            stateFieldLabel.setText(file.getName());
        } else {
            LOGGER.log(Level.INFO, "No file loaded");
        }
    }

    /**
     * Sets up the load Petri net options with "use current Petri net" set to
     * the petriNet parameter
     *
     * @param petriNet   current Petri net
     * @param loadDialog dialog
     */
    public StateSpaceLoader(PetriNet petriNet, FileDialog loadDialog) {
        defaultPetriNet = petriNet;
        this.loadDialog = loadDialog;
        setUp();
    }

    public boolean isBinaryLoadChecked() {
        return loadFromBinariesRadio.isSelected();
    }

    public PetriNet getPetriNet() {
        return useExistingPetriNetRadioButton.isSelected() ? defaultPetriNet : lastLoadedPetriNet;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }


    /**
     * Calculates the steady state exploration of a Petri net and stores its results
     * in a temporary file.
     * <p>
     * These results are then read in and turned into a graphical representation using mxGraph
     * which is displayed to the user
     * </p>
     * @param creator  explorer creator
     * @param vanishingCreator vanishing creator
     * @param threads across which to spread work
     * @return state space explorer results 
     * @throws TimelessTrapException unable to exit cyclic vanishing state
     * @throws InterruptedException  thread interrupted
     * @throws ExecutionException task aborted due to exception
     * @throws IOException error doing IO
     * @throws InvalidRateException functional rate expression invalid
     * @throws StateSpaceLoaderException if error during loading from binaries 
     */
    public StateSpaceExplorer.StateSpaceExplorerResults calculateResults(ExplorerCreator creator,
                                                                         VanishingExplorerCreator vanishingCreator, int threads)
            throws IOException, InterruptedException, ExecutionException, InvalidRateException, TimelessTrapException,
            StateSpaceLoaderException {
        if (loadFromBinariesRadio.isSelected()) {
            return loadFromBinaries();
        } else {
            KryoStateIO stateWriter = new KryoStateIO();
            temporaryTransitions = getTransitionsPath();
            temporaryStates = getStatesPath();

            PetriNet petriNet = useExistingPetriNetRadioButton.isSelected() ? defaultPetriNet : lastLoadedPetriNet;
            if (petriNet == null) {
                String message;
                if (useExistingPetriNetRadioButton.isSelected()) {
                    message = "Error cannot calculate analysis metrics. Please load a Petri net/binaries.";
                } else {
                    message = "Error in loaded Petri net, could not read PNML file.";
                }
                throw new StateSpaceLoaderException(message);
            }
            ExplorerUtilities explorerUtils = creator.create(petriNet);
            VanishingExplorer vanishingExplorer = vanishingCreator.create(explorerUtils);
            return generateStateSpace(stateWriter, temporaryTransitions, temporaryStates, petriNet, explorerUtils,
                    vanishingExplorer, threads);
        }
    }

    /**
     * Loads the transitions and states from binaries
     *
     * @return state space explorer results 
     * @throws IOException if IO error
     * @throws StateSpaceLoaderException if error during loading from binaries 
     * @return state space explorer results 
     */
    private StateSpaceExplorer.StateSpaceExplorerResults loadFromBinaries()
            throws IOException, StateSpaceLoaderException {
        StateReader stateReader = new KryoStateIO();
        temporaryTransitions = getTransitionsPath();
        temporaryStates = getStatesPath();
        return processBinaryResults(stateReader, temporaryTransitions);

    }

    /**
     * @return Path for state space transitions
     * @throws IOException if IO error occurs 
     */
    private Path getTransitionsPath() throws IOException {
        return loadFromBinariesRadio.isSelected() ? binaryTransitions : Files.createTempFile("transitions", ".tmp");
    }

    /**
     * @return Path for state space states
     * @throws IOException if IO error occurs 
     */
    private Path getStatesPath() throws IOException {
        return loadFromBinariesRadio.isSelected() ? binaryStates : Files.createTempFile("states", ".tmp");
    }

    /**
     * Writes the state space into transitions and states
     *
     * @param stateWriter writer
     * @param transitions to write
     * @param states to write
     * @param threads number of worker threads to use
     * @return state space explorer results 
     * @throws TimelessTrapException unable to exit cyclic vanishing state
     * @throws InterruptedException  thread interrupted
     * @throws ExecutionException task aborted due to exception
     * @throws IOException error doing IO
     * @throws InvalidRateException functional rate expression invalid
     */
    private StateSpaceExplorer.StateSpaceExplorerResults generateStateSpace(StateWriter stateWriter, Path transitions,
                                                                            Path states, PetriNet petriNet,
                                                                            ExplorerUtilities explorerUtils,
                                                                            VanishingExplorer vanishingExplorer,
                                                                            int threads)
            throws IOException, TimelessTrapException, ExecutionException, InterruptedException, pipe.gui.imperial.pipe.exceptions.InvalidRateException {
        try (OutputStream transitionStream = Files.newOutputStream(transitions);
             OutputStream stateStream = Files.newOutputStream(states)) {
            try (Output transitionOutput = new Output(transitionStream);
                 Output stateOutput = new Output(stateStream)) {
                return writeStateSpace(stateWriter, transitionOutput, stateOutput, petriNet, explorerUtils,
                        vanishingExplorer, threads);
            }
        }
    }

    /**
     * Processes the binary results and returns their state space
     *
     * @param stateReader reader 
     * @param transitions to process
     * @return state space explorer results 
     * @throws IOException if IO error
     * @throws StateSpaceLoaderException if error during loading from binaries 
     */
    private StateSpaceExplorer.StateSpaceExplorerResults processBinaryResults(StateReader stateReader, Path transitions)
            throws IOException, StateSpaceLoaderException {
        try (InputStream inputStream = Files.newInputStream(transitions);
             Input transitionInput = new Input(inputStream)) {
            try {
                Collection<Record> records = readResults(stateReader, transitionInput);
                int transitionCount = getTransitionCount(records);
                return new StateSpaceExplorer.StateSpaceExplorerResults(transitionCount, records.size());
            } catch (IOException e) {
                throw new StateSpaceLoaderException(
                        "Could not parse binaries.\nAre you sure they were generated using the PIPE 5 state space explorer module?",
                        e);
            }
        }
    }

    /**
     * Writes the petriNet state space out to a temporary file which is referenced by the objectOutputStream
     *
     * @param stateWriter       format in which to write the results to
     * @param transitionOutput  stream to write state space to
     * @param stateOutput       stream to write state integer mappings to
     * @param explorerUtilites  explorer utilities
     * @param threads number of worker threads to use
     * @param vanishingExplorer 
     * @return state space explorer results 
     * @throws TimelessTrapException unable to exit cyclic vanishing state
     * @throws InterruptedException  thread interrupted
     * @throws ExecutionException task aborted due to exception
     * @throws IOException error doing IO
     * @throws InvalidRateException functional rate expression invalid
     */
    private StateSpaceExplorer.StateSpaceExplorerResults writeStateSpace(StateWriter stateWriter,
                                                                         Output transitionOutput, Output stateOutput,
                                                                         PetriNet petriNet,
                                                                         ExplorerUtilities explorerUtilites,
                                                                         VanishingExplorer vanishingExplorer, int threads)
            throws TimelessTrapException, ExecutionException, InterruptedException, IOException, pipe.gui.imperial.pipe.exceptions.InvalidRateException {
        StateProcessor processor = new StateIOProcessor(stateWriter, transitionOutput, stateOutput);
        StateSpaceExplorer stateSpaceExplorer = getStateSpaceExplorer(explorerUtilites, vanishingExplorer, processor, threads);
        return stateSpaceExplorer.generate(explorerUtilites.getCurrentState());
    }

    private AbstractStateSpaceExplorer getStateSpaceExplorer(ExplorerUtilities explorerUtilites, VanishingExplorer vanishingExplorer, StateProcessor stateProcessor, int threads) {
        if (threads == 1) {
            return new SequentialStateSpaceExplorer(explorerUtilites, vanishingExplorer, stateProcessor);
        }
        return new  MassiveParallelStateSpaceExplorer(explorerUtilites, vanishingExplorer, stateProcessor, threads, STATES_PER_THREAD);
    }

    /**
     * Reads results of steady state exploration into a collection of records
     *
     * @param stateReader reader 
     * @param input to process
     * @return state transitions with rates
     * @throws IOException error doing IO
     */
    private Collection<Record> readResults(StateReader stateReader, Input input) throws IOException {
        MultiStateReader reader = new EntireStateReader(stateReader);
        return reader.readRecords(input);
    }

    /**
     * @param records to process
     * @return the number of transitions in the state space
     */
    private int getTransitionCount(Iterable<Record> records) {
        int sum = 0;
        for (Record record : records) {
            sum += record.successors.size();
        }
        return sum;
    }

    /**
     * Loads and processes state space
     *
     * @return results
     * @throws IOException error doing IO
     * @throws StateSpaceLoaderException if error during loading from binaries 
     */
    public Results loadStateSpace() throws StateSpaceLoaderException, IOException {
        KryoStateIO stateReader = new KryoStateIO();
        try (InputStream inputStream = Files.newInputStream(temporaryTransitions);
             InputStream stateInputStream = Files.newInputStream(temporaryStates);
             Input transitionInput = new Input(inputStream);
             Input stateInput = new Input(stateInputStream)) {
            Collection<Record> records = readResults(stateReader, transitionInput);
            Map<Integer, ClassifiedState> stateMap = readMappings(stateReader, stateInput);
            return new Results(records, stateMap);
        }
    }

    /**
     * Reads results of the mapping of an integer state representation to
     * the Classified State it represents
     *
     * @param stateReader reader 
     * @param input to process
     * @return state mappings
     * @throws IOException error doing IO
     */
    private Map<Integer, ClassifiedState> readMappings(StateReader stateReader, Input input) throws IOException {
        MultiStateReader reader = new EntireStateReader(stateReader);
        return reader.readStates(input);
    }

    public void saveBinaryFiles() {
        if (temporaryStates != null && temporaryTransitions != null) {
            copyFile(temporaryTransitions, "Select location for temporary transitions");
            copyFile(temporaryStates, "Select location for temporary states");
        }
    }


    /**
     * @param temporary path to copy to new location
     * @param message   displayed message in save file dialog pop up
     */
    private void copyFile(Path temporary, String message) {
        loadDialog.setMode(FileDialog.SAVE);
        loadDialog.setTitle(message);
        loadDialog.setVisible(true);

        File[] files = loadDialog.getFiles();
        if (files.length > 0) {
            File file = files[0];
            Path path = Paths.get(file.toURI());
            try {
                Files.copy(temporary, path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        }
    }

    public void addPetriNetRadioListener(ActionListener listener) {
        loadPetriNetFromFileRadioButton.addActionListener(listener);
        useExistingPetriNetRadioButton.addActionListener(listener);
    }

    public void addBinariesListener(ActionListener listener) {
        loadFromBinariesRadio.addActionListener(listener);
    }

    /**
     * Used in place of a lambda to create the explorer utilities needed for generating the
     * state space from a Petri net
     */
    public interface ExplorerCreator {
        ExplorerUtilities create(PetriNet petriNet);
    }


    /**
     * Used in place of a lambda to create the vanishing utilities needed for
     * generating the state space from a Petri net
     */
    public interface VanishingExplorerCreator {
        VanishingExplorer create(ExplorerUtilities utils);
    }

    /**
     * State space exploration results
     */
    public class Results {
        /**
         * Transition records
         */
        public final Collection<Record> records;

        /**
         * Classified state mappings
         */
        public final Map<Integer, ClassifiedState> stateMappings;

        /**
         * Constructor
         *
         * @param records of results
         * @param stateMappings state mappings 
         */
        public Results(Collection<Record> records, Map<Integer, ClassifiedState> stateMappings) {
            this.records = records;
            this.stateMappings = stateMappings;
        }
    }
}
