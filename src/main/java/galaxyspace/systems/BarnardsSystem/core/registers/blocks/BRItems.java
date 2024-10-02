package galaxyspace.systems.BarnardsSystem.core.registers.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import galaxyspace.GalaxySpace;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.items.ItemBasicBR;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.items.ItemFoodBR;
import net.minecraft.item.Item;

public class BRItems {

	public static final Item Basic = new ItemBasicBR();
	public static final Item Food = new ItemFoodBR();
	
	public static void initialize() 
	{
		registerItem(Food);
	}
	
	private static void registerItem(Item item)
    {
		GameRegistry.registerItem(item, item.getUnlocalizedName(), GalaxySpace.MODID);
    }
}
