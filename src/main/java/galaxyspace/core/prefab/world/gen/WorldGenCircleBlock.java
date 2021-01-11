package galaxyspace.core.prefab.world.gen;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCircleBlock extends WorldGenerator
{
    private final IBlockState block, replace;
    private final int radius;

    public WorldGenCircleBlock(IBlockState gen_block, int radius, IBlockState replace)
    {
        this.block = gen_block;
        this.radius = radius;
        this.replace = replace;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
    	
        {
            int i = rand.nextInt(this.radius - 2) + 2;
            //int j = 2;

            for (int k = position.getX() - i; k <= position.getX() + i; ++k)
            {
                for (int l = position.getZ() - i; l <= position.getZ() + i; ++l)
                {
                    int i1 = k - position.getX();
                    int j1 = l - position.getZ();

                    if (i1 * i1 + j1 * j1 <= i * i)
                    {
                        for (int k1 = position.getY() - 2; k1 <= position.getY() + 2; ++k1)
                        {
                            BlockPos blockpos = new BlockPos(k, k1, l);
                            IBlockState block = worldIn.getBlockState(blockpos);

                            if (block == this.replace)
                            {
                            	
                                worldIn.setBlockState(blockpos, this.block, 2);
                            }
                        }
                    }
                }
            }

        }
        return true;
    }
}
