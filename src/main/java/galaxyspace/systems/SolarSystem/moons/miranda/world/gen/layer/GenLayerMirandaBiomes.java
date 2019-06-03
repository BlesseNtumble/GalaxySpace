package galaxyspace.systems.SolarSystem.moons.miranda.world.gen.layer;

import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.miccore.IntCache;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;

public class GenLayerMirandaBiomes extends GenLayer {
	private static CelestialBody body = SolarSystemBodies.mirandaUranus;
	
	private static Biome[] biomes = new Biome[body.getBiomes().size()];

	public GenLayerMirandaBiomes(long l, GenLayer parent) {
		super(l);	
		//biomes = this.getBiomes(body);
		
		for(int i = 0; i < body.getBiomes().size(); i++)
			biomes[i] = body.getBiomes().get(i);
		
		this.parent = parent;
	}

	public GenLayerMirandaBiomes(long l) {
		super(l);		
		//biomes = this.getBiomes(body);
		
		for(int i = 0; i < body.getBiomes().size(); i++)
			biomes[i] = body.getBiomes().get(i);
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
	/*
	private Biome[] getBiomes(CelestialBody body)
	{
		return new Biome[body.getBiomes().size()];
	}*/
}
