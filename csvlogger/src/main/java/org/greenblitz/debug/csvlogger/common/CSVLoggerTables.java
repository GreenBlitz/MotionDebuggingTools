package org.greenblitz.debug.csvlogger.common;

import edu.wpi.first.networktables.NetworkTable;
import org.greenblitz.debug.commons.DebugTables;

public class CSVLoggerTables {
    public static final String NT_ROOT_NAME = "csvlogger";

    public static final String NT_NAMES_TABLE = "names";
    public static final String NT_VALUES_TABLE = "values";


    public static final NetworkTable ROOT = DebugTables.DEBUG.getSubTable(NT_ROOT_NAME);
}
