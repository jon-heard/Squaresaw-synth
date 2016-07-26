
package net.pixelgig.squaresaw;

import android.content.pm.ActivityInfo;
import com.badlogic.gdx.backends.android.AndroidApplication;

public class SquareSaw_androidActivity extends AndroidApplication
{
	SquareSaw app;

	@Override
	public void onCreate (android.os.Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(
				ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		new AndroidApp();
		app = new SquareSaw();
		initialize(app, false);
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		app.dispose();
	}
}
