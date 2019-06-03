package galaxyspace.systems.SolarSystem.planets.mercury.world.gen;

import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.registers.blocks.GSBlocks;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorMercury extends BiomeDecoratorSpace
{

    private World currentWorld;
    private WorldGenerator ironGen;
    private WorldGenerator nickelGen;
    private WorldGenerator magnesiumGen;
    private WorldGenerator magmaGen;

    public BiomeDecoratorMercury()
    {
    	this.ironGen = new WorldGenMinableMeta(GSBlocks.MERCURY_BLOCKS, 5, 4, true, GSBlocks.MERCURY_BLOCKS, 2);
        this.nickelGen = new WorldGenMinableMeta(GSBlocks.MERCURY_BLOCKS, 4, 3, true, GSBlocks.MERCURY_BLOCKS, 2);
        this.magnesiumGen = new WorldGenMinableMeta(GSBlocks.MERCURY_BLOCKS, 5, 5, true, GSBlocks.MERCURY_BLOCKS, 2);
        this.magmaGen = new WorldGenMinableMeta(Blocks.MAGMA, 20, 0, true, Blocks.LAVA, 0);
    }

    @Override
    protected void decorate()
    {
    	if(GSConfigCore.enableOresGeneration) {
    		this.generateOre(18, ironGen, 5, 60);
    		this.generateOre(12, nickelGen, 5, 60);
    		this.generateOre(8, magnesiumGen, 5, 40);
    	}
    	
    	this.generateOre(18, magmaGen, 5, 25);

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
