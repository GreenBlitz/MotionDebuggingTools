package org.greenblitz.debug.gbgrapher;

import de.erichseifert.gral.data.DataSeries;
import de.erichseifert.gral.graphics.Insets2D;
import de.erichseifert.gral.graphics.Label;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.DefaultPointRenderer2D;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.IOException;

public class FileGrapher extends JFrame {

    private static final Color[] COLORS = {
            Color.BLUE,
            Color.RED,
            Color.ORANGE,
            Color.GREEN,
            Color.MAGENTA,
            Color.BLACK,
            Color.YELLOW,
            Color.PINK,
            Color.CYAN,
            Color.DARK_GRAY,
    };

    private LoadedTable m_dataTable;
    private DataSeries[] m_series;
    private String name;

    public FileGrapher(String path) throws IOException, InvalidCSVFileException {
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        name = path;
        load(path);
    }

    private void load(String path) throws IOException, InvalidCSVFileException {
        m_dataTable = LoadedTable.mkTable(path);
        m_series = new DataSeries[m_dataTable.names.size() - 1];

        for (var i = 1; i < m_dataTable.names.size(); i++) {
            m_series[i - 1] = new DataSeries(m_dataTable.names.get(i), m_dataTable, 0, i);
        }
    }

    public void draw() {
        getContentPane().add(new InteractivePanel(mkPlot()), BorderLayout.CENTER);
        setVisible(true);
    }

    private XYPlot mkPlot() {
        XYPlot plot = new XYPlot(m_series);

        // Modify point and line renderers
        PointRenderer point;
        LineRenderer line;

        for (var i = 0; i < m_series.length; i++) {
            line = new DefaultLineRenderer2D();
            line.setColor(COLORS[i]);
            plot.setLineRenderers(m_series[i], line);

            point = new DefaultPointRenderer2D();
            point.setColor(COLORS[i]);
            point.setShape(new Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0));

            plot.setPointRenderers(m_series[i], point);
        }


        // Modify axes
        var x = plot.getAxisRenderer(XYPlot.AXIS_X);
        var y = plot.getAxisRenderer(XYPlot.AXIS_Y);

        x.setLabel(new Label(m_dataTable.names.get(0)));

        x.setIntersection(-Double.MAX_VALUE);
        y.setIntersection(-Double.MAX_VALUE);


        // Modify insets
        double insetsTop = 20.0,
                insetsLeft = 60.0,
                insetsBottom = 60.0,
                insetsRight = 40.0;
        plot.setInsets(new Insets2D.Double(
                insetsTop, insetsLeft, insetsBottom, insetsRight));

        // Add legend
        plot.setLegendVisible(true);

        plot.getTitle().setText(name);

        return plot;
    }
}
