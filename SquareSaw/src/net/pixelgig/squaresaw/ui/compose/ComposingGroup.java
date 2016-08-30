
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.ui.compose;

import com.badlogic.gdx.scenes.scene2d.Group;
import net.pixelgig.squaresaw.Song;

public class ComposingGroup extends Group
{
	public ComposingGroup(
			Song tracks, int x, int y, int width, int height)
	{
		setBounds(x, y, width, height);
		
		addActor(new ComposingControls(tracks, 0, height));
		addActor(new TrackSelect(tracks, ComposingControls.WIDTH, height));
	}
}
