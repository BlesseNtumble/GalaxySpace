package galaxyspace.systems.SolarSystem.moons.miranda.world.gen;

import asmodeuscore.core.astronomy.dimension.world.gen.WorldGenLakes;
import galaxyspace.GalaxySpace;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.registers.blocks.GSBlocks;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorMiranda extends BiomeDecoratorSpace
{

    private World currentWorld;
    
    private WorldGenerator OreGenIron, OreGenDolomite, OreGenDiamond, OreGenQuartz, OreGenCobalt, OreGenNickel;
    private WorldGenerator stoneGen_0, stoneGen_1, stoneGen_2, lavalakesGen;
    
    public BiomeDecoratorMiranda()
    {
        //this.nickelGen = new WorldGenMinableMeta(GSBlocks.MERCURY_BLOCKS, 4, 3, true, GSBlocks.MERCURY_BLOCKS, 2);
    	OreGenIron = new WorldGenMinableMeta(GSBlocks.MIRANDA_BLOCKS, 6, 3, true, GSBlocks.MIRANDA_BLOCKS, 13); 
    	OreGenDolomite = new WorldGenMinableMeta(GSBlocks.MIRANDA_BLOCKS, 4, 4, true, GSBlocks.MIRANDA_BLOCKS, 13); 
    	OreGenDiamond = new WorldGenMinableMeta(GSBlocks.MIRANDA_BLOCKS, 4, 5, true, GSBlocks.MIRANDA_BLOCKS, 2); 
    	OreGenQuartz = new WorldGenMinableMeta(GSBlocks.MIRANDA_BLOCKS, 10, 6, true, GSBlocks.MIRANDA_BLOCKS, 2); 
    	OreGenCobalt = new WorldGenMinableMeta(GSBlocks.MIRANDA_BLOCKS, 10, 7, true, GSBlocks.MIRANDA_BLOCKS, 13); 
    	OreGenNickel = new WorldGenMinableMeta(GSBlocks.MIRANDA_BLOCKS, 10, 8, true, GSBlocks.MIRANDA_BLOCKS, 14); 
    	    	
    	stoneGen_0 = new WorldGenMinableMeta(GSBlocks.MIRANDA_BLOCKS, 14, 2, true, GSBlocks.MIRANDA_BLOCKS, 13);
    	stoneGen_1 = new WorldGenMinableMeta(GSBlocks.MIRANDA_BLOCKS, 14, 2, true, GSBlocks.MIRANDA_BLOCKS, 14);
    	stoneGen_2 = new WorldGenMinableMeta(GSBlocks.MIRANDA_BLOCKS, 14, 14, true, GSBlocks.MIRANDA_BLOCKS, 2);
    
    	lavalakesGen = new WorldGenLakes(Blocks.FLOWING_LAVA, 0, GSBlocks.MIRANDA_BLOCKS, 2);
    	
    }

    @Override
    protected void decorate()
    {
    	if(GSConfigCore.enableOresGeneration) {
    		this.generateOre(16, OreGenIron, 10, 80);
    		this.generateOre(10, OreGenDolomite, 10, 40);
    		this.generateOre(4, OreGenDiamond, 5, 16);
    		this.generateOre(16, OreGenQuartz, 10, 90);
    		this.generateOre(10, OreGenCobalt, 10, 50);
    		this.generateOre(8, OreGenNickel, 30, 90);
    	}    	
    	
    	this.generateOre(150, stoneGen_0, 10, 100);
    	this.generateOre(150, stoneGen_1, 10, 100);
    	this.generateOre(150, stoneGen_2, 10, 100);
    	
    	int x = posX + rand.nextInt(16) + 8;
		int z = posZ + rand.nextInt(16) + 8;    	

		if (rand.nextInt(10) == 0) {    		
    		int y = 10 + rand.nextInt(30);
	    	IBlockState state = this.getCurrentWorld().getBlockState(new BlockPos(x, y, z).down());
	    	if(state == GSBlocks.MIRANDA_BLOCKS.getStateFromMeta(2)) {
	    		this.lavalakesGen.generate(this.getCurrentWorld(), this.rand, new BlockPos(x,y,z));
	    	}
    		
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
