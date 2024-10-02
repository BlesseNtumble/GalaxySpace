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
package galaxyspace.systems.SolarSystem.moons.titan.dimension;

import com.google.common.collect.Lists;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.fluids.GSFluids;
import galaxyspace.core.world.gen.ChunkProviderSpaceLakes;
import galaxyspace.core.world.gen.GSBiomeGenBase;
import galaxyspace.systems.SolarSystem.moons.titan.world.gen.BiomeDecoratorTitanOre;
import galaxyspace.systems.SolarSystem.moons.titan.world.gen.MapGenCavesTitan;
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

public class ChunkProviderTitan
extends ChunkProviderSpaceLakes {
    private List<MapGenBaseMeta> worldGenerators;
    private BiomeGenBase[] biomesForGeneration = this.getBiomesForGeneration();
    private final MapGenCavesTitan caveGenerator = new MapGenCavesTitan();

    @Override
    protected List<MapGenBaseMeta> getWorldGenerators() {
        ArrayList generators = Lists.newArrayList();
        generators.add(this.caveGenerator);
        return generators;
    }

    public ChunkProviderTitan(World par1World, long seed, boolean mapFeaturesEnabled) {
        super(par1World, seed, mapFeaturesEnabled);
    }

    @Override
    protected BiomeDecoratorSpace getBiomeGenerator() {
        return new BiomeDecoratorTitanOre();
    }

    @Override
    protected BiomeGenBase[] getBiomesForGeneration() {
        return new BiomeGenBase[]{GSBiomeGenBase.GSSpace};
    }

    @Override
    protected BiomeGenBase.SpawnListEntry[] getCreatures() {
        return new BiomeGenBase.SpawnListEntry[0];
    }

    @Override
    public double getHeightModifier() {
        return 15.0;
    }

    @Override
    protected BiomeGenBase.SpawnListEntry[] getMonsters() {
        BiomeGenBase.SpawnListEntry skele = new BiomeGenBase.SpawnListEntry(EntityEvolvedSkeleton.class, 100, 4, 4);
        BiomeGenBase.SpawnListEntry creeper = new BiomeGenBase.SpawnListEntry(EntityEvolvedCreeper.class, 100, 4, 4);
        BiomeGenBase.SpawnListEntry spider = new BiomeGenBase.SpawnListEntry(EntityEvolvedSpider.class, 100, 4, 4);
        return new BiomeGenBase.SpawnListEntry[]{skele, creeper, spider};
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
        return new BlockMetaPair(GSBlocks.TitanBlocks, (byte) 0);
    }

    @Override
    protected BlockMetaPair getDirtBlock() {
        return new BlockMetaPair(GSBlocks.TitanBlocks, (byte) 1);
    }

    @Override
    protected BlockMetaPair getStoneBlock() {
        return new BlockMetaPair(GSBlocks.TitanBlocks, (byte) 2);
    }

    @Override
    protected boolean enableBiomeGenBaseBlock() {
        return false;
    }

    @Override
    public void onChunkProvider(int cX, int cZ, Block[] blocks, byte[] metadata) {
    }

    @Override
    public int getWaterLevel() {
        return 64;
    }

    @Override
    public boolean canGenerateWaterBlock() {
        return true;
    }

    @Override
    protected BlockMetaPair getWaterBlock() {
        return new BlockMetaPair(GSFluids.BlockLiquidMethane, (byte) 0);
    }

    @Override
    public boolean canGenerateIceBlock() {
        return true;
    }

    @Override
    public double getSmallFeatureHeightModifier() {
        return 0.0;
    }

    @Override
    public double getMountainHeightModifier() {
        return 0.0;
    }

    @Override
    public double getValleyHeightModifier() {
        return 0.0;
    }

    @Override
    public int getCraterProbability() {
        return 0;
    }

    @Override
    protected ChunkProviderSpaceLakes.GenType getGenType() {
        return ChunkProviderSpaceLakes.GenType.VANILLA;
    }
}

