
package net.pixelgig.squaresaw.ui.instrument;
import net.pixelgig.squaresaw.audio.Instrument;
import net.pixelgig.squaresaw.ui.*;
import net.pixelgig.squaresaw.ui.Button.ClickEvent;
import net.pixelgig.squaresaw.ui.Knob.KnobEvent;

public class ToneInstrumentPanel extends InstrumentPanel
{
	public static final int WIDTH = 132;

	static int BUTTON_TONE_MOD = 0;
	static int KNOB_TONE = 0;

	public ToneInstrumentPanel(int x, Instrument track)
	{
		super(x, WIDTH, track);

		buttons = new Button[1];
		knobs = new Knob[1];

		buttons[BUTTON_TONE_MOD] = new Button(
				"Tone Mod", new String[]{"Basic", "Advanced"});
			buttons[BUTTON_TONE_MOD].setPosition(8, 173);
			buttons[BUTTON_TONE_MOD].addListener(this);
			addActor(buttons[BUTTON_TONE_MOD]);
			addActor(new ConstructionNote(10, 175, "Toggle advanced/basic mode (no advanced mode yet)"));
		addActor(new TextActor("Square wave", 10, 130));
		knobs[KNOB_TONE] = new Knob("inst>Tone");
			knobs[KNOB_TONE].setPosition(40, 65);
			knobs[KNOB_TONE].addListener(this);
			addActor(knobs[KNOB_TONE]);

			setTrack(track);
	}

	@Override
	public void setTrack(Instrument track)
	{
		super.setTrack(track);
		knobs[KNOB_TONE].setValue(track.getSettings().getSoundWave().getTone());
	}

	@Override
	public void onKnobValueChanged(KnobEvent evt)
	{
		Knob source = ((Knob)evt.getSource());
		if(source == knobs[KNOB_TONE])
		{
			track.getSettings().getSoundWave().setTone(source.getValue());
		}
	}
	@Override
	public void onButtonClick(ClickEvent evt)
	{
		Button source = ((Button)evt.getSource());
		if(source == buttons[BUTTON_TONE_MOD])
		{
		}
	}
}
