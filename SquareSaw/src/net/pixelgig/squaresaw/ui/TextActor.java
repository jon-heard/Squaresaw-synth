
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.ui;

import net.pixelgig.squaresaw.CFG;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
//import com.badlogic.gdx.scenes.scene2d.Touchable;

public class TextActor extends Actor
{
	protected BitmapFont font = null;
	String text = "";
	GlyphLayout glyph = new GlyphLayout();

	public TextActor(String text)
	{
		this(text, 0, 0);
	}
	public TextActor(String text, int x, int y)
	{
		this.font = CFG.getStandardFont();
		setText(text);
		glyph.setText(font, text);
		this.setPosition(x, y);
	}
	
	public BitmapFont getFont() { return font; }
	public void setFont(BitmapFont value)
	{
		font = value;
		glyph.setText(font, text);
		this.setSize(glyph.width, glyph.height);
	}
	public String getText() { return text; }
	public void setText(String value)
	{
		text = value;
		glyph.setText(font, text);
		this.setSize(glyph.width, glyph.height);
	}
	

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		font.draw(batch, glyph,
				getX()-getOriginX(),
				getY()-getOriginY()+getHeight());
	}
//	@Override
//	public Actor hit(float x, float y, boolean touchable)
//	{
//		return x > 0 && x < getWidth() && y > 0 && y < getHeight() ? this : null;
//	}
}
