package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.api.IResearch;
import galaxyspace.core.client.gui.tile.GuiTileBase;
import galaxyspace.core.handler.capabilities.GSStatsCapabilityClient;
import galaxyspace.core.handler.capabilities.StatsCapabilityClient;
import galaxyspace.core.network.packet.GSPacketSimple;
import galaxyspace.core.network.packet.GSPacketSimple.GSEnumSimplePacket;
import galaxyspace.core.util.researches.ResearchUtil;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerResearchTable;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityResearchTable;
import micdoodle8.mods.galacticraft.core.util.ColorUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiResearchTable extends GuiTileBase {

	private final TileEntityResearchTable tile;
	private static final ResourceLocation iconsTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/researches/research_icons.png");
	public List<IResearch> know_res = new ArrayList<IResearch>();
	
	public GuiResearchTable(InventoryPlayer inventoryPlayer, TileEntityResearchTable tileEntity)
    {
		super(new ContainerResearchTable(inventoryPlayer, tileEntity), -1, -1);
		this.tile = tileEntity;
		//this.xSize = 250;
		//this.ySize = 350; 
    }
	
	@Override
	public void initGui()
    {
		super.initGui();
		
		updateRes();
	
    }
	
	private void updateRes()
	{
		/*StatsCapabilityClient gs_stats = GSStatsCapabilityClient.get(mc.player);
		
		for(IResearch res : ResearchUtil.getReserachList())
		{
			for(int i = 0; i < ResearchUtil.getReserachList().size(); i++)
			{
				if(res.getID() == gs_stats.getKnowledgeResearches()[i] && gs_stats.getKnowledgeResearches()[i] > 0)
					know_res.add(res);	
			}
		}*/
	}
	
	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {		
		super.drawGuiContainerForegroundLayer(par1, par2);
		//this.fontRenderer.drawString(EnumColor.WHITE + this.tile.getName(), 86 - (this.fontRenderer.getStringWidth(this.tile.getName()) / 2), 1, 4210752);
		//this.fontRenderer.drawString(EnumColor.WHITE + GCCoreUtil.translate("container.inventory"), 15, this.ySize - 88, 4210752);
    }
	
	@Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    { 	
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
		/*
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		int containerWidth = (this.width - this.xSize) / 2;
		int containerHeight = (this.height - this.ySize) / 2;
		this.drawRect(containerWidth, containerHeight, containerWidth+this.xSize, containerHeight+this.ySize, GSUtils.getColor(0, 41, 69, 255));
		//this.drawModalRectWithCustomSizedTexture(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize, 780, 500); // Base Gui		

		*/
		
    }
	
	@Override
    public void drawScreen(int mousePosX, int mousePosY, float partialTicks)
    {
		super.drawScreen(mousePosX, mousePosY, partialTicks);
		/*int containerWidth = (this.width - this.xSize) / 2;
		int containerHeight = (this.height - this.ySize) / 2;
		RenderHelper.disableStandardItemLighting();
		int i = 0;		
		for(IResearch res : ResearchUtil.getReserachList()) {
			drawWindow(containerWidth + res.getPosX() * 6, containerHeight + res.getPosY() * 3, partialTicks, res, mousePosX, mousePosY);
			drawString(mc.fontRenderer, res.getID() + ", ", containerWidth + 10 * i++, containerHeight + 30, 0xFFFFFF);
		}
		
		RenderHelper.enableGUIStandardItemLighting();*/
    }
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) throws IOException
    {
		super.mouseClicked(mouseX, mouseY, button);	
		
		int containerWidth = (this.width - this.xSize) / 2;
		int containerHeight = (this.height - this.ySize) / 2;
		//if(button == 0)
			for(IResearch res : ResearchUtil.getReserachList()) {
				int x = containerWidth + res.getPosX() * 6;
				int y = containerHeight + res.getPosY() * 3;
				if(mouseX >= x && mouseX < x + 25 && mouseY >= y && mouseY < y + 25)
				{
					if(button == 0)
						GalaxySpace.packetPipeline.sendToServer(new GSPacketSimple(GSEnumSimplePacket.S_UPDATE_RESEARCH_USER, mc.world, new Object[] {res.getID()}));
					else
						GalaxySpace.packetPipeline.sendToServer(new GSPacketSimple(GSEnumSimplePacket.S_UPDATE_RESEARCH_USER, mc.world, new Object[] {-1}));
					//updateRes();
				}
			}
    }
	
	private void drawWindow(int posX, int posY, float ticks, IResearch res, int mousePosX, int mousePosY)
	{
		boolean isResearched = false;
		this.mc.renderEngine.bindTexture(this.iconsTexture);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		StatsCapabilityClient gs_stats = GSStatsCapabilityClient.get(mc.player);
		
		
		int x = res.getTextureID();
		int y = res.getTextureID() / 8;
		if(res.getTextureID() > 8 * (y + 1))
			x = 8 * y - res.getTextureID() + 1;
		
		
		this.drawScaledCustomSizeModalRect(posX + 16, posY - 16, 32 * x, 32 * y, 32, 32, 24, 24, 256, 256);
		
		for(int i : gs_stats.getKnowledgeResearches())			
			if(i == res.getID())
				isResearched = true;
				
		if(isResearched)
			GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
		
		fontRenderer.drawSplitString(res.getName(), (int)((posX + 30) - fontRenderer.getStringWidth(res.getName()) / 2), posY + 10, 180, 0xFFFFFF);
		fontRenderer.drawSplitString(res.getNeedExperience() + " EXP", (int)((posX + 30) - fontRenderer.getStringWidth(res.getNeedExperience() + " EXP") / 2), posY + 20, 180, 0xFFFFFF);
		
		if(!isResearched && mousePosX >= posX + 15 && mousePosX < posX + 40 && mousePosY >= posY - 15 && mousePosY < posY + 10)
		{
			int i = 0;
			GL11.glPushMatrix();
			GL11.glTranslatef(0, 0, 300);
			this.drawToolTip(mousePosX + 18, mousePosY + 20, "", res.getNeedItems().size() * 18 + 100, 25);
			this.fontRenderer.drawString("Need Items:", mousePosX - 10, mousePosY + 8, 0xFFFFFF);
					
			for(ItemStack s : res.getNeedItems()) {
				RenderHelper.enableGUIStandardItemLighting();
		        this.mc.getRenderItem().renderItemAndEffectIntoGUI(s, mousePosX + i * 18 - 35, mousePosY + 16);
		        RenderHelper.disableStandardItemLighting();
		        i++;
			}
			GL11.glPopMatrix();
		}
	}
	
	private void drawToolTip(int mousePosX, int mousePosY, String tooltip)
	{
		this.drawToolTip(mousePosX, mousePosY, tooltip, this.fontRenderer.getStringWidth(tooltip), 8);
	}
	
	private void drawToolTip(int mousePosX, int mousePosY, String tooltip, int width, int height)
	{
		//GL11.glDepthMask(true);
        //GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glPushMatrix();
        //GL11.glTranslatef(0, 0, 300);
        int k = width;
        int j2 = mousePosX - k / 2;
        int k2 = mousePosY - 12;
        int i1 = height;

        if (j2 + k > this.width)
        {
            j2 -= (j2 - this.width + k);
        }

        if (k2 + i1 + 6 > this.height)
        {
            k2 = this.height - i1 - 6;
        }

        int j1 = ColorUtil.to32BitColor(190, 0, 94, 153);
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

        this.fontRenderer.drawSplitString(tooltip, j2, k2, 150, ColorUtil.to32BitColor(255, 255, 255, 255));

        GL11.glPopMatrix(); 
        //GL11.glDepthMask(false);
	}
	
	@Override
    protected boolean isModuleSupport() {
		return false;
	}
	
    @Override
	protected String getName() {
		return tile.getName();
	}	
    
    @Override
	protected Slot getBatterySlot() {
		return null;
	}
}
