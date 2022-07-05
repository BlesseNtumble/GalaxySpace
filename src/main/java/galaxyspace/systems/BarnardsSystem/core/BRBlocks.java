package galaxyspace.systems.BarnardsSystem.core;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Blocks;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Dandelions;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Falling_Blocks;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Farmland;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Grass;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Leaves;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Logs;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Ores;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Water_Grass;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items.ItemBlocksBarnarda_C;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items.ItemBlocksBarnarda_C_Dandelions;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items.ItemBlocksBarnarda_C_Falling;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items.ItemBlocksBarnarda_C_Grasses;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items.ItemBlocksBarnarda_C_Leaves;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items.ItemBlocksBarnarda_C_Ores;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class BRBlocks {
	
	public static final Block BARNARDA_C_GRASS = new Barnarda_C_Grass();
	public static final Block BARNARDA_C_BLOCKS = new Barnarda_C_Blocks();
	public static final Block BARNARDA_C_FALLING_BLOCKS = new Barnarda_C_Falling_Blocks();
	public static final Block BARNARDA_C_VIOLET_LOG = new Barnarda_C_Logs("barnarda_c_test_log", 1);
	public static final Block BARNARDA_C_VIOLET_GLOW_LOG = new Barnarda_C_Logs("barnarda_c_test_glow_log", 1);
	public static final Block BARNARDA_C_BIRCH_LOG = new Barnarda_C_Logs("barnarda_c_birch_log", 1);
	
	public static final Block BARNARDA_C_DANDELIONS = new Barnarda_C_Dandelions();
	public static final Block BARNARDA_C_LEAVES = new Barnarda_C_Leaves();
	//public static final Block BARNARDA_C_REEDS = new Barnarda_C_Reeds();
	public static final Block BARNARDA_C_FARMLAND = new Barnarda_C_Farmland();
	public static final Block BARNARDA_C_WATER_GRASS = new Barnarda_C_Water_Grass();
	public static final Block BARNARDA_C_ORES = new Barnarda_C_Ores();
	
	public static void initialize() 
	{	
		registerBlock(BARNARDA_C_GRASS, ItemBlocksBarnarda_C_Grasses.class);
		registerBlock(BARNARDA_C_BLOCKS, ItemBlocksBarnarda_C.class);
		registerBlock(BARNARDA_C_FALLING_BLOCKS, ItemBlocksBarnarda_C_Falling.class);
		registerBlock(BARNARDA_C_VIOLET_LOG, GSItemBlockDesc.class);
		registerBlock(BARNARDA_C_BIRCH_LOG, GSItemBlockDesc.class);
		registerBlock(BARNARDA_C_VIOLET_GLOW_LOG, GSItemBlockDesc.class);
		registerBlock(BARNARDA_C_DANDELIONS, ItemBlocksBarnarda_C_Dandelions.class);
		registerBlock(BARNARDA_C_LEAVES, ItemBlocksBarnarda_C_Leaves.class);
		registerBlock(BARNARDA_C_FARMLAND, GSItemBlockDesc.class);
		registerBlock(BARNARDA_C_WATER_GRASS, GSItemBlockDesc.class);
		registerBlock(BARNARDA_C_ORES, ItemBlocksBarnarda_C_Ores.class);
	}
	
	public static void oreDictRegistration() 
	{		
				
		OreDictionary.registerOre("dirt", new ItemStack(BARNARDA_C_BLOCKS, 1, 0));	
		OreDictionary.registerOre("stone", new ItemStack(BARNARDA_C_BLOCKS, 1, 1));
		OreDictionary.registerOre("sandstone", new ItemStack(BARNARDA_C_BLOCKS, 1, 2));
		OreDictionary.registerOre("dirt", new ItemStack(BARNARDA_C_BLOCKS, 1, 3));
		OreDictionary.registerOre("cobblestone", new ItemStack(BARNARDA_C_BLOCKS, 1, 4));
		OreDictionary.registerOre("plankWood", new ItemStack(BARNARDA_C_BLOCKS, 1, 6));
		
		OreDictionary.registerOre("sand", new ItemStack(BARNARDA_C_FALLING_BLOCKS, 1, 0));
		OreDictionary.registerOre("logWood", new ItemStack(BARNARDA_C_VIOLET_LOG, 1, 0));	
		
		OreDictionary.registerOre("oreCoal", new ItemStack(BARNARDA_C_ORES, 1, 0));	
		OreDictionary.registerOre("oreIron", new ItemStack(BARNARDA_C_ORES, 1, 1));	
		OreDictionary.registerOre("oreGold", new ItemStack(BARNARDA_C_ORES, 1, 2));	
		OreDictionary.registerOre("oreRedstone", new ItemStack(BARNARDA_C_ORES, 1, 3));	
		OreDictionary.registerOre("oreLapis", new ItemStack(BARNARDA_C_ORES, 1, 4));	
		OreDictionary.registerOre("oreDiamond", new ItemStack(BARNARDA_C_ORES, 1, 5));	
		OreDictionary.registerOre("oreSilicon", new ItemStack(BARNARDA_C_ORES, 1, 6));	
		OreDictionary.registerOre("oreCopper", new ItemStack(BARNARDA_C_ORES, 1, 7));	
		OreDictionary.registerOre("oreTin", new ItemStack(BARNARDA_C_ORES, 1, 8));	
		OreDictionary.registerOre("oreAluminum", new ItemStack(BARNARDA_C_ORES, 1, 9));	
		OreDictionary.registerOre("oreQuartz", new ItemStack(BARNARDA_C_ORES, 1, 10));	
		OreDictionary.registerOre("oreCobalt", new ItemStack(BARNARDA_C_ORES, 1, 11));	
		OreDictionary.registerOre("oreNickel", new ItemStack(BARNARDA_C_ORES, 1, 12));	
		
	}
	
	public static void registerBlock(Block block, Class<? extends ItemBlock> itemClass)
    {
        GCBlocks.registerBlock(block, itemClass);
    }
}
