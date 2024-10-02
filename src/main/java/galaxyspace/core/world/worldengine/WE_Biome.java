//- By Vamig Aliev.
//- https://vk.com/win_vista.

package galaxyspace.core.world.worldengine;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.IWorldGenerator;
import galaxyspace.core.world.worldengine.additions.WE_CreateChunkGen_InXYZ;
import galaxyspace.core.world.worldengine.additions.WE_CreateChunkGen_InXZ;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_GrassGen;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_LakeGen;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_SnowGen;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_WorldTreeGen;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;


public class WE_Biome extends BiomeGenBase {
	public int id;
	//////////////////
	//- Biome Info -//
	//////////////////
	public double
		biomeMinValueOnMap = -1.0D,
		biomeMaxValueOnMap =  1.0D,
		biomePersistence = 1.0D,
		biomeScaleX = 1.0D, biomeScaleY = 1.0D;
	public int biomeNumberOfOctaves = 1,
		biomeSurfaceHeight = 63,
		biomeInterpolateQuality = 16,		
		biomeGrassColor = 0xFFFFFF;
	
	//////////////////
	//- Generators -//
	//////////////////
	public List<WE_CreateChunkGen_InXZ > createChunkGen_InXZ_List  = new ArrayList();
	public List<WE_CreateChunkGen_InXYZ> createChunkGen_InXYZ_List = new ArrayList();
	public List<IWorldGenerator        > decorateChunkGen_List     = new ArrayList();
		
	/////
	//=//
	/////
	private static WE_ChunkProvider chunk_provider;
	
	public WE_Biome(int ID_FOR_ALL_WE_BIOMES, boolean r) {
		super(ID_FOR_ALL_WE_BIOMES, r);
		setBiomeName("-=|WorldEngine|=-");
		
		spawnableCaveCreatureList .clear();
		spawnableCreatureList     .clear();
		spawnableMonsterList      .clear();
		spawnableWaterCreatureList.clear();
				
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(Blocks.dirt   , (byte)0, Blocks.stone, (byte)0, -256, 0,   -4, -2,  true);
		standardBiomeLayers.add(Blocks.grass  , (byte)0, Blocks.dirt , (byte)0, -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.bedrock, (byte)0,                           0, 2,    0,  0,  true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
		//-//
		createChunkGen_InXZ_List.add(new WE_SnowGen());
		
		decorateChunkGen_List.add(new WE_LakeGen());
		//-//
		WE_WorldTreeGen treeGen = new WE_WorldTreeGen();
		treeGen.add(Blocks.log, 0, (Block)Blocks.leaves, 0, Blocks.sapling, Blocks.vine, Blocks.cocoa, 8, 1, 8, 4, false,
			(byte)2, (byte)0, (byte)0, (byte)1, (byte)2, (byte)1, 1, 12, 4, 0.618D, 0.381D, 1.0D, 1.0D);
		decorateChunkGen_List.add(treeGen);
		//-//
		WE_GrassGen grassGen = new WE_GrassGen();
		grassGen.add(Blocks.tallgrass    , (byte)1,   8, false, Blocks.grass, (byte)0);
		grassGen.add(Blocks.tallgrass    , (byte)2,  16, false, Blocks.grass, (byte)0);
		grassGen.add(Blocks.red_flower   , (byte)0, 128, false, Blocks.grass, (byte)0);
		grassGen.add(Blocks.yellow_flower, (byte)0, 128, false, Blocks.grass, (byte)0);
		grassGen.add(Blocks.deadbush     , (byte)0, 512, false, Blocks.grass, (byte)0);
		grassGen.add(Blocks.waterlily    , (byte)0, 256, false, Blocks.water, (byte)0);
		decorateChunkGen_List.add(grassGen);
	}
	
	public WE_Biome(int ID_FOR_ALL_WE_BIOMES) {
		this(ID_FOR_ALL_WE_BIOMES, false);
	}
	
	public WE_Biome(int ID_FOR_ALL_WE_BIOMES,
		double minMapValue, double maxMapValue,
		double persistence, int numOctaves, double sx, double sy,
		int height, int interpolateQuality) {
		this(ID_FOR_ALL_WE_BIOMES);
		//-//
		biomeMinValueOnMap = minMapValue;
		biomeMaxValueOnMap = maxMapValue;
		//-//
		biomePersistence        =        persistence;
		biomeNumberOfOctaves    =         numOctaves;
		biomeScaleX             =                 sx;
		biomeScaleY             =                 sy;
		//-//
		biomeSurfaceHeight      =             height;
		biomeInterpolateQuality = interpolateQuality;
	}
	
	public static void setBiomeMap(WE_ChunkProvider cp, double persistence, int numOctaves, double sx, double sy) {
		cp.biomemapPersistence     = persistence;
		cp.biomemapNumberOfOctaves =  numOctaves;
		cp.biomemapScaleX          =          sx;
		cp.biomemapScaleY          =          sy;
	}
	
	public static int addBiomeToGeneration(WE_ChunkProvider cp, WE_Biome biome) {
		if(biome.biomeMaxValueOnMap < biome.biomeMinValueOnMap) {
			double                 t = biome.biomeMaxValueOnMap;
			biome.biomeMaxValueOnMap = biome.biomeMinValueOnMap;
			biome.biomeMinValueOnMap =                        t;
		}
		//-//
		cp.biomesList.add(biome);
		biome.id = cp.biomesList.size();
		//-//
		if(cp.standardBiomeOnMap == null)
			cp.standardBiomeOnMap = biome;
		//-//
		return biome.id;
	}
	
	public static void setChunkProvider(WE_ChunkProvider prov)
	{
		chunk_provider = prov;
	}
	
	public static WE_Biome getBiomeAt(long x, long z) {
		return getBiomeAt(chunk_provider, x, z);
	}
	
	public static WE_Biome getBiomeAt(WE_ChunkProvider cp, long x, long z) {
		double biomeMapData = WE_PerlinNoise.PerlinNoise2D((cp.worldObj.getSeed() * 11) ^ 6,
			x / cp.biomemapScaleX, z / cp.biomemapScaleX,
			cp.biomemapPersistence, cp.biomemapNumberOfOctaves)
			* cp.biomemapScaleY;
		//-//
		WE_Biome r = null;
		for(int i = 0; i < cp.biomesList.size(); i++)
			if(biomeMapData >= cp.biomesList.get(i).biomeMinValueOnMap && biomeMapData <= cp.biomesList.get(i).biomeMaxValueOnMap)
				if(r != null) {
					if(cp.biomesList.get(i).biomeMaxValueOnMap - cp.biomesList.get(i).biomeMinValueOnMap < r.biomeMaxValueOnMap - r.biomeMinValueOnMap)
						r = cp.biomesList.get(i);
				}else
					r = cp.biomesList.get(i);
		//-//
		if(r == null)
			r = cp.standardBiomeOnMap;
		return r;
	}
	
	public static int getBiggestInterpolationQuality(WE_ChunkProvider cp) {
		int r = 0;
		for(int i = 0; i < cp.biomesList.size(); i++)
			if(cp.biomesList.get(i).biomeInterpolateQuality > r)
				r = cp.biomesList.get(i).biomeInterpolateQuality;
		return r;
	}
}