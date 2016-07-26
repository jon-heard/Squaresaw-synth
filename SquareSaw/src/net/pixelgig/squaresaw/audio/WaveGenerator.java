
package net.pixelgig.squaresaw.audio;

public abstract class WaveGenerator
{
	double tone = 0;

	public abstract int getType();
	public abstract void getSamples(
			int time, double[] buffer, double[] volumeBuffer,
			double[] cycleSizeBuffer, int start);
	public abstract void getSamples(
			int time, byte[] buffer, double[] volumeBuffer,
			double[] cycleSizeBuffer);

	public double getTone() { return tone; }
	public void setTone(double value)
	{
		if(value > .99) value = .99;
		tone = value;
	}
}
