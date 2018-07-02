package bupt.atp.app.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Maou on 2018/7/1.
 */

public class FileSystem {

    private List<String>        workingDir = null;
    private List<FileAttribute> fileAttrs  = null;

    public FileSystem() {
        this.workingDir = new ArrayList<>();
        this.fileAttrs  = new ArrayList<>();
    }

    public boolean isRoot() { return workingDir.isEmpty(); }

    public boolean containDir(String dirName) {
        for (FileAttribute each : fileAttrs) {
            if (each.isDirectory() && each.getFileName().equals(dirName)) {
                return true;
            }
        }
        return false;
    }

    public boolean containFile(String fileName) {
        for (FileAttribute each : fileAttrs) {
            if (each.getFileName().equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    public boolean gotoChildDir(String dirName, List<FileAttribute> content) {
        if (null == fileAttrs || !containDir(dirName)) { return false; }

        workingDir.add(dirName);
        fileAttrs = content;
        return true;
    }

    public boolean gotoParent(List<FileAttribute> content) {
        if (isRoot()) { return false; }

        workingDir.remove(workingDir.size() - 1);
        fileAttrs = content;
        return true;
    }

    public void changeDir(String path, List<FileAttribute> content) {
        this.workingDir = Arrays.asList(path.split("/"));
        this.fileAttrs  = content;
    }

    public String getWorkingDir() {
        if (isRoot()) { return "/"; }

        StringBuffer buffer = new StringBuffer();
        for (String each : workingDir) {
            buffer.append(each);
            buffer.append('/');
        }

        return buffer.toString();
    }

    public String getParentPath() {
        if (workingDir.size() < 2) { return "/"; }

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < workingDir.size() - 1; ++i) {
            buffer.append(workingDir.get(i));
            buffer.append('/');
        }

        return buffer.toString();
    }

    public List<FileAttribute> getFiles() { return fileAttrs;  }

    public void setFileAttrs(List<FileAttribute> fileAttrs) {
        this.fileAttrs = fileAttrs;
    }

    public FileAttribute getFile(int index) { return fileAttrs.get(index); }
}
