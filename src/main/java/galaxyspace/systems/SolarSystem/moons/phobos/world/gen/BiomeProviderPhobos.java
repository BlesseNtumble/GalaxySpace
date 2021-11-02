package galaxyspace.systems.SolarSystem.moons.phobos.world.gen;

import asmodeuscore.core.astronomy.dimension.world.gen.ACBiome;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeProviderSpace;
import net.minecraft.world.biome.Biome;

public class BiomeProviderPhobos extends BiomeProviderSpace
{
    @Override
    public Biome getBiome()
    {
        return ACBiome.ACSpace;
    }
}
