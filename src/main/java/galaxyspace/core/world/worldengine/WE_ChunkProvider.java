//- By Vamig Aliev.
//- https://vk.com/win_vista.

package galaxyspace.core.world.worldengine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import galaxyspace.core.world.worldengine.additions.WE_ChunkSmartLight;
import galaxyspace.core.world.worldengine.additions.WE_CreateChunkGen;
import galaxyspace.core.world.worldengine.additions.WE_CreateChunkGen_InXYZ;
import galaxyspace.core.world.worldengine.additions.WE_CreateChunkGen_InXZ;
import galaxyspace.core.world.worldengine.additions.WE_GeneratorData;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_CaveGen;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_LakeGen;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_OreGen;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_RavineGen;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_TerrainGenerator;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;


public class WE_ChunkProvider extends ChunkProviderGenerate {
	public World worldObj;
	public Random rand;

	//////////////////
	//- Generators -//
	//////////////////
	public List<WE_CreateChunkGen      > createChunkGen_List       = new ArrayList();
	public List<WE_CreateChunkGen_InXZ > createChunkGen_InXZ_List  = new ArrayList();
	public List<WE_CreateChunkGen_InXYZ> createChunkGen_InXYZ_List = new ArrayList();
	public List<IWorldGenerator        > decorateChunkGen_List     = new ArrayList();
	public List<MapGenBaseMeta		   > worldGenerators 		   = new ArrayList();


	private BiomeDecoratorSpace decorator;

	//////////////////////
	//- Biome Map Info -//
	//////////////////////
	public List<WE_Biome> biomesList = new ArrayList();
	public WE_Biome standardBiomeOnMap;
	//-//
	public double biomemapPersistence = 1.0D, biomemapScaleX = 1.0D, biomemapScaleY = 1.0D;
	public int biomemapNumberOfOctaves = 1;

	public WE_Biome currentbiome;
	/////
	//=//
	/////

	public WE_ChunkProvider(WE_WorldProvider wp) {
		super(wp.worldObj, wp.getSeed(), wp.worldObj.getWorldInfo().isMapFeaturesEnabled());
		worldObj =              wp.worldObj;
		rand     = new Random(wp.getSeed());

		/////
		//=//
		/////

		createChunkGen_List.add(new WE_TerrainGenerator());
		createChunkGen_List.add(new WE_CaveGen         ());
		createChunkGen_List.add(new WE_RavineGen       ());
		
		WE_OreGen standardOres = new WE_OreGen();
		standardOres.add(Blocks.gold_ore   , (byte)0, Blocks.stone, 4,  0,  32,  4, 24, 45, 16);
		standardOres.add(Blocks.iron_ore   , (byte)0, Blocks.stone, 6,  0,  64,  8, 32, 90, 72);
		standardOres.add(Blocks.coal_ore   , (byte)0, Blocks.stone, 6, 32, 128, 16, 48, 60, 56);
		standardOres.add(Blocks.lapis_ore  , (byte)0, Blocks.stone, 4,  0,  16,  8, 24, 30, 16);
		standardOres.add(Blocks.diamond_ore, (byte)0, Blocks.stone, 3,  0,  16,  4, 24, 45, 16);
		standardOres.add(Blocks.emerald_ore, (byte)0, Blocks.stone, 2,  0,  16,  4, 24, 45, 24);
		decorateChunkGen_List.add(standardOres);
		//-//
		WE_LakeGen lavaLakes = new WE_LakeGen();
		lavaLakes.lakeBlock = Blocks.lava;
		lavaLakes.fGen      =       false;
		lavaLakes.maxY      =          32;
		decorateChunkGen_List.add(lavaLakes);
		
		System.out.println("WorldEngine: -Applying your WorldEngine settings..."  );
		wp.genSettings(this);
		decorator = wp.getDecorator();
		WE_Biome.setChunkProvider(this);
		System.out.println("WorldEngine: -WorldEngine is configured successfully!");
	}
	
	@Override
	public Chunk provideChunk(int chunkX, int chunkZ) {
		long chunk_X = (long)chunkX * 16L, chunk_Z = (long)chunkZ * 16L;
		WE_Biome.setChunkProvider(this);
		Block[] chunkBlocks     = new Block[65536];
		byte [] chunkBlocksMeta = new byte [65536];
		rand.setSeed(worldObj.getSeed() * chunkX ^ 3 + chunkZ ^ 2 * 9874L + 7684053L);
		//-//
		WE_Biome[][] chunkBiomes = new WE_Biome[16][16];
		for(int x = 0; x < 16; x++)
			for(int z = 0; z < 16; z++)
			{
				chunkBiomes[x][z] = WE_Biome.getBiomeAt(this, chunk_X + (long)x, chunk_Z + (long)z);
				currentbiome = chunkBiomes[x][z];	
			}
		
		/////
		//=//
		/////
		
		//Terrain Gen
		for(int i = 0; i < createChunkGen_List.size(); i++)
			createChunkGen_List.get(i).gen(new WE_GeneratorData(this, chunkBlocks, chunkBlocksMeta, chunk_X, chunk_Z, chunkBiomes, 0, 0, 0));
		
		//Layers//
		for(int x = 0; x < 16; x++)
			for(int z = 0; z < 16; z++) {
				for(int i = 0; i < createChunkGen_InXZ_List.size(); i++)
					createChunkGen_InXZ_List.get(i).gen(new WE_GeneratorData(this, chunkBlocks, chunkBlocksMeta, chunk_X, chunk_Z, chunkBiomes, x, 0, z));
			
				for(int i = 0; i < chunkBiomes[x][z].createChunkGen_InXZ_List.size(); i++)
					chunkBiomes[x][z].createChunkGen_InXZ_List.get(i).gen(new WE_GeneratorData(this, chunkBlocks, chunkBlocksMeta, chunk_X, chunk_Z, chunkBiomes, x, 0, z));
				//-//
				for(int y = 255; y >= 0; y--) {
					for(int i = 0; i < createChunkGen_InXYZ_List.size(); i++)
						createChunkGen_InXYZ_List                  .get(i).gen(new WE_GeneratorData(this, chunkBlocks, chunkBlocksMeta, chunk_X, chunk_Z, chunkBiomes, x, y, z));
					for(int i = 0; i < chunkBiomes[x][z].createChunkGen_InXYZ_List.size(); i++)
						chunkBiomes[x][z].createChunkGen_InXYZ_List.get(i).gen(new WE_GeneratorData(this, chunkBlocks, chunkBlocksMeta, chunk_X, chunk_Z, chunkBiomes, x, y, z));
				}
			}
		
		/////
		//=//
		/////
		
		for (MapGenBaseMeta generator : this.worldGenerators)
        {
            generator.generate(this, this.worldObj, chunkX, chunkZ, chunkBlocks, chunkBlocksMeta);
        }
		
		
		WE_ChunkSmartLight chunk = new WE_ChunkSmartLight(worldObj, chunkBlocks, chunkBlocksMeta, chunkX, chunkZ);
		chunk.generateSkylightMap();
        return chunk;
	}
	
	@Override
	public void populate(IChunkProvider chunkProvider, int chunkX, int chunkZ) {
		BlockFalling.fallInstantly =  true;
		//-//
		boolean flag = false;
		rand.setSeed(worldObj.getSeed() * chunkX + chunkZ ^ 2 * 107L + 2394720L);
		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre (chunkProvider, worldObj, rand, chunkX, chunkZ, flag));
		
		/////
		//=//
		/////
		long var7 = this.rand.nextLong() / 2L * 2L + 1L;
		long var9 = this.rand.nextLong() / 2L * 2L + 1L;
		this.rand.setSeed(chunkX * var7 + chunkZ * var9 ^ this.worldObj.getSeed());
		
		for(int i = 0; i <   decorateChunkGen_List.size(); i++)
			decorateChunkGen_List  .get(i).generate(rand, chunkX, chunkZ, worldObj, this, this);
		//-//
		WE_Biome b = WE_Biome.getBiomeAt(this, (long)chunkX * 16L + (long)rand.nextInt(16), (long)chunkZ * 16L + (long)rand.nextInt(16));
		for(int i = 0; i < b.decorateChunkGen_List.size(); i++)
			b.decorateChunkGen_List.get(i).generate(rand, chunkX, chunkZ, worldObj, this, this);


		this.decorateWorld(worldObj, rand, chunkX * 16, chunkZ * 16);
		/////
		//=//
		/////
		
		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(chunkProvider, worldObj, rand, chunkX, chunkZ, flag));
		//-//
		BlockFalling.fallInstantly = false;
	}
	
	public void decorateWorld(World world, Random rand, int x, int z)
	{
		this.getBiomeGenerator().decorate(world, rand, x, z);
	}
	
	protected BiomeDecoratorSpace getBiomeGenerator()
	{
		return this.decorator;		
	}
	
	public void genSetBlock(Block[] chunkBlocks, byte[] chunkBlocksMeta, int x, int y, int z, Block block, byte meta) {
		if(x >= 0 && x <= 15 && y >= 0 && y <= 255 && z >= 0 && z <= 15) {
			chunkBlocks    [(x * 16 + z) * 256 + y] = block;
			chunkBlocksMeta[(x * 16 + z) * 256 + y] =  meta;
		}
	}
	
	public Block genReturnBlock    (Block[] chunkBlocks    , int x, int y, int z) {
		if(x >= 0 && x <= 15 && y >= 0 && y <= 255 && z >= 0 && z <= 15)
			return chunkBlocks    [(x * 16 + z) * 256 + y];
		else
			return null;
	}
	
	public byte  genReturnBlockMeta(byte [] chunkBlocksMeta, int x, int y, int z) {
		if(x >= 0 && x <= 15 && y >= 0 && y <= 255 && z >= 0 && z <= 15)
			return chunkBlocksMeta[(x * 16 + z) * 256 + y];
		else
			return    0;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int i, int j, int k)
    {
		WE_Biome biome = WE_Biome.getBiomeAt(this, i, k);
		
		if(biome != null) 
		{/*
			if (par1EnumCreatureType == EnumCreatureType.monster) {
				final List monsters = new ArrayList();
				
				for (SpawnListEntry monster : biome) {
					monsters.add(monster);				
				}				
				return monsters;
			}
	
			if (par1EnumCreatureType == EnumCreatureType.creature) {
				final List creatures = new ArrayList();
	
				for (SpawnListEntry creature : biome.creatures) {
					creatures.add(creature);
				}
				return creatures;
			}
	
			if (par1EnumCreatureType == EnumCreatureType.waterCreature) {
				final List watercreatures = new ArrayList();
	
				for (SpawnListEntry watercreature : biome.watercreatures) {
					watercreatures.add(watercreature);
				}
				return watercreatures;
			}*/
			
			if(biome != null) 
			{		
				return biome.getSpawnableList(par1EnumCreatureType);				
			}
		}
		return null;
    }

}