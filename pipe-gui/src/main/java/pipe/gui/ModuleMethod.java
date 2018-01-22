package pipe.gui;

import pipe.gui.plugin.GuiModule;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This class represents a module dynamically loaded at run-time
 * and is repsonsible for executing the module
 */
public class ModuleMethod
{

    /**
     * Class logger
     */
    private static final Logger LOGGER = Logger.getLogger(ModuleMethod.class.getName());

    private final Method modMeth;
    private final Class<? extends GuiModule> clazz;
    private String name;


    /* Sets up the Class and Method that this class encapsulates
    * @param cl The Class that the Method belongs to
    * @param m The Method that this class represents
    */
    public ModuleMethod(Class<? extends GuiModule> clazz, Method m)
    {
        this.clazz = clazz;
        modMeth = m;
        name = m.getName();
    }


    /**
     * Returns the name of the modMeth
     */
    public String toString()
    {
        return name;
    }


    /**
     *
     * @param _name method name
     */
    public void setName(String _name)
    {
        name = _name;
    }

    /**
     * Executes the module.
     * @param petriNet the current Petri net to pass into the {@link pipe.gui.plugin.GuiModule}
     */
    public void execute(PetriNet petriNet)
    {
        try
        {
            Constructor<? extends GuiModule> ctr = clazz.getDeclaredConstructor(new Class[0]);
            Object moduleObj = ctr.newInstance();

            // handy debug to see what's being passed to the module
            //System.out.println("models obj being passed to module: ");
            //args[0].print();

            // invoke the name method for display
            modMeth.invoke(moduleObj, petriNet);

        } catch (IllegalAccessException | NoSuchMethodException | SecurityException | InvocationTargetException | InstantiationException | IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Error in module method invocation: " + e.getMessage());
        }
    }


    /**
     * @return Returns the modClass.
     */
    public Class<?> getModClass()
    {
        return clazz;
    }

}
