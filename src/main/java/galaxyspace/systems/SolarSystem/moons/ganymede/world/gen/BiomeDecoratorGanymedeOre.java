package galaxyspace.systems.SolarSystem.moons.ganymede.world.gen;

import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.registers.blocks.GSBlocks;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorGanymedeOre extends BiomeDecoratorSpace
{
	private World world;
	private WorldGenerator OreGenMagnesium;
    private WorldGenerator OreGenIlmenite;

	public BiomeDecoratorGanymedeOre()
	{
		this.OreGenMagnesium = new WorldGenMinableMeta(GSBlocks.GanymedeBlocks, 5, 2, true, GSBlocks.GanymedeBlocks, 1);
		this.OreGenIlmenite = new WorldGenMinableMeta(GSBlocks.GanymedeBlocks, 3, 3, true, GSBlocks.GanymedeBlocks, 1);
	}	

	@Override
	protected void decorate()
	{
		if(GSConfigCore.enableOresGeneration) this.generateOre(24, this.OreGenMagnesium, 10, 60);
		if(GSConfigCore.enableOresGeneration) this.generateOre(6, this.OreGenIlmenite, 10, 30);
		
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