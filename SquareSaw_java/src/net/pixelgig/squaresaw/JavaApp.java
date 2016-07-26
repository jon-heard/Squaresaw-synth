
package net.pixelgig.squaresaw;

import net.pixelgig.squaresaw.audio.Instrument;

public class JavaApp extends App
{
	@Override
	public Instrument _createInstrument()
	{
		return new JavaInstrument();
	}

	@Override
	public void _log(String toLog)
	{
		System.out.println(toLog);
	}
}
