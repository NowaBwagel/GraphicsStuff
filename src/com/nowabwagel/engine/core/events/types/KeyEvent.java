package com.nowabwagel.engine.core.events.types;

import com.nowabwagel.engine.core.events.Event;

public class KeyEvent extends Event{
	public static final String EVENT_TAG = "KeyEvent";
	private long window;
	private int key;
	private int scancode;
	private int action;
	private int mods;

	public KeyEvent(long window, int key, int scancode, int action, int mods) {
		super(EVENT_TAG);
		this.window = window;
		this.key = key;
		this.scancode = scancode;
		this.action = action;
		this.mods = mods;
	}

	public long getWindow() {
		return window;
	}

	public int getKey() {
		return key;
	}

	public int getScancode() {
		return scancode;
	}

	public int getAction() {
		return action;
	}

	public int getMods() {
		return mods;
	}
}
