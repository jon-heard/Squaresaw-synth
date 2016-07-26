
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
