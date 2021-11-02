package galaxyspace.systems.SolarSystem.moons.phobos.world.gen;

import galaxyspace.core.GSBlocks;
import galaxyspace.core.configs.GSConfigCore;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorPhobos extends BiomeDecoratorSpace
{

    private World currentWorld;
    private WorldGenerator OreGenIron, OreGenNickel, OreGenDesh, OreGenMeteoricIron, OreGenRegolite;

    public BiomeDecoratorPhobos()
    {
    	OreGenIron = new WorldGenMinableMeta(GSBlocks.PHOBOS_BLOCKS, 6, 2, true, GSBlocks.PHOBOS_BLOCKS, 1); 
    	OreGenNickel = new WorldGenMinableMeta(GSBlocks.PHOBOS_BLOCKS, 6, 4, true, GSBlocks.PHOBOS_BLOCKS, 1); 
    	OreGenDesh = new WorldGenMinableMeta(GSBlocks.PHOBOS_BLOCKS, 4, 5, true, GSBlocks.PHOBOS_BLOCKS, 1); 
    	OreGenMeteoricIron = new WorldGenMinableMeta(GSBlocks.PHOBOS_BLOCKS, 3, 3, true, GSBlocks.PHOBOS_BLOCKS, 1); 
    	OreGenRegolite = new WorldGenMinableMeta(GSBlocks.PHOBOS_BLOCKS, 20, 0, true, GSBlocks.PHOBOS_BLOCKS, 1); 
    }

    @Override
    protected void decorate()
    {
    	if(GSConfigCore.enableOresGeneration) {
    		this.generateOre(16, OreGenIron, 10, 60);
    		this.generateOre(4, OreGenMeteoricIron, 10, 30);
    		this.generateOre(8, OreGenNickel, 10, 40);
    		this.generateOre(4, OreGenDesh, 10, 80);
    		this.generateOre(20, OreGenRegolite, 10, 80);
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
