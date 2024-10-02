package galaxyspace.systems.SolarSystem.planets.mercury.dimension;

 

import java.util.List;

import com.google.common.collect.Lists;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.world.gen.ChunkProviderSpaceLakes;
import galaxyspace.core.world.gen.GSBiomeGenBase;
import galaxyspace.systems.SolarSystem.planets.mercury.world.gen.BiomeDecoratorMercuryOre;
import galaxyspace.systems.SolarSystem.planets.mercury.world.gen.MapGenCavesMercury;
import galaxyspace.systems.SolarSystem.planets.venus.entities.EntityEvolvedFireBlaze;
import galaxyspace.systems.SolarSystem.planets.venus.entities.EntityEvolvedFireCreeper;
import galaxyspace.systems.SolarSystem.planets.venus.entities.EntityEvolvedFireSkeleton;
import galaxyspace.systems.SolarSystem.planets.venus.entities.EntityEvolvedFireSpider;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.chunk.IChunkProvider;


 

public class ChunkProviderMercury extends ChunkProviderSpaceLakes {
	

    private List<MapGenBaseMeta> worldGenerators;
    
    private BiomeGenBase[] biomesForGeneration = this.getBiomesForGeneration();
    private final MapGenCavesMercury caveGenerator = new MapGenCavesMercury();

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
	    return new BiomeDecoratorMercuryOre();	
	}
	//This should be a custom biome for your mod, but I'm opting to go desert instead out of quickness
	//and the fact that biomes are outside the scope of this tutorial
	
	@Override	
	protected BiomeGenBase[] getBiomesForGeneration() {	
	    return new BiomeGenBase[]{GSBiomeGenBase.GSSpace};	
	}
	
	@Override
	public int getCraterProbability() {	
	    return 300;	
	}
	
	@Override
	protected SpawnListEntry[] getCreatures() {	
	    return new SpawnListEntry[]{};	
	}
		
	@Override
	public double getHeightModifier() {	
	    // TODO Auto-generated method stub	
	    return 20;	
	}
		
	@Override
	protected SpawnListEntry[] getMonsters() {	
	    SpawnListEntry skele = new SpawnListEntry(EntityEvolvedFireSkeleton.class, 100, 4, 4);
	    SpawnListEntry creeper = new SpawnListEntry(EntityEvolvedFireCreeper.class, 100, 4, 4);
	    SpawnListEntry spider = new SpawnListEntry(EntityEvolvedFireSpider.class, 100, 4, 4);
	    SpawnListEntry blaze = new SpawnListEntry(EntityEvolvedFireBlaze.class, 100, 4, 4);
	    
	    return new SpawnListEntry[]{skele, creeper, spider, blaze};
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
	public void onChunkProvider(int cX, int cZ, Block[] blocks, byte[] metadata) {
	}	
	
	@Override	
	public void onPopulate(IChunkProvider arg0, int arg1, int arg2){	
	}
	
	
	@Override	
	public boolean chunkExists(int x, int y){	
	    return false;	
	}
	
	
	@Override
	protected SpawnListEntry[] getWaterCreatures() {	
		return new SpawnListEntry[]{};
	}
	
	@Override
	protected BlockMetaPair getGrassBlock() {
		return new BlockMetaPair(GSBlocks.MercuryBlocks, (byte) 0);
	}
	
	@Override
	protected BlockMetaPair getDirtBlock() {
		return new BlockMetaPair(GSBlocks.MercuryBlocks, (byte) 1);
	}
	
	@Override
	protected BlockMetaPair getStoneBlock() {
		return new BlockMetaPair(GSBlocks.MercuryBlocks, (byte) 2);
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
	protected GenType getGenType() {
		return GenType.GC;
	}
	
}