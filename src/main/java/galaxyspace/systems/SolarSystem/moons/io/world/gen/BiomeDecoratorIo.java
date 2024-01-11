package galaxyspace.systems.SolarSystem.moons.io.world.gen;

import asmodeuscore.core.astronomy.dimension.world.gen.WorldGenLakes;
import galaxyspace.core.GSBlocks;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.systems.SolarSystem.moons.io.blocks.IoBlocks;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.feature.WorldGenVaporPool;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fluids.FluidRegistry;

public class BiomeDecoratorIo extends BiomeDecoratorSpace
{

    private World currentWorld;
    private WorldGenerator OreGenCopper;
    private WorldGenerator OreGenSulfur;
    
    private WorldGenerator lavalakesGen, sulfurlakesGen;
    
    private int ioLavaLakesPerChunk = 5;
    private int ioSulfurLakesPerChunk = 10;

    
    public BiomeDecoratorIo()
    {    	
    	this.lavalakesGen = new WorldGenLakes(Blocks.FLOWING_LAVA, 0, GSBlocks.IO_BLOCKS, 0).addAdditions(GSBlocks.IO_BLOCKS.getStateFromMeta(5), 85);
    	this.sulfurlakesGen = new WorldGenLakes(FluidRegistry.getFluid("sulphuricacid").getBlock(), 0, GSBlocks.IO_BLOCKS, 0);
    	
    	this.OreGenCopper = new WorldGenMinableMeta(GSBlocks.IO_BLOCKS, 4, 3, true, GSBlocks.IO_BLOCKS, 1);
    	this.OreGenSulfur = new WorldGenMinableMeta(GSBlocks.IO_BLOCKS, 3, 4, true, GSBlocks.IO_BLOCKS, 1);
    	 
    }

    @Override
    protected void decorate()
    {
    	
    	if(GSConfigCore.enableOresGeneration) {
    		this.generateOre(24, this.OreGenCopper, 10, 60);
    		this.generateOre(12, this.OreGenSulfur, 20, 60);
    	}    
    	
    	
		int x = posX + rand.nextInt(16) + 8;
		int z = posZ + rand.nextInt(16) + 8;    
		

		if (rand.nextInt(this.ioLavaLakesPerChunk) == 0) {    		
    		int y = this.getCurrentWorld().getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).getY();   
    		IBlockState state = this.getCurrentWorld().getBlockState(new BlockPos(x, y, z).down());
    		
    		if(y <= 90 && state == GSBlocks.IO_BLOCKS.getStateFromMeta(0)) 
    			this.lavalakesGen.generate(this.getCurrentWorld(), this.rand, new BlockPos(x,y,z));   		
    	}

		if (rand.nextInt(this.ioSulfurLakesPerChunk) == 0) {    		
    		int y = this.getCurrentWorld().getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).getY();   
    		IBlockState state = this.getCurrentWorld().getBlockState(new BlockPos(x, y, z).down());
    		
    		if(y <= 90 && state == GSBlocks.IO_BLOCKS.getStateFromMeta(0)) 
    			this.sulfurlakesGen.generate(this.getCurrentWorld(), this.rand, new BlockPos(x,y,z));   		
    	}
    	
    	if (rand.nextInt(10) == 0) {

    		int y = this.getCurrentWorld().getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).getY();

    		IBlockState state = this.getCurrentWorld().getBlockState(new BlockPos(x, y, z).down());
    		
    		if(state == GSBlocks.IO_BLOCKS.getStateFromMeta(2)) {
    		
    			this.getCurrentWorld().setBlockState(new BlockPos(x, y - 1, z), Blocks.FLOWING_LAVA.getDefaultState(), 2);
    			if(rand.nextInt(2) == 0)
    				this.getCurrentWorld().setBlockState(new BlockPos(x, y, z), GSBlocks.IO_BLOCKS.getDefaultState().withProperty(IoBlocks.BASIC_TYPE, IoBlocks.EnumIoBlocks.IO_LAVA_GEYSER), 3);
    			this.getCurrentWorld().setBlockState(new BlockPos(x, y + 1 + rand.nextInt(1), z), Blocks.FLOWING_LAVA.getDefaultState(), 2);
    				
    		}
    		
    		if(state.getMaterial() == Material.LAVA)
    		{
    			this.getCurrentWorld().setBlockState(new BlockPos(x, y - 1, z), GSBlocks.IO_BLOCKS.getDefaultState().withProperty(IoBlocks.BASIC_TYPE, IoBlocks.EnumIoBlocks.IO_LAVA_GEYSER), 3);
    			this.getCurrentWorld().setBlockState(new BlockPos(x, y - 2, z), Blocks.LAVA.getDefaultState(), 2);    			
    		}
    		
    	}
    	
    	if (this.rand.nextInt(150) == 0)
        {
            int l2 = this.getCurrentWorld().getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).getY() - 10 - this.rand.nextInt(5);
            (new WorldGenVaporPool()).generate(this.getCurrentWorld(), this.rand, new BlockPos(x, l2, z));
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
