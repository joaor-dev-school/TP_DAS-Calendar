package tp.das.Model.Store.Adapter;

import tp.das.Model.Store.Model.ICalendarStoreData;

import java.io.IOException;
import java.util.List;

public interface ICalendarDataStoreAdapter {
     void write(String filename, ICalendarStoreData data) throws IOException;
    ICalendarStoreData read(String filename) throws IOException;
    List<String> filenames() throws IOException;
    void deleteFile(String filename) throws IOException;
}
