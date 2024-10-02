package galaxyspace.systems.SolarSystem.moons.phobos.dimension;

 

import galaxyspace.core.world.gen.ChunkProviderTestEnd;
import net.minecraft.world.World;


 

public class ChunkProviderPhobos extends ChunkProviderTestEnd {


	public ChunkProviderPhobos(World par1World, long seed, boolean is) 
	{
		super(par1World, seed);
	
	}
	
	@Override	
	public boolean chunkExists(int x, int y){
	
	    return false;
	
	}

}