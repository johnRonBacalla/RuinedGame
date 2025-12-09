package data;

import java.io.*;
import java.util.*;

public class SaveManager {
    private static final String SAVE_FILE = "res/saves/game_save.txt";

    public static void saveGame(int waveNumber, List<String> objectData) {
        try {
            // Create saves directory if needed
            File saveFile = new File(SAVE_FILE);
            saveFile.getParentFile().mkdirs();

            FileWriter writer = new FileWriter(saveFile);

            // Write wave number
            writer.write("WAVE:" + waveNumber + "\n");

            // Write each object (one line per object)
            for (String data : objectData) {
                writer.write(data + "\n");
            }

            writer.close();
            System.out.println("Game saved!");

        } catch (IOException e) {
            System.err.println("Save failed: " + e.getMessage());
        }
    }

}
