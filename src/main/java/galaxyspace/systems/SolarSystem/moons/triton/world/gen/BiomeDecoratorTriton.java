package galaxyspace.systems.SolarSystem.moons.triton.world.gen;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import net.minecraft.world.World;

public class BiomeDecoratorTriton  extends BiomeDecoratorSpace{

	private World currentWorld;
	public BiomeDecoratorTriton()
	{		 
	}

	@Override
	protected void decorate() {		
	}
	
	@Override
	protected void setCurrentWorld(World world) {
		this.currentWorld = world;
	}

	@Override
	protected World getCurrentWorld() {
		return this.currentWorld;
	}
}
