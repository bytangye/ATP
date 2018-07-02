package bupt.atp.app.ftp;

import android.content.Context;

import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.OutputStream;

import static bupt.atp.app.ftp.TransferStateChangedHandler.STATE_FINISH;
import static bupt.atp.app.ftp.TransferStateChangedHandler.STATE_NONE;
import static bupt.atp.app.ftp.TransferStateChangedHandler.STATE_START;

/**
 * Created by tangye on 2018/7/1.
 */

public class FTPTransferTask extends FTPRequestTask {

    private FTPErrorHandler             errorHandler        = null;
    private TransferStateChangedHandler stateChangedHandler = null;

    private String localFileName;
    private Context context;

    private static FTPClientFactory factory = FTPClientFactory.getInstance();

    public FTPTransferTask(String target, String localFileName, Context context) {
        super(target);
        this.localFileName = localFileName;
        this.context       = context;
    }

    @Override
    public void run() {
        FTPClient client = null;
        int       reply  = 0;

        try {
            client = factory.createClient();
            client.enterLocalPassiveMode();
//            reply = client.getReply();
//            if (!FTPReply.isPositiveCompletion(reply)) {
//                throw new IOException("Not positive complete");
//            }

            if (null != stateChangedHandler) {
                stateChangedHandler.handleTransferStateChanged(
                        localFileName, getTarget(), STATE_NONE, STATE_START
                );
            }

            /* 默认本地不存在同名文件 */
            //OutputStream outputStream = new FileOutputStream(localFileName, false);
            OutputStream outputStream = context.openFileOutput(localFileName, Context.MODE_APPEND);
            if (!client.retrieveFile(getTarget(), outputStream)) {
                throw new IOException("Function retrieveFile() failed to invoke.");
            }
            else {
                outputStream.flush();
                outputStream.close();

                if (null != stateChangedHandler) {
                    stateChangedHandler.handleTransferStateChanged(
                            localFileName, getTarget(), STATE_START, STATE_FINISH
                    );
                }
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

    public void setStateChangedHandler(TransferStateChangedHandler stateChangedHandler) {
        this.stateChangedHandler = stateChangedHandler;
    }
}
