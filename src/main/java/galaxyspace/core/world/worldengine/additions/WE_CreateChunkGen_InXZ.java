//- By Vamig Aliev.
//- https://vk.com/win_vista.

package galaxyspace.core.world.worldengine.additions;

import galaxyspace.core.world.worldengine.WE_Biome;
import net.minecraft.block.Block;

public abstract class WE_CreateChunkGen_InXZ {
	public abstract void gen(WE_GeneratorData data);
	
	public void  setBlock    (WE_GeneratorData data, Block block, byte meta, int y_in_chunk) {
		data       .chunkProvider.genSetBlock       (data.chunkBlocks, data.chunkBlocksMeta, data.cr_x, y_in_chunk, data.cr_z, block, meta);
	}
	//-//
	public Block getBlock    (WE_GeneratorData data,                         int y_in_chunk) {
		return data.chunkProvider.genReturnBlock    (data.chunkBlocks                      , data.cr_x, y_in_chunk, data.cr_z             );
	}
	//-//
	public byte  getBlockMeta(WE_GeneratorData data,                         int y_in_chunk) {
		return data.chunkProvider.genReturnBlockMeta(data.chunkBlocksMeta                  , data.cr_x, y_in_chunk, data.cr_z             );
	}
	
	public WE_Biome getBiome(WE_GeneratorData data) {
		return data.biomes[data.cr_x][data.cr_z];
	}
}