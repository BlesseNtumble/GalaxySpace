package galaxyspace.core.client.gui.book.pages.mechanics;

import asmodeuscore.api.IBookPage;
import asmodeuscore.core.utils.BookUtils.Book_Cateroies;
import galaxyspace.api.IPage;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

@IPage
public class Page_CorrosiveAtmosphere implements IBookPage{

	private Minecraft mc = Minecraft.getMinecraft();

	@Override
	public String titlePage() {
		return "corrosive_atmosphere";
	}
	@Override
	public ResourceLocation iconTitle() {
		return null;
	}

	@Override
	public void drawPage(int x, int y, FontRenderer font, int mouseX, int mouseY) {
		this.drawText(GCCoreUtil.translate("book.page.corrosive_atmosphere.text"), x, y - 40, 0, font);
	}

	@Override
	public void mouseClick(int mouseX, int mouseY, int mouseButton, int x, int z) {
		
	}

	@Override
	public String getCategory() {
		return Book_Cateroies.MECHANICS.getName();
	}
}
