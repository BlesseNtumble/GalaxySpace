package galaxyspace.systems.BarnardsSystem.core.registers;

import galaxyspace.core.GSItems;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items.ItemBasicBR;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items.ItemFoodBR;
import net.minecraft.item.Item;

public class BRItems {

	public static final Item BASIC = new ItemBasicBR();
	public static final Item FOODS = new ItemFoodBR();
	
	public static void initialize() 
	{
		registerItem(BASIC);
		registerItem(FOODS);
	}
	
	private static void oreDictRegistration()
    {
		
    }
	
	private static void registerItem(Item item)
    {
		GSItems.registerItem(item);
    }
}
