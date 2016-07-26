
package net.pixelgig.squaresaw;

import net.pixelgig.squaresaw.audio.*;
import net.pixelgig.squaresaw.ui.UIDraw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CFG
{
	public static final int COLOR_CONTROL = 0;
	public static final int COLOR_CONTROL_HIGHLIGHT = 1;
	public static final int COLOR_CONTROL_ON = 2;
	public static final int COLOR_UIPANEL = 3;
	public static final int COLOR_KEY_WHITE_UP = 4;
	public static final int COLOR_KEY_BLACK_UP = 5;
	public static final int COLOR_KEY_WHITE_DOWN = 6;
	public static final int COLOR_KEY_BLACK_DOWN = 7;
	public static final int COLOR_DRAGBAR = 8;
	public static final int COLOR_SHADOW = 9;
	public static final int COLOR_DIMMER = 10;
	public static final int COLOR_CONFIRM_BACK = 11;
	public static final int COLOR_SUNKEN_BACK = 12;
	public static final int COLOR_TOOLTIP_BACK = 13;
	public static final int WAVE_SQUARE = 0;
	public static final int WAVE_TRIANGLE = 1;
	public static final int WAVE_NOISE = 2;
	public static final int WAVE_SINE = 3;
	public static final int WAVE_SAW = 4;
	public static final int WAVE_NES_SQUARE = 5;
	public static final int WAVE_NES_TRIANGLE = 6;
	public static final int WAVE_NES_NOISE = 7;
	public static final int WAVE_SAW_REVERSE = 8;

	static protected CFG instance = null;
	static public void init()
	{
		instance = new CFG();
		instance.setDefaults();
	}
	static public CFG getInstance() { return instance; }
	static public void setInstance(CFG value)
	{
		instance = value;
	}

	static public int getAppWidth() { return instance.appWidth; }
	static public int getAppHeight() { return instance.appHeight; }
	static public void setAppDimensions(int width, int height)
	{
		instance.appWidth = width;
		instance.appHeight = height;
	}
	public static Color getColor(int color) { return instance.colors[color]; }
	public static String getWaveIcon(int wave)
	{
		return instance.waveIcons[wave];
	}
	public static WaveGenerator getWaveGenerator(int wave, double tone)
	{
		try
		{
			Class c = instance.waveGenerators[wave];
			WaveGenerator result = (WaveGenerator)c.newInstance();
			result.setTone(tone);
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public static BitmapFont getStandardFont()
	{
		return instance.standardFont;
	}
	public static TextureRegion getStandardButtonImage()
	{
		return instance.standardButtonImage;
	}
	public static TextureRegion getStandardButtonHighlightImage()
	{
		return instance.standardButtonHighlightImage;
	}
	public static TextureRegion getStandardButtonOnImage()
	{
		return instance.standardButtonOnImage;
	}
	public static TextureRegion getStandardControlBlockImage()
	{
		return instance.standardControlBlockImage;
	}
	public static TextureRegion getStandardHighlightBlockImage()
	{
		return instance.standardHighlightBlockImage;
	}
	public static TextureRegion getStandardKnobImage()
	{
		return instance.standardKnobImage;
	}
	public static TextureRegion getStandardKnobHighlightImage()
	{
		return instance.standardKnobHighlightImage;
	}
	public static TextureRegion getStandardKnobShadowImage()
	{
		return instance.standardKnobShadowImage;
	}
	public static TextureRegion getStandardKnobShineImage()
	{
		return instance.standardKnobShineImage;
	}
	public static TextureRegion getSelectArrowImage()
	{
		return instance.selectArrowImage;
	}
	public static TextureRegion getConstructionImage()
	{
		return instance.constructionImage;
	}
	public static int getKeyWidth() { return instance.keyWidth; }
	public static int getKeyHeight() { return instance.keyHeight; }
	public static TextureRegion getKeyImage(boolean black, boolean up)
	{
		if(black && up) return instance.keyBlackUp;
		if(black && !up) return instance.keyBlackDown;
		if(!black && up) return instance.keyWhiteUp;
		if(!black && !up) return instance.keyWhiteDown;
		return null;
	}
	public static int getDragbarThickness()
	{
		return instance.dragbarThickness;
	}



	public int appWidth, appHeight;
	public Color[] colors;
	public String[] waveIcons;
	public Class[] waveGenerators;
	public BitmapFont standardFont = null;
	public TextureRegion standardButtonImage = null;
	public TextureRegion standardButtonHighlightImage = null;
	public TextureRegion standardButtonOnImage = null;
	public TextureRegion standardControlBlockImage = null;
	public TextureRegion standardHighlightBlockImage = null;
	public TextureRegion standardKnobImage = null;
	public TextureRegion standardKnobHighlightImage = null;
	public TextureRegion standardKnobShadowImage = null;
	public TextureRegion standardKnobShineImage = null;
	public TextureRegion selectArrowImage = null;
	public TextureRegion constructionImage = null;
	public int keyWidth, keyHeight;
	public TextureRegion keyWhiteUp;
	public TextureRegion keyBlackUp;
	public TextureRegion keyWhiteDown;
	public TextureRegion keyBlackDown;
	public int dragbarThickness;

	public void setDefaults()
	{
		appWidth = 480;
		appHeight = 320;
		colors = new Color[14];
		colors[COLOR_CONTROL] = new Color(.7f, .7f, .25f, 1);
			colors[COLOR_CONTROL_HIGHLIGHT] = new Color(.85f, .85f, 0, 1);
			colors[COLOR_CONTROL_ON] = new Color(.85f, 0, 0, 1);
			colors[COLOR_UIPANEL] = new Color(.5f, .5f, .55f, 1);
			colors[COLOR_KEY_WHITE_UP] = new Color(1f, 1f, .85f, 1);
			colors[COLOR_KEY_BLACK_UP] = new Color(.25f, .25f, 0, 1);
			colors[COLOR_KEY_WHITE_DOWN] = new Color(.25f, .25f, .15f, 1);
			colors[COLOR_KEY_BLACK_DOWN] = new Color(.1f, .1f, 0, 1);
			colors[COLOR_DRAGBAR] = new Color(.3f, .3f, .5f, 1);
			colors[COLOR_SHADOW] = new Color(0,0,0, .15f);
			colors[COLOR_DIMMER] = new Color(0,0,0, .5f);
			colors[COLOR_CONFIRM_BACK] = new Color(1,1,1, .8f);
			colors[COLOR_SUNKEN_BACK] = new Color(.55f, .55f, .45f, 1f);
			colors[COLOR_TOOLTIP_BACK] = new Color(1, 1, .75f, 1);
		waveIcons = new String[9];
		waveIcons[WAVE_SQUARE] = "wave_square.png";
			waveIcons[WAVE_TRIANGLE] = "wave_triangle.png";
			waveIcons[WAVE_NOISE] = "wave_noise.png";
			waveIcons[WAVE_SINE] = "wave_sine.png";
			waveIcons[WAVE_SAW] = "wave_saw.png";
			waveIcons[WAVE_SAW_REVERSE] = "wave_saw_reverse.png";
			waveIcons[WAVE_NES_SQUARE] = "wave_nes_square.png";
			waveIcons[WAVE_NES_TRIANGLE] = "wave_nes_triangle.png";
			waveIcons[WAVE_NES_NOISE] = "wave_nes_noise.png";
		waveGenerators = new Class[9];
		waveGenerators[WAVE_SQUARE] = WaveGenerator_Square.class;
			waveGenerators[WAVE_TRIANGLE] = WaveGenerator_Triangle.class;
			waveGenerators[WAVE_NOISE] = WaveGenerator_Noise.class;
			waveGenerators[WAVE_SINE] = WaveGenerator_Sine.class;
			waveGenerators[WAVE_SAW] = WaveGenerator_Saw.class;
			waveGenerators[WAVE_SAW_REVERSE] = WaveGenerator_SawRev.class;
			waveGenerators[WAVE_NES_SQUARE] = WaveGenerator_Square.class;
			waveGenerators[WAVE_NES_TRIANGLE] = WaveGenerator_Triangle.class;
			waveGenerators[WAVE_NES_NOISE] = WaveGenerator_Noise.class;
		standardFont = new BitmapFont(
				Gdx.files.internal("mainfont_black.fnt"),
				Gdx.files.internal("mainfont_black.png"), false);
		standardButtonImage = UIDraw.drawButton(0, 30, 6);
			standardButtonHighlightImage = UIDraw.drawButton(1, 30, 6);
			standardButtonOnImage = UIDraw.drawButton(2, 30, 6);
		standardControlBlockImage = UIDraw.drawControlBlock(false);
		standardHighlightBlockImage = UIDraw.drawControlBlock(true);
		standardKnobImage = UIDraw.drawKnob(0);
			standardKnobHighlightImage = UIDraw.drawKnob(1);
			standardKnobShadowImage= UIDraw.drawKnobShadow();
			standardKnobShineImage= UIDraw.drawKnobShine();
		selectArrowImage = UIDraw.drawSelectArrow();
		constructionImage = UIDraw.drawConstruction();
		keyWidth = 35;
			keyHeight = 80;
		keyWhiteUp = UIDraw.drawWhiteKeyUp(keyWidth, keyHeight);
			keyBlackUp = UIDraw.drawBlackKeyUp(keyWidth, keyHeight);
			keyWhiteDown = UIDraw.drawWhiteKeyDown(keyWidth, keyHeight);
			keyBlackDown = UIDraw.drawBlackKeyDown(keyWidth, keyHeight);
		dragbarThickness = 25;
	}
}
