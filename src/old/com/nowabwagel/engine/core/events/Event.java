package old.com.nowabwagel.engine.core.events;

public class Event {
	private String tag;
	boolean handled;

	protected Event(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}
}
