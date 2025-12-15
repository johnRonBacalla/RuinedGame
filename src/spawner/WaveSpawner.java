package spawner;

import entity.moving.*;
import gfx.SpriteLibrary;
import tile.TileScale;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WaveSpawner {

    private final SpriteLibrary sprites;
    private final List<SpawnTask> spawnQueue;
    private final Random random;

    private boolean waveActive;
    private double spawnTimer;

    public WaveSpawner(SpriteLibrary sprites) {
        this.sprites = sprites;
        this.spawnQueue = new ArrayList<>();
        this.random = new Random();
        this.waveActive = false;
        this.spawnTimer = 0;
    }

    /**
     * Loads spawn data from file and starts the wave
     * @param filePath path to wave file (e.g., "/spawns/wave1.txt")
     */
    public void startWave(String filePath) {
        if (waveActive) {
            System.out.println("Wave already active!");
            return;
        }

        spawnQueue.clear();
        loadSpawnFile(filePath);
        waveActive = true;
        spawnTimer = 0;

        System.out.println("Wave started! " + spawnQueue.size() + " spawn tasks loaded.");
    }

    /**
     * Reads the spawn file and creates spawn tasks
     */
    private void loadSpawnFile(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            if (is == null) {
                System.err.println("Could not find spawn file: " + filePath);
                return;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue; // Skip empty/comment lines

                String[] parts = line.split(",");
                if (parts.length != 3) {
                    System.err.println("Invalid spawn format: " + line);
                    continue;
                }

                String mobType = parts[0].trim();
                int count = Integer.parseInt(parts[1].trim());
                double interval = Double.parseDouble(parts[2].trim());

                // Pick ONE random spawn point for this mob type
                int spawnPointId = random.nextInt(10) + 1; // 1-10
                SpawnTile spawnTile = getSpawnTile(spawnPointId);

                // Create spawn tasks with intervals
                for (int i = 0; i < count; i++) {
                    double delay = i * interval;
                    spawnQueue.add(new SpawnTask(mobType, spawnTile, delay));
                }
            }

            br.close();

        } catch (Exception e) {
            System.err.println("Error loading spawn file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean hasMoreSpawns() {
        return !spawnQueue.isEmpty();
    }

    /**
     * Updates the spawner and returns list of entities to spawn this frame
     */
    public List<MovingEntity> update(double deltaTime) {
        List<MovingEntity> spawnedEntities = new ArrayList<>();

        if (!waveActive) return spawnedEntities;

        spawnTimer += deltaTime;

        // Check all spawn tasks
        List<SpawnTask> toRemove = new ArrayList<>();
        for (SpawnTask task : spawnQueue) {
            if (!task.hasSpawned() && spawnTimer >= task.getDelay()) {
                MovingEntity entity = createEntity(task.getMobType(), task.getSpawnTile());
                if (entity != null) {
                    spawnedEntities.add(entity);
                    task.setSpawned(true);
                    System.out.println("Spawned " + task.getMobType() + " at " +
                            task.getSpawnTile().getTileX() + "," +
                            task.getSpawnTile().getTileY());
                }
                toRemove.add(task);
            }
        }

        spawnQueue.removeAll(toRemove);

        // Check if wave is complete
        if (spawnQueue.isEmpty()) {
            waveActive = false;
            System.out.println("Wave complete!");
        }

        return spawnedEntities;
    }

    /**
     * Creates a MovingEntity based on type and spawn location
     */
    private MovingEntity createEntity(String mobType, SpawnTile spawnTile) {
        double x = TileScale.of(spawnTile.getTileX());
        double y = TileScale.of(spawnTile.getTileY());

        switch (mobType.toLowerCase()) {
            case "skele":
                return new Skele(x, y, sprites);
            case "bat":
                return new Bat(x, y, sprites);
            case "goblin":
                return new Goblin(x, y, sprites);
            case "golem":
                return new Golem(x, y, sprites);
            case "magmabomber":
                return new MagmaBomber(x, y, sprites);
            case "ogre":
                return new Ogre(x, y, sprites);
            case "succubus":
                return new Succubus(x, y, sprites);
            case "witch":
                return new Witch(x, y, sprites);

            default:
                System.err.println("Unknown mob type: " + mobType);
                return null;
        }
    }

    /**
     * Maps spawn point ID (1-10) to actual tile coordinates
     * All spawn points are at X=19, with Y ranging from 5-14
     */
    private SpawnTile getSpawnTile(int spawnPointId) {
        switch (spawnPointId) {
            case 1:  return new SpawnTile(19, 5);
            case 2:  return new SpawnTile(19, 6);
            case 3:  return new SpawnTile(19, 7);
            case 4:  return new SpawnTile(19, 8);
            case 5:  return new SpawnTile(19, 9);
            case 6:  return new SpawnTile(19, 10);
            case 7:  return new SpawnTile(19, 11);
            case 8:  return new SpawnTile(19, 12);
            case 9:  return new SpawnTile(19, 13);
            case 10: return new SpawnTile(19, 14);
            default: return new SpawnTile(19, 9); // fallback to middle
        }
    }

    public boolean isWaveActive() {
        return waveActive;
    }

    public void stopWave() {
        waveActive = false;
        spawnQueue.clear();
        spawnTimer = 0;
    }

    /**
     * Internal class to hold spawn task data
     */
    private static class SpawnTask {
        private final String mobType;
        private final SpawnTile spawnTile;
        private final double delay;
        private boolean spawned;

        public SpawnTask(String mobType, SpawnTile spawnTile, double delay) {
            this.mobType = mobType;
            this.spawnTile = spawnTile;
            this.delay = delay;
            this.spawned = false;
        }

        public String getMobType() { return mobType; }
        public SpawnTile getSpawnTile() { return spawnTile; }
        public double getDelay() { return delay; }
        public boolean hasSpawned() { return spawned; }
        public void setSpawned(boolean spawned) { this.spawned = spawned; }
    }

    public boolean isWaveCleared() {
        return waveActive;
    }
}