package galaxyspace.systems.SolarSystem.planets.venus.world.gen;

import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.world.gen.WorldGenSmallLakes;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorVenus extends BiomeDecoratorSpace
{
	private World currentWorld;
	private WorldGenerator OreGenSulfur;
	private WorldGenerator OreGenDiamond;
	private WorldGenerator lavalakesGen;

	public BiomeDecoratorVenus()
	{
		this.OreGenSulfur = new WorldGenMinableMeta(GSBlocks.VenusBlocks, 6, 2, true, GSBlocks.VenusBlocks, 1);
		this.OreGenDiamond = new WorldGenMinableMeta(GSBlocks.VenusBlocks, 4, 3, true, GSBlocks.VenusBlocks, 1);
	}	

	@Override
	protected void decorate()
	{

		if(GSConfigCore.enableOresGeneration) this.generateOre(4, this.OreGenSulfur, 5, 20);
		if(GSConfigCore.enableOresGeneration) this.generateOre(5, this.OreGenDiamond, 5, 20);
		this.lavalakesGen = new WorldGenSmallLakes(Blocks.flowing_lava, GSBlocks.VenusBlocks, 4);
		
		int var3;
		int var4;
		int var5;
/*
		for (int i = 0; i < 10; i++) {
			 
			var3 = chunkX + rand.nextInt(16);
			var4 = this.rand.nextInt(111);
			var5 = chunkZ + rand.nextInt(16);
       
	        if (this.currentWorld.getBlock(var3, var4 - 1, var5) == GSBlocks.VenusBlocks && this.currentWorld.getBlockMetadata(var3, var4 - 1, var5) == 1 && var4 > 70)
	        {
	        		this.currentWorld.setBlock(var3, var4 - 3, var5, Blocks.lava);
	        		this.currentWorld.setBlock(var3, var4 - 2, var5, Blocks.lava);
	         		this.currentWorld.setBlock(var3, var4 - 1, var5, Blocks.lava);
	         		this.currentWorld.setBlock(var3, var4 + this.rand.nextInt(6), var5, Blocks.flowing_lava);
	        }
		}*/
		
		if(this.currentWorld.rand.nextInt(10) == 0)
		{
			var3 = this.chunkX + this.rand.nextInt(16) + 8;
			var5 = this.chunkZ + this.rand.nextInt(16) + 8;
			var4 = this.getCurrentWorld().getTopSolidOrLiquidBlock(var3, var5);
			

			this.lavalakesGen.generate(this.currentWorld, this.rand, var3, var4, var5);
			
		}
	}

	@Override
	protected void setCurrentWorld(World world)
    {
    	this.currentWorld = world;
    }

	@Override
	protected World getCurrentWorld()
	{
		return this.currentWorld;
	}
}