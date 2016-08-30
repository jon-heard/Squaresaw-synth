
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.ui;

import com.badlogic.gdx.scenes.scene2d.Group;

public class ModalDialog extends Group
{
	Dimmer dimmer;

	public ModalDialog(Group parent)
	{
		Group topLevel = findTopLevelParent(parent); 
		dimmer = new Dimmer();
		topLevel.addActor(dimmer);
		topLevel.addActor(this);
	}
	protected Group findTopLevelParent(Group parent)
	{
		while(parent.getParent() != null)
		{
			parent = parent.getParent();
		}
		return parent;
	}
	public void close()
	{
		dimmer.remove();
		this.remove();
	}
}
