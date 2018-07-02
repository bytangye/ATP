package bupt.atp.app.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import bupt.atp.app.ftp.FTPErrorHandler;
import bupt.atp.app.ftp.FTPQueryResultHandler;
import bupt.atp.app.ftp.FTPQueryTask;
import bupt.atp.app.ftp.FTPTransferTask;
import bupt.atp.app.ftp.TransferStateChangedHandler;
import bupt.atp.app.utils.SimpleThreadPool;


/**
 * Created by tangye on 2018/6/26.
 */

public class FTPService extends Service {

    private FTPQueryResultHandler queryResultHandler = null;
    private FTPErrorHandler       queryErrorHandler  = null;

    private TransferStateChangedHandler stateChangedHandler  = null;
    private FTPErrorHandler             transferErrorHandler = null;

    public class InnerBinder extends Binder {
        public FTPService getService() {
            return FTPService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new InnerBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void startQueryFiles(String path) {
        FTPQueryTask task = new FTPQueryTask(path);
        task.setResultHandler(queryResultHandler);
        task.setErrorHandler(queryErrorHandler);
        SimpleThreadPool.execute(task);
    }

    public void startDownloadFile(String localPath, String remotePath) {
        FTPTransferTask task = new FTPTransferTask(remotePath, localPath);
        task.setStateChangedHandler(stateChangedHandler);
        task.setErrorHandler(transferErrorHandler);
        SimpleThreadPool.execute(task);
    }

    public void setQueryResultHandler(FTPQueryResultHandler queryResultHandler) {
        this.queryResultHandler = queryResultHandler;
    }

    public void setQueryErrorHandler(FTPErrorHandler queryErrorHandler) {
        this.queryErrorHandler = queryErrorHandler;
    }

    public void setStateChangedHandler(TransferStateChangedHandler stateChangedHandler) {
        this.stateChangedHandler = stateChangedHandler;
    }

    public void setTransferErrorHandler(FTPErrorHandler transferErrorHandler) {
        this.transferErrorHandler = transferErrorHandler;
    }
}
