package matchers.component;

import org.mockito.ArgumentMatcher;
import pipe.gui.imperial.pipe.models.petrinet.PetriNetComponent;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class that can have multiple @link{Has} items for a Connectable type
 */
public class HasMultiple<T extends PetriNetComponent> extends ArgumentMatcher<T> {
    List<Has<T>> has_items = new LinkedList<>();

    public HasMultiple(Has<T>... items)
    {
        Collections.addAll(has_items, items);
    }

    @Override
    public boolean matches(Object item) {
        T connectable = (T) item;
        for (Has<T> has : has_items)
        {
            if (!has.matches(connectable))
            {
                return false;
            }
        }
        return true;
    }
}