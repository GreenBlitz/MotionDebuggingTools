package org.greenblitz.debug.guydebugger.station;

import org.greenblitz.debug.guydebugger.common.GuydeBuggerTables;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class MotionData extends JFrame {
	
	public MotionEnv m_env;
	
	public List<MotionProfile> m_profiles;
	
	public MotionData(){
		m_profiles = new LinkedList<>();
	}

	public void run(int width, int height) {
		MotionAnalyzingPanel panel = new MotionAnalyzingPanel();
		panel.setSize(width - 2, height - 30);
		setLayout(null);
		setSize(width, height);
		setPreferredSize(getSize());
		//frame.setResizable(false);
		panel.update();
		setContentPane(panel);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();

		setVisible(true);

		while (true){
			if (GuydeBuggerTables.ENTRY_UPDATE.getBoolean(false)){
                GuydeBuggerTables.ENTRY_UPDATE.setBoolean(false);
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

			repaint();
		}
	}


}
