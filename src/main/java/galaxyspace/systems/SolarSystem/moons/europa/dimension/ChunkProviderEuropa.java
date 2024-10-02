package galaxyspace.systems.SolarSystem.moons.europa.dimension;

 

import java.util.List;

import com.google.common.collect.Lists;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.world.gen.ChunkProviderSpaceLakes;
import galaxyspace.core.world.gen.GSBiomeGenBase;
import galaxyspace.core.world.gen.GS_GenBlocks;
import galaxyspace.systems.SolarSystem.moons.europa.world.gen.BiomeDecoratorEuropaOre;
import galaxyspace.systems.SolarSystem.moons.europa.world.gen.MapGenRavineEuropa;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedCreeper;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import micdoodle8.mods.galacticraft.core.perlin.NoiseModule;
import micdoodle8.mods.galacticraft.core.perlin.generator.Gradient;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.chunk.IChunkProvider;


 

public class ChunkProviderEuropa extends ChunkProviderSpaceLakes {
	
	private final NoiseModule noiseGen4;

    // DO NOT CHANGE
    private static final int CHUNK_SIZE_X = 16;
    private static final int CHUNK_SIZE_Y = 128;
    private static final int CHUNK_SIZE_Z = 16;
    private List<MapGenBaseMeta> worldGenerators;
    
    private BiomeGenBase[] biomesForGeneration = this.getBiomesForGeneration();
    private final MapGenRavineEuropa ravineGenerator = new MapGenRavineEuropa();

	@Override
	protected List getWorldGenerators()
	{
		List<MapGenBaseMeta> generators = Lists.newArrayList();
	    //generators.add(this.caveGenerator);
		generators.add(this.ravineGenerator);
	    return generators;
	}
	
	public ChunkProviderEuropa(World par1World, long seed, boolean mapFeaturesEnabled) 	
	{
		super(par1World, seed, mapFeaturesEnabled);
		this.noiseGen4 = new Gradient(this.rand.nextLong(), 1, 0.25F);
    	
		//this.setBlocks(getBiomesForGeneration()[0],	this.getGrassBlock().getBlock(), (byte) 0, this.getDirtBlock().getBlock(), (byte) 0, Blocks.packed_ice, (byte) 0);
		//this.setBlocks(getBiomesForGeneration()[1],	this.getGrassBlock().getBlock(), (byte) 0, this.getDirtBlock().getBlock(), (byte) 0, Blocks.water, (byte) 0);
		this.setBlocks(new GS_GenBlocks(this.worldObj.provider, getBiomesForGeneration()[0], this.getGrassBlock(), this.getDirtBlock(), this.getStoneBlock()));
	}
	
	@Override
	protected BiomeDecoratorSpace getBiomeGenerator() {
	
	
	    return new BiomeDecoratorEuropaOre();
	
	}
	
	
	
	 //This should be a custom biome for your mod, but I'm opting to go desert instead out of quickness
	
	//and the fact that biomes are outside the scope of this tutorial
	
	@Override
	
	protected BiomeGenBase[] getBiomesForGeneration() {
	
	
	    return new BiomeGenBase[]{GSBiomeGenBase.GSSpace};
	
	}
	
	
	
	@Override
	public int getCraterProbability() {
	
	    return 30;
	
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
	
	    SpawnListEntry skele = new SpawnListEntry(EntityEvolvedSkeleton.class, 100, 4, 4);
	    SpawnListEntry creeper = new SpawnListEntry(EntityEvolvedCreeper.class, 100, 4, 4);
	    SpawnListEntry spider = new SpawnListEntry(EntityEvolvedSpider.class, 100, 4, 4);
	    
	    return new SpawnListEntry[]{skele, creeper, spider};
	
	}
	
	
	
	@Override
	
	public double getMountainHeightModifier() {
	
	    return 35;
	
	}
	
	
	
	@Override
	
	public double getSmallFeatureHeightModifier() {
	
	    return 50;
	
	}
	
	
	
	@Override
	
	public double getValleyHeightModifier() {
	
	    return 5;
	
	}
	
	
	@Override
	public void onChunkProvider(int cX, int cZ, Block[] blocks, byte[] metadata) {	    
	}
	
	
	
	@Override	
	public void onPopulate(IChunkProvider arg0, int arg1, int arg2)
	{		
		int k1;
        int l1;
        int i2;
        
        int k = arg1 * 16;
        int l = arg2 * 16;

		for (k1 = 0; k1 < 16; ++k1) {
			for (l1 = 0; l1 < 16; ++l1) {
				i2 = this.worldObj.getTopSolidOrLiquidBlock(k + k1, l + l1);

				if(i2 > this.getWaterLevel() + 2 || i2 >= this.getWaterLevel() - 5 && i2 < this.getWaterLevel() + 3)
				{
					if (this.worldObj.getBlock(k1 + k, i2 - 1, l1 + l) == GSBlocks.EuropaBlocks && this.worldObj.getBlockMetadata(k1 + k, i2 - 1, l1 + l) == 0) {
						this.worldObj.setBlock(k1 + k, i2, l1 + l, Blocks.snow, 0, 2);
						this.worldObj.setBlock(k1 + k, i2 + 1, l1 + l, Blocks.snow_layer, 0, 2);
					}
				} else if(i2 < this.getWaterLevel() - 6) {						
					
					if (this.worldObj.getBlock(k1 + k, i2 - 1, l1 + l) == GSBlocks.EuropaBlocks && this.worldObj.getBlockMetadata(k1 + k, i2 - 1, l1 + l) == 0) {
						this.worldObj.setBlock(k1 + k, i2, l1 + l, Blocks.packed_ice, 0, 2);
					}
				}			
			}
		}	
	}
	
	@Override
	protected SpawnListEntry[] getWaterCreatures() {
	
		return new SpawnListEntry[]{};
	}
	
	@Override
	protected BlockMetaPair getGrassBlock() {
		return new BlockMetaPair(GSBlocks.EuropaBlocks, (byte) 0);
	}
	
	@Override
	protected BlockMetaPair getDirtBlock() {
		return new BlockMetaPair(Blocks.packed_ice, (byte) 0);
	}
	
	@Override
	protected BlockMetaPair getStoneBlock() {
		return new BlockMetaPair(Blocks.water, (byte) 0);
	}
	
	@Override
	protected boolean enableBiomeGenBaseBlock() {
		return true;
	}
	
	@Override
	public int getWaterLevel() {
		return 95;
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
		return new BlockMetaPair(Blocks.ice, (byte) 1);
	}
	
	@Override
	protected GenType getGenType() {
		return GenType.GC;
	}
	
	@Override
	public double getDirtLayerSize()
    {
    	return 40.0D;
    }

	@Override
	protected BlockMetaPair getIceBlock() {	
		return new BlockMetaPair(GSBlocks.EuropaBlocks, (byte) 1);
	}
}