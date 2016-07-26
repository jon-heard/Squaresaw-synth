
package net.pixelgig.squaresaw;

import net.pixelgig.squaresaw.Song.TrackChangedEvent;
import net.pixelgig.squaresaw.Song.TrackListListener;
import net.pixelgig.squaresaw.audio.Instrument;
import net.pixelgig.squaresaw.ui.*;
import net.pixelgig.squaresaw.ui.Dragbar.*;
import net.pixelgig.squaresaw.ui.Keyboard.*;
import net.pixelgig.squaresaw.ui.compose.ComposingGroup;
import net.pixelgig.squaresaw.ui.instrument.InstrumentGroup;
import net.pixelgig.squaresaw.ui.sequence.SequencingGroup;
import net.pixelgig.squaresaw.ui.settings.SettingsGroup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveTo;

public class SquareSaw_UI extends Group implements
	Dragbar.DragbarListener,
	KeyboardListener,
	TrackListListener
{
	Group app;
	Dragbar appDrag;
	SettingsGroup settingsPane;
	int[] positions = new int[4];
	int currentPosition = 1;

	Song tracks;
	Instrument track;

	ComposingGroup composingUI;

	public SquareSaw_UI(Song tracks)
	{
		this.tracks = tracks;
		this.track = tracks.getCurrentTrack();
		tracks.addListener(this);

		app = new Group();
			addActor(app);
			app.x = CFG.getDragbarThickness();
			app.width = CFG.getAppWidth()-CFG.getDragbarThickness();
			int vPos = 0;
				positions[0] = -vPos;
				app.addActor(
						new SequencingGroup(
								tracks, 0, vPos,
								(int)app.width, CFG.getAppHeight()));
			vPos += CFG.getAppHeight();
				positions[1] = -vPos;
				composingUI = new ComposingGroup(
						tracks, 0, vPos, (int)app.width,
						CFG.getAppHeight()-
						CFG.getKeyHeight()-CFG.getDragbarThickness());
				app.addActor(composingUI);
			vPos += CFG.getAppHeight()-
					CFG.getKeyHeight()-CFG.getDragbarThickness();
				positions[2] = -vPos;
				Keyboard keys = new Keyboard();
				keys.setPosition(
						CFG.getDragbarThickness() - CFG.getKeyWidth()*22+10,
						vPos+25);
				Dragbar keyDrag = new Dragbar(
						true, CFG.getDragbarThickness(),
						(int)keys.width);
				keyDrag.setPosition((int)keys.x, vPos);
				keyDrag.setValueBounds(0, CFG.getAppWidth()-keys.width-25);
				app.addActor(keys);
				keys.addListener(this);
				keyDrag.addListener(keys);
			vPos += 25;
				app.addActor(keyDrag);
			vPos += CFG.getKeyHeight();
				InstrumentGroup i = new InstrumentGroup(tracks, keys, 0, vPos);
				app.addActor(i);
			vPos += InstrumentGroup.HEIGHT;
				positions[3] = -vPos;
				settingsPane = new SettingsGroup(
						tracks, 0, vPos, (int)app.width, CFG.getAppHeight());
				app.addActor(settingsPane);
			app.height = vPos + CFG.getAppHeight();
			app.y = -vPos;
		appDrag = new Dragbar(false, 25, (int)app.height);
			appDrag.setPosition(0, (int)app.y);
			appDrag.addListener(this);
			appDrag.setValueBounds(0, 320-app.height);
			addActor(appDrag);
	}

	public void lockToPosition(int position)
	{
		if(position == -1)
		{
			if(Math.abs(positions[currentPosition] - app.y) > 25)
			{
				int positionIndex = (currentPosition==0) ? 1 : 0;
				for(int i = 0; i < positions.length; ++i)
				{
					if(i == currentPosition) continue;
					if(
							Math.abs(positions[i]-app.y) <
							Math.abs(positions[positionIndex]-app.y))
					{
						positionIndex = i;
					}
				}
				currentPosition = positionIndex;
			}
		}
		else
		{
			this.currentPosition = position;
		}
		app.action(MoveTo.$(24, positions[currentPosition], .15f));
		appDrag.setY(positions[currentPosition]);
	}

	@Override
	public void onTrackListTrackChanged(TrackChangedEvent evt)
	{
		track = tracks.getCurrentTrack();
	}
	@Override
	public void onKeyboardEvent(KeyEvent evt)
	{
		track.setNote(evt.getKey());
	}

	@Override
	public void onDragbarDrag(DragEvent evt)
	{
		Dragbar d = (Dragbar)evt.getSource();
		app.y = d.y;
	}
	@Override
	public void onDragBarDragEnd(DragEvent evt)
	{
		lockToPosition(-1);
		keyDown(0);
	}
	
	@Override
	public boolean keyDown(int keycode)
	{
		if(keycode == Keys.ESCAPE || keycode == Keys.BACK)
		{
			if(currentPosition == 3)
			{
				settingsPane.quit();
			}
			else
			{
				lockToPosition(3);
			}
			return true;
		}
		if(keycode == Keys.MENU)
		{
			lockToPosition(3);
		}
		return false;
	}
}
