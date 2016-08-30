
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.ui;



import com.badlogic.gdx.scenes.scene2d.Group;
//import com.badlogic.gdx.scenes.scene2d.Touchable;

public class UIPanel extends Group
{
	public UIPanel(int x, int y, int width, int height)
	{
		this.setBounds(x, y, width, height);
		addActor(new SpriteActor(UIDraw.drawUIPanel(width, height)));
	}
	public String onString()
	{
		return "UIPanel";
	}
//	@Override
//	public Actor hit(float x, float y, boolean touchable)
//	{
//		if(!isTouchable()) return null;
//		if( x > 0 && x < getWidth() && y > 0 && y < getHeight())
//		{
//			return this;
//		}
//		else
//		{
//			return null;
//		}
//	}
}
