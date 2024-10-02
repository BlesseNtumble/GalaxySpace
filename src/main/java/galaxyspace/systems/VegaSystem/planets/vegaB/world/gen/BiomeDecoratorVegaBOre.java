package galaxyspace.systems.VegaSystem.planets.vegaB.world.gen;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import net.minecraft.world.World;

public class BiomeDecoratorVegaBOre extends BiomeDecoratorSpace
{
	private World world;

	public BiomeDecoratorVegaBOre()
	{

	}	

	@Override
	protected void decorate()
	{
	}

	@Override
	protected void setCurrentWorld(World world)
	{
		this.world = world;
	}

	@Override
	protected World getCurrentWorld()
	{
		return this.world;
	}
}