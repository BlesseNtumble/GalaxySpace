package galaxyspace.core.client.gui.book.pages.blocks;

import asmodeuscore.core.utils.BookUtils.Book_Cateroies;
import galaxyspace.api.IPage;
import galaxyspace.core.client.gui.book.BookRegister;
import galaxyspace.core.client.gui.book.pages.Page_AssemblerCraft;
import galaxyspace.core.registers.blocks.GSBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@IPage
public class Page_Modern_Solar_Minipanel extends Page_AssemblerCraft {

	@Override
	public String titlePage() {
		return getItem().getUnlocalizedName() + ".name";
	}
	
	@Override
	public boolean rawTitle()
	{
		return true;
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
	public ItemStack getItem() {		
		return new ItemStack(GSBlocks.MODERN_SINGLE_SOLARPANEL);
	}

	@Override
	public Recipe_Type getRecipeType() {
		return BookRegister.ASSEMBLER;
	}

}
