package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import asmodeuscore.core.utils.Utils;
import galaxyspace.GalaxySpace;
import galaxyspace.core.prefab.inventory.SlotUpgrades;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerWindGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityWindGenerator;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiWindGenerator extends GuiContainerGC
{
    private static final ResourceLocation solarGuiTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/wind_turbine.png");

    private final TileEntityWindGenerator solarPanel;
    

    private GuiButton buttonEnableSolar;
    private GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 107, (this.height - this.ySize) / 2 + 101, 56, 9, new ArrayList<String>(), this.width, this.height, this);

    public GuiWindGenerator(InventoryPlayer par1InventoryPlayer, TileEntityWindGenerator solarPanel)
    {
        super(new ContainerWindGenerator(par1InventoryPlayer, solarPanel));
        this.solarPanel = solarPanel;
        this.ySize = 201;
        this.xSize = 176;
    }
/*
    @Override
    protected void actionPerformed(GuiButton par1GuiButton)
    {
		switch (par1GuiButton.id) {
		case 0:
			GalacticraftCore.packetPipeline.sendToServer(new PacketSimple(EnumSimplePacket.S_UPDATE_DISABLEABLE_BUTTON,	GCCoreUtil.getDimensionID(this.mc.world), new Object[] { this.solarPanel.getPos(), 0 }));
			break;
		}
    }*/
    
    @Override
    public void initGui()
    {
        super.initGui();
        List<String> electricityDesc = new ArrayList<String>();
        electricityDesc.add(GCCoreUtil.translate("gui.energy_storage.desc.0"));
        electricityDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.energy_storage.desc.1") + ((int) Math.floor(this.solarPanel.getEnergyStoredGC()) + " / " + (int) Math.floor(this.solarPanel.getMaxEnergyStoredGC())));
        this.electricInfoRegion.tooltipStrings = electricityDesc;
        this.electricInfoRegion.xPosition = (this.width - this.xSize) / 2 + 96;
        this.electricInfoRegion.yPosition = (this.height - this.ySize) / 2 + 24;
        this.electricInfoRegion.parentWidth = this.width;
        this.electricInfoRegion.parentHeight = this.height;
        this.infoRegions.add(this.electricInfoRegion);
        List<String> batterySlotDesc = new ArrayList<String>();
        batterySlotDesc.add(GCCoreUtil.translate("gui.battery_slot.desc.0"));
        batterySlotDesc.add(GCCoreUtil.translate("gui.battery_slot.desc.1"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 151, (this.height - this.ySize) / 2 + 82, 18, 18, batterySlotDesc, this.width, this.height, this));
        List<String> sunGenDesc = new ArrayList<String>();
        sunGenDesc.add((this.solarPanel.getWindBoost() > 0 ? GCCoreUtil.translate("gui.status.wind.name") : GCCoreUtil.translate("gui.status.nowind.name")) + ": " + Utils.localeBoolean(this.solarPanel.getWindBoost() > 0));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 47, (this.height - this.ySize) / 2 + 20, 18, 18, sunGenDesc, this.width, this.height, this));
        this.buttonList.add(this.buttonEnableSolar = new GuiButton(0, this.width / 2 - 36, this.height / 2 - 19, 72, 20, GCCoreUtil.translate("gui.button.enable.name")));

        List<String> rotorDesc = new ArrayList<String>();
        rotorDesc.add(GCCoreUtil.translate("gui.rotor_slot_desc.0"));
        rotorDesc.add(GCCoreUtil.translate("gui.rotor_slot_desc.1"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + this.inventorySlots.getSlot(1).xPos, (this.height - this.ySize) / 2 + this.inventorySlots.getSlot(1).yPos, 18, 18, rotorDesc, this.width, this.height, this));
      
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        int offsetY = 35;
        this.buttonEnableSolar.enabled = this.solarPanel.disableCooldown == 0;
        this.buttonEnableSolar.displayString = !this.solarPanel.getDisabled(0) ? GCCoreUtil.translate("gui.button.disable.name") : GCCoreUtil.translate("gui.button.enable.name");
        String displayString = this.solarPanel.getName();
        this.fontRenderer.drawString(EnumColor.WHITE + displayString, this.xSize / 2 - this.fontRenderer.getStringWidth(displayString) / 2, 1, 4210752);
        displayString = GCCoreUtil.translate("gui.message.status.name") + ": " + this.getStatus();
        this.fontRenderer.drawString(EnumColor.WHITE + displayString, this.xSize / 2 - this.fontRenderer.getStringWidth(displayString) / 2, 45 + 23 - 46 + offsetY, 4210752);
        displayString = GCCoreUtil.translate("gui.message.generating.name") + ": " + (this.solarPanel.generateWatts > 0 ? EnergyDisplayHelper.getEnergyDisplayS(this.solarPanel.generateWatts) + "/t" : GCCoreUtil.translate("gui.status.not_generating.name"));
        this.fontRenderer.drawString(EnumColor.WHITE + displayString, this.xSize / 2 - this.fontRenderer.getStringWidth(displayString) / 2, 34 + 23 - 46 + offsetY, 4210752);
        float boost = Math.round((this.solarPanel.getWindBoost() - 1) * 1000) / 10.0F;
        displayString = GCCoreUtil.translate("gui.message.environment.name") + ": " + boost + "%";
        this.fontRenderer.drawString(EnumColor.WHITE + displayString, this.xSize / 2 - this.fontRenderer.getStringWidth(displayString) / 2, 56 + 23 - 46 + offsetY, 4210752);
        //		displayString = ElectricityDisplay.getDisplay(this.solarPanel.getVoltage(), ElectricUnit.VOLTAGE);
        //		this.fontRendererObj.drawString(displayString, this.xSize / 2 - this.fontRendererObj.getStringWidth(displayString) / 2, 68 + 23 - 46 + offsetY, 4210752);
        this.fontRenderer.drawString(EnumColor.WHITE + GCCoreUtil.translate("container.inventory"), 13, this.ySize - 91, 4210752);
    }

    private String getStatus()
    {
        if (this.solarPanel.getDisabled(0))
        {
            return EnumColor.ORANGE + GCCoreUtil.translate("gui.status.disabled.name");
        }

        if (this.solarPanel.generateWatts > 0)
        {
            return EnumColor.DARK_GREEN + GCCoreUtil.translate("gui.status.collectingenergy.name");
        }
        
        if(this.solarPanel.getWindBoost() < 0.1)
        {
        	return EnumColor.DARK_RED + GCCoreUtil.translate("gui.status.nowind.name");
        }

        return EnumColor.ORANGE + GCCoreUtil.translate("gui.status.unknown.name");
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(this.solarGuiTexture);
        final int containerWidth = (this.width - this.xSize) / 2;
        final int containerHeight = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);

        List<String> electricityDesc = new ArrayList<String>();
        EnergyDisplayHelper.getEnergyDisplayTooltip(this.solarPanel.getEnergyStoredGC(), this.solarPanel.getMaxEnergyStoredGC(), electricityDesc);
//		electricityDesc.add(GCCoreUtil.translate("gui.energyStorage.desc.0"));
//		electricityDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.energyStorage.desc.1") + ((int) Math.floor(this.solarPanel.getEnergyStoredGC()) + " / " + (int) Math.floor(this.solarPanel.getMaxEnergyStoredGC())));
        this.electricInfoRegion.tooltipStrings = electricityDesc;

        if (this.solarPanel.getEnergyStoredGC() > 0)
        {
            this.drawTexturedModalRect(containerWidth + 83, containerHeight + 24, 176, 0, 11, 10);
        }

        if (this.solarPanel.generateWatts > 0)
        {
        	this.drawTexturedModalRect(containerWidth + 46, containerHeight + 19, 176, 10, 20, 20);
        }
        
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
		        		//this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 213, 26, 20, 21);	        		 
		        		break;
		        	}	        	
		        	default: 
		        	{
		        		this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 176, 33, 20, 21);
		        		break;
		        	}	        	
		        }
		        
		        if(this.inventorySlots.getSlot(i) instanceof SlotUpgrades)
		        {
	        		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        		this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 213, 175, 20, 20);
	        	}
		        
		        GL11.glPopMatrix();
	        //}
        }
        
        this.drawTexturedModalRect(containerWidth + 97, containerHeight + 25, 187, 0, Math.min(this.solarPanel.getScaledElecticalLevel(54), 54), 7);
        
        if(GalaxySpace.debug) GSUtils.renderDebugGui(this, containerWidth, containerHeight);
    
    }   
  
}
