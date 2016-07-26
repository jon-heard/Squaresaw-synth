
package net.pixelgig.squaresaw.audio;

import java.io.StringReader;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import net.pixelgig.squaresaw.CFG;

public class InstrumentSettings
{
	final double[] ATTACK_SCALE = {
			0,.00001,	.5,.00005,	.75,.0001,	.95,.0005,	1,1};
	final double[] DECAY_SCALE = {
			0,.00001,	.5,.00005,	.75,.0001,	.95,.0005,	1,1};
	final double[] RELEASE_SCALE = {
			0,.00001,	.5,.00005,	.75,.0001,	.95,.0005,	1,1};
	final double[] VLFO_SPEED_SCALE = {0,.001,	.5,25000,	1, 300000};
	final double[] VLFO_DEPTH_SCALE = {0,0,		1,1};
	final double[] VLFO_START_SCALE = {0,0,		1,100000};
	final double[] FLFO_SPEED_SCALE = {0,.001,	1,100000};
	final double[] FLFO_DEPTH_SCALE = {0,0,		1,1};
	final double[] FLFO_START_SCALE = {0,0,		1,100000};


	WaveGenerator soundWave = new WaveGenerator_Square();
	WaveGenerator VLFOWave = new WaveGenerator_Sine();
	WaveGenerator FLFOWave = new WaveGenerator_Sine();

	public double volume, ext_volume = .5;
	public boolean sustain = false;
	public boolean bendPitch = false;

	public double envelopeAttack, ext_envelopeAttack = 1;
	public double envelopeDecay, ext_envelopeDecay = .5;
	public double envelopeSustain = 1;
	public double envelopeRelease, ext_envelopeRelease = 1;

	public double VLFOSpeed, ext_VLFOSpeed = 0;
	public double VLFODepth, ext_VLFODepth = 0;
	public int VLFOStart;
	public double ext_VLFOStart = 0;

	public double FLFOSpeed, ext_FLFOSpeed = 0;
	public double FLFODepth, ext_FLFODepth = 0;//.01;
	public int FLFOStart;
	public double ext_FLFOStart = 0;

	int[] arpSteps = { 0, 4, 7 };
	int arpSpeed;
	double ext_arpSpeed = 0;
	boolean arpLoop = true;
	boolean arpPostFX = true;
	boolean arpResetOnNote = false;

	public InstrumentSettings()
	{
		this.soundWave = new WaveGenerator_Square();
		this.VLFOWave = new WaveGenerator_Sine();
		this.FLFOWave = new WaveGenerator_Sine();
		refreshValuesFromExternal();

	}
	public void refreshValuesFromExternal()
	{
		setVolume(ext_volume);
		setEnvelopeAttack(ext_envelopeAttack);
		setEnvelopeDecay(ext_envelopeDecay);
		setEnvelopeRelease(ext_envelopeRelease);
		setVLFODepth(ext_VLFODepth);
		setVLFOSpeed(ext_VLFOSpeed);
		setVLFOStart(ext_VLFOStart);
		setFLFODepth(ext_FLFODepth);
		setFLFOSpeed(ext_FLFOSpeed);
		setFLFOStart(ext_FLFOStart);
		setArpSpeed(ext_arpSpeed);
	}
	
	public WaveGenerator getSoundWave() { return soundWave; }
	public void setSoundWave(WaveGenerator value)
	{
		soundWave = value;
	}
	public WaveGenerator getVLFOWave() { return VLFOWave; }
	public void setVLFOWave(WaveGenerator value)
	{
		VLFOWave = value;
	}
	public WaveGenerator getFLFOWave() { return FLFOWave; }
	public void setFLFOWave(WaveGenerator value)
	{
		FLFOWave = value;
	}
	public boolean getSustain() { return sustain; }
	public void setSustain(boolean value)
	{
		this.sustain = value;
	}
	public boolean getBendPitch() { return bendPitch; }
	public void setBendPitch(boolean value)
	{
		this.bendPitch = value;
	}
	public double getVolume() { return ext_volume; }
	public void setVolume(double value)
	{
		volume = value*value*.5;
		ext_volume = value;
	}
	public double getEnvelopeAttack() { return ext_envelopeAttack; }
	public void setEnvelopeAttack(double value)
	{
		envelopeAttack = scaleValue(value, ATTACK_SCALE);
		ext_envelopeAttack = value;
	}
	public double getEnvelopeDecay() { return ext_envelopeDecay; }
	public void setEnvelopeDecay(double value)
	{
		envelopeDecay = scaleValue(value, DECAY_SCALE);
		ext_envelopeDecay = value;
	}
	public double getEnvelopeSustain() { return envelopeSustain; }
	public void setEnvelopeSustain(double value)
	{
		this.envelopeSustain = value;
	}
	public double getEnvelopeRelease() { return ext_envelopeRelease; }
	public void setEnvelopeRelease(double value)
	{
		envelopeRelease = scaleValue(value, RELEASE_SCALE);
		ext_envelopeRelease = value;
	}
	public double getVLFOSpeed() { return ext_VLFOSpeed; }
	public void setVLFOSpeed(double value)
	{
		VLFOSpeed = scaleValue(value, VLFO_SPEED_SCALE);
		ext_VLFOSpeed = value;
	}
	public double getVLFODepth() { return ext_VLFODepth; }
	public void setVLFODepth(double value)
	{
		VLFODepth = scaleValue(value, VLFO_DEPTH_SCALE);
		ext_VLFODepth = value;
	}
	public double getVLFOStart() { return ext_VLFOStart; }
	public void setVLFOStart(double value)
	{
		VLFOStart = (int)scaleValue(value, VLFO_START_SCALE);
		ext_VLFOStart = value;
	}
	public double getFLFOSpeed() { return ext_FLFOSpeed; }
	public void setFLFOSpeed(double value)
	{
		FLFOSpeed = scaleValue(value, FLFO_SPEED_SCALE);
		ext_FLFOSpeed = value;
	}
	public double getFLFODepth() { return ext_FLFODepth; }
	public void setFLFODepth(double value)
	{
		FLFODepth = scaleValue(value, FLFO_DEPTH_SCALE);
		ext_FLFODepth = value;
	}
	public double getFLFOStart() { return ext_FLFOStart; }
	public void setFLFOStart(double value)
	{
		FLFOStart = (int)scaleValue(value, FLFO_START_SCALE);
		ext_FLFOStart = value;
	}
	public void addArpStep(int step)
	{
		int[] newStepList = new int[arpSteps.length+1];
		for(int i = 0; i < arpSteps.length; i++)
		{
			newStepList[i] = arpSteps[i];
		}
		newStepList[arpSteps.length] = step;
		arpSteps = newStepList;
	}

	public int[] getArpSteps() { return arpSteps; }
	public void setArpSteps(int[] value)
	{
		arpSteps = value;
	}
	public boolean getArpResetOnNote() { return arpResetOnNote; }
	public void setArpResetOnNote(boolean value)
	{
		arpResetOnNote = value;
	}
	public double getArpSpeed() { return ext_arpSpeed; }
	public void setArpSpeed(double value)
	{
		assert value > 0: "Value must be between 0 and 1";
		assert value < 1: "Value must be between 0 and 1";
		this.arpSpeed = (int)((1-value) * 50000)+Instrument.SAMPLE_COUNT;
		if(value == 0)
		{
			this.arpSpeed = 0;
		}
		ext_arpSpeed = value;
	}
	public boolean getArpLoop() { return arpLoop; }
	public void setArpLoop(boolean value)
	{
		arpLoop = value;
	}
	public boolean getArpPostFX() { return arpPostFX; }
	public void setArpPostFX(boolean value)
	{
		arpPostFX = value;
	}


	public double scaleValue(double value, double[] scale)
	{
		assert value > 0: "Value must be between 0 and 1";
		assert value < 1: "Value must be between 0 and 1";
		for(int i = 2; i < scale.length; i+=2)
		{
			if(value <= scale[i])
			{
				double slope = (scale[i+1]-scale[i-1]) / (scale[i]-scale[i-2]);
				double intercept = scale[i+1] - (scale[i]*slope);
				return value * slope + intercept;
			}
		}
		return 0;
	}
	
	public boolean fromXml(String xml)
	{
		try
		{
			DocumentBuilderFactory dbFactory =
					DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(
					new InputSource(new StringReader(xml)));
			Node parent = doc.getElementsByTagName("instrumentPreset").item(0);
				ext_volume = Double.parseDouble(getProp(parent, "volume"));
				sustain = Boolean.parseBoolean(getProp(parent, "sustain_"));
				bendPitch = Boolean.parseBoolean(getProp(parent, "bendPitch"));
				double tone = Double.parseDouble(getProp(parent, "tone"));
				int soundWaveType = Integer.parseInt(getProp(parent, "sound"));
				soundWave = CFG.getWaveGenerator(soundWaveType, tone);
			parent = doc.getElementsByTagName("envelope").item(0);
				ext_envelopeAttack = Double.parseDouble(
						getProp(parent, "attack"));
				ext_envelopeDecay = Double.parseDouble(
						getProp(parent, "decay"));
				envelopeSustain = Double.parseDouble(
						getProp(parent, "sustain"));
				ext_envelopeRelease = Double.parseDouble(
						getProp(parent, "release"));
			parent = doc.getElementsByTagName("vlfo").item(0);
				ext_VLFOSpeed = Double.parseDouble(getProp(parent, "speed"));
				ext_VLFODepth = Double.parseDouble(getProp(parent, "depth"));
				ext_VLFOStart = Double.parseDouble(getProp(parent, "start"));
				int vlfoWaveType = Integer.parseInt(getProp(parent, "wave"));
				VLFOWave = CFG.getWaveGenerator(vlfoWaveType, tone);
			parent = doc.getElementsByTagName("flfo").item(0);
				ext_FLFOSpeed = Double.parseDouble(getProp(parent, "speed"));
				ext_FLFODepth = Double.parseDouble(getProp(parent, "depth"));
				ext_FLFOStart = Double.parseDouble(getProp(parent, "start"));
				int flfoWaveType = Integer.parseInt(getProp(parent, "wave"));
				FLFOWave = CFG.getWaveGenerator(flfoWaveType, tone);
			parent = doc.getElementsByTagName("arpeggio").item(0);
				ext_arpSpeed = Double.parseDouble(getProp(parent, "speed"));
				arpLoop = Boolean.parseBoolean(getProp(parent, "loop"));
				arpPostFX = Boolean.parseBoolean(getProp(parent, "post"));
				arpResetOnNote = Boolean.parseBoolean(getProp(parent, "reset"));
			parent = doc.getElementsByTagName("steps").item(0);
				Element steps = (Element)parent;
				NodeList stepList = steps.getElementsByTagName("step");
				arpSteps = new int[stepList.getLength()];
				for(int i = 0; i < arpSteps.length; i++)
				{
					Node step = stepList.item(i);
					String tmpbuf = step.getChildNodes().item(0).getNodeValue();
					arpSteps[i] = Integer.parseInt(tmpbuf);
				}
			refreshValuesFromExternal();
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public String getProp(Node parent, String name)
	{
		String result = "0";
		NodeList props = ((Element)parent).getElementsByTagName(name);
		if(props.getLength() != 1)
		{
			return result;
		}
		Node prop = props.item(0);
		props = prop.getChildNodes();
		if(props.getLength() < 1)
		{
			return result;
		}
		result = props.item(0).getNodeValue();
		return result;
	}
	public String toXml()
	{
		String result = "";
		result += "<instrumentPreset>\r\n";
			result += "\t<volume>" + ext_volume + "</volume>\r\n";
			result += "\t<sustain_>" + sustain + "</sustain_>\r\n";
			result += "\t<bendPitch>" + bendPitch + "</bendPitch>\r\n";
			result += "\t<tone>" + soundWave.getTone() + "</tone>\r\n";
			result += "\t<sound>" + soundWave.getType() + "</sound>\r\n";
			result += "\t<envelope>\r\n";
				result += "\t\t<attack>" + ext_envelopeAttack + "</attack>\r\n";
				result += "\t\t<sustain>" + envelopeSustain + "</sustain>\r\n";
				result += "\t\t<decay>" + ext_envelopeDecay + "</decay>\r\n";
				result += "\t\t<release>" + ext_envelopeRelease + "</release>\r\n";
			result += "\t</envelope>\r\n";
			result += "\t<vlfo>\r\n";
				result += "\t\t<speed>" + ext_VLFOSpeed + "</speed>\r\n";
				result += "\t\t<depth>" + ext_VLFODepth + "</depth>\r\n";
				result += "\t\t<wave>" + VLFOWave.getType() + "</wave>\r\n";
				result += "\t\t<start>" + ext_VLFOStart + "</start>\r\n";
			result += "\t</vlfo>\r\n";
			result += "\t<flfo>\r\n";
				result += "\t\t<speed>" + ext_FLFOSpeed + "</speed>\r\n";
				result += "\t\t<depth>" + ext_FLFODepth + "</depth>\r\n";
				result += "\t\t<wave>" + FLFOWave.getType() + "</wave>\r\n";
				result += "\t\t<start>" + ext_FLFOStart + "</start>\r\n";
			result += "\t</flfo>\r\n";
			result += "\t<arpeggio>\r\n";
				result += "\t\t<speed>" + ext_arpSpeed + "</speed>\r\n";
				result += "\t\t<loop>" + arpLoop + "</loop>\r\n";
				result += "\t\t<post>" + arpPostFX + "</post>\r\n";
				result += "\t\t<reset>" + arpResetOnNote + "</reset>\r\n";
				result += "\t\t<steps>\r\n";
				for(int i = 0; i < arpSteps.length; i++)
				{
					result += "\t\t\t<step>" + arpSteps[i] + "</step>\r\n";
				}
				result += "\t\t</steps>\r\n";
			result += "\t</arpeggio>\r\n";
		result += "</instrumentPreset>\r\n";
		return result;
	}
}
