package galaxyspace.systems.SolarSystem.planets.ceres.world.gen.dungeon;

import java.util.Random;

import asmodeuscore.core.astronomy.dimension.world.gen.dungeons.standart.DungeonConfiguration;
import asmodeuscore.core.astronomy.dimension.world.gen.dungeons.standart.RoomBoss;
import galaxyspace.core.GSBlocks;
import galaxyspace.systems.SolarSystem.planets.ceres.blocks.CeresBlocks;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.tile.TileEntityDungeonSpawner;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class RoomBossCeres extends RoomBoss
{
    public RoomBossCeres()
    {
    }

    public RoomBossCeres(DungeonConfiguration configuration, Random rand, int blockPosX, int blockPosZ, int sizeX, int sizeY, int sizeZ, EnumFacing entranceDir)
    {
        super(configuration, rand, blockPosX, blockPosZ, sizeX, sizeY, sizeZ, entranceDir);
    }

    public RoomBossCeres(DungeonConfiguration configuration, Random rand, int blockPosX, int blockPosZ, EnumFacing entranceDir)
    {
        super(configuration, rand, blockPosX, blockPosZ, 24, 11, 24, entranceDir);
    }

    @Override
    public boolean addComponentParts(World worldIn, Random random, StructureBoundingBox chunkBox)
    {
        for (int i = 0; i <= this.sizeX; i++)
        {
            for (int j = 0; j <= this.sizeY; j++)
            {
                for (int k = 0; k <= this.sizeZ; k++)
                {
                    if (i == 0 || i == this.sizeX || j == 0 || k == 0 || k == this.sizeZ)
                    {
                        boolean placeBlock = true;
                        if (getDirection().getAxis() == EnumFacing.Axis.Z)
                        {
                            int start = (this.boundingBox.maxX - this.boundingBox.minX) / 2 - 1;
                            int end = (this.boundingBox.maxX - this.boundingBox.minX) / 2 + 1;
                            if (i > start && i <= end && j < 3 && j > 0)
                            {
                                if (getDirection() == EnumFacing.SOUTH && k == 0)
                                {
                                    placeBlock = false;
                                }
                                else if (getDirection() == EnumFacing.NORTH && k == this.sizeZ)
                                {
                                    placeBlock = false;
                                }
                            }
                        }
                        else
                        {
                            int start = (this.boundingBox.maxZ - this.boundingBox.minZ) / 2 - 1;
                            int end = (this.boundingBox.maxZ - this.boundingBox.minZ) / 2 + 1;
                            if (k > start && k <= end && j < 3 && j > 0)
                            {
                                if (getDirection() == EnumFacing.EAST && i == 0)
                                {
                                    placeBlock = false;
                                }
                                else if (getDirection() == EnumFacing.WEST && i == this.sizeX)
                                {
                                    placeBlock = false;
                                }
                            }
                        }
                        if (placeBlock)
                        {
                        	if(j == 0)
                        	{
                        		if(i == this.sizeX / 2 && k > 1 && k < this.sizeZ - 1)
                                {
                                    this.setBlockState(worldIn, GSBlocks.FUTURE_GLASS_BASIC.getDefaultState(), i, j, k, chunkBox);
                                    this.setBlockState(worldIn, Blocks.PACKED_ICE.getDefaultState(), i, j - 1, k, chunkBox);
                                }
                        		else if(k == this.sizeZ / 2 && i > 1 && i < this.sizeX - 1)
                                {
                                    this.setBlockState(worldIn, GSBlocks.FUTURE_GLASS_BASIC.getDefaultState(), i, j, k, chunkBox);
                                    this.setBlockState(worldIn, Blocks.PACKED_ICE.getDefaultState(), i, j - 1, k, chunkBox);
                                }
                        		else if((i == 3 || i == this.sizeX - 3) && (k == 3 || k == this.sizeZ - 3))
                        			this.setBlockState(worldIn, Blocks.GLOWSTONE.getDefaultState(), i, j, k, chunkBox);
                        		else this.setBlockState(worldIn, GSBlocks.CERES_BLOCKS.getDefaultState().withProperty(CeresBlocks.BASIC_TYPE, CeresBlocks.EnumCeresBlocks.CERES_DUNGEON_FLOOR), i, j, k, boundingBox);
                        		
                        	}
                        	else
                        		this.setBlockState(worldIn, this.configuration.getBrickBlock(), i, j, k, chunkBox);
                        }
                        else
                        {
                            this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), i, j, k, chunkBox);
                        }
                    }
                    else if (j == this.sizeY)
                    {
                        if ((i <= 2 || k <= 2 || i >= this.sizeX - 2 || k >= this.sizeZ - 2) && random.nextInt(4) == 0)
                        {
                            this.setBlockState(worldIn, Blocks.GLOWSTONE.getDefaultState(), i, j, k, chunkBox);
                        }
                        else
                        {
                        	this.setBlockState(worldIn, GSBlocks.CERES_BLOCKS.getDefaultState().withProperty(CeresBlocks.BASIC_TYPE, CeresBlocks.EnumCeresBlocks.CERES_DUNGEON_TOP), i, j, k, boundingBox);
                            
                            //this.setBlockState(worldIn, this.configuration.getBrickBlock(), i, j, k, chunkBox);
                        }
                    }
                   /* else if (j == 1 && (i <= 2 || k <= 2 || i >= this.sizeX - 2 || k >= this.sizeZ - 2) && random.nextInt(6) == 0)
                    {
                        this.setBlockState(worldIn, MarsBlocks.creeperEgg.getDefaultState(), i, j, k, chunkBox);
                    }*/
                    else
                    {
                    	if((i == 2 || i == 4 || i == this.sizeX - 2 || i == this.sizeX - 4) && 
                    			(k == 2 || k == 4 && i != 4 && i != this.sizeX - 4 || k == this.sizeZ - 2 || k == this.sizeZ - 4 && i != 4 && i != this.sizeX - 4))
                    		this.setBlockState(worldIn, Blocks.PACKED_ICE.getDefaultState(), i, j, k, chunkBox);
                    	else
                    		this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), i, j, k, chunkBox);
                    }
                    
                    
                    
                }
            }
        }
        
        int spawnerX = this.sizeX / 2;
        int spawnerY = 1;
        int spawnerZ = this.sizeZ / 2;
        BlockPos blockpos = new BlockPos(this.getXWithOffset(spawnerX, spawnerZ), this.getYWithOffset(spawnerY), this.getZWithOffset(spawnerX, spawnerZ));
        //Is this position inside the chunk currently being generated?
        if (chunkBox.isVecInside(blockpos))
        {
            worldIn.setBlockState(blockpos, GSBlocks.BOSS_SPAWNER_CERES.getDefaultState(), 2);
            TileEntityDungeonSpawner spawner = (TileEntityDungeonSpawner) worldIn.getTileEntity(blockpos);
            if (spawner != null)
            {
                spawner.setRoom(new Vector3(this.boundingBox.minX + 1, this.boundingBox.minY + 1, this.boundingBox.minZ + 1), new Vector3(this.sizeX - 1, this.sizeY - 1, this.sizeZ - 1));
            }
        }

        return true;
    }
}