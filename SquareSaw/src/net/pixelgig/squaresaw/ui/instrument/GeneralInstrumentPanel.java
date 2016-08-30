
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.ui.instrument;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.pixelgig.squaresaw.CFG;
import net.pixelgig.squaresaw.Song;
import net.pixelgig.squaresaw.audio.Instrument;
import net.pixelgig.squaresaw.audio.WaveGenerator;
import net.pixelgig.squaresaw.ui.*;
import net.pixelgig.squaresaw.ui.Button.ClickEvent;
import net.pixelgig.squaresaw.ui.Knob.KnobEvent;

public class GeneralInstrumentPanel extends InstrumentPanel
{
	public static final int WIDTH = 132;

	static int BUTTON_INSTRUMENT = 0;
	static int BUTTON_SOUND = 1;
	static int BUTTON_PITCH_BEND = 2;
	static int BUTTON_SUSTAIN = 3;
	static int KNOB_VOLUME = 0;

	Song tracks;

	public GeneralInstrumentPanel(int x, Song tracks)
	{
		super(x, WIDTH, tracks.getCurrentTrack());

		this.tracks = tracks;
		buttons = new Button[4];
		knobs = new Knob[1];

		// TODO: FEATURE: Remove this label and implement
		Label l = new Label("Instrument");
			l.setPosition(WIDTH/2, 200);
			l.setAlignment(Label.ALIGNMENT_CENTER);
			addActor(l);
		
		buttons[BUTTON_INSTRUMENT] = new Button("Instrument", "Presets");
			buttons[BUTTON_INSTRUMENT].setPosition(8, 173);
			buttons[BUTTON_INSTRUMENT].addListener(this);
			// TODO: FEATURE: uncomment and implement
			//addActor(buttons[BUTTON_INSTRUMENT]);
			//addActor(new ConstructionNote(10, 175, "Dialog to: open, save, saveas, rename, delete instruments"));
		buttons[BUTTON_SOUND] = new Button(
				"inst>Sound",
				new String[]{"0", "1", "2", "3", "4"},
				11*4, 11*4);
			buttons[BUTTON_SOUND].setPosition(12, 108);
			buttons[BUTTON_SOUND].addListener(this);
			TextureRegion[] waves1 = new TextureRegion[8];
				for(int i = 0; i < 8; i++)
				{
					waves1[i] = UIDraw.drawWaveForm(i);
				}
			buttons[BUTTON_SOUND].setOptionPictures(waves1);
			addActor(buttons[BUTTON_SOUND]);
		knobs[KNOB_VOLUME]= new Knob("inst>Volume");
			knobs[KNOB_VOLUME].setPosition(70, 102);
			knobs[KNOB_VOLUME].addListener(this);
			addActor(knobs[KNOB_VOLUME]);
		buttons[BUTTON_PITCH_BEND] = new Button(
				"inst>Pitch bend>", new String[]{"Bend", "Bend"}, 22*4, 6*4);
			buttons[BUTTON_PITCH_BEND].setPosition(24, 58);
			buttons[BUTTON_PITCH_BEND].addListener(this);
			// TODO: FEATURE: uncomment and implement
			//addActor(buttons[BUTTON_PITCH_BEND]);
			//addActor(new ConstructionNote(25, 58, "activate a mode in keyboard object that notifies of offset from down instead of notes"));
		buttons[BUTTON_SUSTAIN] = new Button(
				"inst>Sustain>",
				new String[]{"Sustain", "Sustain"}, 22*4, 6*4);
			buttons[BUTTON_SUSTAIN].setPosition(24, 32);
			buttons[BUTTON_SUSTAIN].addListener(this);
			addActor(buttons[BUTTON_SUSTAIN]);

			setTrack(track);
	}

	@Override
	public void setTrack(Instrument track)
	{
		super.setTrack(track);
		buttons[BUTTON_SOUND].setValue(
				track.getSettings().getSoundWave().getType());
		knobs[KNOB_VOLUME].setValue(
				track.getSettings().getVolume());
		buttons[BUTTON_PITCH_BEND].setValue(
				track.getSettings().getBendPitch() ? 1 : 0);
		buttons[BUTTON_SUSTAIN].setValue(
				track.getSettings().getSustain() ? 1 : 0);
	}

	@Override
	public void onKnobValueChanged(KnobEvent evt)
	{
		Knob source = ((Knob)evt.getSource());
		if(source == knobs[KNOB_VOLUME])
		{
			track.getSettings().setVolume(source.getValue());
		}
	}
	@Override
	public void onButtonClick(ClickEvent evt)
	{
		Button source = ((Button)evt.getSource());
		if(source == buttons[BUTTON_INSTRUMENT])
		{
			new PresetPanel(this.getParent(), tracks);
		}
		if(source == buttons[BUTTON_SOUND])
		{
			int selected = Integer.parseInt(source.getSelectedOption());
			double tone = track.getSettings().getSoundWave().getTone();
			WaveGenerator generator = CFG.getWaveGenerator(selected, tone);
			track.getSettings().setSoundWave(generator);
			((InstrumentGroup)this.getParent()).refreshPanelPositions();
		}
		if(source == buttons[BUTTON_PITCH_BEND])
		{
			track.getSettings().setBendPitch(source.getSelectedIndex() == 1);
		}
		if(source == buttons[BUTTON_SUSTAIN])
		{
			track.getSettings().setSustain(source.getSelectedIndex() == 1);
		}
	}
}
