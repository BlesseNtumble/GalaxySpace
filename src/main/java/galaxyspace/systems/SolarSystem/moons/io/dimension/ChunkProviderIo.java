package galaxyspace.systems.SolarSystem.moons.io.dimension;

 

import java.util.List;

import com.google.common.collect.Lists;

import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.world.gen.ChunkProviderSpaceLakes;
import galaxyspace.core.world.gen.GSBiomeGenBase;
import galaxyspace.core.world.gen.GS_GenBlocks;
import galaxyspace.core.world.gen.dungeon.GSMapGenDungeon;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.BiomeDecoratorIo;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.MapGenCavesIo;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon.RoomBossIo;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon.RoomChestsIo;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon.RoomEmptyIo;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon.RoomSpawnerIo;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon.RoomTreasureIo;
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
import net.minecraftforge.common.util.ForgeDirection;


 

public class ChunkProviderIo extends ChunkProviderSpaceLakes {
	
	
	private final GSMapGenDungeon dungeonGenerator = new GSMapGenDungeon(GSBlocks.DungeonBricks, 1, 8, 16, 3, GSBlocks.IoBlocks, 5, GSBlocks.IoBlocks, 6);
	{
		this.dungeonGenerator.otherRooms.add(new RoomEmptyIo(null, 0, 0, 0, ForgeDirection.UNKNOWN));
		this.dungeonGenerator.otherRooms.add(new RoomSpawnerIo(null, 0, 0, 0, ForgeDirection.UNKNOWN));
		this.dungeonGenerator.otherRooms.add(new RoomSpawnerIo(null, 0, 0, 0, ForgeDirection.UNKNOWN));
		this.dungeonGenerator.otherRooms.add(new RoomSpawnerIo(null, 0, 0, 0, ForgeDirection.UNKNOWN));
		this.dungeonGenerator.otherRooms.add(new RoomSpawnerIo(null, 0, 0, 0, ForgeDirection.UNKNOWN));
		this.dungeonGenerator.otherRooms.add(new RoomSpawnerIo(null, 0, 0, 0, ForgeDirection.UNKNOWN));
		this.dungeonGenerator.otherRooms.add(new RoomSpawnerIo(null, 0, 0, 0, ForgeDirection.UNKNOWN));
		this.dungeonGenerator.otherRooms.add(new RoomSpawnerIo(null, 0, 0, 0, ForgeDirection.UNKNOWN));
		this.dungeonGenerator.otherRooms.add(new RoomSpawnerIo(null, 0, 0, 0, ForgeDirection.UNKNOWN));
		this.dungeonGenerator.otherRooms.add(new RoomChestsIo(null, 0, 0, 0, ForgeDirection.UNKNOWN));
		this.dungeonGenerator.otherRooms.add(new RoomChestsIo(null, 0, 0, 0, ForgeDirection.UNKNOWN));
		this.dungeonGenerator.bossRooms.add(new RoomBossIo(null, 0, 0, 0, ForgeDirection.UNKNOWN));
		this.dungeonGenerator.treasureRooms.add(new RoomTreasureIo(null, 0, 0, 0, ForgeDirection.UNKNOWN));
	}
	 
    private final MapGenCavesIo caveGenerator = new MapGenCavesIo();

	@Override
	protected List getWorldGenerators()
	{
		List<MapGenBaseMeta> generators = Lists.newArrayList();
		//generators.add(this.caveGenerator);
		return generators;
	}
	
	public ChunkProviderIo(World par1World, long seed, boolean mapFeaturesEnabled) 
	
	{
		super(par1World, seed, mapFeaturesEnabled);
    	
		//this.setBlocks(getBiomesForGeneration()[0],	this.getGrassBlock().getBlock(), this.getGrassBlock().getMetadata(), this.getDirtBlock().getBlock(), this.getDirtBlock().getMetadata(), this.getStoneBlock().getBlock(), this.getStoneBlock().getMetadata());
		//this.setBlocks(getBiomesForGeneration()[1], GSBlocks.IoBlocks, (byte) 1, this.getDirtBlock().getBlock(), this.getDirtBlock().getMetadata(), this.getStoneBlock().getBlock(), this.getStoneBlock().getMetadata());
		this.setBlocks(new GS_GenBlocks(this.worldObj.provider, getBiomesForGeneration()[0], this.getGrassBlock(), this.getDirtBlock(), this.getStoneBlock()));
		this.setBlocks(new GS_GenBlocks(this.worldObj.provider, getBiomesForGeneration()[1], new BlockMetaPair(GSBlocks.IoBlocks , (byte) 1), this.getDirtBlock(), this.getStoneBlock()));
		//this.setBlocks(new GS_GenBlocks(this.worldObj.provider, getBiomesForGeneration()[2], new BlockMetaPair(GSBlocks.IoBlocks , (byte) 5), this.getDirtBlock(), this.getStoneBlock()));
		
	}
	
	@Override
	
	protected BiomeDecoratorSpace getBiomeGenerator() {
	    return new BiomeDecoratorIo();	
	}
	
	
	
	 //This should be a custom biome for your mod, but I'm opting to go desert instead out of quickness
	
	//and the fact that biomes are outside the scope of this tutorial
	
	@Override
	
	protected BiomeGenBase[] getBiomesForGeneration() {	
	    return new BiomeGenBase[]{GSBiomeGenBase.GSSpace, GSBiomeGenBase.GSSpaceLowHills/* GSBiomeGenBase.GSSpaceLowPlains*/};	
	}
	
	
	
	
	@Override
	protected SpawnListEntry[] getCreatures() {
	    return new SpawnListEntry[]{};	
	}
	
	
	@Override
	public double getHeightModifier() {
	    return 25;	
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
	public void onChunkProvider(int cX, int cZ, Block[] blocks, byte[] metadata) 
	{		
		if(GSConfigCore.enableDungeonsGeneration) 
			this.dungeonGenerator.generateUsingArrays(this.worldObj, this.worldObj.getSeed(), cX * 16, 30, cZ * 16, cX, cZ, blocks, metadata);
	}
	
	@Override	
	public void onPopulate(IChunkProvider arg0, int arg1, int arg2)
	{
		if(GSConfigCore.enableDungeonsGeneration) 
			this.dungeonGenerator.handleTileEntities(this.rand);	
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
	public double getSmallFeatureHeightModifier() {
		return 60;
	}
	
	@Override
	public double getMountainHeightModifier() {
		return 30;
	}
	
	@Override
	public double getValleyHeightModifier() {
		return 0;
	}
	
	@Override
	public int getCraterProbability() {
		return 300;
	}
	
	@Override
	protected BlockMetaPair getGrassBlock() {
		return new BlockMetaPair(GSBlocks.IoBlocks, (byte) 0);
	}
	
	@Override
	protected BlockMetaPair getDirtBlock() {
		return new BlockMetaPair(GSBlocks.IoBlocks, (byte) 2);
	}
	
	@Override
	protected BlockMetaPair getStoneBlock() {
		return new BlockMetaPair(GSBlocks.IoBlocks, (byte) 2);
	}
	
	@Override
	protected boolean enableBiomeGenBaseBlock() {
		return true;
	}

	@Override
	public int getWaterLevel() {
		return 80;
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