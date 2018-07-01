package bupt.atp.app;

import android.util.Log;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bupt.atp.app.adapters.FileAttribute;

/**
 * Created by tangye on 2018/6/26.
 */

public abstract class CommTask implements Runnable {

    public static final int TASK_QUERY = 0;
    public static final int TASK_LOAD  = 1;

    private String path;
    private int    type;

    public CommTask(String path, int type) {
        this.path = path;
        this.type = type;
    }

    public abstract void handleDirectoryList(List<FileAttribute> attrs);
    public abstract void handleFileDownloadCompleted(String absolutePath);


    @Override
    public void run() {

        FTPClient client = new FTPClient();

        try {
            client.connect("192.168.1.102", 21);
            client.login("ty", "3856");

            //client.doCommand("opts","utf8 off");

            client.setAutodetectUTF8(true);

            if (TASK_QUERY == type) {
                FTPFile[] files = client.listFiles(path);
                Log.d("CommTask:", path + " " +  files.length);

                List<FileAttribute> attrs = new ArrayList<>();
                for (FTPFile each : files) {
                    attrs.add(new FileAttribute(each.getName(), each.isDirectory()));
                }

                handleDirectoryList(attrs);
            }
            else if (TASK_LOAD == type) {
                //client
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                client.logout();
                client.disconnect();
            }
            catch (IOException ignored) { }
        }
    }

    public String getPath() {
        return path;
    }

    public int getType() {
        return type;
    }
}
