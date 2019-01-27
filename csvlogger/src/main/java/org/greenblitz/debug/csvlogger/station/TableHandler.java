package org.greenblitz.debug.csvlogger.station;

import edu.wpi.first.networktables.*;
import org.greenblitz.debug.csvlogger.common.exception.DirectoryCreationException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.greenblitz.debug.csvlogger.common.CSVLoggerTables.*;

public class TableHandler {

    private static TableHandler defaultHandler = null;

    public static TableHandler getInstance() {
        return defaultHandler;
    }

    public static void init() throws DirectoryCreationException {
        defaultHandler = new TableHandler(NT_ROOT_NAME, ROOT);
    }


    private NetworkTable m_values;
    private File m_dir;
    private Map<String, EntryHandler> m_handlers = new HashMap<>();

    public TableHandler(String name, NetworkTable table) throws DirectoryCreationException {
        File dir = new File(name);

        if (!dir.isDirectory()) {
            if (!dir.mkdir()) {
                throw new DirectoryCreationException(name);
            }
        }

        m_dir = dir;

        NetworkTable names = table.getSubTable(NT_NAMES_TABLE);
        m_values = table.getSubTable(NT_VALUES_TABLE);

        for (var entryName : names.getKeys()) {
            addHandler(entryName, names.getEntry(entryName).getValue().getStringArray());
        }

        names.addEntryListener(this::handleNewName, EntryListenerFlags.kNew);
    }

    private void handleNewName(NetworkTable table, String key, NetworkTableEntry entry, NetworkTableValue value, int flags) {
        addHandler(key, value.getStringArray());
    }

    private void addHandler(String key, String[] names) {
        System.out.printf("Entry '%s':%s was added to the logger's watch!\n", key, Arrays.toString(names));
        var valuesEntry = m_values.getEntry(key);
        try {
            File dest = m_dir.toPath().resolve(key + ".csv").toFile();
            m_handlers.put(key, new EntryHandler(dest, names, valuesEntry));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
