package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerOxygenFiller;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemOxygenCanisterBase;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityOxygenFiller;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.items.ItemOxygenTank;
import micdoodle8.mods.galacticraft.core.tile.TileEntityOxygenCompressor;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiOxygenFiller extends GuiContainerGC
{
	private static final ResourceLocation texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/base_gui.png");
    private final TileEntityOxygenFiller compressor;

    private GuiElementInfoRegion oxygenInfoRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 112, (this.height - this.ySize) / 2 + 24, 56, 9, new ArrayList<String>(), this.width, this.height, this);
    private GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 112, (this.height - this.ySize) / 2 + 37, 56, 9, new ArrayList<String>(), this.width, this.height, this);

    public GuiOxygenFiller(InventoryPlayer par1InventoryPlayer, TileEntityOxygenFiller par2TileEntityAirDistributor)
    {
        super(new ContainerOxygenFiller(par1InventoryPlayer, par2TileEntityAirDistributor));
        this.compressor = par2TileEntityAirDistributor;
        this.ySize = 204;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        List<String> batterySlotDesc = new ArrayList<String>();
        batterySlotDesc.add(GCCoreUtil.translate("gui.batterySlot.desc.0"));
        batterySlotDesc.add(GCCoreUtil.translate("gui.batterySlot.desc.1"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 78, (this.height - this.ySize) / 2 + 101, 18, 18, batterySlotDesc, this.width, this.height, this));
        List<String> oxygenSlotDesc = new ArrayList<String>();
        oxygenSlotDesc.add(GCCoreUtil.translate("gui.oxygenSlot.desc.0"));
        oxygenSlotDesc.add(GCCoreUtil.translate("gui.oxygenSlot.desc.1"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 16, (this.height - this.ySize) / 2 + 26, 18, 18, oxygenSlotDesc, this.width, this.height, this));
        List<String> compressorSlotDesc = new ArrayList<String>();
        compressorSlotDesc.add(GCCoreUtil.translate("gui.oxygenCompressor.slot.desc.0"));
        compressorSlotDesc.add(GCCoreUtil.translate("gui.oxygenCompressor.slot.desc.1"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 132, (this.height - this.ySize) / 2 + 70, 18, 18, compressorSlotDesc, this.width, this.height, this));
        List<String> oxygenDesc = new ArrayList<String>();
        oxygenDesc.add(GCCoreUtil.translate("gui.oxygenStorage.desc.0"));
        oxygenDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.oxygenStorage.desc.1") + ": " + ((int) Math.floor(this.compressor.storedOxygen) + " / " + (int) Math.floor(this.compressor.maxOxygen)));
        this.oxygenInfoRegion.tooltipStrings = oxygenDesc;
        this.oxygenInfoRegion.xPosition = (this.width - this.xSize) / 2 + 112;
        this.oxygenInfoRegion.yPosition = (this.height - this.ySize) / 2 + 102;
        this.oxygenInfoRegion.parentWidth = this.width;
        this.oxygenInfoRegion.parentHeight = this.height;
        this.infoRegions.add(this.oxygenInfoRegion);
        List<String> electricityDesc = new ArrayList<String>();
        electricityDesc.add(GCCoreUtil.translate("gui.energyStorage.desc.0"));
        electricityDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.energyStorage.desc.1") + ((int) Math.floor(this.compressor.getEnergyStoredGC()) + " / " + (int) Math.floor(this.compressor.getMaxEnergyStoredGC())));
        this.electricInfoRegion.tooltipStrings = electricityDesc;
        this.electricInfoRegion.xPosition = (this.width - this.xSize) / 2 + 16;
        this.electricInfoRegion.yPosition = (this.height - this.ySize) / 2 + 102;
        this.electricInfoRegion.parentWidth = this.width;
        this.electricInfoRegion.parentHeight = this.height;
        this.infoRegions.add(this.electricInfoRegion);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	String displayString = EnumColor.WHITE + compressor.getInventoryName();
        this.fontRendererObj.drawString(displayString, this.xSize / 2 - this.fontRendererObj.getStringWidth(displayString) / 2, 1, 4210752);
       
        String status = GCCoreUtil.translate("gui.message.status.name") + ": " + this.getStatus();
        this.fontRendererObj.drawString(EnumColor.WHITE + status, this.xSize / 2 - this.fontRendererObj.getStringWidth(status) / 2, 65, 4210752);
        status = GCCoreUtil.translate("gui.oxygenUse.desc") + ": " + TileEntityOxygenCompressor.TANK_TRANSFER_SPEED * 20 + GCCoreUtil.translate("gui.perSecond");
        this.fontRendererObj.drawString(EnumColor.WHITE + status, this.xSize / 2 - this.fontRendererObj.getStringWidth(status) / 2, 75, 4210752);
        //		status = ElectricityDisplay.getDisplay(this.compressor.ueWattsPerTick * 20, ElectricUnit.WATT);
        //		this.fontRendererObj.drawString(status, this.xSize / 2 - this.fontRendererObj.getStringWidth(status) / 2, 70, 4210752);
        //		status = ElectricityDisplay.getDisplay(this.compressor.getVoltage(), ElectricUnit.VOLTAGE);
        //		this.fontRendererObj.drawString(status, this.xSize / 2 - this.fontRendererObj.getStringWidth(status) / 2, 80, 4210752);
        this.fontRendererObj.drawString(EnumColor.WHITE + GCCoreUtil.translate("container.inventory"), 8, this.ySize - 104 + 17, 4210752);
    }

    private String getStatus()
    {
        if (this.compressor.storedOxygen < 1.0D)
        {
            return EnumColor.DARK_RED + GCCoreUtil.translate("gui.status.missingoxygen.name");
        }

        return this.compressor.getGUIstatus();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        final int var5 = (this.width - this.xSize) / 2;
        final int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);

        if (this.compressor != null)
        {
        	int scale = this.compressor.getCappedScaledOxygenLevel(54);
        	//Oxygen
        	this.drawTexturedModalRect(var5 + 112, var6 + 102, 192, 47, 56, 9);
        	this.drawTexturedModalRect(var5 + 100, var6 + 102, 203, 56, 11, 10);
        	if (this.compressor.storedOxygen > 0)
            {
        		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        		this.drawTexturedModalRect(var5 + 113, var6 + 103, 192, 18, scale, 7);
        		this.drawTexturedModalRect(var5 + 99, var6 + 102, 203, 7, 11, 10); 
                
            }
        	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        	//Energy
            this.drawTexturedModalRect(var5 + 16, var6 + 102, 192, 47, 56, 9);
            this.drawTexturedModalRect(var5 + 4, var6 + 102, 192, 56, 11, 10);
            if (this.compressor.getEnergyStoredGC() > 0)
            {
                scale = this.compressor.getScaledElecticalLevel(55);
                this.drawTexturedModalRect(var5 + 116 - 99, var6 + 103, 192, 0, scale, 7);
                this.drawTexturedModalRect(var5 + 3, var6 + 102, 192, 7, 11, 10);           
            }
            
           // int scale = this.compressor.getCappedScaledOxygenLevel(54);
            /*this.drawTexturedModalRect(var5 + 113, var6 + 25, 197, 7, Math.min(scale, 54), 7);
            scale = this.compressor.getScaledElecticalLevel(54);
            this.drawTexturedModalRect(var5 + 113, var6 + 38, 197, 0, Math.min(scale, 54), 7);

            if (this.compressor.getEnergyStoredGC() > 0)
            {
                this.drawTexturedModalRect(var5 + 99, var6 + 37, 176, 0, 11, 10);
            }

            if (this.compressor.storedOxygen > 0)
            {
                this.drawTexturedModalRect(var5 + 100, var6 + 24, 187, 0, 10, 10);
            }*/

            List<String> oxygenDesc = new ArrayList<String>();
            oxygenDesc.add(GCCoreUtil.translate("gui.oxygenStorage.desc.0"));
            oxygenDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.oxygenStorage.desc.1") + ": " + ((int) Math.floor(this.compressor.storedOxygen) + " / " + (int) Math.floor(this.compressor.maxOxygen)));
            this.oxygenInfoRegion.tooltipStrings = oxygenDesc;

            List<String> electricityDesc = new ArrayList<String>();
            electricityDesc.add(GCCoreUtil.translate("gui.energyStorage.desc.0"));
            EnergyDisplayHelper.getEnergyDisplayTooltip(this.compressor.getEnergyStoredGC(), this.compressor.getMaxEnergyStoredGC(), electricityDesc);
//			electricityDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.energyStorage.desc.1") + ((int) Math.floor(this.compressor.getEnergyStoredGC()) + " / " + (int) Math.floor(this.compressor.getMaxEnergyStoredGC())));
            this.electricInfoRegion.tooltipStrings = electricityDesc;
            
            for(int i = 0; i < this.inventorySlots.inventorySlots.size(); i++)
            {
    	        int x = this.inventorySlots.getSlot(i).xDisplayPosition;
    	        int y = this.inventorySlots.getSlot(i).yDisplayPosition;
    	        
    	        if(!(this.inventorySlots.getSlot(i).inventory instanceof InventoryPlayer))
    	        {
    		        
    		        GL11.glPushMatrix();
    		        switch(i)
    		        {
    		        	case 0:
    		        	{
    		        		this.drawTexturedModalRect(var5 + x - 2, var6 + y - 2, 213, 26, 20, 21);	        		 
    		        		break;
    		        	}
    		        	default: 
    		        	{
    		        		this.drawTexturedModalRect(var5 + x - 2, var6 + y - 2, 234, 26, 20, 21);
    		        		break;
    		        	}	        	
    		        }
    		        GL11.glPopMatrix();
    	        }
            }  
           
        }
    }
}
