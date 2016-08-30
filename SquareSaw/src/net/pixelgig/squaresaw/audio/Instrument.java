
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.audio;

import net.pixelgig.squaresaw.ui.Keyboard;


public abstract class Instrument implements Runnable
{
	public static int SAMPLE_RATE = 44100;
	public static int SAMPLE_COUNT = 1000;
	public static int BUFFER_SIZE = (int)(SAMPLE_COUNT*2f);

	protected static final int ENVELOPE_STAGE_ATTACK = 0;
	protected static final int ENVELOPE_STAGE_DECAY = 1;
	protected static final int ENVELOPE_STAGE_SUSTAIN = 2;
	protected static final int ENVELOPE_STAGE_RELEASE = 3;

	final double[] NOTE_CYCLESIZES;

	protected Thread thread;
	protected byte[] buffer = new byte[BUFFER_SIZE];
	protected double[] volumeBuffer = new double[SAMPLE_COUNT];
	protected double[] cycleSizeBuffer = new double[SAMPLE_COUNT];
	protected double[] VLFOvolumeBuffer = new double[SAMPLE_COUNT];
	protected double[] VLFOcycleSizeBuffer = new double[SAMPLE_COUNT];
	protected double[] FLFOvolumeBuffer = new double[SAMPLE_COUNT];
	protected double[] FLFOcycleSizeBuffer = new double[SAMPLE_COUNT];

	InstrumentSettings settings;
	int currentSample = 0;
	int note = -1;
	protected boolean running = false;
	boolean sustain = false;
	double envelope = 0;
	int envelopeStage = ENVELOPE_STAGE_RELEASE;
	int arpCurrentStep = 0;
	int arpBaseNote = -1;
	int arpCounter = 0;

	abstract public void stop();
	abstract public void pause();
	abstract public void resume();
	abstract protected void sendBuffer();
	
	public Instrument()
	{
		settings = new InstrumentSettings();
		NOTE_CYCLESIZES = new double[Keyboard.KEY_COUNT];
		for(int i = 0; i < Keyboard.KEY_COUNT; i++)
		{
			NOTE_CYCLESIZES[i] = SAMPLE_RATE / (440 * Math.pow(2, (i-45)/12.0));
		}
	
		java.util.Arrays.fill(volumeBuffer, 1);
}

	public void run()
	{
		generateSamples();
	}
	protected void generateSamples()
	{
		while(running)
		{
			if(sustain != settings.sustain)
			{
				//arpBaseNote = -1;
				envelopeStage = ENVELOPE_STAGE_RELEASE;
				sustain = settings.sustain;
			}

			if(arpBaseNote == -1)
			{
				java.util.Arrays.fill(buffer, (byte)0);
			}
			else
			{
				settings.VLFOWave.getSamples(
						currentSample, volumeBuffer, VLFOvolumeBuffer,
						VLFOcycleSizeBuffer, settings.VLFOStart);
//				FLFOWave.getSamples(
//						currentSample, cycleSizeBuffer, FLFOvolumeBuffer,
//						FLFOcycleSizeBuffer, VLFOStart);

				for(int i = 0; i < SAMPLE_COUNT; i++)
				{

					/// Arpeggiation
					if(settings.arpSpeed > 0)
					{
						arpCounter++;
						while(arpCounter > settings.arpSpeed)
						{
							arpCurrentStep++;
							if(arpCurrentStep >= settings.arpSteps.length)
							{
								arpCurrentStep = 0;
							}
							if(settings.arpPostFX)
							{
								currentSample = 0;
								envelope = 0;
								envelopeStage = ENVELOPE_STAGE_ATTACK;
							}
							if(arpBaseNote == -1)
							{
								note = -1;
							}
							else
							{
								note =	arpBaseNote +
										settings.arpSteps[arpCurrentStep];
							}
							arpCounter -= settings.arpSpeed;
						}
					}
					else
					{
						note = arpBaseNote;
					}


					/// Un-note
					if(note == -1)
					{
						volumeBuffer[i] = 0;
					}
					else
					{
						/// Volume and pitch buffers
						volumeBuffer[i] *= settings.volume * envelope;
						cycleSizeBuffer[i] = NOTE_CYCLESIZES[note];
						/// LFOs
						VLFOvolumeBuffer[i] = settings.VLFODepth;
						VLFOcycleSizeBuffer[i] = settings.VLFOSpeed;
						FLFOvolumeBuffer[i] = settings.FLFODepth;
						FLFOcycleSizeBuffer[i] = settings.FLFOSpeed;
						/// Envelope
						switch(envelopeStage)
						{
							case ENVELOPE_STAGE_ATTACK:
								envelope += settings.envelopeAttack;
								if(envelope > 1)
								{
									envelope = 1;
									envelopeStage = ENVELOPE_STAGE_DECAY;
								}
								break;
							case ENVELOPE_STAGE_DECAY:
								envelope -= settings.envelopeDecay;
								if(envelope < settings.envelopeSustain)
								{
									envelope = settings.envelopeSustain;
									envelopeStage = ENVELOPE_STAGE_SUSTAIN;
								}
								break;
							case ENVELOPE_STAGE_RELEASE:
								envelope -= settings.envelopeRelease;
								if(envelope < 0)
								{
									envelope = 0;
								}
								break;
						}
					}
				}
				/// Calculate samples
				settings.soundWave.getSamples(
						currentSample, buffer,
						volumeBuffer, cycleSizeBuffer);

				
				/// increase sample counter
				currentSample += SAMPLE_COUNT;
				if(currentSample > Integer.MAX_VALUE)// NOTE_CYCLESIZES[note]*5)
				{
					currentSample = 0;
				}
			}
			/// Submit calculated samples
			sendBuffer();
		}
	}

	public InstrumentSettings getSettings() { return settings; }
	public void setSettings(InstrumentSettings value)
	{
		settings = value;
	}
	public double getCurrentNote() { return note; }
	public void setNote(int value)
	{
		if(value != -1)
		{
			arpBaseNote = value;
			if(settings.arpSpeed == 0)
			{
				currentSample = 0;
				envelope = 0;
				envelopeStage = ENVELOPE_STAGE_ATTACK;
			}
			else
			{
				if(envelopeStage == ENVELOPE_STAGE_RELEASE)
				{
					currentSample = 0;
					envelope = 0;
					envelopeStage = ENVELOPE_STAGE_ATTACK;
				}
				if(settings.arpResetOnNote)
				{
					arpCurrentStep = 0;
					arpCounter = 0;
					note = arpBaseNote;
					if(settings.arpPostFX )
					{
						currentSample = 0;
						envelope = 0;
						envelopeStage = ENVELOPE_STAGE_ATTACK;
						arpBaseNote = value;
					}
				}
			}
		}
		else if(!settings.sustain)
		{
			//arpBaseNote = -1;
			envelopeStage = ENVELOPE_STAGE_RELEASE;
		}
	}
}
