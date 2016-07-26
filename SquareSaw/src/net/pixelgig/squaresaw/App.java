
package net.pixelgig.squaresaw;

import net.pixelgig.squaresaw.audio.Instrument;

public abstract class App
{
	public static App instance;

	public App()
	{
		instance = this;
	}

	public static Instrument createInstrument()
	{
		assert instance != null : "App referenced before initializing";
		return instance._createInstrument();
	}
	public static void log(String toLog)
	{
		assert instance != null : "App referenced before initializing";
		instance._log(toLog);
	}
	public static void log()
	{
		assert instance != null : "App referenced before initializing";
		instance._log("logged");
	}
	
	public abstract Instrument _createInstrument();
	public abstract void _log(String toLog);
}
