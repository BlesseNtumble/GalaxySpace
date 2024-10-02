package galaxyspace.core.world.gen;

import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;

public class GS_GenBlocks {
	
	private WorldProvider provider;
	private BiomeGenBase biome;
	private BlockMetaPair grass;
	private BlockMetaPair dirt;
	private BlockMetaPair stone;
	
	public GS_GenBlocks(WorldProvider provider, BiomeGenBase biome, BlockMetaPair blockGrass, BlockMetaPair blockDirt, BlockMetaPair blockStone)
	{
		this.provider = provider;	
		this.biome = biome;
		this.grass = blockGrass;
		this.dirt = blockDirt;
		this.stone = blockStone;
	}
	
	public WorldProvider getWorldProvider()
	{
		return this.provider;
	}

	public BiomeGenBase getBiome()
	{
		return this.biome;
	}
	
	public BlockMetaPair getGrassBlock()
	{
		return this.grass;
	}
	
	public BlockMetaPair getDirtBlock()
	{
		return this.dirt;
	}
	
	public BlockMetaPair getStoneBlock()
	{
		return this.stone;
	}

}
