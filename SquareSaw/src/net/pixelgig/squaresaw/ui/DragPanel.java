
package net.pixelgig.squaresaw.ui;

import com.badlogic.gdx.scenes.scene2d.Group;

public class DragPanel extends Group
{
	Group toScroll;

	public DragPanel(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		toScroll = new Group();
		addActor(toScroll);
	}
}
