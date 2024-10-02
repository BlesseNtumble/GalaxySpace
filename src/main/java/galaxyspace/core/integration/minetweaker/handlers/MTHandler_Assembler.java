package galaxyspace.core.integration.minetweaker.handlers;

import static galaxyspace.core.integration.minetweaker.GSMinetweakerConfig.getObjects;
import static galaxyspace.core.integration.minetweaker.GSMinetweakerConfig.getStack;

import java.util.ArrayList;
import java.util.List;

import galaxyspace.systems.SolarSystem.planets.overworld.recipe.AssemberRecipes;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;


@ZenClass("mods.galaxyspace.Assembler")
public class MTHandler_Assembler {

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient[] ingredients) {
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
			AssemberRecipes.instance.addShapelessRecipe(this.stack, this.objects);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			AssemberRecipes.instance.removeRecipe(stack);
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
			AssemberRecipes.instance.removeRecipe(output);
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
