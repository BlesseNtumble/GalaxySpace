package galaxyspace.systems.SolarSystem.moons.callisto.world.gen;

import galaxyspace.core.configs.GSConfigCore;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import net.minecraft.world.World;

public class BiomeDecoratorCallisto extends BiomeDecoratorSpace
{

    private World currentWorld;
    


    public BiomeDecoratorCallisto()
    {
        //this.nickelGen = new WorldGenMinableMeta(GSBlocks.MERCURY_BLOCKS, 4, 3, true, GSBlocks.MERCURY_BLOCKS, 2);
    }

    @Override
    protected void decorate()
    {
    	if(GSConfigCore.enableOresGeneration) {

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
