
package net.pixelgig.squaresaw.ui.settings;

import com.badlogic.gdx.Gdx;
import net.pixelgig.squaresaw.SquareSaw;
import net.pixelgig.squaresaw.CFG;
import net.pixelgig.squaresaw.Song;
import net.pixelgig.squaresaw.ui.Button;
import net.pixelgig.squaresaw.ui.Dialog_Confirmation;
import net.pixelgig.squaresaw.ui.ConstructionNote;
import net.pixelgig.squaresaw.ui.SpriteActor;
import net.pixelgig.squaresaw.ui.TextActor;
import net.pixelgig.squaresaw.ui.UIDraw;
import net.pixelgig.squaresaw.ui.UIPanel;
import net.pixelgig.squaresaw.ui.Button.ButtonListener;
import net.pixelgig.squaresaw.ui.Button.ClickEvent;

public class SettingsGroup extends UIPanel implements ButtonListener
{
	static int BUTTON_SONG_NEW = 0;
	static int BUTTON_SONG_LOAD = 1;
	static int BUTTON_SONG_SAVE = 2;
	static int BUTTON_SONG_SAVEAS = 3;
	static int BUTTON_OPTIONS = 4;
	static int BUTTON_QUIT = 5;

	Song tracks;
	Button[] buttons;

	public SettingsGroup(Song tracks, int x, int y, int width, int height)
	{
		super(x, y, width, height);

		this.tracks = tracks;
		buttons = new Button[6];

		SpriteActor s;
		s = new SpriteActor(UIDraw.drawTitle());
		s.setPosition(width/2-255, 225);
		addActor(s);
		String c = "Copyright (c) 2011 Pixelgig";
			addActor(new TextActor(
					c,
					(int)(width-CFG.getStandardFont().getBounds(c).width)-5,
					45));
		c = "ver " + SquareSaw.VERSION;
			addActor(new TextActor(
					c,
					(int)(width-CFG.getStandardFont().getBounds(c).width)-5,
					30));
		buttons[BUTTON_SONG_NEW] = new Button("song>New>", "New Song");
			buttons[BUTTON_SONG_NEW].setPosition((width-Button.WIDTH)/2, 200);
			buttons[BUTTON_SONG_NEW].addListener(this);
			addActor(buttons[BUTTON_SONG_NEW]);
			addActor(new ConstructionNote((width-Button.WIDTH)/2, 200, "Song options"));
		buttons[BUTTON_SONG_LOAD] = new Button("song>Load>", "Load Song");
			buttons[BUTTON_SONG_LOAD].setPosition((width-Button.WIDTH)/2, 175);
			buttons[BUTTON_SONG_LOAD].addListener(this);
			addActor(buttons[BUTTON_SONG_LOAD]);
			addActor(new ConstructionNote((width-Button.WIDTH)/2, 175, "Song options"));
		buttons[BUTTON_SONG_SAVE] = new Button("song>Save>", "Save Song");
			buttons[BUTTON_SONG_SAVE].setPosition((width-Button.WIDTH)/2, 150);
			buttons[BUTTON_SONG_SAVE].addListener(this);
			addActor(buttons[BUTTON_SONG_SAVE]);
			addActor(new ConstructionNote((width-Button.WIDTH)/2, 150, "Song options"));
		buttons[BUTTON_SONG_SAVEAS] = new Button("song>Save as>", "Save As");
			buttons[BUTTON_SONG_SAVEAS].setPosition((width-Button.WIDTH)/2, 125);
			buttons[BUTTON_SONG_SAVEAS].addListener(this);
			addActor(buttons[BUTTON_SONG_SAVEAS]);
			addActor(new ConstructionNote((width-Button.WIDTH)/2, 125, "Song options"));
		buttons[BUTTON_OPTIONS] = new Button("Options>", "Options");
			buttons[BUTTON_OPTIONS].setPosition((width-Button.WIDTH)/2, 100);
			buttons[BUTTON_OPTIONS].addListener(this);
			addActor(buttons[BUTTON_OPTIONS]);
			addActor(new ConstructionNote((width-Button.WIDTH)/2, 100, "Song options"));
		buttons[BUTTON_QUIT] = new Button("Quit>", "Quit");
			buttons[BUTTON_QUIT].setPosition((width-Button.WIDTH)/2, 75);
			buttons[BUTTON_QUIT].addListener(this);
			addActor(buttons[BUTTON_QUIT]);
			addActor(new ConstructionNote((
					width-Button.WIDTH)/2,
					75, "confirmation dialog"));
		s = new SpriteActor(UIDraw.drawDragNotice());
		s.setPosition(5, 30);
		addActor(s);
	}
	
	public void quit()
	{
		new Dialog_Confirmation(
				"Are you sure you want to quit?", this,
				new Dialog_Confirmation.ConfirmationUser() {
					@Override
					public void onConfirmationComplete(boolean confirmed)
					{
						if(confirmed)
						{
							tracks.stop();
							Gdx.app.exit();
						}
					}
				});
	}

	@Override
	public void onButtonClick(ClickEvent e)
	{
		Button source = (Button)e.getSource();
		if(source == buttons[BUTTON_QUIT])
		{
			quit();
		}
	}
}
