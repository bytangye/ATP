package bupt.atp.app.ftp;


import java.util.List;

import bupt.atp.app.adapters.FileAttribute;

/**
 * Created by tangye on 2018/7/1.
 */

public interface FTPQueryResultHandler {

    void handleQueryResult(List<FileAttribute> result);
}
