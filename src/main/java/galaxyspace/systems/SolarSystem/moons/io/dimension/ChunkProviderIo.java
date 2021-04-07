package galaxyspace.systems.SolarSystem.moons.io.dimension;

 

import java.util.List;

import com.google.common.collect.Lists;

import asmodeuscore.core.astronomy.dimension.world.gen.ACBiome;
import asmodeuscore.core.astronomy.dimension.world.gen.ChunkProviderSpaceLakes;
import asmodeuscore.core.astronomy.dimension.world.gen.MapGenCaves;
import asmodeuscore.core.astronomy.dimension.world.gen.dungeons.standart.DungeonConfiguration;
import galaxyspace.core.GSBlocks;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.systems.SolarSystem.moons.io.blocks.IoBlocks;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.BiomeDecoratorIo;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.MapGenDungeonIo;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon.CorridorIo;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon.RoomBossIo;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon.RoomChestIo;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon.RoomEmptyIo;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon.RoomSpawnerIo;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon.RoomTreasureIo;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;


 

public class ChunkProviderIo extends ChunkProviderSpaceLakes {
	

    private List<MapGenBaseMeta> worldGenerators;
    
    private final MapGenCaves caveGenerator = new MapGenCaves(GSBlocks.IO_BLOCKS.getStateFromMeta(0), GSBlocks.IO_BLOCKS.getStateFromMeta(1));
    
    private IBlockState top = GSBlocks.IO_BLOCKS.getDefaultState().withProperty(IoBlocks.BASIC_TYPE, IoBlocks.EnumIoBlocks.IO_DUNGEON_TOP);
    private IBlockState floor = GSBlocks.IO_BLOCKS.getDefaultState().withProperty(IoBlocks.BASIC_TYPE, IoBlocks.EnumIoBlocks.IO_DUNGEON_FLOOR);
    private IBlockState bricks = GSBlocks.IO_BLOCKS.getDefaultState().withProperty(IoBlocks.BASIC_TYPE, IoBlocks.EnumIoBlocks.IO_DUNGEON_BRICKS);
    
    private final MapGenDungeonIo dungeonGenerator = new MapGenDungeonIo(new DungeonConfiguration(bricks, top, floor, 30, 30, 35, 7, 7, CorridorIo.class, RoomEmptyIo.class, RoomSpawnerIo.class, RoomChestIo.class, RoomBossIo.class, RoomTreasureIo.class));
    
	@Override
	protected List<MapGenBaseMeta> getWorldGenerators()
	{
		List<MapGenBaseMeta> generators = Lists.newArrayList();
		generators.add(this.caveGenerator);
		
		return generators;
	}
	
	public ChunkProviderIo(World par1World, long seed, boolean mapFeaturesEnabled)	
	{
		super(par1World, seed, mapFeaturesEnabled);
		this.setBlocks(new GenBlocks(this.worldObj.provider, getBiomesForGeneration()[0], this.getGrassBlock(), this.getDirtBlock(), this.getStoneBlock()));
		this.setBlocks(new GenBlocks(this.worldObj.provider, getBiomesForGeneration()[1], new BlockMetaPair(GSBlocks.IO_BLOCKS , (byte) 2), this.getDirtBlock(), this.getStoneBlock()));
		this.setBlocks(new GenBlocks(this.worldObj.provider, getBiomesForGeneration()[2], this.getGrassBlock(), this.getDirtBlock(), this.getStoneBlock()));
		
	}
	
	@Override	
	protected BiomeDecoratorSpace getBiomeGenerator() {	
	    return new BiomeDecoratorIo();	
	}
	//This should be a custom biome for your mod, but I'm opting to go desert instead out of quickness
	//and the fact that biomes are outside the scope of this tutorial
	
	@Override	
	protected Biome[] getBiomesForGeneration() {	
	    return new Biome[]{ACBiome.ACSpace, ACBiome.ACSpaceLowHills, ACBiome.ACSpaceLowPlains};	
	}
	
	@Override
	public int getCraterProbability() {	
	    return 500;	
	}
	

		
	@Override
	public double getHeightModifier() {	
	    return 25;	
	}
	    
	@Override
	public double getMountainHeightModifier() {
	    return 30;
	}
	
	@Override
	public int getWaterLevel() {
	    return 80;
	}
	
	@Override	
	public double getSmallFeatureHeightModifier() {	
	    return 60;	
	}
	
	@Override	
	public double getValleyHeightModifier() {	
	    return 0;	
	}
	
	@Override
	protected BlockMetaPair getGrassBlock() {
		return new BlockMetaPair(GSBlocks.IO_BLOCKS, (byte) 0);
	}
	
	@Override
	protected BlockMetaPair getDirtBlock() {
		return new BlockMetaPair(GSBlocks.IO_BLOCKS, (byte) 1);
	}
	
	@Override
	protected BlockMetaPair getStoneBlock() {
		return new BlockMetaPair(GSBlocks.IO_BLOCKS, (byte) 1);
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
	
}