package galaxyspace.systems.SolarSystem.moons.titan.dimension;

 

import java.util.List;

import com.google.common.collect.Lists;

import asmodeuscore.core.astronomy.dimension.world.gen.ACBiome;
import asmodeuscore.core.astronomy.dimension.world.gen.ChunkProviderSpaceLakes;
import galaxyspace.core.GSBlocks;
import galaxyspace.core.GSFluids;
import galaxyspace.systems.SolarSystem.moons.titan.world.gen.BiomeDecoratorTitan;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;


 

public class ChunkProviderTitan extends ChunkProviderSpaceLakes {	

    private List<MapGenBaseMeta> worldGenerators;
    
    //private final MapGenCaves caveGenerator = new MapGenCaves(GSBlocks.CALLISTO_BLOCKS, 0, 1, 1);
    
	@Override
	protected List<MapGenBaseMeta> getWorldGenerators()
	{
		List<MapGenBaseMeta> generators = Lists.newArrayList();
		//generators.add(this.ravineGenerator);
		return generators;
	}
	
	public ChunkProviderTitan(World par1World, long seed, boolean mapFeaturesEnabled)	
	{
		super(par1World, seed, mapFeaturesEnabled);
	}
	
	@Override	
	protected BiomeDecoratorSpace getBiomeGenerator() {	
	    return new BiomeDecoratorTitan();	
	}
	//This should be a custom biome for your mod, but I'm opting to go desert instead out of quickness
	//and the fact that biomes are outside the scope of this tutorial
	
	@Override	
	protected Biome[] getBiomesForGeneration() {	
	    return new Biome[]{ACBiome.ACSpace};	
	}
	
	@Override
	public int getCraterProbability() {	
	    return 0;	
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
	    return 10;
	}
	
	@Override
	public int getWaterLevel() {
	    return 75;
	}
	
	@Override
	public int getTerrainLevel() {
	    return 64;
	}
	
	@Override	
	public double getSmallFeatureHeightModifier() {	
	    return 10;	
	}
	
	@Override	
	public double getValleyHeightModifier() {	
	    return 0;	
	}
	
	@Override
	protected IBlockState getGrassBlock() {
		return GSBlocks.TITAN_BLOCKS.getStateFromMeta(0);
	}
	
	@Override
	protected IBlockState getDirtBlock() {
		return GSBlocks.TITAN_BLOCKS.getStateFromMeta(1);
	}
	
	@Override
	protected IBlockState getStoneBlock() {
		return GSBlocks.TITAN_BLOCKS.getStateFromMeta(2);
	}
	
	@Override
	protected boolean enableBiomeGenBaseBlock() {
		return false;
	}

	@Override
	public boolean canGenerateWaterBlock() {
		return true;
	}

	@Override
	public boolean canGenerateIceBlock() {
		return true;
	}

	@Override
	protected IBlockState getWaterBlock() {
		return GSFluids.BLOCK_LEMETHANE.getDefaultState();
	}

	@Override
	protected GenType getGenType() {
		return GenType.VANILLA;
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {
		
	}
	
}