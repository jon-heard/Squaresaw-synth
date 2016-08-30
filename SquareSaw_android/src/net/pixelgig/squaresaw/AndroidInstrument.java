
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw;

import net.pixelgig.squaresaw.audio.Instrument;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class AndroidInstrument extends Instrument implements Runnable
{
	public AudioTrack track = null;

	public AndroidInstrument()
	{
		int minSize =AudioTrack.getMinBufferSize(
				SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO,
				AudioFormat.ENCODING_PCM_16BIT );
		track = new AudioTrack( AudioManager.STREAM_MUSIC, SAMPLE_RATE, 
				AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
				minSize, AudioTrack.MODE_STREAM);

		running = true;
		thread = new Thread(this);
		track.play(); 
		thread.start();
	}
	protected void sendBuffer()
	{
		track.write(buffer, 0, buffer.length);
	}
	@Override
	public void finalize()
	{
		running = false;
		track.stop();
	}
	
	public void stop()
	{
		finalize();
	}
	public void pause()
	{
		if(running)
		{
			running = false;
		}
	}
	public void resume()
	{
		if(!running)
		{
			running = true;
			thread = new Thread(this);
			thread.start();
		}
	}
}
