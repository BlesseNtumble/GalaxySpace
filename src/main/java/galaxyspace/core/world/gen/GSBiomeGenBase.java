package galaxyspace.core.world.gen;

import galaxyspace.core.configs.GSConfigBiomes;
import galaxyspace.core.world.gen.biome.BiomeGenSpaceGS;
import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class GSBiomeGenBase extends BiomeGenBase
{
	public static BiomeGenBase GSSpace = new BiomeGenSpaceGS(GSConfigBiomes.IDSpaceBiome).setBiomeName("Space").setHeight(height_Default);
	public static BiomeGenBase GSSpaceShallowWaters = new BiomeGenSpaceGS(GSConfigBiomes.IDSpaceShallowWatersBiome).setBiomeName("SpaceShallowWaters").setHeight(height_ShallowWaters);
	public static BiomeGenBase GSSpaceOceans = new BiomeGenSpaceGS(GSConfigBiomes.IDSpaceOceansBiome).setBiomeName("SpaceOceans").setHeight(height_Oceans);
	public static BiomeGenBase GSSpaceDeepOceans = new BiomeGenSpaceGS(GSConfigBiomes.IDSpaceDeepOceansBiome).setBiomeName("SpaceDeepOceans").setHeight(height_DeepOceans);
	public static BiomeGenBase GSSpaceLowPlains = new BiomeGenSpaceGS(GSConfigBiomes.IDSpaceLowPlainsBiome).setBiomeName("SpaceLowPlains").setHeight(height_LowPlains);
	public static BiomeGenBase GSSpaceMidPlains = new BiomeGenSpaceGS(GSConfigBiomes.IDSpaceMidPlainsBiome).setBiomeName("SpaceMidPlains").setHeight(height_MidPlains);
	public static BiomeGenBase GSSpaceLowHills = new BiomeGenSpaceGS(GSConfigBiomes.IDSpaceLowHillsBiome).setBiomeName("SpaceLowHills").setHeight(height_LowHills);
	public static BiomeGenBase GSSpaceHighPlateaus = new BiomeGenSpaceGS(GSConfigBiomes.IDSpaceHighPlateausBiome).setBiomeName("SpaceHighPlateaus").setHeight(height_HighPlateaus);
	public static BiomeGenBase GSSpaceMidHills = new BiomeGenSpaceGS(GSConfigBiomes.IDSpaceMidHillsBiome).setBiomeName("SpaceMidHills").setHeight(height_MidHills);
	public static BiomeGenBase GSSpaceRockyWaters = new BiomeGenSpaceGS(GSConfigBiomes.IDSpaceRockyWatersBiome).setBiomeName("SpaceRockyWaters").setHeight(height_RockyWaters);
	public static BiomeGenBase GSSpaceLowIslands = new BiomeGenSpaceGS(GSConfigBiomes.IDSpaceLowIslandsBiome).setBiomeName("SpaceLowIslands").setHeight(height_LowIslands);
	public static BiomeGenBase GSSpacePartiallySubmerged = new BiomeGenSpaceGS(GSConfigBiomes.IDSpacePartiallySubmergedBiome).setBiomeName("SpacePartiallySubmerged").setHeight(height_PartiallySubmerged);
	public static BiomeGenBase GSSpaceBeach = new BiomeGenSpaceGS(GSConfigBiomes.IDSpaceBeachBiome).setBiomeName("SpaceBeach").setHeight(height_Shores);
	
	public Block stoneBlock;
	public byte topMeta;
	public byte fillerMeta;
	public byte stoneMeta;
	
	public GSBiomeGenBase(int id)
	{
		super(id);
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.rainfall = 0F;
		this.setColor(-16744448);
		
		this.theBiomeDecorator.treesPerChunk = -999;
		this.theBiomeDecorator.flowersPerChunk = -999;
		this.theBiomeDecorator.grassPerChunk = -999;
	}

	@Override
	public GSBiomeGenBase setColor(int var1)
	{
		return (GSBiomeGenBase) super.setColor(var1);
	}

	@Override
	public float getSpawningChance()
	{
		return 0.1F;
	}
	
	@Override
	public BiomeDecorator createBiomeDecorator()
	{
		return new GSBiomeDecorator();
	}

	protected GSBiomeDecorator getBiomeDecorator()
	{
		return (GSBiomeDecorator)this.theBiomeDecorator;
	}
		
}