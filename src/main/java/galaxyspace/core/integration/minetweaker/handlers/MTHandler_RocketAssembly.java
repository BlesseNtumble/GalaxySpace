package galaxyspace.core.integration.minetweaker.handlers;

import galaxyspace.systems.SolarSystem.planets.overworld.recipe.RocketAssemblyRecipes;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

import static galaxyspace.core.integration.minetweaker.GSMinetweakerConfig.getObjects;
import static galaxyspace.core.integration.minetweaker.GSMinetweakerConfig.getStack;



@ZenClass("mods.galaxyspace.RocketAssembly")
public class MTHandler_RocketAssembly {

	/**
	 * Index:
	 *  1 - Cone
	 *  2 - Body Right
	 *  3 - Body Left
	 *  4 - Engine
	 *  5 - Booster Top
	 *  6 - Booster Bottom
	 *  7 - Vane Top
	 *  8 - Vane Bottom
	 */
	public static List<List<ItemStack>> material = new ArrayList<>(8);
	static {
		for (int i = 0; i < 8; i++) {
			material.add(new ArrayList<>());
		}
	}
	public static boolean itemIsMaterial(ItemStack s) {
		for(List<ItemStack> list : material )
		{
			for(ItemStack stack : list)
			{
				if(stack.isItemEqual(s)) {
						return true;
				}
			}
		}
		return false;
	}
	@ZenMethod
    public static void addRecipe(IItemStack output, IIngredient[] ingredients) {
			if(ingredients.length != 0) {
				for (int i = 0; i < ingredients.length; i++) {
					material.get(i).add((ItemStack)getObjects(ingredients)[i]);
				}
		}
        MineTweakerAPI.apply(new Add(getStack(output), getObjects(ingredients)));
    }
    
    @ZenMethod
	public static void removeRecipe(IItemStack output) {
		MineTweakerAPI.apply(new Remove(getStack(output)));
	}
    
    private static class Add implements IUndoableAction {
		
    	private ItemStack stack;
    	private Object[] objects;
    	
		public Add(ItemStack stack, Object[] objects) {
			this.stack = stack;
			this.objects = objects;

		}

		@Override
		public void apply() {
			System.out.println("Added rocket assembly recipe for: " + this.stack.getDisplayName());
			RocketAssemblyRecipes.addShapelessRecipe(this.stack, this.objects);

		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			RocketAssemblyRecipes.instance.removeRecipe(stack);
		}

		@Override
		public String describe() {
			return String.format("Adding Assembly Machine recipe for item %s", stack.getDisplayName());
		}

		@Override
		public String describeUndo() {
			return String.format("Removing Assembly Machine recipe for item %s", stack.getDisplayName());
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}  
	
	private static class Remove implements IUndoableAction {

		private final ItemStack output;
		List<ItemStack> removed = new ArrayList<ItemStack>();
		
		public Remove(ItemStack stack) {
			output = stack;
		}

		@Override
		public void apply() {
			RocketAssemblyRecipes.instance.removeRecipe(output);
		}

		@Override
		public boolean canUndo() {
			return false;
		}

		@Override
		public void undo() {
			
		}

		@Override
		public String describe() {
			return String.format("Removing all recipes for %s", output.getUnlocalizedName());
		}

		@Override
		public String describeUndo() {
			return String.format("Re-adding previously removed recipes for %s", output.getUnlocalizedName());
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

}
