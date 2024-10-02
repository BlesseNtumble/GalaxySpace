package galaxyspace.core.integration.minetweaker.handlers;

import static galaxyspace.core.integration.minetweaker.GSMinetweakerConfig.getBlock;
import static galaxyspace.core.integration.minetweaker.GSMinetweakerConfig.getStack;

import java.util.ArrayList;
import java.util.List;

import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityHydroponicBase;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.galaxyspace.HydroponicFarm")
public class MTHandler_HydroponicFarm {

	//addPlant(ItemStack seed, ItemStack product, ItemStack secproduct, int secchance, Block block, int stages, boolean[] rand)
    
    @ZenMethod
    public static void addPlant(IItemStack seed, IItemStack product, IItemStack block, int stages) {
      	MineTweakerAPI.apply(new Add(getStack(seed), getStack(product), getBlock(block), stages));
    }
    
    @ZenMethod
	public static void removeRecipe(IItemStack seed) {
    	MineTweakerAPI.apply(new Remove(getStack(seed)));
	}
    
    private static class Add implements IUndoableAction {
		
    	private ItemStack seed;
    	private ItemStack product;
    	private Block block;
    	private int stages;
    	
		public Add(ItemStack seed, ItemStack result, Block block, int stages) {
			this.seed = seed;
			this.product = result;
			this.block = block;
			this.stages = stages;
		}		
		
		@Override
		public void apply() {
			TileEntityHydroponicBase.addPlant(seed, product, null, 100, block, stages, new boolean[] {false, false});
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			TileEntityHydroponicBase.getSeeds().remove(TileEntityHydroponicBase.getData(seed));
		}

		@Override
		public String describe() {
			return String.format("Adding Hydroponic Farm recipe for seed %s", seed.getDisplayName());
		}

		@Override
		public String describeUndo() {
			return String.format("Removing Hydroponic Farm recipe for seed %s", seed.getDisplayName());
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
			TileEntityHydroponicBase.getSeeds().remove(output);
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


