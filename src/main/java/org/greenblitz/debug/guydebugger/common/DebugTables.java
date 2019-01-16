package org.greenblitz.debug.guydebugger.common;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class DebugTables {
    public static final NetworkTableInstance NT_INSTANCE = NetworkTableInstance.getDefault();
    public static final NetworkTable DEBUG_TABLE = NT_INSTANCE.getTable("debug");
}
