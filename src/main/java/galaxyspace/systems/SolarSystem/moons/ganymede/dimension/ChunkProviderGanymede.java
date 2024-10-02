package galaxyspace.systems.SolarSystem.moons.ganymede.dimension;

 

import java.util.List;

import com.google.common.collect.Lists;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.world.gen.ChunkProviderSpaceLakes;
import galaxyspace.core.world.gen.GSBiomeGenBase;
import galaxyspace.systems.SolarSystem.moons.ganymede.world.gen.BiomeDecoratorGanymedeOre;
import galaxyspace.systems.SolarSystem.moons.ganymede.world.gen.MapGenCavernGanymede;
import galaxyspace.systems.SolarSystem.moons.ganymede.world.gen.MapGenCavesGanymede;
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


 

public class ChunkProviderGanymede extends ChunkProviderSpaceLakes {
	
    private List<MapGenBaseMeta> worldGenerators;
    
    private BiomeGenBase[] biomesForGeneration = this.getBiomesForGeneration();
    private final MapGenCavesGanymede caveGenerator = new MapGenCavesGanymede();
    private final MapGenCavernGanymede cavernGenerator = new MapGenCavernGanymede();

	@Override
	protected List<MapGenBaseMeta> getWorldGenerators()
	{
	    List<MapGenBaseMeta> generators = Lists.newArrayList();
	    generators.add(this.caveGenerator);
	    //generators.add(this.cavernGenerator);
	    return generators;
	}
	
	public ChunkProviderGanymede(World par1World, long seed, boolean mapFeaturesEnabled) 
	{
		super(par1World, seed, mapFeaturesEnabled);	
		//this.setBlocks(GSBiomeGenBase.GSSpace,	this.getGrassBlock().getBlock(), this.getGrassBlock().getMetadata(), this.getDirtBlock().getBlock(), this.getDirtBlock().getMetadata(), this.getStoneBlock().getBlock(), this.getStoneBlock().getMetadata());
		//this.setBlocks(GSBiomeGenBase.GSSpaceLowPlains,	this.getGrassBlock().getBlock(), this.getGrassBlock().getMetadata(), this.getDirtBlock().getBlock(), this.getDirtBlock().getMetadata(), Blocks.packed_ice, (byte) 0);
	
	}
	
	@Override
	
	protected BiomeDecoratorSpace getBiomeGenerator() {	
	    return new BiomeDecoratorGanymedeOre();	
	}
	
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
	
	
	    return 20;
	
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
	
	    return 5;
	
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
	
	    return 15;
	
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
		return new BlockMetaPair(GSBlocks.GanymedeBlocks, (byte) 0);
	}
	
	@Override
	protected BlockMetaPair getDirtBlock() {
		return new BlockMetaPair(GSBlocks.SurfaceIce, (byte) 0);
	}
	
	@Override
	protected BlockMetaPair getStoneBlock() {
		return new BlockMetaPair(GSBlocks.GanymedeBlocks, (byte) 1);
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
	
	@Override
	public BlockMetaPair getIceBlock()
	{
		return new BlockMetaPair(Blocks.water, (byte) 0);
	}
	
	@Override
	public void getCraterAdditions(Block[] chunkArray, byte[] metaArray, int x, int y, int z)
	{
		if(this.rand.nextInt(13) == 0 && chunkArray[this.getIndex(x, y-1, z)] != Blocks.air)
		{
			chunkArray[this.getIndex(x, y-1, z)] = GSBlocks.SurfaceIce;
			metaArray[this.getIndex(x, y-1, z)] = 0;
		}
	}
}