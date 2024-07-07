package galaxyspace.core;

import galaxyspace.core.prefab.potions.AntiRadiation;
import galaxyspace.core.util.GSUtils;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


public class GSPotions {

	public static AntiRadiation antiradiation;
	
	public static void initialize()
	{
		initPotions();
	}
	
	private static void initPotions()
	{
		
		antiradiation = new AntiRadiation(false, GSUtils.getColor(255, 10, 100, 10));		
	
		ForgeRegistries.POTIONS.register(antiradiation);
	}
	
	
}

