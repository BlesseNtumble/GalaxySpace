package galaxyspace.systems.TauCetiSystem.core;

import galaxyspace.systems.TauCetiSystem.planets.tauceti_f.blocks.TauCeti_F_Blocks;
import galaxyspace.systems.TauCetiSystem.planets.tauceti_f.items.ItemBlocksTauCeti_F;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class TCBlocks {

	public static final Block TAUCETI_F_BLOCKS = new TauCeti_F_Blocks();
	
	public static void initialize() 
	{		
		registerBlock(TAUCETI_F_BLOCKS, ItemBlocksTauCeti_F.class);		
	}
		
	public static void oreDictRegistration() 
	{
		
	}
	
	private static void registerBlock(Block block, Class<? extends ItemBlock> itemClass)
    {
        GCBlocks.registerBlock(block, itemClass);
    }
}
