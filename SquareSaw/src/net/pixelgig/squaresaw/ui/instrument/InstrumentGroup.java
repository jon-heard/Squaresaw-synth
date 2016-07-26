
package net.pixelgig.squaresaw.ui.instrument;

import java.util.LinkedList;
import java.util.List;

import net.pixelgig.squaresaw.CFG;
import net.pixelgig.squaresaw.Song;
import net.pixelgig.squaresaw.Song.TrackChangedEvent;
import net.pixelgig.squaresaw.ui.*;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveTo;

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
		this.x = x;
		this.y = y;
		this.width = 928 + 25;
		this.height = HEIGHT;
		
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
			currentPanels.add(panels[PANEL_FREQENCY]);
		panels[PANEL_ARPEGGIATION] = new ArpeggiationInstrumentPanel(
				0, tracks.getCurrentTrack(), keys);
			currentPanels.add(panels[PANEL_ARPEGGIATION]);
		panels[PANEL_ECHO] = new EchoInstrumentPanel(
				0, tracks.getCurrentTrack());
			currentPanels.add(panels[PANEL_ECHO]);

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
			curPanel.action(MoveTo.$(curPos, curPanel.y, .25f));
			curPanel.x = curPos;
			this.addActor(curPanel);
			curPos += curPanel.width;
		}
		this.width = curPos;
	}


	@Override
	public boolean touchDown(float x, float y, int pointer)
	{
		super.touchDown(x, y, pointer);
		dragOffset = x;
		return true;
	}
	@Override
	public void touchUp(float x, float y, int pointer) {}
	@Override
	public void touchDragged(float x, float y, int pointer)
	{
		this.x = this.x + x - dragOffset;
		if(this.x < CFG.getAppWidth()-24-width)
		{
			this.x = CFG.getAppWidth()-24-width;
		}
		if(this.x > 0)
		{
			this.x = 0;
		}
	}
	@Override
	public Actor hit(float x, float y)
	{
		if(x > 0 && x < width && y > 0 && y < height)
		{
			return this;
		}
		else
		{
			return null;
		}
	}
}
