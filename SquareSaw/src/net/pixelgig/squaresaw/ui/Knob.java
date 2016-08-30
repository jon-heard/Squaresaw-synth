
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
//import com.badlogic.gdx.scenes.scene2d.Touchable;

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
		this.setLabelText(label);
		this.addControlListener(this);
		this.label.setAlignment(Label.ALIGNMENT_CENTER);
		
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
	public void draw(Batch batch, float parentAlpha)
	{
		shadow.setPosition(getX()+3, getY()-5);
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

