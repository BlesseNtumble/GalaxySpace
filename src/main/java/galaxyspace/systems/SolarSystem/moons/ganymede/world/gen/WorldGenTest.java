package galaxyspace.systems.SolarSystem.moons.ganymede.world.gen;

import java.util.Random;

import galaxyspace.core.registers.blocks.GSBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenTest extends WorldGenerator
{
    private Block block;
    /** The maximum radius used when generating a patch of blocks. */
    private int radius;
    private static final String __OBFID = "CL_00000431";

    public WorldGenTest(Block block, int radius)
    {
        this.block = block;
        this.radius = radius;
    }

    public boolean generate(World world, Random rand, int x, int y, int z)
    {
        if (world.getBlock(x, y, z).getMaterial() == Material.water)
        {
            return false;
        }
        else
        {
            int l = rand.nextInt(this.radius - 2) + 2;
            byte b0 = 2;

            for (int i1 = x - l; i1 <= x + l; ++i1)
            {
                for (int j1 = z - l; j1 <= z + l; ++j1)
                {
                    int k1 = i1 - x;
                    int l1 = j1 - z;

                    if (k1 * k1 + l1 * l1 <= l * l)
                    {
                        for (int i2 = y - b0; i2 <= y - b0; ++i2)
                        {
                            Block block = world.getBlock(i1, i2, j1);

                            if (block == GSBlocks.GanymedeBlocks)
                            {
                                world.setBlock(i1, i2, j1, this.block, 0, 2);                                
                            }
                        }
                    }
                }
            }

            return true;
        }
    }
}
