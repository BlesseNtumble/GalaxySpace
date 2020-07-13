package galaxyspace.systems.SolarSystem.planets.ceres.world.gen.dungeon;

import java.util.Random;

import asmodeuscore.core.astronomy.dimension.world.gen.dungeons.standart.DungeonConfiguration;
import asmodeuscore.core.astronomy.dimension.world.gen.dungeons.standart.RoomTreasure;
import galaxyspace.GalaxySpace;
import galaxyspace.core.GSBlocks;
import galaxyspace.systems.SolarSystem.planets.ceres.blocks.BlockTier4TreasureChest;
import galaxyspace.systems.SolarSystem.planets.ceres.blocks.CeresBlocks;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.tile.TileEntityTreasureChest;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.storage.loot.LootTableList;

public class RoomTreasureCeres extends RoomTreasure
{
    public static ResourceLocation CERESCHEST = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "dungeon_tier_4");
    public static final ResourceLocation TABLE_TIER_4_DUNGEON = LootTableList.register(CERESCHEST);

    public RoomTreasureCeres()
    {
    }

    public RoomTreasureCeres(DungeonConfiguration configuration, Random rand, int blockPosX, int blockPosZ, EnumFacing entranceDir)
    {
        super(configuration, rand, blockPosX, blockPosZ, rand.nextInt(4) + 6, configuration.getRoomHeight(), rand.nextInt(4) + 6, entranceDir);
    }

    public RoomTreasureCeres(DungeonConfiguration configuration, Random rand, int blockPosX, int blockPosZ, int sizeX, int sizeY, int sizeZ, EnumFacing entranceDir)
    {
        super(configuration, rand, blockPosX, blockPosZ, sizeX, sizeY, sizeZ, entranceDir);
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
                    else if ((i == 1 && k == 1) || (i == 1 && k == this.sizeZ - 1) || (i == this.sizeX - 1 && k == 1) || (i == this.sizeX - 1 && k == this.sizeZ - 1))
                    {
                        this.setBlockState(worldIn, Blocks.GLOWSTONE.getDefaultState(), i, j, k, boundingBox);
                    }
                    else if (i == this.sizeX / 2 && j == 1 && k == this.sizeZ / 2)
                    {
                        BlockPos blockpos = new BlockPos(this.getXWithOffset(i, k), this.getYWithOffset(j), this.getZWithOffset(i, k));
                        if (boundingBox.isVecInside(blockpos))
                        {
                            worldIn.setBlockState(blockpos, GSBlocks.TREASURE_CHEST_TIER_4.getDefaultState().withProperty(BlockTier4TreasureChest.FACING, this.getDirection().getOpposite()), 2);
                            TileEntityTreasureChest treasureChest = (TileEntityTreasureChest) worldIn.getTileEntity(blockpos);
                            if (treasureChest != null)
                            {
                                ResourceLocation chesttype = CERESCHEST;
                                if (worldIn.provider instanceof IGalacticraftWorldProvider)
                                {
                                    chesttype = ((IGalacticraftWorldProvider)worldIn.provider).getDungeonChestType();
                                }
                                treasureChest.setLootTable(chesttype, random.nextLong());
                            }
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
}
