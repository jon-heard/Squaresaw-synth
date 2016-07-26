
package net.pixelgig.squaresaw;

import net.pixelgig.squaresaw.audio.*;
import net.pixelgig.squaresaw.ui.Dimmer;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class SquareSaw implements ApplicationListener
{
	public static String VERSION = "0.00.08.023";

	private Stage stage;
	Song tracks;
	SquareSaw_UI ui;

	public SquareSaw()
	{
		super();

		Instrument[] tracks = new Instrument[4];
		for(int i = 0; i < tracks.length; i++)
		{
			tracks[i] = App.createInstrument();
		}
		this.tracks = new Song(tracks);
	}
	
	public void create()
	{
		CFG.init();
		ui = new SquareSaw_UI(tracks);
		stage = new Stage(0, 0, true) {
			public boolean keyDown(int keyCode)
			{
				return ui.keyDown(keyCode);
			}
		};
		Gdx.input.setInputProcessor(stage);
		stage.addActor(ui);
	}

	public void render()
	{
		stage.act(Gdx.graphics.getDeltaTime());
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}

	public void resize (int width, int height)
	{
		stage.setViewport(width, height, true);
		CFG.setAppDimensions(width, height);
	}

	public void pause()
	{
		tracks.pause();
	}

	public void resume()
	{
		tracks.resume();
	}

	public void dispose ()
	{
		tracks.stop();
		if(stage != null)
			stage.dispose();
	}

}
