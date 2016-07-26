
package net.pixelgig.squaresaw.ui.instrument;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.pixelgig.squaresaw.CFG;
import net.pixelgig.squaresaw.audio.Instrument;
import net.pixelgig.squaresaw.audio.WaveGenerator;
import net.pixelgig.squaresaw.ui.*;
import net.pixelgig.squaresaw.ui.Button.ClickEvent;
import net.pixelgig.squaresaw.ui.Knob.KnobEvent;

public class FrequencyInstrumentPanel extends InstrumentPanel
{
	public static final int WIDTH = 150;

	static int BUTTON_FREQUENCY_MOD = 0;
	static int BUTTON_FLFO_WAVE = 1;
	static int KNOB_FLFO_SPEED = 0;
	static int KNOB_FLFO_DEPTH = 1;
	static int KNOB_FLFO_START = 2;
	
	public FrequencyInstrumentPanel(int x, Instrument track)
	{
		super(x, WIDTH, track);

		buttons = new Button[2];
		knobs = new Knob[3];

		buttons[BUTTON_FREQUENCY_MOD] = new Button(
				"Pitch Mod", new String[]{"Basic", "Advanced"});
			buttons[BUTTON_FREQUENCY_MOD].setPosition(17, 173);
			buttons[BUTTON_FREQUENCY_MOD].addListener(this);
			addActor(buttons[BUTTON_FREQUENCY_MOD]);
			addActor(new ConstructionNote(
					10, 175,
					"Toggle advanced/basic mode (no advanced mode yet)"));
		UIPanel flfo = new UIPanel(10, 10, 130, 160);
			addActor(flfo);
			flfo.addActor(new TextActor("Freq. LFO", 15, 145));
			knobs[KNOB_FLFO_SPEED] = new Knob("flfo>Speed");
				knobs[KNOB_FLFO_SPEED].setPosition(10, 77);
				knobs[KNOB_FLFO_SPEED].addListener(this);
				flfo.addActor(knobs[KNOB_FLFO_SPEED]);
				flfo.addActor(new ConstructionNote(10, 77, "Fix FLFO"));
			knobs[KNOB_FLFO_DEPTH] = new Knob("flfo>Depth");
				knobs[KNOB_FLFO_DEPTH].setPosition(70, 77);
				knobs[KNOB_FLFO_DEPTH].addListener(this);
				flfo.addActor(knobs[KNOB_FLFO_DEPTH]);
				flfo.addActor(new ConstructionNote(70, 77, "Fix FLFO"));
			buttons[BUTTON_FLFO_WAVE] = new Button(
					"flfo>Wave",
					new String[]{"3", "4", "8","0", "2"},
					11*4, 11*4);
				buttons[BUTTON_FLFO_WAVE].setPosition(17, 16);
				buttons[BUTTON_FLFO_WAVE].addListener(this);
				TextureRegion[] waves = new TextureRegion[5];
					waves[0] = UIDraw.drawWaveForm(3);
					waves[1] = UIDraw.drawWaveForm(4);
					waves[2] = UIDraw.drawWaveForm(8);
					waves[3] = UIDraw.drawWaveForm(0);
					waves[4] = UIDraw.drawWaveForm(2);
				buttons[BUTTON_FLFO_WAVE].setOptionPictures(waves);
				flfo.addActor(buttons[BUTTON_FLFO_WAVE]);
				flfo.addActor(new ConstructionNote(17, 16, "Fix FLFO"));
			knobs[KNOB_FLFO_START] = new Knob("flfo>Start");
				knobs[KNOB_FLFO_START].setPosition(70, 10);
				knobs[KNOB_FLFO_START].addListener(this);
				flfo.addActor(knobs[KNOB_FLFO_START]);
				flfo.addActor(new ConstructionNote(70, 10, "Fix FLFO"));

				setTrack(track);
	}
	
	@Override
	public void setTrack(Instrument track)
	{
		super.setTrack(track);
		switch(track.getSettings().getFLFOWave().getType())
		{
			case CFG.WAVE_SINE: buttons[BUTTON_FLFO_WAVE].setValue(0); break;
			case CFG.WAVE_SAW: buttons[BUTTON_FLFO_WAVE].setValue(1); break;
			case CFG.WAVE_SAW_REVERSE: buttons[BUTTON_FLFO_WAVE].setValue(2); break;
			case CFG.WAVE_SQUARE: buttons[BUTTON_FLFO_WAVE].setValue(3); break;
			case CFG.WAVE_NOISE: buttons[BUTTON_FLFO_WAVE].setValue(4); break;
		}
		knobs[KNOB_FLFO_SPEED].setValue(track.getSettings().getFLFOSpeed());
		knobs[KNOB_FLFO_DEPTH].setValue(track.getSettings().getFLFODepth());
		knobs[KNOB_FLFO_START].setValue(track.getSettings().getFLFOStart());
	}

	@Override
	public void onKnobValueChanged(KnobEvent evt)
	{
		Knob source = ((Knob)evt.getSource());
		if(source == knobs[KNOB_FLFO_SPEED])
		{
			track.getSettings().setFLFOSpeed(source.getValue());
		}
		if(source == knobs[KNOB_FLFO_DEPTH])
		{
			track.getSettings().setFLFODepth(source.getValue());
		}
		if(source == knobs[KNOB_FLFO_START])
		{
			track.getSettings().setFLFOStart(source.getValue());
		}
	}
	@Override
	public void onButtonClick(ClickEvent evt)
	{
		Button source = ((Button)evt.getSource());
		if(source == buttons[BUTTON_FREQUENCY_MOD])
		{
		}
		if(source == buttons[BUTTON_FLFO_WAVE])
		{
			int selected = Integer.parseInt(source.getSelectedOption());
			WaveGenerator generator = CFG.getWaveGenerator(selected, 0);
			track.getSettings().setFLFOWave(generator);
		}
	}
}
