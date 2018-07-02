package bupt.atp.app.activities.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import bupt.atp.app.R;
import bupt.atp.app.adapters.DataAdapter;
import bupt.atp.app.data.FileAttribute;
import bupt.atp.app.ftp.FTPQueryResultHandler;
import bupt.atp.app.ftp.TransferStateChangedHandler;


/**
 * Created by tangye on 2018/6/26.
 */

public class DataFragment
        extends Fragment
        implements FTPQueryResultHandler, TransferStateChangedHandler {

    private DataAdapter         adapter           = null;
    private FTPRequestCommitter queryCommitter    = null;
    private FTPRequestCommitter downloadCommitter = null;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup      container,
                             Bundle         savedInstanceState) {

        View view = inflater.inflate(R.layout.info_layout, container, false);
        ListView dirListView = view.findViewById(R.id.info_fragment_listview);

        if (adapter == null) { adapter = new DataAdapter(getActivity()); }

        dirListView.setAdapter(adapter);

        dirListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(
                    final AdapterView<?> adapterView, View view, int index, long id
            ) {
                TextView textView = view.findViewById(R.id.info_listview_textview);
                final String name = textView.getText().toString();

                FileAttribute attr = (FileAttribute) adapter.getItem(index);
                String path = adapter.getFileSystem().getWorkingDir();

                if (attr.isDirectory() && null != queryCommitter) {
                    queryCommitter.commit(path + name + "/");
                }
                else if (!attr.isDirectory() && null != downloadCommitter) {
                    downloadCommitter.commit(path + name);
                }
            }
        });

        Button refresh = view.findViewById(R.id.info_fragment_refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == queryCommitter) { return; }
                queryCommitter.commit(adapter.getFileSystem().getWorkingDir());
            }
        });

        return view;
    }

    @Override
    public void handleQueryResult(String path, List<FileAttribute> result) {
        final String              finalPath   = path;
        final List<FileAttribute> finalResult = result;

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.getFileSystem().changeDir(finalPath, finalResult);
                adapter.update();
            }
        });
    }

    @Override
    public void handleTransferStateChanged(
            String localPath, String remotePath, int formerState, int state
    ) {
        if (STATE_START == formerState && STATE_FINISH == state) {
            // todo 使用其他应用打开下载下来的文件
            Log.wtf("DataFragment", "what a fuck. Finished.");
        }
    }

    public DataAdapter getAdapter() {
        return adapter;
    }

    public void setQueryCommitter(FTPRequestCommitter queryCommitter) {
        this.queryCommitter = queryCommitter;
    }

    public void setDownloadCommitter(FTPRequestCommitter downloadCommitter) {
        this.downloadCommitter = downloadCommitter;
    }
}
