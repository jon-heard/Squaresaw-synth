
package net.pixelgig.squaresaw.ui;

public class Dimmer extends SpriteActor
{
	Dimmer()
	{
		super(UIDraw.drawDimmer());
		setSize(10000, 10000);
		touchable = true;
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer)
	{
		return this.visible;
	}
}
