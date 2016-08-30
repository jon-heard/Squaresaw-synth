
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.ui.compose;

import net.pixelgig.squaresaw.Song;
import net.pixelgig.squaresaw.Song.TrackChangedEvent;
import net.pixelgig.squaresaw.Song.TrackListListener;
import net.pixelgig.squaresaw.ui.*;
import net.pixelgig.squaresaw.ui.ButtonGroup.ButtonGroupListener;
import net.pixelgig.squaresaw.ui.ButtonGroup.ValueChangedEvent;

public class TrackSelect extends UIPanel implements
	ButtonGroupListener,
	TrackListListener
{
	public static final int WIDTH = 50;
	
	static int BUTTON_TRACK_1 = 0;
	static int BUTTON_TRACK_2 = 1;
	static int BUTTON_TRACK_3 = 2;
	static int BUTTON_TRACK_4 = 3;

	Song tracks;
	ButtonGroup trackSelect;
	Button[] buttons;
	
	public TrackSelect(Song tracks, int x, int height)
	{
		super(x, 0, WIDTH, height);
		
		this.tracks = tracks;
		tracks.addListener(this);

		buttons = new Button[4];
		trackSelect = new ButtonGroup();
		trackSelect.addListener(this);

		buttons[BUTTON_TRACK_1] = new Button(
				"1>", new String[]{"1", "1"}, 10*4, 8*4);
			buttons[BUTTON_TRACK_1].setPosition(7, 155);
			trackSelect.addButton(buttons[BUTTON_TRACK_1]);
			addActor(buttons[BUTTON_TRACK_1]);
		buttons[BUTTON_TRACK_2] = new Button(
				"2>", new String[]{"2", "2"}, 10*4, 8*4);
			buttons[BUTTON_TRACK_2].setPosition(7, 115);
			trackSelect.addButton(buttons[BUTTON_TRACK_2]);
			addActor(buttons[BUTTON_TRACK_2]);
		buttons[BUTTON_TRACK_3] = new Button(
				"3>", new String[]{"3", "3"}, 10*4, 8*4);
			buttons[BUTTON_TRACK_3].setPosition(7, 75);
			trackSelect.addButton(buttons[BUTTON_TRACK_3]);
			addActor(buttons[BUTTON_TRACK_3]);
		buttons[BUTTON_TRACK_4] = new Button(
				"4>", new String[]{"4", "4"}, 10*4, 8*4);
			buttons[BUTTON_TRACK_4].setPosition(7, 35);
			trackSelect.addButton(buttons[BUTTON_TRACK_4]);
			addActor(buttons[BUTTON_TRACK_4]);
	}

	@Override
	public void onTrackListTrackChanged(TrackChangedEvent evt)
	{
		trackSelect.setValue(tracks.getCurrentTrackIndex());
	}
	@Override
	public void onButtonGroupValueChanged(ValueChangedEvent evt)
	{
		tracks.setCurrentTrack(trackSelect.getValueIndex());
	}
}
