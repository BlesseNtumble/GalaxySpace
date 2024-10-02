/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  micdoodle8.mods.galacticraft.core.GalacticraftCore
 *  micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC
 *  micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion
 *  micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper
 *  micdoodle8.mods.galacticraft.core.network.IPacket
 *  micdoodle8.mods.galacticraft.core.network.PacketSimple
 *  micdoodle8.mods.galacticraft.core.network.PacketSimple$EnumSimplePacket
 *  micdoodle8.mods.galacticraft.core.util.EnumColor
 *  micdoodle8.mods.galacticraft.core.util.GCCoreUtil
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerSolarPanel;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntitySolarPanel;
import java.util.ArrayList;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.network.IPacket;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiSolarPanel
extends GuiContainerGC {
    private static final ResourceLocation solarGuiTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/solarpanel.png");
    private final TileEntitySolarPanel solarPanel;
    private GuiButton buttonEnableSolar;
    private GuiElementInfoRegion electricInfoRegion;

    public GuiSolarPanel(InventoryPlayer par1InventoryPlayer, TileEntitySolarPanel solarPanel) {
        super((Container)new ContainerSolarPanel(par1InventoryPlayer, solarPanel));
        this.electricInfoRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 107, (this.height - this.ySize) / 2 + 101, 56, 9, new ArrayList(), this.width, this.height, (GuiContainerGC)this);
        this.solarPanel = solarPanel;
        this.ySize = 201;
        this.xSize = 176;
    }

    protected void actionPerformed(GuiButton par1GuiButton) {
        switch (par1GuiButton.id) {
            case 0: {
                GalacticraftCore.packetPipeline.sendToServer((IPacket)new PacketSimple(PacketSimple.EnumSimplePacket.S_UPDATE_DISABLEABLE_BUTTON, new Object[]{this.solarPanel.xCoord, this.solarPanel.yCoord, this.solarPanel.zCoord, 0}));
            }
        }
    }

    public void initGui() {
        super.initGui();
        ArrayList<String> electricityDesc = new ArrayList<String>();
        electricityDesc.add(GCCoreUtil.translate((String)"gui.energyStorage.desc.0"));
        electricityDesc.add(EnumColor.YELLOW + GCCoreUtil.translate((String)"gui.energyStorage.desc.1") + (int)Math.floor(this.solarPanel.getEnergyStoredGC()) + " / " + (int)Math.floor(this.solarPanel.getMaxEnergyStoredGC()));
        this.electricInfoRegion.tooltipStrings = electricityDesc;
        this.electricInfoRegion.xPosition = (this.width - this.xSize) / 2 + 96;
        this.electricInfoRegion.yPosition = (this.height - this.ySize) / 2 + 24;
        this.electricInfoRegion.parentWidth = this.width;
        this.electricInfoRegion.parentHeight = this.height;
        this.infoRegions.add(this.electricInfoRegion);
        ArrayList<String> batterySlotDesc = new ArrayList<String>();
        batterySlotDesc.add(GCCoreUtil.translate((String)"gui.batterySlot.desc.0"));
        batterySlotDesc.add(GCCoreUtil.translate((String)"gui.batterySlot.desc.1"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 151, (this.height - this.ySize) / 2 + 82, 18, 18, batterySlotDesc, this.width, this.height, (GuiContainerGC)this));
        ArrayList<String> sunGenDesc = new ArrayList<String>();
        float sunVisible = (float)Math.round((float)this.solarPanel.solarStrength / 9.0f * 1000.0f) / 10.0f;
        sunGenDesc.add(this.solarPanel.solarStrength > 0 ? GCCoreUtil.translate((String)"gui.status.sunVisible.name") + ": " + sunVisible + "%" : GCCoreUtil.translate((String)"gui.status.blockedfully.name"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 47, (this.height - this.ySize) / 2 + 20, 18, 18, sunGenDesc, this.width, this.height, (GuiContainerGC)this));
        this.buttonEnableSolar = new GuiButton(0, this.width / 2 - 36, this.height / 2 - 19, 72, 20, GCCoreUtil.translate((String)"gui.button.enable.name"));
        this.buttonList.add(this.buttonEnableSolar);
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        int offsetY = 35;
        this.buttonEnableSolar.enabled = this.solarPanel.disableCooldown == 0;
        this.buttonEnableSolar.displayString = !this.solarPanel.getDisabled(0) ? GCCoreUtil.translate((String)"gui.button.disable.name") : GCCoreUtil.translate((String)"gui.button.enable.name");
        String displayString = this.solarPanel.getInventoryName();
        this.fontRendererObj.drawString(EnumColor.WHITE + displayString, this.xSize / 2 - this.fontRendererObj.getStringWidth(displayString) / 2, 1, 0x404040);
        displayString = GCCoreUtil.translate((String)"gui.message.status.name") + ": " + this.getStatus();
        this.fontRendererObj.drawString(EnumColor.WHITE + displayString, this.xSize / 2 - this.fontRendererObj.getStringWidth(displayString) / 2, 22 + offsetY, 0x404040);
        displayString = GCCoreUtil.translate((String)"gui.message.generating.name") + ": " + (this.solarPanel.generateWatts > 0 ? EnergyDisplayHelper.getEnergyDisplayS((float)this.solarPanel.generateWatts) + "/t" : GCCoreUtil.translate((String)"gui.status.notGenerating.name"));
        this.fontRendererObj.drawString(EnumColor.WHITE + displayString, this.xSize / 2 - this.fontRendererObj.getStringWidth(displayString) / 2, 11 + offsetY, 0x404040);
        float boost = (float)Math.round((this.solarPanel.getSolarBoost() - 1.0f) * 1000.0f) / 10.0f;
        displayString = GCCoreUtil.translate((String)"gui.message.environment.name") + ": " + boost + "%";
        this.fontRendererObj.drawString(EnumColor.WHITE + displayString, this.xSize / 2 - this.fontRendererObj.getStringWidth(displayString) / 2, 33 + offsetY, 0x404040);
        this.fontRendererObj.drawString(EnumColor.WHITE + GCCoreUtil.translate((String)"container.inventory"), 13, this.ySize - 91, 0x404040);
    }

    private String getStatus() {
        if (this.solarPanel.getDisabled(0)) {
            return EnumColor.ORANGE + GCCoreUtil.translate((String)"gui.status.disabled.name");
        }
        if (!this.solarPanel.getWorldObj().isDaytime()) {
            return EnumColor.DARK_RED + GCCoreUtil.translate((String)"gui.status.blockedfully.name");
        }
        if (this.solarPanel.getWorldObj().isRaining() || this.solarPanel.getWorldObj().isThundering()) {
            return EnumColor.DARK_RED + GCCoreUtil.translate((String)"gui.status.raining.name");
        }
        if (this.solarPanel.solarStrength == 0) {
            return EnumColor.DARK_RED + GCCoreUtil.translate((String)"gui.status.blockedfully.name");
        }
        if (this.solarPanel.solarStrength < 9) {
            return EnumColor.DARK_RED + GCCoreUtil.translate((String)"gui.status.blockedpartial.name");
        }
        if (this.solarPanel.generateWatts > 0) {
            return EnumColor.DARK_GREEN + GCCoreUtil.translate((String)"gui.status.collectingenergy.name");
        }
        return EnumColor.ORANGE + GCCoreUtil.translate((String)"gui.status.unknown.name");
    }

    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.mc.getTextureManager().bindTexture(solarGuiTexture);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        ArrayList electricityDesc = new ArrayList();
        EnergyDisplayHelper.getEnergyDisplayTooltip((float)this.solarPanel.getEnergyStoredGC(), (float)this.solarPanel.getMaxEnergyStoredGC(), electricityDesc);
        this.electricInfoRegion.tooltipStrings = electricityDesc;
        if (this.solarPanel.getEnergyStoredGC() > 0.0f) {
            this.drawTexturedModalRect(var5 + 83, var6 + 24, 176, 0, 11, 10);
        }
        if (this.solarPanel.solarStrength > 0) {
            this.drawTexturedModalRect(var5 + 48, var6 + 21, 176, 10, 16, 16);
        }
        this.drawTexturedModalRect(var5 + 97, var6 + 25, 187, 0, Math.min(this.solarPanel.getScaledElecticalLevel(54), 54), 7);
    }
}

