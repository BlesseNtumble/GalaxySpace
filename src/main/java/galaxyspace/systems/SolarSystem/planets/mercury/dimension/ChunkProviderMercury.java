package galaxyspace.systems.SolarSystem.planets.mercury.dimension;

 

import java.util.List;

import com.google.common.collect.Lists;

import asmodeuscore.core.astronomy.dimension.world.gen.ChunkProviderSpaceLakes;
import galaxyspace.core.GSBlocks;
import galaxyspace.core.prefab.world.gen.MapGenCavesGS;
import galaxyspace.systems.SolarSystem.planets.mercury.world.gen.BiomeDecoratorMercury;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeAdaptive;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;


 

public class ChunkProviderMercury extends ChunkProviderSpaceLakes {
	

    private List<MapGenBaseMeta> worldGenerators;
    
    private final MapGenCavesGS caveGenerator = new MapGenCavesGS(
    		GSBlocks.MERCURY_BLOCKS.getStateFromMeta(0),
    		GSBlocks.MERCURY_BLOCKS.getStateFromMeta(1),
    		GSBlocks.MERCURY_BLOCKS.getStateFromMeta(2),
    		Blocks.LAVA.getDefaultState(),
    		20);

	@Override
	protected List<MapGenBaseMeta> getWorldGenerators()
	{
		List<MapGenBaseMeta> generators = Lists.newArrayList();
		generators.add(this.caveGenerator);
		return generators;
	}
	
	public ChunkProviderMercury(World par1World, long seed, boolean mapFeaturesEnabled)	
	{
		super(par1World, seed, mapFeaturesEnabled);
	}
	
	@Override	
	protected BiomeDecoratorSpace getBiomeGenerator() {	
	    return new BiomeDecoratorMercury();	
	}
	//This should be a custom biome for your mod, but I'm opting to go desert instead out of quickness
	//and the fact that biomes are outside the scope of this tutorial
	
	@Override	
	protected Biome[] getBiomesForGeneration() {	
	    return new Biome[]{BiomeAdaptive.biomeDefault};	
	}
	
	@Override
	public int getCraterProbability() {	
	    return 300;	
	}
	

		
	@Override
	public double getHeightModifier() {	
	    return 20;	
	}
		
	@Override
	public void onChunkProvider(int cX, int cZ, ChunkPrimer primer) {

	}

	@Override
	public void onPopulate(int cX, int cZ) {
		
	}
	    
	@Override
	public double getMountainHeightModifier() {
	    return 0;
	}
	
	@Override
	public int getWaterLevel() {
	    return 80;
	}
	
	@Override	
	public double getSmallFeatureHeightModifier() {	
	    return 1;	
	}
	
	@Override	
	public double getValleyHeightModifier() {	
	    return 0;	
	}
	
	@Override
	protected IBlockState getGrassBlock() {
		return GSBlocks.MERCURY_BLOCKS.getStateFromMeta(0);
	}
	
	@Override
	protected IBlockState getDirtBlock() {
		return GSBlocks.MERCURY_BLOCKS.getStateFromMeta(1);
	}
	
	@Override
	protected IBlockState getStoneBlock() {
		return GSBlocks.MERCURY_BLOCKS.getStateFromMeta(2);
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
	protected IBlockState getWaterBlock() {
		return null;
	}

	@Override
	protected GenType getGenType() {
		return GenType.GC;
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {
		
	}

	
}