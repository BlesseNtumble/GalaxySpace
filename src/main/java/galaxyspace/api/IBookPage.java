package galaxyspace.api;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public interface IBookPage {

	public String titlePage();
	public ResourceLocation iconTitle();
	public void drawPage(int x, int y, FontRenderer font, int mouseX, int mouseY);
	public void mouseClick(int mouseX, int mouseY, int mouseButton, int x, int z);	
	public String getCategory();		
	public boolean hookBackButton();
}
