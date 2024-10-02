package galaxyspace.systems.SolarSystem.planets.venus.dimension;

 

import java.util.List;

import com.google.common.collect.Lists;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.world.gen.ChunkProviderSpaceLakes;
import galaxyspace.core.world.gen.GSBiomeGenBase;
import galaxyspace.systems.SolarSystem.planets.venus.entities.EntityEvolvedFireBlaze;
import galaxyspace.systems.SolarSystem.planets.venus.entities.EntityEvolvedFireCreeper;
import galaxyspace.systems.SolarSystem.planets.venus.entities.EntityEvolvedFireSkeleton;
import galaxyspace.systems.SolarSystem.planets.venus.entities.EntityEvolvedFireSpider;
import galaxyspace.systems.SolarSystem.planets.venus.world.gen.BiomeDecoratorVenus;
import galaxyspace.systems.SolarSystem.planets.venus.world.gen.MapGenCavesVenus;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.chunk.IChunkProvider;


 

public class ChunkProviderVenus extends ChunkProviderSpaceLakes {
	
    private List<MapGenBaseMeta> worldGenerators;
    
    private BiomeGenBase[] biomesForGeneration = this.getBiomesForGeneration();
    private final MapGenCavesVenus caveGenerator = new MapGenCavesVenus();

    @Override
    protected List<MapGenBaseMeta> getWorldGenerators() {
        List<MapGenBaseMeta> generators = Lists.newArrayList();
        generators.add(this.caveGenerator);
        return generators;
    }

	public ChunkProviderVenus(World par1World, long seed, boolean mapFeaturesEnabled) {
		super(par1World, seed, mapFeaturesEnabled);
	}
	
	@Override
	
	protected BiomeDecoratorSpace getBiomeGenerator() {
	    return new BiomeDecoratorVenus();	
	}
	
	//This should be a custom biome for your mod, but I'm opting to go desert instead out of quickness
	
	//and the fact that biomes are outside the scope of this tutorial
	
	@Override	
	protected BiomeGenBase[] getBiomesForGeneration() {	
	    return new BiomeGenBase[]
	    		{
	    			GSBiomeGenBase.GSSpaceLowHills,
	    			GSBiomeGenBase.GSSpaceLowPlains,
	    			GSBiomeGenBase.GSSpaceShallowWaters
	    		};	
	}
	
	@Override
	protected SpawnListEntry[] getCreatures() {
	    return new SpawnListEntry[]{};	
	}
		
	@Override
	public double getHeightModifier() {	
		return 15;	
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
		return new BlockMetaPair(GSBlocks.VenusBlocks, (byte) 0);
	}
	
	@Override
	protected BlockMetaPair getDirtBlock() {
		return new BlockMetaPair(GSBlocks.VenusBlocks, (byte) 1);
	}
	
	@Override
	protected BlockMetaPair getStoneBlock() {
		return new BlockMetaPair(GSBlocks.VenusBlocks, (byte) 1);
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
		return 65;
	}
	
	@Override
	public boolean canGenerateWaterBlock() {
		return true;
	}
	
	@Override
	public boolean canGenerateIceBlock() {
		return false;
	}
	
	@Override
	protected BlockMetaPair getWaterBlock() {
		return new BlockMetaPair(Blocks.lava, (byte)0);
	}

	@Override
	public double getSmallFeatureHeightModifier() {
		return 0;
	}

	@Override
	public double getMountainHeightModifier() {
		return 0;
	}

	@Override
	public double getValleyHeightModifier() {
		return 0;
	}

	@Override
	public int getCraterProbability() {
		return 0;
	}

	@Override
	protected GenType getGenType() {
		return GenType.VANILLA;
	}

}