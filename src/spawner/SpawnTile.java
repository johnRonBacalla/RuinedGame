package spawner;

/**
 * Represents a tile coordinate for spawning entities
 */
public class SpawnTile {
    private final int tileX;
    private final int tileY;

    public SpawnTile(int tileX, int tileY) {
        this.tileX = tileX;
        this.tileY = tileY;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    @Override
    public String toString() {
        return "SpawnTile(" + tileX + ", " + tileY + ")";
    }
}