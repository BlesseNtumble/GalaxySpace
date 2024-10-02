package galaxyspace.systems.SolarSystem.moons.triton.dimension;
 

import java.util.List;

import com.google.common.collect.Lists;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.world.gen.ChunkProviderSpaceLakes;
import galaxyspace.core.world.gen.GSBiomeGenBase;
import galaxyspace.systems.SolarSystem.moons.europa.entities.EntityEvolvedColdBlaze;
import galaxyspace.systems.SolarSystem.moons.triton.world.gen.BiomeDecoratorTriton;
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


 

public class ChunkProviderTriton extends ChunkProviderSpaceLakes {
	

    private List<MapGenBaseMeta> worldGenerators;
    
    private BiomeGenBase[] biomesForGeneration = this.getBiomesForGeneration();
    //private final MapGenCavesMiranda caveGenerator = new MapGenCavesMiranda();

    @Override
    protected List<MapGenBaseMeta> getWorldGenerators()
    {
        List<MapGenBaseMeta> generators = Lists.newArrayList();
        //generators.add(this.caveGenerator);
        return generators;
    }

	public ChunkProviderTriton(World par1World, long seed, boolean mapFeaturesEnabled)

	{
		super(par1World, seed, mapFeaturesEnabled);

	}

	@Override

	protected BiomeDecoratorSpace getBiomeGenerator() {

		return new BiomeDecoratorTriton();

	}

	@Override
	protected BiomeGenBase[] getBiomesForGeneration() {
		return new BiomeGenBase[] { GSBiomeGenBase.GSSpace };
	}

	@Override
	public int getCraterProbability() {

		return 900;

	}

	@Override
	protected SpawnListEntry[] getCreatures() {

		return new SpawnListEntry[] {};

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
		SpawnListEntry blaze = new SpawnListEntry(EntityEvolvedColdBlaze.class, 100, 4, 4);

		return new SpawnListEntry[] { skele, creeper, spider, blaze };

	}

	@Override

	public double getMountainHeightModifier() {

		return 10;

	}

	@Override
	public int getWaterLevel() {
		return 70;
	}

	@Override

	public double getSmallFeatureHeightModifier() {

		return 15;

	}

	@Override

	public double getValleyHeightModifier() {

		return 10;

	}

	@Override
	public void onChunkProvider(int cX, int cZ, Block[] blocks, byte[] metadata) {

	}

	@Override	
	public void onPopulate(IChunkProvider arg0, int chunkX, int chunkZ)
	{		
		int k1;
        int l1;
        int i2;
        
        int k = chunkX * 16;
        int l = chunkZ * 16;

		for (k1 = 0; k1 < 16; ++k1) {
			for (l1 = 0; l1 < 16; ++l1) {
				i2 = this.worldObj.getTopSolidOrLiquidBlock(k + k1, l + l1);

				if(i2 > this.getWaterLevel() - 6 && this.worldObj.rand.nextInt(10) == 0)
				{
					if(this.worldObj.getBlock(k1 + k, i2 - 1, l1 + l) == GSBlocks.TritonBlocks && this.worldObj.getBlockMetadata(k1 + k, i2 - 1, l1 + l) == 0)
					{
						this.worldObj.setBlock(k1 + k, i2 - 1, l1 + l, GSBlocks.TritonBlocks, 2, 3);
					}
				}
				
				else if(i2 < this.getWaterLevel() - 4 )
				{
					if(this.worldObj.getBlock(k1 + k, i2 - 1, l1 + l) == GSBlocks.TritonBlocks && this.worldObj.getBlockMetadata(k1 + k, i2 - 1, l1 + l) == 0)
					{
						this.worldObj.setBlock(k1 + k, i2 - 1, l1 + l, GSBlocks.TritonBlocks, 1, 3);
					}
				}
			}
		}	
	}

	@Override
	public boolean chunkExists(int x, int y) {

		return false;

	}

	@Override
	protected SpawnListEntry[] getWaterCreatures() {

		return new SpawnListEntry[] {};
	}

	@Override
	protected BlockMetaPair getGrassBlock() {
		return new BlockMetaPair(GSBlocks.TritonBlocks, (byte) 0);
	}

	@Override
	protected BlockMetaPair getDirtBlock() {
		return new BlockMetaPair(GSBlocks.TritonBlocks, (byte) 2);
	}

	@Override
	protected BlockMetaPair getStoneBlock() {
		return new BlockMetaPair(GSBlocks.TritonBlocks, (byte) 3);
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

}