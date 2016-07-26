
package net.pixelgig.squaresaw.ui;

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
		this.touchable = true;
		width = isHorizontal ? length : thickness;
		height = isHorizontal ? thickness : length;
		this.setSize(width, height);
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

	@Override
	public boolean touchDown(float x, float y, int pointer)
	{
		dragOffset = isHorizontal? x : y;
		return true;
	}
	@Override
	public void touchUp(float x, float y, int pointer)
	{
		listeners.event("onDragBarDragEnd", new DragEvent(this));
	}
	@Override
	public void touchDragged(float x, float y, int pointer)
	{
		if(isHorizontal)
		{
			this.setX(this.x + x - dragOffset);
			if(this.x > lowerBounds)
			{
				this.setX(lowerBounds);
			}
			if(this.x < upperBounds)
			{
				this.setX(upperBounds);
			}
		}
		else
		{
			this.setY(this.y + y - dragOffset);
			if(this.y > lowerBounds)
			{
				this.setY(lowerBounds);
			}
			if(this.y < upperBounds)
			{
				this.setY(upperBounds);
			}
		}
		listeners.event("onDragbarDrag", new DragEvent(this));
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
