package galaxyspace.systems.SolarSystem.moons.titan.world.gen;

import galaxyspace.GalaxySpace;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.registers.blocks.GSBlocks;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorTitan extends BiomeDecoratorSpace
{

    private World currentWorld;
    private WorldGenerator subgruntGen;


    public BiomeDecoratorTitan()
    {
        this.subgruntGen = new WorldGenMinableMeta(GSBlocks.TITAN_BLOCKS, 38, 1, true, GSBlocks.TITAN_BLOCKS, 0);
    }

    @Override
    protected void decorate()
    {
    	if(GSConfigCore.enableOresGeneration) {

    	}    
    	this.generateOre(25, subgruntGen, 0, 256);
    }

    @Override
    protected void setCurrentWorld(World world)
    {
        this.currentWorld = world;
    }

    @Override
    protected World getCurrentWorld()
    {
        return this.currentWorld;
    }
    
    protected void generateOre(int amountPerChunk, WorldGenerator worldGenerator, int minY, int maxY)
    {
        World currentWorld = this.getCurrentWorld();
		for (int var5 = 0; var5 < amountPerChunk; ++var5)
        {
            final int var6 = this.posX + this.rand.nextInt(16);
            final int var7 = this.rand.nextInt(maxY - minY) + minY;
            final int var8 = this.posZ + this.rand.nextInt(16);
            if(currentWorld.isBlockLoaded(new BlockPos(var6, var7, var8)))
            	worldGenerator.generate(currentWorld, this.rand, new BlockPos(var6, var7, var8));
        }
    }
}
