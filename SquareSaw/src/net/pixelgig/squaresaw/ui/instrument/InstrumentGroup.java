
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.ui.instrument;

import java.util.LinkedList;
import java.util.List;

import net.pixelgig.squaresaw.Song;
import net.pixelgig.squaresaw.Song.TrackChangedEvent;
import net.pixelgig.squaresaw.ui.*;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class InstrumentGroup extends Group implements
	Song.TrackListListener
{

	public static final int HEIGHT = 215;

	static final int PANEL_TRACKS = 0;
	static final int PANEL_GENERAL = 1;
	static final int PANEL_TONE = 2;
	static final int PANEL_VOLUME = 3;
	static final int PANEL_FREQENCY = 4;
	static final int PANEL_ARPEGGIATION = 5;
	static final int PANEL_ECHO = 6;

	InstrumentPanel[] panels;
	List<InstrumentPanel> currentPanels;
	Song tracks;
	float dragOffset;

	public InstrumentGroup(
			Song tracks, Keyboard keys,
			float x, float y)
	{
		this.tracks = tracks;
		tracks.addListener(this);
		this.setX(x);
		this.setY(y);
		this.setWidth(928 + 25);
		this.setHeight(HEIGHT);
		
		panels = new InstrumentPanel[7];
		currentPanels = new LinkedList<InstrumentPanel>();
		panels[PANEL_TRACKS] = new TrackInstrumentPanel(
				0, tracks);
			currentPanels.add(panels[PANEL_TRACKS]);
		panels[PANEL_GENERAL] = new GeneralInstrumentPanel(
				0, tracks);
			currentPanels.add(panels[PANEL_GENERAL]);
		panels[PANEL_TONE] = new ToneInstrumentPanel(
				0, tracks.getCurrentTrack());
			currentPanels.add(panels[PANEL_TONE]);
		panels[PANEL_VOLUME] = new VolumeInstrumentPanel(
				0, tracks.getCurrentTrack());
			currentPanels.add(panels[PANEL_VOLUME]);
		panels[PANEL_FREQENCY] = new FrequencyInstrumentPanel(
				0, tracks.getCurrentTrack());
			// TODO: FEATURE: uncomment and implement
			//currentPanels.add(panels[PANEL_FREQENCY]);
		panels[PANEL_ARPEGGIATION] = new ArpeggiationInstrumentPanel(
				0, tracks.getCurrentTrack(), keys);
			currentPanels.add(panels[PANEL_ARPEGGIATION]);
		panels[PANEL_ECHO] = new EchoInstrumentPanel(
				0, tracks.getCurrentTrack());
			// TODO: FEATURE: uncomment and implement
			//currentPanels.add(panels[PANEL_ECHO]);

		refreshPanelPositions();
	}
	
	@Override
	public void onTrackListTrackChanged(TrackChangedEvent evt)
	{
		for(InstrumentPanel panel : panels)
		{
			panel.setTrack(tracks.getCurrentTrack());
		}
		refreshPanelPositions();
	}
	public void refreshPanelPositions()
	{
		int buttonIndex = GeneralInstrumentPanel.BUTTON_SOUND;
		Button soundButton = panels[PANEL_GENERAL].buttons[buttonIndex];
		int curSound = soundButton.getSelectedIndex();
		if(curSound == 0)
		{
			if(!currentPanels.contains(panels[PANEL_TONE]))
			{
				currentPanels.add(2, panels[PANEL_TONE]);
			}
		}
		else
		{
			if(currentPanels.contains(panels[PANEL_TONE]))
			{
				currentPanels.remove(panels[PANEL_TONE]);
			}
		}

		int curPos = 0;
		this.clear();
		for(int i = 0; i < currentPanels.size(); i++)
		{
			UIPanel curPanel = currentPanels.get(i);
			// TODO: Figure out how to do this
			//curPanel.action(MoveTo.$(curPos, curPanel.y, .25f));
			// TODO: ...instead of this
			curPanel.setPosition(curPos,  curPanel.getY());
			curPanel.setX(curPos);
			this.addActor(curPanel);
			curPos += curPanel.getWidth();
		}
		this.setWidth(curPos);

		initInput();
	}


	private boolean inHit = false;
	private void initInput()
	{
		this.addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent evt, float x, float y, int pointer, int mouseButton)
			{
				Object focus = hit(x, y, true);
				if(!(focus instanceof Knob) && !(focus instanceof Button))
				{
					inHit = true;
					dragOffset = x;
				}
				return true;
			}
			@Override
			public void touchUp(InputEvent evt, float x, float y, int pointer, int mouseButton)
			{
				Object focus = hit(x, y, true);
				if(!(focus instanceof Knob) && !(focus instanceof Button))
				{
					inHit = false;
				}
			}
			@Override
			public void touchDragged(InputEvent evt, float x, float y, int pointer)
			{
				if(inHit)
				{
					InstrumentGroup.this.setX(InstrumentGroup.this.getX() + x - dragOffset);
					// TODO: SYNTHDONE: Remove
					if(InstrumentGroup.this.getX() < 480-InstrumentGroup.this.getWidth())
					// TODO: SYNTHDONE: Uncommment
					//if(this.x < 480-24-width)
					{
						// TODO: SYNTHDONE: Remove
						InstrumentGroup.this.setX(480-InstrumentGroup.this.getWidth());
						// TODO: SYNTHDONE: Uncommment
						//this.x = 480-24-width;
					}
					if(InstrumentGroup.this.getX() > 0)
					{
						InstrumentGroup.this.setX(0);
					}
				}
			}
		});
	}

	public String onString()
	{
		return "InstrumentGroup";
	}

}
