package galaxyspace.systems.SolarSystem.moons.miranda.world.gen;

import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.registers.blocks.GSBlocks;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorMiranda extends BiomeDecoratorSpace
{
	private World world;
	private WorldGenerator OreGenIron;
	private WorldGenerator OreGenDolomite;

	public BiomeDecoratorMiranda()
	{
		this.OreGenIron = new WorldGenMinableMeta(GSBlocks.MirandaBlocks, 5, 3, true, GSBlocks.MirandaBlocks, 2);
		this.OreGenDolomite = new WorldGenMinableMeta(GSBlocks.MirandaBlocks, 3, 4, true, GSBlocks.MirandaBlocks, 2);
	}	

	@Override
	protected void decorate()
	{
		if(GSConfigCore.enableOresGeneration) this.generateOre(30, this.OreGenIron, 10, 60);
		if(GSConfigCore.enableOresGeneration) this.generateOre(30, this.OreGenDolomite, 10, 80);
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