
package net.pixelgig.squaresaw.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.pixelgig.squaresaw.CFG;
import net.pixelgig.squaresaw.Listeners.*;

public class Knob extends Control implements Control.ControlListener
{
	public static int WIDTH = 50;

	double value = 0;
	double dragOffsetAngle;
	protected ListenerList knobListeners = new ListenerList();
	Component shadow, shine;
	Label valueDisplay;
	
	public Knob(String label)
	{
		setView(CFG.getStandardKnobImage());
		setSize(WIDTH, WIDTH);
		setOrigin(WIDTH/2, WIDTH/2);
		this.setTouchable(true);
		this.setLabelText(label);
		this.addControlListener(this);
		
		shadow = new Component();
		shadow.setView(CFG.getStandardKnobShadowImage());
		shadow.setSize(WIDTH, WIDTH);
		shadow.setOrigin(WIDTH/2, WIDTH/2);
		shadow.setColor(CFG.getColor(CFG.COLOR_SHADOW));

		shine = new Component();
		shine.setView(CFG.getStandardKnobShineImage());
		shine.setPosition(11, 34);
		shine.setSize(WIDTH/8, WIDTH/8);
		addActor(shine);
		
		valueDisplay = new Label("00");
		valueDisplay.setAlignment(Label.ALIGNMENT_CENTER);
		valueDisplay.setPosition(25, 21);
		addActor(valueDisplay);

		setValue(.5f);
	}


	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		shadow.setPosition(x+3, y-5);
		shadow.draw(batch, parentAlpha);
		super.draw(batch, parentAlpha);
	}


	public double getValue() { return value; }
	public void setValue(double value)
	{
		value = Math.round(value*100) / 100.0;
		if(value > .99) value = .99;
		if(value < 0) value = 0;
		this.value = value;
		view.setRotation((float)(-value*270 + 135));
		shadow.setRotation((float)(-value*270 + 135));
		valueDisplay.setText(String.format("%02d", (int)(value*100)));
		knobListeners.event("onKnobValueChanged", new KnobEvent(this));
	}

	public void addListener(KnobListener listener)
	{
		knobListeners.add(listener);
	}
	public void removeListener(KnobListener listener)
	{
		knobListeners.remove(listener);
	}

	@Override
	public void setPosition(float x, float y)
	{
		super.setPosition(x, y);
	}

	@Override
	public void touchDown(ControlEvent evt)
	{
		float x = originX - evt.getX();
		float y = evt.getY() - originY;
		dragOffsetAngle = getRotation() - Math.toDegrees(Math.atan2(x, y));
		view.setRegion(CFG.getStandardKnobHighlightImage());
	}
	@Override
	public void touchUp(ControlEvent evt)
	{
		view.setRegion(CFG.getStandardKnobImage());
	}
	@Override
	public void touched(ControlEvent evt)
	{
	}
	@Override
	public void dragged(ControlDraggedEvent evt)
	{
		float x = originX - evt.getX();
		float y = evt.getY() - originY;
		float rot =
				(float)Math.toDegrees(Math.atan2(x, y)) +
				(float)dragOffsetAngle;
		if(rot < -180) rot += 360;
		if(rot > 180) rot -= 360;
		value = (rot-135) / (-270);
		setValue(value);
	}
	
	public interface KnobListener extends Listener
	{
		public void onKnobValueChanged(KnobEvent evt);
	}
	public class KnobEvent extends ListenerEvent
	{
		public KnobEvent(Knob src)
		{
			super(src);
		}
	}
}


//public class Knob extends SpriteActor
//{
//	public static int WIDTH = 50;
//
//	String name = "";
//	String label = "";
//	float labelOffsetX, labelOffsetY;
//
//	double value = 0;
//	double dragOffsetAngle;
//	protected ListenerList listeners = new ListenerList();
//	Sprite shadow, shine;
//	
//	public Knob(String name)
//	{
//		super(CFG.getStandardKnobImage());
//		setSize(WIDTH, WIDTH);
//		setOrigin(WIDTH/2, WIDTH/2);
//		this.touchable = true;
//
//		shadow = new Sprite(CFG.getStandardKnobShadowImage());
//		shadow.setSize(WIDTH, WIDTH);
//		shadow.setColor(CFG.getColor(CFG.COLOR_SHADOW));
//
//		shine = new Sprite(CFG.getStandardKnobShineImage());
//		shine.setSize(WIDTH/8, WIDTH/8);
//
//		setName(name);
//		
//		setValue(.5f);
//	}
//	
//	public String getName() { return name; }
//	public void setName(String value)
//	{
//		name = value;
//		this.label = name.substring(name.lastIndexOf(">")+1);
//		labelOffsetX = (width - CFG.getStandardFont().getBounds(label).width)/2;
//		labelOffsetY = height + CFG.getStandardFont().getCapHeight() + 3;
//	}
//	
//	public double getValue() { return value; }
//	public void setValue(double value)
//	{
//		value = Math.round(value*100) / 100.0;
//		if(value > .99) value = .99;
//		if(value < 0) value = 0;
//		this.value = value;
//		setRotation((float)(-value*270 + 135));
//		listeners.event("onKnobValueChanged", new KnobEvent(this));
//	}
//
//	public void addListener(KnobListener listener)
//	{
//		listeners.add(listener);
//	}
//	public void removeListener(KnobListener listener)
//	{
//		listeners.remove(listener);
//	}
//
//	@Override
//	public void setPosition(float x, float y)
//	{
//		super.setPosition(x, y);
//		shadow.setPosition(x+4, y-4);
//		shine.setPosition(x+10, y+34);
//	}
//	@Override
//	public void draw(SpriteBatch batch, float parentAlpha)
//	{
//		CFG.getStandardFont().draw(
//				batch, label,
//				x + labelOffsetX,
//				y + labelOffsetY);
//		shadow.draw(batch);
//		super.draw(batch, parentAlpha);
//		CFG.getStandardFont().draw(
//				batch,
//				String.format("%02d", (int)(value*100)),
//				x + 15, y+30);
//		shine.draw(batch);
//	}
//
//	@Override
//	public boolean touchDown(float x, float y, int pointer)
//	{
//		x = originX - x;
//		y = y - originY;
//		dragOffsetAngle = getRotation() - Math.toDegrees(Math.atan2(x, y));
//
//		view.setRegion(CFG.getStandardKnobHighlightImage());
//		return true;
//	}
//	@Override
//	public void touchUp(float x, float y, int pointer)
//	{
//		view.setRegion(CFG.getStandardKnobImage());
//	}
//
//	@Override
//	public void touchDragged(float x, float y, int pointer)
//	{
//		x = originX - x;
//		y = y - originY;
//		float rot =
//				(float)Math.toDegrees(Math.atan2(x, y)) +
//				(float)dragOffsetAngle;
//		if(rot < -180) rot += 360;
//		if(rot > 180) rot -= 360;
//		value = (rot-135) / (-270);
//		setValue(value);
//	}
//
//	public interface KnobListener extends Listener
//	{
//		public void onKnobValueChanged(KnobEvent evt);
//	}
//	public class KnobEvent extends ListenerEvent
//	{
//		public KnobEvent(Knob src)
//		{
//			super(src);
//		}
//	}
//}
