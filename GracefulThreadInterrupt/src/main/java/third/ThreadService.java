package third;

/**
 * @ClassName ThreadService
 * @Description 通过设置守护线程 终止线程
 * @Author mars.zhang@beyonds.com
 * @Date 2020/8/25 11:16
 * @Version 1.0
 */
public class ThreadService {

    private Thread thread;

    public ThreadService(Runnable runnable) {
        thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
    }

    public void shutdown(int i){

    }
}
