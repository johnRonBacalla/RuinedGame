package map;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import entity.GameObject;
import entity.House;
import entity.Tree;
import gfx.SpriteLibrary;
import tile.TileScale;

public class SpawnObjects {

    public static List<GameObject> loadObjects(String path, SpriteLibrary sprites) {
        List<GameObject> objects = new ArrayList<>();

        try (InputStream is = SpawnObjects.class.getResourceAsStream(path);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0];
                int x = Integer.parseInt(parts[1]);
                int y = Integer.parseInt(parts[2]);

                switch (id) {
                    case "house" -> objects.add(new House(TileScale.of(x), TileScale.of(y), sprites));
                    case "tree" -> objects.add(new Tree(TileScale.of(x), TileScale.of(y), sprites));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return objects;
    }
}
