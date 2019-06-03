package galaxyspace.systems.SolarSystem.moons.io.world.gen.layers;

import java.util.List;

import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import micdoodle8.mods.miccore.IntCache;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;

public class GenLayerIoBiomes extends GenLayer {
	private static List<Biome> bodybiome = SolarSystemBodies.ioJupiter.getBiomes();
	private static final Biome[] biomes = new Biome[bodybiome.size()];

	public GenLayerIoBiomes(long l, GenLayer parent) {
		super(l);		
		for(int i = 0; i < bodybiome.size(); i++)
			biomes[i] = bodybiome.get(i);
		
		this.parent = parent;
	}

	public GenLayerIoBiomes(long l) {
		super(l);		
		for(int i = 0; i < bodybiome.size(); i++)
			biomes[i] = bodybiome.get(i);
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
