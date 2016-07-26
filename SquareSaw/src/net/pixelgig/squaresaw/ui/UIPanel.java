
package net.pixelgig.squaresaw.ui;



import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class UIPanel extends Group
{
	public UIPanel(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		addActor(new SpriteActor(UIDraw.drawUIPanel(width, height)));
	}
	@Override
	public Actor hit(float x, float y)
	{
		if(!touchable) return null;
		if( x > 0 && x < width && y > 0 && y < height)
		{
			return this;
		}
		else
		{
			return null;
		}
	}
}
