//- By Vamig Aliev.
//- https://vk.com/win_vista.

package galaxyspace.core.world.worldengine;

import galaxyspace.GalaxySpace;
import galaxyspace.core.configs.GSConfigBiomes;
import galaxyspace.core.world.gen.WorldProviderAdvancedSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;

public abstract class WE_WorldProvider extends WorldProviderAdvancedSpace{
	protected int we_id = GSConfigBiomes.IDWorldEngineBiome;
	float rainfall = 0.1F;
	
	@Override
	public void registerWorldChunkManager() {
		System.out.println("////////////////////////////////////-"                                      );
		System.out.println("//#===============================//=* Version: " + GalaxySpace.VERSION + "."   );
		System.out.println("//#=-------| WorldEngine |-------=//=* By Vamig Aliev (vk.com/win_vista)."  );
		System.out.println("//#===============================//=* Part of VamigA_core (vk.com/vamiga).");
		System.out.println("////////////////////////////////////-"                                      );
		//-//
		System.out.println("WorldEngine: -Registering WorldEngine..."          );
		worldChunkMgr = new WorldChunkManagerHell(new WE_Biome(we_id, true), rainfall);
		System.out.println("WorldEngine: -Registration completed successfully!");
    }
	
	@Override
	public IChunkProvider createChunkGenerator() {
		System.out.println("WorldEngine: -Starting WorldEngine..."          );
		WE_ChunkProvider m = new WE_ChunkProvider(this);
		System.out.println("WorldEngine: -WorldEngine started successfully!");
		//-//
		return m;
	}
	
	public abstract void genSettings(WE_ChunkProvider cp);
	public abstract BiomeDecoratorSpace getDecorator();
}