package com.gbmotion.debug;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MotionAnalyzingPanel extends JPanel {

	public int m_pageNum = -1;
	
	private boolean isLookingAtTF = false;
	private JButton leftPage, rightPage, firstPage, lastPage, exportData, importData, resetData;

	private JTextField frameData;
	private JLabel frameData_outOf;
	private JLabel locX, locY, locAngle, gyroAngle, encLeft, encRight, motorLeft, motorRight;

	public MotionPanel panel;

	public MotionEnv m_env;

	public List<MotionProfile> m_profiles;

	public MotionAnalyzingPanel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setLayout(null);
		frameData = new JTextField();
		frameData_outOf = new JLabel();
		m_profiles = new LinkedList<>();
		panel = new MotionPanel();
		leftPage = new JButton();
		leftPage.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				backPage();
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseClicked(MouseEvent arg0) {
			}
		});

		rightPage = new JButton();
		rightPage.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				nextPage();
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseClicked(MouseEvent arg0) {
			}
		});

		lastPage = new JButton();
		lastPage.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				lastPage();
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseClicked(MouseEvent arg0) {
			}
		});

		firstPage = new JButton();
		firstPage.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				firstPage();
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseClicked(MouseEvent arg0) {
			}
		});

		leftPage.setText("<");
		rightPage.setText(">");
		firstPage.setText("<<");
		lastPage.setText(">>");

		leftPage.setSize(50, 30);
		rightPage.setSize(50, 30);
		firstPage.setSize(50, 30);
		lastPage.setSize(50, 30);

		add(leftPage);
		add(rightPage);
		add(firstPage);
		add(lastPage);
		add(frameData);

		
		importData = new JButton();
		exportData = new JButton();
		resetData = new JButton();

		importData.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				importData();
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseClicked(MouseEvent arg0) {
			}
		});

		exportData.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				exportData();
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseClicked(MouseEvent arg0) {
			}
		});

		resetData.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				resetData();
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseClicked(MouseEvent arg0) {
			}
		});
		
		importData.setSize(60, 30);
		importData.setText("Load");
		
		exportData.setSize(60, 30);
		exportData.setText("Save");
		
		resetData.setSize(70, 30);
		resetData.setText("Reset");
		
		
		add(importData);
		add(exportData);
		add(resetData);
		
		locX = new JLabel("X:----");
		locY = new JLabel("Y:----");
		locAngle = new JLabel("Angle:----");
		gyroAngle = new JLabel("Gyro Angle:----");
		encLeft = new JLabel("Encoder Left:----");
		encRight = new JLabel("Encoder Right:----");
		motorLeft = new JLabel("Motor Left:----");
		motorRight = new JLabel("Motor Right:----");
	
		locX.setLocation(10, 10);
		locY.setLocation(10, 40);
		locAngle.setLocation(10, 70);
		gyroAngle.setLocation(10, 100);
		encLeft.setLocation(10, 130);
		encRight.setLocation(10, 160);
		motorLeft.setLocation(10, 190);
		motorRight.setLocation(10, 220);
		
		locX.setSize(200, 30);
		locY.setSize(200, 30);
		locAngle.setSize(250, 30);
		gyroAngle.setSize(250, 30);
		encLeft.setSize(200, 30);
		encRight.setSize(200, 30);
		motorLeft.setSize(200, 30);
		motorRight.setSize(200, 30);
		
		
	
		frameData.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9'){
					isLookingAtTF = true;
					gotoPage(Integer.parseInt(frameData.getText() + e.getKeyChar()));
					isLookingAtTF = false;
				}
				else
					e.consume();
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		add(locX);
		add(locY);
		add(locAngle);
		add(gyroAngle);
		add(encLeft);
		add(encRight);
		add(motorLeft);
		add(motorRight);
		add(panel);
		add(frameData_outOf);
	}

	protected void importData() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Motion Data Logs (.mdl)", "mdl");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
			try {
				setData(chooser.getSelectedFile());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void setData(File selectedFile) throws IOException {
		
		FileInputStream fis = new FileInputStream(selectedFile);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		String line = br.readLine();
		m_env = MotionEnv.loadFromString(line);
		List<MotionProfile> list = new LinkedList<>();
		while((line = br.readLine()) != null){
			MotionProfile profile = MotionProfile.loadString(line);
			list.add(profile);
		}
		br.close();
		
		m_profiles = list;
		panel.m_robotPoints = new LinkedList<>();
		m_pageNum = 0;
		for (int i = 0; i < m_profiles.size(); i++){
			panel.m_robotPoints.add(m_profiles.get(i).m_x);
			panel.m_robotPoints.add(m_profiles.get(i).m_y);
		}
		gotoPage(0);
	}

	private void saveData(File file) throws IOException{
		if (!file.exists()){
			file.createNewFile();
		}
		
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		osw.write(m_env.toString());
		osw.write("\n");
		for (MotionProfile prof : m_profiles){
			osw.write(prof.toString());
			osw.write("\n");
		}
		osw.flush();
		fos.close();
	}

	protected void resetData() {
		m_env = null;
		panel.m_env = null;
		m_pageNum = 0;
		m_profiles = new LinkedList<>();
		panel.m_profile = null;
		panel.m_robotPoints = new LinkedList<>();
	
	}

	protected void exportData() {	
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Motion Data Logs (.mdl)", "mdl");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to save this file: " + chooser.getSelectedFile().getName());
			try {
				saveData(chooser.getSelectedFile());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void update() {
		Font font = getFont();
		String fData = "/" + m_profiles.size();
		if (!isLookingAtTF)
		frameData.setFont(font);
		leftPage.setFont(font);
		rightPage.setFont(font);
		lastPage.setFont(font);
		firstPage.setFont(font);
		resetData.setFont(font);
		exportData.setFont(font);
		importData.setFont(font);
		
		frameData_outOf.setText(fData);
		if (!isLookingAtTF)
		frameData.setText(""+(m_pageNum + 1));
		int width1 = getFontMetrics(font).stringWidth(fData);
		int width2 = getFontMetrics(font).stringWidth(frameData.getText());
		if (!isLookingAtTF)
		frameData.setLocation((getWidth() - width1 - width2 - 20) / 2, getHeight() - 50);
		
		frameData_outOf.setLocation((getWidth() - width1 -width2 - 20) / 2 + width2 + 15, getHeight() - 50);
		
		int width = width1 + width2 + 20;
		if (!isLookingAtTF)
		frameData.setSize(width2 + 15, 30);
		frameData_outOf.setSize(width1, 30);

		
		leftPage.setLocation((getWidth() - width) / 2 - 55, getHeight() - 50);
		firstPage.setLocation((getWidth() - width) / 2 - 110, getHeight() - 50);
		rightPage.setLocation((getWidth() + width) / 2 + 5, getHeight() - 50);
		lastPage.setLocation((getWidth() + width) / 2 + 60, getHeight() - 50);

		importData.setLocation((getWidth() + width) / 2 + 115, getHeight() - 50);
		exportData.setLocation((getWidth() + width) / 2 + 180, getHeight() - 50);
		resetData.setLocation((getWidth() - width) / 2 - 185, getHeight() - 50);
		
		
		panel.setSize(getWidth() - 25, getHeight() - 60);
		panel.setLocation(5, 5);
		if (m_pageNum != -1) {
			DecimalFormat format = new DecimalFormat("#.####");
			locX.setText("X:" + format.format(m_profiles.get(m_pageNum).m_x));
			locY.setText("Y:" + format.format(m_profiles.get(m_pageNum).m_y));
			locAngle.setText("Angle:" + format.format(m_profiles.get(m_pageNum).m_angle * 180 / Math.PI) + "deg");
			gyroAngle.setText("Gyro Angle:" + format.format(m_profiles.get(m_pageNum).m_gyroAngle * 180 / Math.PI) + "deg");
			encLeft.setText("Encoder Left:" + format.format(m_profiles.get(m_pageNum).m_encL));
			encRight.setText("Encoder Right:" + format.format(m_profiles.get(m_pageNum).m_encR));
			motorLeft.setText("Motor Left:" + format.format(m_profiles.get(m_pageNum).m_motorL));
			motorRight.setText("Motor Right:" + format.format(m_profiles.get(m_pageNum).m_motorR));
		}
	
	}

	protected void gotoPage(int page) {
		System.out.println("kk");
		if (page < m_profiles.size() && page >= 0) {
			setPage(page);
		}
	}

	protected void setPage(int page) {
		m_pageNum = page;
		panel.m_profile = m_profiles.get(page);
		panel.m_env = m_env;

		lastPage.setEnabled(page != m_profiles.size() - 1);
		firstPage.setEnabled(page != 0);
		rightPage.setEnabled(page != m_profiles.size() - 1);
		leftPage.setEnabled(page != 0);
		m_env.calculateMinMax(m_profiles.get(page).m_x, m_profiles.get(page).m_y);
		update();

	}

	protected void firstPage() {
		gotoPage(0);
	}

	protected void lastPage() {
		gotoPage(m_profiles.size() - 1);
	}

	protected void backPage() {
		gotoPage(m_pageNum - 1);
	}

	protected void nextPage() {
		gotoPage(m_pageNum + 1);
	}
}
