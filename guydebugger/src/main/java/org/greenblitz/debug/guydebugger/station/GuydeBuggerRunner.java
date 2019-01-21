package org.greenblitz.debug.guydebugger.station;

import org.greenblitz.debug.commons.NTConnectionResolver;

public class GuydeBuggerRunner {
	public static void main(String[] args) {
		if (NTConnectionResolver.resolveRobotConnection()) {
			new MotionData().run(600, 600);
		}
	}
}
