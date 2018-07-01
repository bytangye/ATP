package bupt.atp.app.fragments;

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

import bupt.atp.app.FragmentReflash;
import bupt.atp.app.R;
import bupt.atp.app.Search;
import bupt.atp.app.adapters.FileAttribute;
import bupt.atp.app.adapters.InfoAdapter;


/**
 * Created by tangye on 2018/6/26.
 */

public class InfoFragment extends Fragment {
    Search search = null;

    FragmentReflash fragmentReflash = null;
    public void setFragmentReflash(FragmentReflash listener){
        this.fragmentReflash = listener;
    }

    InfoAdapter adapter = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.info_layout, container, false);

        ListView dirListView = view.findViewById(R.id.info_fragment_listview);
        if(adapter == null) {
            adapter = new InfoAdapter(getActivity());
        }

        adapter.setSearchListener(search);

        dirListView.setAdapter(adapter);

        //todo 点击每个Item的响应
        dirListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Item", "Click");
                TextView textView = view.findViewById(R.id.info_listview_textview);
                String name = textView.getText().toString();
                for(int j = 0;j<adapter.getFileAttrs().size();j++){
                    if(adapter.getFileAttrs().get(j).getFileName().equals(name)){
                        if(adapter.getFileAttrs().get(j).isDirectory()){
                            if(adapter.searchListener!=null){
                                Log.d("Item", "NewDict");
                                String path = adapter.getCurrentPath();
                                path += name;
                                adapter.searchListener.onSearchNewDict(path);
                                adapter.workingDir.add(name + '/');
                                break;
                            }
                        }
                    }
                }
            }
        });


        Button reflash = view.findViewById(R.id.info_fragment_refresh);
        reflash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentReflash.onReflashClick(getCurrentPath());
            }
        });



        return view;
    }



    public void updateList(List<FileAttribute> list){
        adapter.initData(list);
        adapter.update();
    }

    public void setListener(Search listener){
        search = listener;
    }

    public String getCurrentPath(){
        return adapter.getCurrentPath();
    }

    public String backToParentPath(){
        return adapter.gotoParent();
    }

}
