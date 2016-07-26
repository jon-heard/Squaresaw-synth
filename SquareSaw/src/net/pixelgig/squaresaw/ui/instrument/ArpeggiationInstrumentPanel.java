
package net.pixelgig.squaresaw.ui.instrument;

import com.badlogic.gdx.scenes.scene2d.Group;

import net.pixelgig.squaresaw.CFG;
import net.pixelgig.squaresaw.audio.Instrument;
import net.pixelgig.squaresaw.ui.*;
import net.pixelgig.squaresaw.ui.Button.ClickEvent;
import net.pixelgig.squaresaw.ui.Keyboard.KeyEvent;
import net.pixelgig.squaresaw.ui.Keyboard.KeyboardListener;
import net.pixelgig.squaresaw.ui.Knob.KnobEvent;

public class ArpeggiationInstrumentPanel extends InstrumentPanel implements
	KeyboardListener
{
	public static final int WIDTH = 132;

	static int BUTTON_ARP_REC = 0;
	static int BUTTON_ARP_LOOP = 1;
	static int BUTTON_ARP_RESET = 2;
	static int BUTTON_ARP_POST = 3;
	static int KNOB_ARP_SPEED = 0;

	Group arpeggiationList;
	boolean recording = false;
	int baseNote = -1;
	double arpSpeed = 0;

	public ArpeggiationInstrumentPanel(int x, Instrument track, Keyboard keys)
	{
		super(x, WIDTH, track);

		buttons = new Button[4];
		knobs = new Knob[1];
		keys.addListener(this);

		addActor(new TextActor("Arpeggiate", 16, 199));
		buttons[BUTTON_ARP_REC] = new Button(
				"arp>Rec>", new String[]{"Rec", "Rec"}, 15*4, 7*4);
			buttons[BUTTON_ARP_REC].setPosition(9, 163);
			buttons[BUTTON_ARP_REC].addListener(this);
			addActor(buttons[BUTTON_ARP_REC]);
		knobs[KNOB_ARP_SPEED] = new Knob("arp>Speed");
			knobs[KNOB_ARP_SPEED].setPosition(11, 96);
			knobs[KNOB_ARP_SPEED].addListener(this);
			addActor(knobs[KNOB_ARP_SPEED]);
		buttons[BUTTON_ARP_LOOP] = new Button(
				"arp>Loop>", new String[]{"Loop", "Loop"}, 16*4, 6*4);
			buttons[BUTTON_ARP_LOOP].setPosition(6, 64);
			buttons[BUTTON_ARP_LOOP].addListener(this);
			addActor(buttons[BUTTON_ARP_LOOP]);
		buttons[BUTTON_ARP_RESET] = new Button(
				"arp>Reset on note>", new String[]{"Reset", "Reset"}, 16*4, 6*4);
			buttons[BUTTON_ARP_RESET].setPosition(6, 38);
			buttons[BUTTON_ARP_RESET].addListener(this);
			addActor(buttons[BUTTON_ARP_RESET]);
		buttons[BUTTON_ARP_POST] = new Button(
				"arp>Post>", new String[]{"Post", "Post"}, 16*4, 6*4);
			buttons[BUTTON_ARP_POST].setPosition(6, 12);
			buttons[BUTTON_ARP_POST].addListener(this);
			addActor(buttons[BUTTON_ARP_POST]);
		addActor(new TextActor("Steps", 75, 181));
		SpriteActor listBack = new SpriteActor(UIDraw.drawSunkenArea(40, 166));
			listBack.setPosition(80, 12);
			addActor(listBack);
		arpeggiationList = new Group();
			arpeggiationList.x = 115;
			arpeggiationList.y = 166;
			addActor(arpeggiationList);
		

		setTrack(track);
	}
	
	@Override
	public void setTrack(Instrument track)
	{
		stopRecordingArpeggiation();
		super.setTrack(track);
		refreshArpeggiationList();
		buttons[BUTTON_ARP_LOOP].setValue(
				track.getSettings().getArpLoop() ? 1 : 0);
		buttons[BUTTON_ARP_RESET].setValue(
				track.getSettings().getArpResetOnNote() ? 1 : 0);
		buttons[BUTTON_ARP_POST].setValue(
				track.getSettings().getArpPostFX() ? 1 : 0);
		knobs[KNOB_ARP_SPEED].setValue(track.getSettings().getArpSpeed());
	}

	@Override
	public void onKnobValueChanged(KnobEvent evt)
	{
		Knob source = ((Knob)evt.getSource());
		if(source == knobs[KNOB_ARP_SPEED])
		{
			track.getSettings().setArpSpeed(source.getValue());
		}
	}
	@Override
	public void onButtonClick(ClickEvent evt)
	{
		Button source = ((Button)evt.getSource());
		if(source == buttons[BUTTON_ARP_REC])
		{
			if(source.getSelectedIndex() == 1)
			{
				startRecordingArpeggiation();
			}
			if(source.getSelectedIndex() == 0)
			{
				stopRecordingArpeggiation();
			}
		}
		if(source == buttons[BUTTON_ARP_LOOP])
		{
			track.getSettings().setArpLoop(source.getSelectedIndex()==1);
		}
		if(source == buttons[BUTTON_ARP_RESET])
		{
			track.getSettings().setArpResetOnNote(source.getSelectedIndex()==1);
		}
		if(source == buttons[BUTTON_ARP_POST])
		{
			track.getSettings().setArpPostFX(source.getSelectedIndex()==1);
		}
	}

	public void refreshArpeggiationList()
	{
		int[] steps = track.getSettings().getArpSteps(); 
		arpeggiationList.clear();
		for(int i = 0; i < steps.length; i++)
		{
			String text = steps[i] + "";
			int width = (int)CFG.getStandardFont().getBounds(text).width;
			arpeggiationList.addActor(new TextActor(text, -width, i*-10));
		}
	}
	public void startRecordingArpeggiation()
	{
		recording = true;
		arpSpeed = track.getSettings().getArpSpeed();
		track.getSettings().setArpSpeed(0);
		track.getSettings().setArpSteps(new int[0]);
		baseNote = -1;
		refreshArpeggiationList();
		buttons[BUTTON_ARP_REC].setValue(1);
	}
	public void stopRecordingArpeggiation()
	{
		if(!recording) return;
		if(track.getSettings().getArpSteps().length == 0)
		{
			addArpeggiationStep(1);
		}
		track.getSettings().setArpSpeed(arpSpeed);
		recording = false;
		buttons[BUTTON_ARP_REC].setValue(0);
	}
	public void addArpeggiationStep(int note)
	{
		if(!recording) return;
		if(note != -1)
		{
			if(baseNote == -1)
			{
				baseNote = note;
			}
			track.getSettings().addArpStep(note-baseNote);
			refreshArpeggiationList();
			if(track.getSettings().getArpSteps().length >= 16)
			{
				stopRecordingArpeggiation();
			}
		}
	}
	@Override
	public void onKeyboardEvent(KeyEvent evt)
	{
		addArpeggiationStep(evt.getKey());
	}
}
