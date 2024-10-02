package galaxyspace.systems.BarnardsSystem.planets.barnardaC.dimension;

import java.util.List;

import com.google.common.collect.Lists;

import galaxyspace.core.world.gen.ChunkProviderSpaceLakes;
import galaxyspace.core.world.gen.GSBiomeGenBase;
import galaxyspace.core.world.gen.GS_GenBlocks;
import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRBlocks;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.BiomeDecoratorBarnardaC;
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

public class ChunkProviderBarnardaC extends ChunkProviderSpaceLakes{
	private List<MapGenBaseMeta> worldGenerators;
	private BiomeGenBase[] biomesForGeneration = this.getBiomesForGeneration();
	
	@Override
    protected List<MapGenBaseMeta> getWorldGenerators()
    {
        List<MapGenBaseMeta> generators = Lists.newArrayList();
        //generators.add(this.caveGenerator);
        return generators;
    }
	
	public ChunkProviderBarnardaC(World par1World, long seed, boolean mapFeaturesEnabled) 	
	{
		super(par1World, seed, mapFeaturesEnabled);
		this.setBlocks(new GS_GenBlocks(this.worldObj.provider, getBiomesForGeneration()[0], this.getGrassBlock(), this.getDirtBlock(), this.getStoneBlock()));
		this.setBlocks(new GS_GenBlocks(this.worldObj.provider, getBiomesForGeneration()[1], new BlockMetaPair(Blocks.stone, (byte) 0), new BlockMetaPair(Blocks.stone, (byte) 0), this.getStoneBlock()));
	
	}
	
	@Override
	protected BiomeDecoratorSpace getBiomeGenerator() {
		return new BiomeDecoratorBarnardaC();
	}

	@Override
	protected BiomeGenBase[] getBiomesForGeneration() {
		return new BiomeGenBase[] { GSBiomeGenBase.GSSpace, GSBiomeGenBase.GSSpaceLowPlains, GSBiomeGenBase.GSSpaceDeepOceans, GSBiomeGenBase.GSSpaceOceans };
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
		return new BlockMetaPair(BRBlocks.BarnardaCGrass, (byte) 0);
	}
	
	@Override
	protected BlockMetaPair getDirtBlock() {
		return new BlockMetaPair(BRBlocks.BarnardaCBlocks, (byte) 0);
	}
	
	@Override
	protected BlockMetaPair getStoneBlock() {
		return new BlockMetaPair(Blocks.stone, (byte) 0);
	}
	
	@Override
	protected boolean enableBiomeGenBaseBlock() {
		return true;
	}
	
	@Override
	public void onChunkProvider(int cX, int cZ, Block[] blocks, byte[] metadata) {		
	}
	
	@Override
	public int getWaterLevel() {	
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
