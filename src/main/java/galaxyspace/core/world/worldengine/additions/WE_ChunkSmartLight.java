//- By Vamig Aliev.
//- https://vk.com/win_vista.

package galaxyspace.core.world.worldengine.additions;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class WE_ChunkSmartLight extends Chunk {
	public WE_ChunkSmartLight(World p1, Block[] p2, byte[] p3, int p4, int p5) {
		super(p1, p2, p3, p4, p5);
	}
	
	public void generateSkylightMap() {
		int i = getTopFilledSegment();
		heightMapMinimum = Integer.MAX_VALUE;
		//-//
		for(int j = 0; j < 16; ++j) {
			for(int k = 0; k < 16; ++k) {
				precipitationHeightMap[j + (k << 4)] = -999;
				
				for(int l = i + 16; l > 0; --l)
					if(func_150808_b(j, l - 1, k) != 0) {
						heightMap[k << 4 | j] = l;
						if(l < heightMapMinimum)
							heightMapMinimum = l;
						break;
					}
				
				if(!worldObj.provider.hasNoSky) {
					int k1 = 15, i1 = i + 15;
					while(true) {
						int j1 = func_150808_b(j, i1, k);
						if(j1 == 0 && k1 != 15)
							j1 = 1;
						//-//
						k1 -= j1;
						if(k1 > 0) {
							ExtendedBlockStorage extendedblockstorage = getBlockStorageArray()[i1 >> 4];
							if(extendedblockstorage != null) {
								extendedblockstorage.setExtSkylightValue(j, i1 & 15, k, k1);
								worldObj.func_147479_m((xPosition << 4) + j, i1, (zPosition << 4) + k);
							}
						}
						//-//
						--i1;
						if(i1 <= 0 || k1 <= 0)
							break;
					}
				}
			}
		}
		
		isModified = true;
	}
}