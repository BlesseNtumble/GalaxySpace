package galaxyspace.core.integration.minetweaker.handlers;

import static galaxyspace.core.integration.minetweaker.GSMinetweakerConfig.getFluidStack;
import static galaxyspace.core.integration.minetweaker.GSMinetweakerConfig.getStack;

import java.util.ArrayList;
import java.util.List;

import galaxyspace.systems.SolarSystem.planets.overworld.recipe.RecyclerRecipes;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.galaxyspace.Recycler")
public class MTHandler_Recycler {

    @ZenMethod
    public static void addRecipe(IItemStack output, IItemStack ingredient, int chance) {
      	MineTweakerAPI.apply(new Add(getStack(output), getStack(ingredient), chance, null));
    }
    
    @ZenMethod
    public static void addRecipe(IItemStack output, IItemStack ingredient, int chance, ILiquidStack liquid) {
      	MineTweakerAPI.apply(new Add(getStack(output), getStack(ingredient), chance, getFluidStack(liquid)));
    }
    
    @ZenMethod
	public static void removeRecipe(IItemStack output) {
    	MineTweakerAPI.apply(new Remove(getStack(output)));
	}
    
    private static class Add implements IUndoableAction {
		
    	private ItemStack result;
    	private ItemStack stack;
    	private int chance;
    	private FluidStack fluidstack;
    	
		public Add(ItemStack stack, ItemStack objects, int chance, FluidStack fluid) {
			this.result = stack;
			this.stack = objects;
			this.chance = chance;
			this.fluidstack = fluid;
		}		
		
		@Override
		public void apply() {
			RecyclerRecipes.recycling().addNewRecipe(stack, result, chance, fluidstack);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			RecyclerRecipes.recycling().removeRecipe(stack);
		}

		@Override
		public String describe() {
			return String.format("Adding Universal Recycler recipe for item %s", stack.getDisplayName());
		}

		@Override
		public String describeUndo() {
			return String.format("Removing Universal Recycler recipe for item %s", stack.getDisplayName());
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
			RecyclerRecipes.recycling().removeRecipe(output);
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

