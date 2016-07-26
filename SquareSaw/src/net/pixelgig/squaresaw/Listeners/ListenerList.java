
package net.pixelgig.squaresaw.Listeners;

import java.lang.reflect.Method;
import java.util.*;

public class ListenerList
{
	List<Listener> listeners = new ArrayList<Listener>();
	int listenerCount = 0;
	
	public void event(String eventName, ListenerEvent param)
	{
		if(listenerCount == 0) return;
		try
		{
			for(Listener listener : listeners)
			{
				Method method = listener.getClass().
						getMethod(eventName, param.getClass());
				method.invoke(listener, param);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void add(Listener listener)
	{
		if(!listeners.contains(listener))
		{
			listeners.add(listener);
			listenerCount++;
		}
	}
	public void remove(Listener listener)
	{
		if(listeners.contains(listener))
		{
			listeners.remove(listener);
			listenerCount--;
		}
	}
}
