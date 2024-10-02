//- By Vamig Aliev.
//- https://vk.com/win_vista.

package galaxyspace.core.world.worldengine.additions;

import galaxyspace.core.world.worldengine.WE_Biome;
import net.minecraft.block.Block;

public abstract class WE_CreateChunkGen {
	public abstract void gen(WE_GeneratorData data);
	
	public void  setBlock    (WE_GeneratorData data, Block block, byte meta, int x_in_chunk, int y_in_chunk, int z_in_chunk) {
		data       .chunkProvider.genSetBlock       (data.chunkBlocks, data.chunkBlocksMeta, x_in_chunk, y_in_chunk, z_in_chunk, block, meta);
	}
	//-//
	public Block getBlock    (WE_GeneratorData data,                         int x_in_chunk, int y_in_chunk, int z_in_chunk) {
		return data.chunkProvider.genReturnBlock    (data.chunkBlocks                      , x_in_chunk, y_in_chunk, z_in_chunk             );
	}
	//-//
	public byte  getBlockMeta(WE_GeneratorData data,                         int x_in_chunk, int y_in_chunk, int z_in_chunk) {
		return data.chunkProvider.genReturnBlockMeta(data.chunkBlocksMeta                  , x_in_chunk, y_in_chunk, z_in_chunk             );
	}
	
	public WE_Biome getBiome(WE_GeneratorData data, int x_in_chunk, int z_in_chunk) {
		return data.biomes[x_in_chunk][z_in_chunk];
	}
}