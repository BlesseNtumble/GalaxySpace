//- By Vamig Aliev.
//- https://vk.com/win_vista.

package galaxyspace.core.world.worldengine.standardcustomgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import galaxyspace.core.world.worldengine.standardcustomgen.help.WE_BigTreeGen;
import galaxyspace.core.world.worldengine.standardcustomgen.help.WE_TreeGen;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class WE_WorldTreeGen implements IWorldGenerator {
	public List<WE_TreeGen   > ug = new ArrayList();
	public List<WE_BigTreeGen> bg = new ArrayList();
	//-//
	public List<Integer> cwt = new ArrayList(), tfc = new ArrayList(), tfb = new ArrayList();
	
	public void add(Block bWood, int mWood, Block bLeaves, int mLeaves, Block bSapling, Block bVine, Block bCocoa,
		int v1, int v2, int v3,
		int minHeight, boolean vines,
		byte ocp1, byte ocp2, byte ocp3, byte ocp4, byte ocp5, byte ocp6, int ts, int hl, int ldl, double ha, double bs, double sw, double ld) {
		ug.add(new WE_TreeGen   (false));
		bg.add(new WE_BigTreeGen(false));
		//-//
		cwt.add(v1);
		tfc.add(v2);
		tfb.add(v3);
		
		ug.get(ug.size() - 1).bWood      =    bWood;
		ug.get(ug.size() - 1).metaWood   =    mWood;
		ug.get(ug.size() - 1).bLeaves    =  bLeaves;
		ug.get(ug.size() - 1).metaLeaves =  mLeaves;
		ug.get(ug.size() - 1).bSapling   = bSapling;
		ug.get(ug.size() - 1).bVine      =    bVine;
		ug.get(ug.size() - 1).bCocoa     =   bCocoa;
		//-//
		bg.get(bg.size() - 1).bWood      =    bWood;
		bg.get(bg.size() - 1).bLeaves    =  bLeaves;
		bg.get(bg.size() - 1).metaLeaves =  mLeaves;
		bg.get(bg.size() - 1).bSapling   = bSapling;
		
		ug.get(ug.size() - 1).minTreeHeight = minHeight;
		ug.get(ug.size() - 1).vinesGrow     =     vines;
		//-//
		bg.get(bg.size() - 1).otherCoordPairs = new byte[] {ocp1, ocp2, ocp3, ocp4, ocp5, ocp6};
		bg.get(bg.size() - 1).trunkSize         =  ts;
		bg.get(bg.size() - 1).heightLimitLimit  =  hl;
		bg.get(bg.size() - 1).leafDistanceLimit = ldl;
		bg.get(bg.size() - 1).heightAttenuation =  ha;
		bg.get(bg.size() - 1).branchSlope       =  bs;
		bg.get(bg.size() - 1).scaleWidth        =  sw;
		bg.get(bg.size() - 1).leafDensity       =  ld;
	}
	
	public void add(Block bWood, int mWood, Block bLeaves, int mLeaves, Block bSapling, Block bVine, Block bCocoa, int v1, int v2, int minHeight, boolean vines) {
		ug.add(new WE_TreeGen(false));
		bg.add(                 null);
		//-//
		cwt.add(v1);
		tfc.add(v2);
		tfb.add( 0);
		
		ug.get(ug.size() - 1).bWood      =    bWood;
		ug.get(ug.size() - 1).metaWood   =    mWood;
		ug.get(ug.size() - 1).bLeaves    =  bLeaves;
		ug.get(ug.size() - 1).metaLeaves =  mLeaves;
		ug.get(ug.size() - 1).bSapling   = bSapling;
		ug.get(ug.size() - 1).bVine      =    bVine;
		ug.get(ug.size() - 1).bCocoa     =   bCocoa;
		//-//
		ug.get(ug.size() - 1).minTreeHeight = minHeight;
		ug.get(ug.size() - 1).vinesGrow     =     vines;
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		for(int i = 0; i < ug.size(); i++)
			if(random.nextInt(cwt.get(i)) == 0)
				for(int i2 = 0; i2 < tfc.get(i); i2++) {
					int x = chunkX * 16 + random.nextInt(16),
						z = chunkZ * 16 + random.nextInt(16),
						y = world.getHeightValue(x, z);
					
					if(tfb.get(i) <= 0)
						ug.get(i).generate(world, random, x, y, z);
					else
						if(random.nextInt(tfb.get(i)) == 0)
							bg.get(i).generate(world, random, x, y, z);
						else
							ug.get(i).generate(world, random, x, y, z);
				}
	}
}