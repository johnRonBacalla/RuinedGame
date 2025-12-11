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
//
//        Map<String, List<String>> gameData = new HashMap<>();
//
//        // Add seeds
//        List<String> placables = new ArrayList<>();
//        placables.add("entity_type,x,y,id"); // Header
//        placables.add("chest,4,7,2");
//        gameData.put("PLACABLES", placables);
//
//        dataManager.saveGame(2, gameData);


        // Initial JFrame size
        // width = 16 * 64 = 1024px
        // height = 10 * 64 = 640px

//        List<String> objects = new ArrayList<>();
//        objects.add("wind,200,150,50");      // rune_type,x,y,stage
//        objects.add("fire,500,400,2");       // rune_type,x,y,stage
//        objects.add("ice,450,320,100");
//
//        SaveManager save = new SaveManager();
//        save.saveGame(2, objects);


        new Thread(new GameLoop(new Game(1024, 640))).start();
    }
}
