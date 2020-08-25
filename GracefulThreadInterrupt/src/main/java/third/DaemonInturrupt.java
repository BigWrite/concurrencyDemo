package third;

/**
 * @ClassName DaemonInturrupt
 * @Description TODO
 * @Author mars.zhang@beyonds.com
 * @Date 2020/8/25 10:51
 * @Version 1.0
 */
public class DaemonInturrupt {


    public static void main(String[] args) throws InterruptedException {
        ThreadService threadService = new ThreadService(() -> {
            while (true) {
                System.out.println(System.currentTimeMillis());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        threadService.shutdown(5000);
        Thread.sleep(10000);

    }
}
