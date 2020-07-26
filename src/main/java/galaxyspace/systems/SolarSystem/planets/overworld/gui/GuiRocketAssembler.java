package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.GSItems;
import galaxyspace.core.prefab.inventory.SlotUpgrades;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerRocketAssembler;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityRocketAssembler;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiRocketAssembler extends GuiContainerGC
{
    private static final ResourceLocation electricFurnaceTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/rocket_assembly.png");
    private TileEntityRocketAssembler tileEntity;
    private GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion(0, 0, 56, 9, null, 0, 0, this);
    private GuiElementInfoRegion processInfoRegion = new GuiElementInfoRegion(0, 0, 52, 9, null, 0, 0, this);

    public GuiRocketAssembler(InventoryPlayer par1InventoryPlayer, TileEntityRocketAssembler tileEntity)
    {
        super(new ContainerRocketAssembler(par1InventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
        this.xSize = 204;
        this.ySize = 256;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.electricInfoRegion.tooltipStrings = new ArrayList<String>();
        this.electricInfoRegion.xPosition = (this.width - this.xSize) / 2 + 126;
        this.electricInfoRegion.yPosition = (this.height - this.ySize) / 2 + 146;
        this.electricInfoRegion.parentWidth = this.width;
        this.electricInfoRegion.parentHeight = this.height;
        this.infoRegions.add(this.electricInfoRegion);
        List<String> desc = new ArrayList<String>();
        desc.add(GCCoreUtil.translate("gui.battery_slot.desc.0"));
        desc.add(GCCoreUtil.translate("gui.battery_slot.desc.1"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + this.inventorySlots.getSlot(0).xPos, (this.height - this.ySize) / 2 + + this.inventorySlots.getSlot(0).yPos, 18, 18, desc, this.width, this.height, this));
        this.processInfoRegion.tooltipStrings = new ArrayList<String>();
        this.processInfoRegion.xPosition = (this.width - this.xSize) / 2 + 100;
        this.processInfoRegion.yPosition = (this.height - this.ySize) / 2 + 90;
        this.processInfoRegion.parentWidth = this.width;
        this.processInfoRegion.parentHeight = this.height;
        this.infoRegions.add(this.processInfoRegion);
        
        desc = new ArrayList<String>();
        desc.add(EnumColor.BRIGHT_GREEN + GCCoreUtil.translate("gui.available_modules.desc"));
        desc.add("");
        desc.add("- " + new ItemStack(GSItems.UPGRADES, 1, 2).getDisplayName());
        desc.add("- " + new ItemStack(GSItems.UPGRADES, 1, 3).getDisplayName());
        this.infoRegions.add(new GuiElementInfoRegion((this.width + this.xSize) / 2, (this.height - this.ySize) / 2 + 16, 18, 21 * 4, desc, this.width, this.height, this));
     
    }
    

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(EnumColor.WHITE + this.tileEntity.getName(), 19, 7, 4210752);
        String displayText;

        if (this.tileEntity.processTicks > 0)
        {
            displayText = EnumColor.BRIGHT_GREEN + GCCoreUtil.translate("gui.status.running.name");
        }
        else
        {
            displayText = EnumColor.ORANGE + GCCoreUtil.translate("gui.status.idle.name");
        }

        String str = EnumColor.WHITE + GCCoreUtil.translate("gui.message.status.name") + ": " + displayText;
        this.fontRenderer.drawString(str, 140 - this.fontRenderer.getStringWidth(str) / 2, 137, 4210752);
        this.fontRenderer.drawString(EnumColor.WHITE + GCCoreUtil.translate("container.inventory"), 32, this.ySize - 91, 4210752);
//		str = "" + this.tileEntity.storage.getMaxExtract();
//		this.fontRendererObj.drawString(str, 120 - this.fontRendererObj.getStringWidth(str) / 2, 85, 4210752);
//		//		str = ElectricityDisplay.getDisplay(this.tileEntity.getVoltage(), ElectricUnit.VOLTAGE);
//		this.fontRendererObj.drawString(str, 120 - this.fontRendererObj.getStringWidth(str) / 2, 95, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the
     * items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        this.mc.renderEngine.bindTexture(this.electricFurnaceTexture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int containerWidth = (this.width - this.xSize) / 2;
        int containerHeight = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);
        int scale;

        List<String> electricityDesc = new ArrayList<String>();
        electricityDesc.add(GCCoreUtil.translate("gui.energy_storage.desc.0"));
        EnergyDisplayHelper.getEnergyDisplayTooltip(this.tileEntity.getEnergyStoredGC(), this.tileEntity.getMaxEnergyStoredGC(), electricityDesc);
        this.electricInfoRegion.tooltipStrings = electricityDesc;

        if (this.tileEntity.processTicks > 0)
        {
            scale = (int) ((double) this.tileEntity.processTicks / (double) this.tileEntity.processTimeRequired * 100);
        }
        else
        {
            scale = 0;
        }

        List<String> processDesc = new ArrayList<String>();
        processDesc.clear();
        processDesc.add(GCCoreUtil.translate("gui.electric_compressor.desc.0") + ": " + scale + "%");
        this.processInfoRegion.tooltipStrings = processDesc;

        this.drawTexturedModalRect(containerWidth + 100, containerHeight + 91, 204, 96, 52, 9);
       
        this.drawTexturedModalRect(containerWidth + 125, containerHeight + 147, 204, 96, 52, 9);
        this.drawTexturedModalRect(containerWidth + 110, containerHeight + 147, 217, 239, 11, 10);
        
        if (this.tileEntity.processTicks > 0)
        {
            scale = (int) ((double) this.tileEntity.processTicks / (double) this.tileEntity.processTimeRequired * 50);
            GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
            this.drawTexturedModalRect(containerWidth + 101, containerHeight + 92, 206, 230, scale, 7);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }

        if (this.tileEntity.getEnergyStoredGC() > 0)
        {
            scale = this.tileEntity.getScaledElecticalLevel(54);
            this.drawTexturedModalRect(containerWidth + 126, containerHeight + 148, 206, 230, scale, 7);
            this.drawTexturedModalRect(containerWidth + 110, containerHeight + 147, 206, 239, 11, 10);
        }       
   
        for(int i = 0; i < this.inventorySlots.inventorySlots.size(); i++)
        {
        	
	        int x = this.inventorySlots.getSlot(i).xPos;
	        int y = this.inventorySlots.getSlot(i).yPos;
	        Slot slot = (Slot)this.inventorySlots.inventorySlots.get(i);
        	
	        
	        GL11.glPushMatrix();
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.drawTexturedModalRect(containerWidth + x, containerHeight + y, 228, slot.slotNumber == 0 ? 185 : 206, 16, 16);

	        float[] color = new float[3];
	        
	        if(!(this.inventorySlots.getSlot(i).inventory instanceof InventoryPlayer))
	        {		   
	        	
	        	if(slot.slotNumber == 10 || slot.slotNumber == 11 || slot.slotNumber == 12)
		        {
		        	color[0] = 0.2F; //R
			        color[1] = 1.0F; //G
			        color[2] = 0.0F; //B	        
			        
		        }
	        	else if(slot.slotNumber >= 13)
		        {
			        color[0] = 1.0F; //R
			        color[1] = 0.0F; //G
			        color[2] = 0.0F; //B	    
	        	}
	        	else if(slot.slotNumber != 1)
	        	{	        		
	        		color[0] = 0.0F; //R
	    	        color[1] = 0.9F; //G
	    	        color[2] = 1.0F; //B
	        	}

	        	GL11.glColor3f(color[0], color[1], color[2]);
	        	if(slot.slotNumber == 1) 
	        	{
	        		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	 	        	this.drawTexturedModalRect(containerWidth + x - 9, containerHeight + y - 9, 215, 144, 34, 34);
	        	}
	 	        else if(slot.slotNumber < 13)
	 	        	this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 3, 207, 182, 20, 21);
	 	        else
	 	        	this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 3, 207, 203, 20, 21);
	        	  
	        	if(this.inventorySlots.getSlot(i) instanceof SlotUpgrades)
		        {
	        		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        		this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 225, 75, 20, 20);
	        	}
	        }	       	      
	        else
	        {
	        	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
 	        	this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 3, 207, 182, 20, 21);
	        }
	        
	        GL11.glPopMatrix();
        }    
        
        if(GalaxySpace.debug) GSUtils.renderDebugGui(this, containerWidth, containerHeight);
    }
}
