package com.nowabwagel.util;

import java.util.ArrayList;
import java.util.List;

public class EventHandler {
	protected List<EventListener> listeners = new ArrayList<EventListener>();

	public void update() {
		for (EventListener event : listeners)
			event.update();
	}
}
//FIXME: I broke
