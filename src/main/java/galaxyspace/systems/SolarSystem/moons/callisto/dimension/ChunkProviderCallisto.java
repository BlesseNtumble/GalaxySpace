/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair
 *  micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace
 *  micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta
 *  micdoodle8.mods.galacticraft.core.entities.EntityEvolvedCreeper
 *  micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton
 *  micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider
 *  net.minecraft.block.Block
 *  net.minecraft.world.World
 *  net.minecraft.world.biome.BiomeGenBase
 *  net.minecraft.world.biome.BiomeGenBase$SpawnListEntry
 *  net.minecraft.world.chunk.IChunkProvider
 */
package galaxyspace.systems.SolarSystem.moons.callisto.dimension;

import com.google.common.collect.Lists;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.world.gen.ChunkProviderSpaceLakes;
import galaxyspace.core.world.gen.GSBiomeGenBase;
import galaxyspace.systems.SolarSystem.moons.callisto.world.gen.BiomeDecoratorCallisto;
import galaxyspace.systems.SolarSystem.moons.callisto.world.gen.MapGenCavesCallisto;
import java.util.ArrayList;
import java.util.List;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedCreeper;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

public class ChunkProviderCallisto
extends ChunkProviderSpaceLakes {
    private List<MapGenBaseMeta> worldGenerators;
    private BiomeGenBase[] biomesForGeneration = this.getBiomesForGeneration();
    private final MapGenCavesCallisto caveGenerator = new MapGenCavesCallisto();

    @Override
    protected List<MapGenBaseMeta> getWorldGenerators() {
        ArrayList generators = Lists.newArrayList();
        generators.add(this.caveGenerator);
        return generators;
    }

    public ChunkProviderCallisto(World par1World, long seed, boolean mapFeaturesEnabled) {
        super(par1World, seed, mapFeaturesEnabled);
    }

    @Override
    protected BiomeDecoratorSpace getBiomeGenerator() {
        return new BiomeDecoratorCallisto();
    }

    @Override
    protected BiomeGenBase[] getBiomesForGeneration() {
        return new BiomeGenBase[]{GSBiomeGenBase.GSSpace};
    }

    @Override
    public int getCraterProbability() {
        return 500;
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
        return 15.0;
    }

    @Override
    public int getWaterLevel() {
        return 70;
    }

    @Override
    public double getSmallFeatureHeightModifier() {
        return 10.0;
    }

    @Override
    public double getValleyHeightModifier() {
        return 1.0;
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
        return new BlockMetaPair(GSBlocks.CallistoBlocks, (byte) 0);
    }

    @Override
    protected BlockMetaPair getDirtBlock() {
        return new BlockMetaPair(GSBlocks.CallistoBlocks, (byte) 1);
    }

    @Override
    protected BlockMetaPair getStoneBlock() {
        return new BlockMetaPair(GSBlocks.CallistoBlocks, (byte) 1);
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

