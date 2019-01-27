package org.greenblitz.debug.csvlogger.station;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.NetworkTableEntry;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedList;

public class EntryHandler {
    private CSVPrinter m_filePrinter;

    EntryHandler(File location, String[] names, NetworkTableEntry values) throws IOException {
        System.out.printf("Entry handler created at %s, tracking %s\n", location, Arrays.toString(names));
        m_filePrinter = CSVFormat.EXCEL.withHeader(names).print(location, Charset.defaultCharset());
        m_filePrinter.flush();
        values.addListener(this::handleValueChange, EntryListenerFlags.kUpdate);
    }

    private void handleValueChange(EntryNotification values) {
        System.out.println("Value update: " + values.getEntry().getName());
        var res = values.getEntry().getValue().getDoubleArray();

        var add = new LinkedList<Double>();
        for (var result : res) add.add(result);

        try {
            m_filePrinter.printRecord(add);
            m_filePrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
