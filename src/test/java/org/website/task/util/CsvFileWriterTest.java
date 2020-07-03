package org.website.task.util;

import com.opencsv.CSVWriter;
import org.easymock.EasyMock;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvFileWriterTest {

    @Test
    public void writeDataToCsvFileTest() throws Exception {
        List<String[]> dataList = new ArrayList<>();
        String[] st = new String[5];
        st[0] = "hai";
        dataList.add(st);
        File fileMock = EasyMock.createMock(File.class);
        CSVWriter writer = new CSVWriter(new StringWriter(),
                CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
        EasyMock.expect(fileMock.toPath()).andReturn(Paths.get("test.csv"));
        PowerMockito.mock(CSVWriter.class);
        PowerMockito.mock(FileWriter.class);
        PowerMockito.whenNew(FileWriter.class).withArguments(new File("test.csv").toPath().toString()).thenReturn(null);
        PowerMockito
                .whenNew(CSVWriter.class)
                .withArguments(null, CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER).thenReturn(writer);
        boolean expected = false;
        CsvFileWriter.writeDataToCsvFile(fileMock.toPath().toString(), dataList);
    }
}
