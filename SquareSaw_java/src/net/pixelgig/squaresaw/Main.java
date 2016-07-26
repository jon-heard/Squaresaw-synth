
package net.pixelgig.squaresaw;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class Main
{
	public static void main(String args[])
	{
		new JavaApp();
		new LwjglApplication(
				new SquareSaw(),
				"SquareSaw", 480, 320, false);
	}
}
