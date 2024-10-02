package galaxyspace.systems.SolarSystem.planets.ceres.world.gen.dungeon;

import java.util.ArrayList;
import java.util.Random;

import galaxyspace.core.world.gen.dungeon.GSDungeonBoundingBox;
import galaxyspace.core.world.gen.dungeon.GSDungeonRoom;
import galaxyspace.core.world.gen.dungeon.GSMapGenDungeon;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.util.ForgeDirection;

public class RoomChestsCeres extends GSDungeonRoom
{
    int sizeX;
    int sizeY;
    int sizeZ;

    private final ArrayList<ChunkCoordinates> chests = new ArrayList<ChunkCoordinates>();

    public RoomChestsCeres(GSMapGenDungeon dungeon, int posX, int posY, int posZ, ForgeDirection entranceDir)
    {
        super(dungeon, posX, posY, posZ, entranceDir);
        if (this.worldObj != null)
        {
            final Random rand = new Random(this.worldObj.getSeed() * posX * posY * 57 * posZ);
            this.sizeX = rand.nextInt(5) + 6;
            this.sizeY = rand.nextInt(2) + 7;
            this.sizeZ = rand.nextInt(5) + 6;
        }
    }

    @Override
    public void generate(Block[] chunk, byte[] meta, int cx, int cz)
    {
        for (int i = this.posX - 1; i <= this.posX + this.sizeX; i++)
        {
            for (int j = this.posY - 1; j <= this.posY + this.sizeY; j++)
            {
                for (int k = this.posZ - 1; k <= this.posZ + this.sizeZ; k++)
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
                        this.placeBlock(chunk, meta, i, j, k, cx, cz, Blocks.air, 0);
                    }
                }
            }
        }
        final int hx = (this.posX + this.posX + this.sizeX) / 2;
        final int hz = (this.posZ + this.posZ + this.sizeZ) / 2;
        if (this.placeBlock(chunk, meta, hx, this.posY + 1, hz, cx, cz, Blocks.chest, 0))
        {
        	this.placeBlock(chunk, meta, hx, this.posY, hz, cx, cz, this.dungeonInstance.DUNGEON_WALL_ID, this.dungeonInstance.DUNGEON_WALL_META);
            this.chests.add(new ChunkCoordinates(hx, this.posY + 1, hz));
            Block block = Blocks.ice;
            for(int o = -1; o < this.sizeY; o++)
            {

                this.placeBlock(chunk, meta, hx + 1, this.posY + o, hz + 1, cx, cz, block, 0);
                this.placeBlock(chunk, meta, hx + 1, this.posY + o, hz - 1, cx, cz, block, 0);
                this.placeBlock(chunk, meta, hx - 1, this.posY + o, hz + 1, cx, cz, block, 0);
                this.placeBlock(chunk, meta, hx - 1, this.posY + o, hz - 1, cx, cz, block, 0);
            }
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
        return new RoomChestsCeres(dungeon, x, y, z, dir);
    }

    @Override
    protected void handleTileEntities(Random rand)
    {
        if (!this.chests.isEmpty())
        {
            this.worldObj.setBlock(this.chests.get(0).posX, this.chests.get(0).posY, this.chests.get(0).posZ, Blocks.chest, 0, 2);
            TileEntityChest chest = (TileEntityChest) this.worldObj.getTileEntity(this.chests.get(0).posX, this.chests.get(0).posY, this.chests.get(0).posZ);

            if (chest != null)
            {
                for (int i = 0; i < chest.getSizeInventory(); i++)
                {
                    chest.setInventorySlotContents(i, null);
                }

                ChestGenHooks info = ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST);

                WeightedRandomChestContent.generateChestContents(rand, info.getItems(rand), chest, info.getCount(rand));
            }

            this.chests.clear();
        }
    }
}
