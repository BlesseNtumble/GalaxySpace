package galaxyspace.systems.TCetiSystem.core.registers.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import galaxyspace.systems.TCetiSystem.planets.tcetiF.blocks.TCetiFBlockDandelions;
import galaxyspace.systems.TCetiSystem.planets.tcetiF.blocks.TCetiFBlocks;
import galaxyspace.systems.TCetiSystem.planets.tcetiF.items.ItemBlockTCetiFDandelions;
import galaxyspace.systems.TCetiSystem.planets.tcetiF.items.ItemBlocksTCetiF;


public class TCBlocks {
		// TCetiE ------------------
		public static TCetiFBlocks TCetiEBlocks;
		public static TCetiFBlockDandelions TCetiEDandelions;
		// --------------------------
		
		
		public static void initialize() 
		{

			TCetiEBlocks = new TCetiFBlocks();
			TCetiEDandelions = new TCetiFBlockDandelions();
		
		   
			GameRegistry.registerBlock(TCetiEBlocks, ItemBlocksTCetiF.class, "tcetieblocks");
			GameRegistry.registerBlock(TCetiEDandelions, ItemBlockTCetiFDandelions.class, "tcetiedandelions");
						
			oreDictRegistration();
		   
	   }
	   
	   public static void oreDictRegistration()
	    {

	    }
	}
