
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.audio;

import net.pixelgig.squaresaw.CFG;

public class WaveGenerator_Sine extends WaveGenerator
{
	@Override
	public int getType() { return CFG.WAVE_SINE; }
	@Override
	public void getSamples(
			int time, double[] buffer, double[] volumeBuffer,
			double[] cycleSizeBuffer, int start)
	{
		time -= start;
		int length = buffer.length;
		for(int i = 0; i < length; i++)
		{
			if(time < start)
			{
				buffer[i] = 1;
			}
			else
			{
				buffer[i] = Math.sin(2.0 * Math.PI * time / cycleSizeBuffer[i])
						* volumeBuffer[i] + 1;
			}
			time++;
		}
	}
	@Override
	public void getSamples(
			int time, byte[] buffer, double[] volumeBuffer,
			double[] cycleSizeBuffer)
	{
		int length = buffer.length/2;
		for(int i = 0; i < length; i++)
		{
			double result = Math.sin(2.0 * Math.PI * time / cycleSizeBuffer[i])
					* volumeBuffer[i];			
			short pre = (short)(result*4096*volumeBuffer[i]);
			buffer[i*2] = (byte)(pre & 0xff);
			buffer[i*2+1] = (byte)((pre >> 8) & 0xff);
			time++;
		}
	}
}
