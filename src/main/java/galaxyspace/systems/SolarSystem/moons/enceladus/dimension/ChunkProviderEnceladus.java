/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair
 *  micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace
 *  micdoodle8.mods.galacticraft.core.entities.EntityEvolvedCreeper
 *  micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton
 *  micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider
 *  micdoodle8.mods.galacticraft.core.perlin.NoiseModule
 *  micdoodle8.mods.galacticraft.core.perlin.generator.Gradient
 *  net.minecraft.block.Block
 *  net.minecraft.init.Blocks
 *  net.minecraft.world.World
 *  net.minecraft.world.biome.BiomeGenBase
 *  net.minecraft.world.biome.BiomeGenBase$SpawnListEntry
 *  net.minecraft.world.chunk.IChunkProvider
 */
package galaxyspace.systems.SolarSystem.moons.enceladus.dimension;

import com.google.common.collect.Lists;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.world.gen.ChunkProviderSpaceLakes;
import galaxyspace.core.world.gen.GSBiomeGenBase;
import galaxyspace.systems.SolarSystem.moons.enceladus.world.gen.BiomeDecoratorEnceladus;
import galaxyspace.systems.SolarSystem.moons.enceladus.world.gen.MapGenCavesEnceladus;
import galaxyspace.systems.SolarSystem.moons.enceladus.world.gen.MapGenRavineEnceladus;
import java.util.ArrayList;
import java.util.List;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedCreeper;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import micdoodle8.mods.galacticraft.core.perlin.NoiseModule;
import micdoodle8.mods.galacticraft.core.perlin.generator.Gradient;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

public class ChunkProviderEnceladus
extends ChunkProviderSpaceLakes {
    private final NoiseModule noiseGen4;
    private static final int CHUNK_SIZE_X = 16;
    private static final int CHUNK_SIZE_Y = 128;
    private static final int CHUNK_SIZE_Z = 16;
    private BiomeGenBase[] biomesForGeneration = this.getBiomesForGeneration();
    private final MapGenCavesEnceladus caveGenerator = new MapGenCavesEnceladus();
    private final MapGenRavineEnceladus ravineGenerator = new MapGenRavineEnceladus();

    protected List getWorldGenerators() {
        ArrayList generators = Lists.newArrayList();
        generators.add(this.caveGenerator);
        generators.add(this.ravineGenerator);
        return generators;
    }

    public ChunkProviderEnceladus(World par1World, long seed, boolean mapFeaturesEnabled) {
        super(par1World, seed, mapFeaturesEnabled);
        this.noiseGen4 = new Gradient(this.rand.nextLong(), 1, 0.25f);
    }

    @Override
    protected BiomeDecoratorSpace getBiomeGenerator() {
        return new BiomeDecoratorEnceladus();
    }

    @Override
    protected BiomeGenBase[] getBiomesForGeneration() {
        return new BiomeGenBase[]{GSBiomeGenBase.GSSpace};
    }

    @Override
    public int getCraterProbability() {
        return 5;
    }

    @Override
    protected BiomeGenBase.SpawnListEntry[] getCreatures() {
        return new BiomeGenBase.SpawnListEntry[0];
    }

    @Override
    public double getHeightModifier() {
        return 20.0;
    }

    @Override
    protected BiomeGenBase.SpawnListEntry[] getMonsters() {
        BiomeGenBase.SpawnListEntry skele = new BiomeGenBase.SpawnListEntry(EntityEvolvedSkeleton.class, 100, 4, 4);
        BiomeGenBase.SpawnListEntry creeper = new BiomeGenBase.SpawnListEntry(EntityEvolvedCreeper.class, 100, 4, 4);
        BiomeGenBase.SpawnListEntry spider = new BiomeGenBase.SpawnListEntry(EntityEvolvedSpider.class, 100, 4, 4);
        return new BiomeGenBase.SpawnListEntry[]{skele, creeper, spider};
    }

    @Override
    public double getMountainHeightModifier() {
        return 10.0;
    }

    @Override
    public int getWaterLevel() {
        return 70;
    }

    @Override
    public double getSmallFeatureHeightModifier() {
        return 55.0;
    }

    @Override
    public double getValleyHeightModifier() {
        return 50.0;
    }

    @Override
    public void onChunkProvider(int cX, int cZ, Block[] blocks, byte[] metadata) {
    }

    @Override
    public void onPopulate(IChunkProvider arg0, int arg1, int arg2) {
    }

    @Override
    public boolean chunkExists(int x, int y) {
        return false;
    }

    @Override
    protected BiomeGenBase.SpawnListEntry[] getWaterCreatures() {
        return new BiomeGenBase.SpawnListEntry[0];
    }

    @Override
    protected BlockMetaPair getGrassBlock() {
        return new BlockMetaPair(GSBlocks.EnceladusBlocks, (byte) 0);
    }

    @Override
    protected BlockMetaPair getDirtBlock() {
        return new BlockMetaPair(Blocks.packed_ice, (byte) 0);
    }

    @Override
    protected BlockMetaPair getStoneBlock() {
        return new BlockMetaPair(GSBlocks.EnceladusBlocks, (byte) 1);
    }

    @Override
    protected boolean enableBiomeGenBaseBlock() {
        return false;
    }

    @Override
    public boolean canGenerateWaterBlock() {
        return false;
    }

    @Override
    public boolean canGenerateIceBlock() {
        return false;
    }

    @Override
    protected BlockMetaPair getWaterBlock() {
        return null;
    }

    @Override
    protected ChunkProviderSpaceLakes.GenType getGenType() {
        return ChunkProviderSpaceLakes.GenType.GC;
    }
}

