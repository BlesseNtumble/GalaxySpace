package galaxyspace.core.integration.minetweaker;

import galaxyspace.GalaxySpace;
import galaxyspace.core.integration.minetweaker.handlers.MTHandler_Assembler;
import galaxyspace.core.integration.minetweaker.handlers.MTHandler_HydroponicFarm;
import galaxyspace.core.integration.minetweaker.handlers.MTHandler_Recycler;
import galaxyspace.core.integration.minetweaker.handlers.MTHandler_RocketAssembly;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class GSMinetweakerConfig {

	public static void loadConfig() {
		GalaxySpace.info("Initialized MineTweaker Integration");
		
		MineTweakerAPI.registerClass(MTHandler_Assembler.class);
		MineTweakerAPI.registerClass(MTHandler_Recycler.class);
		MineTweakerAPI.registerClass(MTHandler_HydroponicFarm.class);
		MineTweakerAPI.registerClass(MTHandler_RocketAssembly.class);
	}
	
	public static ItemStack getStack(IItemStack stack) {
		return MineTweakerMC.getItemStack(stack);
	} 
	
	public static FluidStack getFluidStack(ILiquidStack liquid)	{
		return MineTweakerMC.getLiquidStack(liquid);
	}
	
	public static Block getBlock(IItemStack block)	{
		return MineTweakerMC.getBlock(block);
	}
		
	public static String getString(IOreDictEntry entry) {
		return ((IOreDictEntry) entry).getName();
	}
	
	public static Object getObject(IIngredient stack) {
		if (stack == null)	return null;
		
		if (stack instanceof IOreDictEntry) 
			return getString((IOreDictEntry) stack);
		else if (stack instanceof IItemStack) 
			return getStack((IItemStack) stack);

		return null;		
	}

	public static Object[] getObjects(IIngredient[] ingredient) {
		if (ingredient == null)	return null;
		
		Object[] output = new Object[ingredient.length];
		for (int i = 0; i < ingredient.length; i++) {
			if (ingredient[i] != null) {
				output[i] = getObject(ingredient[i]);
			} else
				output[i] = "";
		}

		return output;		
	}
	
	public static ItemStack[] getStacks(IItemStack[] stack) {
		ItemStack[] output = new ItemStack[stack.length];
		for (int i = 0; i < stack.length; i++) {
			if (stack[i] != null) {
				output[i] = MineTweakerMC.getItemStack(stack[i]);
			} else output[i] = null;
		}
		return output;
	} 
	
}
