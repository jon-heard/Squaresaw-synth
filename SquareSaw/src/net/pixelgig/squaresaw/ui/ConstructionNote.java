
package net.pixelgig.squaresaw.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.pixelgig.squaresaw.CFG;

public class ConstructionNote extends SpriteActor
{
	public static final boolean DEBUG = true;
	public static TextureRegion textBack = null;

	String text;
	boolean drawText = false;

	public ConstructionNote(int x, int y, String text)
	{
		super(CFG.getConstructionImage());
		this.setPosition(x-16, y);
		this.text = text;
		this.touchable = true;
		if(textBack == null)
		{
			textBack = UIDraw.drawButton(1, 300, 20);
		}
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		if(DEBUG)
		{
			super.draw(batch, parentAlpha);
			if(drawText)
			{
				batch.draw(textBack, x, y);
				CFG.getStandardFont().draw(batch, text, x, y+15);
			}
		}
	}
	@Override
	public boolean touchDown(float x, float y, int pointer)
	{
		System.out.println(text);
		drawText = true;
		return true;
	}
	@Override
	public void touchUp(float x, float y, int pointer)
	{
		drawText = false;
	}
}
