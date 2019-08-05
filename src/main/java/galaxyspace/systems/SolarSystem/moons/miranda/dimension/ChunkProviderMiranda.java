package galaxyspace.systems.SolarSystem.moons.miranda.dimension;

 

import java.util.List;

import com.google.common.collect.Lists;

import asmodeuscore.core.astronomy.dimension.world.gen.ACBiome;
import asmodeuscore.core.astronomy.dimension.world.gen.ChunkProviderSpaceLakes;
import asmodeuscore.core.astronomy.dimension.world.gen.MapGenCaves;
import asmodeuscore.core.astronomy.dimension.world.gen.MapGenCaves.BlockGen;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.systems.SolarSystem.moons.miranda.blocks.MirandaBlocks;
import galaxyspace.systems.SolarSystem.moons.miranda.world.gen.BiomeDecoratorMiranda;
import galaxyspace.systems.SolarSystem.moons.miranda.world.gen.MapGenIceMiranda;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;


 

public class ChunkProviderMiranda extends ChunkProviderSpaceLakes {
	

    private List<MapGenBaseMeta> worldGenerators;
    
    private BlockGen grass = new BlockGen(GSBlocks.MIRANDA_BLOCKS.getDefaultState().withProperty(MirandaBlocks.BASIC_TYPE, MirandaBlocks.EnumMirandaBlocks.MIRANDA_GRUNT), true);
    private BlockGen subgrunt = new BlockGen(GSBlocks.MIRANDA_BLOCKS.getDefaultState().withProperty(MirandaBlocks.BASIC_TYPE, MirandaBlocks.EnumMirandaBlocks.MIRANDA_SUBGRUNT), false);
    private BlockGen stone = new BlockGen(GSBlocks.MIRANDA_BLOCKS.getDefaultState().withProperty(MirandaBlocks.BASIC_TYPE, MirandaBlocks.EnumMirandaBlocks.MIRANDA_STONE), false);
    
    private BlockGen grass_2 = new BlockGen(GSBlocks.MIRANDA_BLOCKS.getDefaultState().withProperty(MirandaBlocks.BASIC_TYPE, MirandaBlocks.EnumMirandaBlocks.MIRANDA_GRUNT_2), true);
    private BlockGen subgrunt_2 = new BlockGen(GSBlocks.MIRANDA_BLOCKS.getDefaultState().withProperty(MirandaBlocks.BASIC_TYPE, MirandaBlocks.EnumMirandaBlocks.MIRANDA_SUBGRUNT_2), false);
    private BlockGen stone_2 = new BlockGen(GSBlocks.MIRANDA_BLOCKS.getDefaultState().withProperty(MirandaBlocks.BASIC_TYPE, MirandaBlocks.EnumMirandaBlocks.MIRANDA_STONE_2), false);
    
    private BlockGen grass_3 = new BlockGen(GSBlocks.MIRANDA_BLOCKS.getDefaultState().withProperty(MirandaBlocks.BASIC_TYPE, MirandaBlocks.EnumMirandaBlocks.MIRANDA_GRUNT_3), true);
    private BlockGen subgrunt_3 = new BlockGen(GSBlocks.MIRANDA_BLOCKS.getDefaultState().withProperty(MirandaBlocks.BASIC_TYPE, MirandaBlocks.EnumMirandaBlocks.MIRANDA_SUBGRUNT_3), false);
    private BlockGen stone_3 = new BlockGen(GSBlocks.MIRANDA_BLOCKS.getDefaultState().withProperty(MirandaBlocks.BASIC_TYPE, MirandaBlocks.EnumMirandaBlocks.MIRANDA_STONE_3), false);
    
    private final MapGenCaves caveGenerator = new MapGenCaves(grass, subgrunt, stone, grass_2, subgrunt_2, stone_2, grass_3, subgrunt_3, stone_3);
    private final MapGenIceMiranda iceGenerator = new MapGenIceMiranda();
    
	@Override
	protected List<MapGenBaseMeta> getWorldGenerators()
	{
		List<MapGenBaseMeta> generators = Lists.newArrayList();
		generators.add(this.caveGenerator);
		generators.add(this.iceGenerator);
		return generators;
	}
	
	public ChunkProviderMiranda(World par1World, long seed, boolean mapFeaturesEnabled)	
	{
		super(par1World, seed, mapFeaturesEnabled);
		
		this.setBlocks(new GenBlocks(this.worldObj.provider, getBiomesForGeneration()[0], this.getGrassBlock(), this.getDirtBlock(), this.getStoneBlock()));
		this.setBlocks(new GenBlocks(this.worldObj.provider, getBiomesForGeneration()[1], new BlockMetaPair(GSBlocks.MIRANDA_BLOCKS, (byte) 9), new BlockMetaPair(GSBlocks.MIRANDA_BLOCKS, (byte) 11), new BlockMetaPair(GSBlocks.MIRANDA_BLOCKS, (byte) 13)));
		this.setBlocks(new GenBlocks(this.worldObj.provider, getBiomesForGeneration()[2], new BlockMetaPair(GSBlocks.MIRANDA_BLOCKS, (byte) 10), new BlockMetaPair(GSBlocks.MIRANDA_BLOCKS, (byte) 12), new BlockMetaPair(GSBlocks.MIRANDA_BLOCKS, (byte) 14)));
	
	}
	
	@Override	
	protected BiomeDecoratorSpace getBiomeGenerator() {	
	    return new BiomeDecoratorMiranda();	
	}
	//This should be a custom biome for your mod, but I'm opting to go desert instead out of quickness
	//and the fact that biomes are outside the scope of this tutorial
	
	@Override	
	protected Biome[] getBiomesForGeneration() {
		
	    return new Biome[]{ACBiome.ACSpace, ACBiome.ACSpaceLowPlains, ACBiome.ACSpaceMidHills};	
	}
	
	@Override
	public int getCraterProbability() {	
	    return 300;	
	}
	

		
	@Override
	public double getHeightModifier() {	
	    return 55;	
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
	    return 25;	
	}
	
	@Override	
	public double getValleyHeightModifier() {	
	    return 25;	
	}
	
	@Override
	protected BlockMetaPair getGrassBlock() {
		return new BlockMetaPair(GSBlocks.MIRANDA_BLOCKS, (byte) 0);
	}
	
	@Override
	protected BlockMetaPair getDirtBlock() {
		return new BlockMetaPair(GSBlocks.MIRANDA_BLOCKS, (byte) 1);
	}
	
	@Override
	protected BlockMetaPair getStoneBlock() {
		return new BlockMetaPair(GSBlocks.MIRANDA_BLOCKS, (byte) 2);
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
		return false;
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