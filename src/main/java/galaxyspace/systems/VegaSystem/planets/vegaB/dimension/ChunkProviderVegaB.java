package galaxyspace.systems.VegaSystem.planets.vegaB.dimension;

 


import java.util.List;

import com.google.common.collect.Lists;

import galaxyspace.core.world.gen.ChunkProviderSpaceLakes;
import galaxyspace.core.world.gen.GSBiomeGenBase;
import galaxyspace.systems.VegaSystem.core.registers.blocks.VGBlocks;
import galaxyspace.systems.VegaSystem.planets.vegaB.world.gen.BiomeDecoratorVegaBOre;
import galaxyspace.systems.VegaSystem.planets.vegaB.world.gen.MapGenCavesVegaB;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedCreeper;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.chunk.IChunkProvider;


 

public class ChunkProviderVegaB extends ChunkProviderSpaceLakes {
	
	private BiomeGenBase[] biomesForGeneration = this.getBiomesForGeneration();
    private final MapGenCavesVegaB caveGenerator = new MapGenCavesVegaB();

    @Override
    protected List getWorldGenerators()
    {
        List<MapGenBaseMeta> generators = Lists.newArrayList();
        //generators.add(this.caveGenerator);
        return generators;
    }

	public ChunkProviderVegaB(World par1World, long seed, boolean mapFeaturesEnabled) 
	
	{
		super(par1World, seed, mapFeaturesEnabled);
	}

	@Override
	protected BiomeDecoratorSpace getBiomeGenerator() {
		return new BiomeDecoratorVegaBOre();
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
	    return 5;	
	}
	
	
	
	@Override
	protected SpawnListEntry[] getMonsters() {	
	    SpawnListEntry skele = new SpawnListEntry(EntityEvolvedSkeleton.class, 100, 4, 4);
	    SpawnListEntry creeper = new SpawnListEntry(EntityEvolvedCreeper.class, 100, 4, 4);
	    SpawnListEntry spider = new SpawnListEntry(EntityEvolvedSpider.class, 100, 4, 4);	  
	
	    return new SpawnListEntry[]{skele, creeper, spider};	
	}
		
	
	@Override	
	public double getMountainHeightModifier() {	
	    return 105;	
	}
	
	
	@Override	
	public double getSmallFeatureHeightModifier() {	
	    return 50;	
	}
	
	
	@Override	
	public double getValleyHeightModifier() {	
	    return 0;	
	}

	@Override
	public void onPopulate(IChunkProvider arg0, int arg1, int arg2) {

	}

	@Override
	protected SpawnListEntry[] getWaterCreatures() {

		return new SpawnListEntry[] {};
	}

	@Override
	protected BlockMetaPair getGrassBlock() {
		return new BlockMetaPair(VGBlocks.VegaBGrunt, (byte) 0);
	}

	@Override
	protected BlockMetaPair getDirtBlock() {
		return new BlockMetaPair(VGBlocks.VegaBSubGrunt, (byte) 0);
	}

	@Override
	protected BlockMetaPair getStoneBlock() {
		return new BlockMetaPair(VGBlocks.VegaBSubGrunt, (byte) 0);
	}

	@Override
	protected boolean enableBiomeGenBaseBlock() {
		return false;
	}

	@Override
	public void onChunkProvider(int cX, int cZ, Block[] blocks, byte[] metadata) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getWaterLevel() {
		return 70;
	}

	@Override
	public boolean canGenerateWaterBlock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canGenerateIceBlock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected BlockMetaPair getWaterBlock() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenType getGenType() {
		return GenType.GC;
	}

}