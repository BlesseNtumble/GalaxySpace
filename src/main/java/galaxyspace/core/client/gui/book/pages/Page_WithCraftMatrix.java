package galaxyspace.core.client.gui.book.pages;

import org.lwjgl.opengl.GL11;

import asmodeuscore.AsmodeusCore;
import asmodeuscore.core.astronomy.gui.book.Page_WithScroll;
import galaxyspace.GalaxySpace;
import galaxyspace.core.client.gui.book.BookRegister.Recipe_Type;
import micdoodle8.mods.galacticraft.core.util.ColorUtil;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class Page_WithCraftMatrix extends Page_WithScroll {

	private Minecraft mc = Minecraft.getMinecraft();
	private static final ResourceLocation guiTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/base_gui_1.png");
	private static final ResourceLocation bookPageTexture = new ResourceLocation(AsmodeusCore.ASSET_PREFIX, "textures/gui/tablet.png");
	
	@Override
	public abstract String titlePage();

	@Override
	public abstract ResourceLocation iconTitle();

	@Override
	public abstract int getMaxScroll();
	
	@Override
	public abstract String getCategory();
	
	public abstract ItemStack[] getRecipe();
	
	public abstract Recipe_Type getRecipeType();	
	
	@Override
	public void drawPage(int x, int y, FontRenderer font, int mouseX, int mouseY)
	{
		if(this.getScroll() > 0)
			super.drawPage(x, y, font, mouseX, mouseY);
		
		this.mc.renderEngine.bindTexture(this.guiTexture);	
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		if(getRecipe().length > 0)
		{
			for(int i = 0; i < 3; i++)
				for(int k = 0; k < 3; k++) {
					this.drawTexturedModalRect(x + 308 + (k * 25), y + 70 + (i * 25), 20, 20, 192, 196, 20, 20, false, false, 256, 256);
				}
			
			
			int z = 0, j = 0;
						
			for(ItemStack s : getRecipe())
			{
				//GalaxySpace.debug(s.getDisplayName() + "");
				
				RenderHelper.enableGUIStandardItemLighting();
                this.mc.getRenderItem().renderItemAndEffectIntoGUI(s, x + 310 + (j * 25), y + 72 + (z * 25));
                RenderHelper.disableStandardItemLighting();
                
                if(this.checkClick(mouseX, mouseY, x + 310 + (j * 25), y + 72 + (z * 25), 16, 16))
                	this.drawToolTip(x + 308 + (j * 25), y + 70 + (z * 25), s.getDisplayName());
                
                j++;
                
                if(j % 3 == 0) 
                {
                	z++;
                	j = 0;
                }
			}
			
			this.drawText("Recipe for: " + this.getRecipeType().getItem().getDisplayName(), x + 260, y - 40, 0, font);
			
			RenderHelper.enableGUIStandardItemLighting();
            this.mc.getRenderItem().renderItemAndEffectIntoGUI(this.getRecipeType().getItem(), x + 275, y + 47);
            RenderHelper.disableStandardItemLighting();
            
			this.mc.getTextureManager().bindTexture(bookPageTexture);
			drawTexturedModalRect(x + 257, y + 36 , 4, 188, 492, 10, 14, 160, false, false, 512, 256);
				
		}		
		String str = GCCoreUtil.translate("book.page." + titlePage() + ".text");
		this.drawText(str, x, y - 40, this.getScroll(), font);
	}
	
	private boolean checkClick(int mouseX, int mouseY, int x, int y, int sizeX, int sizeY)
	{		
		return mouseX >= x && mouseX <= x + sizeX && mouseY >= y && mouseY <= y + sizeY;
	}
	
	private void drawToolTip(int mousePosX, int mousePosY, String tooltip)
	{
		this.drawToolTip(mousePosX, mousePosY, tooltip, this.mc.fontRenderer.getStringWidth(tooltip), 8);
	}
	
	private void drawToolTip(int mousePosX, int mousePosY, String tooltip, int width, int height)
	{
		GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glPushMatrix();
        GL11.glTranslatef(0, 0, 300);
        int k = width;
        int j2 = mousePosX - k / 2;
        int k2 = mousePosY - 12;
        int i1 = height;
/*
        if (j2 + k > width)
        {
            j2 -= (j2 - width + k);
        }

        if (k2 + i1 + 6 > height)
        {
            k2 = height - i1 - 6;
        }
*/
        int j1 = ColorUtil.to32BitColor(190, 0, 153, 255);
        this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
        this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
        this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
        this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
        this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
        int k1 = ColorUtil.to32BitColor(170, 0, 153, 255);
        int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
        this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
        this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
        this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
        this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);

        this.mc.fontRenderer.drawSplitString(tooltip, j2, k2, 150, ColorUtil.to32BitColor(255, 255, 255, 255));

        GL11.glPopMatrix();   
	}
	
	protected void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor)
    {
        float f = (float)(startColor >> 24 & 255) / 255.0F;
        float f1 = (float)(startColor >> 16 & 255) / 255.0F;
        float f2 = (float)(startColor >> 8 & 255) / 255.0F;
        float f3 = (float)(startColor & 255) / 255.0F;
        float f4 = (float)(endColor >> 24 & 255) / 255.0F;
        float f5 = (float)(endColor >> 16 & 255) / 255.0F;
        float f6 = (float)(endColor >> 8 & 255) / 255.0F;
        float f7 = (float)(endColor & 255) / 255.0F;
        float zLevel = 0.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)right, (double)top, (double)zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)left, (double)top, (double)zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)left, (double)bottom, (double)zLevel).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, (double)zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

}
