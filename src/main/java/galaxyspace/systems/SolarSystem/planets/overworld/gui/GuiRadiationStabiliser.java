package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.GSItems;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerRadiationStabiliser;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityRadiationStabiliser;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementCheckbox;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementCheckbox.ICheckBoxCallback;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.RedstoneUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiRadiationStabiliser extends GuiContainerGC implements ICheckBoxCallback {

	private static final ResourceLocation pressureGui = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/base_gui.png");
	private TileEntityRadiationStabiliser tileEntity;
	private GuiElementCheckbox checkboxRenderBubble;
	private GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion(0, 0, 52, 9, null, 0, 0, this);
	private GuiButton disableButton;
	
	public final int BTN_ENABLE = 6;
    public final int FIELD_STRENGTH= 10;
    
	public GuiRadiationStabiliser(InventoryPlayer par1InventoryPlayer, TileEntityRadiationStabiliser tileEntity)
    {
        super(new ContainerRadiationStabiliser(par1InventoryPlayer, tileEntity));
        this.ySize = 204;
        this.tileEntity = tileEntity;
    }
	
	@Override
    public void drawScreen(int par1, int par2, float par3)
    {
		super.drawScreen(par1, par2, par3);
    }
	
    @Override
    public void initGui()
    {
		super.initGui();

		this.electricInfoRegion.tooltipStrings = new ArrayList<String>();
		this.electricInfoRegion.xPosition = (this.width - this.xSize) / 2 + 17;
		this.electricInfoRegion.yPosition = (this.height - this.ySize) / 2 + 102;
		this.electricInfoRegion.parentWidth = this.width;
		this.electricInfoRegion.parentHeight = this.height;
		this.infoRegions.add(this.electricInfoRegion);
		
		List<String> desc = new ArrayList<String>();
        desc.add(GCCoreUtil.translate("gui.battery_slot.desc.0"));
        desc.add(GCCoreUtil.translate("gui.battery_slot.desc.1"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 77, (this.height - this.ySize) / 2 + 100, 18, 18, desc, this.width, this.height, this));
 
        desc = new ArrayList<String>();
        desc.add(GCCoreUtil.translate("gui.show_bubble.desc.0"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 10, (this.height - this.ySize) / 2 + 67, 85, 13, desc, this.width, this.height, this));
       
        desc = new ArrayList<String>();
        desc.add(GCCoreUtil.translate("gui.module_expander.desc"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 80, (this.height - this.ySize) / 2 + 40, 18, 18, desc, this.width, this.height, this));
       
        this.buttonList.clear();
        
        final int var5 = (this.width - this.xSize) / 2;
        final int var6 = (this.height - this.ySize) / 2;
        
        disableButton = new GuiButton(BTN_ENABLE, var5 + 110, var6 + 64, 50, 20, GCCoreUtil.translate("gui.button.disable.name"));

        this.checkboxRenderBubble = new GuiElementCheckbox(0, this, var5 + 10, var6 + 67, EnumColor.WHITE + GCCoreUtil.translate("gui.message.bubble_visible.name"));
        this.buttonList.add(this.checkboxRenderBubble);
        this.buttonList.add(disableButton);
        
        desc = new ArrayList<String>();
        desc.add(EnumColor.BRIGHT_GREEN + GCCoreUtil.translate("gui.available_modules.desc"));
        desc.add("");
        desc.add("- " + new ItemStack(GSItems.UPGRADES, 1, 0).getDisplayName());
        desc.add("- " + new ItemStack(GSItems.UPGRADES, 1, 3).getDisplayName());
        this.infoRegions.add(new GuiElementInfoRegion((this.width + this.xSize) / 2, (this.height - this.ySize) / 2 + 16, 18, 21 * 4, desc, this.width, this.height, this));
      
        
    }
	
	@Override
    protected void actionPerformed(GuiButton par1GuiButton)
    {
		switch (par1GuiButton.id)
        {
        case BTN_ENABLE:
        	GalacticraftCore.packetPipeline.sendToServer(new PacketSimple(EnumSimplePacket.S_UPDATE_DISABLEABLE_BUTTON, GCCoreUtil.getDimensionID(this.mc.world), new Object[] { this.tileEntity.getPos(), 0 }));
            break;
        default:
            break;
        }
    }
	
	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String displayString = EnumColor.WHITE + tileEntity.getName();
        this.fontRenderer.drawString(displayString, this.xSize / 2 - this.fontRenderer.getStringWidth(displayString) / 2, 1, 4210752);
        this.fontRenderer.drawString(EnumColor.WHITE + GCCoreUtil.translate("container.inventory"), 16, 116, 4210752);
        this.fontRenderer.drawSplitString(EnumColor.WHITE + GCCoreUtil.translate("gui.message.status.name") + ": " + this.getStatus(), 105, 104, this.xSize - 105, 4210752);
    }
	
	private String getStatus()
    {
		if (RedstoneUtil.isBlockReceivingRedstone(this.tileEntity.getWorld(), this.tileEntity.getPos()))
        {
        	return EnumColor.RED + GCCoreUtil.translate("gui.status.off.name");
        }
		
		if (this.tileEntity.getEnergyStoredGC() <= 0.0F)
        {
            return EnumColor.RED + GCCoreUtil.translate("gui.message.no_energy.name");
        }
		
		if (tileEntity.getDisabled(0)) 
		{
			return EnumColor.RED + GCCoreUtil.translate("gui.status.disabled.name");
		}
		
		if (this.tileEntity.hasEnoughEnergyToRun)
        {
			return EnumColor.BRIGHT_GREEN + GCCoreUtil.translate("gui.status.running.name");
        }
		
		return EnumColor.ORANGE + GCCoreUtil.translate("gui.status.idle.name");
    }
	
	@Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int containerWidth = (this.width - this.xSize) / 2;
		int containerHeight = (this.height - this.ySize) / 2;
		this.mc.renderEngine.bindTexture(this.pressureGui);
		this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);
		
		int scale = 0;
		
		//Energy
        this.drawTexturedModalRect(containerWidth + 16, containerHeight + 102, 192, 47, 56, 9);
        this.drawTexturedModalRect(containerWidth + 4, containerHeight + 102, 192, 56, 11, 10);
        if (this.tileEntity.getEnergyStoredGC() > 0)
        {
            scale = this.tileEntity.getScaledElecticalLevel(55);
            this.drawTexturedModalRect(containerWidth + 116 - 99, containerHeight + 103, 192, 0, scale, 7);
            this.drawTexturedModalRect(containerWidth + 3, containerHeight + 102, 192, 7, 11, 10);           
        }
        
		List<String> electricityDesc = new ArrayList<String>();
        electricityDesc.add(GCCoreUtil.translate("gui.energy_storage.desc.0"));
        EnergyDisplayHelper.getEnergyDisplayTooltip(this.tileEntity.getEnergyStoredGC(), this.tileEntity.getMaxEnergyStoredGC(), electricityDesc);
        this.electricInfoRegion.tooltipStrings = electricityDesc;
        
		this.checkboxRenderBubble.isSelected = this.tileEntity.shouldRenderBubble;
		
		this.disableButton.displayString = GCCoreUtil.translate(this.tileEntity.getDisabled(0) ? "gui.button.enable.name" : "gui.button.disable.name");

		// Slots
        for(int i = 0; i < this.inventorySlots.inventorySlots.size(); i++)
        {
	        int x = this.inventorySlots.getSlot(i).xPos;
	        int y = this.inventorySlots.getSlot(i).yPos;
	        
	       /* if(!(this.inventorySlots.getSlot(i).inventory instanceof InventoryPlayer))
	        {*/
		        
		        GL11.glPushMatrix();
		        switch(i)
		        {
		        	case 0:
		        	{
		        		this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 213, 26, 20, 21);	        		 
		        		break;
		        	}
		        	default: 
		        	{
		        		this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 192, 26, 20, 21);
		        		break;
		        	}	        	
		        }
		        GL11.glPopMatrix();
	        //}
        }
        
		String gen = "Consumption: " + EnergyDisplayHelper.getEnergyDisplayS(this.tileEntity.storage.getMaxExtract()) + "/t";
		this.drawString(fontRenderer, gen, containerWidth + 30, containerHeight + 40, 0xFFFFFF);
		gen = "Bubble size: " + (int) this.tileEntity.bubbleSize + " blocks";
		this.drawString(fontRenderer, gen, containerWidth + 30, containerHeight + 40 + fontRenderer.FONT_HEIGHT, 0xFFFFFF);

        if(GalaxySpace.debug) GSUtils.renderDebugGui(this, containerWidth, containerHeight);
    }
	
	@Override
    public void onSelectionChanged(GuiElementCheckbox checkbox, boolean newSelected)
    {
        this.tileEntity.shouldRenderBubble = newSelected;
        GalacticraftCore.packetPipeline.sendToServer(new PacketSimple(EnumSimplePacket.S_ON_ADVANCED_GUI_CLICKED_INT, GCCoreUtil.getDimensionID(mc.world), new Object[] { 6, this.tileEntity.getPos(), newSelected ? 1 : 0 }));
    }
	
	@Override
    public boolean canPlayerEdit(GuiElementCheckbox checkbox, EntityPlayer player)
    {
        return true;
    }
	
	@Override
    public boolean getInitiallySelected(GuiElementCheckbox checkbox)
    {
        return this.tileEntity.shouldRenderBubble;
    }
	
	@Override
    public void onIntruderInteraction()
    {

    }
}
