package galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon;

import java.util.Random;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.world.gen.dungeon.GSDungeonBoundingBox;
import galaxyspace.core.world.gen.dungeon.GSDungeonRoom;
import galaxyspace.core.world.gen.dungeon.GSMapGenDungeon;
import galaxyspace.systems.SolarSystem.moons.io.tile.TileEntityDungeonSpawnerIo;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.tile.TileEntityDungeonSpawner;
import micdoodle8.mods.galacticraft.planets.mars.blocks.MarsBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;


public class RoomBossIo extends GSDungeonRoom
{
    public int sizeX;
    public int sizeY;
    public int sizeZ;
    Random rand;
    ChunkCoordinates spawnerCoords;

    public RoomBossIo(GSMapGenDungeon dungeon, int posX, int posY, int posZ, ForgeDirection entranceDir)
    {
        super(dungeon, posX, posY, posZ, entranceDir);
        if (this.worldObj != null)
        {
            this.rand = new Random(this.worldObj.getSeed() * posX * posY * 57 * posZ);
            this.sizeX = 24;
            this.sizeY = 11;
            this.sizeZ = 24;
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
                        if (j == this.posY - 1 && (i <= this.posX + 1 || i >= this.posX + this.sizeX - 2 || k == this.posZ + 1 || k == this.posZ + this.sizeZ - 2) && this.rand.nextInt(4) == 0)
                        {
                            this.placeBlock(chunk, meta, i, j, k, cx, cz, GSBlocks.DungeonGlowstones, 1);
                        }
                        else
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
                    }
                    else if (j == this.posY && (i <= this.posX + 1 || i >= this.posX + this.sizeX - 2 || k == this.posZ + 1 || k == this.posZ + this.sizeZ - 2) && this.rand.nextInt(6) == 0)
                    {
                        this.placeBlock(chunk, meta, i, j, k, cx, cz, Blocks.air, 0); //Egg
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
        this.spawnerCoords = new ChunkCoordinates(hx, this.posY + 2, hz);
        Block block = GSBlocks.FutureGlass;
        int n = 8;
        for(int i = -n; i < n; i++)
        {
        	for(int j = -n; j < n; j++)
            {
        		this.placeBlock(chunk, meta, hx + i, this.posY - 1, hz + j, cx, cz, block, 0);
        		this.placeBlock(chunk, meta, hx + i, this.posY - 2, hz + j, cx, cz, Blocks.lava, 0);
        		for(int o = 0; o < this.sizeY + 2; o++)
                {
        			this.placeBlock(chunk, meta, hx + n, this.posY - 2 + o, hz + n, cx, cz, this.dungeonInstance.DUNGEON_WALL_ID, this.dungeonInstance.DUNGEON_WALL_META);
        			this.placeBlock(chunk, meta, hx + n, this.posY - 2 + o, hz - n - 1, cx, cz, this.dungeonInstance.DUNGEON_WALL_ID, this.dungeonInstance.DUNGEON_WALL_META);
        			this.placeBlock(chunk, meta, hx - n - 1, this.posY - 2 + o, hz + n, cx, cz, this.dungeonInstance.DUNGEON_WALL_ID, this.dungeonInstance.DUNGEON_WALL_META);
        			this.placeBlock(chunk, meta, hx - n - 1, this.posY - 2 + o, hz - n - 1, cx, cz, this.dungeonInstance.DUNGEON_WALL_ID, this.dungeonInstance.DUNGEON_WALL_META);
        			
        			this.placeBlock(chunk, meta, hx + 11, this.posY - 2 + o, hz + j * 2, cx, cz, this.dungeonInstance.DUNGEON_WALL_ID, this.dungeonInstance.DUNGEON_WALL_META);
        			this.placeBlock(chunk, meta, hx - 12, this.posY - 2 + o, hz + j * 2, cx, cz, this.dungeonInstance.DUNGEON_WALL_ID, this.dungeonInstance.DUNGEON_WALL_META);
        			
        			this.placeBlock(chunk, meta, hx + i * 2, this.posY - 2 + o, hz + 11, cx, cz, this.dungeonInstance.DUNGEON_WALL_ID, this.dungeonInstance.DUNGEON_WALL_META);
        			this.placeBlock(chunk, meta, hx + i * 2, this.posY - 2 + o, hz - 12, cx, cz, this.dungeonInstance.DUNGEON_WALL_ID, this.dungeonInstance.DUNGEON_WALL_META);
        		       
                }        		
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
        return new RoomBossIo(dungeon, x, y, z, dir);
    }

    @Override
    protected void handleTileEntities(Random rand)
    {
        if (this.spawnerCoords == null)
        {
            return;
        }

        this.worldObj.setBlock(this.spawnerCoords.posX, this.spawnerCoords.posY, this.spawnerCoords.posZ, MarsBlocks.marsBlock, 10, 3);

        final TileEntity tile = this.worldObj.getTileEntity(this.spawnerCoords.posX, this.spawnerCoords.posY, this.spawnerCoords.posZ);

        if (tile == null || !(tile instanceof TileEntityDungeonSpawnerIo))
        {
            TileEntityDungeonSpawner spawner = new TileEntityDungeonSpawnerIo();
            spawner.setRoom(new Vector3(this.posX, this.posY, this.posZ), new Vector3(this.sizeX, this.sizeY, this.sizeZ));
            this.worldObj.setTileEntity(this.spawnerCoords.posX, this.spawnerCoords.posY, this.spawnerCoords.posZ, spawner);
        }
        else if (tile instanceof TileEntityDungeonSpawner)
        {
            ((TileEntityDungeonSpawner) tile).setRoom(new Vector3(this.posX, this.posY, this.posZ), new Vector3(this.sizeX, this.sizeY, this.sizeZ));
        }
    }

}
