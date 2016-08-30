
/*
 * Copyrignt (c) 2015 Jonathan Heard to present
 * All rights reserved
 */

package net.pixelgig.squaresaw;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

//
public class Main
{
	public static void main(String args[])
	{
		new JavaApp();
		new LwjglApplication(
			new SquareSaw(),
			"SquareSaw", 480, 320);
//		new LwjglApplication(
//			new Test(),
//			"Test test", 1500, 1000);
//		new LwjglApplication(
//				new SquareSaw(),
//				"SquareSaw", 480, 320, false);
	}
}
