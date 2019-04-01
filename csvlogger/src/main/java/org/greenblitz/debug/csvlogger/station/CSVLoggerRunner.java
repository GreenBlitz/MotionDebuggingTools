package org.greenblitz.debug.csvlogger.station;

import org.greenblitz.debug.commons.NTConnectionResolver;
import org.greenblitz.debug.csvlogger.common.exception.DirectoryCreationException;

import java.io.IOException;
import java.util.Scanner;

public class CSVLoggerRunner {
    public static void main(String[] args) throws DirectoryCreationException, IOException {
        if (NTConnectionResolver.resolveRobotConnection()) {
            TableHandler.init();

            while (true) {} // I know, i know. The NT thread is a daemon and I can't change that which sucks
        }
    }
}
