package galaxyspace.core.client.gui.book;

import org.lwjgl.opengl.GL11;

import galaxyspace.api.IBookPage;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public abstract class Page_Default implements IBookPage{

	@Override
	public abstract String titlePage();

	@Override
	public abstract ResourceLocation iconTitle();	

	@Override
	public abstract void drawPage(int x, int y, FontRenderer font, int mouseX, int mouseY);

	@Override
	public abstract void mouseClick(int mouseX, int mouseY, int mouseButton, int x, int z);

	@Override
	public abstract String getCategory();
	
	public boolean hookBackButton()
	{
		return false;
	}
	
	public void drawText(String text, int x, int y, int pos, FontRenderer font)
	{
		//String str = Utils.translate("book.page." + titlePage() + ".text");
		String[] str1 = text.split("!n");
		
		for(int i = pos; i < str1.length; i++)
		{
			String strings = str1[i];
			strings = strings.replace("!c_o", EnumColor.ORANGE + "");
			strings = strings.replace("!c_w", EnumColor.WHITE + "");
			strings = strings.replace("!c_r", EnumColor.RED + "");
			strings = strings.replace("!c_y", EnumColor.YELLOW + "");
			strings = strings.replace("!c_g", EnumColor.BRIGHT_GREEN + "");
			
			font.drawSplitString(strings, x + 40, y + 90 + (20 * (i - pos)), 360, 0xFFFFFF);			
		}
	}
	
	public void drawTexturedModalRect(int x, int y, int width, int height, int u, int v, int uWidth, int vHeight, boolean invertX, boolean invertY)
    {
        this.drawTexturedModalRect(x, y, width, height, u, v, uWidth, vHeight, invertX, invertY, 512, 512);
    }

	public void drawTexturedModalRect(float x, float y, float width, float height, float u, float v, float uWidth, float vHeight, boolean invertX, boolean invertY, float texSizeX, float texSizeY)
    {
		float zLevel = 0.0F;
		
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        float texModX = 1F / texSizeX;
        float texModY = 1F / texSizeY;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        float height0 = invertY ? 0 : vHeight;
        float height1 = invertY ? vHeight : 0;
        float width0 = invertX ? uWidth : 0;
        float width1 = invertX ? 0 : uWidth;
        tessellator.addVertexWithUV(x, y + height, zLevel, (u + width0) * texModX, (v + height0) * texModY);
        tessellator.addVertexWithUV(x + width, y + height, zLevel, (u + width1) * texModX, (v + height0) * texModY);
        tessellator.addVertexWithUV(x + width, y, zLevel, (u + width1) * texModX, (v + height1) * texModY);
        tessellator.addVertexWithUV(x, y, zLevel, (u + width0) * texModX, (v + height1) * texModY);
        tessellator.draw();
    }
}
