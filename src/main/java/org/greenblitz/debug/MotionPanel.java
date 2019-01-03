package org.greenblitz.debug;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

public class MotionPanel extends JPanel {
	
	protected MotionProfile m_profile;
	protected MotionEnv m_env;
	protected List<Double> m_robotPoints = new LinkedList<>();
	
	
	
	protected Color m_pathPointColor = Color.black;
	protected int m_pathPointRadius = 6;
	protected Color m_robotColor = Color.blue;
	protected Point m_robotSize = new Point(30, 30);
	protected Color m_selectedPointColor = Color.green;
	protected int m_selectedPointRadius = 12;
	protected Color m_selectedLineColor = Color.RED;
	protected int m_selectedLineWidth = 6;
	protected Color m_pastPathColor = new Color(180, 0, 255);
	protected int m_pastPathPointSize = 6;
	
	public void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
	   Graphics2D g2 = (Graphics2D)g;
	    RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);
		if (m_env != null)
			paintEnv(g2);
		if (m_profile != null)
			paintProfile(g2);
	}

	private void paintEnv(Graphics g) {
		List<Double> copy = new LinkedList<>();
		copy.addAll(m_env.m_points);
		Iterator<Double> it = copy.iterator();
		g.setColor(m_pathPointColor);
		for (int i = 0; i < m_env.m_points.size(); i+=2){
			Point point = getPoint(it.next(), it.next());	
			((Graphics2D)g).fill(new Ellipse2D.Double(point.x, point.y, m_pathPointRadius, m_pathPointRadius));
		}
		
		
	}
	
	private Point getPoint(double x, double y){
		double rawLengthX = (m_env.m_maxX - m_env.m_minX); 
		double rawLengthY = (m_env.m_maxY - m_env.m_minY);

		double minX = m_env.m_minX - rawLengthX * (m_env.m_mulOffX - 1);
		double minY = m_env.m_minY - rawLengthY * (m_env.m_mulOffY - 1);
		

		double lengthX = rawLengthX * (m_env.m_mulOffX * 2 - 1);
		double lengthY = rawLengthY * (m_env.m_mulOffY * 2 - 1);

		double relativeX = (x - minX) / lengthX;
		double relativeY = (y - minY) / lengthY;

		return new Point((int)(relativeX * getWidth()), (int)(relativeY * getHeight()));

	}

	private void paintProfile(Graphics g) {
		
	
		//draw robot
		g.setColor(m_robotColor);
		Point robotPoint = getPoint(m_profile.m_x, m_profile.m_y);
		double robX = robotPoint.x;
		double robY = robotPoint.y;
		double sizeX = m_robotSize.x;
		double sizeY = m_robotSize.y;	
		
		Graphics2D g2d = (Graphics2D)g.create();
	    g2d.setColor(m_robotColor);
	    Rectangle2D.Double rect2 = 
	    		new Rectangle2D.Double(robX - sizeX / 2, robY - sizeY / 2, sizeX, sizeY);

	    g2d.rotate(m_profile.m_angle,  rect2.x + rect2.width/2, rect2.y + rect2.height/2);
	  //  g2d.draw(rect2);
	    g2d.fill(rect2);
	    g2d.dispose();
		
	//    g2d.rotate(-m_profile.m_angle);
		//draw line from robot to currentPoint
		if (m_profile.m_pointIdx != -1 && m_env.m_points.size() != 0){
			double ptX = m_env.m_points.get(m_profile.m_pointIdx * 2);
			double ptY = m_env.m_points.get(m_profile.m_pointIdx * 2 + 1);
			Point selectedPoint = getPoint(ptX,ptY);
			g.setColor(m_selectedLineColor);
			for (int i = 0; i < m_selectedLineWidth; i++){
				g.drawLine(robotPoint.x - m_selectedLineWidth/2 + i, robotPoint.y, selectedPoint.x - m_selectedLineWidth/2 + i, selectedPoint.y);
			}
			g.setColor(m_selectedPointColor);
			int diff =(m_selectedPointRadius - m_pathPointRadius) / 2;
			((Graphics2D)g).fill(new Ellipse2D.Double(selectedPoint.x - diff, selectedPoint.y - diff, m_selectedPointRadius, m_selectedPointRadius));
		}
		
		//draw robot past path
		g.setColor(m_pastPathColor);
		List<Double> cpy = new LinkedList<>();
		synchronized (m_robotPoints){
			cpy.addAll(m_robotPoints);
		}
		
		
		for (int idx = 0; idx < m_robotPoints.size(); idx+=2){
			Point pt = getPoint(cpy.get(idx), cpy.get(idx + 1));
			((Graphics2D)g).fill(new Ellipse2D.Double(pt.x, pt.y, m_pastPathPointSize, m_pastPathPointSize));
		}
	}

	public void addRobotLocation() {
		m_robotPoints.add(m_profile.m_x);
		m_robotPoints.add(m_profile.m_y);
	}
}
