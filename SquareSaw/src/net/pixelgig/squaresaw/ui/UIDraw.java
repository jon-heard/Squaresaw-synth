
package net.pixelgig.squaresaw.ui;

import net.pixelgig.squaresaw.CFG;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;

public class UIDraw
{
	public static final int GRADIENT_HORIZONTAL = 1;
	public static final int GRADIENT_VERTICAL = 2;
	public static final int GRADIENT_DIAGONAL_1 = 3;
	public static final int GRADIENT_DIAGONAL_2 = 4;
	public static final int GRADIENT_RADIAL = 5;
	
	public static Color XColor(Color src, float val)
	{
		return new Color(
				src.r * val,
				src.g * val,
				src.b * val,
				1);
	}

	public static TextureRegion drawKnob(int state)
	{
		Texture result = new Texture(16, 16, Pixmap.Format.RGBA8888);
		Pixmap p = new Pixmap(15, 15, Pixmap.Format.RGBA8888);

		Color color = CFG.getColor(CFG.COLOR_CONTROL);
		if(state == 1)
		{
			color = CFG.getColor(CFG.COLOR_CONTROL_HIGHLIGHT);
		}
		p.setColor(color);
			p.fillCircle(7, 7, 7);
		p.setColor(XColor(color, 1.1f));
			p.fillCircle(7, 7, 4);
		p.setColor(XColor(color, .4f));
			p.drawCircle(7, 7, 7);
			p.drawLine(7, 2, 7, 1);

		result.draw(p, 0,0);
		p.dispose();
		return new TextureRegion(result, 0, 0, 15, 15);
	}

	public static TextureRegion drawKnobShadow()
	{
		Texture result = new Texture(16, 16, Pixmap.Format.RGBA8888);
		Pixmap p = new Pixmap(15, 15, Pixmap.Format.RGBA8888);

		p.setColor(new Color(0,0,0,1));
			p.fillCircle(7, 7, 7);

		result.draw(p, 0,0);
		p.dispose();
		return new TextureRegion(result, 0, 0, 15, 15);
	}

	public static TextureRegion drawKnobShine()
	{
		Texture result = new Texture(2, 2, Pixmap.Format.RGBA8888);
		Pixmap p = new Pixmap(2, 2, Pixmap.Format.RGBA8888);

		p.setColor(new Color(1,1,1,.86f));
			p.drawPixel(0, 1);
			p.drawPixel(1, 0);

		result.draw(p, 0,0);
		p.dispose();
		return new TextureRegion(result);
	}

	public static TextureRegion drawSpinnerBack()
	{
		Texture result = new Texture(1, 4, Pixmap.Format.RGBA8888);
		Pixmap p = new Pixmap(1, 4, Pixmap.Format.RGBA8888);

		p.setColor(CFG.getColor(CFG.COLOR_SUNKEN_BACK));
			p.drawLine(0, 0, 0, 4);

		result.draw(p, 0,0);
		p.dispose();
		return new TextureRegion(result);
	}

	public static TextureRegion drawButton(int state, int width, int height)
	{
		int texWidth = MathUtils.nextPowerOfTwo(width);
		int texHeight = MathUtils.nextPowerOfTwo(height);
		Texture result = new Texture(
				texWidth, texHeight,
				Pixmap.Format.RGBA8888);
		Pixmap p = new Pixmap(width, height, Pixmap.Format.RGBA8888);

		Color color = CFG.getColor(CFG.COLOR_CONTROL);
		int offset = 0;
		if(state == Button.BUTTON_STATE_PRESSED)
		{
			color = CFG.getColor(CFG.COLOR_CONTROL_HIGHLIGHT);
			offset = 1;
		}
		p.setColor(color);
			if(state == Button.BUTTON_STATE_ON)
			{
				p.setColor(CFG.getColor(CFG.COLOR_CONTROL_ON));
			}
			p.fillRectangle(1+offset, 1+offset, width-3, height-3);
		p.setColor(XColor(color, .6f));
			p.drawLine(1+offset, 0+offset, width-3+offset, 0+offset);
			p.drawLine(0+offset, 1+offset, 0+offset, height-3+offset);
		p.setColor(XColor(color, .4f));
			p.drawLine(
					width - 2 + offset,	1 + offset,
					width - 2 + offset,	height - 3 + offset);
			p.drawLine(
					1 + offset,			height - 2 + offset,
					width - 3 + offset,	height - 2 + offset);
		if(state != Button.BUTTON_STATE_PRESSED)
		{
			p.setColor(CFG.getColor(CFG.COLOR_SHADOW));
				p.drawLine(width-1, 2, width-1, height-2);
				p.drawLine(2, height-1, width-2, height-1);
				p.drawPixel(width-2, height-2);
		}

		result.draw(p, 0,0);
		p.dispose();
		return new TextureRegion(result, 0, 0, width, height);
	}
	public static TextureRegion drawSelectArrow()
	{
		Texture result = new Texture(16, 8, Pixmap.Format.RGBA8888);
		Pixmap p = new Pixmap(9, 5, Pixmap.Format.RGBA8888);

		p.setColor(new Color(0, 0, 0, .5f));
		p.drawLine(0, 0, 8, 0);
		p.drawLine(1, 1, 7, 1);
		p.drawLine(2, 2, 6, 2);
		p.drawLine(3, 3, 5, 3);
		p.drawLine(4, 4, 4, 4);

		result.draw(p, 0,0);
		p.dispose();
		return new TextureRegion(result, 0, 0, 9, 5);
	}
	public static TextureRegion drawUIPanel(int width, int height)
	{
		int texWidth = MathUtils.nextPowerOfTwo(width);
		int texHeight = MathUtils.nextPowerOfTwo(height);
		Texture result = new Texture(
				texWidth, texHeight,
				Pixmap.Format.RGBA8888);
		Pixmap p = new Pixmap(width, height, Pixmap.Format.RGBA8888);

		/// Main area
		p.setColor(CFG.getColor(CFG.COLOR_UIPANEL));
			p.fillRectangle(0, 0, width, height);
		/// Shine
		drawRays(p);
		/// Highlight
		p.setColor(new Color(1, 1, 1, .25f));
			p.fillRectangle(0, 0, 3, height);
			p.fillRectangle(0, 0, width, 3);
		/// Lowlight
		p.setColor(new Color(0, 0, 0, .25f));
			p.fillRectangle(width-3, 0, 3, height);
			p.fillRectangle(0, height-3, width, 3);
		/// Faded bottom
		p.setColor(new Color(0, 0, 0, .2f));
			p.fillRectangle(0, height-30, width, 30);
			p.fillRectangle(0, height-15, width, 15);
			p.fillRectangle(0, height-7, width, 7);
		/// Rivets
		p.setColor(new Color(1, 1, 1, .25f));
			p.fillRectangle(5, 5, 3, 5);
			p.fillRectangle(5, 5, 5, 3);
			p.fillRectangle(width-10, 5, 3, 5);
			p.fillRectangle(width-10, 5, 5, 3);
			p.fillRectangle(5, height-10, 3, 5);
			p.fillRectangle(5, height-10, 5, 3);
			p.fillRectangle(width-10, height-10, 3, 5);
			p.fillRectangle(width-10, height-10, 5, 3);
			
		result.draw(p, 0,0);
		p.dispose();
		return new TextureRegion(result, 0, 0, width, height);
	}
	public static TextureRegion drawSunkenArea(int width, int height)
	{
		int texWidth = MathUtils.nextPowerOfTwo(width);
		int texHeight = MathUtils.nextPowerOfTwo(height);
		Texture result = new Texture(
				texWidth, texHeight,
				Pixmap.Format.RGBA8888);
		Pixmap p = new Pixmap(width, height, Pixmap.Format.RGBA8888);

		/// Main area
		p.setColor(CFG.getColor(CFG.COLOR_SUNKEN_BACK));
			p.fillRectangle(0, 0, width, height);
		/// Shine
			drawRays(p);
		/// Lowlight
			p.setColor(new Color(0, 0, 0, .25f));
			p.fillRectangle(0, 0, 3, height);
			p.fillRectangle(0, 0, width, 3);
		/// Highlight
			p.setColor(new Color(1, 1, 1, .25f));
			p.fillRectangle(width-3, 0, 3, height);
			p.fillRectangle(0, height-3, width, 3);			
		result.draw(p, 0,0);
		p.dispose();
		return new TextureRegion(result, 0, 0, width, height);
	}
	protected static void drawRays(Pixmap p)
	{
		drawRay(p, 30, 15, .04f);
		drawRay(p, 60, 5, .06f);
		drawRay(p, 90, 25, .03f);
		drawRay(p, 140, 5, .03f);
		drawRay(p, 150, 5, .045f);
		drawRay(p, 220, 10, .04f);
		drawRay(p, 240, 20, .06f);
		drawRay(p, 270, 5, .03f);
		drawRay(p, 350, 10, .04f);
		drawRay(p, 400, 10, .03f);
		drawRay(p, 415, 25, .05f);
		drawRay(p, 470, 15, .04f);
		drawRay(p, 500, 10, .06f);
		drawRay(p, 570, 10, .03f);
		drawRay(p, 585, 5, .04f);
		drawRay(p, 620, 15, .06f);
		drawRay(p, 680, 10, .05f);
		drawRay(p, 720, 20, .06f);
	}
	protected static void drawRay(
			Pixmap p, int position, int width, float brightness)
	{
		int height = p.getHeight();
		p.setColor(new Color(1, 1, 1, brightness));
		for(int i = position; i < position+width; i++)
		{
			p.drawLine(i, 0, i-height, height);
		}
	}

	public static TextureRegion drawBorderedBox(
			Color color, int width, int height)
	{
		int texWidth = MathUtils.nextPowerOfTwo(width);
		int texHeight = MathUtils.nextPowerOfTwo(height);
		Texture result = new Texture(
				texWidth, texHeight, Pixmap.Format.RGBA8888);
		Pixmap p = new Pixmap(width, height, Pixmap.Format.RGBA8888);

		p.setColor(color);
			p.fillRectangle(0, 0, width, height);
		p.setColor(new Color(0, 0, 0, 1));
			p.drawRectangle(0, 0, width, height);

		result.draw(p, 0,0);
		p.dispose();
		return new TextureRegion(result, 0, 0, width, height);
	}

	public static TextureRegion drawDimmer()
	{
		Texture result = new Texture(1, 1, Pixmap.Format.RGBA8888);
		Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);

		p.setColor(CFG.getColor(CFG.COLOR_DIMMER));
			p.drawPixel(0, 0);

		result.draw(p, 0,0);
		p.dispose();
		return new TextureRegion(result);
	}
	public static TextureRegion drawControlBlock(boolean highlighted)
	{
		Texture result = new Texture(1, 1, Pixmap.Format.RGBA8888);
		Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);

		p.setColor(CFG.getColor(CFG.COLOR_CONTROL));
		if(highlighted)
		{
			p.setColor(CFG.getColor(CFG.COLOR_CONTROL_HIGHLIGHT));
		}
			p.drawPixel(0, 0);

		result.draw(p, 0,0);
		p.dispose();
		return new TextureRegion(result);
	}

	public static TextureRegion drawConfirmBack(int height)
	{
		final int SHADOW_HEIGHT = 10;
		
		height += SHADOW_HEIGHT;
		int texHeight = MathUtils.nextPowerOfTwo(height);
		Texture result = new Texture(1, texHeight, Pixmap.Format.RGBA8888);
		Pixmap p = new Pixmap(1, height, Pixmap.Format.RGBA8888);

		p.setColor(CFG.getColor(CFG.COLOR_CONFIRM_BACK));
			p.drawLine(0, 0, 0, height-SHADOW_HEIGHT-1);
		p.setColor(Color.BLACK);
			p.drawPixel(0, 0);
			p.drawPixel(0, height-SHADOW_HEIGHT-1);
		p.setColor(CFG.getColor(CFG.COLOR_SHADOW));
			p.drawLine(0, height-SHADOW_HEIGHT, 0, height);

		result.draw(p, 0,0);
		p.dispose();
		return new TextureRegion(result, 0, 0, 1, height);
	}

	public static TextureRegion drawWhiteKeyUp(int width, int height)
	{
		return drawKey(width, height, CFG.getColor(CFG.COLOR_KEY_WHITE_UP));
	}
	public static TextureRegion drawBlackKeyUp(int width, int height)
	{
		return drawKey(
				width, (int)(height/1.5),
				CFG.getColor(CFG.COLOR_KEY_BLACK_UP));
	}
	public static TextureRegion drawWhiteKeyDown(int width, int height)
	{
		return drawKey(width, height, CFG.getColor(CFG.COLOR_KEY_WHITE_DOWN));
	}
	public static TextureRegion drawBlackKeyDown(int width, int height)
	{
		return drawKey(
				width, (int)(height/1.5),
				CFG.getColor(CFG.COLOR_KEY_BLACK_DOWN));
	}

	protected static TextureRegion drawKey(int width, int height, Color color)
	{
		int texWidth = MathUtils.nextPowerOfTwo(width);
		int texHeight = MathUtils.nextPowerOfTwo(height);
		Texture result = new Texture(
				texWidth, texHeight,
				Pixmap.Format.RGBA8888);
		Pixmap p = new Pixmap(width, height, Pixmap.Format.RGBA8888);

		p.setColor(color);
			p.fillRectangle(5, 0, width-10, height-5);
		p.setColor(XColor(color, .6f));
			p.fillRectangle(2, 0, 3, height-5);
			p.fillRectangle(width-5, 0, 3, height-5);
			p.fillRectangle(5, height-5, width-10, 3);
		p.setColor(new Color(0, 0, 0, .25f));
			p.fillRectangle(2, 0, width-4, 10);
			p.fillRectangle(2, 0, width-4, 25);

		result.draw(p, 0,0);
		p.dispose();
		return new TextureRegion(result, 0, 0, width, height);
	}

	public static TextureRegion drawHorizontalDragBar()
	{
		Texture result = new Texture(1, 4, Pixmap.Format.RGBA8888);
		Pixmap p = new Pixmap(1, 4, Pixmap.Format.RGBA8888);

		p.setColor(CFG.getColor(CFG.COLOR_DRAGBAR));
			p.drawLine(0,  0, 0, 3);
		p.setColor(new Color(0, 0, 0, .25f));
			p.drawPixel(0, 0);
			p.drawPixel(0, 3);

		result.draw(p, 0,0);
		p.dispose();
		return new TextureRegion(result);
	}
	public static TextureRegion drawVerticalDragBar()
	{
		Texture result = new Texture(4, 1, Pixmap.Format.RGBA8888);
		Pixmap p = new Pixmap(4, 1, Pixmap.Format.RGBA8888);

		p.setColor(CFG.getColor(CFG.COLOR_DRAGBAR));
			p.drawLine(0,  0, 3, 0);
		p.setColor(new Color(0, 0, 0, .25f));
			p.drawPixel(0, 0);
			p.drawPixel(3, 0);

		result.draw(p, 0,0);
		p.dispose();
		return new TextureRegion(result);
	}
	
	public static TextureRegion drawDragNotice()
	{
		Texture result = new Texture(32, 64, Pixmap.Format.RGBA8888);
		Pixmap p = new Pixmap(32, 64, Pixmap.Format.RGBA8888);

		p.setColor(new Color(0, 0, 0, .35f));
			p.fillRectangle(11, 17, 10, 20);
			for(int i = 0; i < 16; i++)
			{
				p.drawLine(i, 16, i, 16-i);
				p.drawLine(31-i, 16, 31-i, 16-i);
			}

		result.draw(p, 0,0);
		p.dispose();
		return new TextureRegion(result, 0, 0, 32, 41);
	}

	public static TextureRegion drawWaveForm(int type)
	{
		String filename = CFG.getWaveIcon(type);
		Texture result = new Texture(filename);
		return new TextureRegion(result);
	}

	public static TextureRegion drawConstruction()
	{
		Texture result = new Texture("construction.png");
		return new TextureRegion(result);
	}

	public static TextureRegion drawTitle()
	{
		Texture result = new Texture("title.png");
		return new TextureRegion(result);
	}
}
