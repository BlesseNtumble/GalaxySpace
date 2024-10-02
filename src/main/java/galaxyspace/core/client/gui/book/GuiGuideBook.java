package galaxyspace.core.client.gui.book;

import java.util.Map;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.api.IBookPage;
import galaxyspace.core.util.BookUtils;
import galaxyspace.core.util.BookUtils.Book_Cateroies;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class GuiGuideBook extends GuiScreen{

	private final int bookImageHeight = 250;
	private final int bookImageWidth = 450;
	
	private static ResourceLocation bookPageTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/tablet.png");
	 
	enum Mode {
		CATEGORIES,
		PAGES,
		TEXT
	}
	
	private Mode mode = Mode.CATEGORIES;	
	private String category = Book_Cateroies.GENERAL.getName();
	private static IBookPage page;
	private int maxX = 0;
	
	public GuiGuideBook()
	{		
	}
	
	public GuiGuideBook(Mode mode, String category, IBookPage page)
	{
		this.mode = mode;
		this.category = category;
		this.page = page;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(bookPageTexture);
		
		int offsetFromScreenLeft = (width - bookImageWidth ) / 2;
	    int offsetFromScreenTop = (height - bookImageHeight ) / 2;
	    
	    drawTexturedModalRect(offsetFromScreenLeft, offsetFromScreenTop, 450, 256, 0, 0, 400, 256, false, false, 512, 256);
	    
    	int posY = 0;
    	if(mouseX >= offsetFromScreenLeft + 25 && mouseX < offsetFromScreenLeft + 58 && mouseY >= offsetFromScreenTop + 18 && mouseY <= offsetFromScreenTop + 32)
			posY = 17;
    	if(mode != Mode.CATEGORIES) {
    		drawTexturedModalRect(offsetFromScreenLeft + 25, offsetFromScreenTop + 18, 30, 15, 400, posY, 20, 15, false, false, 512, 256);
    		drawTexturedModalRect(offsetFromScreenLeft + 40, offsetFromScreenTop + 18, 30, 15, 467, posY, 20, 15, false, false, 512, 256);
    		this.fontRendererObj.drawString(GCCoreUtil.translate("book.button.back.name"), offsetFromScreenLeft + 39, offsetFromScreenTop + 22, 0xFFFFFF);
    	}
    	
	    if(mode == Mode.CATEGORIES)
	    {
	    	int i = 0, offsetX = 65, offsetY = 0;	
	    	
	    	for(Map.Entry<String, ResourceLocation> entry : BookUtils.GUIDE_BOOK_CATEGORIES.entrySet())
	    	{
	    		
	    		
	    		offsetY = i / 6 * 60;		    		
	    		int k = i > i % 6 ? i - (6 * (i / 6)) : i;	    		
	    		offsetX = 65 * k; 
	    		
	    		
	    		this.mc.getTextureManager().bindTexture(bookPageTexture);
	    		drawTexturedModalRect(offsetFromScreenLeft + 42 + offsetX, offsetFromScreenTop + 52 + offsetY, 36, 35, 460, 34, 22, 24, false, false, 512, 256);
	    		 
	    		this.mc.getTextureManager().bindTexture(entry.getValue());
	    		drawTexturedModalRect(offsetFromScreenLeft + 51 + offsetX, offsetFromScreenTop + 60 + offsetY, 16, 16, 0, 0, 16, 16, false, false, 16, 16);
	    		   
	    		this.fontRendererObj.drawSplitString(GCCoreUtil.translate("book.category." + entry.getKey() + ".name"), offsetFromScreenLeft + offsetX + 60 - (this.fontRendererObj.getStringWidth(entry.getKey()) / 2), offsetFromScreenTop + 85 + offsetY, 50, 0xFFFFFF);
	    			    		
	    		i++;	    			    		
	    	}	    	
	    }
	    else if(mode == Mode.PAGES)
	    {
	    	this.mc.getTextureManager().bindTexture(bookPageTexture);
	    	
    		this.fontRendererObj.drawString(GCCoreUtil.translate("book.category." + category + ".name"), width / 2 - (this.fontRendererObj.getStringWidth(category) / 2), offsetFromScreenTop + 20, 0xFFFFFF);

			maxX = 0;
			for(Map.Entry<IBookPage, String> entry : BookUtils.GUIDE_BOOK_PAGES.entrySet())
	    	{
				int currX = this.fontRendererObj.getStringWidth(GCCoreUtil.translate("book.page." + entry.getKey().titlePage() + ".name"));
    			maxX = maxX < currX ? currX : maxX;
	    	}
			
			
    		int i = 0, offsetX = 21;
	    	for(Map.Entry<IBookPage, String> entry : BookUtils.GUIDE_BOOK_PAGES.entrySet())
	    	{    		
    			
	    		if(entry.getValue().equals(this.category)) {
	    			
	    			this.mc.getTextureManager().bindTexture(bookPageTexture);
	    			posY = 0;
	    			if(mouseX >= offsetFromScreenLeft + 44 && mouseX < offsetFromScreenLeft + 40 + 25 + maxX && mouseY >= offsetFromScreenTop + 41 + (offsetX * i) && mouseY <= offsetFromScreenTop + 41 + (offsetX * i) + 16)
	    				posY = 17;
	    			drawTexturedModalRect(offsetFromScreenLeft + 40, offsetFromScreenTop + 41 + (offsetX * i), 25, 16, 400, posY, 20, 15, false, false, 512, 256);
	    			drawTexturedModalRect(offsetFromScreenLeft + 40 + 16, offsetFromScreenTop + 41 + (offsetX * i), maxX, 16, 410, posY, 70, 15, false, false, 512, 256);
	    			drawTexturedModalRect(offsetFromScreenLeft + 40 + maxX, offsetFromScreenTop + 41 + (offsetX * i), 25, 16, 467, posY, 20, 15, false, false, 512, 256);
	    			
	    			
	    			//drawTexturedModalRect(offsetFromScreenLeft + 40, offsetFromScreenTop + 41 + (offsetX * i), 120, 16, 400, posY, 96, 15, false, false, 512, 256);
    	    		
	    			if(entry.getKey().iconTitle() != null)
	    			{
	    				this.mc.getTextureManager().bindTexture(bookPageTexture);
	    				drawTexturedModalRect(offsetFromScreenLeft + 20, offsetFromScreenTop + 41 + (offsetX * i), 21, 21, 461, 35, 18, 21, false, false, 512, 256);
	    	    		
	    				
	    				this.mc.getTextureManager().bindTexture(entry.getKey().iconTitle());
	    				drawTexturedModalRect(offsetFromScreenLeft + 25, offsetFromScreenTop + 45 + (offsetX * i), 12, 12, 0, 0, 16, 16, false, false, 16, 16);
	    	    		
	    			}
	    			this.fontRendererObj.drawString(GCCoreUtil.translate("book.page." + entry.getKey().titlePage() + ".name"), offsetFromScreenLeft + 52, offsetFromScreenTop + 45 + (offsetX * i), 0xFFFFFF);
        			i++;
	    		}
    		}
	    	
	    	if(i == 0)
	    		this.fontRendererObj.drawString("Whoops! Empty category! We will work on pages from this category!", offsetFromScreenLeft + 52, offsetFromScreenTop + 45 + (offsetX * i), 0xFFFFFF);
			
	    		
	    }
	    else if(mode == Mode.TEXT)
	    {
	    	this.fontRendererObj.drawString(GCCoreUtil.translate("book.page." + page.titlePage() + ".name"), width / 2 - this.fontRendererObj.getStringWidth(category) - 10, offsetFromScreenTop + 20, 0xFFFFFF);
    		
	    	page.drawPage(offsetFromScreenLeft, offsetFromScreenTop, this.fontRendererObj, mouseX, mouseY);
	    	//this.fontRenderer.drawString(page.text(), offsetFromScreenLeft + 40, offsetFromScreenTop + 45, 0xFFFFFF);
			
	    }
	    this.fontRendererObj.drawString(EnumColor.ORANGE + "Book in Beta", offsetFromScreenLeft + 370, offsetFromScreenTop + 22, 0xFFFFFF);

	    //this.fontRenderer.drawString("X: " + mouseX + " | Y: " + mouseY, mouseX - 25, mouseY + 10, 0xFFFFFF);
		
    }

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
		int offsetFromScreenLeft = (width - bookImageWidth ) / 2;
	    int offsetFromScreenTop = (height - bookImageHeight ) / 2;
	    
	    if(mode == Mode.CATEGORIES)
	    {
	    	int i = 0, offsetX = 65, offsetY = 0;	
	    	for(Map.Entry<String, ResourceLocation> entry : BookUtils.GUIDE_BOOK_CATEGORIES.entrySet())
	    	{
	    		offsetY = i / 6 * 60;		    		
	    		int k = i > i % 6 ? i - (6 * (i / 6)) : i;	    		
	    		offsetX = 65 * k; 
	    		
	    		if(mouseX >= offsetFromScreenLeft + 30 + offsetX && mouseX <= offsetFromScreenLeft + 70 + offsetX + 10
	    				&& mouseY >= offsetFromScreenTop + 50 + offsetY && mouseY <= offsetFromScreenTop + 100 + offsetY)
	    		{
	    			category = entry.getKey();
	    			mode = Mode.PAGES;	    			
	    		}
	    		i++;
	    	}
	    }
	    else if(mode == Mode.PAGES)
	    {
	    	int i = 0, offsetX = 21;
	    	for(Map.Entry<IBookPage, String> entry : BookUtils.GUIDE_BOOK_PAGES.entrySet())
	    	{
	    		if(entry.getValue().equals(category)) {
	    				    			
	    			if(mouseX >= offsetFromScreenLeft + 44 && mouseX < offsetFromScreenLeft + 40 + 25 + maxX && mouseY >= offsetFromScreenTop + 40 + (offsetX * i) && mouseY <= offsetFromScreenTop + 56 + (offsetX * i))
		    		{
		    			try {
							page = entry.getKey().getClass().newInstance();
						} catch (InstantiationException | IllegalAccessException e) {
							e.printStackTrace();
						}
		    			mode = Mode.TEXT;
		    		}
		    		i++;
	    		}
	    	}
	    	
	    	//RETURN BUTTON
	    	if(mouseX >= offsetFromScreenLeft + 25 && mouseX < offsetFromScreenLeft + 58 && mouseY >= offsetFromScreenTop + 18 && mouseY <= offsetFromScreenTop + 32)
				
    		{
	    		mode = Mode.CATEGORIES;
    		}
	    	
	    }
	    else if(mode == Mode.TEXT)
	    {
	    	page.mouseClick(mouseX, mouseY, mouseButton, offsetFromScreenLeft, offsetFromScreenTop);
    		
	    	//RETURN BUTTON
	    	if(mouseX >= offsetFromScreenLeft + 25 && mouseX < offsetFromScreenLeft + 58 && mouseY >= offsetFromScreenTop + 18 && mouseY <= offsetFromScreenTop + 32)	
    		{
	    		if(!page.hookBackButton())
	    			mode = Mode.PAGES;
    		}
	    }
    }
	
	@Override
	public boolean doesGuiPauseGame()
    {
        return false;
    }
	
	public void drawTexturedModalRect(int x, int y, int width, int height, int u, int v, int uWidth, int vHeight, boolean invertX, boolean invertY)
    {
        this.drawTexturedModalRect(x, y, width, height, u, v, uWidth, vHeight, invertX, invertY, 512, 512);
    }

	public void drawTexturedModalRect(float x, float y, float width, float height, float u, float v, float uWidth, float vHeight, boolean invertX, boolean invertY, float texSizeX, float texSizeY)
    {
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
        tessellator.addVertexWithUV(x, y + height, this.zLevel, (u + width0) * texModX, (v + height0) * texModY);
        tessellator.addVertexWithUV(x + width, y + height, this.zLevel, (u + width1) * texModX, (v + height0) * texModY);
        tessellator.addVertexWithUV(x + width, y, this.zLevel, (u + width1) * texModX, (v + height1) * texModY);
        tessellator.addVertexWithUV(x, y, this.zLevel, (u + width0) * texModX, (v + height1) * texModY);
        tessellator.draw();
    }
}
