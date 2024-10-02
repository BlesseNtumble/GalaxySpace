package galaxyspace.systems.SolarSystem.planets.ceres.world.gen;

import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.registers.blocks.GSBlocks;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorCeres extends BiomeDecoratorSpace
{
	private World world;
	private WorldGenerator OreGenMeteoricIron;
	private WorldGenerator OreGenDolomite;
	private WorldGenerator GenIce;

	public BiomeDecoratorCeres()
	{
		this.OreGenMeteoricIron = new WorldGenMinableMeta(GSBlocks.CeresBlocks, 3, 3, true, GSBlocks.CeresBlocks, 1);
		this.OreGenDolomite = new WorldGenMinableMeta(GSBlocks.CeresBlocks, 3, 2, true, GSBlocks.CeresBlocks, 1);
		this.GenIce = new WorldGenMinableMeta(Blocks.ice, 3, 15, true, GSBlocks.CeresBlocks, 1);
	}	

	@Override
	protected void decorate()
	{
		if(GSConfigCore.enableOresGeneration) {
			this.generateOre(16, this.OreGenMeteoricIron, 20, 50);
			this.generateOre(30, this.OreGenDolomite, 30, 50);
		}
		this.generateOre(80, this.GenIce, 25, 80);
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