package galaxyspace.systems.SolarSystem.moons.titan.world.gen;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import net.minecraft.world.World;

public class BiomeDecoratorTitanOre extends BiomeDecoratorSpace
{
	private World world;

	public BiomeDecoratorTitanOre()
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