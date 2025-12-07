import core.Game;
import core.GameLoop;

public class Launcher {

    public static void main(String[] args){
        // Initial JFrame size
        // width = 16 * 64 = 1024px
        // height = 10 * 64 = 640px

        new Thread(new GameLoop(new Game(1024, 640))).start();
    }
}
