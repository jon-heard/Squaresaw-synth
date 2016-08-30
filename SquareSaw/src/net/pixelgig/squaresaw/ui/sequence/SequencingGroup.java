
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.ui.sequence;

import net.pixelgig.squaresaw.Song;
import net.pixelgig.squaresaw.ui.UIPanel;

public class SequencingGroup extends UIPanel
{
	Song tracks;

	public SequencingGroup(
			Song tracks, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.tracks = tracks;
	}
	
	public interface SequencingControlsListener {}
}
