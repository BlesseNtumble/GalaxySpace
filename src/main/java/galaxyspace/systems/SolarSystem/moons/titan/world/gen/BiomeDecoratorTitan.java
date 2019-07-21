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
    private WorldGenerator subgruntGen, sapphireOre, emeraldOre, diamondOre, coalOre, lapisOre, redstoneOre;


    public BiomeDecoratorTitan()
    {
        this.subgruntGen = new WorldGenMinableMeta(GSBlocks.TITAN_BLOCKS, 38, 1, true, GSBlocks.TITAN_BLOCKS, 0);
        this.sapphireOre = new WorldGenMinableMeta(GSBlocks.TITAN_BLOCKS, 4, 3, true, GSBlocks.TITAN_BLOCKS, 2);
        this.emeraldOre = new WorldGenMinableMeta(GSBlocks.TITAN_BLOCKS, 4, 4, true, GSBlocks.TITAN_BLOCKS, 2);
        this.diamondOre = new WorldGenMinableMeta(GSBlocks.TITAN_BLOCKS, 6, 5, true, GSBlocks.TITAN_BLOCKS, 2);
        this.coalOre = new WorldGenMinableMeta(GSBlocks.TITAN_BLOCKS, 16, 6, true, GSBlocks.TITAN_BLOCKS, 2);
        this.lapisOre = new WorldGenMinableMeta(GSBlocks.TITAN_BLOCKS, 4, 7, true, GSBlocks.TITAN_BLOCKS, 2);
        this.redstoneOre = new WorldGenMinableMeta(GSBlocks.TITAN_BLOCKS, 6, 8, true, GSBlocks.TITAN_BLOCKS, 2);
    }

    @Override
    protected void decorate()
    {
    	if(GSConfigCore.enableOresGeneration) {

    	}    
    	this.generateOre(25, subgruntGen, 0, 256);
    	
    	this.generateOre(6, sapphireOre, 0, 40);
    	this.generateOre(4, emeraldOre, 0, 20);
    	this.generateOre(6, diamondOre, 0, 20);
    	this.generateOre(16, coalOre, 0, 90);
    	this.generateOre(8, lapisOre, 0, 40);
    	this.generateOre(10, redstoneOre, 0, 30);
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
