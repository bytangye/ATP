package bupt.atp.app.ftp;


import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

/**
 * Created by tangye on 2018/7/1.
 */

public class FTPClientFactory {

    private static final String HOST_NAME = "10.28.205.71";
    private static final String USER_NAME = "ty";
    private static final String PASSWORD = "3856";

    private static volatile FTPClientFactory factory = null;

    public static FTPClientFactory getInstance() {
        if (null == factory) {
            synchronized (FTPClientFactory.class) {
                if (null == factory) {
                    factory = new FTPClientFactory();
                }
            }
        }

        return factory;
    }

    public FTPClient createClient() {
        FTPClient client = new FTPClient();

        try {
            client.connect(HOST_NAME, 21);
            if (!client.isConnected()) { return null; }

            client.login(USER_NAME, PASSWORD);
        }
        catch (IOException ignored) { }

        return client;
    }

    private FTPClientFactory() { }
}
