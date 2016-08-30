
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.audio;

import net.pixelgig.squaresaw.CFG;

public class WaveGenerator_Triangle extends WaveGenerator
{
	@Override
	public int getType() { return CFG.WAVE_TRIANGLE; }
	@Override
	public void getSamples(
			int time, double[] buffer, double[] volumeBuffer,
			double[] cycleSizeBuffer, int start)
	{
	}
	@Override
	public void getSamples(
			int time, byte[] buffer, double[] volumeBuffer,
			double[] cycleSizeBuffer)
	{
		int length = buffer.length/2;
		for(int i = 0; i < length; i++)
		{
			double result = (time/cycleSizeBuffer[i]*2);
			if(((int)result) % 2 == 0)
			{
				result %= 1;
				result = 1 - result*2;
			}
			else
			{
				result %= 1;
				result = result*2 - 1;
			}
			short pre = (short)(result*2048*volumeBuffer[i]);
			buffer[i*2] = (byte)(pre & 0xff);
			buffer[i*2+1] = (byte)((pre >> 8) & 0xff);
			time++;
		}
	}
}
