
package net.pixelgig.squaresaw.ui.instrument;

import net.pixelgig.squaresaw.audio.Instrument;
import net.pixelgig.squaresaw.ui.*;
import net.pixelgig.squaresaw.ui.Knob.KnobEvent;

public class EchoInstrumentPanel extends InstrumentPanel
{
	public static final int WIDTH = 72;

	static int KNOB_ECHO_VOLUME = 0;
	static int KNOB_ECHO_DELAY = 1;

	public EchoInstrumentPanel(int x, Instrument track)
	{
		super(x, WIDTH, track);

		knobs = new Knob[2];

		addActor(new TextActor("Echo", 16, 199));
		knobs[KNOB_ECHO_VOLUME] = new Knob("echo>Volume");
			knobs[KNOB_ECHO_VOLUME].setPosition(10, 115);
			knobs[KNOB_ECHO_VOLUME].addListener(this);
			addActor(knobs[KNOB_ECHO_VOLUME]);
			addActor(new ConstructionNote(10, 115, "Implement echo feature"));
		knobs[KNOB_ECHO_DELAY] = new Knob("echo>Delay");
			knobs[KNOB_ECHO_DELAY].setPosition(10, 40);
			knobs[KNOB_ECHO_DELAY].addListener(this);
			addActor(knobs[KNOB_ECHO_DELAY]);
			addActor(new ConstructionNote(10, 40, "Implement echo feature"));

			setTrack(track);
	}
	
	@Override
	public void setTrack(Instrument track)
	{
		super.setTrack(track);
	}

	@Override
	public void onKnobValueChanged(KnobEvent evt)
	{
		Knob source = ((Knob)evt.getSource());
		if(source == knobs[KNOB_ECHO_DELAY])
		{
		}
		if(source == knobs[KNOB_ECHO_VOLUME])
		{
		}
	}
}
