
package net.pixelgig.squaresaw.ui;

import net.pixelgig.squaresaw.Listeners.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Control extends Component
{
	private ListenerList controlListeners = new ListenerList();
	String labelText = "";
	Label label = new Label("");
	private boolean isTouched = false;
	float originX, originY;



	public Control()
	{
		touchable = false;
		label.setAlignment(Label.ALIGNMENT_CENTER);
		addActor(label);
	}
	public void addControlListener(ControlListener listener)
	{
		controlListeners.add(listener);
	}
	public void removeControlListener(ControlListener listener)
	{
		controlListeners.remove(listener);
	}
	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		super.draw(batch, parentAlpha);
	}



	private void refresh()
	{
		label.setPosition(width/2, height+3);
	}
	@Override
	public void setSize(float width, float height)
	{
		super.setSize(width, height);
		refresh();
	}
	@Override
	public void setBounds(float x, float y, float width, float height)
	{
		super.setBounds(x, y, width, height);
		refresh();
	}

	public String getLabelText() { return labelText; }
	public void setLabelText(String value)
	{
		labelText = value;
		label.setText(labelText.substring(labelText.lastIndexOf(">")+1));
		refresh();
	}



	@Override
	public boolean touchDown(float x, float y, int pointer)
	{
		isTouched = true;
		originX = x;
		originY = y;
		controlListeners.event("touchDown", new ControlEvent(this, x, y, 0));
		return true;
	}
	@Override
	public void touchUp(float x, float y, int pointer)
	{
		if(isTouched)
		{
			controlListeners.event("touched", new ControlEvent(this, x, y, 0));
			isTouched = false;
		}
		controlListeners.event("touchUp", new ControlEvent(this, x, y, 0));
	}
	@Override
	public void touchDragged(float x, float y, int pointer)
	{
		isTouched = (hit(x, y) == this);
		controlListeners.event(
				"dragged",
				new ControlDraggedEvent(
						this, x, y, originX, originY, 0, isTouched));
	}
	@Override
	public Actor hit(float x, float y)
	{
		if(x > 0 && x < width && y > 0 && y < height)
		{
			return this;
		}
		return null;
	}



	public interface ControlListener extends Listener
	{
		public void touchDown(ControlEvent evt);
		public void touchUp(ControlEvent evt);
		public void touched(ControlEvent evt);
		public void dragged(ControlDraggedEvent evt);
	}
	public class ControlEvent extends ListenerEvent
	{
		float x, y;
		int touchCount;
		public ControlEvent(Control source, float x, float y, int touchCount)
		{
			super(source);
			this.x = x;
			this.y = y;
			this.touchCount = touchCount;
		}
		public float getX() { return x; }
		public float getY() { return y; }
		public int getTouchCount() { return touchCount; }
	}
	public class ControlDraggedEvent extends ControlEvent
	{
		float originX, originY;
		boolean isInControl;
		public ControlDraggedEvent(
				Control source,
				float x, float y,
				float originX, float originY,
				int touchCount, boolean isInControl)
		{
			super(source, x, y, touchCount);
			this.originX = originX;
			this.originY = originY;
			this.isInControl = isInControl;
		}
		public float getOriginX() { return originX; }
		public float getOriginY() { return originY; }
		public boolean getIsInControl() { return isInControl; }
	}
}
