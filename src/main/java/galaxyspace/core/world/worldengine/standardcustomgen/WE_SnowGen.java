//- By Vamig Aliev.
//- https://vk.com/win_vista.

package galaxyspace.core.world.worldengine.standardcustomgen;

import galaxyspace.core.world.worldengine.additions.WE_CreateChunkGen_InXZ;
import galaxyspace.core.world.worldengine.additions.WE_GeneratorData;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

public class WE_SnowGen extends WE_CreateChunkGen_InXZ {
	public int snowPoint       = 111;
	public int randomSnowPoint =   2;
	//-//
	public Block   snowBlock     = Blocks.snow_layer;
	public byte    snowBlockMeta =                 0;
	public boolean genSnow       =              true;
	//-//
	public Block    iceBlock       =     Blocks.ice;
	public byte     iceBlockMeta   =              0;
	public Material freezeMaterial = Material.water;
	//-//
	public int snowOnWaterRandom = 2;
	
	@Override
	public void gen(WE_GeneratorData data) {
		for(int y = 255; y >= snowPoint + data.chunkProvider.rand.nextInt(randomSnowPoint + 1); y--)
			if(getBlock(data, y) != null) {
				if(getBlock(data, y).getMaterial() == freezeMaterial) {
					setBlock    (data,  iceBlock,  iceBlockMeta, y    );
					if(genSnow && data.chunkProvider.rand.nextInt(snowOnWaterRandom + 1) == 0)
						setBlock(data, snowBlock, snowBlockMeta, y + 1);
				}else
					if(genSnow)
						setBlock(data, snowBlock, snowBlockMeta, y + 1);
				break;
			}
	}
}