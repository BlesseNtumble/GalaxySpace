//- By Vamig Aliev.
//- https://vk.com/win_vista.

package galaxyspace.core.world.worldengine.standardcustomgen;

import java.util.ArrayList;
import java.util.List;

import galaxyspace.core.world.worldengine.additions.WE_CreateChunkGen_InXZ;
import galaxyspace.core.world.worldengine.additions.WE_GeneratorData;
import net.minecraft.block.Block;

public class WE_BiomeLayer extends WE_CreateChunkGen_InXZ {
	public List<Block> layerBlock     = new ArrayList();
	public List<Byte > layerBlockMeta = new ArrayList();
	//-//
	public List<Block> layerReplacingBlock     = new ArrayList();
	public List<Byte > layerReplacingBlockMeta = new ArrayList();
	//-//
	public List<Integer>
		layerStart       = new ArrayList(),
		layerRandomStart = new ArrayList(),
		layerEnd         = new ArrayList(),
		layerRandomEnd   = new ArrayList();
	//-//
	public List<Boolean> layerUnderWaterGen = new ArrayList();
	
	public void add(Block block, byte meta, Block replacingBlock, byte replacingBlockMeta, int start, int r_start, int end, int r_end, boolean underWater) {
		layerBlock    .add(block);
		layerBlockMeta.add(meta );
		//-//
		layerReplacingBlock    .add(replacingBlock    );
		layerReplacingBlockMeta.add(replacingBlockMeta);
		//-//
		layerStart      .add(start  );
		layerRandomStart.add(r_start);
		layerEnd        .add(end    );
		layerRandomEnd  .add(r_end  );
		//-//
		layerUnderWaterGen.add(underWater);
	}
	
	public void add(Block block, byte meta,                                                int start, int r_start, int end, int r_end, boolean underWater) {
		layerBlock    .add(block);
		layerBlockMeta.add(meta );
		//-//
		layerReplacingBlock    .add(      null);
		layerReplacingBlockMeta.add((byte)  -1);
		//-//
		layerStart      .add(start  );
		layerRandomStart.add(r_start);
		layerEnd        .add(end    );
		layerRandomEnd  .add(r_end  );
		//-//
		layerUnderWaterGen.add(underWater);
	}
	
	@Override
	public void gen(WE_GeneratorData data) {
		for(int i = 0; i < layerBlock.size(); i++) {
			int startPoint = layerStart.get(i), endPoint = layerEnd.get(i);
			if(layerStart.get(i) < 0 || layerEnd.get(i) < 0) {
				if(layerStart.get(i) < -255)
					startPoint = Math.abs(layerStart.get(i) % 256);
				if(layerEnd  .get(i) < -255)
					endPoint   = Math.abs(layerEnd  .get(i) % 256);
				for(int y = 255; y >= 0; y--)
					if(getBlock(data, y) != null) {
						if(layerStart.get(i) < 0)
							startPoint += y;
						if(layerEnd  .get(i) < 0)
							endPoint   += y;
						break;
					}
			}
			//-//
			if(     layerRandomStart.get(i) > 0)
				startPoint += data.chunkProvider.rand.nextInt(         layerRandomStart.get(i)  + 1);
			else if(layerRandomStart.get(i) < 0)
				startPoint -= data.chunkProvider.rand.nextInt(Math.abs(layerRandomStart.get(i)) + 1);
			if(     layerRandomEnd  .get(i) > 0)
				endPoint   += data.chunkProvider.rand.nextInt(         layerRandomEnd  .get(i)  + 1);
			else if(layerRandomEnd  .get(i) < 0)
				endPoint   -= data.chunkProvider.rand.nextInt(Math.abs(layerRandomEnd  .get(i)) + 1);
			//-//
			if(!layerUnderWaterGen.get(i) && getBlock(data, startPoint + 1) != null)
				if(getBlock(data, startPoint + 1).getMaterial().isLiquid())
					return;
			
			if(layerReplacingBlockMeta.get(i) != -1) {
				for(int y = startPoint; y >= endPoint; y--)
					if(getBlock(data, y) == layerReplacingBlock.get(i) && getBlockMeta(data, y) == layerReplacingBlockMeta.get(i))
						setBlock(data, layerBlock.get(i), layerBlockMeta.get(i), y);
			}else
				for(int y = startPoint; y >= endPoint; y--)
					setBlock    (data, layerBlock.get(i), layerBlockMeta.get(i), y);
		}
	}
}