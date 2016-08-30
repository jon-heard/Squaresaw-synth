
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.ui;

import com.badlogic.gdx.scenes.scene2d.Group;

public class DragPanel extends Group
{
	Group toScroll;

	public DragPanel(int x, int y, int width, int height)
	{
		this.setBounds(x, y, width, height);
		toScroll = new Group();
		addActor(toScroll);
	}
}
