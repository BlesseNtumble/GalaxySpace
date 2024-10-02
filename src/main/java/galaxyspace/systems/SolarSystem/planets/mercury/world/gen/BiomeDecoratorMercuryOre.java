package galaxyspace.systems.SolarSystem.planets.mercury.world.gen;

import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.registers.blocks.GSBlocks;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorMercuryOre extends BiomeDecoratorSpace
{
	private World world;
	private WorldGenerator OreGenIron;
    private WorldGenerator OreGenNickel;
    private WorldGenerator OreGenMagnesium;

	public BiomeDecoratorMercuryOre()
	{
		this.OreGenIron = new WorldGenMinableMeta(GSBlocks.MercuryBlocks, 6, 3, true, GSBlocks.MercuryBlocks, 2);
		this.OreGenNickel = new WorldGenMinableMeta(GSBlocks.MercuryBlocks, 4, 4, true, GSBlocks.MercuryBlocks, 2);
		this.OreGenMagnesium = new WorldGenMinableMeta(GSBlocks.MercuryBlocks, 4, 5, true, GSBlocks.MercuryBlocks, 2);

	}	

	@Override
	protected void decorate()
	{
		if(GSConfigCore.enableOresGeneration) {
			this.generateOre(12, this.OreGenIron, 20, 70);
			this.generateOre(6, this.OreGenNickel, 5, 20);
			this.generateOre(8, this.OreGenMagnesium, 20, 60);
		}
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