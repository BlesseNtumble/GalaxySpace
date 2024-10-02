package galaxyspace.systems.SolarSystem.satellites.venus.dimension;

import galaxyspace.core.world.gen.GSBiomeGenBase;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldChunkManagerSpace;
import net.minecraft.world.biome.BiomeGenBase;

public class WorldChunkManagerVenusSS extends WorldChunkManagerSpace
{
    @Override
    public BiomeGenBase getBiome()
    {
        return GSBiomeGenBase.GSSpace;
    }
}
