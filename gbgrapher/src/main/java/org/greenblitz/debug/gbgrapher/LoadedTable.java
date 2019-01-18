package org.greenblitz.debug.gbgrapher;

import de.erichseifert.gral.data.DataTable;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class LoadedTable extends DataTable {

    public final List<String> names;

    private LoadedTable(CSVParser records, Class<Double>... classes) throws InvalidCSVFileException {
        super(classes);

        names = new ArrayList<>(records.getHeaderMap().keySet());
        Double[] data = new Double[classes.length];

        for (var record : records) {
            if (record.size() != data.length) {
                throw new InvalidCSVFileException();
            }

            for (var i = 0; i < names.size(); i++) {
                data[i] = Double.valueOf(record.get(names.get(i)));
            }
            add(data);
        }
    }

    @SuppressWarnings("unchecked")
    public static LoadedTable mkTable(String path) throws IOException, InvalidCSVFileException {
        try (Reader in = new FileReader(path)) {
            var records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);

            Class<Double>[] doubles = new Class[records.getHeaderMap().keySet().size()];
            for (var i = 0; i < doubles.length; i++) {
                doubles[i] = Double.class;
            }

            return new LoadedTable(records, doubles);
        }
    }
}

