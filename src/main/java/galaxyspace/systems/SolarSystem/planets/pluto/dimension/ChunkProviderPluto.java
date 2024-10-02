package galaxyspace.systems.SolarSystem.planets.pluto.dimension;

 

import java.util.List;

import com.google.common.collect.Lists;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.world.gen.ChunkProviderSpaceLakes;
import galaxyspace.core.world.gen.GSBiomeGenBase;
import galaxyspace.core.world.gen.GS_GenBlocks;
import galaxyspace.systems.SolarSystem.planets.pluto.world.gen.BiomeDecoratorPluto;
import galaxyspace.systems.SolarSystem.planets.pluto.world.gen.MapGenCavesPluto;
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


 

public class ChunkProviderPluto extends ChunkProviderSpaceLakes {
	
	/*
	private final MapGenDungeon dungeonGenerator = new MapGenDungeon(GSBlocks.PlutoBlocks, 6, 8, 16, 3);
	 
	 {
      this.dungeonGenerator.otherRooms.add(new RoomEmptyPluto(null, 0, 0, 0, ForgeDirection.UNKNOWN));
      this.dungeonGenerator.otherRooms.add(new RoomSpawnerPluto(null, 0, 0, 0, ForgeDirection.UNKNOWN));
      this.dungeonGenerator.otherRooms.add(new RoomSpawnerPluto(null, 0, 0, 0, ForgeDirection.UNKNOWN));
      this.dungeonGenerator.otherRooms.add(new RoomSpawnerPluto(null, 0, 0, 0, ForgeDirection.UNKNOWN));
      this.dungeonGenerator.otherRooms.add(new RoomSpawnerPluto(null, 0, 0, 0, ForgeDirection.UNKNOWN));
      this.dungeonGenerator.otherRooms.add(new RoomSpawnerPluto(null, 0, 0, 0, ForgeDirection.UNKNOWN));
      this.dungeonGenerator.otherRooms.add(new RoomSpawnerPluto(null, 0, 0, 0, ForgeDirection.UNKNOWN));
      this.dungeonGenerator.otherRooms.add(new RoomSpawnerPluto(null, 0, 0, 0, ForgeDirection.UNKNOWN));
      this.dungeonGenerator.otherRooms.add(new RoomSpawnerPluto(null, 0, 0, 0, ForgeDirection.UNKNOWN));
      this.dungeonGenerator.otherRooms.add(new RoomChestsPluto(null, 0, 0, 0, ForgeDirection.UNKNOWN));
      this.dungeonGenerator.otherRooms.add(new RoomChestsPluto(null, 0, 0, 0, ForgeDirection.UNKNOWN));
      this.dungeonGenerator.bossRooms.add(new RoomBossPluto(null, 0, 0, 0, ForgeDirection.UNKNOWN));
      this.dungeonGenerator.treasureRooms.add(new RoomTreasurePluto(null, 0, 0, 0, ForgeDirection.UNKNOWN));
		
	 }*/

    private List<MapGenBaseMeta> worldGenerators;
    
    private BiomeGenBase[] biomesForGeneration = this.getBiomesForGeneration();
    private final MapGenCavesPluto caveGenerator = new MapGenCavesPluto();

    @Override
    protected List<MapGenBaseMeta> getWorldGenerators()
    {
        List<MapGenBaseMeta> generators = Lists.newArrayList();
        generators.add(this.caveGenerator);
        return generators;
    }

	public ChunkProviderPluto(World par1World, long seed, boolean mapFeaturesEnabled) 
	
	{
		super(par1World, seed, mapFeaturesEnabled);
		
		//this.setBlocks(getBiomesForGeneration()[0], GSBlocks.PlutoBlocks, (byte) 0, this.getDirtBlock().getBlock(), this.getDirtBlock().getMetadata(), this.getStoneBlock().getBlock(), this.getStoneBlock().getMetadata());
		//this.setBlocks(getBiomesForGeneration()[1], GSBlocks.PlutoBlocks, (byte) 1, this.getDirtBlock().getBlock(), this.getDirtBlock().getMetadata(), this.getStoneBlock().getBlock(), this.getStoneBlock().getMetadata());
		//this.setBlocks(getBiomesForGeneration()[2], GSBlocks.PlutoBlocks, (byte) 2, this.getDirtBlock().getBlock(), this.getDirtBlock().getMetadata(), this.getStoneBlock().getBlock(), this.getStoneBlock().getMetadata());
		//this.setBlocks(getBiomesForGeneration()[3], GSBlocks.PlutoBlocks, (byte) 3, this.getDirtBlock().getBlock(), this.getDirtBlock().getMetadata(), this.getStoneBlock().getBlock(), this.getStoneBlock().getMetadata());
		
		this.setBlocks(new GS_GenBlocks(this.worldObj.provider, getBiomesForGeneration()[0], new BlockMetaPair(GSBlocks.PlutoBlocks, (byte) 0), this.getDirtBlock(), this.getStoneBlock()));
		this.setBlocks(new GS_GenBlocks(this.worldObj.provider, getBiomesForGeneration()[1], new BlockMetaPair(GSBlocks.PlutoBlocks, (byte) 1), this.getDirtBlock(), this.getStoneBlock()));
		this.setBlocks(new GS_GenBlocks(this.worldObj.provider, getBiomesForGeneration()[2], new BlockMetaPair(GSBlocks.PlutoBlocks, (byte) 2), this.getDirtBlock(), this.getStoneBlock()));
		this.setBlocks(new GS_GenBlocks(this.worldObj.provider, getBiomesForGeneration()[3], new BlockMetaPair(GSBlocks.PlutoBlocks, (byte) 3), this.getDirtBlock(), this.getStoneBlock()));
	}
	
	@Override
	
	protected BiomeDecoratorSpace getBiomeGenerator() {
	
	
	    return new BiomeDecoratorPluto();
	
	}
	
	
	
	 //This should be a custom biome for your mod, but I'm opting to go desert instead out of quickness
	
	//and the fact that biomes are outside the scope of this tutorial
	
	@Override
	
	protected BiomeGenBase[] getBiomesForGeneration() {
	
	
	    return new BiomeGenBase[]{GSBiomeGenBase.GSSpace, GSBiomeGenBase.GSSpaceLowPlains, GSBiomeGenBase.GSSpaceLowHills, GSBiomeGenBase.GSSpaceMidPlains};
	
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
	    //SpawnListEntry enderman = new SpawnListEntry(EntityEvolvedEnderman.class, 100, 4, 4);
	    //SpawnListEntry blaze = new SpawnListEntry(EntityEvolvedColdBlaze.class, 100, 4, 4);
	    
	    return new SpawnListEntry[]{skele, creeper, spider};
	
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
	
		//if(GSConfigCore.enableDungeonsGeneration) this.dungeonGenerator.generateUsingArrays(this.worldObj, this.worldObj.getSeed(), cX * 16, 30, cZ * 16, cX, cZ, blocks, metadata);
	}	
	
	@Override	
	public void onPopulate(IChunkProvider arg0, int arg1, int arg2){		
		//if(GSConfigCore.enableDungeonsGeneration) this.dungeonGenerator.handleTileEntities(this.rand);
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
		return new BlockMetaPair(GSBlocks.PlutoBlocks, (byte) 0);
	}
	
	@Override
	protected BlockMetaPair getDirtBlock() {
		return new BlockMetaPair(GSBlocks.PlutoBlocks, (byte) 4);
	}
	
	@Override
	protected BlockMetaPair getStoneBlock() {
		return new BlockMetaPair(GSBlocks.PlutoBlocks, (byte) 5);
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