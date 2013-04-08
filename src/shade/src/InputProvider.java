package shade.src;

import java.util.*;

import shade.src.geom.*;

public abstract class InputProvider {


	public final HashMap<Integer, Character> keyCharMap = new HashMap<Integer, Character>();

	/**
	 * @param s - Name of a key
	 * @return The <tt>int</tt> representation of the key
	 */
	public abstract int getKey(String s);

	/**
	 * @param k - <tt>int</tt> code of a key
	 * @return The <tt>String</tt> representation of the key
	 */
	public abstract String getKeyName(int k);

	/**
	 * Does any setup before mouse and keyboard input can be polled
	 * @throws Exception
	 */
	public abstract void init() throws Exception;

	public abstract void poll();

	public abstract Duple<Integer, Integer> getMousePos();
	public abstract Duple<Integer, Integer> getMouseDelta();
	
	protected abstract Iterator<Triple<Integer, Boolean, Duple<Integer, Integer>>> getMouseEvents();
	protected abstract Iterator<Integer> getMousePressEvents();
	protected abstract Iterator<Integer> getDownMouseButtons();
	protected abstract Iterator<Duple<Integer, Boolean>> getKeyEvents();
	protected abstract Iterator<Integer> getKeyPressEvents();
	protected abstract Iterator<Integer> getDownKeys();

	@SuppressWarnings("unchecked")
	public Iterable<Triple<Integer, Boolean, Duple<Integer, Integer>>> mouseEvents() {
		return (Iterable<Triple<Integer, Boolean, Duple<Integer, Integer>>>) makeIterable(getMouseEvents());
	}

	@SuppressWarnings("unchecked")
	public Iterable<Integer> mousePressEvents() {
		return (Iterable<Integer>) makeIterable(getMousePressEvents());
	}

	@SuppressWarnings("unchecked")
	public Iterable<Integer> downMouseButtons() {
		return (Iterable<Integer>) makeIterable(getDownMouseButtons());
	}

	@SuppressWarnings("unchecked")
	public Iterable<Duple<Integer, Boolean>> keyEvents() {
		return (Iterable<Duple<Integer, Boolean>>) makeIterable(getKeyEvents());
	}

	@SuppressWarnings("unchecked")
	public Iterable<Integer> keyPressEvents() {
		return (Iterable<Integer>) makeIterable(getKeyPressEvents());
	}

	@SuppressWarnings("unchecked")
	public Iterable<Integer> downKeys() {
		return (Iterable<Integer>) makeIterable(getDownKeys());
	}

	@SuppressWarnings("rawtypes")
	private Iterable makeIterable(Iterator i) {
		final Iterator it = i;
		return new Iterable() {

			public Iterator iterator() {
				return it;
			}

		};
	}

	public boolean keyDown(String s) {
		return keyDown(getKey(s));
	}

	public boolean keyDown(int k) {
		for(int i : downKeys())
			if(i == k)
				return true;
		return false;
	}

	public boolean mouseButtonDown(int k) {
		for(int i : downMouseButtons())
			if(i == k)
				return true;
		return false;
	}

	public boolean keyPressed(int k) {
		for(int i : keyPressEvents())
			if(i == k)
				return true;
		return false;
	}
	
	public boolean mousButtonPressed(int k) {
		for(int i : mousePressEvents())
			if(i == k)
				return true;
		return false;
	}
	
	public Duple<Integer, Boolean> getKeyEvent(int k) {
		for(Duple<Integer, Boolean> d : keyEvents())
			if(d.value1().intValue() == k)
				return d;
		return null;
	}

	public Triple<Integer, Boolean, Duple<Integer, Integer>> getMouseEvent(int k) {
		for(Triple<Integer, Boolean, Duple<Integer, Integer>> t : mouseEvents())
			if(t.value1().intValue() == k)
				return t;
		return null;
	}
	
}