package bupt.atp.app;


import java.util.List;

import bupt.atp.app.adapters.FileAttribute;

/**
 * Created by tangye on 2018/6/26.
 */

public interface MessageListener {

    void onDictChanged(List<FileAttribute> attrs);

}
