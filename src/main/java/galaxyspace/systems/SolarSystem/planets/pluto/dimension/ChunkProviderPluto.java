package galaxyspace.systems.SolarSystem.planets.pluto.dimension;

 

import java.util.List;

import com.google.common.collect.Lists;

import asmodeuscore.core.astronomy.dimension.world.gen.ACBiome;
import asmodeuscore.core.astronomy.dimension.world.gen.ChunkProviderSpaceLakes;
import asmodeuscore.core.astronomy.dimension.world.gen.MapGenCaves;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.systems.SolarSystem.planets.pluto.world.gen.BiomeDecoratorPluto;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;


 

public class ChunkProviderPluto extends ChunkProviderSpaceLakes {
	

    private List<MapGenBaseMeta> worldGenerators;
    
    private final MapGenCaves caveGenerator = new MapGenCaves(GSBlocks.PLUTO_BLOCKS, 0, 4, 5);

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
		
		this.setBlocks(new GenBlocks(this.worldObj.provider, ACBiome.ACSpace, new BlockMetaPair(GSBlocks.PLUTO_BLOCKS, (byte) 0), this.getDirtBlock(), this.getStoneBlock()));
		this.setBlocks(new GenBlocks(this.worldObj.provider, ACBiome.ACSpaceLowPlains, new BlockMetaPair(GSBlocks.PLUTO_BLOCKS, (byte) 1), this.getDirtBlock(), this.getStoneBlock()));
		this.setBlocks(new GenBlocks(this.worldObj.provider, ACBiome.ACSpaceMidPlains, new BlockMetaPair(GSBlocks.PLUTO_BLOCKS, (byte) 2), this.getDirtBlock(), this.getStoneBlock()));
		this.setBlocks(new GenBlocks(this.worldObj.provider, ACBiome.ACSpaceLowHills, new BlockMetaPair(GSBlocks.PLUTO_BLOCKS, (byte) 3), this.getDirtBlock(), this.getStoneBlock()));
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
	protected BlockMetaPair getGrassBlock() {
		return new BlockMetaPair(GSBlocks.PLUTO_BLOCKS, (byte) 0);
	}
	
	@Override
	protected BlockMetaPair getDirtBlock() {
		return new BlockMetaPair(GSBlocks.PLUTO_BLOCKS, (byte) 4);
	}
	
	@Override
	protected BlockMetaPair getStoneBlock() {
		return new BlockMetaPair(GSBlocks.PLUTO_BLOCKS, (byte) 5);
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
	protected BlockMetaPair getWaterBlock() {
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