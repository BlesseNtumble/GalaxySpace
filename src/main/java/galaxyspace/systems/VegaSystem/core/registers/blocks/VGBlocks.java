package galaxyspace.systems.VegaSystem.core.registers.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import galaxyspace.systems.VegaSystem.planets.vegaB.blocks.VegaBBlockGrunt;
import galaxyspace.systems.VegaSystem.planets.vegaB.blocks.VegaBBlockSubGrunt;


public class VGBlocks {
		// VegaB ------------------
		public static VegaBBlockGrunt VegaBGrunt;
		public static VegaBBlockSubGrunt VegaBSubGrunt;
		// --------------------------
		
		
		public static void initialize() 
		{

			VegaBGrunt = new VegaBBlockGrunt();
			VegaBSubGrunt = new VegaBBlockSubGrunt();
			
		   
			GameRegistry.registerBlock(VegaBGrunt, "vegabgrunt");
			GameRegistry.registerBlock(VegaBSubGrunt, "vegabsubgrunt");
						
			oreDictRegistration();
		   
	   }
	   
	   public static void oreDictRegistration()
	    {

	    }
	}
