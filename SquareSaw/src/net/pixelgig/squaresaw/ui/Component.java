
package net.pixelgig.squaresaw.ui;

import java.util.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;

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
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		if(visible)
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
		view.setPosition(x, y);
		width = view.getWidth();
		height = view.getHeight();
		view.setOrigin(originX, originY);
		view.setRotation(rotation);
		view.setScale(scaleX, scaleY);
		view.setColor(color);
	}
	public boolean getVisible() { return visible; }
	public void setVisible(boolean value)
	{
		visible = value;
	}
	public boolean getTouchable() { return touchable; }
	public void setTouchable(boolean value)
	{
		touchable = value;
	}
	public float getX() { return view.getX(); }
	public float getY() { return view.getY(); }
	public void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
		if(view != null)
		{
			view.setPosition(x, y);
		}
	}
	public float getWidth() { return view.getWidth(); }
	public float getHeight() { return view.getHeight(); }
	public void setSize(float width, float height)
	{
		this.width = width;
		this.height = height;
		if(view != null)
		{
			view.setSize(width, height);
		}
	}
	public void setBounds(float x, float y, float width, float height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		if(view != null)
		{
			view.setBounds(x, y, width, height);
		}
	}
	public float getOriginX() { return view.getOriginX(); }
	public float getOriginY() { return view.getOriginY(); }
	public void setOrigin(float x, float y)
	{
		this.originX = x;
		this.originY = y;
		if(view != null)
		{
			view.setOrigin(x, y);
		}
	}
	public float getRotation() { return view.getRotation(); }
	public void setRotation(float value)
	{
		rotation = value;
		if(view != null)
		{
			view.setRotation(value);
		}
	}
	public float getScaleX() { return view.getScaleX(); }
	public float getScaleY() { return view.getScaleY(); }
	public void setScale(float value)
	{
		scaleX = value;
		scaleY = value;
		if(view != null)
		{
			view.setScale(value);
		}
	}
	public void setScale(float x, float y)
	{
		this.scaleX = x;
		this.scaleY = y;
		if(view != null)
		{
			view.setScale(x, y);
		}
	}
	public Color getColor() { return view.getColor(); }
	public void setColor(Color value)
	{
		if(view != null)
		{
			view.setColor(value);
		}
	}
}
