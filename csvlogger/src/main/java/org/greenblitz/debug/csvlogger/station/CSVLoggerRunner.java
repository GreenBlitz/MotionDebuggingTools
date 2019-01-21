package org.greenblitz.debug.csvlogger.station;

import edu.wpi.first.networktables.NetworkTableInstance;
import org.greenblitz.debug.commons.NTConnectionResolver;
import org.greenblitz.debug.csvlogger.common.exception.DirectoryCreationException;

public class CSVLoggerRunner {
    public static void main(String[] args) throws DirectoryCreationException {
        if (NTConnectionResolver.resolveRobotConnection()) {
            TableHandler.init();

            while (NetworkTableInstance.getDefault().isConnected()) {

            }
        }
    }
}
