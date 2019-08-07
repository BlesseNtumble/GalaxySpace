package galaxyspace.core.client.gui.book.pages.mechanics;

import asmodeuscore.AsmodeusCore;
import asmodeuscore.api.space.IBookPage;
import asmodeuscore.core.utils.BookUtils.Book_Cateroies;
import galaxyspace.GalaxySpace;
import galaxyspace.api.IPage;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

@IPage
public class Page_AtmospherePressure implements IBookPage {

	private Minecraft mc = Minecraft.getMinecraft();
	private static ResourceLocation starTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/book/star_bg.png");
	private static ResourceLocation guiTexture = new ResourceLocation(AsmodeusCore.ASSET_PREFIX, "textures/gui/gui.png");
	
	@Override
	public String titlePage() {
		return "atmosphere_pressure";
	}

	@Override
	public ResourceLocation iconTitle() {
		return null;
	}

	@Override
	public void drawPage(int x, int y, FontRenderer font, int mouseX, int mouseY) {
		int offsetY = 10;
		int scroll = 0;
		this.mc.getTextureManager().bindTexture(starTexture);
		this.drawTexturedModalRect(x + 50, y + 50 + offsetY, 70, 60, 0, 0, 256, 256, false, false, 256, 256);
		
		this.mc.getTextureManager().bindTexture(guiTexture);
		this.drawTexturedModalRect(x + 55, y + 70 + offsetY, 60, 80, 0, 0, 64, 64, false, false, 256, 256);
		
		String str = GCCoreUtil.translate("book.page.atmosphere_pressure.text");
		String[] str1 = str.split("!n");
		
		for(int i = scroll; i < str1.length; i++)
		{
			String strings = str1[i];
			strings = strings.replace("!c_o", EnumColor.ORANGE + "");
			strings = strings.replace("!c_w", EnumColor.WHITE + "");
			strings = strings.replace("!c_r", EnumColor.RED + "");
			strings = strings.replace("!c_y", EnumColor.YELLOW + "");
			strings = strings.replace("!c_g", EnumColor.BRIGHT_GREEN + "");
			if(y + 60 + (20 * i) > y + 125 + offsetY)
				font.drawSplitString(strings, x + 40, y + 60 + (20 * (i - scroll)), 260, 0xFFFFFF);
			else
				font.drawSplitString(strings, x + 140, y + 60 + (20 * (i - scroll)), 260, 0xFFFFFF);

		}

	}

	@Override
	public void mouseClick(int mouseX, int mouseY, int mouseButton, int x, int z) {
		
	}
	
	@Override
	public String getCategory() {
		return Book_Cateroies.MECHANICS.getName();
	}

}
