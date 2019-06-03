package galaxyspace.systems.SolarSystem.planets.pluto.world.gen.layers;

import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import micdoodle8.mods.miccore.IntCache;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;

public class GenLayerPlutoBiomes extends GenLayer {
	private static final Biome[] biomes = new Biome[SolarSystemBodies.planetPluto.getBiomes().size()];

	public GenLayerPlutoBiomes(long l, GenLayer parent) {
		super(l);		
		for(int i = 0; i < SolarSystemBodies.planetPluto.getBiomes().size(); i++)
			biomes[i] = SolarSystemBodies.planetPluto.getBiomes().get(i);
		
		this.parent = parent;
	}

	public GenLayerPlutoBiomes(long l) {
		super(l);		
		for(int i = 0; i < SolarSystemBodies.planetPluto.getBiomes().size(); i++)
			biomes[i] = SolarSystemBodies.planetPluto.getBiomes().get(i);
	}

	@Override
	public int[] getInts(int x, int z, int width, int depth) {
		int[] dest = IntCache.getIntCache(width * depth);

		for (int k = 0; k < depth; ++k) {
			for (int i = 0; i < width; ++i) {
				initChunkSeed(x + i, z + k);
				dest[i + k * width] = Biome.getIdForBiome(biomes[nextInt(biomes.length)]);
			}
		}

		return dest;
	}
}
