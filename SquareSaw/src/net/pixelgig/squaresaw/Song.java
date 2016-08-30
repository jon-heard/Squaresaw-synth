
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw;

import net.pixelgig.squaresaw.Listeners.Listener;
import net.pixelgig.squaresaw.Listeners.ListenerEvent;
import net.pixelgig.squaresaw.Listeners.ListenerList;
import net.pixelgig.squaresaw.audio.Instrument;

public class Song
{
	Instrument[] tracks;
	int index;
	protected ListenerList listeners = new ListenerList();

	public Song(Instrument[] tracks)
	{
		this.tracks = tracks;
		this.index = 0;
	}
	
	public int getTrackCount() { return tracks.length; }
	public int getCurrentTrackIndex() { return index; }
	public Instrument getCurrentTrack() { return tracks[index]; }
	public void setCurrentTrack(int index)
	{
		assert index > 0 && index < tracks.length : "Track set out of bounds.";
		this.index = index;
		listeners.event("onTrackListTrackChanged", new TrackChangedEvent(this));
	}

	public void addListener(TrackListListener listener)
	{
		listeners.add(listener);
	}
	public void removeListener(TrackListListener listener)
	{
		listeners.remove(listener);
	}
	public interface TrackListListener extends Listener
	{
		public void onTrackListTrackChanged(TrackChangedEvent evt);
	}
	public class TrackChangedEvent extends ListenerEvent
	{
		public TrackChangedEvent(Song src)
		{
			super(src);
		}
	}


	public void resume()
	{
		for(Instrument track : tracks)
		{
			if(track != null)
			{
				track.resume();
			}
		}
	}
	public void pause()
	{
		for(Instrument track : tracks)
		{
			if(track != null)
			{
				track.pause();
			}
		}
	}
	public void stop()
	{
		for(Instrument track : tracks)
		{
			if(track != null)
			{
				track.stop();
			}
		}
	}
}
