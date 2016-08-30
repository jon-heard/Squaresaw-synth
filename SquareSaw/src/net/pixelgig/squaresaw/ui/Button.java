
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.ui;

import net.pixelgig.squaresaw.CFG;
import net.pixelgig.squaresaw.Listeners.*;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Button extends SpriteActor
{
	public static final int WIDTH = 112;
	public static final int HEIGHT = 20;

	public static final int BUTTON_STATE_NORMAL = 0;
	public static final int BUTTON_STATE_PRESSED = 1;
	public static final int BUTTON_STATE_ON = 2;

	static final int BUTTON_TYPE_NORMAL = 0;
	static final int BUTTON_TYPE_TOGGLE = 1;
	static final int BUTTON_TYPE_SELECT = 2;

	protected ListenerList listeners = new ListenerList();
	String name = "";
	String label = "";
	float labelOffsetX, labelOffsetY;
	int value = 0;
	String[] options;
	Sprite[] optionPictures = null;
	float optionOffsetX[];
	float optionOffsetY;
	TextureRegion[] viewRegions = new TextureRegion[3];
	int state = BUTTON_STATE_NORMAL;
	int buttonType = BUTTON_TYPE_NORMAL;
	
	public Button(String name, String option)
	{
		this(name, new String[] { option });
	}
	public Button(
			String name, String option, int width, int height)
	{
		this(name, new String[] { option }, width, height);
	}
	public Button(String name, String[] options)
	{
		super(CFG.getStandardButtonImage());
		viewRegions[0] = CFG.getStandardButtonImage();
		viewRegions[1] = CFG.getStandardButtonHighlightImage();
		viewRegions[2] = CFG.getStandardButtonOnImage();
		init(name, options);
	}
	public Button(
			String name, String[] options, int width, int height)
	{
		super(UIDraw.drawButton(0, width/4, height/4));
		viewRegions[0] = UIDraw.drawButton(0, width/4, height/4);
		viewRegions[1] = UIDraw.drawButton(1, width/4, height/4);
		viewRegions[2] = UIDraw.drawButton(2, width/4, height/4);
		init(name, options);
	}
	
	protected void init(String name, String[] options)
	{
		setSize(view.getWidth()*4, view.getHeight()*4);
		this.options = options;
		
		setName(name);

		if(options.length == 2 && options[0].equals(options[1]))
		{
			buttonType = BUTTON_TYPE_TOGGLE;
		}
		if(options.length > 1 && buttonType != BUTTON_TYPE_TOGGLE)
		{
			buttonType = BUTTON_TYPE_SELECT;
		}

		optionOffsetX = new float[options.length];
		for(int i = 0; i < options.length; i++)
		{
			GlyphLayout glyph = new GlyphLayout(CFG.getStandardFont(), options[i]);
			optionOffsetX[i] =
					(getWidth() - glyph.width) / 2 - 2;
			if(buttonType == BUTTON_TYPE_SELECT)
			{
				optionOffsetX[i] -= 5;
			}
		}
		optionOffsetY = getHeight()/2 + CFG.getStandardFont().getCapHeight()/2 + 3;
		
		initInput();
	}
	
	public void addListener(ButtonListener listener)
	{
		listeners.add(listener);
	}
	public void removeListener(ButtonListener listener)
	{
		listeners.remove(listener);
	}

	public String getName() { return name; }
	public void setName(String value)
	{
		name = value;
		this.label = name.substring(name.lastIndexOf(">")+1);
		GlyphLayout glyph = new GlyphLayout(CFG.getStandardFont(), this.label);
		labelOffsetX = (getWidth() - glyph.width) / 2 - 2;
		labelOffsetY = getHeight() + CFG.getStandardFont().getCapHeight() + 3;
	}
	public String getSelectedOption() { return options[value]; }
	public int getSelectedIndex() { return value; }
	public void setValue(int value)
	{
		if(value < 0) value = 0;
		if(value > options.length-1) value = options.length-1;
		this.value = value;
		if(buttonType == BUTTON_TYPE_TOGGLE)
		{
			if(value == 1)
			{
				setState(BUTTON_STATE_ON);
			}
			else
			{
				setState(BUTTON_STATE_NORMAL);
			}
		}
	}
	
	public int getState() { return state; }
	public void setState(int value)
	{
		state = value;
		view.setRegion(viewRegions[state]);
	}
	
	public void setOptionPictures(TextureRegion[] value)
	{
		optionPictures = new Sprite[value.length];
		for(int i = 0; i < optionPictures.length; ++i)
		{
			optionPictures[i] = new Sprite(value[i]);
			optionPictures[i].setScale(2);
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		CFG.getStandardFont().draw(
				batch, label, getX() + labelOffsetX, getY() + labelOffsetY);
		super.draw(batch, parentAlpha);
		int offset = 0;
		if(state == BUTTON_STATE_PRESSED)
		{
			offset = 4;
		}
		if(optionPictures == null)
		{
			CFG.getStandardFont().draw(
					batch, options[value],
					getX() + optionOffsetX[value] + offset,
					getY() + optionOffsetY - offset);
		}
		else
		{
			optionPictures[value].setPosition(getX()+12+offset, getY()+14-offset);
			optionPictures[value].draw(batch);
		}
		if(buttonType == BUTTON_TYPE_SELECT)
		{
			batch.draw(
					CFG.getSelectArrowImage(),
					getX() + getWidth() - 19 + offset,
					getY() + getHeight() - 13 - offset);
		}
	}

	public interface ButtonListener extends Listener
	{
		public void onButtonClick(ClickEvent evt);
	}
	public class ClickEvent extends ListenerEvent
	{
		public ClickEvent(Button src)
		{
			super(src);
		}
	}
	
	public void initInput()
	{
		addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int mouseButton)
			{
				setState(BUTTON_STATE_PRESSED);
				return true;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int mouseButton)
			{
				if(buttonType == BUTTON_TYPE_TOGGLE && value == 1)
				{
					setState(BUTTON_STATE_ON);
				}
				else
				{
					setState(BUTTON_STATE_NORMAL);
				}
//				if(this.hit(x,  y, isTouchable()) != this) return false;
				value++;
				if(value > options.length-1)
				{
					value = 0;
				}
				setValue(value);
				listeners.event("onButtonClick", new ClickEvent(Button.this));
			}
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer)
			{
//				if(hit(x, y, isTouchable()) == this)
//				{
//					setState(BUTTON_STATE_PRESSED);
//				}
//				else
//				{
//					if(buttonType == BUTTON_TYPE_TOGGLE && value == 1)
//					{
//						setState(BUTTON_STATE_ON);
//					}
//					else
//					{
//						setState(BUTTON_STATE_NORMAL);
//					}
//				}
			}
		});
	}
}
