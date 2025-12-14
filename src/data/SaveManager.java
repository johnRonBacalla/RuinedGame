package data;

import java.io.*;
import java.util.*;

//WAVE:2
//
//[SEEDS]
//seed_type,x,y,stage
//wind,200,150,2
//fire,150,200,1
//
//[PLACABLES]
//entity_type,x,y,id
//chest,23,1,1234
//
//[CHEST_ITEMS]
//chest_id,item1,item2,item3
//1234,gold,fire_seed,water_seed
//
//[TOWERS]
//tower_type,x,y,tier
//fire,100,200,2
//
//[PLAYER_INVENTORY]
//object_type,row,col
//wind_seed,1,3
//gold,2,1

import java.io.*;
import java.util.*;

public class SaveManager {
    private static final String SAVE_DIR = "res/saves/";

    // Save game data to a specific file
    public static void saveGame(String fileName, int waveNumber, Map<String, List<String>> gameData) {
        try {
            File saveDir = new File(SAVE_DIR);
            saveDir.mkdirs();

            File saveFile = new File(SAVE_DIR + fileName + ".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));

            // Write wave number
            writer.write("WAVE:" + waveNumber);
            writer.newLine();
            writer.newLine();

            // Write each section
            String[] sections = {"SEEDS", "PLACABLES", "CHEST_ITEMS", "TOWERS", "PLAYER_INVENTORY"};

            for (String section : sections) {
                if (gameData.containsKey(section) && !gameData.get(section).isEmpty()) {
                    writer.write("[" + section + "]");
                    writer.newLine();

                    for (String line : gameData.get(section)) {
                        writer.write(line);
                        writer.newLine();
                    }

                    writer.newLine(); // Blank line between sections
                }
            }

            writer.close();
            System.out.println("Game saved to: " + fileName + ".txt");

        } catch (IOException e) {
            System.err.println("Save failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Load game data from save file
    public static Map<String, Object> loadGame(String filePath) {
        try {
            File saveFile = new File(filePath);
            if (!saveFile.exists()) {
                System.out.println("No save file found at: " + filePath);
                return null;
            }

            BufferedReader reader = new BufferedReader(new FileReader(saveFile));
            Map<String, Object> saveData = new HashMap<>();
            Map<String, List<String>> sections = new HashMap<>();

            String line;
            String currentSection = null;
            int waveNumber = 0;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                // Parse wave number
                if (line.startsWith("WAVE:")) {
                    waveNumber = Integer.parseInt(line.split(":")[1]);
                    continue;
                }

                // Parse section headers
                if (line.startsWith("[") && line.endsWith("]")) {
                    currentSection = line.substring(1, line.length() - 1);
                    sections.put(currentSection, new ArrayList<>());
                    continue;
                }

                // Add data to current section
                if (currentSection != null) {
                    sections.get(currentSection).add(line);
                }
            }

            reader.close();

            saveData.put("wave", waveNumber);
            saveData.put("sections", sections);

            System.out.println("Game loaded from: " + filePath);
            System.out.println("Wave: " + waveNumber);
            System.out.println("Sections found: " + sections.keySet());

            return saveData;

        } catch (IOException e) {
            System.err.println("Load failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (NumberFormatException e) {
            System.err.println("Save file corrupted: Invalid wave number");
            return null;
        }
    }

    // Parse a CSV line into an array, handling empty values
    public static String[] parseLine(String line) {
        return line.split(",", -1);
    }

    // Check if a specific save file exists
    public static boolean saveExists(String fileName) {
        return new File(SAVE_DIR + fileName + ".txt").exists();
    }

    // Delete a specific save file
    public static boolean deleteSave(String fileName) {
        File saveFile = new File(SAVE_DIR + fileName + ".txt");
        if (saveFile.exists()) {
            return saveFile.delete();
        }
        return false;
    }

    // Get all save files
    public static List<String> getAllSaveFiles() {
        File saveDir = new File(SAVE_DIR);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
            return new ArrayList<>();
        }

        File[] files = saveDir.listFiles((dir, name) -> name.endsWith(".txt"));
        List<String> saveFiles = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                saveFiles.add(file.getName().replace(".txt", ""));
            }
        }

        return saveFiles;
    }
}
