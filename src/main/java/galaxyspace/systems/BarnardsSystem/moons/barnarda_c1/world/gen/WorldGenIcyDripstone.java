package galaxyspace.systems.BarnardsSystem.moons.barnarda_c1.world.gen;

import galaxyspace.systems.BarnardsSystem.core.BRBlocks;
import galaxyspace.systems.BarnardsSystem.moons.barnarda_c1.blocks.Barnarda_C1_Blocks;
import galaxyspace.systems.BarnardsSystem.moons.barnarda_c1.blocks.Barnarda_C1_Decorations;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class WorldGenIcyDripstone extends WorldGenerator {
    private static final Set<IBlockState> VALID_BLOCKS = new HashSet<>();

    static {
        VALID_BLOCKS.add(BRBlocks.BARNARDA_C1_BLOCKS.getDefaultState()
                .withProperty(Barnarda_C1_Blocks.BASIC_TYPE, Barnarda_C1_Blocks.EnumBlockBarnardaC1.SNOWY_SUBSURFACE));
        VALID_BLOCKS.add(BRBlocks.BARNARDA_C1_BLOCKS.getDefaultState()
                .withProperty(Barnarda_C1_Blocks.BASIC_TYPE, Barnarda_C1_Blocks.EnumBlockBarnardaC1.ICY_SUBSURFACE));
        VALID_BLOCKS.add(BRBlocks.BARNARDA_C1_BLOCKS.getDefaultState()
                .withProperty(Barnarda_C1_Blocks.BASIC_TYPE, Barnarda_C1_Blocks.EnumBlockBarnardaC1.STONE));
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos chunkPos) {

        int randPosX = chunkPos.getX() + rand.nextInt(16) + 8;
        int randPosZ = chunkPos.getZ() + rand.nextInt(16) + 8;

        for (int y = 100; y > 45; y--) {
            BlockPos pos = new BlockPos(randPosX, y, randPosZ);

            if(world.canBlockSeeSky(pos))
                continue;

            if (isValidSurface(world, pos) && world.isAirBlock(pos.down())) {
                generateStalactite(world, rand, pos);
            }
        }

        for (int y = 20; y < 60; y++) {
            BlockPos pos = new BlockPos(randPosX, y, randPosZ);

            if (isValidSurface(world, pos) && world.isAirBlock(pos.up())) {
                generateStalagmite(world, rand, pos);
            }
        }

        return true;
    }

    private boolean isValidSurface(World world, BlockPos pos) {
        return VALID_BLOCKS.contains(world.getBlockState(pos));
    }

    private int calculateLength(World world, BlockPos pos, Random rand, boolean up) {
        int length = 0;


        if(!up)
            while (world.isAirBlock(pos.down(length + 1))) {
                length++;
            }
        else {
            while (world.isAirBlock(pos.up(length + 1))) {
                length++;
            }
        }

        if(length < 0)
            length = 0;

        return Math.min(15, Math.abs(length - rand.nextInt(length + 1)));
    }

    private void generateStalactite(World world, Random rand, BlockPos pos) {
        int length = calculateLength(world, pos, rand, false);
        if (length < 3) return;

        world.setBlockState(pos, BRBlocks.BARNARDA_C1_BLOCKS.getDefaultState().withProperty(Barnarda_C1_Blocks.BASIC_TYPE, Barnarda_C1_Blocks.EnumBlockBarnardaC1.ICY_SUBSURFACE), 2);
        world.setBlockState(pos.down(), BRBlocks.BARNARDA_C1_DECORATIONS.getDefaultState()
                .withProperty(Barnarda_C1_Decorations.BASIC_TYPE, Barnarda_C1_Decorations.EnumBlockBarnardaC1Decoration.ICY_DRIPSTONE_BASE)
                .withProperty(Barnarda_C1_Decorations.UP, true), 2);

        for (int i = 2; i <= length; i++) {
            if (i < length - 1) {
                world.setBlockState(pos.down(i), BRBlocks.BARNARDA_C1_DECORATIONS.getDefaultState()
                        .withProperty(Barnarda_C1_Decorations.BASIC_TYPE, (Barnarda_C1_Decorations.EnumBlockBarnardaC1Decoration.ICY_DRIPSTONE_LARGE))
                        .withProperty(Barnarda_C1_Decorations.UP, true), 2);
            } else if (i == length - 1) {
                world.setBlockState(pos.down(i), BRBlocks.BARNARDA_C1_DECORATIONS.getDefaultState()
                        .withProperty(Barnarda_C1_Decorations.BASIC_TYPE, Barnarda_C1_Decorations.EnumBlockBarnardaC1Decoration.ICY_DRIPSTONE_MEDIUM)
                        .withProperty(Barnarda_C1_Decorations.UP, true), 2);
            } else {
                world.setBlockState(pos.down(i), BRBlocks.BARNARDA_C1_DECORATIONS.getDefaultState()
                        .withProperty(Barnarda_C1_Decorations.BASIC_TYPE, (rand.nextBoolean() ? Barnarda_C1_Decorations.EnumBlockBarnardaC1Decoration.ICY_DRIPSTONE_THIN : Barnarda_C1_Decorations.EnumBlockBarnardaC1Decoration.ICY_DRIPSTONE_SMALL))
                        .withProperty(Barnarda_C1_Decorations.UP, true), 2);
            }
        }
    }

    private void generateStalagmite(World world, Random rand, BlockPos pos) {
        int length = calculateLength(world, pos, rand, true);
        if (length < 3) return;

        world.setBlockState(pos, BRBlocks.BARNARDA_C1_BLOCKS.getDefaultState().withProperty(Barnarda_C1_Blocks.BASIC_TYPE, Barnarda_C1_Blocks.EnumBlockBarnardaC1.ICY_SUBSURFACE), 2);
        world.setBlockState(pos.up(), BRBlocks.BARNARDA_C1_DECORATIONS.getDefaultState()
                .withProperty(Barnarda_C1_Decorations.BASIC_TYPE, Barnarda_C1_Decorations.EnumBlockBarnardaC1Decoration.ICY_DRIPSTONE_BASE)
                .withProperty(Barnarda_C1_Decorations.UP, false), 2);


        for (int i = 2; i <= length; i++) {
            if (i < length - 1) {
                world.setBlockState(pos.up(i), BRBlocks.BARNARDA_C1_DECORATIONS.getDefaultState()
                        .withProperty(Barnarda_C1_Decorations.BASIC_TYPE, Barnarda_C1_Decorations.EnumBlockBarnardaC1Decoration.ICY_DRIPSTONE_LARGE)
                        .withProperty(Barnarda_C1_Decorations.UP, false), 2);
            } else if (i == length - 1) {
                world.setBlockState(pos.up(i), BRBlocks.BARNARDA_C1_DECORATIONS.getDefaultState()
                        .withProperty(Barnarda_C1_Decorations.BASIC_TYPE, Barnarda_C1_Decorations.EnumBlockBarnardaC1Decoration.ICY_DRIPSTONE_MEDIUM)
                        .withProperty(Barnarda_C1_Decorations.UP, false), 2);

            } else {
                world.setBlockState(pos.up(i), BRBlocks.BARNARDA_C1_DECORATIONS.getDefaultState()
                        .withProperty(Barnarda_C1_Decorations.BASIC_TYPE, (rand.nextBoolean() ? Barnarda_C1_Decorations.EnumBlockBarnardaC1Decoration.ICY_DRIPSTONE_THIN : Barnarda_C1_Decorations.EnumBlockBarnardaC1Decoration.ICY_DRIPSTONE_SMALL))
                        .withProperty(Barnarda_C1_Decorations.UP, false), 2);
            }
        }
    }

}
