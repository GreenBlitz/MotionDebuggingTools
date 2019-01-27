package org.greenblitz.debug.guydebugger.common;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import org.greenblitz.debug.commons.DebugTables;

public class GuydeBuggerTables {
    public static final NetworkTable GUYDEBUGGER_TABLE = DebugTables.DEBUG.getSubTable("guydebugger");

    public static final NetworkTableEntry ENTRY_X = GUYDEBUGGER_TABLE.getEntry("x");
    public static final NetworkTableEntry ENTRY_Y = GUYDEBUGGER_TABLE.getEntry("y");
    public static final NetworkTableEntry ENTRY_HEADING = GUYDEBUGGER_TABLE.getEntry("heading");
    public static final NetworkTableEntry ENTRY_UPDATE = GUYDEBUGGER_TABLE.getEntry("isUpdated");
}
