package galaxyspace.systems.TauCetiSystem.core;

import galaxyspace.systems.TauCetiSystem.planets.tauceti_f.blocks.TauCeti_F_Blocks;
import galaxyspace.systems.TauCetiSystem.planets.tauceti_f.blocks.TauCeti_F_Corals;
import galaxyspace.systems.TauCetiSystem.planets.tauceti_f.blocks.TauCeti_F_Watergrass;
import galaxyspace.systems.TauCetiSystem.planets.tauceti_f.items.ItemBlocksTauCeti_F;
import galaxyspace.systems.TauCetiSystem.planets.tauceti_f.items.ItemBlocksTauCeti_F_Corals;
import galaxyspace.systems.TauCetiSystem.planets.tauceti_f.items.ItemBlocksTauCeti_F_Dandelions;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class TCBlocks {

	public static final Block TAUCETI_F_BLOCKS = new TauCeti_F_Blocks();
	public static final Block TAUCETI_F_WATERGRASS = new TauCeti_F_Watergrass();
	public static final Block TAUCETI_F_CORALS = new TauCeti_F_Corals();
	
	public static void initialize() 
	{		
		registerBlock(TAUCETI_F_BLOCKS, ItemBlocksTauCeti_F.class);		
		registerBlock(TAUCETI_F_WATERGRASS, ItemBlocksTauCeti_F_Dandelions.class);		
		registerBlock(TAUCETI_F_CORALS, ItemBlocksTauCeti_F_Corals.class);		
	}
		
	public static void oreDictRegistration() 
	{
		
	}
	
	private static void registerBlock(Block block, Class<? extends ItemBlock> itemClass)
    {
        GCBlocks.registerBlock(block, itemClass);
    }
}
