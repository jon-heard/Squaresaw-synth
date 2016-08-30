
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
//import com.badlogic.gdx.scenes.scene2d.Touchable;

import net.pixelgig.squaresaw.Listeners.*;

public class Dragbar extends SpriteActor
{
	float dragOffset;
	boolean isHorizontal = true;
	float lowerBounds = 0, upperBounds = 100;
	protected ListenerList listeners = new ListenerList();

	public Dragbar(
			boolean isHorizontal,
			int thickness, int length)
	{
		super(
				isHorizontal ?
					UIDraw.drawHorizontalDragBar() :
					UIDraw.drawVerticalDragBar());
		this.isHorizontal = isHorizontal;
		this.setSize(
			isHorizontal ? length : thickness,
			isHorizontal ? thickness : length
		);
		initInput();
	}
	
	public void addListener(DragbarListener listener)
	{
		listeners.add(listener);
	}
	public void removeListener(DragbarListener listener)
	{
		listeners.remove(listener);
	}
	
	public void setValueBounds(float lowerBounds, float upperBounds)
	{
		this.lowerBounds = lowerBounds;
		this.upperBounds = upperBounds;
	}

	public void setX(float value)
	{
		this.setPosition(value, this.getY());
	}
	public void setY(float value)
	{
		this.setPosition(this.getX(), value);
	}

	private void initInput()
	{
		addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent evt, float x, float y, int pointer, int mouseButton)
			{
				dragOffset = isHorizontal? x : y;
				return true;
			}
			@Override
			public void touchUp(InputEvent evt, float x, float y, int pointer, int mouseButton)
			{
				listeners.event("onDragBarDragEnd", new DragEvent(Dragbar.this));
			}
			@Override
			public void touchDragged(InputEvent evt, float x, float y, int pointer)
			{
				if(isHorizontal)
				{
					Dragbar.this.setX(Dragbar.this.getX() + x - dragOffset);
					if(Dragbar.this.getX() > lowerBounds)
					{
						Dragbar.this.setX(lowerBounds);
					}
					if(Dragbar.this.getX() < upperBounds)
					{
						Dragbar.this.setX(upperBounds);
					}
				}
				else
				{
					Dragbar.this.setY(Dragbar.this.getY() + y - dragOffset);
					if(Dragbar.this.getY() > lowerBounds)
					{
						Dragbar.this.setY(lowerBounds);
					}
					if(Dragbar.this.getY() < upperBounds)
					{
						Dragbar.this.setY(upperBounds);
					}
				}
				listeners.event("onDragbarDrag", new DragEvent(Dragbar.this));
			}
		});
	}
	
	public interface DragbarListener extends Listener
	{
		public void onDragbarDrag(DragEvent evt);
		public void onDragBarDragEnd(DragEvent evt);
	}
	public class DragEvent extends ListenerEvent
	{
		public DragEvent(Dragbar src)
		{
			super(src);
		}
	}
}
