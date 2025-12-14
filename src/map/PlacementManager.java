package map;

import entity.GameObject;
import entity.moving.Skele;
import entity.placeable.EarthPlant;
import entity.placeable.FirePlant;
import entity.placeable.IcePlant;
import entity.placeable.WindPlant;
import entity.placeable.towers.*;
import gfx.SpriteLibrary;
import physics.box.Box;
import tile.Tile;
import tile.TileScale;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlacementManager {
    private SpriteLibrary spriteLibrary;
    private MapManager mapManager;

    // Track occupied tiles per location
    private HashMap<Location, Set<Point>> occupiedTiles;

    // Track removed objects per location (so they don't respawn)
    private HashMap<Location, Set<GameObject>> removedObjects;

    public PlacementManager(SpriteLibrary spriteLibrary, MapManager mapManager) {
        this.spriteLibrary = spriteLibrary;
        this.mapManager = mapManager;
        this.occupiedTiles = new HashMap<>();
        this.removedObjects = new HashMap<>();

        // Initialize sets for each location
        for (Location loc : Location.values()) {
            occupiedTiles.put(loc, new HashSet<>());
            removedObjects.put(loc, new HashSet<>());
        }
    }

    public boolean canPlaceAt(int tileX, int tileY, Map currentMap, Location currentLocation) {
        if (tileX < 0 || tileX >= currentMap.getTileWidth() ||
                tileY < 0 || tileY >= currentMap.getTileHeight()) {
            return false;
        }

        int tileNum = currentMap.getMapTiles()[tileX][tileY];
        Tile tile = currentMap.getTileLibrary().getTile(tileNum);

        if (tile == null || !tile.isSolid()) {
            return false;
        }

        Point tilePoint = new Point(tileX, tileY);
        return !occupiedTiles.get(currentLocation).contains(tilePoint);
    }

    private GameObject placeObject(GameObject obj, int tileX, int tileY, Map currentMap, Location currentLocation) {
        if (!canPlaceAt(tileX, tileY, currentMap, currentLocation)) {
            return null;
        }

        occupiedTiles.get(currentLocation).add(new Point(tileX, tileY));
        return obj;
    }

    // PLANTS
    public GameObject placeFirePlant(int tileX, int tileY, Map currentMap, Location currentLocation) {
        return placeObject(new FirePlant(TileScale.of(tileX), TileScale.of(tileY), spriteLibrary),
                tileX, tileY, currentMap, currentLocation);
    }

    public GameObject placeIcePlant(int tileX, int tileY, Map currentMap, Location currentLocation) {
        return placeObject(new IcePlant(TileScale.of(tileX), TileScale.of(tileY), spriteLibrary),
                tileX, tileY, currentMap, currentLocation);
    }

    public GameObject placeEarthPlant(int tileX, int tileY, Map currentMap, Location currentLocation) {
        return placeObject(new EarthPlant(TileScale.of(tileX), TileScale.of(tileY), spriteLibrary),
                tileX, tileY, currentMap, currentLocation);
    }

    public GameObject placeWindPlant(int tileX, int tileY, Map currentMap, Location currentLocation) {
        return placeObject(new WindPlant(TileScale.of(tileX), TileScale.of(tileY), spriteLibrary),
                tileX, tileY, currentMap, currentLocation);
    }

    // TOWERS TIER 1
    public GameObject placeFireTower1(int tileX, int tileY, Map currentMap, Location currentLocation) {
        return placeObject(new FireTower1(TileScale.of(tileX), TileScale.of(tileY), spriteLibrary),
                tileX, tileY, currentMap, currentLocation);
    }

    public GameObject placeIceTower1(int tileX, int tileY, Map currentMap, Location currentLocation) {
        return placeObject(new IceTower1(TileScale.of(tileX), TileScale.of(tileY), spriteLibrary),
                tileX, tileY, currentMap, currentLocation);
    }

    public GameObject placeEarthTower1(int tileX, int tileY, Map currentMap, Location currentLocation) {
        return placeObject(new EarthTower1(TileScale.of(tileX), TileScale.of(tileY), spriteLibrary),
                tileX, tileY, currentMap, currentLocation);
    }

    public GameObject placeWindTower1(int tileX, int tileY, Map currentMap, Location currentLocation) {
        return placeObject(new WindTower1(TileScale.of(tileX), TileScale.of(tileY), spriteLibrary),
                tileX, tileY, currentMap, currentLocation);
    }

    // TOWERS TIER 2
    public GameObject placeFireTower2(int tileX, int tileY, Map currentMap, Location currentLocation) {
        return placeObject(new FireTower2(TileScale.of(tileX), TileScale.of(tileY), spriteLibrary),
                tileX, tileY, currentMap, currentLocation);
    }

    public GameObject placeIceTower2(int tileX, int tileY, Map currentMap, Location currentLocation) {
        return placeObject(new IceTower2(TileScale.of(tileX), TileScale.of(tileY), spriteLibrary),
                tileX, tileY, currentMap, currentLocation);
    }

    public GameObject placeWindTower2(int tileX, int tileY, Map currentMap, Location currentLocation) {
        return placeObject(new WindTower2(TileScale.of(tileX), TileScale.of(tileY), spriteLibrary),
                tileX, tileY, currentMap, currentLocation);
    }

    public GameObject placeEarthTower2(int tileX, int tileY, Map currentMap, Location currentLocation) {
        return placeObject(new EarthTower2(TileScale.of(tileX), TileScale.of(tileY), spriteLibrary),
                tileX, tileY, currentMap, currentLocation);
    }

    public void removeObjectAt(int tileX, int tileY, Map currentMap, Location currentLocation,
                               List<GameObject> currentObject, List<GameObject> worldObjects,
                               List<Box> currentBox, List<Box> worldBoxes) {
        if (tileX < 0 || tileX >= currentMap.getTileWidth() ||
                tileY < 0 || tileY >= currentMap.getTileHeight()) {
            return;
        }

        int pixelX = tileX * 64;
        int pixelY = tileY * 64;

        GameObject toRemove = null;
        for (GameObject obj : currentObject) {
            Box box = obj.getBox();

            if (box.getX() >= pixelX && box.getX() < pixelX + 64 &&
                    box.getY() >= pixelY && box.getY() < pixelY + 64) {
                toRemove = obj;
                break;
            }
        }

        if (toRemove != null) {
            currentObject.remove(toRemove);
            worldObjects.remove(toRemove);
            currentBox.remove(toRemove.getBox());
            worldBoxes.remove(toRemove.getBox());

            // Track as removed so it doesn't respawn
            removedObjects.get(currentLocation).add(toRemove);

            // Free up the tile
            occupiedTiles.get(currentLocation).remove(new Point(tileX, tileY));

            System.out.println("Removed object at (" + tileX + ", " + tileY + ")");
        }
    }

    // Check if an object should be filtered out (was removed)
    public boolean wasRemoved(Location location, GameObject obj) {
        // Check by position instead of object reference since objects are recreated on map load
        for (GameObject removed : removedObjects.get(location)) {
            if (removed.getPosition().getX() == obj.getPosition().getX() &&
                    removed.getPosition().getY() == obj.getPosition().getY() &&
                    removed.getClass().equals(obj.getClass())) {
                return true;
            }
        }
        return false;
    }

    public void renderPlacementPreview(Graphics2D g, int tileX, int tileY, Map currentMap, Location currentLocation) {
        if (tileX < 0 || tileX >= currentMap.getTileWidth() ||
                tileY < 0 || tileY >= currentMap.getTileHeight()) {
            return;
        }

        boolean canPlace = canPlaceAt(tileX, tileY, currentMap, currentLocation);

        int pixelX = tileX * 64;
        int pixelY = tileY * 64;

        g.setColor(canPlace ? new Color(0, 255, 0, 100) : new Color(255, 0, 0, 100));
        g.fillRect(pixelX, pixelY, 64, 64);

        g.setColor(canPlace ? Color.GREEN : Color.RED);
        g.setStroke(new BasicStroke(2));
        g.drawRect(pixelX, pixelY, 64, 64);
    }

    public void renderShovelOutline(Graphics2D g, int tileX, int tileY, Map currentMap) {
        if (tileX < 0 || tileX >= currentMap.getTileWidth() ||
                tileY < 0 || tileY >= currentMap.getTileHeight()) {
            return;
        }

        int pixelX = tileX * 64;
        int pixelY = tileY * 64;

        g.setColor(new Color(0, 100, 255, 100));
        g.fillRect(pixelX, pixelY, 64, 64);

        g.setColor(new Color(0, 100, 255));
        g.setStroke(new BasicStroke(2));
        g.drawRect(pixelX, pixelY, 64, 64);
    }

    public Set<Point> getOccupiedTiles(Location location) {
        return occupiedTiles.get(location);
    }

    public void setOccupiedTiles(Location location, Set<Point> tiles) {
        occupiedTiles.put(location, tiles);
    }
}