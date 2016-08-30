
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.ui;

import com.badlogic.gdx.scenes.scene2d.Group;

import net.pixelgig.squaresaw.ui.Button.ButtonListener;
import net.pixelgig.squaresaw.ui.Button.ClickEvent;

public class Dialog_Confirmation extends ModalDialog implements
	ButtonListener
{
	Button confirmButton, cancelButton;

	ConfirmationUser user;

	public Dialog_Confirmation(
			String text, Group parent, ConfirmationUser user)
	{
		this(text, "Ok", "Cancel", parent, user);
	}
	public Dialog_Confirmation(
			String text, String confirmText,
			String cancelText, Group parent,
			ConfirmationUser user)
	{
		super(parent);

		setBounds(0, 150, 70, 70);
		this.user = user;

		SpriteActor back = new SpriteActor(
				UIDraw.drawConfirmBack((int)getHeight()));
			back.setSize(1000, getHeight());
			addActor(back);
		addActor(new TextActor(text, 10, 55));
		confirmButton = new Button("Confirm>",	confirmText);
			confirmButton.setPosition(10, 15);
			confirmButton.addListener(this);
			addActor(confirmButton);
		if(cancelText != "" && cancelText != null)
		{
			cancelButton = new Button("Cancel>", cancelText);
				cancelButton.setPosition(135, 15);
				cancelButton.addListener(this);
				addActor(cancelButton);
		}
	}

	@Override
	public void onButtonClick(ClickEvent evt)
	{
		Button source = (Button)evt.getSource();
		close();
		if(source == confirmButton && user != null)
		{
			user.onConfirmationComplete(true);
		}
		if(source == cancelButton && user != null)
		{
			user.onConfirmationComplete(false);
		}
	}

	public interface ConfirmationUser
	{
		public void onConfirmationComplete(boolean confirmed);
	}
}
