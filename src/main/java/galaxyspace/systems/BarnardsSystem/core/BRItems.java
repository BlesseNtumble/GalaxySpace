package galaxyspace.systems.BarnardsSystem.core;

import galaxyspace.core.GSItems;
import galaxyspace.core.prefab.items.ItemDoorGS;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items.ItemBasicBR;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items.ItemFoodBR;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class BRItems {

	public static final Item BASIC = new ItemBasicBR();
	public static final Item FOODS = new ItemFoodBR();
	public static final Item VIOLET_DOOR = new ItemDoorGS("barnarda_c_violet_door", BRBlocks.BARNARDA_C_VIOLET_DOOR);
	
	public static void initialize() 
	{
		registerItem(BASIC);
		registerItem(FOODS);
		registerItem(VIOLET_DOOR);
	}
	
	public static void oreDictRegistration()
    {

		OreDictionary.registerOre("slimeball", new ItemStack(BASIC, 1, 2));

		BRBlocks.BARNARDA_C_VIOLET_DOOR.setDoorItem(VIOLET_DOOR);
    }
	
	private static void registerItem(Item item)
    {
		GSItems.registerItem(item);
    }
}
