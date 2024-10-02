package galaxyspace.core.registers.potions;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.prefab.potions.AntiRadiation;
import galaxyspace.core.prefab.potions.Radiation;
import galaxyspace.core.util.GSUtils;
import micdoodle8.mods.galacticraft.core.util.GCLog;
import net.minecraft.potion.Potion;


public class GSPotions {
	
	public static Potion radiation;
	public static Potion antiradiation;
	public static Potion[] potionTypes;
	
	public static void initialize()
	{
		initPotionHook();
		initPotions();
	}
	
	private static void initPotions()
	{
		radiation = new Radiation(GSConfigCore.idSolarRadiation, true, -4502242).setPotionName("potion.radiation");
		antiradiation = new AntiRadiation(GSConfigCore.idAntiRadiation, true, GSUtils.getColor(10, 100, 10)).setPotionName("potion.antiradiation");
	}
	
	private static void initPotionHook()
	{
		for (Field f : Potion.class.getDeclaredFields())
		{
			f.setAccessible(true);

			try
			{
				if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a"))
				{
					Field modfield = Field.class.getDeclaredField("modifiers");
					modfield.setAccessible(true);
					modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);
					potionTypes = (Potion[])f.get(null);
					Potion[] newPotionTypes = new Potion[256];
					System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
					f.set(null, newPotionTypes);
				}
			}
			catch (Exception e)
			{
				GCLog.info("Potion registering failed, please report this to Galaxy Space GitHub");
			}
		}
	}
}
