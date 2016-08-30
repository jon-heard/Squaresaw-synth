
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw;

import net.pixelgig.squaresaw.App;
import net.pixelgig.squaresaw.audio.Instrument;

public class AndroidApp extends App
{
	@Override
	public Instrument _createInstrument()
	{
		return new AndroidInstrument();
	}

	@Override
	public void _log(String toLog)
	{
		android.util.Log.d("SSLog", toLog);
	}
}
