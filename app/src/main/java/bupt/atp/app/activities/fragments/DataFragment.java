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


/**
 * Created by tangye on 2018/6/26.
 */

public class DataFragment extends Fragment implements FTPQueryResultHandler {

    private DataAdapter          adapter = null;
    private PositiveQueryHandler handler = null;

    public DataFragment() {
        //this.adapter = new DataAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup      container,
                             Bundle         savedInstanceState) {

        View view = inflater.inflate(R.layout.info_layout, container, false);

        ListView dirListView = view.findViewById(R.id.info_fragment_listview);

        if (adapter == null) { adapter = new DataAdapter(getActivity()); }

        dirListView.setAdapter(adapter);

        //todo 点击每个Item的响应
        dirListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(
                    final AdapterView<?> adapterView, View view, int index, long id
            ) {

                Log.d("Item", "Click");

                TextView textView = view.findViewById(R.id.info_listview_textview);
                final String name = textView.getText().toString();

                for (int j = 0; j < adapter.getCount(); j++) {

                    FileAttribute attr = (FileAttribute) adapter.getItem(j);

                    if (
                        attr.getFileName().equals(name) &&
                        attr.isDirectory()              &&
                        null != handler
                    ) {
                        String path = adapter.getFileSystem().getWorkingDir();
                        handler.handlePositiveQuery(path + name + "/");
                        break;
                    }
                }
            }
        });

        Button refresh = view.findViewById(R.id.info_fragment_refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == handler) { return; }
                handler.handlePositiveQuery(adapter.getFileSystem().getWorkingDir());
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

    public DataAdapter getAdapter() {
        return adapter;
    }

    public void setHandler(PositiveQueryHandler handler) {
        this.handler = handler;
    }
}
