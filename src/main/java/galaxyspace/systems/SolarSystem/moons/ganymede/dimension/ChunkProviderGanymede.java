package galaxyspace.systems.SolarSystem.moons.ganymede.dimension;

 

import java.util.List;

import com.google.common.collect.Lists;

import asmodeuscore.core.astronomy.dimension.world.gen.ACBiome;
import asmodeuscore.core.astronomy.dimension.world.gen.ChunkProviderSpaceLakes;
import asmodeuscore.core.astronomy.dimension.world.gen.MapGenCaves;
import asmodeuscore.core.astronomy.dimension.world.gen.MapGenCaves.BlockGen;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.systems.SolarSystem.moons.ganymede.world.gen.BiomeDecoratorGanymede;
import galaxyspace.systems.SolarSystem.moons.ganymede.world.gen.MapGenSnowGanymede;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;


 

public class ChunkProviderGanymede extends ChunkProviderSpaceLakes {
	

    private List<MapGenBaseMeta> worldGenerators;
    

    private BlockGen grass = new BlockGen(getGrassBlock().getBlockState(), true);
    private BlockGen subgrunt = new BlockGen(getDirtBlock().getBlockState(), false);
    private BlockGen stone = new BlockGen(getStoneBlock().getBlockState(), false);
    
    
    private final MapGenCaves caveGenerator = new MapGenCaves(grass, subgrunt, stone);
    private final MapGenSnowGanymede snowGenerator = new MapGenSnowGanymede();

    
	@Override
	protected List<MapGenBaseMeta> getWorldGenerators()
	{
		List<MapGenBaseMeta> generators = Lists.newArrayList();
		generators.add(this.caveGenerator);
		generators.add(this.snowGenerator);
		return generators;
	}
	
	public ChunkProviderGanymede(World par1World, long seed, boolean mapFeaturesEnabled)	
	{
		super(par1World, seed, mapFeaturesEnabled);
	}
	
	@Override	
	protected BiomeDecoratorSpace getBiomeGenerator() {	
	    return new BiomeDecoratorGanymede();	
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
	    return 25;	
	}
		
	@Override
	public void onChunkProvider(int cX, int cZ, ChunkPrimer primer) {

	}

	@Override
	public void onPopulate(int cX, int cZ) {
	}
	    
	@Override
	public double getMountainHeightModifier() {
	    return 45;
	}
	
	@Override
	public int getWaterLevel() {
	    return 70;
	}
	
	@Override	
	public double getSmallFeatureHeightModifier() {	
	    return 35;	
	}
	
	@Override	
	public double getValleyHeightModifier() {	
	    return 35;	
	}
	
	@Override
	protected BlockMetaPair getGrassBlock() {
		return new BlockMetaPair(GSBlocks.GANYMEDE_BLOCKS, (byte) 0);
	}
	
	@Override
	protected BlockMetaPair getDirtBlock() {
		return new BlockMetaPair(GSBlocks.SURFACE_ICE, (byte) 0);
	}
	
	@Override
	protected BlockMetaPair getStoneBlock() {
		return new BlockMetaPair(GSBlocks.GANYMEDE_BLOCKS, (byte) 1);
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
	
	@Override
	public void getCraterAdditions(ChunkPrimer primer, int x, int y, int z)
	{
		/*if(this.rand.nextInt(8) == 0 && primer.getBlockState(x, y - 1, z).getBlock() != Blocks.AIR)
		{
			primer.setBlockState(x, y - 1, z, GSBlocks.SURFACE_ICE.getDefaultState());
		}*/
	}
	
}