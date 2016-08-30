
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.ui;


import net.pixelgig.squaresaw.CFG;

import com.badlogic.gdx.graphics.g2d.*;

public class Key extends SpriteActor
{
	Sprite up, down;
	boolean isBlack;
	boolean isDown;
	int id;
	
	public Key(int x, boolean isBlack, int id)
	{
		super(CFG.getKeyImage(false, true));
		this.isBlack = isBlack;
		this.isDown = false;
		this.id = id;
		if(isBlack)
		{
			up = new Sprite(CFG.getKeyImage(true, true));
			down = new Sprite(CFG.getKeyImage(true, false));
			setPosition(
					x,
					(int)(CFG.getKeyImage(true, true).getRegionHeight() / 1.5));
		}
		else
		{
			up = new Sprite(CFG.getKeyImage(false, true));
			down = new Sprite(CFG.getKeyImage(false, false));
			setPosition(x, 0);
		}
		
		refresh();
	}

	public void refresh()
	{
		setView(isDown ? down : up);
	}
	public int getID() { return id; }
	public boolean getIsDown() { return isDown; }
	public void setIsDown(boolean value)
	{
		isDown = value;
		refresh();
	}
	
	public boolean containsPoint(float x, float y)
	{
		float tx = this.getX();
		float ty = this.getY();
		float twidth = this.getWidth();
		float theight = this.getHeight();
		if(
			x > tx && x < tx+twidth &&
			y > ty && y < ty+theight)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
