
package net.pixelgig.squaresaw.ui.instrument;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveTo;

import net.pixelgig.squaresaw.App;
import net.pixelgig.squaresaw.CFG;
import net.pixelgig.squaresaw.Song;
import net.pixelgig.squaresaw.audio.*;
import net.pixelgig.squaresaw.ui.*;
import net.pixelgig.squaresaw.ui.Button.ButtonListener;
import net.pixelgig.squaresaw.ui.Button.ClickEvent;
import net.pixelgig.squaresaw.ui.ListBox.ListBoxListener;
import net.pixelgig.squaresaw.ui.ListBox.ValueChangedEvent;

public class PresetPanel extends ModalDialog implements
	ButtonListener, ListBoxListener
{
	
	public static final int WIDTH = 370;
	public static final int HEIGHT = 200;
	
	static int BUTTON_CATEGORY_NEW = 0;
	static int BUTTON_CATEGORY_DELETE = 1;
	static int BUTTON_PRESET_LOAD = 2;
	static int BUTTON_PRESET_SAVE = 3;
	static int BUTTON_PRESET_DELETE = 4;	
	static int BUTTON_CLOSE = 5;

	Button[] buttons;
	ListBox listCategories;
	ListBox listPresets;
	Song tracks;
	Instrument track;
	InstrumentSettings currentSettings;
	InstrumentSettings[] presets;

	public PresetPanel(Group parent, Song tracks)
	{
		super(parent);
		this.tracks = tracks;
		this.track = tracks.getCurrentTrack();
		currentSettings = track.getSettings();
		this.x = (CFG.getAppWidth() - WIDTH) / 2;
		this.y = (CFG.getAppHeight() - HEIGHT) / 2;
		
		buttons = new Button[6];
		
		UIPanel panel = new UIPanel(
				0, 0, WIDTH, HEIGHT);
		addActor(panel);
		Group category = new Group();
			category.x = 15;
			category.y = 35;
			panel.addActor(category);
			category.addActor(new TextActor("Categories", 0, HEIGHT-55));
			listCategories = new ListBox(
					0, 30, 125, HEIGHT-55 - 35);
				listCategories.addListener(this);
				category.addActor(listCategories);
			buttons[BUTTON_CATEGORY_NEW] = new Button(
					"presets>categories>New>", "New", 50, 30);
				buttons[BUTTON_CATEGORY_NEW].addListener(this);
				buttons[BUTTON_CATEGORY_NEW].setPosition(0, 0);
				category.addActor(buttons[BUTTON_CATEGORY_NEW]);
			buttons[BUTTON_CATEGORY_DELETE] = new Button(
					"presets>categories>Delete>", "Delete", 80, 30);
				buttons[BUTTON_CATEGORY_DELETE].addListener(this);
				buttons[BUTTON_CATEGORY_DELETE].setPosition(50, 0);
				category.addActor(buttons[BUTTON_CATEGORY_DELETE]);
		Group preset = new Group();
			preset.x = 150;
			preset.y = 35;
			panel.addActor(preset);
			preset.addActor(new TextActor( "Presets", 0, HEIGHT-55));
			listPresets = new ListBox(
					0, 30,
					205, HEIGHT-55 - 35);
				listPresets.addListener(this);
				preset.addActor(listPresets);
	 		buttons[BUTTON_PRESET_LOAD] = new Button(
	 				"presets>Load>", "Load", 60, 30);
				buttons[BUTTON_PRESET_LOAD].addListener(this);
				buttons[BUTTON_PRESET_LOAD].setPosition(0, 0);
				preset.addActor(buttons[BUTTON_PRESET_LOAD]);
			buttons[BUTTON_PRESET_SAVE] = new Button(
					"presets>Save>", "Save", 60, 30);
				buttons[BUTTON_PRESET_SAVE].addListener(this);
				buttons[BUTTON_PRESET_SAVE].setPosition(65, 0);
				preset.addActor(buttons[BUTTON_PRESET_SAVE]);
			buttons[BUTTON_PRESET_DELETE] = new Button(
					"presets>Delete>", "Delete", 80, 30);
				buttons[BUTTON_PRESET_DELETE].addListener(this);
				buttons[BUTTON_PRESET_DELETE].setPosition(130, 0);
				preset.addActor(buttons[BUTTON_PRESET_DELETE]);
		buttons[BUTTON_CLOSE] = new Button("presets>Close>", "Close", 100, 25);
			buttons[BUTTON_CLOSE].addListener(this);
			buttons[BUTTON_CLOSE].setPosition(WIDTH-110, 10);
			panel.addActor(buttons[BUTTON_CLOSE]);

		refreshCategories();
	}
	
	public void refreshCategories()
	{
		FileHandle dir = Gdx.files.external("app-data/squaresaw/presets");
		if(!dir.exists())
		{
			dir.mkdirs();
		}
		FileHandle[] subs = dir.list();
		List<String> categories = new LinkedList<String>();
		for(FileHandle sub : subs)
		{
			if(sub.isDirectory())
			{
				categories.add(sub.name());
			}
		}
		listCategories.setItems(categories.toArray(new String[0]));
	}
	public void refreshPresets()
	{
		String category = listCategories.getValue();
		if(category != null)
		{
			FileHandle dir = Gdx.files.external(
					"app-data/squaresaw/presets/" + category);
			FileHandle[] subs = dir.list(".ssp");
			List<String> presetNameList = new LinkedList<String>();
			List<InstrumentSettings> presetList =
					new LinkedList<InstrumentSettings>();
			for(FileHandle sub : subs)
			{
				InstrumentSettings preset = new InstrumentSettings();
				boolean loaded = preset.fromXml(sub.readString());
				if(loaded)
				{
					presetList.add(preset);
					presetNameList.add(
							sub.name().substring(0, sub.name().length()-4));
				}
			}
			presets = presetList.toArray(new InstrumentSettings[0]);
			listPresets.setItems(presetNameList.toArray(new String[0]));
		}
		else
		{
			listPresets.setItems(null);
		}
	}

	@Override
	public void onListBoxSelecting(ValueChangedEvent evt)
	{
		if(		evt.getSource() == listPresets &&
				presets != null &&
				evt.getIndex() != -1)
		{
			int index = ((ListBox)evt.getSource()).getValueIndex();
			track.setSettings(presets[index]);
			track.setNote(45);
		}
	}
	@Override
	public void onListBoxValueChangedEvent(ValueChangedEvent evt)
	{
		ListBox source = ((ListBox)evt.getSource());
		if(source == listCategories)
		{
			refreshPresets();
		}
		if(source == listPresets)
		{
			track.setNote(-1);
		}
	}


	@Override
	public void onButtonClick(ClickEvent evt)
	{
		Button source = ((Button)evt.getSource());
		if(source == buttons[BUTTON_CATEGORY_NEW])
		{
			newCategory();
		}
		if(source == buttons[BUTTON_CATEGORY_DELETE])
		{
			deleteCategory();
		}
		if(source == buttons[BUTTON_PRESET_LOAD])
		{
			loadPreset();
		}
		if(source == buttons[BUTTON_PRESET_SAVE])
		{
			savePreset();
		}
		if(source == buttons[BUTTON_PRESET_DELETE])
		{
			deletePreset();
		}
		if(source == buttons[BUTTON_CLOSE])
		{
			track.setSettings(currentSettings);
			this.close();
			tracks.setCurrentTrack(tracks.getCurrentTrackIndex());
		}
	}

	public void newCategory()
	{
		Gdx.input.getTextInput(new TextInputListener() {
			@Override
			public void input(String text)
			{
				FileHandle dir = Gdx.files.external(
						"app-data/squaresaw/presets/" + text);
				dir.mkdirs();
				refreshCategories();
			}
			@Override
			public void canceled() {}
		}, "Enter a category name", "");
	}
	public void deleteCategory()
	{
		final String category = listCategories.getValue();
		if(category != null)
		{
			new Dialog_Confirmation(
					"Remove \"" + category + "\" and all of its prests?", this,
					new Dialog_Confirmation.ConfirmationUser() {
						@Override
						public void onConfirmationComplete(boolean confirmed)
						{
							if(confirmed)
							{
								FileHandle toDelete = Gdx.files.external(
										"app-data/squaresaw/presets/" + category);
								toDelete.delete();
								refreshCategories();
							}
						}
					});
		}
	}
	public void loadPreset()
	{
		if(listPresets.getValueIndex() != -1)
		{
			new Dialog_Confirmation(
					"Load \"" + listPresets.getValue() + "\" into current track?",
					this,
					new Dialog_Confirmation.ConfirmationUser()
					{
						@Override
						public void onConfirmationComplete(boolean confirmed)
						{
							if(confirmed)
							{
								int selection = listPresets.getValueIndex();
								currentSettings = presets[selection];
							}
						}
					});
		}
	}
	public void savePreset()
	{
		Gdx.input.getTextInput(new TextInputListener() {
			@Override
			public void input(String text)
			{
				final Boolean[] cancel = {false};
				FileHandle toSave = Gdx.files.external(
						"app-data/squaresaw/presets/" +
						listCategories.getValue() + "/" + text + ".ssp");
				if(toSave.exists())
				{
					new Dialog_Confirmation(
							"Overwrite existing preset named \"" + text + "\"?",
							PresetPanel.this,
							new Dialog_Confirmation.ConfirmationUser()
							{
								@Override
								public void onConfirmationComplete(boolean confirmed)
								{
									if(!confirmed)
									{
										cancel[0] = true;
									}
								}
							});
				}
				if(!cancel[0])
				{
					OutputStreamWriter o = new OutputStreamWriter(
							toSave.write(false));
					try
					{
						o.write(currentSettings.toXml());
						o.close();
					}
					catch (IOException e)
					{
						new Dialog_Confirmation(
								"Unable to save the preset",
								"Ok", "", PresetPanel.this, null);
						e.printStackTrace();
					}				
					refreshPresets();
				}
			}
			@Override
			public void canceled() {}
		}, "Enter a preset name", "");
		App.log();
	}
	public void deletePreset()
	{
		final String category = listCategories.getValue();
		final String preset = listPresets.getValue();
		if(preset != null)
		{
			new Dialog_Confirmation(
					"Remove \"" + preset + "\"?", this,
					new Dialog_Confirmation.ConfirmationUser()
					{
						@Override
						public void onConfirmationComplete(boolean confirmed)
						{
							if(confirmed)
							{
								FileHandle toDelete = Gdx.files.external(
										"app-data/squaresaw/presets/" +
										category + "/" + preset + ".ssp");
								toDelete.delete();
								refreshPresets();
							}
						}
					});
		}
	}
}
