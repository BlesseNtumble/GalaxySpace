package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.GSItems;
import galaxyspace.core.client.gui.tile.GuiTileBase;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerRadiationStabiliser;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityRadiationStabiliser;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
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
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class GuiRadiationStabiliser extends GuiTileBase implements ICheckBoxCallback {

	private TileEntityRadiationStabiliser tileEntity;
	private GuiElementCheckbox checkboxRenderBubble;
	private GuiButton disableButton;
	
	public final int BTN_ENABLE = 6;
    public final int FIELD_STRENGTH= 10;
    
	public GuiRadiationStabiliser(InventoryPlayer par1InventoryPlayer, TileEntityRadiationStabiliser tileEntity)
    {
        super(new ContainerRadiationStabiliser(par1InventoryPlayer, tileEntity), 2, 1);
        this.ySize = 204;
        this.tileEntity = tileEntity;
        this.header = 3;
        moduleInfoX = this.inventorySlots.getSlotFromInventory(tileEntity, 1).xPos;
		moduleInfoY = this.inventorySlots.getSlotFromInventory(tileEntity, 1).yPos;
		moduleList = new ItemStack[] {new ItemStack(GSItems.UPGRADES, 1, 0), new ItemStack(GSItems.UPGRADES, 1, 3)};
	    
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
		
		List<String> desc = new ArrayList<String>();
        desc = new ArrayList<String>();
        desc.add(GCCoreUtil.translate("gui.show_bubble.desc.0"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 10, (this.height - this.ySize) / 2 + 67, 85, 13, desc, this.width, this.height, this));

        this.buttonList.clear();
        
        final int var5 = (this.width - this.xSize) / 2;
        final int var6 = (this.height - this.ySize) / 2;
        
        disableButton = new GuiButton(BTN_ENABLE, var5 + 110, var6 + 64, 50, 20, GCCoreUtil.translate("gui.button.disable.name"));

        this.checkboxRenderBubble = new GuiElementCheckbox(0, this, var5 + 10, var6 + 67, EnumColor.WHITE + GCCoreUtil.translate("gui.message.bubble_visible.name"));
        this.buttonList.add(this.checkboxRenderBubble);
        this.buttonList.add(disableButton);

        
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
		super.drawGuiContainerForegroundLayer(par1, par2);
		TextFormatting color = getStyle() == Style.MODERN ? TextFormatting.WHITE : TextFormatting.DARK_GRAY;
        this.fontRenderer.drawSplitString(color + GCCoreUtil.translate("gui.message.status.name") + ": " + this.getStatus(), 105, 98, this.xSize - 105, 4210752);
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
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int containerWidth = (this.width - this.xSize) / 2;
		int containerHeight = (this.height - this.ySize) / 2;
		//Energy
		this.renderEnergyBar(containerWidth + 5, containerHeight + 102, this.tileEntity.getScaledElecticalLevel(55), this.tileEntity.getEnergyStoredGC(), this.tileEntity.getMaxEnergyStoredGC());

		this.checkboxRenderBubble.isSelected = this.tileEntity.shouldRenderBubble;
		
		this.disableButton.displayString = GCCoreUtil.translate(this.tileEntity.getDisabled(0) ? "gui.button.enable.name" : "gui.button.disable.name");
		
        
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

	@Override
	protected boolean isModuleSupport() {
		return true;
	}

	@Override
	protected String getName() {
		return tileEntity.getName();
	}

	@Override
	protected Slot getBatterySlot() {
		return inventorySlots.getSlotFromInventory(tileEntity, 0);
	}
}
