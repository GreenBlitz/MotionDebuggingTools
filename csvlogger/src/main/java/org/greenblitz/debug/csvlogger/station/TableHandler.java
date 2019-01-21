package org.greenblitz.debug.csvlogger.station;

import edu.wpi.first.networktables.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import static org.greenblitz.debug.csvlogger.common.CSVLoggerTables.NT_ROOT_TABLE;
import static org.greenblitz.debug.csvlogger.common.CSVLoggerTables.NT_UPDATE;

public class TableHandler {

    private CSVPrinter m_filePrinter = null;
    private Set<TableHandler> m_children = new HashSet<>();
    private NetworkTable m_handledTable;
    private String[] m_entries;
    private NetworkTableEntry m_update;
    private Path m_path;
    private File m_file;

    public static TableHandler getRootHandler(NetworkTableInstance instance) {
        return new TableHandler(instance.getTable(NT_ROOT_TABLE), Paths.get(""), "root");
    }

    public static TableHandler getRootHnadler() {
        return getRootHandler(NetworkTableInstance.getDefault());
    }

    private TableHandler(NetworkTable handledTable, Path path, String name) {
        m_handledTable = handledTable;
        m_handledTable.addSubTableListener(this::handleSubTable,false);
        m_handledTable.addEntryListener(NT_UPDATE, this::handleUpdate, EntryListenerFlags.kUpdate);

        m_update = m_handledTable.getEntry(NT_UPDATE);

        var keys = handledTable.getKeys();
        m_entries = new String[keys.size()];
        keys.toArray(m_entries);

        m_path = path.resolve(name);
        m_file = new File(m_path.toFile(), name + ".csv");
    }

    public TableHandler mkChild(String name) {
        return mkChild(m_handledTable.getSubTable(name), name);
    }

    private TableHandler mkChild(NetworkTable child, String name) {
        TableHandler ret = new TableHandler(child, m_path, name);
        m_children.add(ret);
        return ret;
    }

    private void handleSubTable(NetworkTable parent, String name, NetworkTable created) {
        mkChild(created, name);
    }

    private void handleUpdate(NetworkTable table, String key, NetworkTableEntry entry, NetworkTableValue value, int flags) {
        if (entry.getBoolean(false)) {
            try {
                update();
            } catch (IOException e) {
                System.err.println("ERROR failed to handle file while attempting to flush to csv: " + e.getMessage());
            }
        }
    }

    private void update() throws IOException {
        flush();
        for (var handler : m_children) handler.update();
        m_update.setBoolean(true);
    }

    private void flush() throws IOException {
        if (m_filePrinter == null) initFile();

        Double[] record = new Double[m_entries.length];

        for (var i = 0; i < record.length; i++)
            record[i] = m_handledTable.getEntry(m_entries[i]).getDouble(0);

        m_filePrinter.printRecords((Object[]) record);
    }

    private void initFile() throws IOException {
        m_filePrinter = CSVFormat.DEFAULT.withHeader(m_entries).print(m_file, Charset.defaultCharset());
    }
}
