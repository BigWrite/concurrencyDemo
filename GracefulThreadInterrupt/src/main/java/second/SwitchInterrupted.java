package second;

/**
 * @ClassName SwitchInterrupted
 * @Description 通过标记 中断线程
 * @Author mars.zhang@beyonds.com
 * @Date 2020/8/25 10:50
 * @Version 1.0
 */
public class SwitchInterrupted {

    private static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (flag) {
                System.out.println(System.currentTimeMillis());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
            System.out.println("I'm out");
        });
        thread.start();
        Thread.sleep(5000);
        flag = false;
        System.out.println("end");
    }
}
