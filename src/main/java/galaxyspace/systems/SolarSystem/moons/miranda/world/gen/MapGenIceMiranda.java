package galaxyspace.systems.SolarSystem.moons.miranda.world.gen;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.systems.SolarSystem.moons.miranda.blocks.MirandaBlocks;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class MapGenIceMiranda extends MapGenBaseMeta{

	public IBlockState icelayerBlock = GSBlocks.MIRANDA_BLOCKS.getDefaultState().withProperty(MirandaBlocks.BASIC_TYPE, MirandaBlocks.EnumMirandaBlocks.MIRANDA_ICE);
	
	@Override
	public void generate(World world, int chunkX, int chunkZ, ChunkPrimer primer) {
		for(int x = 0; x < 16; x++) 
			for(int z = 0; z < 16; z++) 
				for (int y = 255; y >= 0; --y)
				{
					if(y < 54 + world.rand.nextInt(5) 
					&& primer.getBlockState(x, y, z).getBlock() == Blocks.AIR 
					&& 
					(
						primer.getBlockState(x, y - 1, z) == GSBlocks.MIRANDA_BLOCKS.getDefaultState().withProperty(MirandaBlocks.BASIC_TYPE, MirandaBlocks.EnumMirandaBlocks.MIRANDA_GRUNT) ||
						primer.getBlockState(x, y - 1, z) == GSBlocks.MIRANDA_BLOCKS.getDefaultState().withProperty(MirandaBlocks.BASIC_TYPE, MirandaBlocks.EnumMirandaBlocks.MIRANDA_GRUNT_2) ||
						primer.getBlockState(x, y - 1, z) == GSBlocks.MIRANDA_BLOCKS.getDefaultState().withProperty(MirandaBlocks.BASIC_TYPE, MirandaBlocks.EnumMirandaBlocks.MIRANDA_GRUNT_3)
					))
					{
						primer.setBlockState(x, y, z, icelayerBlock);					
					}					
				}
	}
	
	@Override
	protected void recursiveGenerate(World world, int chunkX, int chunkZ, int x, int z, ChunkPrimer primer) {
	
	}
}
