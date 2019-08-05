package galaxyspace.core.client.gui.book.pages.blocks;

import asmodeuscore.core.utils.BookUtils.Book_Cateroies;
import galaxyspace.api.IPage;
import galaxyspace.core.client.gui.book.BookRegister.Recipe_Type;
import galaxyspace.core.client.gui.book.pages.Page_WithCraftMatrix;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@IPage
public class Page_Assembly_Machine extends Page_WithCraftMatrix{
	
	@Override
	public String titlePage() {
		return "assembly_machine";
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
		
		String str = GCCoreUtil.translate("book.page." + titlePage() + ".text");
		this.drawText(str, x, y - 40, 0, font);
	}

	@Override
	public Recipe_Type getRecipeType() {
		return Recipe_Type.CRAFTING_TABLE;
	}

	@Override
	public ItemStack[] getRecipe() {
		return new ItemStack[] 
		{ 
			new ItemStack(GCItems.basicItem, 1, 9),	new ItemStack(GCBlocks.aluminumWire, 1, 0),	new ItemStack(GCItems.basicItem, 1, 9),
			new ItemStack(GCItems.basicItem, 1, 13), new ItemStack(Blocks.CRAFTING_TABLE, 1, 0), new ItemStack(GCItems.basicItem, 1, 13),
			new ItemStack(GCBlocks.machineBase, 1, 12),	new ItemStack(GCBlocks.aluminumWire, 1, 0), new ItemStack(GCBlocks.machineBase2, 1, 4)
							
		};		
	}

}
