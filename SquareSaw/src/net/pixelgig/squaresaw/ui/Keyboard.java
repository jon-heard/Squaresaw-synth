
package net.pixelgig.squaresaw.ui;

import java.util.*;
import net.pixelgig.squaresaw.CFG;
import net.pixelgig.squaresaw.Listeners.*;
import net.pixelgig.squaresaw.ui.Dragbar.*;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class Keyboard extends Group implements DragbarListener
{
	public static final int KEY_COUNT = 96;

	Key[] keys;
	Key currentKey;
	protected ListenerList listeners = new ListenerList();

	public Keyboard()
	{
		width = (KEY_COUNT/12*7) * CFG.getKeyWidth();
		height = CFG.getKeyHeight();

		keys = new Key[KEY_COUNT];
		
		int j = 0;
		List<Key> accidentals = new LinkedList<Key>();
		List<Key> keyList = new LinkedList<Key>();
		for(int i = 0; i < KEY_COUNT; ++i)
		{
			Key newKey = null;
			int note = i%12;
			if(	note == 1 || note == 3 || note == 6 ||
				note == 8 || note == 10 || note == 12)
			{
				newKey = new Key(j-CFG.getKeyWidth()/2, true, i);
				accidentals.add(newKey);
			}
			else
			{
				newKey = new Key(j, false, i);
				keyList.add(newKey);
				addActor(newKey);
				if(note == 0)
				addActor(new TextActor("C" + (i/12+1), j+7, 10));
				j+=CFG.getKeyWidth();
			}
		}
		for(Key accidental : accidentals)
		{
			keyList.add(0, accidental);
			addActor(accidental);
		}
		keys = keyList.toArray(new Key[0]);
	}
	
	public void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void addListener(KeyboardListener listener)
	{
		listeners.add(listener);
	}
	public void removeListener(KeyboardListener listener)
	{
		listeners.remove(listener);
	}

	public Key findKeyAt(float x, float y)
	{
		for(int i = 0; i < KEY_COUNT; ++i)
		{
			Key check = keys[i];
			if(check.containsPoint(x, y))
			{
				check.setIsDown(true);
				return check;
			}
		}
		return null;
	}
	void changeHitOver(float x, float y)
	{
		if(currentKey != findKeyAt(x, y))
		{
			if(currentKey != null)
			{
				currentKey.setIsDown(false);
			}
			currentKey = findKeyAt(x, y);
			if(currentKey == null)
			{
				listeners.event("onKeyboardEvent", new KeyEvent(this, -1));
			}
			else
			{
				listeners.event(
						"onKeyboardEvent",
						new KeyEvent(this, currentKey.getID()));
			}
		}
	}

	@Override
	public boolean touchDown(float x, float y, int pointer)
	{
		changeHitOver(x, y);
		return true;
	}
	@Override
	public void touchUp(float x, float y, int pointer)
	{
		if(currentKey != null)
		{
			currentKey.setIsDown(false);
			currentKey = null;			
			listeners.event("onKeyboardEvent", new KeyEvent(this, -1));
		}
	}
	@Override
	public void touchDragged(float x, float y, int pointer)
	{
		changeHitOver(x, y);
	}
	@Override
	public Actor hit(float x, float y)
	{
		if(x > 0 && x < width && y > 0 && y < height)
		{
			return this;
		}
		else
		{
			return null;
		}
	}
	@Override
	public void onDragbarDrag(DragEvent evt)
	{
		Dragbar d = (Dragbar)evt.getSource();
		this.x = d.x;
	}
	@Override
	public void onDragBarDragEnd(DragEvent evt) {}

	public interface KeyboardListener extends Listener
	{
		public void onKeyboardEvent(KeyEvent evt);
	}
	public class KeyEvent extends ListenerEvent
	{
		protected int key;
		public KeyEvent(Keyboard src, int key)
		{
			super(src);
			this.key = key;
		}
		public int getKey() { return key; }
	}
}
