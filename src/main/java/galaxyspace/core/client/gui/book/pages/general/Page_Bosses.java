package galaxyspace.core.client.gui.book.pages.general;

import asmodeuscore.api.space.IBookPage;
import asmodeuscore.core.utils.BookUtils.Book_Cateroies;
import galaxyspace.api.IPage;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

@IPage
public class Page_Bosses implements IBookPage{

	private enum Bosses {
		BLAZE,
		GHAST
	};
	
	private Bosses selected = null;
	
	@Override
	public String titlePage() {
		return "bosses";
	}

	@Override
	public ResourceLocation iconTitle() {
		return null;
	}

	@Override
	public void drawPage(int x, int y, FontRenderer font, int mouseX, int mouseY) {
	}

	@Override
	public void mouseClick(int mouseX, int mouseY, int mouseButton, int x, int z) {
	}

	@Override
	public String getCategory() {
		return Book_Cateroies.GENERAL.getName();
	}

}
