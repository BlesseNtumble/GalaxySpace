package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerHydroponicBase;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityHydroponicBase;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class GuiHydroponicBase extends GuiContainerGC
{
	private static final ResourceLocation fuelGeneratorTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/base_gui.png");
	private TileEntityHydroponicBase tileEntity;
	private GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion(0, 0, 56, 9, null, 0, 0, this);
	  
	
	public GuiHydroponicBase(InventoryPlayer par1InventoryPlayer, TileEntityHydroponicBase tileEntity)
    {
        super(new ContainerHydroponicBase(par1InventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
        this.xSize = 173;
        this.ySize = 205;
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
    }
	
	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	int yOffset = -18;
    	int scale;
    	
    	if (this.tileEntity.processTicks > 0)
        {
            scale = (int) ((double) this.tileEntity.processTicks / (double) this.tileEntity.processTimeRequired * 101);
        }
        else
        {
            scale = 100;
        }
    	String displayString = this.tileEntity.getName();
        this.fontRenderer.drawString(EnumColor.WHITE + displayString, this.xSize / 2 - this.fontRenderer.getStringWidth(displayString) / 2, -19, 4210752);
          	  
        String status = this.tileEntity.waterTank.getFluidAmount() > 0 ? 100 - scale + "%" : EnumColor.RED + "gui.status.nowater.name";
        String displayText = EnumColor.WHITE + GCCoreUtil.translate("gui.message.status.name") + ": " + status;           
       
        this.fontRenderer.drawString(EnumColor.WHITE + displayText, 100, 123 + yOffset, 4210752);
        this.fontRenderer.drawString(EnumColor.WHITE + GCCoreUtil.translate("container.inventory"), 14, this.ySize - 93 + 5, 4210752);
    }
	
	@Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        this.mc.renderEngine.bindTexture(this.fuelGeneratorTexture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int containerWidth = (this.width - this.xSize) / 2;
        int containerHeight = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(containerWidth, containerHeight - 20, 0, 0, this.xSize, 20);
        
        this.drawTexturedModalRect(containerWidth, containerHeight, 0, 18, this.xSize, 25);
        
        this.drawTexturedModalRect(containerWidth, containerHeight + 20, 0, 20, this.xSize, this.ySize);
        
        FluidStack water = this.tileEntity.waterTank.getFluid();
        
        int scale;

        //Arrows
      		if(tileEntity.getModuleLevel() > 0)
      			for(int i = 0; i < tileEntity.getModuleLevel(); i++)
      				this.drawTexturedModalRect(containerWidth + 65, containerHeight + 56 - (22 * i), 192, 108, 36, 17);
      	
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
            scale = 100;
        }
        
        if (tileEntity.getModuleLevel() > 0 && this.tileEntity.processTicks > 0)
        {
        	scale = (int) ((double) this.tileEntity.processTicks / (double) this.tileEntity.processTimeRequired * 36);
             
        	for(int i = 0; i < tileEntity.getModuleLevel(); i++)
        		this.drawTexturedModalRect(containerWidth + 65, containerHeight + 56 - (22 * i), 192, 124, 36 - scale, 17);
        }
        
        //Energy
        this.drawTexturedModalRect(containerWidth + 16, containerHeight + 102, 192, 47, 56, 9);
        this.drawTexturedModalRect(containerWidth + 4, containerHeight + 102, 192, 56, 11, 10);
        if (this.tileEntity.getEnergyStoredGC() > 0)
        {
            scale = this.tileEntity.getScaledElecticalLevel(55);
            this.drawTexturedModalRect(containerWidth + 116 - 99, containerHeight + 103, 192, 0, scale, 7);
            this.drawTexturedModalRect(containerWidth + 3, containerHeight + 102, 192, 7, 11, 10);           
        }
        
        //Tanks       
		this.drawTexturedModalRect(containerWidth + 5, containerHeight + 43, 192, 66, 20, 42);
		//this.drawTexturedModalRect(containerWidth + this.xSize - 28, containerHeight + 40, 192, 66, 20, 42);
		
		//this.drawTexturedModalRect(containerWidth + 100, containerHeight + 43, 192, 141, 36, 16);
		
		GL11.glPushMatrix();		
	        final int watergraphLevel = this.tileEntity.getScaledFluidLevel(38);
	        if(water != null)
	        {
	    		
	        	GL11.glPushMatrix();
	        	GSUtils.displayGauge(containerWidth + 7, containerHeight + 25, watergraphLevel, this.tileEntity.waterTank.getFluid(), 0);
	 	        //GSUtils.drawFluid(water, (this.width - this.xSize) / 2 + 7, this.height / 2 - 57, 16, 38, this.tileEntity.waterTank.getCapacity());
	 			GL11.glPopMatrix();
	        }          
	     
        GL11.glPopMatrix();
        		
        this.mc.renderEngine.bindTexture(this.fuelGeneratorTexture);
        this.drawTexturedModalRect(containerWidth + 5, containerHeight + 43, 192+20, 66, 20, 42);
        //this.drawTexturedModalRect(containerWidth + 145, containerHeight + 40, 192+20, 66, 20, 42);
        /*
        if(tileEntity.getModuleLevel() <= 0) {
        	GalaxySpace.debug(tileEntity.getModuleLevel() + "");
	        this.inventorySlots.getSlotFromInventory(tileEntity, 2).xDisplayPosition = 1000;
	        this.inventorySlots.getSlotFromInventory(tileEntity, 3).xDisplayPosition = 1000;
        }
       */
        
        // Slots
        for(int i = 0; i < this.inventorySlots.inventorySlots.size(); i++)
        {
        	int x = this.inventorySlots.getSlot(i).xPos;
	        int y = this.inventorySlots.getSlot(i).yPos;
	        
	        if(!(this.inventorySlots.getSlot(i).inventory instanceof InventoryPlayer))
	        {
		        
		        GL11.glPushMatrix();

		        switch(this.inventorySlots.getSlot(i).getSlotIndex())
		        {
		        	case 0:
		        	{
		        		this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 213, 26, 20, 21);	        		 
		        		break;
		        	}
		        	
		        	case 8:
		        	{
		        		this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 234, 175, 20, 21);	        		 
		        		break;
		        	}	
		        	
		        	case 2:
		        	case 4:
		        	case 6:
		        	{
		        		this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 192, 196, 20, 21);	        		 
		        		break;
		        	}
		        	
		        	default: 
		        	{
		        		this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 192, 26, 20, 21);
		        		break;
		        	}	        	
		        }
		        GL11.glPopMatrix();
	        }
        }
/*
        for(int i = 0; i < this.inventorySlots.inventorySlots.size(); i++)
        {
        	int x = this.inventorySlots.getSlot(i).xPos;
	        int y = this.inventorySlots.getSlot(i).yPos;
        	this.fontRenderer.drawString(EnumColor.WHITE + "" + this.inventorySlots.getSlot(i).getSlotIndex(), containerWidth + x + (4), containerHeight + y + 5, 4210752);
        }
*/
    }

	
}
