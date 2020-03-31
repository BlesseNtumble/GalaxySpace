package galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon;

import java.util.Random;

import asmodeuscore.core.astronomy.dimension.world.gen.dungeons.standart.DungeonConfiguration;
import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.systems.SolarSystem.moons.io.blocks.BlockTier5TreasureChest;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.tile.TileEntityTreasureChest;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.storage.loot.LootTableList;

public class RoomTreasureIo extends SizedPieceIo
{
    public static ResourceLocation IOCHEST = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "dungeon_tier_5");
    public static final ResourceLocation TABLE_TIER_5_DUNGEON = LootTableList.register(IOCHEST);

    public RoomTreasureIo()
    {
    }
    
    public RoomTreasureIo(DungeonConfiguration configuration, Random rand, int blockPosX, int blockPosZ, EnumFacing entranceDir)
    {
        this(configuration, rand, blockPosX, blockPosZ, rand.nextInt(4) + 6, configuration.getRoomHeight(), rand.nextInt(4) + 6, entranceDir);
    }

    public RoomTreasureIo(DungeonConfiguration configuration, Random rand, int blockPosX, int blockPosZ, int sizeX, int sizeY, int sizeZ, EnumFacing entranceDir)
    {
        super(configuration, sizeX, sizeY, sizeZ, entranceDir.getOpposite());
        this.setCoordBaseMode(EnumFacing.SOUTH);
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
                        		this.setBlockState(worldIn, this.configuration.getOtherBlock(false), i, j, k, boundingBox);
                        	else if(j == 0)
                        		this.setBlockState(worldIn, this.configuration.getOtherBlock(true), i, j, k, boundingBox);
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
                            worldIn.setBlockState(blockpos, GSBlocks.TREASURE_CHEST_TIER_5.getDefaultState().withProperty(BlockTier5TreasureChest.FACING, this.getDirection().getOpposite()), 2);
                            TileEntityTreasureChest treasureChest = (TileEntityTreasureChest) worldIn.getTileEntity(blockpos);
                            if (treasureChest != null)
                            {
                                ResourceLocation chesttype = IOCHEST;
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
    
    @Override
    public PieceIo getNextPiece(DungeonStartIo startPiece, Random rand)
    {
        if (startPiece.attachedComponents.size() > 2)
        {
            StructureComponent component = startPiece.attachedComponents.get(startPiece.attachedComponents.size() - 3);
            if (component instanceof RoomBossIo)
            {
                BlockPos blockpos = new BlockPos(this.getXWithOffset(this.sizeX / 2, this.sizeZ / 2), this.getYWithOffset(1), this.getZWithOffset(this.sizeX / 2, this.sizeZ / 2));
                ((RoomBossIo) component).setChestPos(new BlockPos(blockpos));
            }
        }
        return null;
    }
}
