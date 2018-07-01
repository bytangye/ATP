package bupt.atp.app.ftp;

/**
 * Created by tangye on 2018/7/1.
 */

public interface FTPErrorHandler {

    void handleFTPError(Object sender, int code, Throwable reason);
}
