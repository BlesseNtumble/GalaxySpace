package galaxyspace.core.client.gui.book.pages.mechanics;

import asmodeuscore.core.astronomy.gui.book.Page_WithScroll;
import asmodeuscore.core.utils.BookUtils.Book_Cateroies;
import galaxyspace.api.IPage;
import galaxyspace.core.client.gui.book.BookRegister;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

@IPage
public class Page_TemperatureOnPlanet extends Page_WithScroll{

	private Minecraft mc = Minecraft.getMinecraft();
	
	@Override
	public String titlePage() {
		return "temp_on_planet";
	}

	@Override
	public ResourceLocation iconTitle() {
		return null;
	}

	@Override
	public void drawPage(int x, int y, FontRenderer font, int mouseX, int mouseY) {
		super.drawPage(x, y, font, mouseX, mouseY);
		int offsetY = 10;
		if(getScroll() < 2) {
			this.mc.getTextureManager().bindTexture(BookRegister.starTexture);
			this.drawTexturedModalRect(x + 50, y + 50 + offsetY - (getScroll() * 20), 70, 60, 0, 0, 256, 256, false, false, 256, 256);
			
			this.mc.getTextureManager().bindTexture(BookRegister.guiTexture);
			this.drawTexturedModalRect(x + 55, y + 65 + offsetY - (getScroll() * 20), 60, 28, 0, 16, 64, 28, false, false, 256, 256);
			
			this.drawTexturedModalRect(x + 105, y + 73 + offsetY - (getScroll() * 20), 6, 14, 81, 0, 6, 14, false, false, 256, 256);
		}
		String str = GCCoreUtil.translate("book.page.temp_on_planet.text");
		String[] str1 = str.split("!n");
		
		for(int i = getScroll(); i < str1.length; i++)
		{
			String strings = str1[i];
			strings = strings.replace("!c_o", EnumColor.ORANGE + "");
			strings = strings.replace("!c_w", EnumColor.WHITE + "");
			strings = strings.replace("!c_r", EnumColor.RED + "");
			strings = strings.replace("!c_y", EnumColor.YELLOW + "");
			strings = strings.replace("!c_g", EnumColor.BRIGHT_GREEN + "");
			if(y + 60 + (20 * i) > y + 100 + offsetY)
				font.drawSplitString(strings, x + 40, y + 60 + (20 * (i - getScroll())), 260, 0xFFFFFF);
			else
				font.drawSplitString(strings, x + 140, y + 60 + (20 * (i - getScroll())), 260, 0xFFFFFF);

		}
		
	}

	@Override
	public void mouseClick(int mouseX, int mouseY, int mouseButton, int x, int z) {
	}

	@Override
	public String getCategory() {
		return Book_Cateroies.MECHANICS.getName();
	}

	@Override
	public int getMaxScroll() {
		return 4;
	}
}