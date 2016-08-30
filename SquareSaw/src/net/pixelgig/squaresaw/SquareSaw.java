
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw;

import net.pixelgig.squaresaw.audio.*;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.*;
//import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SquareSaw implements ApplicationListener
{
	public static String VERSION = "0.00.03.023";
	private final int TRACK_COUNT = 4;

	private Stage stage;
	Sprite aspectRatios;
	OrthographicCamera camera;
	Viewport viewport;
	Song tracks;
	SquareSaw_UI ui;

	public SquareSaw()
	{
		super();

		Instrument[] tracks = new Instrument[TRACK_COUNT];
		for(int i = 0; i < tracks.length; i++)
		{
			tracks[i] = App.createInstrument();
		}
		this.tracks = new Song(tracks);
	}
	
	public void create()
	{
		camera = new OrthographicCamera();
		viewport = new FitViewport(480,320,camera);
		//viewport = new FillViewport(1000, 500,camera);
		viewport.apply();
		
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);

		
		CFG.init();
		ui = new SquareSaw_UI(tracks);
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		stage.addActor(ui);
	}

	public void render()
	{
		camera.update();
		stage.act(Gdx.graphics.getDeltaTime());
		//stage.clear();
		//Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//batch.setProjectionMatrix(camera.combined);
		stage.draw();
	}

	public void resize (int width, int height)
	{
		viewport.update(width, height);
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
