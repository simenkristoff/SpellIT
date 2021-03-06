package spellit.core.events;

import java.util.EventListener;

/**
 * The listener interface for receiving nextTurn events. The class that is interested in processing
 * a nextTurn event implements this interface, and the object created with that class is registered
 * with a component using the component's <code>addNextTurnListener</code> method. When the nextTurn
 * event occurs, that object's appropriate method is invoked.
 *
 * @see NextTurnEvent
 */
public interface NextTurnListener extends EventListener {

  /**
   * On next turn.
   */
  void onNextTurn();
}
