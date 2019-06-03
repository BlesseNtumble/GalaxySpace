package galaxyspace.core.client.gui.book.pages.general;

import org.lwjgl.opengl.GL11;

import asmodeuscore.api.IBookPage;
import asmodeuscore.core.astronomy.gui.book.Page_WithScroll;
import asmodeuscore.core.utils.BookUtils.Book_Cateroies;
import galaxyspace.GalaxySpace;
import galaxyspace.api.IPage;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

@IPage
public class Page_AboutGS implements IBookPage {

	private Minecraft mc = Minecraft.getMinecraft();
	private static final ResourceLocation mainmenu1 = new ResourceLocation(GalaxySpace.ASSET_PREFIX + ":" + "textures/gui/guimenu.png");

	@Override
	public String titlePage() {
		return "about_addon";
	}

	@Override
	public ResourceLocation iconTitle() {
		return null;
	}

	@Override
	public void drawPage(int x, int y, FontRenderer font, int mouseX, int mouseY) {
		
		//super.drawPage(x, y, font, mouseX, mouseY);
		
		GlStateManager.pushMatrix();
		this.mc.getTextureManager().bindTexture(mainmenu1);
		GL11.glColor4f(0.0F, 0.0F, 0.0F, 1);
        this.drawTexturedModalRect(x + 250, y + 40, 290, 50, 440, 5, 290, 50, false, false, 512, 512);
        this.drawTexturedModalRect(x + 120, y + 40, 180, 50, 250, 5, 180, 50, false, false, 512, 512);
                  
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1);
        this.drawTexturedModalRect(x + 117, y + 37, 180, 50, 250, 5, 180, 50, false, false, 512, 512);
        GL11.glColor4f(0.0F, 0.6F, 1.0F, 1);
        this.drawTexturedModalRect(x + 247, y + 37, 290, 50, 440, 5, 290, 50, false, false, 512, 512);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1);
        GlStateManager.popMatrix();

		
		String str = GCCoreUtil.translate("book.page." + titlePage() + ".text");
		this.drawText(str, x, y, 0, font);
		/*String[] str1 = str.split("!n");
		
		for(int i = this.getScroll(); i < str1.length; i++)
		{
			String strings = str1[i];
			strings = strings.replace("!c_o", EnumColor.ORANGE + "");
			strings = strings.replace("!c_w", EnumColor.WHITE + "");
			strings = strings.replace("!c_r", EnumColor.RED + "");
			strings = strings.replace("!c_y", EnumColor.YELLOW + "");
			strings = strings.replace("!c_g", EnumColor.BRIGHT_GREEN + "");
			
			font.drawSplitString(strings, x + 40, y + 90 + (20 * (i - this.getScroll())), 360, 0xFFFFFF);			
		}*/
	}

	@Override
	public void mouseClick(int mouseX, int mouseY, int mouseButton, int x, int y) {
		//super.mouseClick(mouseX, mouseY, mouseButton, x, y);
	}
	
	@Override
	public String getCategory() {
		return Book_Cateroies.GENERAL.getName();
	}
/*
	@Override
	public int getMaxScroll() {
		return 0;
	}*/

}
