package org.greenblitz.debug.guydebugger.station;

import org.greenblitz.debug.guydebugger.common.GuydeBuggerTables;

public class MotionProfile {

    public static final MotionProfile loadString(String motionProfile) {
        String[] entries = motionProfile.split(",");
        double _x = Double.parseDouble(entries[0]),
                _y = Double.parseDouble(entries[1]),
                _angle = Double.parseDouble(entries[2]);
        return new MotionProfile(_x, _y, _angle);

    }

    public static final MotionProfile fromNetworkTables() {
        double x = GuydeBuggerTables.ENTRY_X.getDouble(0);
        double y = GuydeBuggerTables.ENTRY_Y.getDouble(0);
        double angle = GuydeBuggerTables.ENTRY_HEADING.getDouble(0);
        return new MotionProfile(x, y, angle);
    }

    public double m_x, m_y, m_angle;

    public MotionProfile(double x, double y, double angle) {
        m_x = x;
        m_y = y;
        m_angle = angle;
    }

    public String toString() {
        return m_x + "," + m_y + "," + m_angle;
    }


}
