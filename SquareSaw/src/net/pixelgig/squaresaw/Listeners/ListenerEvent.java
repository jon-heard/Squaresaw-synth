
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw.Listeners;

public class ListenerEvent
{
	Object src;
	public ListenerEvent(Object src)
	{
		this.src = src;
	}
	public Object getSource() { return src; }
}
