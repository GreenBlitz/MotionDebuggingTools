package com.gbmotion.debug;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class MotionDataTesting {
	
	public MotionEnv m_env;
	
	public List<MotionProfile> m_profiles;
	
	public MotionDataTesting(){
		m_profiles = new LinkedList<>();
	}
	
	public static void main(String[] args) {
		new MotionData().run(600, 600);
	}
	

	
}
