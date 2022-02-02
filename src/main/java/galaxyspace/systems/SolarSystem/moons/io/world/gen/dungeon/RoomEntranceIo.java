package galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon;

import java.util.Random;

import asmodeuscore.core.astronomy.dimension.world.gen.dungeons.standart.DungeonConfiguration;
import galaxyspace.GalaxySpace;
import galaxyspace.core.GSBlocks;
import galaxyspace.core.prefab.blocks.DungeonBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class RoomEntranceIo extends SizedPieceIo
{
    private static int range = 18;
    private EnumFacing exitDirection;
    
    public RoomEntranceIo()
    {
    }
    
    public RoomEntranceIo(World world, DungeonConfiguration configuration, Random rand, int blockPosX, int blockPosZ)
    {
        super(configuration, range, rand.nextInt(2) + 15, range, EnumFacing.Plane.HORIZONTAL.random(rand));
    	this.setCoordBaseMode(EnumFacing.SOUTH);

        this.boundingBox = new StructureBoundingBox(blockPosX - range, configuration.getYPosition() - 5, blockPosZ - range, blockPosX + range - 1, 150, blockPosZ + range);
        GalaxySpace.instance.debug(blockPosX  + " | " + blockPosZ);
    }

    @Override
    public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
    {
        IBlockState block1;

        int maxLevel = 0;

        for (int i = -range; i <= range; i++)
        {
            for (int k = -range; k <= range; k++)
            {
                int j = this.boundingBox.getYSize();

                while (j >= 0)
                {
                    j--;

                    Block block = getBlockStateFromPos(worldIn, i + range, j, k + range, boundingBox).getBlock();

                    if (Blocks.AIR != block && block != null)
                    {
                        break;
                    }
                }

                maxLevel = Math.max(maxLevel, j + 3);
            }
        }

        int startX = range - this.sizeX / 2;
        int startZ = range - this.sizeZ / 2;
        int endX = range + this.sizeX / 2;
        int endZ = range + this.sizeZ / 2;

        for (int i = startX; i <= endX; i++)
        {
            for (int j = 0; j <= this.sizeY; j++)
            {
                for (int k = startZ; k <= endZ; k++)
                {      
                	if(j == this.sizeY && (i == startX + 3 || i == endX - 3) && (k == startZ + 3 || k == endZ - 3))
            			this.setBlockState(worldIn, Blocks.FLOWING_LAVA.getDefaultState(), i, j, k, boundingBox);   
                	
                	
                	else if (i == startX || i == endX || j == 0 || j == 5 || j == this.sizeY || k == startZ || k == endZ)
                    {                		
                		if(j == this.sizeY)
                    	{                    		
                    		if(this.configuration.getOtherBlock(false) != null)
                    			this.setBlockState(worldIn, this.configuration.getOtherBlock(false), i, j, k, boundingBox);
                    	}
                    	else if(j == 0)
                			this.setBlockState(worldIn, Blocks.LAVA.getDefaultState(), i, j, k, boundingBox);                    	
                    	
                    	else if(j >= 5 && j <= 9 && this.configuration.getOtherBlock(true) != null)
                     	{
                    		int l = 5;    
                    		if(j == 5) 
                    		{
	                     		if(i > startX + 3 && i < endX - 3 && k > startZ + 3 && k < endZ - 3)
	                     		{
	                     			this.setBlockState(worldIn, this.configuration.getOtherBlock(true), i, j, k, boundingBox);
	                     		}
	                     		else if(i != startX && i != endX && k != startZ && k != endZ)                       
	                                this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), i, j, k, boundingBox);  
	                     		else
	                     			this.setBlockState(worldIn, this.configuration.getBrickBlock(), i, j, k, boundingBox);
	                     		
	                     		
	                     		     		
	                     		switch(exitDirection)
	                     		{
	    							case EAST:
	    								if(k > startZ + l && k < endZ - l && i < endX - l)
	    									this.setBlockState(worldIn, this.configuration.getOtherBlock(true), i, j, k, boundingBox);
	    								break;
	    							case NORTH:
	    								if(i > startX + l && i < endX - l && k > endZ - l) 
	    									this.setBlockState(worldIn, this.configuration.getOtherBlock(true), i, j, k, boundingBox); 		// ----- V
	    								break;
	    							case SOUTH:
	    								if(i > startX + l && i < endX - l && k < endZ - l)
	    									this.setBlockState(worldIn, this.configuration.getOtherBlock(true), i, j, k, boundingBox); 			// ----- V
	    								break;
	    							case WEST:
	    								if(k > startZ + l && k < endZ - l && i > endX - l)	    								
	    									this.setBlockState(worldIn, this.configuration.getOtherBlock(true), i, j, k, boundingBox); 	// ----- V
	    								break;
	    							default:
	    								break;                    		
	                     		}
                    		}
                    		else
                    		{	
	                     		this.setBlockState(worldIn, this.configuration.getBrickBlock(), i, j, k, boundingBox);
	                     		
	                     		switch(exitDirection)
	                     		{
	                     			case EAST:
	                     				if(k > startZ + l && k < endZ - l && i < endX - l) {
	                     					this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), i, j, k, boundingBox);
	                     				}
	                     				break;
	                     			case NORTH:
	                     				if(i > startX + l && i < endX - l && k > endZ - l) {
	                     					this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), i, j, k, boundingBox);
	                     				}
	                     				break;
	                     			case SOUTH:
	                     				if(i > startX + l && i < endX - l && k < endZ - l) {
	                     					this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), i, j, k, boundingBox);
	                     				}
	                     				break;
	                     			case WEST:
	                     				if(k > startZ + l && k < endZ - l && i > endX - l) {
	                     					this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), i, j, k, boundingBox);
	                     				}
	                     				break;
	                     			default:	                     				
	                     				break;
                    			
	                     		}
                    		}
                    		
                     	} 	
                    	else if(j == 1)
                    	{
                    		this.setBlockState(worldIn, GSBlocks.DUNGEON_BLOCKS.getDefaultState().withProperty(DungeonBlocks.BASIC_TYPE, DungeonBlocks.EnumDungeonBlocks.IO_BRICKS), i, j, k, boundingBox);
                    	}
                    	else if(j >= 2 && j <= 4 && worldIn.rand.nextInt(10) > 4)
                    	{
                    		this.setBlockState(worldIn, GSBlocks.DUNGEON_BLOCKS.getDefaultState().withProperty(DungeonBlocks.BASIC_TYPE, DungeonBlocks.EnumDungeonBlocks.IO_BRICKS), i, j, k, boundingBox);
                    	}
                    	else 
                    		this.setBlockState(worldIn, this.configuration.getBrickBlock(), i, j, k, boundingBox);
                    }             	
                    else
                    {
                        this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), i, j, k, boundingBox);
                    }
                }
            }
        }    
        
        for (int i = -range; i < range; i++)
        {
            for (int k = -range; k < range; k++)
            {
                final double xDev = i / 20D;
                final double zDev = k / 20D;
                final double distance = xDev * xDev + zDev * zDev;
                final int depth = (int) Math.abs(0.5 / (distance * 2 + .00001D));
                int helper = 0;
                for (int j = maxLevel; j > 5 && helper <= depth; j--)
                {
                    block1 = this.getBlockStateFromPos(worldIn, i + range, j, k + range, boundingBox);
                    if ((block1 == this.configuration.getBrickBlock() || block1 == this.configuration.getOtherBlock(false)) || j != this.sizeY || j != 5)
                    {
                        this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), i + range, j, k + range, boundingBox);
                        helper++;
                    }
                }
            }
        }

        return true;
    }

    @Override
    protected void writeStructureToNBT(NBTTagCompound tagCompound)
    {
        super.writeStructureToNBT(tagCompound);

        if (this.exitDirection != null)
        {
            tagCompound.setInteger("direction_exit", this.exitDirection.ordinal());
        }
    }
    
    @Override
    protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager manager)
    {
        super.readStructureFromNBT(tagCompound, manager);

        if (tagCompound.hasKey("direction_exit"))
        {
            this.exitDirection = EnumFacing.byIndex(tagCompound.getInteger("direction_exit"));
        }
        else
        {
            this.exitDirection = null;
        }
    }
    
    @Override
    public PieceIo getNextPiece(DungeonStartIo startPiece, Random rand)
    {
        if (startPiece.attachedComponents.isEmpty())
        {
        	DirectionalPieceIo corridor = (DirectionalPieceIo) getCorridor(rand, startPiece, 20, false, 12);
            this.exitDirection = corridor == null ? null : corridor.getDirection().getOpposite();
            return corridor;//getCorridor(rand, startPiece, 10, false);
        }

        return null;
    }
    
    @Override
    protected StructureBoundingBox getExtension(EnumFacing direction, int length, int width)
    {
        int blockX, blockZ, sizeX, sizeZ;
        int startX = this.boundingBox.minX + range - this.sizeX / 2;
        int startZ = this.boundingBox.minZ + range - this.sizeZ / 2;
        int endX = this.boundingBox.minX + range + this.sizeX / 2;
        int endZ = this.boundingBox.minZ + range + this.sizeZ / 2;
        switch (direction)
        {
        case NORTH:
            sizeX = width;
            sizeZ = length;
            blockX = startX + (endX - startX) / 2 - sizeX / 2;
            blockZ = startZ - sizeZ;
            break;
        case EAST:
            sizeX = length;
            sizeZ = width;
            blockX = endX;
            blockZ = startZ + (endZ - startZ) / 2 - sizeZ / 2;
            break;
        case SOUTH:
            sizeX = width;
            sizeZ = length;
            blockX = startX + (endX - startX) / 2 - sizeX / 2;
            blockZ = endZ;
            break;
        case WEST:
        default:
            sizeX = length;
            sizeZ = width;
            blockX = startX - sizeX;
            blockZ = startZ + (endZ - startZ) / 2 - sizeZ / 2;
            break;
        }
        return new StructureBoundingBox(blockX, blockZ, blockX + sizeX, blockZ + sizeZ);
    }
}
