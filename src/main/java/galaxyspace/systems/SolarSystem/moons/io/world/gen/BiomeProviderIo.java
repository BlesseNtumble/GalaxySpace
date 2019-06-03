package galaxyspace.systems.SolarSystem.moons.io.world.gen;

import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.layers.GenLayerIoBiomes;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public class BiomeProviderIo extends BiomeProvider
{
    public BiomeProviderIo(World world)
    {
    	super(world.getWorldInfo());
    	allowedBiomes.clear();
    	allowedBiomes = SolarSystemBodies.ioJupiter.getBiomes();
    }

    @Override
    public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original)
    {
    	GenLayer biomes = new GenLayerIoBiomes(seed);
		biomes = new GenLayerZoom(1000L, biomes);
		biomes = new GenLayerZoom(1001L, biomes);
		biomes = new GenLayerZoom(1002L, biomes);
		biomes = new GenLayerZoom(1003L, biomes);
		biomes = new GenLayerZoom(1004L, biomes);
		GenLayer genLayerVeronoiZoom = new GenLayerVoronoiZoom(10L, biomes);
		
		biomes.initWorldGenSeed(seed);
		genLayerVeronoiZoom.initWorldGenSeed(seed);

		return new GenLayer[] { biomes, genLayerVeronoiZoom };
    }
  
}