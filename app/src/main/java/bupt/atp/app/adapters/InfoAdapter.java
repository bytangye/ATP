package bupt.atp.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bupt.atp.app.R;
import bupt.atp.app.Search;


/**
 * Created by tangye on 2018/6/26.
 */

public class InfoAdapter extends BaseAdapter {

    public List<String>        workingDir = new ArrayList<>();
    private List<FileAttribute> fileAttrs;
    private Context             mContext;
    public Search searchListener = null;

    public void setSearchListener(Search searchListener){
        this.searchListener = searchListener;
    }

    public InfoAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void initData(List<FileAttribute> rootFileAttrs){
        this.fileAttrs  = rootFileAttrs;
    }

    public void update() {
        notifyDataSetChanged();
    }

    public void gotoChildDir(int index, List<FileAttribute> fileAttrs) {
        this.workingDir.add(fileAttrs.get(index).getFileName());
        this.fileAttrs = fileAttrs;
    }

    public void gotoParentDir(int index, List<FileAttribute> fileAttrs) {
        this.workingDir.remove(this.workingDir.size() - 1);
        this.fileAttrs = fileAttrs;
    }

    public String getCurrentPath(){
        String path = "";
        for (String each : workingDir) {
            path += each + "/";
        }
        return path;
    }

    @Override
    public int getCount() {
        if(fileAttrs != null) {
            return fileAttrs.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.info_list_layout,viewGroup,false);
        TextView txt_aName = (TextView) view.findViewById(R.id.info_listview_textview);


        txt_aName.setText(fileAttrs.get(i).getFileName());
        return view;

    }

    public String gotoParent() {
        this.workingDir.remove(this.workingDir.size() - 1);

        String path = "/";
        for (String each : workingDir) {
            path += each + "/";
        }

        return path;
    }


    public List<FileAttribute> getFileAttrs() {
        return fileAttrs;
    }

    public void setFileAttrs(List<FileAttribute> fileAttrs) {
        this.fileAttrs = fileAttrs;
    }
}
