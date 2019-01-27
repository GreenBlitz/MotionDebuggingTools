package org.greenblitz.debug.commons;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class DebugTables {
    public static final NetworkTableInstance NT_INSTANCE = NetworkTableInstance.getDefault();
    public static final NetworkTable DEBUG = NT_INSTANCE.getTable("debug");
}
