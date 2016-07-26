
package net.pixelgig.squaresaw.ui;

import net.pixelgig.squaresaw.CFG;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextActor extends Actor
{
	protected BitmapFont font = null;
	String text = "";

	public TextActor(String text)
	{
		this(text, 0, 0);
	}
	public TextActor(String text, int x, int y)
	{
		this.font = CFG.getStandardFont();
		this.text = text;
		this.x = x;
		this.y = y;
		this.touchable = false;
		TextBounds bounds = font.getBounds(text);
		width = bounds.width;
		height = bounds.height;
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
	}
	
	public float getX() { return x; }
	public float getY() { return y; }
	public void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	public float getWidth() { return width; }
	public float getHeight() { return height; }
	public void setSize(float width, float height)
	{
		this.width = width;
		this.height = height;
	}
	public void setBounds(float x, float y, float width, float height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public float getOriginX() { return originX; }
	public float getOriginY() { return originY; }
	public void setOrigin(float x, float y)
	{
		this.originX = x;
		this.originY = y;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		font.draw(batch, text, x-originX, y-originY+height);
	}
	@Override
	public boolean touchDown(float x, float y, int pointer) { return false; }
	@Override
	public void touchUp(float x, float y, int pointer) {}
	@Override
	public void touchDragged(float x, float y, int pointer) {}
	@Override
	public Actor hit(float x, float y)
	{
		return x > 0 && x < width && y > 0 && y < height ? this : null;
	}
}
