package org.greenblitz.debug.gbgrapher;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class GBGrapher {

    private static void startGrapher(String path) throws IOException, InvalidCSVFileException {
        new FileGrapher(path).draw();
    }

    public static void run(String[] args) {
        File file;

        if (args.length == 0) {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            var chooser = new JFileChooser(".");
            var option = chooser.showOpenDialog(frame);

            if (option != JFileChooser.APPROVE_OPTION) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                return;
            }

            file = chooser.getSelectedFile();
        } else {
            file = new File(args[0]);
        }

        try {
            startGrapher(file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Could not find file " + file.getAbsolutePath());
        } catch (InvalidCSVFileException e) {
            System.err.println("csv file is malformed " + file.getAbsolutePath());
        }
    }

    public static void main(String[] args) {
        GBGrapher.run(args);
    }
}
