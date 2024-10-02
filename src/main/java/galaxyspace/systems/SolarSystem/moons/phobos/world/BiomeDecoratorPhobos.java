package galaxyspace.systems.SolarSystem.moons.phobos.world;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import net.minecraft.world.World;

public class BiomeDecoratorPhobos extends BiomeDecoratorSpace
{
	private World world;
	//private WorldGenerator OreGenCobaltum;
   // private WorldGenerator OreGenDesh;

	public BiomeDecoratorPhobos()
	{
		//if(GSConfigCore.enableOresGeneration) this.OreGenCobaltum = new WorldGenMinableMeta(GSBlocks.PhobosBlocks, 4, 4, true, GSBlocks.PhobosBlocks, 2);
		//if(GSConfigCore.enableOresGeneration) this.OreGenDesh = new WorldGenMinableMeta(GSBlocks.PhobosBlocks, 5, 3, true, GSBlocks.PhobosBlocks, 2);
												
		
	}	

	@Override
	protected void decorate()
	{
		//if(GSConfigCore.enableOresGeneration) this.generateOre(6, this.OreGenCobaltum, 10, 30);
		//if(GSConfigCore.enableOresGeneration) this.generateOre(12, this.OreGenDesh, 30, 50);
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