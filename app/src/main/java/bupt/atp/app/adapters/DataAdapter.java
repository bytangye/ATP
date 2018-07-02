package bupt.atp.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import bupt.atp.app.R;
import bupt.atp.app.data.FileSystem;


/**
 * Created by tangye on 2018/6/26.
 */

public class DataAdapter extends BaseAdapter {

    private Context    context    = null;
    private FileSystem fileSystem = null;

    public DataAdapter(Context context) {
        this.context = context;
        this.fileSystem = new FileSystem();
    }

//    public void initData(List<FileAttribute> rootFileAttrs){
//        this.fileAttrs  = rootFileAttrs;
//    }

    public void update() {
        notifyDataSetChanged();
    }

//    public boolean gotoChildDir(String dirName, List<FileAttribute> content) {
//        if (null == fileAttrs) {
//            return false;
//        }
//
//        for (FileAttribute each : fileAttrs) {
//            //if ()
//        }
//    }
//
//    public void gotoChildDir(int index, List<FileAttribute> fileAttrs) {
//        this.workingDir.add(fileAttrs.get(index).getFileName());
//        this.fileAttrs = fileAttrs;
//    }
//
//    public void gotoParentDir(int index, List<FileAttribute> fileAttrs) {
//        this.workingDir.remove(this.workingDir.size() - 1);
//        this.fileAttrs = fileAttrs;
//    }
//
//    public String getCurrentPath(){
//        String path = "";
//        for (String each : workingDir) {
//            path += each + "/";
//        }
//        return path;
//    }

    @Override
    public int getCount() {
        return null != fileSystem ? fileSystem.getFiles().size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return null != fileSystem ? fileSystem.getFile(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(context).inflate(
                R.layout.info_list_layout, viewGroup, false
        );

        TextView textView = view.findViewById(R.id.info_listview_textview);
        textView.setText(fileSystem.getFile(i).getFileName());

        return view;
    }

//    public String gotoParent() {
//        this.workingDir.remove(this.workingDir.size() - 1);
//
//        String path = "/";
//        for (String each : workingDir) {
//            path += each + "/";
//        }
//
//        return path;
//    }
//
//
//    public List<FileAttribute> getFileAttrs() {
//        return fileAttrs;
//    }
//
//    public void setFileAttrs(List<FileAttribute> fileAttrs) {
//        this.fileAttrs = fileAttrs;
//    }

    public FileSystem getFileSystem() { return fileSystem; }
}
