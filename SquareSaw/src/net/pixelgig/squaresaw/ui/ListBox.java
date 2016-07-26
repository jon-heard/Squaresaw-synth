
package net.pixelgig.squaresaw.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import net.pixelgig.squaresaw.CFG;
import net.pixelgig.squaresaw.Listeners.Listener;
import net.pixelgig.squaresaw.Listeners.ListenerEvent;
import net.pixelgig.squaresaw.Listeners.ListenerList;

public class ListBox extends DragPanel
{
	static final int ITEM_OFFSET = 3;
	static final int ITEM_HEIGHT = 15;

	SpriteActor highlight;
	protected ListenerList listeners = new ListenerList();
	String[] items;
	int index = -1;

	public ListBox(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.clear();
		addActor(new SpriteActor(UIDraw.drawSunkenArea(width, height)));
		highlight = new SpriteActor(CFG.getStandardControlBlockImage());
			highlight.setSize(width-10, ITEM_HEIGHT);
			highlight.visible = false;
			addActor(highlight);
		addActor(toScroll);
	}
	
	public int getValueIndex() { return index; }
	public void setValueIndex(int index)
	{
		this.index = index;
		highlight.setPosition(
				5, height-(index*ITEM_HEIGHT)-ITEM_HEIGHT-ITEM_OFFSET);
		highlight.visible = (index != -1);
	}
	public String getValue()
	{
		if(index != -1)
		{
			return items[index];
		}
		return null;
	}
	
	public void setItems(String[] items)
	{
		this.items = items;
		toScroll.clear();
		if(items != null && items.length > 0)
		{
			setValueIndex(0);
			highlight.visible = true;
			for(int i = 0; i < items.length; i++)
			{
				toScroll.addActor(new TextActor(
						items[i],
						10,
						(int)(height - i*ITEM_HEIGHT - ITEM_HEIGHT)));
			}
		}
		else
		{
			setValueIndex(-1);
			highlight.visible = false;
		}
		listeners.event(
				"onListBoxValueChangedEvent",
				new ValueChangedEvent(this, index, getValue()));
	}
	
	public void addListener(ListBoxListener listener)
	{
		listeners.add(listener);
	}
	public void removeListener(ListBoxListener listener)
	{
		listeners.remove(listener);
	}

	public void updateHighlight(float x, float y)
	{
		int selectedIndex = (int)((height - y - ITEM_OFFSET)/ITEM_HEIGHT);
		if(selectedIndex < 0 || selectedIndex >= items.length)
		{
			selectedIndex = -1;
		}
		if(index != selectedIndex)
		{
			setValueIndex(selectedIndex);
			listeners.event(
					"onListBoxSelecting",
					new ValueChangedEvent(this, index, getValue()));
		}
	}

	@Override
	public boolean touchDown(float x, float y, int pointer)
	{
		highlight.view.setRegion(CFG.getStandardHighlightBlockImage());
		updateHighlight(x, y);
		listeners.event(
				"onListBoxSelecting",
				new ValueChangedEvent(this, index, getValue()));
		return true;
	}
	@Override
	public void touchUp(float x, float y, int pointer)
	{
		highlight.view.setRegion(CFG.getStandardControlBlockImage());
		listeners.event(
				"onListBoxValueChangedEvent",
				new ValueChangedEvent(this, index, getValue()));
	}
	@Override
	public void touchDragged(float x, float y, int pointer)
	{
		updateHighlight(x, y);
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
	
	public interface ListBoxListener extends Listener
	{
		public void onListBoxSelecting(ValueChangedEvent evt);
		public void onListBoxValueChangedEvent(ValueChangedEvent evt);
	}
	public class ValueChangedEvent extends ListenerEvent
	{
		protected int index;
		protected String value;
		public ValueChangedEvent(ListBox src, int index, String value)
		{
			super(src);
			this.index = index;
			this.value = value;
		}
		public int getIndex() { return index; }
		public String getValue() { return value; }
	}
}
