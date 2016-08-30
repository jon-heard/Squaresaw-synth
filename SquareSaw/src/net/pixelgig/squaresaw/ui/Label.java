
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.ui;

import net.pixelgig.squaresaw.CFG;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class Label extends Component
{
	public static final int ALIGNMENT_LEFT = 0;
	public static final int ALIGNMENT_CENTER = 1;
	public static final int ALIGNMENT_RIGHT = 2;
	
	protected BitmapFont font = null;
	String text = "";
	GlyphLayout glyph = new GlyphLayout();
	int alignment = ALIGNMENT_LEFT;
	float alignmentOffset = 0;
	
	public Label(String text)
	{
		this(text, 0, 0);
	}
	public Label(String text, int x, int y)
	{
		setSize(x, y);
		this.font = CFG.getStandardFont();
		setText(text);
	}


	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		font.draw(batch, glyph, getX()+alignmentOffset, getY()+getHeight());
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
	public int getAlignment() { return alignment; }
	public void setAlignment(int value)
	{
		alignment = value;
		switch(alignment)
		{
			case ALIGNMENT_LEFT:
				alignmentOffset = 0;
				break;
			case ALIGNMENT_CENTER:
				alignmentOffset = -getWidth()/2; 
				break;
			case ALIGNMENT_RIGHT:
				alignmentOffset = -getWidth(); 
				break;
		}
	}
}
