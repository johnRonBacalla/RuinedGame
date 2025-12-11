import core.Game;
import core.GameLoop;
import data.SaveManager;
import sound.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Launcher {

    public static void main(String[] args){
        SaveManager dataManager = new SaveManager();
        Sound soundBoardBGM = new Sound("sounds/bgm/StoryBoardBGM.wav");
        soundBoardBGM.play();

        Map<String, List<String>> gameData = new HashMap<>();

        // Add seeds
        List<String> placables = new ArrayList<>();
        placables.add("entity_type,x,y,id"); // Header
        placables.add("chest,10,7,2");
        gameData.put("PLACABLES", placables);

        SaveManager.saveGame(2, gameData);


        // Initial JFrame size
        // width = 16 * 64 = 1024px
        // height = 10 * 64 = 640px


        new Thread(new GameLoop(new Game(1024, 640))).start();
    }
}
