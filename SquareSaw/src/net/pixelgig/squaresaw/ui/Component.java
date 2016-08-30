
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.ui;

import java.util.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;
//import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Component extends Group
{
	protected static List<Component> components = new ArrayList<Component>();
	public static void regenerateTextures()
	{
		long time = System.currentTimeMillis();
		for(Component component : components)
		{
			component.generateTextures(time);
		}
	}
	
	protected Sprite view;

	public Component()
	{
		components.add(this);
	}
	public void dispose()
	{
		components.remove(this);
	}
	public void generateTextures(long time) {}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		if(this.isVisible())
		{
			if(view != null)
			{
				view.draw(batch);
			}
			super.draw(batch, parentAlpha);
		}
	}


	public Sprite getView() { return view; }
	public void setView(Texture value)
	{
		setView(new Sprite(value));
	}
	public void setView(TextureRegion value)
	{
		setView(new Sprite(value));
	}
	public void setView(Sprite value)
	{
		view = value;
		if(view != null)
		{
			view.setPosition(getX(), getY());
			setWidth(view.getWidth());
			setHeight(view.getHeight());
			view.setOrigin(getOriginX(), getOriginY());
			view.setRotation(getRotation());
			view.setScale(getScaleX(), getScaleY());
			view.setColor(getColor());
		}
	}
	public void setPosition(float x, float y)
	{
		super.setPosition(x, y);
		if(view != null)
		{
			view.setPosition(x, y);
		}
	}
	public void setSize(float width, float height)
	{
		super.setSize(width, height);
		if(view != null)
		{
			view.setSize(width, height);
		}
	}
	public void setBounds(float x, float y, float width, float height)
	{
		super.setBounds(x, y, width, height);
		if(view != null)
		{
			view.setBounds(x, y, width, height);
		}
	}
	public void setOrigin(float x, float y)
	{
		super.setOrigin(x, y);
		if(view != null)
		{
			view.setOrigin(x, y);
		}
	}
	public void setRotation(float value)
	{
		super.setRotation(value);
		if(view != null)
		{
			view.setRotation(value);
		}
	}
	public void setScale(float value)
	{
		super.setScale(value);
		if(view != null)
		{
			view.setScale(value);
		}
	}
	public void setScale(float x, float y)
	{
		super.setScale(x, y);
		if(view != null)
		{
			view.setScale(x, y);
		}
	}
	public void setColor(Color value)
	{
		super.setColor(value);
		if(view != null)
		{
			view.setColor(value);
		}
	}
}
