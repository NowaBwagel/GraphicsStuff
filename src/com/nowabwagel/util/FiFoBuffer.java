package com.nowabwagel.util;

public class FiFoBuffer<E> {

	private final Class<E> classType;

	private E[] buffer;
	private int size;
	private int head;
	private int tail;
	private boolean isFull;
	private int objectsIn;

	public FiFoBuffer(Class<E> classType, int size) {
		this.classType = classType;
		this.size = size;
		this.head = -1;
		this.tail = -1;
		this.buffer = (E[]) new Object[size];
		this.isFull = false;
		this.objectsIn = 0;
	}

	public boolean add(E o) {
		if (head == -1) {
			head++;
			tail += 2;
			buffer[0] = o;
			return true;
		} else if (!isFull) {
			if (tail < size) {
				buffer[tail] = o;
				tail++;
			}
			if (tail >= size && head != 0)
				tail = 0;
			objectsIn += 1;
			if (objectsIn == size)
				isFull = true;
			return true;
		} else
			return false;
	}

	public E get() {
		if (objectsIn != 0) {
			E ret = (E) buffer[head];
			buffer[head] = null;
			head++;
			if (head >= size)
				head = 0;
			return ret;
		} else
			return null;
	}
}
