package bupt.atp.app.ftp;


import java.util.List;

import bupt.atp.app.data.FileAttribute;

/**
 * Created by tangye on 2018/7/1.
 */

public interface FTPQueryResultHandler {

    void handleQueryResult(String path, List<FileAttribute> result);
}
