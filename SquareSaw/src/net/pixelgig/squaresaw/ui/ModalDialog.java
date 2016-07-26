
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
		while(parent.parent != null)
		{
			parent = parent.parent;
		}
		return parent;
	}
	public void close()
	{
		dimmer.remove();
		this.remove();
	}
}
