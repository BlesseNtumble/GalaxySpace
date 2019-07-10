package galaxyspace.systems.BarnardsSystem.core.registers;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Blocks;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Dandelions;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Falling_Blocks;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Farmland;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Grass;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Leaves;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Logs;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Reeds;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items.ItemBlocksBarnarda_C;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items.ItemBlocksBarnarda_C_Dandelions;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items.ItemBlocksBarnarda_C_Grasses;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items.ItemBlocksBarnarda_C_Leaves;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class BRBlocks {
	
	public static final Block BARNARDA_C_GRASS = new Barnarda_C_Grass();
	public static final Block BARNARDA_C_BLOCKS = new Barnarda_C_Blocks().setHardness(3.0F);
	public static final Block BARNARDA_C_TEST_LOG = new Barnarda_C_Logs("barnarda_c_test_log", 2);
	public static final Block BARNARDA_C_TEST_GLOW_LOG = new Barnarda_C_Logs("barnarda_c_test_glow_log", 2);
	public static final Block BARNARDA_C_DANDELIONS = new Barnarda_C_Dandelions();
	public static final Block BARNARDA_C_LEAVES = new Barnarda_C_Leaves();
	//public static final Block BARNARDA_C_REEDS = new Barnarda_C_Reeds();
	public static final Block BARNARDA_C_FARMLAND = new Barnarda_C_Farmland();
	
	public static void initialize() 
	{	
		registerBlock(BARNARDA_C_GRASS, ItemBlocksBarnarda_C_Grasses.class);
		registerBlock(BARNARDA_C_BLOCKS, ItemBlocksBarnarda_C.class);
		registerBlock(BARNARDA_C_TEST_LOG, GSItemBlockDesc.class);
		registerBlock(BARNARDA_C_TEST_GLOW_LOG, GSItemBlockDesc.class);
		registerBlock(BARNARDA_C_DANDELIONS, ItemBlocksBarnarda_C_Dandelions.class);
		registerBlock(BARNARDA_C_LEAVES, ItemBlocksBarnarda_C_Leaves.class);
		registerBlock(BARNARDA_C_FARMLAND, GSItemBlockDesc.class);
	}
	
	public static void oreDictRegistration() 
	{		
	}
	
	public static void registerBlock(Block block, Class<? extends ItemBlock> itemClass)
    {
        GCBlocks.registerBlock(block, itemClass);
    }
}
