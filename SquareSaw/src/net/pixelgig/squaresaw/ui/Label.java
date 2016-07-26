
package net.pixelgig.squaresaw.ui;

import net.pixelgig.squaresaw.CFG;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;

public class Label extends Component
{
	public static final int ALIGNMENT_LEFT = 0;
	public static final int ALIGNMENT_CENTER = 1;
	public static final int ALIGNMENT_RIGHT = 2;
	
	protected BitmapFont font = null;
	String text = "";
	int alignment = ALIGNMENT_LEFT;
	float alignmentOffset = 0;
	
	public Label(String text)
	{
		this(text, 0, 0);
	}
	public Label(String text, int x, int y)
	{
		this.x = x;
		this.y = y;
		this.font = CFG.getStandardFont();
		setText(text);
	}


	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		font.draw(batch, text, x+alignmentOffset, y+height);
	}

	public BitmapFont getFont() { return font; }
	public void setFont(BitmapFont value)
	{
		font = value;
	}
	public String getText() { return text; }
	public void setText(String value)
	{
		text = value;
		TextBounds bounds = font.getBounds(text);
		width = bounds.width;
		height = bounds.height;
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
				alignmentOffset = -width/2; 
				break;
			case ALIGNMENT_RIGHT:
				alignmentOffset = -width; 
				break;
		}
	}
}
