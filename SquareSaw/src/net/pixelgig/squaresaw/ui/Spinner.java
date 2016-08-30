
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.ui;

import net.pixelgig.squaresaw.ui.Button.ButtonListener;
import net.pixelgig.squaresaw.ui.Button.ClickEvent;

import com.badlogic.gdx.scenes.scene2d.Group;

public class Spinner extends Group implements ButtonListener
{
	String name = "";
	TextActor label;
	TextActor display;
	int value = 0;
	int step = 1;
	int min = 0;
	int max = 10;
	
	public Spinner(String name, int value, int step, int min, int max)
	{
		this.value = value;
		this.step = step;
		this.min = min;
		this.max = max;

		label = new TextActor("");
			addActor(label);
		setName(name);

		SpriteActor back;
		Button b;
		back = new SpriteActor(UIDraw.drawSpinnerBack());
			back.setPosition(10, 10);
			back.setSize(54, 14);
			addActor(back);
		display = new TextActor("0");
			addActor(display);
		b = new Button("->", "-", 7*4, 7*4);
			b.addListener(this);
			addActor(b);
		b = new Button("+>", "+", 7*4, 7*4);
			b.setPosition(54, 0);
			b.addListener(this);
			addActor(b);
		setValue(value);
	}
	
	public String getName() { return name; }
	public void setName(String value)
	{
		name = value;
		String txt = name.substring(name.lastIndexOf(">")+1);
		label.setText(txt);
		label.setPosition(39-label.getWidth()/2, 30);
	}
	public int getValue() { return value; }
	public void setValue(int value)
	{
		if(value < min) value = min;
		if(value > max) value = max;
		this.value = value;
		
		String txt = "" + value;
		display.setText(txt);
		display.setPosition(39-getWidth()/2, 12);
	}

	@Override
	public void onButtonClick(ClickEvent evt)
	{
		Button source = ((Button)evt.getSource());
		String selection = source.getName();
		if(selection.equals("->"))
		{
			setValue(value - step);
		}
		if(selection.equals("+>"))
		{
			setValue(value + step);
		}
	}
}
