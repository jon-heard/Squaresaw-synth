
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

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

import com.badlogic.gdx.scenes.scene2d.Group;

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
			app.setX(CFG.getDragbarThickness());
			app.setWidth(CFG.getAppWidth()-CFG.getDragbarThickness());
			int vPos = 0;
				positions[0] = -vPos;
				app.addActor(
						new SequencingGroup(
								tracks, 0, vPos,
								(int)app.getWidth(), CFG.getAppHeight()));
			vPos += CFG.getAppHeight();
				positions[1] = -vPos;
				composingUI = new ComposingGroup(
						tracks, 0, vPos, (int)app.getWidth(),
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
						(int)keys.getWidth());
				keyDrag.setPosition((int)keys.getX(), vPos);
				// TODO: SYNTHDONE: Remove
				keyDrag.setValueBounds(0, CFG.getAppWidth()-keys.getWidth());
				// TODO: SYNTHDONE: Uncommment
				//keyDrag.setValueBounds(0, CFG.getAppWidth()-keys.getWidth()-25);
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
						tracks, 0, vPos, (int)app.getWidth(), CFG.getAppHeight());
				app.addActor(settingsPane);
			app.setHeight(vPos + CFG.getAppHeight());
			app.setY(-vPos);
		// TODO: SYNTHDONE: Remove
		appDrag = new Dragbar(false, 0, (int)app.getHeight());
		// TODO: SYNTHDONE: Uncommment
		//appDrag = new Dragbar(false, 25, (int)app.height);
			appDrag.setPosition(0, (int)app.getY());
			appDrag.addListener(this);
			appDrag.setValueBounds(0, 320-app.getHeight());
			addActor(appDrag);

		// TODO: SYNTHDONE: Remove
		lockToPosition(2);
	}

	public void lockToPosition(int position)
	{
		if(position == -1)
		{
			if(Math.abs(positions[currentPosition] - app.getY()) > 25)
			{
				int positionIndex = (currentPosition==0) ? 1 : 0;
				for(int i = 0; i < positions.length; ++i)
				{
					if(i == currentPosition) continue;
					if(
							Math.abs(positions[i]-app.getY()) <
							Math.abs(positions[positionIndex]-app.getY()))
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
		// TODO: SYNTHDONE: Remove
		app.setPosition(0, positions[currentPosition]);
		// TODO: SYNTHDONE: Uncommment
		//app.action(MoveTo.$(24, positions[currentPosition], .15f));
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
		app.setY(d.getY());
	}
	@Override
	public void onDragBarDragEnd(DragEvent evt)
	{
		lockToPosition(-1);
		// TODO: SYNTHDONE: Uncommment
//		keyDown(0);
	}
	
	// TODO: SYNTHDONE: Uncommment
//	@Override
//	public boolean keyDown(int keycode)
//	{
//		if(keycode == Keys.ESCAPE || keycode == Keys.BACK)
//		{
//			if(currentPosition == 3)
//			{
//				settingsPane.quit();
//			}
//			else
//			{
//				lockToPosition(3);
//			}
//			return true;
//		}
//		if(keycode == Keys.MENU)
//		{
//			lockToPosition(3);
//		}
//		return false;
//	}
}
