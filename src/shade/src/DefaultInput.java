package shade.src;

import java.util.*;

import org.lwjgl.input.*;

import shade.src.geom.*;

public class DefaultInput extends InputProvider {

	public ArrayList<Triple<Integer, Boolean, Duple<Integer, Integer>>> mouseEvents = new ArrayList<Triple<Integer, Boolean, Duple<Integer, Integer>>>();
	public ArrayList<Integer> mousePressEvents = new ArrayList<Integer>();
	public final ArrayList<Integer> downMouseButtons = new ArrayList<Integer>();
	public ArrayList<Duple<Integer, Boolean>> keyEvents = new ArrayList<Duple<Integer, Boolean>>();
	public ArrayList<Integer> keyPressEvents = new ArrayList<Integer>();
	public final ArrayList<Integer> downKeys = new ArrayList<Integer>();

	public Duple<Integer, Integer> mousePos = new Duple<Integer, Integer>(0, 0);
	public Duple<Integer, Integer> mouseDelta = new Duple<Integer, Integer>(0,
			0);

	public Iterator<Integer> getDownKeys() {
		return downKeys.iterator();
	}

	public Iterator<Integer> getDownMouseButtons() {
		return downMouseButtons.iterator();
	}

	public int getKey(String name) {
		int i = Keyboard.getKeyIndex(name.toUpperCase());
		if (i == 0)
			throw new RuntimeException("No such key: " + name);
		return i;
	}

	public Iterator<Duple<Integer, Boolean>> getKeyEvents() {
		return keyEvents.iterator();
	}

	public String getKeyName(int k) {
		return Keyboard.getKeyName(k);
	}

	public Iterator<Integer> getKeyPressEvents() {
		return keyPressEvents.iterator();
	}

	public Duple<Integer, Integer> getMouseDelta() {
		return mouseDelta;
	}

	public Iterator<Triple<Integer, Boolean, Duple<Integer, Integer>>> getMouseEvents() {
		return mouseEvents.iterator();
	}

	public Duple<Integer, Integer> getMousePos() {
		return mousePos;
	}

	public Iterator<Integer> getMousePressEvents() {
		return mousePressEvents.iterator();
	}

	public void init() throws Exception {
		Mouse.create();
		Keyboard.create();
	}

	public void poll() {
		Keyboard.enableRepeatEvents(false);
		keyEvents.clear();
		keyPressEvents.clear();
		mouseEvents.clear();
		mousePressEvents.clear();
		while (Keyboard.next()) {
			Integer k = Keyboard.getEventKey();
			boolean down = Keyboard.getEventKeyState();
			keyEvents.add(new Duple<Integer, Boolean>(k, down));
			if (down)
				keyPressEvents.add(k);
			if (down && !downKeys.contains(k))
				downKeys.add(k);
			else
				downKeys.remove(k);
			keyCharMap.put(k, Keyboard.getEventCharacter());
		}
		while (Mouse.next()) {
			Integer k = Mouse.getEventButton();
			boolean down = Mouse.getEventButtonState();
			Triple<Integer, Boolean, Duple<Integer, Integer>> t = new Triple<Integer, Boolean, Duple<Integer, Integer>>(
					k, down, new Duple<Integer, Integer>(Mouse.getEventX(),
							Mouse.getEventY()));
			mouseEvents.add(t);
			if (down)
				mousePressEvents.add(k);
			if (down && !downMouseButtons.contains(k))
				downMouseButtons.add(k);
			else
				downMouseButtons.remove(k);
		}
		mousePos = new Duple<Integer, Integer>(Mouse.getX(), Mouse.getY());
		mouseDelta = new Duple<Integer, Integer>(Mouse.getDX(), Mouse.getDY());
	}

}