package org.greenblitz.debug.csvlogger.station;

import org.greenblitz.debug.commons.NTConnectionResolver;

public class CSVLoggerRunner {
    public static void main(String[] args) {
        if (NTConnectionResolver.resolveRobotConnection()) {
            var root = TableHandler.getRootHnadler();
        }
    }
}
