package galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon;

import java.util.Random;

import asmodeuscore.core.astronomy.dimension.world.gen.dungeons.standart.DungeonConfiguration;
import galaxyspace.core.prefab.blocks.DungeonBlocks;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.systems.SolarSystem.planets.ceres.blocks.CeresBlocks;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.tile.TileEntityDungeonSpawner;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class RoomBossIo extends SizedPieceIo
{
	private EnumFacing exitDirection;
    private BlockPos chestPos;
    
    public RoomBossIo()
    {
    }

    public RoomBossIo(DungeonConfiguration configuration, Random rand, int blockPosX, int blockPosZ, EnumFacing entranceDir)
    {
        this(configuration, rand, blockPosX, blockPosZ, 24, rand.nextInt(2) + 12, 24, entranceDir);
    }

    public RoomBossIo(DungeonConfiguration configuration, Random rand, int blockPosX, int blockPosZ, int sizeX, int sizeY, int sizeZ, EnumFacing entranceDir)
    {
        super(configuration, sizeX, sizeY, sizeZ, entranceDir.getOpposite());
        this.setCoordBaseMode(EnumFacing.SOUTH);
        this.sizeX = sizeX;
        this.sizeZ = sizeZ;
        this.sizeY = sizeY;
        int yPos = configuration.getYPosition();

        this.boundingBox = new StructureBoundingBox(blockPosX, yPos, blockPosZ, blockPosX + this.sizeX, yPos + this.sizeY, blockPosZ + this.sizeZ);
    }
    
    @Override
    protected void writeStructureToNBT(NBTTagCompound tagCompound)
    {
        super.writeStructureToNBT(tagCompound);

        if (this.exitDirection != null)
        {
            tagCompound.setInteger("direction_exit", this.exitDirection.ordinal());
        }


        tagCompound.setBoolean("chestPosNull", this.chestPos == null);
        if (this.chestPos != null)
        {
            tagCompound.setInteger("chestX", this.chestPos.getX());
            tagCompound.setInteger("chestY", this.chestPos.getY());
            tagCompound.setInteger("chestZ", this.chestPos.getZ());
        }
    }

    @Override
    protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager manager)
    {
        super.readStructureFromNBT(tagCompound, manager);

        if (tagCompound.hasKey("direction_exit"))
        {
            this.exitDirection = EnumFacing.getFront(tagCompound.getInteger("direction_exit"));
        }
        else
        {
            this.exitDirection = null;
        }

        if (tagCompound.hasKey("chestPosNull") && !tagCompound.getBoolean("chestPosNull"))
        {
            this.chestPos = new BlockPos(tagCompound.getInteger("chestX"), tagCompound.getInteger("chestY"), tagCompound.getInteger("chestZ"));
        }
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
                    if (i == 0 || i == this.sizeX || j == 0 || j == this.sizeY || k == 0 || k == this.sizeZ)
                    {
                        boolean placeBlock = true;
                        if (getDirection().getAxis() == EnumFacing.Axis.Z)
                        {
                            int start = (this.boundingBox.maxX - this.boundingBox.minX) / 2 - 1;
                            int end = (this.boundingBox.maxX - this.boundingBox.minX) / 2 + 1;
                            if (i > start && i <= end && j < 4 && j > 0)
                            {
                                if (getDirection() == EnumFacing.SOUTH && (k == 0 || k == 2))
                                {
                                    placeBlock = false;
                                }
                                else if (getDirection() == EnumFacing.NORTH && (k == this.sizeZ || k == this.sizeZ - 2))
                                {
                                    placeBlock = false;
                                }
                            }
                        }
                        else
                        {
                            int start = (this.boundingBox.maxZ - this.boundingBox.minZ) / 2 - 1;
                            int end = (this.boundingBox.maxZ - this.boundingBox.minZ) / 2 + 1;
                            if (k > start && k <= end && j < 4 && j > 0)
                            {
                                if (getDirection() == EnumFacing.EAST && (i == 0 || i == 2))
                                {
                                    placeBlock = false;
                                }
                                else if (getDirection() == EnumFacing.WEST && (i == this.sizeX || i == this.sizeX - 2))
                                {
                                    placeBlock = false;
                                }
                            }
                        }
                        if (placeBlock)
                        {
                        	if(j == 0)
                        	{
                        		if(i > 2 && i < this.sizeX - 2 && k > 2 && k < this.sizeZ - 2)
                                {
                                    this.setBlockState(worldIn, GSBlocks.FUTURE_GLASS_BASIC.getDefaultState(), i, j, k, chunkBox);
                                    this.setBlockState(worldIn, Blocks.LAVA.getDefaultState(), i, j - 1, k, chunkBox);
                                }
                        		else
                        			this.setBlockState(worldIn, this.configuration.getOtherBlock(true), i, j, k, boundingBox);
                           	}                        	
                        	else if(j == this.sizeY)
                        	{
                        		if ((i <= 2 || k <= 2 || i >= this.sizeX - 2 || k >= this.sizeZ - 2) && random.nextInt(4) == 0)
                                {
                                    this.setBlockState(worldIn, Blocks.GLOWSTONE.getDefaultState(), i, j, k, chunkBox);
                                }
                                else
                                {
                                	this.setBlockState(worldIn, this.configuration.getOtherBlock(false), i, j, k, boundingBox);

                                }
                        	}
                        	//else if(i == 1 || i == this.sizeX - 2 || k == 1 || k == this.sizeZ - 2) 
                        		//this.setBlockState(worldIn, GSBlocks.DUNGEON_BLOCKS.getDefaultState().withProperty(DungeonBlocks.BASIC_TYPE, DungeonBlocks.EnumDungeonBlocks.IO_BRICKS), i, j, k, chunkBox);                        	
                        	else
                        		this.setBlockState(worldIn, this.configuration.getBrickBlock(), i, j, k, chunkBox);
                        }
                        else
                        {
                            this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), i, j, k, chunkBox);
                        }
                    }
                    else if ((i == 2 || i == this.sizeX - 2) || (k == 2 || k == this.sizeZ - 2))
                    {               
                    	if(i % 2 == 0 && k % 2 == 0)
                    		this.setBlockState(worldIn, GSBlocks.DUNGEON_BLOCKS.getDefaultState().withProperty(DungeonBlocks.BASIC_TYPE, DungeonBlocks.EnumDungeonBlocks.IO_BRICKS), i, j, k, chunkBox);                        	
                    	else
                    		this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), i, j, k, chunkBox);
                    }
                    else
                    	this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), i, j, k, chunkBox);
                    /*
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
                    }*/
                   /* else if (j == 1 && (i <= 2 || k <= 2 || i >= this.sizeX - 2 || k >= this.sizeZ - 2) && random.nextInt(6) == 0)
                    {
                        this.setBlockState(worldIn, MarsBlocks.creeperEgg.getDefaultState(), i, j, k, chunkBox);
                    }*/
                  /*  else
                    {
                    	if((i == 2 || i == 4 || i == this.sizeX - 2 || i == this.sizeX - 4) && 
                    			(k == 2 || k == 4 && i != 4 && i != this.sizeX - 4 || k == this.sizeZ - 2 || k == this.sizeZ - 4 && i != 4 && i != this.sizeX - 4))
                    		this.setBlockState(worldIn, Blocks.PACKED_ICE.getDefaultState(), i, j, k, chunkBox);
                    	else
                    		this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), i, j, k, chunkBox);
                    }*/
                    
                    
                    
                }
            }
        }
        
        int spawnerX = this.sizeX / 2;
        int spawnerY = this.sizeY / 2;
        int spawnerZ = this.sizeZ / 2;
        BlockPos blockpos = new BlockPos(this.getXWithOffset(spawnerX, spawnerZ), this.getYWithOffset(spawnerY), this.getZWithOffset(spawnerX, spawnerZ));
        //Is this position inside the chunk currently being generated?
        if (chunkBox.isVecInside(blockpos))
        {
            worldIn.setBlockState(blockpos, GSBlocks.BOSS_SPAWNER_IO.getDefaultState(), 2);
            TileEntityDungeonSpawner spawner = (TileEntityDungeonSpawner) worldIn.getTileEntity(blockpos);
            if (spawner != null)
            {
                spawner.setRoom(new Vector3(this.boundingBox.minX + 1, this.boundingBox.minY + 1, this.boundingBox.minZ + 1), new Vector3(this.sizeX - 1, this.sizeY - 1, this.sizeZ - 1));
            }
        }

        return true;
    }
    
    public BlockPos getChestPos()
    {
        return chestPos;
    }

    public void setChestPos(BlockPos chestPos)
    {
        this.chestPos = chestPos;
    }

    @Override
    public PieceIo getNextPiece(DungeonStartIo startPiece, Random rand)
    {
        DirectionalPieceIo corridor = (DirectionalPieceIo) getCorridor(rand, startPiece, 10, true);
        this.exitDirection = corridor == null ? null : corridor.getDirection().getOpposite();
        return corridor;
    }
}