package first;

/**
 * @ClassName ThreadInturrupt
 * @Description 通过thread.inturrupt 打断
 * @Author mars.zhang@beyonds.com
 * @Date 2020/8/25 10:50
 * @Version 1.0
 */
public class ThreadInturrupt {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(()->{
            while (true){
                System.out.println("run ---");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
        Thread.State blocked = Thread.State.BLOCKED;
        thread.start();
        thread.join(60000);
        thread.interrupt();
    }
}
