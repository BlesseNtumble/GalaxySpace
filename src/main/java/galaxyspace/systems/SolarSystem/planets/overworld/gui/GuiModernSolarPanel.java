package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import galaxyspace.core.client.gui.tile.GuiTileBase;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerModernSolarPanel;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityModernSolarPanel;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class GuiModernSolarPanel extends GuiTileBase
{
   
    private final TileEntityModernSolarPanel solarPanel;
    private GuiButton buttonEnableSolar;
   
    public GuiModernSolarPanel(InventoryPlayer par1InventoryPlayer, TileEntityModernSolarPanel solarPanel)
    {
        super(new ContainerModernSolarPanel(par1InventoryPlayer, solarPanel), 2, 1);
        this.solarPanel = solarPanel;
        this.ySize = 201;
        this.header = 0;
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        switch (par1GuiButton.id)
        {
        case 0:
        	GalacticraftCore.packetPipeline.sendToServer(new PacketSimple(EnumSimplePacket.S_UPDATE_DISABLEABLE_BUTTON, GCCoreUtil.getDimensionID(this.mc.world), new Object[] { this.solarPanel.getPos(), 0 }));
            break;
        }
    }

    @Override
    public void initGui()
    {
        super.initGui();

        List<String> sunGenDesc = new ArrayList<String>();
        float sunVisible = Math.round(this.solarPanel.solarStrength / 9.0F * 1000) / 10.0F;
        sunGenDesc.add(this.solarPanel.solarStrength > 0 ? GCCoreUtil.translate("gui.status.sun_visible.name") + ": " + sunVisible + "%" : GCCoreUtil.translate("gui.status.blockedfully.name"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 47, (this.height - this.ySize) / 2 + 20, 18, 18, sunGenDesc, this.width, this.height, this));
        this.buttonList.add(this.buttonEnableSolar = new GuiButton(0, this.width / 2 - 36, this.height / 2 - 19, 72, 20, GCCoreUtil.translate("gui.button.enable.name")));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	super.drawGuiContainerForegroundLayer(par1, par2);
        int offsetY = 35;
        this.buttonEnableSolar.enabled = this.solarPanel.disableCooldown == 0;
        this.buttonEnableSolar.displayString = !this.solarPanel.getDisabled(0) ? GCCoreUtil.translate("gui.button.disable.name") : GCCoreUtil.translate("gui.button.enable.name");
        String displayString = GCCoreUtil.translate("gui.message.status.name") + ": " + this.getStatus();
        this.fontRenderer.drawString(EnumColor.WHITE + displayString, this.xSize / 2 - this.fontRenderer.getStringWidth(displayString) / 2, 45 + 23 - 46 + offsetY, 4210752);
        displayString = GCCoreUtil.translate("gui.message.generating.name") + ": " + (this.solarPanel.generateWatts > 0 ? EnergyDisplayHelper.getEnergyDisplayS(this.solarPanel.generateWatts) + "/t" : GCCoreUtil.translate("gui.status.not_generating.name"));
        this.fontRenderer.drawString(EnumColor.WHITE + displayString, this.xSize / 2 - this.fontRenderer.getStringWidth(displayString) / 2, 34 + 23 - 46 + offsetY, 4210752);
        float boost = Math.round((this.solarPanel.getSolarBoost() - 1) * 1000) / 10.0F;
        if(boost < 100.0F)
        	boost = -100.0F;
        displayString = GCCoreUtil.translate("gui.message.environment.name") + ": " + boost + "%";
        this.fontRenderer.drawString(EnumColor.WHITE + displayString, this.xSize / 2 - this.fontRenderer.getStringWidth(displayString) / 2, 56 + 23 - 46 + offsetY, 4210752);
    }

    private String getStatus()
    {
        if (this.solarPanel.getDisabled(0))
        {
            return EnumColor.ORANGE + GCCoreUtil.translate("gui.status.disabled.name");
        }

        if (!this.solarPanel.getWorld().isDaytime())
        {
            return EnumColor.DARK_RED + GCCoreUtil.translate("gui.status.blockedfully.name");
        }

        if (this.solarPanel.getWorld().isRaining() || this.solarPanel.getWorld().isThundering())
        {
            return EnumColor.DARK_RED + GCCoreUtil.translate("gui.status.raining.name");
        }

        if (this.solarPanel.solarStrength == 0)
        {
            return EnumColor.DARK_RED + GCCoreUtil.translate("gui.status.blockedfully.name");
        }

        if (this.solarPanel.solarStrength < 9)
        {
            return EnumColor.DARK_RED + GCCoreUtil.translate("gui.status.blockedpartial.name");
        }

        if (this.solarPanel.generateWatts > 0)
        {
            return EnumColor.DARK_GREEN + GCCoreUtil.translate("gui.status.collectingenergy.name");
        }

        return EnumColor.ORANGE + GCCoreUtil.translate("gui.status.unknown.name");
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
    	super.drawGuiContainerBackgroundLayer(var1, var2, var3);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        final int var5 = (this.width - this.xSize) / 2;
        final int var6 = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(var5 + 48, var6 + 18, 180, 198, 22, 22);
        
        this.renderEnergyBar(var5 + 83, var6 + 24, Math.min(this.solarPanel.getScaledElecticalLevel(54), 54), this.solarPanel.getEnergyStoredGC(), this.solarPanel.getMaxEnergyStoredGC());

        if (this.solarPanel.solarStrength > 0)        
            this.drawTexturedModalRect(var5 + 51, var6 + 21, 203, 201, 16, 16);        

    }

	@Override
	protected boolean isModuleSupport() {
		return false;
	}

	@Override
	protected String getName() {
		return solarPanel.getName();
	}

	@Override
	protected Slot getBatterySlot() {
		return inventorySlots.getSlotFromInventory(solarPanel, 0);
	}
}

