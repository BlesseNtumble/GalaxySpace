package galaxyspace.systems.SolarSystem.planets.ceres.world.gen;

import galaxyspace.core.GSBlocks;
import galaxyspace.core.configs.GSConfigCore;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorCeres extends BiomeDecoratorSpace
{

    private World currentWorld;
    private WorldGenerator iceGen;
    private WorldGenerator meteoricIronGen;
    private WorldGenerator dolomiteGen;

    public BiomeDecoratorCeres()
    {
    	 
        this.dolomiteGen = new WorldGenMinableMeta(GSBlocks.CERES_BLOCKS, 6, 2, true, GSBlocks.CERES_BLOCKS, 1);
        this.meteoricIronGen = new WorldGenMinableMeta(GSBlocks.CERES_BLOCKS, 4, 3, true, GSBlocks.CERES_BLOCKS, 1);
        this.iceGen = new WorldGenMinableMeta(GSBlocks.SURFACE_ICE, 8, 4, true, GSBlocks.CERES_BLOCKS, 1);
    }

    @Override
    protected void decorate()
    {
    	if(GSConfigCore.enableOresGeneration) 
    	{    		
    		this.generateOre(12, dolomiteGen, 10, 60);
    		this.generateOre(12, meteoricIronGen, 5, 40);
    		this.generateOre(20, iceGen, 5, 60);
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
