package bupt.atp.app;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.List;

import bupt.atp.app.adapters.FileAttribute;
import bupt.atp.app.ftp.FTPQueryResultHandler;
import bupt.atp.app.ftp.FTPQueryTask;
import bupt.atp.app.utils.SimpleThreadPool;


/**
 * Created by tangye on 2018/6/26.
 */

public class CommService extends Service {

    MessageListener listener = null;

    public void setListener(MessageListener l){
        listener = l;
    }


    public class CommBinder extends Binder {
        CommService getService() {
            return CommService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new CommBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void startQueryFiles(String path) {

//        new Thread(new CommTask(path, CommTask.TASK_QUERY) {
//            @Override
//            public void handleDirectoryList(List<FileAttribute> attrs) {
//                if(listener != null) {
//                    listener.onDictChanged(attrs);
//                }
//                Log.d("CommService:", "" + attrs.size());
//            }
//
//            @Override
//            public void handleFileDownloadCompleted(String absolutePath) {
//
//            }
//
//        }).start();

        FTPQueryTask task = new FTPQueryTask(path);

        task.setResultHandler(new FTPQueryResultHandler() {
            @Override
            public void handleQueryResult(List<FileAttribute> result) {
                if (listener != null) {
                    listener.onDictChanged(result);
                }
                //Log.d("CommService:", "result size: " + result.size());
            }
        });

        SimpleThreadPool.execute(task);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
