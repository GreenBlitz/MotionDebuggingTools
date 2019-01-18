package org.greenblitz.debug.guydebugger.station;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.List;

import javax.swing.JPanel;

public class MotionGraph extends JPanel{
	
	public double m_minX, m_minY, m_maxX, m_maxY, m_stepX, m_thickness;
	
	private double curX;
	
	private Color m_graphColor;
	
	public List<Double> m_points;
	
	private Color m_bgColor = Color.WHITE;
	
	public void setGraphThickness(double thickness){
		m_thickness = thickness;
	}
	
	public void setDomain(double minX, double minY, double maxX, double maxY){
		m_minX = minX;
		m_minY = minY;
		m_maxX = maxX;
		m_maxY = maxY;
	}
	
	public void addValue(double value){
		addPoint(curX + m_stepX, value);
	}
	
	public void paintComponent(Graphics g){
		g.setColor(m_bgColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		if (m_points.size() > 0)
			paintGraph(g);
	}
	
	private void paintGraph(Graphics g) {
		g.setColor(m_graphColor);
		Point lastPoint = getPoint(m_points.get(0), m_points.get(1));	
		Graphics2D g2 = ((Graphics2D)g);
		Stroke curStroke = g2.getStroke();
		g2.setStroke(new BasicStroke((float) m_thickness));
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);

		for (int i = 0; i < m_points.size(); i+=2){
			Point point = getPoint(m_points.get(i), m_points.get(i + 1));

			g.drawLine(lastPoint.x, lastPoint.y, point.x, point.y);
			lastPoint = point;

		}
		g2.setStroke(curStroke);
	}

	protected void addPoint(double x, double y){
		m_points.add(x);
		m_points.add(y);
		calculateMinMax(x,y);
	}
	
	public void calculateMinMax(double x, double y){
		m_minX = Math.min(m_minX, x - 0.125);
		m_minY = Math.min(m_minY, y - 0.125);
		m_maxX = Math.max(m_maxX, x + 0.125);
		m_maxY = Math.max(m_maxY, y + 0.125);
	}
	
	private Point getPoint(double x, double y){
		double lengthX = (m_maxX - m_minX); 
		double lengthY = (m_maxY - m_minY);

		double minX = m_minX;
		double minY = m_minY;

		double relativeX = (x - minX) / lengthX;
		double relativeY = (y - minY) / lengthY;

		return new Point((int)(relativeX * getWidth()), (int)(relativeY * getHeight()));

	}
}
