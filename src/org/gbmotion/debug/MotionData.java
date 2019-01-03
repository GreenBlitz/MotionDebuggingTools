package org.gbmotion.debug;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

public class MotionData {
	
	public MotionEnv m_env;
	
	public List<MotionProfile> m_profiles;
	
	public MotionData(){
		m_profiles = new LinkedList<>();
	}
	
	public void run(int width, int height){
		NetworkTableInstance.getDefault().setServerTeam(4590);
		
		MotionAnalyzingPanel panel = new MotionAnalyzingPanel();
		panel.setSize(width - 2, height - 30);
		JFrame frame = new JFrame();
		frame.setLayout(null);
		frame.setSize(width, height);
		frame.setPreferredSize(frame.getSize());
		//frame.setResizable(false);
		panel.update();
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		
		frame.setVisible(true);
		NetworkTable motionTable = NetworkTableInstance.getDefault().getTable("motion");
		while (true){
			if (motionTable.getEntry("isUpdated").getBoolean(false)){
				motionTable.getEntry("isUpdated").setBoolean(false);
				if (panel.m_env == null){
					m_env = MotionEnv.fromNetworkTable();
					panel.panel.m_robotPoints = new LinkedList<>();
					panel.m_env = m_env;
				}
				
				panel.m_profiles.add(MotionProfile.fromNetworkTables());
				panel.gotoPage(panel.m_profiles.size() - 1);
				panel.panel.addRobotLocation();
				
				panel.update();
			}
			
			frame.repaint();
		}
	}
	
}
