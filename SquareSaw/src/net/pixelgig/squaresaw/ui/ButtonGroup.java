
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.ui;

import java.util.*;

import net.pixelgig.squaresaw.Listeners.*;
import net.pixelgig.squaresaw.ui.Button.ButtonListener;
import net.pixelgig.squaresaw.ui.Button.ClickEvent;

public class ButtonGroup implements ButtonListener
{
	List<Button> buttons;
	int index = 0;
	protected ListenerList listeners = new ListenerList();

	public ButtonGroup()
	{
		buttons = new ArrayList<Button>();
	}
	
	public int getValueIndex() { return index; } 
	public Button getValue() { return buttons.get(index); }
	public void setValue(int value)
	{
		setValue(buttons.get(value));
	}
	public void setValue(Button value)
	{
		boolean changed = false;
		for(int i = 0; i < buttons.size(); i++)
		{
			if(buttons.get(i) == value)
			{
				if(index != i)
				{
					index = i;
					changed = true;
				}
			}
		}
		for(Button button : buttons)
		{
			button.setValue(0);
		}
		buttons.get(index).setValue(1);
		if(changed)
		{
			listeners.event(
					"onButtonGroupValueChanged",
					new ValueChangedEvent(this));
		}
	}
	
	public void addListener(ButtonGroupListener listener)
	{
		listeners.add(listener);
	}
	public void removeListener(ButtonGroupListener listener)
	{
		listeners.remove(listener);
	}
	
	public void addButton(Button toAdd)
	{
		toAdd.addListener(this);
		if(buttons.size() == 0)
		{
			toAdd.setState(Button.BUTTON_STATE_ON);
		}
		buttons.add(toAdd);
	}
	public void removeButton(Button toRemove)
	{
		if(buttons.contains(toRemove))
		{
			toRemove.removeListener(this);
			buttons.remove(toRemove);
		}
		if(index > buttons.size())
		{
			index = 0;
		}
	}

	@Override
	public void onButtonClick(ClickEvent evt)
	{
		setValue((Button)evt.getSource());
	}

	public interface ButtonGroupListener extends Listener
	{
		public void onButtonGroupValueChanged(ValueChangedEvent evt);
	}
	public class ValueChangedEvent extends ListenerEvent
	{
		public ValueChangedEvent(ButtonGroup src)
		{
			super(src);
		}
	}
}
