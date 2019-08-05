package galaxyspace.core.client.gui.book.pages.blocks;

import asmodeuscore.core.utils.BookUtils.Book_Cateroies;
import galaxyspace.api.IPage;
import galaxyspace.core.client.gui.book.BookRegister.Recipe_Type;
import galaxyspace.core.client.gui.book.pages.Page_WithCraftMatrix;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.GCItems;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@IPage
public class Page_Fuel_Generator extends Page_WithCraftMatrix {

	@Override
	public String titlePage() {
		return "fuel_generator";
	}

	@Override
	public ResourceLocation iconTitle() {
		return null;
	}

	@Override
	public int getMaxScroll() {
		return 0;
	}

	@Override
	public String getCategory() {
		return Book_Cateroies.BLOCKS.getName();
	}

	@Override
	public void drawPage(int x, int y, FontRenderer font, int mouseX, int mouseY)
	{
		super.drawPage(x, y, font, mouseX, mouseY);
	}
	
	@Override
	public ItemStack[] getRecipe() {
		return new ItemStack[] 
		{ 
			new ItemStack(GCItems.canister, 1, 1),	new ItemStack(Blocks.GLASS_PANE, 1, 0),	new ItemStack(GCItems.canister, 1, 1),
			new ItemStack(GCItems.canister, 1, 1), new ItemStack(GCBlocks.machineBase, 1, 0), new ItemStack(GCItems.canister, 1, 1),
			new ItemStack(GCItems.basicItem, 1, 9),	new ItemStack(GCItems.basicItem, 1, 9), new ItemStack(GCItems.basicItem, 1, 9)						
		};
	}

	@Override
	public Recipe_Type getRecipeType() {
		return Recipe_Type.CRAFTING_TABLE;
	}

}
