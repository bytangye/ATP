package bupt.atp.app.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bupt.atp.app.adapters.FileAttribute;


/**
 * Created by tangye on 2018/7/1.
 */

public class FTPQueryTask extends FTPRequestTask {

    private FTPErrorHandler       errorHandler = null;
    private FTPQueryResultHandler resultHandler = null;

    private static FTPClientFactory factory = FTPClientFactory.getInstance();

    public FTPQueryTask(String target) {
        super(target);
    }

    @Override
    public void run() {

        FTPClient client = null;
        int       reply  = 0;

        try {
            client = factory.createClient();

            if (null == client) {
                throw new IOException("Failed to connect to server.");
            }

            //todo check reply code
//            reply = client.getReply();
//            if (!FTPReply.isPositiveCompletion(reply)) {
//                throw new IOException("Not positive complete");
//            }

            FTPFile[] files = client.listFiles(getTarget());

            List<FileAttribute> attrs = new ArrayList<>(files.length);
            for (FTPFile each : files) {
                attrs.add(new FileAttribute(each.getName(), each.isDirectory()));
            }

            if (null != resultHandler) {
                resultHandler.handleQueryResult(attrs);
            }
        }

        catch (IOException ex) {
            if (null != errorHandler) {
                errorHandler.handleFTPError(this, reply, ex);
            }
        }

        finally {
            try {
                client.logout();
            }
            catch (IOException ignored) { }

            try {
                client.disconnect();
            }
            catch (IOException ignored) { }
        }
    }

    public void setErrorHandler(FTPErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void setResultHandler(FTPQueryResultHandler resultHandler) {
        this.resultHandler = resultHandler;
    }
}
