package com.nowabwagel.engine.core.input.events;

public class KeyEvent {
	private long window;
	private int key;
	private int scancode;
	private int action;
	private int mods;

	public KeyEvent(long window, int key, int scancode, int action, int mods) {
		super();
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
