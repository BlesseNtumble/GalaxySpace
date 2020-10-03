package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.api.IResearch;
import galaxyspace.core.handler.capabilities.GSStatsCapabilityClient;
import galaxyspace.core.handler.capabilities.StatsCapabilityClient;
import galaxyspace.core.util.researches.ResearchUtil;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerResearchTable;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityResearchTable;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiResearchTable extends GuiContainerGC {

	private final TileEntityResearchTable tile;
	private static final ResourceLocation guiTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/research_table.png");
	public List<IResearch> know_res = new ArrayList<IResearch>();
	
	public GuiResearchTable(InventoryPlayer inventoryPlayer, TileEntityResearchTable tileEntity)
    {
		super(new ContainerResearchTable(inventoryPlayer, tileEntity));
		this.tile = tileEntity;
		this.xSize = 640;
		this.ySize = 400; 
    }
	
	@Override
	public void initGui()
    {
		super.initGui();
		
		StatsCapabilityClient gs_stats = GSStatsCapabilityClient.get(mc.player);
		
		for(IResearch res : ResearchUtil.getReserachList())
		{
			for(int i = 0; i < ResearchUtil.getReserachList().size(); i++)
			{
				if(res.getID() == gs_stats.getKnowledgeResearches()[i] && gs_stats.getKnowledgeResearches()[i] > 0)
					know_res.add(res);	
			}
		}
	
    }
	
	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {		
		this.fontRenderer.drawString(EnumColor.WHITE + this.tile.getName(), 86 - (this.fontRenderer.getStringWidth(this.tile.getName()) / 2), 1, 4210752);
		this.fontRenderer.drawString(EnumColor.WHITE + GCCoreUtil.translate("container.inventory"), 15, this.ySize - 88, 4210752);
    }
	
	@Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    { 		
		this.mc.renderEngine.bindTexture(this.guiTexture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		int containerWidth = (this.width - this.xSize) / 2;
		int containerHeight = (this.height - this.ySize) / 2;
		this.drawModalRectWithCustomSizedTexture(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize, 780, 500); // Base Gui		

		int i = 0;
		for(IResearch res : know_res)
			drawWindow(containerWidth + 50, containerHeight + 50, par3, res);
			//drawString(mc.fontRenderer, res.getID() + ", ", containerWidth + 10 * i++, containerHeight + 10, 0xFFFFFF);
		i = 0;
		for(IResearch res : ResearchUtil.getReserachList())
			drawString(mc.fontRenderer, res.getID() + ", ", containerWidth + 10 * i++, containerHeight + 30, 0xFFFFFF);
    }
	
	@Override
    public void drawScreen(int mousePosX, int mousePosY, float partialTicks)
    {
		super.drawScreen(mousePosX, mousePosY, partialTicks);
    }
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) throws IOException
    {
		super.mouseClicked(mouseX, mouseY, button);		
    }
	
	private void drawWindow(int posX, int posY, int ticks, IResearch res)
	{
		this.mc.renderEngine.bindTexture(this.guiTexture);
		GL11.glColor4f(1.0F, 0.0F, 1.0F, 1.0F);
		this.drawModalRectWithCustomSizedTexture(posX, posY, 0, 0, 150, 40, 150, 50); // Base Gui		

		this.drawString(mc.fontRenderer, res.getName(), posX + 10, posY + 5, 0xFFFFFF);
		
		int i = 0;
		for(ItemStack s : res.getNeedItems()) {
			RenderHelper.enableGUIStandardItemLighting();
	        this.mc.getRenderItem().renderItemAndEffectIntoGUI(s, posX + (16 * i) + 10, posY + (16 * i) + 16);
	        RenderHelper.disableStandardItemLighting();
	        i++;
		}
	}
}
