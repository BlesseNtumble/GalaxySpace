package galaxyspace.systems.TCetiSystem.planets.tcetiF.dimension;

 

import java.util.List;

import com.google.common.collect.Lists;

import galaxyspace.core.world.gen.ChunkProviderSpaceLakes;
import galaxyspace.core.world.gen.GSBiomeGenBase;
import galaxyspace.systems.TCetiSystem.core.registers.blocks.TCBlocks;
import galaxyspace.systems.TCetiSystem.planets.tcetiF.world.gen.BiomeDecoratorTCetiFOre;
import galaxyspace.systems.TCetiSystem.planets.tcetiF.world.gen.MapGenCavesTCetiF;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedCreeper;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.chunk.IChunkProvider;


 

public class ChunkProviderTCetiF extends ChunkProviderSpaceLakes {
	
	
    private List<MapGenBaseMeta> worldGenerators;
    
    private BiomeGenBase[] biomesForGeneration = this.getBiomesForGeneration();
    private final MapGenCavesTCetiF caveGenerator = new MapGenCavesTCetiF();

    @Override
    protected List<MapGenBaseMeta> getWorldGenerators()
    {
        List<MapGenBaseMeta> generators = Lists.newArrayList();
        generators.add(this.caveGenerator);
        return generators;
    }

	public ChunkProviderTCetiF(World par1World, long seed, boolean mapFeaturesEnabled) 
	
	{
		super(par1World, seed, mapFeaturesEnabled);
	}
	
	@Override
	
	protected BiomeDecoratorSpace getBiomeGenerator() {
	
	
	    return new BiomeDecoratorTCetiFOre();
	
	}
	
	
	
	 //This should be a custom biome for your mod, but I'm opting to go desert instead out of quickness
	
	//and the fact that biomes are outside the scope of this tutorial
	
	@Override
	
	protected BiomeGenBase[] getBiomesForGeneration() {
	
	
	    return new BiomeGenBase[]{GSBiomeGenBase.GSSpace};
	
	}
	
	
	@Override
	protected SpawnListEntry[] getCreatures() {
	
	 
	
	    return new SpawnListEntry[]{};
	
	}
	
	
	@Override
	public double getHeightModifier() {
	
	    return 40;
	
	}
	
	
	
	@Override
	
	protected SpawnListEntry[] getMonsters() {
	
	    SpawnListEntry skele = new SpawnListEntry(EntityEvolvedSkeleton.class, 100, 4, 4);
	    SpawnListEntry creeper = new SpawnListEntry(EntityEvolvedCreeper.class, 100, 4, 4);
	    SpawnListEntry spider = new SpawnListEntry(EntityEvolvedSpider.class, 100, 4, 4);
	    //SpawnListEntry enderman = new SpawnListEntry(EntityEvolvedEnderman.class, 100, 4, 4);
	    
	    return new SpawnListEntry[]{skele, creeper, spider};
	
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
		return new BlockMetaPair(TCBlocks.TCetiEBlocks, (byte) 0);
	}
	
	@Override
	protected BlockMetaPair getDirtBlock() {
		return new BlockMetaPair(TCBlocks.TCetiEBlocks, (byte) 1);
	}
	
	@Override
	protected BlockMetaPair getStoneBlock() {
		return new BlockMetaPair(TCBlocks.TCetiEBlocks, (byte) 2);
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
	
		return 110;
	}
	
	@Override
	public int getTerrainLevel() {
		
		return 64;
	}
	
	@Override
	public boolean canGenerateWaterBlock() {
		return true;
	}
	
	@Override
	protected BlockMetaPair getWaterBlock() {
		return new BlockMetaPair(Blocks.water, (byte) 0);
	}
	
	@Override
	public boolean canGenerateIceBlock() {
		return false;
	}
	
	@Override
	public double getSmallFeatureHeightModifier() {
		return 15;
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
		return GenType.GC;
	}

}