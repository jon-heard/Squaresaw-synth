
package net.pixelgig.squaresaw.ui.compose;

import net.pixelgig.squaresaw.Song;
import net.pixelgig.squaresaw.ui.*;
import net.pixelgig.squaresaw.ui.Button.ClickEvent;

public class ComposingControls extends UIPanel implements
	Button.ButtonListener
{
	public static final int WIDTH = 95;
	
	static int BUTTON_CONTINUE_NOTE = 0;
	static int BUTTON_STOP_NOTE = 1;
	static int BUTTON_REC = 2;
	static int BUTTON_PLAY = 3;
	static int SPINNER_STEP = 0;
	static int SPINNER_TEMPO = 1;
	static int SPINNER_PATTERN_SIZE = 2;

	Song tracks;
	Button[] buttons;
	Spinner[] spinners;
	
	public ComposingControls(Song tracks, int x, int height)
	{
		super(x, 0, WIDTH, height);
		
		this.tracks = tracks;

		buttons = new Button[4];
		spinners = new Spinner[3];

		buttons[BUTTON_CONTINUE_NOTE] = new Button(
				"Continue>", ">", 10*4, 10*4);
			buttons[BUTTON_CONTINUE_NOTE].setPosition(9, 165);
			buttons[BUTTON_CONTINUE_NOTE].addListener(this);
			addActor(buttons[BUTTON_CONTINUE_NOTE]);
		buttons[BUTTON_STOP_NOTE] = new Button("Stop>", "-", 10*4, 10*4);
			buttons[BUTTON_STOP_NOTE].setPosition(50, 165);
			buttons[BUTTON_STOP_NOTE].addListener(this);
			addActor(buttons[BUTTON_STOP_NOTE]);
		buttons[BUTTON_REC] = new Button(
				"Rec>", new String[]{"Rec", "Rec"}, 15*4, 7*4);
			buttons[BUTTON_REC].setPosition(19, 135);
			buttons[BUTTON_REC].addListener(this);
			addActor(buttons[BUTTON_REC]);
		buttons[BUTTON_PLAY] = new Button(
				"Play>", new String[]{"Play", "Play"}, 15*4, 7*4);
			buttons[BUTTON_PLAY].setPosition(19, 105);
			buttons[BUTTON_PLAY].addListener(this);
			addActor(buttons[BUTTON_PLAY]);
		spinners[SPINNER_TEMPO] = new Spinner("Tempo", 120, 10, 30, 300);
			spinners[SPINNER_TEMPO].setPosition(8, 60);
			addActor(spinners[SPINNER_TEMPO]);
		spinners[SPINNER_PATTERN_SIZE] = new Spinner("Rows", 64, 1, 4, 256);
			spinners[SPINNER_PATTERN_SIZE].setPosition(8, 20);
			addActor(spinners[SPINNER_PATTERN_SIZE]);
	}

	@Override
	public void onButtonClick(ClickEvent evt)
	{
		Button source = ((Button)evt.getSource());
		if(source == buttons[BUTTON_CONTINUE_NOTE])
		{

		}
		if(source == buttons[BUTTON_STOP_NOTE])
		{

		}
		if(source == buttons[BUTTON_REC])
		{

		}
		if(source == buttons[BUTTON_PLAY])
		{

		}
	}
}
