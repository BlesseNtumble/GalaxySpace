package galaxyspace.api;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IBodies {
	public boolean canRegister();
	public void preInit(FMLPreInitializationEvent event);
	public void init(FMLInitializationEvent event);
	public void postInit(FMLPostInitializationEvent event);
	public void registerRecipes();
	public void registerRender();
	public void registerVariant();
	
	default public void preInitialization(FMLPreInitializationEvent event) {}
	default public void registerItems(RegistryEvent.Register<Item> event) {}
}
