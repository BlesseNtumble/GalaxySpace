package galaxyspace.systems.SolarSystem.moons.europa.world.gen;

import galaxyspace.core.registers.blocks.GSBlocks;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class MapGenSnowEuropa extends MapGenBaseMeta{

	public Block snow_layerBlock = Blocks.SNOW_LAYER;
	public byte snow_layerBlockMeta = 0;
	
	public Block snowBlock = Blocks.SNOW;
	public byte snowBlockMeta = 0;
	
	@Override
	public void generate(World world, int chunkX, int chunkZ, ChunkPrimer primer) {
		for(int x = 0; x < 16; x++) 
			for(int z = 0; z < 16; z++) 
				for (int y = 255; y >= 0; --y)
				{
					if(primer.getBlockState(x, y, z).getBlock() == Blocks.AIR && primer.getBlockState(x, y - 1, z).getBlock() == GSBlocks.EUROPA_BLOCKS)
					{
						if(world.rand.nextInt(5) == 0) primer.setBlockState(x, y + 1, z, snow_layerBlock.getStateFromMeta(snow_layerBlockMeta));
						primer.setBlockState(x, y, z, snowBlock.getStateFromMeta(snowBlockMeta));					
					}					
				}
	}
	
	@Override
	protected void recursiveGenerate(World world, int chunkX, int chunkZ, int x, int z, ChunkPrimer primer) {
	
	}
}
