package old.com.nowabwagel.engine.core.events;

public class EventDispatcher {
	private Event event;

	public EventDispatcher(Event event) {
		this.event = event;
	}

	public void dispatch(String tag, EventHandler handler) {
		if (event.handled)
			return;
		if (event.getTag().equals(tag))
			event.handled = handler.onEvent(event);
	}
}
