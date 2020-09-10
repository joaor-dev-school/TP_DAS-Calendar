package tp.das.DTOs.Store;

import java.util.List;

public class FilenamesListResponseDTO {
    private List<String> filenames;

    public FilenamesListResponseDTO(List<String> filenames) {
        this.filenames = filenames;
    }

    public List<String> getFilenames() {
        return filenames;
    }

    public void setFilenames(List<String> filenames) {
        this.filenames = filenames;
    }
}
