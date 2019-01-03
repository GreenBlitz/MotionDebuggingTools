package org.greenblitz.debug;

import edu.wpi.first.networktables.NetworkTableInstance;

import java.util.Arrays;

public class NTConnectionResolver {

    public static final long CONNECTION_TIMEOUT = 1000L;
    public static final int DEFAULT_TEAM_NUMBER = 4590;
    public static final String[] DEFAULT_IP_ADDRESSES = { "10.45.90.2", "172.22.11.2" };

    private static void waitFor(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException ignored) {
        }
    }

    private static void waitFor() {
        waitFor(CONNECTION_TIMEOUT);
    }

    private static boolean resolveTeamNumber(int teamNumber) {
        System.out.printf("Trying to find robot using team number (%d) ...\n", teamNumber);
        NetworkTableInstance.getDefault().startClientTeam(teamNumber);
        waitFor();
        if (!NetworkTableInstance.getDefault().isConnected()) {
            System.err.println("Could not find robot with team number!");
            return false;
        }
        return true;
    }

    private static boolean resolveDS() {
        System.out.println("Trying to find robot using DriverStation ...");
        NetworkTableInstance.getDefault().startDSClient();
        waitFor();
        if (!NetworkTableInstance.getDefault().isConnected()) {
            System.err.println("Could not find robot with DriverStation!");
            return false;
        }
        return true;
    }

    private static boolean resolveIPAddress(String ipaddr) {
        NetworkTableInstance.getDefault().startClient(ipaddr);
        waitFor();
        return NetworkTableInstance.getDefault().isConnected();
    }

    private static boolean resolveIPAddresses(String... ipaddrs) {
        for (String addr : ipaddrs) {
            System.out.printf("Trying to find robot using IP address (%s) ...\n", addr);
            if (resolveIPAddress(addr)) {
                return true;
            }
        }

        System.err.printf("Could not find robot Using IP addresses (%s)!\n", Arrays.toString(ipaddrs));
        return false;
    }

    private static boolean resolveRobotConnection(int teamNumber, String... ipaddrs) {
        if (resolveTeamNumber(teamNumber)) {
            return true;
        }

        if (resolveDS()) {
            return true;
        }

        return resolveIPAddresses(ipaddrs);
    }

    public static boolean resolveRobotConnection() {
        return resolveRobotConnection(DEFAULT_TEAM_NUMBER, DEFAULT_IP_ADDRESSES);
    }

}
