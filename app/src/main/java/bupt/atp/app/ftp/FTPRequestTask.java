package bupt.atp.app.ftp;

/**
 * Created by tangye on 2018/7/1.
 */

public abstract class FTPRequestTask implements Runnable {

    private String target;

    public FTPRequestTask(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
