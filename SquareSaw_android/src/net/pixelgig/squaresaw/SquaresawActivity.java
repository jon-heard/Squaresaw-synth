
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw;

import net.pixelgig.squaresaw.SquareSaw;

import com.badlogic.gdx.backends.android.AndroidApplication;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class SquaresawActivity extends AndroidApplication
{
	SquareSaw app;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(
				ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		new AndroidApp();
		app = new SquareSaw();
		initialize(app);
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		app.dispose();
	}
}
