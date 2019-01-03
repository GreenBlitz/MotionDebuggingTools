package org.gbmotion.debug;


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.util.LinkedList;
import java.util.List;

public class MotionEnv {
	
	public List<Double> m_points;
	
	public double m_mulOffX = 1.2, m_mulOffY = 1.2;
	
	public double m_minX = Double.MAX_VALUE, m_minY = Double.MAX_VALUE, m_maxX = Double.MIN_VALUE, m_maxY = Double.MIN_VALUE;
	
	public boolean isDomainForced = false;
	
	private static String NT_PATH = "path";
	public MotionEnv(){
		m_points = new LinkedList<>();
	}
	
	public void addPoint(double x, double y){
		m_points.add(x);
		m_points.add(y);
		calculateMinMax(x,y);
	}
	
	public void calculateMinMax(double x, double y){
		if (!isDomainForced){
			m_minX = Math.min(m_minX, x - 0.125);
			m_minY = Math.min(m_minY, y - 0.125);
			m_maxX = Math.max(m_maxX, x + 0.125);
			m_maxY = Math.max(m_maxY, y + 0.125);
		}
	}
	
	public static MotionEnv fromNetworkTable(){
		NetworkTable motionTable = NetworkTableInstance.getDefault().getTable("motion");
		int length = (int)motionTable.getEntry("pathLength").getDouble(0);
		
		double[] points = new double[((length * 2 + 254) / 255) * 255];
		for (int i = 0; i < (length * 2 + 254) / 255; i++){
			System.arraycopy(motionTable.getEntry(NT_PATH + i).getDoubleArray(new double[255]), 0, points, i * 255, 255);
		}
		MotionEnv env = new MotionEnv();
		if (length != 0){
			int diff = points.length - length * 2;
			for (int i = points.length/2 - diff/2 ; i >= 0; i--){
				System.out.println(points[2 * i + 1] + ":" + points[2 * i]);
				env.addPoint(points[2 * i + 1], points[2 * i]);
			}
		}
		return env;
		
	}
	
	public static MotionEnv loadFromString(String data){
		String[] coords = data.split(",");
		
		MotionEnv env = new MotionEnv();
	
		for (int i = coords.length / 2 - 1; i >= 0; i--){
			env.addPoint(Double.parseDouble(coords[2 * i]), Double.parseDouble(coords[2 * i + 1]));
		}
		
		return env;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		int i = 0;
		for (double coord : m_points){
			i++;
			builder.append(coord);
			if (m_points.size() != i)
				builder.append(",");
		}
		return builder.toString();
	}
	

	
	
}
