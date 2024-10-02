package galaxyspace.core.client.gui.book;

import org.lwjgl.input.Mouse;

import galaxyspace.GalaxySpace;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public abstract class Page_WithScroll extends Page_Default {

	private static int scroll = 0, maxscroll = 1;
	private static ResourceLocation bookPageTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/tablet.png");
	private Minecraft mc = Minecraft.getMinecraft();
	
	@Override
	public abstract String titlePage();

	@Override
	public abstract ResourceLocation iconTitle();	

	@Override
	public void drawPage(int x, int y, FontRenderer font, int mouseX, int mouseY)
	{
		if(Mouse.hasWheel())
		{
			int dWheel = Mouse.getDWheel();
			
			if(dWheel > 0 && scroll > 0) 
				scroll--;
			else if(dWheel < 0 && scroll < getMaxScroll()) 
				scroll++;			
		}
		
		this.mc.getTextureManager().bindTexture(bookPageTexture);
		drawTexturedModalRect(x + 417, y + 36 , 17, 186, 492, 0, 14, 185, false, false, 512, 256);
			
		drawTexturedModalRect(x + 417, y + 36, 17, 28, 400, 68, 15, 28, false, false, 512, 256);
		//116
		int pos = getMaxScroll() > 0 ? Math.round(116 / getMaxScroll()) : getMaxScroll();

		drawTexturedModalRect(x + 420, y + 58 + (scroll * pos), 15, 28, 429, 68, 14, 28, false, false, 512, 256);
			
		drawTexturedModalRect(x + 419, y + 194, 16, 28, 400, 68, 14, 28, true, true, 512, 256);
			
		
	}

	@Override
	public void mouseClick(int mouseX, int mouseY, int mouseButton, int x, int y)
	{
		if(checkClick(mouseX, mouseY, x+420, y+40, 10, 15))		
			if(scroll > 0) scroll--;
		
		if(checkClick(mouseX, mouseY, x+420, y+198, 10, 20))		
			if(scroll < getMaxScroll()) scroll++;
	}
	
	@Override
	public abstract String getCategory();
	
	public abstract int getMaxScroll();	
	
	public int getScroll()
	{
		return scroll;
	}
	
	private boolean checkClick(int mouseX, int mouseY, int x, int y, int sizeX, int sizeY)
	{		
		return mouseX >= x && mouseX <= x + sizeX && mouseY >= y && mouseY <= y + sizeY;
	}

}
