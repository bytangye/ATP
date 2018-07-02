package bupt.atp.app.ftp;

/**
 * Created by tangye on 2018/7/1.
 */

public interface TransferStateChangedHandler {

    int STATE_NONE    = 0;
    int STATE_START   = 1;
    int STATE_WORKING = 2;
    int STATE_FINISH  = 3;

    void handleTransferStateChanged(
            String localPath  , String remotePath,
            int    formerState, int    state
    );
}
