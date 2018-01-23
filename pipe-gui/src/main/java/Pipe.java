import pipe.actions.gui.PipeApplicationModel;
import pipe.controllers.application.PipeApplicationController;
import pipe.ucl.constructor.controllers.Constructor;
import pipe.views.PipeApplicationBuilder;
import pipe.views.PipeApplicationView;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public final class Pipe {

    protected static PipeApplicationView applicationView;

    private Pipe(String version) {
        PipeApplicationModel applicationModel = new PipeApplicationModel(version);
        PipeApplicationController applicationController = new PipeApplicationController(applicationModel);
        PipeApplicationBuilder builder = new PipeApplicationBuilder();
        applicationView = builder.build(applicationController, applicationModel);
        applicationController.createEmptyPetriNet();
        Constructor constructor = new Constructor(applicationController, applicationModel, builder, applicationView);

    }

    public static void main(String[] args) {
        Runnable runnable = pipeRunnable();
        SwingUtilities.invokeLater(runnable);
    }

    protected static Runnable pipeRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                new Pipe("v5.0.2");
            }
        };
    }

    protected static void runPipeForTesting() throws InterruptedException, InvocationTargetException {
        SwingUtilities.invokeAndWait(pipeRunnable());
    }
}
