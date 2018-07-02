package bupt.atp.app.activities.fragments;

import bupt.atp.app.services.FTPService;

/**
 * Created by Maou on 2018/7/2.
 */

public class DownloadRequestCommitter implements FTPRequestCommitter {

    private FTPService service   = null;

    public DownloadRequestCommitter(FTPService service) {
        this.service = service;
    }

    @Override
    public void commit(String path) {
        String[] splits = path.split("/");
        this.service.startDownloadFile(splits[splits.length - 1], path);
    }
}
