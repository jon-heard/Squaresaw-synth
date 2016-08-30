
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
//import com.badlogic.gdx.scenes.scene2d.Touchable;

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
		if(textBack == null)
		{
			textBack = UIDraw.drawButton(1, 300, 20);
		}
		initInput();
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		if(DEBUG)
		{
			super.draw(batch, parentAlpha);
			if(drawText)
			{
				batch.draw(textBack, getX(), getY());
				CFG.getStandardFont().draw(batch, text, getX(), getY()+15);
			}
		}
	}
	
	private void initInput()
	{
		addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent evt, float x, float y, int pointer, int mouseButton)
			{
				drawText = true;
				return true;
			}
			@Override
			public void touchUp(InputEvent evt, float x, float y, int pointer, int mouseButton)
			{
				drawText = false;
			}
		});
	}
}
