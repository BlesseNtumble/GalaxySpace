package galaxyspace.systems.SolarSystem.planets.ceres.world.gen.dungeon;

import java.util.Random;

import asmodeuscore.core.astronomy.dimension.world.gen.dungeons.standart.DungeonConfiguration;
import asmodeuscore.core.astronomy.dimension.world.gen.dungeons.standart.DungeonStart;
import asmodeuscore.core.astronomy.dimension.world.gen.dungeons.standart.Piece;
import asmodeuscore.core.astronomy.dimension.world.gen.dungeons.standart.SizedPiece;
import galaxyspace.core.GSBlocks;
import galaxyspace.systems.SolarSystem.planets.ceres.blocks.CeresBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class RoomEmptyCeres extends SizedPiece
{
    public RoomEmptyCeres()
    {
    }

    public RoomEmptyCeres(DungeonConfiguration configuration, Random rand, int blockPosX, int blockPosZ, EnumFacing entranceDir)
	{
		this(configuration, rand, blockPosX, blockPosZ, rand.nextInt(4) + 6, configuration.getRoomHeight(), rand.nextInt(4) + 6, entranceDir);
	}
    
    public RoomEmptyCeres(DungeonConfiguration configuration, Random rand, int blockPosX, int blockPosZ, int sizeX, int sizeY, int sizeZ, EnumFacing entranceDir)
    {
        super(configuration, sizeX, sizeY, sizeZ, entranceDir.getOpposite());
        this.setCoordBaseMode(EnumFacing.SOUTH);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        int yPos = configuration.getYPosition();

        this.boundingBox = new StructureBoundingBox(blockPosX, yPos, blockPosZ, blockPosX + this.sizeX, yPos + this.sizeY, blockPosZ + this.sizeZ);
    }

    @Override
    public boolean addComponentParts(World worldIn, Random random, StructureBoundingBox boundingBox)
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
                            if (i > start && i <= end && j < this.configuration.getHallwayHeight() && j > 0)
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
                            if (k > start && k <= end && j < this.configuration.getHallwayHeight() && j > 0)
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
                        	if(j == this.configuration.getHallwayHeight())
                        		this.setBlockState(worldIn, GSBlocks.CERES_BLOCKS.getDefaultState().withProperty(CeresBlocks.BASIC_TYPE, CeresBlocks.EnumCeresBlocks.CERES_DUNGEON_TOP), i, j, k, boundingBox);
                        	else if(j == 0)
                        		this.setBlockState(worldIn, GSBlocks.CERES_BLOCKS.getDefaultState().withProperty(CeresBlocks.BASIC_TYPE, CeresBlocks.EnumCeresBlocks.CERES_DUNGEON_FLOOR), i, j, k, boundingBox);
                        	else
                        		this.setBlockState(worldIn, this.configuration.getBrickBlock(), i, j, k, boundingBox);
                        }
                        else
                        {
                            this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), i, j, k, boundingBox);
                        }
                    }
                    else
                    {
                        this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), i, j, k, boundingBox);
                    }
                }
            }
        }

        return true;
    }
    
    @Override
    public Piece getNextPiece(DungeonStart startPiece, Random rand)
    {
        if (Math.abs(startPiece.getBoundingBox().maxZ - boundingBox.minZ) > 200)
        {
            return null;
        }

        if (Math.abs(startPiece.getBoundingBox().maxX - boundingBox.minX) > 200)
        {
            return null;
        }

        return getCorridor(rand, startPiece, 10, false);
    }
}
