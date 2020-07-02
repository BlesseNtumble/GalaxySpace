package galaxyspace.systems.SolarSystem.moons.ganymede.world.gen;

import galaxyspace.core.GSBlocks;
import galaxyspace.core.configs.GSConfigCore;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorGanymede extends BiomeDecoratorSpace
{

    private World currentWorld;
    
    private WorldGenerator OreGenMagnesium;
    private WorldGenerator OreGenIlmenite;

    public BiomeDecoratorGanymede()
    {
        //this.nickelGen = new WorldGenMinableMeta(GSBlocks.MERCURY_BLOCKS, 4, 3, true, GSBlocks.MERCURY_BLOCKS, 2);
    	OreGenMagnesium = new WorldGenMinableMeta(GSBlocks.GANYMEDE_BLOCKS, 4, 2, true, GSBlocks.GANYMEDE_BLOCKS, 1); 
    	OreGenIlmenite = new WorldGenMinableMeta(GSBlocks.GANYMEDE_BLOCKS, 4, 3, true, GSBlocks.GANYMEDE_BLOCKS, 1); 
    }

    @Override
    protected void decorate()
    {
    	if(GSConfigCore.enableOresGeneration) {
    		this.generateOre(16, OreGenMagnesium, 10, 60);
    		this.generateOre(10, OreGenIlmenite, 10, 40);
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
