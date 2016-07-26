
package net.pixelgig.squaresaw.ui.instrument;

import net.pixelgig.squaresaw.audio.Instrument;
import net.pixelgig.squaresaw.ui.*;
import net.pixelgig.squaresaw.ui.Button.ClickEvent;
import net.pixelgig.squaresaw.ui.Knob.KnobEvent;

public class InstrumentPanel extends UIPanel implements
	Button.ButtonListener,
	Knob.KnobListener
{
	Button[] buttons;
	Knob[] knobs;
	Instrument track;
	
	public InstrumentPanel(int x, int width, Instrument track)
	{
		super(x, 0, width, InstrumentGroup.HEIGHT);
		this.track = track;
	}
	
	public void setTrack(Instrument track)
	{
		this.track = track;
	}

	@Override
	public void onKnobValueChanged(KnobEvent evt)
	{
	}
	@Override
	public void onButtonClick(ClickEvent evt)
	{
	}
}
