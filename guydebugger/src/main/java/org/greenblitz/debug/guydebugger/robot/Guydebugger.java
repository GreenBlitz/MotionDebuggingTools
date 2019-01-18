package org.greenblitz.debug.guydebugger.robot;

import org.greenblitz.debug.guydebugger.common.GuydeBuggerTables;

public class Guydebugger {
    public static void update() {
        GuydeBuggerTables.ENTRY_UPDATE.setBoolean(true);
    }

    public static void report(double x, double y, double angle) {
        GuydeBuggerTables.ENTRY_X.setNumber(x);
        GuydeBuggerTables.ENTRY_Y.setNumber(y);
        GuydeBuggerTables.ENTRY_HEADING.setNumber(angle);
    }

    public static void reportAndUpdate(double x, double y, double angle) {
        report(x, y, angle);
        update();
    }
}
