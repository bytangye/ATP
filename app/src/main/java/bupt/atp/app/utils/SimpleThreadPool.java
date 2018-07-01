package bupt.atp.app.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by tangye on 2018/7/1.
 */

public class SimpleThreadPool {

    private static final int      CORE_SIZE  = 1;
    private static final int      MAX_SIZE   = 2;
    private static final long     KEEP_ALIVE = 30;
    private static final TimeUnit SECONDS    = TimeUnit.SECONDS;

    private static BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

//    private static ThreadPoolExecutor executor =
//            new ThreadPoolExecutor(
//                    CORE_SIZE, MAX_SIZE, KEEP_ALIVE, SECONDS, queue
//            );

    public static void execute(Runnable task) {
        //executor.execute(task);
        new Thread(task).start();
    }
}
