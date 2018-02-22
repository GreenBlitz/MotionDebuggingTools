package com.gbmotion.debug;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.function.BiConsumer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MotionGraphPanel extends JPanel {

	private MotionGraph m_graph;

	private BiConsumer<MotionGraph, MotionProfile> m_onUpdate;

	private JTextField minX, minY, maxX, maxY;

	private JLabel labelMinX, labelMinY, labelMaxX, labelMaxY;
	
	private JButton setDomain;
	

	
	public MotionGraphPanel(BiConsumer<MotionGraph, MotionProfile> onUpdate) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setLayout(null);
		m_graph = new MotionGraph();
		m_onUpdate = onUpdate;

		labelMinX = new JLabel("From X:");
		labelMinY = new JLabel("From Y:");
		labelMaxX = new JLabel("To X:");
		labelMaxY = new JLabel("To Y:");
		
		minX = new JTextField();
		minY = new JTextField();
		maxX = new JTextField();
		maxY = new JTextField();
		
		setDomain = new JButton("Set Domain!");
		
		
		labelMinX.setSize(80, 30);
		labelMinY.setSize(80, 30);
		labelMaxX.setSize(60, 30);
		labelMaxY.setSize(60, 30);
		
		minX.setSize(80, 30);
		minY.setSize(80, 30);
		maxX.setSize(80, 30);
		maxY.setSize(80, 30);
		
		setDomain.setSize(120, 30);
		
		setDomain.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				m_graph.m_minX = Double.parseDouble(minX.getText());
				m_graph.m_minY = Double.parseDouble(minY.getText());
				m_graph.m_maxX = Double.parseDouble(maxX.getText());
				m_graph.m_maxY = Double.parseDouble(maxY.getText());
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		
		minX.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() != '.' && e.getKeyChar() != '-' && (e.getKeyChar() < '0' || e.getKeyChar() > '9')) {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		minY.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() != '.' && e.getKeyChar() != '-' && (e.getKeyChar() < '0' || e.getKeyChar() > '9')) {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		
		maxX.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() != '.' && e.getKeyChar() != '-' && (e.getKeyChar() < '0' || e.getKeyChar() > '9')) {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		
		maxY.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() != '.' && e.getKeyChar() != '-' && (e.getKeyChar() < '0' || e.getKeyChar() > '9')) {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}

	public void updateProfile(MotionProfile profile) {
		m_onUpdate.accept(m_graph, profile);
	}

	public void update() {
		m_graph.setSize(getWidth() - 10, getHeight() - 80);

	}

	public void reset() {
		m_graph.m_points = new LinkedList<>();
	}

}
