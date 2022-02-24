package galaxyspace.systems.SolarSystem.planets.pluto.dimension;

 

import java.util.List;

import com.google.common.collect.Lists;

import asmodeuscore.core.astronomy.dimension.world.gen.ACBiome;
import asmodeuscore.core.astronomy.dimension.world.gen.ChunkProviderSpaceLakes;
import asmodeuscore.core.astronomy.dimension.world.gen.MapGenCaves;
import galaxyspace.core.GSBlocks;
import galaxyspace.systems.SolarSystem.planets.pluto.world.gen.BiomeDecoratorPluto;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;


 

public class ChunkProviderPluto extends ChunkProviderSpaceLakes {
	
    private final MapGenCaves caveGenerator = new MapGenCaves(GSBlocks.PLUTO_BLOCKS.getStateFromMeta(0), GSBlocks.PLUTO_BLOCKS.getStateFromMeta(4), GSBlocks.PLUTO_BLOCKS.getStateFromMeta(5));

	@Override
	protected List<MapGenBaseMeta> getWorldGenerators()
	{
		List<MapGenBaseMeta> generators = Lists.newArrayList();
		generators.add(this.caveGenerator);
		return generators;
	}
	
	public ChunkProviderPluto(World par1World, long seed, boolean mapFeaturesEnabled)	
	{
		super(par1World, seed, mapFeaturesEnabled);
		
		this.setBlocks(new GenBlocks(this.worldObj.provider, ACBiome.ACSpace, GSBlocks.PLUTO_BLOCKS.getStateFromMeta(0), this.getDirtBlock()));
		this.setBlocks(new GenBlocks(this.worldObj.provider, ACBiome.ACSpaceLowPlains, GSBlocks.PLUTO_BLOCKS.getStateFromMeta(1), this.getDirtBlock()));
		this.setBlocks(new GenBlocks(this.worldObj.provider, ACBiome.ACSpaceMidPlains, GSBlocks.PLUTO_BLOCKS.getStateFromMeta(2), this.getDirtBlock()));
		this.setBlocks(new GenBlocks(this.worldObj.provider, ACBiome.ACSpaceLowHills, GSBlocks.PLUTO_BLOCKS.getStateFromMeta(3), this.getDirtBlock()));
	}
	
	@Override	
	protected BiomeDecoratorSpace getBiomeGenerator() {	
	    return new BiomeDecoratorPluto();	
	}
	//This should be a custom biome for your mod, but I'm opting to go desert instead out of quickness
	//and the fact that biomes are outside the scope of this tutorial
	
	@Override	
	protected Biome[] getBiomesForGeneration() {	
	    return new Biome[]{ACBiome.ACSpace, ACBiome.ACSpaceLowPlains, ACBiome.ACSpaceMidPlains, ACBiome.ACSpaceLowHills};	
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
		return GSBlocks.PLUTO_BLOCKS.getStateFromMeta(0);
	}
	
	@Override
	protected IBlockState getDirtBlock() {
		return GSBlocks.PLUTO_BLOCKS.getStateFromMeta(4);
	}
	
	@Override
	protected IBlockState getStoneBlock() {
		return GSBlocks.PLUTO_BLOCKS.getStateFromMeta(5);
	}
	
	@Override
	protected boolean enableBiomeGenBaseBlock() {
		return true;
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

	
}