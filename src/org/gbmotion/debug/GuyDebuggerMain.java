package org.gbmotion.debug;

public class GuyDebuggerMain {

	public static void main(String[] args) {
		if (NTConnectionResolver.resolveRobotConnection()) {
			new MotionData().run(600, 600);
		} else {
			System.err.println("----------------------------------------");
			System.err.println("### Connection to robot could not be formed! ###");
		}
	}

}
