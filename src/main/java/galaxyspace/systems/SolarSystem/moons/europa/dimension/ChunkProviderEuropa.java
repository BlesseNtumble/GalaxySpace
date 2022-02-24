package galaxyspace.systems.SolarSystem.moons.europa.dimension;

 

import java.util.List;

import com.google.common.collect.Lists;

import asmodeuscore.core.astronomy.dimension.world.gen.ACBiome;
import asmodeuscore.core.astronomy.dimension.world.gen.ChunkProviderSpaceLakes;
import galaxyspace.core.GSBlocks;
import galaxyspace.systems.SolarSystem.moons.europa.world.gen.BiomeDecoratorEuropa;
import galaxyspace.systems.SolarSystem.moons.europa.world.gen.MapGenRavineEuropa;
import galaxyspace.systems.SolarSystem.moons.europa.world.gen.MapGenSnowEuropa;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;


 

public class ChunkProviderEuropa extends ChunkProviderSpaceLakes {
	

    private List<MapGenBaseMeta> worldGenerators;
    
    //private final MapGenCaves caveGenerator = new MapGenCaves(GSBlocks.CERES_BLOCKS, 0, 1, 1);
    private final MapGenRavineEuropa ravineGenerator = new MapGenRavineEuropa();
    private final MapGenSnowEuropa snowGenerator = new MapGenSnowEuropa();
    
	@Override
	protected List<MapGenBaseMeta> getWorldGenerators()
	{
		List<MapGenBaseMeta> generators = Lists.newArrayList();
		generators.add(this.ravineGenerator);
		generators.add(this.snowGenerator);
		return generators;
	}
	
	public ChunkProviderEuropa(World par1World, long seed, boolean mapFeaturesEnabled)	
	{
		super(par1World, seed, mapFeaturesEnabled);
	}
	
	@Override	
	protected BiomeDecoratorSpace getBiomeGenerator() {	
	    return new BiomeDecoratorEuropa();	
	}
	//This should be a custom biome for your mod, but I'm opting to go desert instead out of quickness
	//and the fact that biomes are outside the scope of this tutorial
	
	@Override	
	protected Biome[] getBiomesForGeneration() {	
	    return new Biome[]{ACBiome.ACSpace};	
	}
	
	@Override
	public int getCraterProbability() {	
	    return 900;	
	}
	

		
	@Override
	public double getHeightModifier() {	
	    return 8;	
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
	    return 95;
	}
	
	@Override	
	public double getSmallFeatureHeightModifier() {	
	    return 0;	
	}
	
	@Override	
	public double getValleyHeightModifier() {	
	    return 0;	
	}
	
	@Override
	protected IBlockState getGrassBlock() {
		return GSBlocks.EUROPA_BLOCKS.getStateFromMeta(0);
	}
	
	@Override
	protected IBlockState getDirtBlock() {
		return Blocks.PACKED_ICE.getDefaultState();
	}
	
	@Override
	protected IBlockState getStoneBlock() {
		return Blocks.WATER.getDefaultState();
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
		return true;
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
	
	@Override
	public double getDirtLayerSize()
    {
    	return 40.0D;
    }
	
	@Override
	protected IBlockState getIceBlock() {	
		return GSBlocks.EUROPA_BLOCKS.getStateFromMeta(1);
	}

	
}