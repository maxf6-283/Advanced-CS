public class GameAnimator implements Runnable{
    private GameFrame game;
    private int fps;

    public GameAnimator(GameFrame gameFrame, int framesPerSecond) {
        game = gameFrame;
        fps = framesPerSecond;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000/fps);
            } catch (InterruptedException e) {
                break;
            }
            game.update();
        }
    }
}
