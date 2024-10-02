package galaxyspace.core.world.gen.dungeon;

public class GSDungeonBoundingBox
{
    int minX;
    int minZ;
    int maxX;
    int maxZ;

    public GSDungeonBoundingBox(int minX, int minZ, int maxX, int maxZ)
    {
        this.minX = minX;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxZ = maxZ;
    }

    public boolean isOverlapping(GSDungeonBoundingBox bb)
    {
        return this.minX < bb.maxX && this.minZ < bb.maxZ && this.maxX > bb.minX && this.maxZ > bb.minZ;
    }

}
