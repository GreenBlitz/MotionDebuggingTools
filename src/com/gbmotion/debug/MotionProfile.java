package com.gbmotion.debug;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class MotionProfile {
	
	private static final String NT_X = "locX",
								NT_Y = "locY",
								NT_ANGLE = "locAngle",
								NT_POINT_IDX = "pointIdx",
								NT_ENCODER_LEFT = "encLeft",
								NT_ENCODER_RIGHT = "encRight",
								NT_MOTOR_LEFT = "motorLeft",
								NT_MOTOR_RIGHT = "motorRight",
								NT_GYRO_ANGLE = "gyroAngle";
	
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
		NetworkTable motionTable = NetworkTable.getTable("motion");
		double x = motionTable.getNumber(NT_X, 0);
		double y = motionTable.getNumber(NT_Y, 0);
		double encLeft = motionTable.getNumber(NT_ENCODER_LEFT, 0);
		double encRight = motionTable.getNumber(NT_ENCODER_RIGHT, 0);
		double angle = motionTable.getNumber(NT_ANGLE, 0);
		int pointIdx = (int) motionTable.getNumber(NT_POINT_IDX, -1);
		double motorLeft = motionTable.getNumber(NT_MOTOR_LEFT, 0);
		double motorRight = motionTable.getNumber(NT_MOTOR_RIGHT, 0);
		double gyroAngle = motionTable.getNumber(NT_GYRO_ANGLE, 0);
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
