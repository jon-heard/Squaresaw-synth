
package net.pixelgig.squaresaw.ui.instrument;

import net.pixelgig.squaresaw.Song;
import net.pixelgig.squaresaw.audio.Instrument;
import net.pixelgig.squaresaw.ui.*;
import net.pixelgig.squaresaw.ui.ButtonGroup.ValueChangedEvent;

public class TrackInstrumentPanel extends InstrumentPanel implements
	ButtonGroup.ButtonGroupListener
{
	public static final int WIDTH = 72;
	
	static int BUTTON_TRACK_1 = 0;
	static int BUTTON_TRACK_2 = 1;
	static int BUTTON_TRACK_3 = 2;
	static int BUTTON_TRACK_4 = 3;

	ButtonGroup trackSelect;
	Song tracks;
	
	public TrackInstrumentPanel(int x, Song tracks)
	{
		super(x, WIDTH, tracks.getCurrentTrack());

		this.tracks = tracks;
		
		buttons = new Button[4];
		trackSelect = new ButtonGroup();
		trackSelect.addListener(this);
		
		addActor(new TextActor("Track", 10, 199));

		buttons[BUTTON_TRACK_1] = new Button(
				"1>", new String[]{"1", "1"}, 10*4, 8*4);
			buttons[BUTTON_TRACK_1].setPosition(17, 155);
			trackSelect.addButton(buttons[BUTTON_TRACK_1]);
			addActor(buttons[BUTTON_TRACK_1]);
		buttons[BUTTON_TRACK_2] = new Button(
				"2>", new String[]{"2", "2"}, 10*4, 8*4);
			buttons[BUTTON_TRACK_2].setPosition(17, 115);
			trackSelect.addButton(buttons[BUTTON_TRACK_2]);
			addActor(buttons[BUTTON_TRACK_2]);
		buttons[BUTTON_TRACK_3] = new Button(
				"3>", new String[]{"3", "3"}, 10*4, 8*4);
			buttons[BUTTON_TRACK_3].setPosition(17, 75);
			trackSelect.addButton(buttons[BUTTON_TRACK_3]);
			addActor(buttons[BUTTON_TRACK_3]);
		buttons[BUTTON_TRACK_4] = new Button(
				"4>", new String[]{"4", "4"}, 10*4, 8*4);
			buttons[BUTTON_TRACK_4].setPosition(17, 35);
			trackSelect.addButton(buttons[BUTTON_TRACK_4]);
			addActor(buttons[BUTTON_TRACK_4]);

			setTrack(track);
	}

	@Override
	public void setTrack(Instrument track)
	{
		super.setTrack(track);
		trackSelect.setValue(tracks.getCurrentTrackIndex());
	}

	@Override
	public void onButtonGroupValueChanged(ValueChangedEvent evt)
	{
		tracks.setCurrentTrack(trackSelect.getValueIndex());
	}
}
