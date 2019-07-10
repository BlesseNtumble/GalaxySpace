package galaxyspace.systems.BarnardsSystem.core.registers;

import galaxyspace.core.registers.items.GSItems;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items.ItemBasicBR;
import net.minecraft.item.Item;

public class BRItems {

	public static final Item BASIC = new ItemBasicBR();
	
	public static void initialize() 
	{
		registerItem(BASIC);
	}
	
	private static void oreDictRegistration()
    {
		
    }
	
	private static void registerItem(Item item)
    {
		GSItems.registerItem(item);
    }
}
