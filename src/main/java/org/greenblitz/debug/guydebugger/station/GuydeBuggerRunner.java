package org.greenblitz.debug.guydebugger.station;

public class GuydeBuggerRunner {
	public static void main(String[] args) {
		if (NTConnectionResolver.resolveRobotConnection()) {
			new MotionData().run(600, 600);
		} else {
			System.err.println("----------------------------------------");
			System.err.println("### Connection to station could not be formed! ###");
		}
	}
}
