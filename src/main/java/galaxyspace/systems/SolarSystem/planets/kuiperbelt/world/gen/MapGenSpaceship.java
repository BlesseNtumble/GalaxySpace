package galaxyspace.systems.SolarSystem.planets.kuiperbelt.world.gen;

import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class MapGenSpaceship extends MapGenBaseMeta{

	@Override
	public void generate(IChunkProvider par1IChunkProvider, World world, int chunkX, int chunkZ, Block[] blocks, byte[] metadata)
    {
		int y = world.getHeight() - 200;
		int x = chunkX * 16 + rand.nextInt(8);
		int z = chunkZ * 16 + rand.nextInt(8);
		
		if(this.rand.nextInt(200) == 0) {
			GalaxySpace.debug("gen: " + x + " | " + z);

		}
    }
}
