package galaxyspace.systems.SolarSystem.planets.haumea.world.gen;

import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.registers.blocks.GSBlocks;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorHaumea extends BiomeDecoratorSpace
{

    private World currentWorld;
    private WorldGenerator iceGen, nitrogenIceGen, oxygenIceGen, cobblestoneGen, aluminumGen;

   
    public BiomeDecoratorHaumea()
    {
    	this.iceGen = new WorldGenMinableMeta(GSBlocks.SURFACE_ICE, 25, 4, true, GSBlocks.HAUMEA_BLOCKS, 0);
    	this.nitrogenIceGen = new WorldGenMinableMeta(GSBlocks.SURFACE_ICE, 6, 1, true, GSBlocks.HAUMEA_BLOCKS, 1);
    	this.oxygenIceGen = new WorldGenMinableMeta(GSBlocks.SURFACE_ICE, 3, 0, true, GSBlocks.HAUMEA_BLOCKS, 1);
    	this.cobblestoneGen = new WorldGenMinableMeta(GSBlocks.HAUMEA_BLOCKS, 15, 2, true, GSBlocks.HAUMEA_BLOCKS, 1);
    	this.aluminumGen = new WorldGenMinableMeta(GSBlocks.HAUMEA_BLOCKS, 6, 3, true, GSBlocks.HAUMEA_BLOCKS, 1);

    }

    @Override
    protected void decorate()
    {
    	this.generateOre(12, iceGen, 60, 90);
    	this.generateOre(12, nitrogenIceGen, 30, 90);
    	this.generateOre(6, oxygenIceGen, 15, 40);
    	this.generateOre(55, cobblestoneGen, 15, 90);
    	this.generateOre(4, aluminumGen, 5, 40);
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
