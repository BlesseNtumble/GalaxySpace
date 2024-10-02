//- By Vamig Aliev.
//- https://vk.com/win_vista.

package galaxyspace.core.world.worldengine.standardcustomgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class WE_OreGen implements IWorldGenerator {
	public List<Integer>
		fieldMinY            = new ArrayList(),
		fieldMaxY            = new ArrayList(),
		fieldMinRadius       = new ArrayList(),
		fieldMaxRadius       = new ArrayList(),
		oreInMaxFieldDensity = new ArrayList(),
		chunksForField       = new ArrayList();
	public List<WorldGenMinable> oreGen = new ArrayList();
	
	public void add(Block oreBlock, byte oreBlockMeta, Block replacingBlock, int oreBlockCountInLode,
		int p_fieldMinY, int p_fieldMaxY, int p_fieldMinRadius, int p_fieldMaxRadius, int p_oreInMaxFieldDensity, int p_chunksForField) {
		oreGen.add(new WorldGenMinable(oreBlock, oreBlockMeta, oreBlockCountInLode, replacingBlock));
		//-//
		fieldMinY           .add(p_fieldMinY           );
		fieldMaxY           .add(p_fieldMaxY           );
		fieldMinRadius      .add(p_fieldMinRadius      );
		fieldMaxRadius      .add(p_fieldMaxRadius      );
		oreInMaxFieldDensity.add(p_oreInMaxFieldDensity);
		chunksForField      .add(p_chunksForField      );
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		for(int i = 0; i < oreGen.size(); i++)
			if(random.nextInt(chunksForField.get(i)) == 0) {
				float s = 360.0F / oreInMaxFieldDensity.get(i);
				int sy = fieldMinY.get(i) + random.nextInt(fieldMaxY.get(i) - fieldMinY.get(i)),
					sx = chunkX * 16 + random.nextInt(16),
					sz = chunkZ * 16 + random.nextInt(16),
					r = fieldMinRadius.get(i) + random.nextInt(fieldMaxRadius.get(i) - fieldMinRadius.get(i) + 1);
				for(float v = -90.0F; v <= 90.0F; v += s)
					for(float h = 0.0F; h <= 359.0F; h += s) {
						int rc = random.nextInt(r + 1);
						float
							px = MathHelper.cos(v * (float)Math.PI / 180.0F) * MathHelper.sin(h * (float)Math.PI / 180.0F),
							pz = MathHelper.cos(v * (float)Math.PI / 180.0F) * MathHelper.cos(h * (float)Math.PI / 180.0F),
							py = MathHelper.sin(v * (float)Math.PI / 180.0F)                                              ;
						oreGen.get(i).generate(world, random,
							sx + MathHelper.floor_float(px * rc), sy + MathHelper.floor_float(py * rc), sz + MathHelper.floor_float(pz * rc));
					}
			}
	}
}