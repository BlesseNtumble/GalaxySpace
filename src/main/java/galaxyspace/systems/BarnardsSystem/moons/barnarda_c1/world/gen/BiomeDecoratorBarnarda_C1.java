package galaxyspace.systems.BarnardsSystem.moons.barnarda_c1.world.gen;

import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.registers.blocks.GSBlocks;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorBarnarda_C1 extends BiomeDecoratorSpace
{

    private World currentWorld;

   
    public BiomeDecoratorBarnarda_C1()
    {
;
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
