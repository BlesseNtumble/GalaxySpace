package galaxyspace.systems.SolarSystem.moons.io.world.gen.feature;

import java.util.Random;

import galaxyspace.core.GSBlocks;
import galaxyspace.systems.SolarSystem.moons.io.blocks.IoBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fluids.FluidRegistry;

public class WorldGenVaporPool extends WorldGenerator {

	@Override
	public boolean generate(World world, Random rand, BlockPos position) 
	{
		
		if (position.getY() <= 4)
        {
            return false;
        }

        IBlockState ioSoft = GSBlocks.IO_BLOCKS.getDefaultState().withProperty(IoBlocks.BASIC_TYPE, IoBlocks.EnumIoBlocks.IO_GRUNT);

        int radius = 5 + rand.nextInt(4);
        int radiusSq = radius * radius;
        for (int poolX = -radius; poolX <= radius; poolX++)
        {
            for (int poolY = -radius; poolY <= radius; poolY++)
            {
                for (int poolZ = -radius; poolZ <= radius; poolZ++)
                {
                    int distance = poolX * poolX + poolY * poolY + poolZ * poolZ;

                    if (distance <= radiusSq)
                    {
                        BlockPos pos = new BlockPos(poolX + position.getX(), poolY + position.getY(), poolZ + position.getZ());
                        world.setBlockState(pos, distance >= radiusSq - 16 ? ioSoft : (poolY <= 0 ? FluidRegistry.getFluid("sulphuricacid").getBlock().getDefaultState() : Blocks.AIR.getDefaultState()), distance == radiusSq ? 3 : 2);
                    }
                }
            }
        }

        boolean firstSet = false;
        for (int i = 255; i >= position.getY() + 1; --i)
        {
            BlockPos pos = new BlockPos(position.getX(), i, position.getZ());
            if (world.getBlockState(pos).getBlock() != Blocks.AIR)
            {
                if (!firstSet)
                {
                    world.setBlockState(pos, GSBlocks.IO_BLOCKS.getDefaultState().withProperty(IoBlocks.BASIC_TYPE, IoBlocks.EnumIoBlocks.IO_SULFUR_GEYSER), 3);
                    world.scheduleBlockUpdate(pos, GSBlocks.IO_BLOCKS.getDefaultState().withProperty(IoBlocks.BASIC_TYPE, IoBlocks.EnumIoBlocks.IO_SULFUR_GEYSER).getBlock(), 0, 0);
                    firstSet = true;
                }
                else
                {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                }
            }
        }

        return true;
    }
}
