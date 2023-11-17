public class Animate implements Runnable {
    private Screen sc;

    public Animate(Screen sc) {
        this.sc = sc;
    }

    public void run() {
        while (true) {
            sc.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}