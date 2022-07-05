package galaxyspace.systems.ACentauriSystem.core;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.blocks.Proxima_B_Blocks;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.blocks.Proxima_B_Dandelions;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.blocks.Proxima_B_Grass;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.blocks.Proxima_B_Logs;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.items.ItemBlocksProxima_B;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.items.ItemBlocksProxima_B_Dandelions;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ACBlocks {
	
	public static final Block PROXIMA_B_BLOCKS = new Proxima_B_Blocks().setHardness(3.0F);
	public static final Block PROXIMA_B_LOG_1 = new Proxima_B_Logs("proxima_b_log_1");
	public static final Block PROXIMA_B_LOG_2 = new Proxima_B_Logs("proxima_b_log_2");
	public static final Block PROXIMA_B_MUSHROOM_LOG = new Proxima_B_Logs("proxima_b_mushroom_log");
	public static final Block PROXIMA_B_DANDELIONS = new Proxima_B_Dandelions();
	public static final Block PROXIMA_B_GRASS = new Proxima_B_Grass();
	
	public static void initialize() 
	{	
		registerBlock(PROXIMA_B_BLOCKS, ItemBlocksProxima_B.class);
		registerBlock(PROXIMA_B_LOG_1, GSItemBlockDesc.class);
		registerBlock(PROXIMA_B_LOG_2, GSItemBlockDesc.class);
		registerBlock(PROXIMA_B_MUSHROOM_LOG, GSItemBlockDesc.class);
		registerBlock(PROXIMA_B_GRASS, GSItemBlockDesc.class);
		registerBlock(PROXIMA_B_DANDELIONS, ItemBlocksProxima_B_Dandelions.class);
	}
	
	public static void oreDictRegistration() 
	{
		OreDictionary.registerOre("oreGold", new ItemStack(PROXIMA_B_BLOCKS, 1, 5));
		OreDictionary.registerOre("oreTin", new ItemStack(PROXIMA_B_BLOCKS, 1, 6));
		OreDictionary.registerOre("oreCopper", new ItemStack(PROXIMA_B_BLOCKS, 1, 7));
		OreDictionary.registerOre("oreCoal", new ItemStack(PROXIMA_B_BLOCKS, 1, 8));
		OreDictionary.registerOre("oreSilicon", new ItemStack(PROXIMA_B_BLOCKS, 1, 9));
		OreDictionary.registerOre("oreDiamond", new ItemStack(PROXIMA_B_BLOCKS, 1, 10));
	}
	
	public static void registerBlock(Block block, Class<? extends ItemBlock> itemClass)
    {
        GCBlocks.registerBlock(block, itemClass);
    }
}
