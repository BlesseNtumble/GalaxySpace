package galaxyspace.systems.SolarSystem.planets.ceres.dimension;

 

import java.util.List;

import com.google.common.collect.Lists;

import asmodeuscore.core.astronomy.dimension.world.gen.ChunkProviderSpaceLakes;
import asmodeuscore.core.astronomy.dimension.world.gen.MapGenCaves;
import asmodeuscore.core.astronomy.dimension.world.gen.dungeons.standart.DungeonConfiguration;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.prefab.blocks.DungeonBlocks;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.systems.SolarSystem.planets.ceres.blocks.CeresBlocks;
import galaxyspace.systems.SolarSystem.planets.ceres.world.gen.BiomeDecoratorCeres;
import galaxyspace.systems.SolarSystem.planets.ceres.world.gen.MapGenDungeonCeres;
import galaxyspace.systems.SolarSystem.planets.ceres.world.gen.dungeon.CorridorCeres;
import galaxyspace.systems.SolarSystem.planets.ceres.world.gen.dungeon.RoomBossCeres;
import galaxyspace.systems.SolarSystem.planets.ceres.world.gen.dungeon.RoomChestCeres;
import galaxyspace.systems.SolarSystem.planets.ceres.world.gen.dungeon.RoomEmptyCeres;
import galaxyspace.systems.SolarSystem.planets.ceres.world.gen.dungeon.RoomSpawnerCeres;
import galaxyspace.systems.SolarSystem.planets.ceres.world.gen.dungeon.RoomTreasureCeres;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeAdaptive;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;

public class ChunkProviderCeres extends ChunkProviderSpaceLakes {
	
    private List<MapGenBaseMeta> worldGenerators;
    
    private final MapGenCaves caveGenerator = new MapGenCaves(GSBlocks.CERES_BLOCKS, 0, 1, 1);
    
    private IBlockState top = GSBlocks.CERES_BLOCKS.getDefaultState().withProperty(CeresBlocks.BASIC_TYPE, CeresBlocks.EnumCeresBlocks.CERES_DUNGEON_TOP);
    private IBlockState floor = GSBlocks.CERES_BLOCKS.getDefaultState().withProperty(CeresBlocks.BASIC_TYPE, CeresBlocks.EnumCeresBlocks.CERES_DUNGEON_FLOOR);
    private IBlockState bricks = GSBlocks.DUNGEON_BLOCKS.getDefaultState().withProperty(DungeonBlocks.BASIC_TYPE, DungeonBlocks.EnumDungeonBlocks.CERES_BRICKS);
    private final MapGenDungeonCeres dungeonGenerator = new MapGenDungeonCeres(new DungeonConfiguration(bricks, top, floor, 30, 8, 16, 7, 7, CorridorCeres.class, RoomEmptyCeres.class, RoomSpawnerCeres.class, RoomChestCeres.class, RoomBossCeres.class, RoomTreasureCeres.class));
    
	@Override
	protected List<MapGenBaseMeta> getWorldGenerators()
	{
		List<MapGenBaseMeta> generators = Lists.newArrayList();
		generators.add(this.caveGenerator);
		return generators;
	}
	
	public ChunkProviderCeres(World par1World, long seed, boolean mapFeaturesEnabled)	
	{
		super(par1World, seed, mapFeaturesEnabled);
	}
	
	@Override	
	protected BiomeDecoratorSpace getBiomeGenerator() {	
	    return new BiomeDecoratorCeres();	
	}
	//This should be a custom biome for your mod, but I'm opting to go desert instead out of quickness
	//and the fact that biomes are outside the scope of this tutorial
	
	@Override	
	protected Biome[] getBiomesForGeneration() {	
	    return new Biome[]{BiomeAdaptive.biomeDefault};	
	}
	
	@Override
	public int getCraterProbability() {	
	    return 300;	
	}
	

		
	@Override
	public double getHeightModifier() {	
	    return 20;	
	}
	
	@Override
	public void onChunkProvider(int cX, int cZ, ChunkPrimer primer) {
		if(GSConfigCore.enableDungeonsGeneration) this.dungeonGenerator.generate(this.worldObj, cX, cZ, primer);
	}

	@Override
	public void onPopulate(int cX, int cZ) {
		if(GSConfigCore.enableDungeonsGeneration) this.dungeonGenerator.generateStructure(this.worldObj, this.rand, new ChunkPos(cX, cZ));
	}
	    
	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {
		if(GSConfigCore.enableDungeonsGeneration) this.dungeonGenerator.generate(this.worldObj, x, z, null);
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
	protected BlockMetaPair getGrassBlock() {
		return new BlockMetaPair(GSBlocks.CERES_BLOCKS, (byte) 0);
	}
	
	@Override
	protected BlockMetaPair getDirtBlock() {
		return new BlockMetaPair(GSBlocks.CERES_BLOCKS, (byte) 1);
	}
	
	@Override
	protected BlockMetaPair getStoneBlock() {
		return new BlockMetaPair(GSBlocks.CERES_BLOCKS, (byte) 1);
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