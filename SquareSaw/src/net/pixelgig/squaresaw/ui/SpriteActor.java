
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
//import com.badlogic.gdx.scenes.scene2d.Touchable;

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
		this.setScale(1,1);
		this.setColor(Color.WHITE);
		setView(view);
	}
	
	public void setView(Sprite value)
	{
		view = value;
		view.setPosition(getX(), getY());
		setSize(view.getWidth(), view.getHeight());
		view.setOrigin(getOriginX(), getOriginY());
		view.setRotation(getRotation());
		view.setScale(getScaleX(), getScaleY());
		view.setColor(getColor());
	}

	public void setPosition(float x, float y)
	{
		super.setX(x);
		super.setY(y);
		//super.setPosition(x, y);
		view.setPosition(x, y);
	}

	public void setSize(float width, float height)
	{
		super.setSize(width, height);
		view.setSize(width, height);
	}
	public void setBounds(float x, float y, float width, float height)
	{
		super.setBounds(x, y, width, height);
		view.setBounds(x, y, width, height);
	}

	public void setOrigin(float x, float y)
	{
		super.setOrigin(x, y);
		view.setOrigin(x, y);
	}

	public void setRotation(float value)
	{
		super.setRotation(value);;
		view.setRotation(value);
	}

	public void setScale(float value)
	{
		super.setScale(value);
		view.setScale(value);
	}
	public void setScale(float x, float y)
	{
		super.setScale(x, y);
		view.setScale(x, y);
	}

	public void setColor(Color value)
	{
		super.setColor(value);
		view.setColor(value);
	}


	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		if(isVisible())
		{
			view.draw(batch);
		}
	}
//	@Override
//	public Actor hit(float x, float y, boolean touchable)
//	{
//		if(isTouchable()) return null;
//		if(x > 0 && x < getWidth() && y > 0 && y < getHeight())
//		{
//			return this;
//		}
//		else
//		{
//			return null;
//		}
//	}
}
