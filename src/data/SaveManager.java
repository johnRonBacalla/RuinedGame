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

    public static Map<String, Object> loadGame() {
        Map<String, Object> data = new HashMap<>();
        List<String> objects = new ArrayList<>();

        try {
            File saveFile = new File(SAVE_FILE);
            if (!saveFile.exists()) {
                System.out.println("No save file found");
                return null;
            }

            BufferedReader reader = new BufferedReader(new FileReader(saveFile));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("WAVE:")) {
                    int wave = Integer.parseInt(line.split(":")[1]);
                    data.put("wave", wave);
                } else {
                    objects.add(line);
                }
            }

            reader.close();
            data.put("objects", objects);
            System.out.println("Game loaded!");
            return data;

        } catch (IOException e) {
            System.err.println("Load failed: " + e.getMessage());
            return null;
        }
    }

}
