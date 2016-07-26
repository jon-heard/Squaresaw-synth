
package net.pixelgig.squaresaw.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SpriteActor extends Actor
{
	protected Sprite view;
	public SpriteActor(TextureRegion view)
	{
		this(new Sprite(view));
	}
	public SpriteActor(Sprite view)
	{
		this.view = view;
		this.touchable = false;
		this.scaleX = this.scaleY = 1;
		this.setColor(Color.WHITE);
		setView(view);
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
	
	public float getX() { return view.getX(); }
	public float getY() { return view.getY(); }
	public void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
		view.setPosition(x, y);
	}
	public float getWidth() { return view.getWidth(); }
	public float getHeight() { return view.getHeight(); }
	public void setSize(float width, float height)
	{
		this.width = width;
		this.height = height;
		view.setSize(width, height);
	}
	public void setBounds(float x, float y, float width, float height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		view.setBounds(x, y, width, height);
	}
	public float getOriginX() { return view.getOriginX(); }
	public float getOriginY() { return view.getOriginY(); }
	public void setOrigin(float x, float y)
	{
		this.originX = x;
		this.originY = y;
		view.setOrigin(x, y);
	}
	public float getRotation() { return view.getRotation(); }
	public void setRotation(float value)
	{
		view.setRotation(value);
	}
	public float getScaleX() { return view.getScaleX(); }
	public float getScaleY() { return view.getScaleY(); }
	public void setScale(float value)
	{
		scaleX = value;
		scaleY = value;
		view.setScale(value);
	}
	public void setScale(float x, float y)
	{
		this.scaleX = x;
		this.scaleY = y;
		view.setScale(x, y);
	}
	public Color getColor() { return view.getColor(); }
	public void setColor(Color value)
	{
		view.setColor(value);
	}


	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		if(visible)
		{
			view.draw(batch);
		}
	}
	@Override
	public boolean touchDown(float x, float y, int pointer) { return false; }
	@Override
	public void touchUp(float x, float y, int pointer) {}
	@Override
	public void touchDragged(float x, float y, int pointer) {}
	@Override
	public Actor hit(float x, float y)
	{
		if(!touchable) return null;
		if(x > 0 && x < width && y > 0 && y < height)
		{
			return this;
		}
		else
		{
			return null;
		}
	}
}
