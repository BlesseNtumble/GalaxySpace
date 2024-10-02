package galaxyspace.systems.SolarSystem.moons.io.world.gen;

import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.fluids.GSFluids;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorIo extends BiomeDecoratorSpace
{
	private World world;
	public WorldGenerator lavalakesGen, sulfurlakesGen;

    private WorldGenerator OreGenCopper;
    private WorldGenerator OreGenSulfur;
	public int ioLavaLakesPerChunk, ioSulfurLakesPerChunk;

	public BiomeDecoratorIo()
	{
		this.lavalakesGen = new WorldGenIoLava(Blocks.flowing_lava, GSBlocks.IoBlocks, 1);
		this.ioLavaLakesPerChunk = 4;
		
		this.sulfurlakesGen = new WorldGenIoLava(GSFluids.BlockSulfurAcid, GSBlocks.IoBlocks, 0);
		this.ioSulfurLakesPerChunk = 5;

		this.OreGenCopper = new WorldGenMinableMeta(GSBlocks.IoBlocks, 4, 3, true, GSBlocks.IoBlocks, 2);
		this.OreGenSulfur = new WorldGenMinableMeta(GSBlocks.IoBlocks, 3, 4, true, GSBlocks.IoBlocks, 2);
		
	}	

	@Override
	protected void decorate()
	{

		if(GSConfigCore.enableOresGeneration) this.generateOre(24, this.OreGenCopper, 10, 60);
		if(GSConfigCore.enableOresGeneration) this.generateOre(12, this.OreGenSulfur, 20, 60);
		
		int var2;
		int var3;
		int var4;
		int var5;
			
		if (this.rand.nextInt(150) == 0)
        {
            int i2 = this.chunkX + this.rand.nextInt(16) + 8;
            int k3 = this.chunkZ + this.rand.nextInt(16) + 8;
            int l2 = this.world.getTopSolidOrLiquidBlock(i2, k3) - 10 - this.rand.nextInt(5);
            (new WorldGenVaporPool()).generate(this.world, this.rand, i2, l2, k3);
        }
		
		for (var2 = 0; var2 < this.ioLavaLakesPerChunk; ++var2)
		{
			var3 = this.chunkX + this.rand.nextInt(16) + 16;
			var4 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(112) + 8) + 8);
			var5 = this.chunkZ + this.rand.nextInt(16) + 16;
			if (var4 > 68)
	        {
				this.lavalakesGen.generate(this.world, this.rand, var3, var4, var5);
	        }
		}
		
		for (var2 = 0; var2 < this.ioSulfurLakesPerChunk; ++var2)
		{
			var3 = this.chunkX + this.rand.nextInt(16) + 16;
			var4 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(112) + 8) + 8);
			var5 = this.chunkZ + this.rand.nextInt(16) + 16;
			if (var4 > 68)
	        {
				this.sulfurlakesGen.generate(this.world, this.rand, var3, var4, var5);
	        }
		}
		
		for (int i = 0; i < 10; i++) {
			 
			var3 = chunkX + rand.nextInt(16);
			var4 = this.rand.nextInt(111);
			var5 = chunkZ + rand.nextInt(16);
       
	        if (this.world.getBlock(var3, var4, var5) == GSBlocks.IoBlocks && this.world.getBlockMetadata(var3, var4, var5) == 1 && var4 > 76)
	        {	        
	        	this.world.setBlock(var3, var4 - 1, var5, Blocks.lava);
	         	if(rand.nextInt(2) == 0) 
	         		this.world.setBlock(var3, var4, var5, GSBlocks.IoBlocks, 8, 3);
	         	this.world.setBlock(var3, var4 + 1 + rand.nextInt(1), var5, Blocks.flowing_lava);
	        }
	        
	        if(this.world.getBlock(var3, var4, var5).getMaterial() == Material.lava)
	        {
	        	this.world.setBlock(var3, var4 - 1, var5, GSBlocks.IoBlocks, 8, 3);
	        	this.world.setBlock(var3, var4 - 2, var5, Blocks.lava);
	        }
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