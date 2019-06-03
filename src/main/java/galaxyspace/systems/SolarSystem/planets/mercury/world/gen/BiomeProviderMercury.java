package galaxyspace.systems.SolarSystem.planets.mercury.world.gen;

import asmodeuscore.core.astronomy.dimension.world.gen.ACBiome;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeProviderSpace;
import net.minecraft.world.biome.Biome;

public class BiomeProviderMercury extends BiomeProviderSpace
{
    @Override
    public Biome getBiome()
    {
        return ACBiome.ACSpace;
    }
}
