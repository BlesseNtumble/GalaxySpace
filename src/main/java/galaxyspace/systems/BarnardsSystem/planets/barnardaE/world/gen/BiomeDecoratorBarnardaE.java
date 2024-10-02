package galaxyspace.systems.BarnardsSystem.planets.barnardaE.world.gen;

import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRBlocks;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorBarnardaE extends BiomeDecoratorSpace
{
	private World world;
	private WorldGenerator OreGenIron;
	private WorldGenerator OreGenGold;

	public BiomeDecoratorBarnardaE()
	{
		this.OreGenIron = new WorldGenMinableMeta(BRBlocks.BarnardaEBlocks, 5, 2, true, BRBlocks.BarnardaEBlocks, 1);
		this.OreGenGold = new WorldGenMinableMeta(BRBlocks.BarnardaEBlocks, 4, 3, true, BRBlocks.BarnardaEBlocks, 1);
	}	

	@Override
	protected void decorate()
	{
		if(GSConfigCore.enableOresGeneration) this.generateOre(12, this.OreGenIron, 5, 60);
		if(GSConfigCore.enableOresGeneration) this.generateOre(6, this.OreGenGold, 5, 20);
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