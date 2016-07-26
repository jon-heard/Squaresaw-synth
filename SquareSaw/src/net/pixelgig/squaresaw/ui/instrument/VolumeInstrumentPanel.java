
package net.pixelgig.squaresaw.ui.instrument;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.pixelgig.squaresaw.CFG;
import net.pixelgig.squaresaw.audio.Instrument;
import net.pixelgig.squaresaw.audio.WaveGenerator;
import net.pixelgig.squaresaw.ui.*;
import net.pixelgig.squaresaw.ui.Button.ClickEvent;
import net.pixelgig.squaresaw.ui.Knob.KnobEvent;

public class VolumeInstrumentPanel extends InstrumentPanel
{
	public static final int WIDTH = 310;

	static int BUTTON_VOLUME_MOD = 0;
	static int BUTTON_VLFO_WAVE = 1;
	static int KNOB_ENV_ATTACK = 0;
	static int KNOB_ENV_DECAY = 1;
	static int KNOB_ENV_SUSTAIN = 2;
	static int KNOB_ENV_RELEASE = 3;
	static int KNOB_VLFO_SPEED = 4;
	static int KNOB_VLFO_DEPTH = 5;
	static int KNOB_VLFO_START = 6;
	
	public VolumeInstrumentPanel(int x, Instrument track)
	{
		super(x, WIDTH, track);
		
		buttons = new Button[2];
		knobs = new Knob[7];

		buttons[BUTTON_VOLUME_MOD] = new Button(
				"Volume Mod", new String[]{"Basic", "Advanced"});
			buttons[BUTTON_VOLUME_MOD].setPosition(8, 173);
			buttons[BUTTON_VOLUME_MOD].addListener(this);
			addActor(buttons[BUTTON_VOLUME_MOD]);
			addActor(new ConstructionNote(10, 175, "Toggle advanced/basic mode (no advanced mode yet)"));
		UIPanel envelope = new UIPanel(10, 10, 157, 160);
			addActor(envelope);
			envelope.addActor(new TextActor("Envelope", 15, 145));
			knobs[KNOB_ENV_ATTACK] = new Knob("env>Attack");
				knobs[KNOB_ENV_ATTACK].setPosition(13, 77);
				knobs[KNOB_ENV_ATTACK].addListener(this);
				envelope.addActor(knobs[KNOB_ENV_ATTACK]);
			knobs[KNOB_ENV_RELEASE] = new Knob("env>Release");
				knobs[KNOB_ENV_RELEASE].setPosition(90, 77);
				knobs[KNOB_ENV_RELEASE].addListener(this);
				envelope.addActor(knobs[KNOB_ENV_RELEASE]);
			knobs[KNOB_ENV_DECAY] = new Knob("env>Decay");
				knobs[KNOB_ENV_DECAY].setPosition(13, 10);
				knobs[KNOB_ENV_DECAY].addListener(this);
				envelope.addActor(knobs[KNOB_ENV_DECAY]);
			knobs[KNOB_ENV_SUSTAIN] = new Knob("env>Sustain");
				knobs[KNOB_ENV_SUSTAIN].setPosition(90, 10);
				knobs[KNOB_ENV_SUSTAIN].addListener(this);
				envelope.addActor(knobs[KNOB_ENV_SUSTAIN]);
		UIPanel vlfo = new UIPanel(170, 10, 130, 160);
			addActor(vlfo);
			vlfo.addActor(new TextActor("Volume LFO", 15, 145));
			knobs[KNOB_VLFO_SPEED] = new Knob("vlfo>Speed");
				knobs[KNOB_VLFO_SPEED].setPosition(10, 77);
				knobs[KNOB_VLFO_SPEED].addListener(this);
				vlfo.addActor(knobs[KNOB_VLFO_SPEED]);
			knobs[KNOB_VLFO_DEPTH] = new Knob("vlfo>Depth");
				knobs[KNOB_VLFO_DEPTH].setPosition(70, 77);
				knobs[KNOB_VLFO_DEPTH].addListener(this);
				vlfo.addActor(knobs[KNOB_VLFO_DEPTH]);
			buttons[BUTTON_VLFO_WAVE] = new Button(
					"vlfo>Wave",
					new String[]{"3", "4", "8","0", "2"},
					11*4, 11*4);
				buttons[BUTTON_VLFO_WAVE].setPosition(17, 16);
				buttons[BUTTON_VLFO_WAVE].addListener(this);
				TextureRegion[] waves = new TextureRegion[5];
					waves[0] = UIDraw.drawWaveForm(3);
					waves[1] = UIDraw.drawWaveForm(4);
					waves[2] = UIDraw.drawWaveForm(8);
					waves[3] = UIDraw.drawWaveForm(0);
					waves[4] = UIDraw.drawWaveForm(2);
				buttons[BUTTON_VLFO_WAVE].setOptionPictures(waves);
				vlfo.addActor(buttons[BUTTON_VLFO_WAVE]);
			knobs[KNOB_VLFO_START] = new Knob("vlfo>Start");
				knobs[KNOB_VLFO_START].setPosition(70, 10);
				knobs[KNOB_VLFO_START].addListener(this);
				vlfo.addActor(knobs[KNOB_VLFO_START]);

				setTrack(track);
	}

	@Override
	public void setTrack(Instrument track)
	{
		super.setTrack(track);
		switch(track.getSettings().getVLFOWave().getType())
		{
			case CFG.WAVE_SINE: buttons[BUTTON_VLFO_WAVE].setValue(0); break;
			case CFG.WAVE_SAW: buttons[BUTTON_VLFO_WAVE].setValue(1); break;
			case CFG.WAVE_SAW_REVERSE: buttons[BUTTON_VLFO_WAVE].setValue(2); break;
			case CFG.WAVE_SQUARE: buttons[BUTTON_VLFO_WAVE].setValue(3); break;
			case CFG.WAVE_NOISE: buttons[BUTTON_VLFO_WAVE].setValue(4); break;
		}
		knobs[KNOB_ENV_ATTACK].setValue(track.getSettings().getEnvelopeAttack());
		knobs[KNOB_ENV_DECAY].setValue(track.getSettings().getEnvelopeDecay());
		knobs[KNOB_ENV_RELEASE].setValue(track.getSettings().getEnvelopeRelease());
		knobs[KNOB_ENV_SUSTAIN].setValue(track.getSettings().getEnvelopeSustain());
		knobs[KNOB_VLFO_SPEED].setValue(track.getSettings().getVLFOSpeed());
		knobs[KNOB_VLFO_DEPTH].setValue(track.getSettings().getVLFODepth());
		knobs[KNOB_VLFO_START].setValue(track.getSettings().getVLFOStart());
	}

	@Override
	public void onKnobValueChanged(KnobEvent evt)
	{
		Knob source = ((Knob)evt.getSource());
		if(source == knobs[KNOB_ENV_ATTACK])
		{
			track.getSettings().setEnvelopeAttack(source.getValue());
		}
		if(source == knobs[KNOB_ENV_DECAY])
		{
			track.getSettings().setEnvelopeDecay(source.getValue());
		}
		if(source == knobs[KNOB_ENV_SUSTAIN])
		{
			track.getSettings().setEnvelopeSustain(source.getValue());
		}
		if(source == knobs[KNOB_ENV_RELEASE])
		{
			track.getSettings().setEnvelopeRelease(source.getValue());
		}
		if(source == knobs[KNOB_VLFO_SPEED])
		{
			track.getSettings().setVLFOSpeed(source.getValue());
		}
		if(source == knobs[KNOB_VLFO_DEPTH])
		{
			track.getSettings().setVLFODepth(source.getValue());
		}
		if(source == knobs[KNOB_VLFO_START])
		{
			track.getSettings().setVLFOStart(source.getValue());
		}
	}
	@Override
	public void onButtonClick(ClickEvent evt)
	{
		Button source = ((Button)evt.getSource());
		if(source == buttons[BUTTON_VOLUME_MOD])
		{
		}
		if(source == buttons[BUTTON_VLFO_WAVE])
		{
			int selected = Integer.parseInt(source.getSelectedOption());
			WaveGenerator generator = CFG.getWaveGenerator(selected, 0);
			track.getSettings().setVLFOWave(generator);
		}
	}
}
