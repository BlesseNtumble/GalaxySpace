package galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon;

import java.util.HashSet;
import java.util.Random;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.world.gen.dungeon.GSDungeonBoundingBox;
import galaxyspace.core.world.gen.dungeon.GSDungeonRoom;
import galaxyspace.core.world.gen.dungeon.GSMapGenDungeon;
import galaxyspace.systems.SolarSystem.moons.io.tile.TileEntityIoTreasureChest;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;

public class RoomTreasureIo extends GSDungeonRoom
{
    int sizeX;
    int sizeY;
    int sizeZ;

    private final HashSet<ChunkCoordinates> chests = new HashSet<ChunkCoordinates>();

    public RoomTreasureIo(GSMapGenDungeon dungeon, int posX, int posY, int posZ, ForgeDirection entranceDir)
    {
        super(dungeon, posX, posY, posZ, entranceDir);
        if (this.worldObj != null)
        {
            final Random rand = new Random(this.worldObj.getSeed() * posX * posY * 57 * posZ);
            this.sizeX = rand.nextInt(6) + 7;
            this.sizeY = rand.nextInt(2) + 8;
            this.sizeZ = rand.nextInt(6) + 7;
        }
    }

    @Override
    public void generate(Block[] chunk, byte[] meta, int cx, int cz)
    {
        for (int i = this.posX - 1; i <= this.posX + this.sizeX; i++)
        {
            for (int k = this.posZ - 1; k <= this.posZ + this.sizeZ; k++)
            {
                for (int j = this.posY - 1; j <= this.posY + this.sizeY; j++)
                {
                    if (i == this.posX - 1 || i == this.posX + this.sizeX || j == this.posY - 1 || j == this.posY + this.sizeY || k == this.posZ - 1 || k == this.posZ + this.sizeZ)
                    {
                        //this.placeBlock(chunk, meta, i, j, k, cx, cz, this.dungeonInstance.DUNGEON_WALL_ID, this.dungeonInstance.DUNGEON_WALL_META);
                    	if(j == this.posY - 1 || i == this.posX + this.sizeX)
                        {
                        	this.placeBlock(chunk, meta, i, j, k, cx, cz, this.dungeonInstance.DUNGEON_FLOOR_ID, this.dungeonInstance.DUNGEON_FLOOR_META);
                        }
                        if(j == this.posY + this.sizeY || i == this.posX - 1 || i == this.posX + this.sizeX || k == this.posZ - 1 || k == this.posZ + this.sizeZ) this.placeBlock(chunk, meta, i, j, k, cx, cz, this.dungeonInstance.DUNGEON_WALL_ID, this.dungeonInstance.DUNGEON_WALL_META);
                        if(j == this.posY + this.sizeY || i == this.posX + this.sizeX + 1)
                        {
                        	this.placeBlock(chunk, meta, i, j, k, cx, cz, this.dungeonInstance.DUNGEON_TOP_ID, this.dungeonInstance.DUNGEON_TOP_META);
                        }
                    }
                    else
                    {
                        if ((i == this.posX || i == this.posX + this.sizeX - 1) && (k == this.posZ || k == this.posZ + this.sizeZ - 1))
                        {
                            this.placeBlock(chunk, meta, i, j, k, cx, cz, GSBlocks.DungeonGlowstones, 1);
                        }
                        else
                        {
                            this.placeBlock(chunk, meta, i, j, k, cx, cz, Blocks.air, 0);
                        }
                    }
                }
            }
        }
        final int hx = (this.posX + this.posX + this.sizeX) / 2;
        final int hz = (this.posZ + this.posZ + this.sizeZ) / 2;
        if (this.placeBlock(chunk, meta, hx, this.posY, hz, cx, cz, GSBlocks.IoTChestT5, 0))
        {
            this.chests.add(new ChunkCoordinates(hx, this.posY, hz));
        }
    }

    @Override
    public GSDungeonBoundingBox getBoundingBox()
    {
        return new GSDungeonBoundingBox(this.posX, this.posZ, this.posX + this.sizeX, this.posZ + this.sizeZ);
    }

    @Override
    protected GSDungeonRoom makeRoom(GSMapGenDungeon dungeon, int x, int y, int z, ForgeDirection dir)
    {
        return new RoomTreasureIo(dungeon, x, y, z, dir);
    }

    @Override
    protected void handleTileEntities(Random rand)
    {
        if (!this.chests.isEmpty())
        {
            HashSet<ChunkCoordinates> removeList = new HashSet<ChunkCoordinates>();

            for (ChunkCoordinates coords : this.chests)
            {
                this.worldObj.setBlock(coords.posX, coords.posY, coords.posZ, GSBlocks.IoTChestT5, 1, 0);
                this.worldObj.setTileEntity(coords.posX, coords.posY, coords.posZ, new TileEntityIoTreasureChest());
                removeList.add(coords);
            }

            this.chests.removeAll(removeList);
        }
    }
}
