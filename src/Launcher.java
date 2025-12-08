import core.Game;
import core.GameLoop;
import music.Sound;

public class Launcher {

    public static void main(String[] args){
        Sound menuScreenBGM = new Sound("/sfx/MenuScreenBGM.wav");
        menuScreenBGM.play();
        // Initial JFrame size
        // width = 16 * 64 = 1024px
        // height = 10 * 64 = 640px

        new Thread(new GameLoop(new Game(1024, 640))).start();
    }
}
