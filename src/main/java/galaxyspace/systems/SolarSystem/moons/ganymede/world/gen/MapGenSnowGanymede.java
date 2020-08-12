package galaxyspace.systems.SolarSystem.moons.ganymede.world.gen;

import galaxyspace.core.GSBlocks;
import galaxyspace.systems.SolarSystem.moons.ganymede.blocks.GanymedeBlocks;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class MapGenSnowGanymede extends MapGenBaseMeta{

	public IBlockState icelayerBlock = GSBlocks.SURFACE_ICE.getStateFromMeta(4);
	
	@Override
	public void generate(World world, int chunkX, int chunkZ, ChunkPrimer primer) {
		for(int x = 0; x < 16; x++) 
			for(int z = 0; z < 16; z++) 
				for (int y = 255; y >= 0; --y)
				{
					if((y < 52 + world.rand.nextInt(2) || y > 90 - world.rand.nextInt(2)) && primer.getBlockState(x, y, z).getBlock() == Blocks.AIR 
							&& primer.getBlockState(x, y - 1, z) == GSBlocks.GANYMEDE_BLOCKS.getDefaultState().withProperty(GanymedeBlocks.BASIC_TYPE, GanymedeBlocks.EnumGanymedeBlocks.GANYMEDE_GRUNT))
					{
						primer.setBlockState(x, y, z, icelayerBlock);					
					}					
				}
	}
	
	@Override
	protected void recursiveGenerate(World world, int chunkX, int chunkZ, int x, int z, ChunkPrimer primer) {
	
	}
}
