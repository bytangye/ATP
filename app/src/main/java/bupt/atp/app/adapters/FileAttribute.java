package bupt.atp.app.adapters;

/**
 * Created by tangye on 2018/6/26.
 */

public class FileAttribute {

    private String  fileName;
    private boolean isDirectory;

    public FileAttribute(String fileName, boolean isDirectory) {
        this.fileName = fileName;
        this.isDirectory = isDirectory;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

}
