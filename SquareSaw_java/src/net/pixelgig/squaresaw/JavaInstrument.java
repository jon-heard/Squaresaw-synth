
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import net.pixelgig.squaresaw.audio.Instrument;

public class JavaInstrument extends Instrument
{
	SourceDataLine line = null;
	boolean paused = false;

	public JavaInstrument()
	{
		AudioFormat format = new AudioFormat(
				44100,
				16, 1, true, false);
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		try
		{
			line = (SourceDataLine)AudioSystem.getLine(info);
			line.open(format, buffer.length);
			running = true;
			line.start();
			thread = new Thread(this);
			thread.start();
		}
		catch(Exception e)
		{
			System.out.println("ERROR gathering the data line.");
		}		
	}
	@Override
	public void finalize()
	{
		running = false;
		line.stop();
	}


	@Override
	protected void sendBuffer()
	{
		line.write(buffer, 0, Instrument.BUFFER_SIZE);
	}

	@Override
	public void stop()
	{
		finalize();
	}
	@Override
	public void pause()
	{
		running = false;
		line.stop();
		paused = true;
	}
	@Override
	public void resume()
	{
		if(paused)
		{
			paused = false;
			line.start();
			running = true;
			thread = new Thread(this);
			thread.start();
		}
	}
}
