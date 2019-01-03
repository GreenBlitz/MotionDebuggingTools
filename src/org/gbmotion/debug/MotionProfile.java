package org.gbmotion.debug;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class MotionProfile {
	
	private static final String NT_X 				= "x",
								NT_Y 				= "y",
								NT_ANGLE 			= "heading",
								NT_POINT_IDX 		= "pointIndex",
								NT_ENCODER_LEFT 	= "encoderLeft",
								NT_ENCODER_RIGHT 	= "encoderRight",
								NT_MOTOR_LEFT 		= "motorLeft",
								NT_MOTOR_RIGHT 		= "motorRight",
								NT_GYRO_ANGLE 		= "gyroHeading";
	
	public static final MotionProfile loadString(String motionProfile){
		String[] entries = motionProfile.split(",");
		double _x = Double.parseDouble(entries[0]),
		_y = Double.parseDouble(entries[1]),
		_angle = Double.parseDouble(entries[2]),
		_encL = Double.parseDouble(entries[4]),
		_encR = Double.parseDouble(entries[5]),
		_motorL = Double.parseDouble(entries[6]),
		_motorR = Double.parseDouble(entries[7]),
		_gyroAngle = Double.parseDouble(entries[8]);
		int _pointIdx = Integer.parseInt(entries[3]);
		return new MotionProfile(_x, _y, _angle, _pointIdx, _encL, _encR, _motorL, _motorR, _gyroAngle);
		
	}
	
	public static final MotionProfile fromNetworkTables(){
		NetworkTable localizerTable = NetworkTableInstance.getDefault().getTable("motion)").getSubTable("localizer");
		double x = localizerTable.getEntry(NT_X).getDouble(0);
		double y = localizerTable.getEntry(NT_Y).getDouble(0);
		double encLeft = localizerTable.getEntry(NT_ENCODER_LEFT).getDouble(0);
		double encRight = localizerTable.getEntry(NT_ENCODER_RIGHT).getDouble(0);
		double angle = localizerTable.getEntry(NT_ANGLE).getDouble(0);
		int pointIdx = (int) localizerTable.getEntry(NT_POINT_IDX).getDouble(-1);
		double motorLeft = localizerTable.getEntry(NT_MOTOR_LEFT).getDouble(0);
		double motorRight = localizerTable.getEntry(NT_MOTOR_RIGHT).getDouble(0);
		double gyroAngle = localizerTable.getEntry(NT_GYRO_ANGLE).getDouble(0);
		return new MotionProfile(x,y,angle,pointIdx,encLeft,encRight,motorLeft,motorRight, gyroAngle);
	}
	
	public double m_x, m_y, m_angle, m_encL, m_encR, m_motorL, m_motorR, m_gyroAngle; 
	public int m_pointIdx;
	
	public MotionProfile(double x, double y, double angle, int pointIdx, double encL, double encR, double motorL, double motorR, double gyroAngle){
		m_x = x;
		m_y = y;
		m_angle = angle;
		m_pointIdx = pointIdx;
		m_encL = encL;
		m_encR = encR;
		m_motorL = motorL;
		m_motorR = motorR;
		m_gyroAngle = gyroAngle;
	}
	
	public String toString(){
		return m_x + "," + m_y + "," + m_angle + "," + m_pointIdx + "," + m_encL + "," + m_encR + "," + m_motorL + "," + m_motorR + "," + m_gyroAngle;
	}
	
	
	
	
}
